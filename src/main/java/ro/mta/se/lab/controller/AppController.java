package ro.mta.se.lab.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.Country;

import javafx.beans.value.ChangeListener;
import java.io.FileWriter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class is the controller of the application
 *
 * @author Vlijia Stefan
 */

public class AppController implements Initializable {

    /** List of Country objects*/
    private ObservableList<Country> countryList = FXCollections.observableArrayList();
    /** List of countries name to be displayed */
    private ObservableList<String> countryNameList = FXCollections.observableArrayList();
    /** List of City objects*/
    private ObservableList<City> cityList = FXCollections.observableArrayList();
    /** List of cities name to be displayed */
    private ObservableList<String> cityNameList = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> contryComboBox;

    @FXML
    private ComboBox<String> cityComboBox;

    @FXML
    private Label cityNameLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label tempLabel;

    @FXML
    private Label pressureLabel;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label windLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private TableView<Country> studentTable;

    /**
     * AppController constructor used for initializing the list of Country objects
     *
     * @param countryList is the city id
     */

    public AppController(ObservableList<Country> countryList) {
        this.countryList = countryList;
    }

    /**
     * Function responsible for initializing the graphical interface
     *
     * @param location the location of view class corresponding to this controller
     * @param resources contain locale-specific objects
     */

    public void initialize(URL location, ResourceBundle resources)
    {
        initializeCountryComboBox();
        initializeCityComboBox();
        try {
            updateWeatherInformation(countryList.get(0).getCityList().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function responsible for building the list of
     * country names based on the list of Country objects
     */

    private void getCountriyNameList()
    {
        for(int i =0; i<countryList.size(); i++)
        {
            countryNameList.add(countryList.get(i).getCountryName());
        }
    }

    /**
     * Function responsible for searching a country in list of Country objects
     * by country name
     *
     * @param countryName is the country name
     * @return Country object or null if doesn't exist
     */

    private Country searchCountryByName(String countryName)
    {
        for(int i = 0; i<countryList.size(); i++)
        {
            if(countryList.get(i).getCountryName().equals(countryName))
            {
                return countryList.get(i);
            }
        }

        return null;
    }

    /**
     * Function responsible for linking the country combo box
     * to list of country names
     */

    private void initializeCountryComboBox()
    {
        getCountriyNameList();
        contryComboBox.setItems(countryNameList);

        contryComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateCityComboBox(searchCountryByName(newValue).getCityList());
            }
        });
    }

    /**
     * Function responsible for linking the city combo box
     * to list of city names
     */

    private void initializeCityComboBox()
    {
        this.cityList = FXCollections.observableList(countryList.get(0).getCityList());
        this.cityNameList.clear();
        getCityNameList();

        cityComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String  newValue) {
                try {
                    updateWeatherInformation(searchCityByName(newValue));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Function responsible for building the list of
     * city names based on the list of City objects
     */

    private void getCityNameList()
    {
        for(int i =0; i<cityList.size(); i++)
        {
            cityNameList.add(cityList.get(i).getCityName());
        }
    }

    /**
     * Function responsible for searching a city in list of City objects
     * by city name
     *
     * @param cityName is the city name
     * @return City object or null if doesn't exist
     */

    private City searchCityByName(String cityName)
    {
        for(int i = 0; i<cityList.size(); i++)
        {
            if(cityList.get(i).getCityName().equals(cityName))
            {
                return cityList.get(i);
            }
        }

        return null;
    }

    /**
     * Function responsible for update the city combo box
     * based the current list of City object
     */

    private void updateCityComboBox(List<City> cityList)
    {
        this.cityList = FXCollections.observableList(cityList);
        this.cityNameList.clear();
        getCityNameList();

        cityComboBox.setItems(this.cityNameList);
    }

    /**
     * Function responsible for update the weather information
     * from interface
     */

    private void updateWeatherInformation(City city) throws Exception {

        city.setWeatherApiKey("9caee444bc2df925cb6eba10841e9f28");
        city.updateWeather();
        writeInHistoryFile(city);

        cityNameLabel.setText(city.getCityName());
        timeLabel.setText(city.getTime());
        descriptionLabel.setText(city.getDescription());

        pressureLabel.setText(city.getPressure());
        humidityLabel.setText(city.getHumidity());
        windLabel.setText(city.getWind());

        tempLabel.setText(city.getTemp());
        imageView.setImage(city.getImage());

    }

    /**
     * Function responsible for writing in history file
     * the current information displayed in interface
     */

    private void writeInHistoryFile(City city) throws Exception {
        String strToWrite = new String();

        strToWrite += city.getCityName();
        strToWrite += ": ";
        strToWrite += city.getDescription();
        strToWrite += ", ";
        strToWrite += city.getTime();
        strToWrite += ", Temperature = ";
        strToWrite += city.getTemp();
        strToWrite += " C, Pressure = ";
        strToWrite += city.getPressure();
        strToWrite += ", Humidity = ";
        strToWrite += city.getHumidity();
        strToWrite += ", Wind = ";
        strToWrite += city.getWind();
        strToWrite += "\n";

        FileWriter myWriter = new FileWriter("src/main/resources/displayHistory.txt", true);
        myWriter.write(strToWrite);
        myWriter.close();
    }

}
