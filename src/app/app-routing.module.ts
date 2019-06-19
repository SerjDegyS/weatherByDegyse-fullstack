import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {WeatherCardComponent} from './weather/weather-card/weather-card.component';
import {ForcastWeatherCardComponent} from './weather/weather-card/forcast-weather-card/forcast-weather-card.component';
import {FavoritesComponent} from './weather/favorites/favorites.component';
import {UserLoginComponent} from './ui/user-login/user-login.component';

const routes: Routes = [{path: '', component: WeatherCardComponent},
  {path: 'forecast', component: ForcastWeatherCardComponent},
  {path: 'favorites', component: FavoritesComponent},
  {path: 'login', component: UserLoginComponent}
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
