import {TempUnits} from './Weathers.enum';
import {IWeatherCardCity, IWeatherDayNight} from './IWeatherCity.interface';
import {IWeatherItemForecast, IWeatherItemCurrent} from './IWeather-item.interface';
import {generate} from 'rxjs';



export class WeatherCardCity< T extends IWeatherItemCurrent , E extends IWeatherItemForecast> implements IWeatherCardCity< T, E> {

  // private city: {
  //   id: number,
  //   name: string,
  //   country: string
  // }
  private _current: T;
  private _forecast: E[] = [];

  // private tempUnit: TempUnits;

  constructor(private city: {
                id: number,
                name: string,
                country: string,
                coord: {
                  lat: number,
                  lon: number
                }
              },
              private tempUnit: TempUnits) {
  }

  set forecast(value) {
    this._forecast = value;
  }

  set current(value) {
    this._current = value;
  }

  getCity() {
    return this.city;
  }

  getCurrentWeather(): T {
    return this._current;
  }

  getForcastWeather(): IWeatherDayNight[] {
    // console.log(this._forcast);
    if (this._forecast.length === 0) {
      // console.log('return NULL');
      return null;
    } else {

      /*Next day*/
      let nextDate: number = new Date().setDate(new Date().getDate() + 1);
      /*Beginning of the next day*/
      nextDate = new Date(nextDate).setHours(0, 0, 0, 0);

      // console.log(this._forcast);
      // console.log(nextDate);
      // console.log(this._forcast);
      let forcastToday: E[] = this._forecast.filter((fi) => {
        return fi.date.getTime() < nextDate;
      });
      let forcastNextDays = this._forecast.filter((fi) => {
        return fi.date.getTime() >= nextDate;
      });
      // console.log(forcastToday);
      let forcastDayNight: IWeatherDayNight[] = this.generateDayNightForcast(forcastToday);

      // console.log(this.generateDayNightForcast(forcastNextDays));
      forcastDayNight = forcastDayNight.concat(this.generateDayNightForcast(forcastNextDays));
      // console.log(forcastDayNight);

      return forcastDayNight;
    }
  }


  private generateDayNightForcast(forcast: E[]): IWeatherDayNight[] {
    let dailyForcast: E[][] = [];
    let c = [];

    forcast.reduce((prev, curr, i) => {
      prev.push(curr);
      if (forcast.length === i + 1) {
        // console.log(forcast.length - rez.length * 8);
        dailyForcast.push(prev);
      } else if ((i + 1) % 8 === 0 && i !== 0) {
        dailyForcast.push(prev);
        prev = [];
      }
      return prev;
    }, c = []);

    // console.log(dailyForcast);
    return dailyForcast.map((dailly) => {
        return {
          day: dailly[Math.floor(dailly.length / 2)].date,
          tempMax: dailly.reduce((prev: E, current: E, i: number): E => {
            return (prev.tempMax > current.tempMax) ? prev : current;
          }).tempMax,
          tempMin: dailly.reduce((prev: E, current: E) => {
            return (prev.tempMin < current.tempMin) ? prev : current;
          }).tempMin,
          condition: dailly[Math.floor(dailly.length / 2)].condition,
          icon: dailly[Math.floor(dailly.length / 2)].icon,
          fullWeatherDayNight: dailly
        }});

  }


  getTempUnit() {
    return this.tempUnit;
  }
}
