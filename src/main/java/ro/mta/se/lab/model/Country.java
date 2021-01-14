package ro.mta.se.lab.model;

import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class contains information about a country
 * and a list of City object corresponding to the country
 *
 * @author Vlijia Stefan
 */

public class Country {

    /**
     * Member description
     */

    String countryCode;
    ArrayList<City> cityList;

    /**
     * Country constructor used for members initialization
     *
     * @param cityId is the city id
     * @param cityName is the city name
     * @param lat is the latitude of city
     * @param lon is the longitude of city
     * @param countryCode is the country code
     */

    public Country(String cityId, String cityName, String lat, String lon, String countryCode)
    {
        this.countryCode = countryCode;
        cityList = new ArrayList<>();
        addCity(cityId, cityName, lat, lon);
    }

    /**@return String of country code*/
    public String getCountryCode() {
        return this.countryCode;
    }

    /**@return String of country name*/
    public String getCountryName() {
        Locale locale=new Locale( "",this.countryCode );
        return locale.getDisplayCountry();
    }

    /**@return List of cities*/
    public List<City> getCityList() {
        return this.cityList;
    };

    /**
     * Function responsible for adding a new city in cities list
     *
     * @param cityId is the city id
     * @param cityName is the city name
     * @param lat is the city latitude
     * @param lon is the city longitude
     */

    public void addCity(String cityId, String cityName, String lat, String lon){
        try {
            City city = new City(cityId, cityName, lat, lon);
            city.setWeatherDates(new WeatherDates());
            cityList.add(city);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
