import {Pod, TempUnits} from './Weathers.enum';

export interface IWeatherItemForecast {
  date: Date;
  temp: number;
  tempMin: number;
  tempMax: number;
  pressure: number;
  humidity: number;
  windSpeed: number;
  condition: string;
  description: string;
  icon: string;
  pod: Pod;
}


export interface IWeatherItemCurrent extends IWeatherItemForecast {
  sunrise: Date;
  sunset: Date;
  windDeg: number;
  seaLevel?: number;
  grndLevel?: number;
}

