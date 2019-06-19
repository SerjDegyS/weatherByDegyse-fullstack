import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthService} from './core/auth.service';
import {IUser} from './core/user.model';

declare var jquery: any;
declare var $: any;





@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'WeatherByDegys';
  user: IUser;
  isOpened: boolean = false;

  constructor(private auth: AuthService) {
    this.auth.user.subscribe(user => {
      this.isOpened = (user) ? true : false;
      this.user = user;
    });
  }

  ngOnInit(): void {

    // Hidden or show user-menu

    // this.showUserMenuByClick();
    // // JSON.parse(localStorage.getItem('user'));
    // console.log(this.user);
    

  }


  showUserMenuByClick(event) {
    this.isOpened = event;
  }


  logOut(){
    this.auth.signOut();
  }
}
