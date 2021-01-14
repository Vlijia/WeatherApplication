package ro.mta.se.lab.model;

import com.google.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

/**
 * This class contains information about a city
 * and a instance of WeatherDates object
 *
 * @author Vlijia Stefan
 */

public class City {

    /**
     * Member description
     */

    String id;
    StringProperty cityName;
    String  lat;
    String lon;

    WeatherDates weatherDates;

    /**
     * City constructor used for members initialization
     *
     * @param cityId is the city id
     * @param cityName is the city name
     * @param lat is the latitude of city
     * @param lon is the longitude of city
     */

    public City(String cityId, String cityName, String lat, String lon) {
        this.id = cityId;
        this.cityName = new SimpleStringProperty(cityName);
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Function responsible for injection based
     * on Java EE 6 / JSR 330 annotations
     *
     * @param weatherDates is the WeatherDates object to be injected
     */

    @Inject
    public void setWeatherDates(WeatherDates weatherDates) {

        this.weatherDates = weatherDates;
    }

    /**@return String of city name*/
    public String getCityName() {
        return cityName.get();
    };

    /**@return city id value*/
    public String getCityId() {
        return this.id;
    }

    /**
     * @param API_KEY is the api key to be set
     * @return true if the setting of api key succeed
     * @throws Exception if api kei is invalid
     * */

    public boolean setWeatherApiKey(String API_KEY) throws Exception {
        return weatherDates.setApiKey(API_KEY);
    }

    /**Function responsible for updating the weather dates
     *
     * @return true if the actualization succeed
     * @throws Exception if city id is invalid
     * */

    public boolean updateWeather() throws Exception {
        return weatherDates.updateWeatherDates(this.id);
    }

    /**@return temperature value or null*/
    public String getTemp() {
        return weatherDates.getTemp();
    }

    /**@return pressure value or null*/
    public String getPressure() {
        return weatherDates.getPressure();
    }

    /**@return humidity value or null*/
    public String getHumidity() {
        return weatherDates.getHumidity();
    }

    /**@return wind value or null*/
    public String getWind() {
        return weatherDates.getWind();
    }

    /**@return time to display value or null*/
    public String getTime() {
        return weatherDates.getTime();
    }

    /**@return meteorological description or null*/
    public String getDescription() {
        return weatherDates.getdescription();
    }

    /**@return rain value or null*/
    public String getRain() {
        return weatherDates.getRain();
    }

    /**@return Image object value or null*/
    public Image getImage() {
        return weatherDates.getImg();
    }

}
