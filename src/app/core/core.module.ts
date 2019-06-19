import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from './auth.service';
import { NotifyService } from './notify.service';

@NgModule({
  declarations: [],
  providers: [AuthService, NotifyService],
  imports: [
    CommonModule
  ]
})
export class CoreModule { }
