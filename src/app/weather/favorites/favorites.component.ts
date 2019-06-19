import {ChangeDetectorRef, Component, Input, OnInit, Output} from '@angular/core';
import {IWeatherCardCity, IWeatherDayNight} from '../model/IWeatherCity.interface';
import {IWeatherItemCurrent, IWeatherItemForecast} from '../model/IWeather-item.interface';
import {FormControl} from '@angular/forms';
import {WeatherService} from '../services/weather.service';
import { AuthService } from 'src/app/core/auth.service';
import { IFavCity } from 'src/app/core/user.model';
import {FavoriteCitiesService} from '../services/favorite-cities.service';
import {map} from 'rxjs/operators';
import { NotifyService } from 'src/app/core/notify.service';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.scss']
})
export class FavoritesComponent implements OnInit {

  private favsCities: IFavCity[] = [];
  @Input() favsCitiesCards: IWeatherCardCity<IWeatherItemCurrent, IWeatherItemForecast>[] = [];
  forecastWeather: IWeatherDayNight[] = [];
  showForecast: boolean = false;
  // message: string;
  // showForecast: boolean = true;

  constructor(private authService: AuthService,
              private weatherService: WeatherService,
              private favCitiesService: FavoriteCitiesService,
              private ref: ChangeDetectorRef,
              private notify: NotifyService) {
  }

  ngOnInit() {
    /*Get user favorite cities*/
    console.log('Get user favorite cities');
    this.notify.update('SEARCH YOU FAVORITE CITY...', 'info');
      this.authService.user.subscribe(user => {
        if(user) {
          this.favCitiesService.getFavCities().subscribe(favCitiesData => {
            console.log(favCitiesData);
            this.favsCities = favCitiesData;
            this.getWeatherForFavCities();
          })
        }
      })
  };





  private getWeatherForFavCities(): IWeatherCardCity<IWeatherItemCurrent, IWeatherItemForecast>[] {
    if(this.favsCities.length !== 0) {
      this.notify.update('YOU FAVORITE CITIES', 'info');
      console.log('favcity');
      
      let favsCitiesString = this.favsCities.reduce((listId, city) => listId + city.id + ',', '');
      this.weatherService.getCurrentWeatherByCitesGroup(favsCitiesString.slice(0, -1))
      /*Get forecast weather for cities*/
        .pipe(map(obsCityCards => obsCityCards.
          map(cityCard => this.weatherService.getForcastWeatherByCityCard(cityCard)
          .pipe(map(cityCardWithForecast => cityCard = cityCardWithForecast)))))
            .pipe(map(obsFullCityCards => obsFullCityCards
              .map(obsFullCityCard => obsFullCityCard.subscribe(fullCityCard => this.favsCitiesCards.push(fullCityCard))
              ))).subscribe();
    } else {
      this.notify.update('YOU DON\'T HAVE FAVORITE CITIES YET', 'info');
      return this.favsCitiesCards = [];
    }
  }

  public stateOfTemperature(cityCard): string {
    return (cityCard.getCurrentWeather().temp <= cityCard.getForcastWeather()[0].fullWeatherDayNight[0].temp) ? 'and rising' : 'and falling';
  }

  getForecastWeather(cityCard: IWeatherCardCity<IWeatherItemCurrent, IWeatherItemForecast>){
    this.forecastWeather = cityCard.getForcastWeather();
    console.log(this.forecastWeather);
    return this.forecastWeather;
  }

  showForecastWeather() {
    this.showForecast = !this.showForecast;
  }

  removeCity(city: IWeatherCardCity<IWeatherItemCurrent, IWeatherItemForecast>, item){
    const remCity = {
      id: city.getCity().id,
      name: city.getCity().name
    };
    console.log(this.favsCitiesCards.splice(item, 1));
    this.ref.detectChanges();
    this.favsCitiesCards = [];

    this.favCitiesService.removeFavCity(remCity);
  }
}

// public addCityToFav(city: IWeatherCardCity<IWeatherItemCurrent, IWeatherItemForecast>){
//   const newFavCity = {
//     id: city.getCity().id,
//     name: city.getCity().name
//   }
//   this.favCitiesService.updateFavCities(newFavCity);
