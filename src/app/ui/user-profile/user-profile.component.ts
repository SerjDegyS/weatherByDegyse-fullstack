import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/auth.service';
import {Observable} from 'rxjs';
import {IUser} from '../../core/user.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  constructor(public auth: AuthService,     private router: Router) {
  }

  ngOnInit() {
  }

  login(){
    this.router.navigate(['/login']);
  }

  logOut() {
    this.auth.signOut();
  }

}
