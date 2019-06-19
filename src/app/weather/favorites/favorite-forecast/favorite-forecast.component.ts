import {Component, Input, OnInit} from '@angular/core';
import {IWeatherItemForecast} from '../../model/IWeather-item.interface';
import {IWeatherDayNight} from '../../model/IWeatherCity.interface';

@Component({
  selector: 'app-favorite-forecast',
  templateUrl: './favorite-forecast.component.html',
  styleUrls: ['./favorite-forecast.component.scss']
})
export class FavoriteForecastComponent implements OnInit {

  @Input() dailyForecast: IWeatherDayNight[];
  currdailyForecast: IWeatherItemForecast[];
  isShowedForecast: boolean = false;
  currWeatherItem: number = 0;

  constructor() {
  }

  ngOnInit() {
    this.currdailyForecast = this.dailyForecast[this.currWeatherItem].fullWeatherDayNight;
  }

  showForecast() {
    this.isShowedForecast = !this.isShowedForecast;
    console.log(this.isShowedForecast)
  }

  showWeatherItemLeft(){
    this.currWeatherItem -= 1;
    this.currdailyForecast = this.dailyForecast[this.currWeatherItem].fullWeatherDayNight;
  }

  showWeatherItemRight(){
    this.currWeatherItem += 1;
    this.currdailyForecast = this.dailyForecast[this.currWeatherItem].fullWeatherDayNight;
  }

}
