import {Directive, ElementRef, EventEmitter, HostListener, Output, Renderer2} from '@angular/core';


@Directive({
  selector: '[appShowUserMenu]',
})
export class ShowUserMenuDirective {

  @Output() clickOutside: EventEmitter<boolean> = new EventEmitter();

  constructor(private _el: ElementRef, private renderer: Renderer2){  }

  @HostListener('window:click', ['$event'])
  public onClick(event){
    if (!this._el.nativeElement.contains(event.target)) {
      this.clickOutside.emit(false);
    }
  }
}
