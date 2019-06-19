import {Component, OnInit, Output, Input} from '@angular/core';
import {Pod, TempUnits} from '../model/Weathers.enum';
import {WeatherCardCity} from '../model/WeatherCardCity.class';
import {IWeatherItemCurrent, IWeatherItemForecast} from '../model/IWeather-item.interface';

import {WeatherService} from '../services/weather.service';
import {IWeatherCardCity, IWeatherDayNight} from '../model/IWeatherCity.interface';
import {IFavCity, IUser} from '../../core/user.model';
import {AuthService} from '../../core/auth.service';
import {FavoriteCitiesService} from '../services/favorite-cities.service';
import {el} from '@angular/platform-browser/testing/src/browser_util';
import { NotifyService } from 'src/app/core/notify.service';
import { error } from 'util';

@Component({
  selector: 'app-weather-card',
  templateUrl: './weather-card.component.html',
  styleUrls: ['./weather-card.component.scss'],
  providers: [WeatherService, AuthService]
})
export class WeatherCardComponent implements OnInit {

  weatherCardCity: IWeatherCardCity<IWeatherItemCurrent, IWeatherItemForecast>;
  currentWeather: IWeatherItemCurrent;
  favoriteCities: IFavCity[] = [];
  showforcast: boolean = false;
  @Output() dailyForecast: IWeatherItemForecast[];
  city: string = 'kiev';
  private currentPosition: {
    lat: number,
    lng: number,
  };


  constructor(private weatherHttp: WeatherService, 
              // private favoriteCitiesService: FavoriteCitiesService,
              private favCitiesService: FavoriteCitiesService, 
              private authService: AuthService,
              private notify: NotifyService ) {
  }

  ngOnInit(): void {
    if(navigator.geolocation) {
      this.notify.update('SEARCH YOU CURRENT POSITION...', 'info');
      navigator.geolocation.getCurrentPosition(position => {
          this.currentPosition = {
            lat: position.coords.latitude,
            lng: +position.coords.longitude
          }

          this.weatherHttp.getCurrentWeatherCardByPosition(this.currentPosition).subscribe(data => {
            console.log(data);
            this.weatherCardCity = data;
            this.currentWeather = this.weatherCardCity.getCurrentWeather();
            this.city = this.weatherCardCity.getCity().name;
          });
        },
        error => {
          // alert(error.message + '. Please turn on you geolocation!');
          this.notify.update('PLEASE TURN GEOLOCATION !!!', 'error');
          this.weatherHttp.getCurrentWeatherCardByCity(this.city).subscribe(data => {
            this.weatherCardCity = data;
            this.currentWeather = this.weatherCardCity.getCurrentWeather();
          })
        }
      )} else {
        this.weatherHttp.getCurrentWeatherCardByCity(this.city).subscribe(data => {
          this.weatherCardCity = data;
          this.currentWeather = this.weatherCardCity.getCurrentWeather();
      });
    }

    console.log('Get user favorite cities');
    this.authService.user.subscribe(user => {
      if(user) {
        this.favCitiesService.getFavCities().subscribe(favCitiesData => {
          console.log(favCitiesData);
          this.favoriteCities = favCitiesData;
        })
      }
    })

    // this.userService.user.subscribe(user => {
    //   this.user = user
    //   console.log(this.user);
    // });
  }

  public searchCityWeatherCard(){
    this.showforcast = false;
    console.log('SEARCH!!!')
    this.weatherHttp.getCurrentWeatherCardByCity(this.city).subscribe(data =>{
      this.weatherCardCity = data;
      this.currentWeather = this.weatherCardCity.getCurrentWeather();
      console.log(this.weatherCardCity);
      this.notify.update('CITY FOUND', 'success');
    }, error => {
      (error.status == '404') ? this.notify.update('CITY NOT FOUND! CHECK AND TRY AGAIN', 'error') : this.notify.update('SERVSER  OR NETWORK ERROR! TRY AGAIN', 'error')
    });
  }
    

  public receiveDailyForecastFromChild(evnt){
    this.dailyForecast = evnt;
    }


  public addCityToFav(city: IWeatherCardCity<IWeatherItemCurrent, IWeatherItemForecast>){
    const newFavCity = {
      id: city.getCity().id,
      name: city.getCity().name
    }
    this.favCitiesService.addFavCities(newFavCity);
  }

  isAlreadyAdd(city: IWeatherCardCity<IWeatherItemCurrent, IWeatherItemForecast>): boolean {
    // console.log(!(this.favoriteCities.filter(favCity => city.getCity().id === favCity.id).length === 0));
    return !(this.favoriteCities.filter(favCity => city.getCity().id === favCity.id).length === 0);
  }
}
