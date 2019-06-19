import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {WeatherCardModule} from './weather-card/weather-card.module';
import {FavoritesModule} from './favorites/favorites.module';
import {WeatherService} from './services/weather.service';
import {FavoriteCitiesService} from './services/favorite-cities.service';
import { WeatherDetailsComponent } from './weather-details/weather-details.component';
import {AuthService} from '../core/auth.service';

@NgModule({
  declarations: [WeatherDetailsComponent],
  imports: [
    CommonModule,
    WeatherCardModule,
    FavoritesModule
  ],
  providers: [WeatherService, FavoriteCitiesService, AuthService]
})
export class WeatherModule { }
