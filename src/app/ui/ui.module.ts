import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserLoginComponent } from './user-login/user-login.component';
import {MaterialAppModule} from '../ngmaterial.module';
import {ReactiveFormsModule} from '@angular/forms';
import { UserProfileComponent } from './user-profile/user-profile.component';
import {AuthService} from '../core/auth.service';
import { NotificationMessageComponent } from './notification-message/notification-message.component';

@NgModule({
  declarations: [UserLoginComponent, UserProfileComponent, NotificationMessageComponent],
  providers:[AuthService],
  imports: [
    CommonModule,
    MaterialAppModule,
    ReactiveFormsModule,
  ],
  exports:[UserLoginComponent, UserProfileComponent, NotificationMessageComponent]
})
export class UiModule { }
