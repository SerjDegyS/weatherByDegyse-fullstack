import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {WeatherDetailsComponent} from './weather-details.component';

@NgModule({
  declarations: [WeatherDetailsComponent],
  imports: [
    CommonModule
  ],
  exports: [WeatherDetailsComponent]
})
export class WeatherDetailsModule { }
