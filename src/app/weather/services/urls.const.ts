export class UrlsConst {

  private static APPID = `d7068ea79439a1c4e4435f942b417139`;


  public static weatherByCityName(city: string): string {
    const url = `https://api.openweathermap.org/data/2.5/weather?q=${city.toLowerCase()}&units=metric&APPID=` + this.APPID;
    return url;
  }

  public static weatherByPosition(lat: string, lng: string): string {
    const url = `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lng}&units=metric&APPID=` + this.APPID;
    return url;
  }

  public static forecastByCityId(cityId: string): string {
    const url = `https://api.openweathermap.org/data/2.5/forecast?id=${cityId}&units=metric&APPID=` + this.APPID;
    return url;
  }

  public static weatherByAllCitesId(groupId: string): string{
    const url = `https://api.openweathermap.org/data/2.5/group?id=${groupId}&units=metric&APPID=` + this.APPID;
    return url;
  }
}

