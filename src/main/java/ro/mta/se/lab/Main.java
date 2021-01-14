package ro.mta.se.lab;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mta.se.lab.controller.AppController;
import ro.mta.se.lab.model.Country;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class Main extends Application {
    private ObservableList<Country> countryList = FXCollections.observableArrayList();

    public static void main(String[] args)
    {
        launch(args);
    }

    public int search(String countryCode)
    {
        for(int i = 0; i<countryList.size(); i++)
        {
            if(countryList.get(i).getCountryCode().equals(countryCode))
            {
                return i;
            }
        }
        return -1;
    }

    public void initializeCountries()
    {
        File inputFile = new File("src/main/resources/input.txt");
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String id, cityName, lat, lon, countryCode;
        while (sc2.hasNextLine())
        {
            Scanner s2 = new Scanner(sc2.nextLine());
            id = s2.next();
            cityName = s2.next();
            lat = s2.next();
            lon = s2.next();
            countryCode = s2.next();

            int index = search(countryCode);
            if(index == -1)
            {
                countryList.add(new Country(id, cityName, lat, lon, countryCode));
            }
            else
            {
                countryList.get(index).addCity(id, cityName, lat, lon);
            }
        }
    }

    public void start(Stage primaryStage) {
        initializeCountries();
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(this.getClass().getResource("/view/AppView.fxml"));
            loader.setController(new AppController(countryList));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.setTitle("WeatherApp");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}