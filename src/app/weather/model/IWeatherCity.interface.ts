import {TempUnits} from './Weathers.enum';
import {IWeatherItemForecast, IWeatherItemCurrent} from './IWeather-item.interface';

export interface IWeatherDayNight {
  day: Date;
  tempMax: number;
  tempMin: number;
  condition: string;
  icon: string;
  fullWeatherDayNight: IWeatherItemForecast[];
}


export interface IWeatherCardCity<T extends IWeatherItemCurrent, E extends IWeatherItemForecast> {

  current(arg: T): void;
  forecast(arg: E[]): void;

  getCity(): {
    id: number,
    name: string,
    country: string
  };

  getCurrentWeather(): T;

  getForcastWeather<E>(): IWeatherDayNight[];

  // Temperature units
  getTempUnit(): TempUnits;


}
