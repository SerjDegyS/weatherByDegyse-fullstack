import {Injectable, OnInit} from '@angular/core';
// import {AngularFirestore, AngularFirestoreCollection} from '@angular/fire/firestore';
import {IFavCity, IUser} from '../../core/user.model';
import {map} from 'rxjs/operators';
import {AuthService} from '../../core/auth.service';
import {Observable} from 'rxjs';
import { NotifyService } from 'src/app/core/notify.service';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

@Injectable()
export class FavoriteCitiesService implements OnInit {

  // favCitiesCollection: AngularFirestoreCollection<any>;
  currUser: IUser;
  ObsUserFavCities: Observable<IFavCity[] | []>;
  userFavCities: IFavCity[] = [];

  constructor(private http: HttpClient, private auth: AuthService, private notify: NotifyService) {
    this.auth.user.subscribe(user => {
      if (user) {
        this.currUser = user;
        this.ObsUserFavCities = this.getFavCities();
        this.ObsUserFavCities.subscribe(favCities => this.userFavCities = favCities);
        //  set document fo user on firebase data\\
        // this.favCitiesCollection = this.afs.collection('userFavCities');
      }
      });
  }

  ngOnInit(): void {
  }

  getFavCities(): Observable<IFavCity[] | []> {

    // return this.afs.doc<IFavCity[]>(`userFavCities/${this.currUser.uid}`).valueChanges().pipe(map(favData => {
    //   return (favData) ? this.userFavCities = favData['favCities'] : [];
    // }));
    return Observable.create([]);
  }

  addFavCities(newFavCity: IFavCity): boolean {
    let rez = false;
    // this.afs.doc<any>(`userFavCities/${this.currUser.uid}`).set({favCities: this.userFavCities.concat(newFavCity)})
    // .then(() => {
    //   this.notify.update('CITY SUCCESSFULLY ADDED', 'success');
    // })
    return rez;
  }


  removeFavCity(remFavCity: IFavCity) {
    for (let i = 0; i < this.userFavCities.length; i++) {
      if (this.userFavCities[i].id === remFavCity.id) {
        this.userFavCities.splice(i, 1);
        console.log(this.userFavCities);
      }
    }
    // console.log(remFavCity)
    // this.afs.doc<any>(`userFavCities/${this.currUser.uid}`).set({favCities: this.userFavCities});

  }
}
