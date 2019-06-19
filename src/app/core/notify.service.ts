import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

// Notify users about errors and other messages
export interface Msg {
  content: string,
  style: string
}

@Injectable({
  providedIn: 'root'
})
export class NotifyService {

  private _msgSource = new Subject<Msg | null>();

  msg = this._msgSource.asObservable();

  constructor() { };

  update(content: string, style: string) {
    const msg: Msg = {content, style};
    this._msgSource.next(msg);
  }

  clear() {
    this._msgSource.next(null);
  }
}
