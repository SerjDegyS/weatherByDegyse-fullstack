import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FavoriteForecastComponent } from './favorite-forecast.component';

describe('FavoriteForecastComponent', () => {
  let component: FavoriteForecastComponent;
  let fixture: ComponentFixture<FavoriteForecastComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FavoriteForecastComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FavoriteForecastComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
