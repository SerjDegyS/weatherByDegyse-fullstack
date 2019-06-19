import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {WeatherCardComponent} from './weather-card.component';
import {ForcastWeatherCardComponent} from './forcast-weather-card/forcast-weather-card.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {WeatherDetailsComponent} from '../weather-details/weather-details.component';
import {WeatherDetailsModule} from '../weather-details/weather-details.module';

@NgModule({
  declarations: [WeatherCardComponent, ForcastWeatherCardComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    WeatherDetailsModule
  ]
})
export class WeatherCardModule { }
