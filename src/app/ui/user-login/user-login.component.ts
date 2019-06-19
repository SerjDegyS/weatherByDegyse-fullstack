import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/auth.service';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { Router } from '@angular/router';
import { IUser } from 'src/app/core/user.model';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent implements OnInit {

  user: IUser;
  message: string = 'CHOSE YOU LOGIN PROVIDERS';

  constructor(public auth: AuthService, private router: Router) {
    auth.user.subscribe(data => {
      this.user = data;
      this.message = (this.user) ? 'YOU ARE ALREADY LOGINED' : 'CHOSE YOU LOGIN PROVIDERS';
    });
    
   }

  ngOnInit() {

  }
///  Social Login
  async signInWithGoogle(){
    console.log(this.user);
    await this.auth.googleLogin();
    return  await this.afterSignIn();
  }

  async signInWithFacebook(){
    console.log(this.user);
    await this.auth.facebookLogin();
    console.log(this.user);
    
    return await this.afterSignIn();
  }

// Shared
  private afterSignIn(){

    return this.router.navigate(['/favorites']);
  }

  async signOut(){
    await this.auth.signOut();
  }
}
