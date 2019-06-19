import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';
import {IWeatherItemCurrent, IWeatherItemForecast} from '../../model/IWeather-item.interface';
import {IWeatherCardCity, IWeatherDayNight} from '../../model/IWeatherCity.interface';
import {WeatherCardCity} from '../../model/WeatherCardCity.class';
import {WeatherService} from '../../services/weather.service';

@Component({
  selector: 'app-forcast-weather-card',
  templateUrl: './forcast-weather-card.component.html',
  styleUrls: ['./forcast-weather-card.component.scss']
})
export class ForcastWeatherCardComponent implements OnInit {
  @Input() weatherCardCity: IWeatherCardCity<IWeatherItemCurrent, IWeatherItemForecast>;
  @Output() dailyForecast = new EventEmitter();
  forcastWeather: IWeatherDayNight[];


  constructor(private weatherHttp: WeatherService) {
  }

  ngOnInit() {
    // console.log(this.weatherCardCity);
    // console.log(this.weatherCardCity.getCurrentWeather().date);
    if (this.weatherCardCity.getForcastWeather()) {
      this.setForecastToComponent();
    } else {
      console.log('getForcast');
      this.weatherHttp.getForcastWeatherByCityCard(this.weatherCardCity).subscribe(data => {
        this.weatherCardCity = data;
        // console.log(this.weatherCardCity);
        this.setForecastToComponent();
      });
    }
  }
/*Sent to card.component forecast weather by current day*/
  public showForecast(weather) {
    this.dailyForecast.emit(weather.fullWeatherDayNight);
  }

  private setForecastToComponent(){
    this.forcastWeather = this.weatherCardCity.getForcastWeather();
    this.dailyForecast.emit(this.forcastWeather[0].fullWeatherDayNight);
  }
}
