import { TestBed } from '@angular/core/testing';

import { WeatherService} from './weather.service';

describe('Weather.ServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WeatherService = TestBed.get(WeatherService);
    expect(service).toBeTruthy();
  });
});
