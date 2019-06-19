import {Component, Input, OnInit} from '@angular/core';
import {IWeatherDayNight} from '../model/IWeatherCity.interface';
import {IWeatherItemForecast} from '../model/IWeather-item.interface';

@Component({
  selector: 'app-weather-details',
  templateUrl: './weather-details.component.html',
  styleUrls: ['./weather-details.component.scss']
})
export class WeatherDetailsComponent implements OnInit {

  @Input() isShowBackgroundImage: boolean = true;
  @Input() dailyForecast: IWeatherItemForecast[] = [];

  constructor() { }

  ngOnInit() {
    console.log(this.dailyForecast);
  }

}
