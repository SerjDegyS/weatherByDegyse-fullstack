package com.weatherbydegys.backend.city_data_source;


import com.weatherbydegys.backend.model.FavCity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReadJSONCityList {

//    public static void main(String[] args){
//        System.out.println(readAndWriteToJDBC());
//    }


    public static List<FavCity> readAndWriteToJDBC() {

        //JSON parser object to parse file
        JSONParser jsonParser = new JSONParser();

        List<FavCity> favCities = new ArrayList<>();

        try (FileReader reader = new FileReader("src\\main\\resources\\DataSource\\city.list.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray cityList = (JSONArray) obj;

            //Iterate over array and parse all city
             favCities =(List<FavCity>) cityList
                     .stream()
                     .map(city -> parseCityObject((JSONObject) city))
                     .collect(Collectors.toList());
            System.out.println("Cities found = " + favCities.size());



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return favCities;
    }

    private static FavCity parseCityObject(JSONObject city) {

        FavCity favCity = new FavCity(
                //Get city id
                Long.parseLong(city.get("id").toString()),
                //Get city name
                city.get("name").toString(),
                //Get city country
                city.get("country").toString()
        );
        System.out.println(favCity);
        return favCity;
    }
}
