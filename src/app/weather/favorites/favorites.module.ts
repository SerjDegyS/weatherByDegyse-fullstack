import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FavoritesComponent } from './favorites.component';
import {FavoriteCitiesService} from '../services/favorite-cities.service';
import { AuthService } from 'src/app/core/auth.service';
import {WeatherDetailsComponent} from '../weather-details/weather-details.component';
import {WeatherCardModule} from '../weather-card/weather-card.module';
import {WeatherDetailsModule} from '../weather-details/weather-details.module';
import { FavoriteForecastComponent } from './favorite-forecast/favorite-forecast.component';

@NgModule({
  declarations: [FavoritesComponent, FavoriteForecastComponent],
  providers:[AuthService],
  imports: [
    CommonModule,
    WeatherDetailsModule
  ]
})
export class FavoritesModule { }
