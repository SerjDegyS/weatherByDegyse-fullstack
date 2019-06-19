import { Injectable } from '@angular/core';
import {Observable, of} from 'rxjs';
import {IFavCity, IUser} from './user.model';
// import {AngularFireAuth} from '@angular/fire/auth';
// import {AngularFirestore, AngularFirestoreDocument} from '@angular/fire/firestore';
import {Router} from '@angular/router';
import {switchMap, map} from 'rxjs/operators';
// import {auth} from 'firebase';
import { NotifyService } from './notify.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';




@Injectable()
export class AuthService {
  user: Observable<IUser | null>;

  constructor(
    private http: HttpClient,

    // private afAuth: AngularFireAuth,
    // private afs: AngularFirestore,
    private router: Router,
    private notify: NotifyService
  ) {
    this.user = this.http.get('resource').pipe(
      map((user) => {
        if (user) {
          return {
              uid: user['uid'],
              email: user['email'],
              photoURL: user['photoURL'],
              displayName: user['name'],
              favCity: null,
            };
        } else {return null; }
    }));
    console.log(this.user);

    // this.user = this.afAuth.authState.pipe(
    //   switchMap(user => {
    //     if (user) {
    //       // localStorage.setItem('user', JSON.stringify(user));
    //       return this.afs.doc<IUser>(`users/${user.uid}`).valueChanges();
    //     } else {return of(null)}
    //   })
    // );
    //
    // this.updateUserFavCities();


  }
  // return this.afs.doc<IUser>(`users/${user.uid}`).valueChanges().pipe(switchMap(data => {
  // return this.afs.doc<IFavCity[]>(`users/${data.uid}/favCities/${data.uid}`).valueChanges().subscribe(favData => {
  // this.currUser = data;
  // this.currUser.favCity = favData[`favCities`];
  // return this.currUser;
  // });
  // });


  // private updateUserFavCities(){
  //   this.user.subscribe(user => {
  //     if (user) {
  //       this.currUser = user;
  //       this.afs.doc<IFavCity[]>(`users/${this.currUser.uid}/favCities/${this.currUser.uid}`).valueChanges()
  //         .subscribe(favCities => {
  //           if (!favCities['favCities']) {
  //             this.currUser.favCity = [];
  //           }else {
  //             this.currUser.favCity = favCities['favCities'];
  //           }
  //             console.log(this.currUser);
  //
  //         })
  //     }
  //   }
  //     )
  //   }


  //  Auth Methods //

  headerDict = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Access-Control-Allow-Headers': 'content-type',
    'Access-Control-Allow-Origin': '*',
    // 'Cookie': 'JSESSIONID=31FBBB40AEC0A2287755FF96CAC463FC'
  };

  public googleLogin() {


    const requestOptions = { headers: new HttpHeaders(this.headerDict)};
    return this.http.get('http://localhost:8080/api/user/12', requestOptions).subscribe(data => console.log(data)
    );
    // const provider = new auth.GoogleAuthProvider();
    // return this.oAuthLogin(provider);
  }

  public facebookLogin(){
    const requestOptions = { headers: new HttpHeaders(this.headerDict)};
    return this.http.get('http://localhost:8080/api/user/12', requestOptions).subscribe(data => console.log(data));
    // const provider = new auth.FacebookAuthProvider();
    // return this.oAuthLogin(provider);
  }
  //
  public signOut() {
    this.http.post('http://localhost:8080/signout', {}).toPromise().then( () => {
      this.router.navigate(['/']);
    });

    // this.afAuth.auth.signOut().then(() => {
    //   console.log('SIGNOUT!!!');
    //   console.log(this.user)
    //   this.router.navigate(['/']);
    // });
  }




  // private oAuthLogin(provider: any){
  //   return this.afAuth.auth
  //     .signInWithPopup(provider)
  //     .then(credential => {
  //       console.log('Welcome to WeatherByDegys!!!');
  //       this.notify.update('Welcome to WeatherByDegys!!!', 'success');
  //       return this.updateUserData(credential.user);
  //     })
  //     .catch(error => this.handleError(error));
  // }

  // public addFavCity(uid: string, city: IFavCity) {
  //   console.log(this.currUser);
  //
  //   const userRefFavCities = this.afs.doc(`users/${uid}/favCities/${uid}`);
  //   console.log(city);
  //
  //
  //   // if (!this.currUser.favCity){
  //   //   this.currUser.favCity  = [].concat(city);
  //   // }else {
  //     this.currUser.subscribe(user => {
  //       user.favCity.push(city);
  //       console.log(user);
  //       return userRefFavCities.set({ favCities: user.favCity});
  //     })
  // }



  private handleError(err: Error) {
    console.log(err);
    this.notify.update(err.message, 'error');
  }

  // private updateUserData(user: IUser) {
  //   const userRef: AngularFirestoreDocument<IUser> = this.afs.doc(`users/${user.uid}`);
  //
  // const data: IUser = {
  //   uid: user.uid,
  //   email: user.email || null,
  //   displayName: user.displayName || 'user name',
  //   photoURL: user.photoURL || 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRIfMLw1MOz84I_ySvxyLSaKUoATrd30bkNuhn43A84xA2tsryV',
  // };
  // console.log(data);
  // return userRef.set(data).catch(err => this.handleError(err));
  // }

}
