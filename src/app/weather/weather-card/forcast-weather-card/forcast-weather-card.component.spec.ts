import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForcastWeatherCardComponent } from './forcast-weather-card.component';

describe('ForcastWeatherCardComponent', () => {
  let component: ForcastWeatherCardComponent;
  let fixture: ComponentFixture<ForcastWeatherCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForcastWeatherCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForcastWeatherCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
