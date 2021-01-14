package ro.mta.se.lab.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class implementing work with weather api service
 *
 * @author Vlijia Stefan
 */

public class WeatherDates {

    /**
     * Member description
     */

    StringProperty pressure;
    StringProperty humidity;
    StringProperty wind;
    StringProperty temperature;
    StringProperty time;
    StringProperty description;
    StringProperty rain;

    Image image;

    String API_KEY;

    /**
     * WeatherDates constructor used for members initialization
     */

    public WeatherDates()
    {
        this.pressure = new SimpleStringProperty();
        this.humidity = new SimpleStringProperty();
        this.wind = new SimpleStringProperty();
        this.temperature = new SimpleStringProperty();
        this.time = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this. API_KEY = new String();
    }

    /**@return pressure value or null*/
    public String getPressure() {
        if (pressure == null) {
            return null;
        }
        return pressure.get();
    }

    /**@param pressure pressure value to set*/
    public void setPressure(String pressure) {
        this.pressure.set(pressure);
    }

    /**@return humidity value or null*/
    public String getHumidity() {
        if (humidity == null) {
            return null;
        }
        return humidity.get();
    }

    /**@param humidity humidity value to set*/
    public void setHumidity(String humidity) {
        this.humidity.set(humidity);
    }

    /**@return wind value or null*/
    public String getWind() {
        if (wind == null) {
            return null;
        }
        return wind.get();
    }

    /**@param wind wind value to set*/
    public void setWind(String wind) {
        this.wind.set(wind);
    }

    /**@return temperature value or null*/
    public String getTemp() {
        if (temperature == null) {
            return null;
        }
        return temperature.get();
    }

    /**@param temperature temperature value to set*/
    public void setTemperature(String temperature) {
        this.temperature.set(temperature);
    }

    /**@return time to display or null*/
    public String getTime() {
        if (time == null) {
            return null;
        }
        return time.get();
    };

    /**@param time time value to set*/
    public void setTime(String time) {
        this.time.set(time);
    }

    /**@return meteorological description or null*/
    public String getdescription() {
        if (description == null) {
            return null;
        }
        return description.get();
    };

    /**@param description description value to set*/
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**@return rain value or null*/
    public String getRain() {
        if (rain == null) {
            return null;
        }
        return rain.get();
    }

    /**@param rain rain value to set*/
    public void setRain(String rain) {
        this.rain.set(rain);
    }

    /**@return Image object or null*/
    public Image getImg() {
        if (image == null) {
            return null;
        }
        return this.image;
    };

    /**
     * Function responsible to test if api key is valid and set the API_KEY member
     *
     * @param apiKey is KEY string used for http request
     * @return true is the api key is valid
     * @throws Exception if the api key is invalid
     */

    public boolean setApiKey(String apiKey) throws Exception {
        String urlString = "http://api.openweathermap.org/data/2.5/weather?id=524901" + "&appid=" + apiKey + "&units=metric";

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        this.API_KEY = apiKey;
        return true;
    }

    /**
     * Function responsible for converting the
     * json response to Map<String,Object>
     *
     * @param str is json response from weather service
     * @return Map<String,Object> with content of json response
     */

    private static Map<String,Object> jsonToMap(String str){
        Map<String,Object> map = new Gson().fromJson(str,new TypeToken<HashMap<String,Object>>() {}.getType());
        return map;
    }

    /**
     * Function responsible for making a new request to weather service and
     * parse and prelucrate the json response
     *
     * @param cityId is the id of the city
     * @return true if the cityId is valid and request
     *         to weather service succeed
     * @throws Exception if the cityId is invalid
     */

    public boolean updateWeatherDates(String cityId) throws Exception {
        String urlString = "http://api.openweathermap.org/data/2.5/weather?id=" + cityId + "&appid=" + API_KEY + "&units=metric";

        StringBuilder result = new StringBuilder();
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null){
            result.append(line);
        }

        rd.close();

        Map<String, Object > respMap = jsonToMap (result.toString());

        if(respMap.get("main") != null)
        {
            Map<String, Object > mainMap = jsonToMap (respMap.get("main").toString());

            String temp = mainMap.get("temp").toString();
            float float_temp = Float.parseFloat(temp);
            this.setTemperature(String.valueOf((int)float_temp));

            String humidity = mainMap.get("humidity").toString();
            float float_humidity = Float.parseFloat(humidity);
            this.setHumidity(String.valueOf((int)float_humidity) + "%");

            String press = mainMap.get("pressure").toString();
            float float_press = Float.parseFloat(press);
            this.setPressure(String.valueOf((int)float_press) + " hPa");
        }

        if(respMap.get("wind").toString() != null)
        {
            Map<String, Object > windMap = jsonToMap (respMap.get("wind").toString());

            String wind = windMap.get("speed").toString();
            double speed = Double.parseDouble(wind);
            this.setWind(String.valueOf((int)(3.6 * speed)) + " km/h");
        }

        if(respMap.get("weather") != null)
        {
            String weather_str = respMap.get("weather").toString();

            String description = new String();
            int description_index = weather_str.indexOf("description");
            description_index += 12;
            while(weather_str.charAt(description_index) != ','){
                description = description+weather_str.charAt(description_index);
                description_index++;
            }
            this.setDescription(description);

            int icon_index = weather_str.indexOf("icon");
            String iconId = new String();
            icon_index += 5;
            while(weather_str.charAt(icon_index) != '}'){
                iconId = iconId+weather_str.charAt(icon_index);
                icon_index++;
            }
            BufferedImage imageBuff =null;
            URL img_url = new URL("http://openweathermap.org/img/wn/"+iconId+".png");
            imageBuff = ImageIO.read(img_url);
            this.image = SwingFXUtils.toFXImage(imageBuff, null);
        }

        if(respMap.get("rain") != null)
        {
            Map<String, Object > rainMap = jsonToMap (respMap.get("rain").toString());
            this.rain.set(rainMap.get("rain.1h").toString());

        }

        String time = new String();
        Date todayDate = new Date();
        DateFormat f = new SimpleDateFormat("EEEE");
        time += f.format(todayDate);
        time += " ";
        f = new SimpleDateFormat("hh:mm a");
        time += f.format(todayDate);
        this.setTime(time);

        return true;
    }
}
