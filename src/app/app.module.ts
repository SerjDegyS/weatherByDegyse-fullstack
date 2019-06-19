import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
// import { AngularFireModule} from '@angular/fire';
// import {AngularFirestoreModule} from '@angular/fire/firestore';
// import { AngularFireStorageModule } from '@angular/fire/storage';
// import { AngularFireAuthModule } from '@angular/fire/auth';
// import {environment} from '../environments/environment';
import {FavoritesModule} from './weather/favorites/favorites.module';
import {WeatherCardModule} from './weather/weather-card/weather-card.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialAppModule} from './ngmaterial.module';
import {AuthService} from './core/auth.service';
import {UiModule} from './ui/ui.module';
import {ShowUserMenuDirective} from './show-user-menu.directive';
import { CoreModule } from './core/core.module';


@NgModule({
  declarations: [
    AppComponent,
    ShowUserMenuDirective
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialAppModule,
    FormsModule,
    AppRoutingModule,
    CoreModule,
    HttpClientModule,
    WeatherCardModule,
    FavoritesModule,
    UiModule,
    // AngularFireModule.initializeApp(environment.firebase, 'WeatherByDegys'),
    // AngularFirestoreModule,
    // AngularFireAuthModule,
    // AngularFireStorageModule

  ],
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
