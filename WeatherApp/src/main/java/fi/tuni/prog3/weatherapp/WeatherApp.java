package fi.tuni.prog3.weatherapp;

import java.io.IOException;
import java.nio.file.Path;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//git add 
//git stash --staged
//git pull
//git stash apply
//git add
//git commit
//git push

/**
 * JavaFX WeatherApp
 */
public class WeatherApp extends Application {
    private WeatherData weather;

    @Override
    public void start(Stage stage) throws IOException {
        // get the weather
        weatherApiImpl  weatherApi = new  weatherApiImpl();
        weatherApi.lookUpLocation("Rovaniemi");  
        ReadFile file = new ReadFile();
        file.readFromFile("weatherData");
        weather = file.getWeather();
        
        // Create the main sections
        HBox menuSection = createMenuSection();
        GridPane currentSection = createCurrentSection();
        HBox hourlySection = createHourlySection();
        HBox dailySection = createDailySection();

        // Create a VBox to hold the sections
        VBox root = new VBox(10); // 10 is the spacing between sections
        root.setPadding(new Insets(10));
        root.getChildren().addAll(menuSection, currentSection, hourlySection, dailySection);

        // Create the scene
        Scene scene = new Scene(root, 600, 750);

        // Set the stage properties
        stage.setTitle("OONAN JA REETAN WeatherApp");
        stage.setScene(scene);
        stage.show();
     
    }
      
    private HBox createMenuSection() {
        // Add elements
        Button search = getSearchButton();
        Button quit = getQuitButton();
        HBox menuSection = new HBox(10);
        menuSection.setPadding(new Insets(10));
        menuSection.getChildren().addAll(search, quit);
        
        // Set style
        menuSection.setStyle("-fx-background-color: lightgray;");
        menuSection.setPrefHeight(40);

        return menuSection;
    }

    private GridPane createCurrentSection() {
        // Add elements
        Label titleLabel = new Label("Tämän hetken sää");

        String location = weather.getCityName();
        Label locationLabel = new Label(location);
        locationLabel.setStyle("-fx-font-size: 24;");

        String temp = String.format("%.1f ℃", weather.getCurrentTemp());
        Label tempLabel = new Label(temp);
        tempLabel.setStyle("-fx-font-size: 30;");
        
        String tempFeelsLike = String.format("Feels like %.1f ℃", weather.getFeelsLike());
        Label feelsLikeLabel = new Label(tempFeelsLike);
        
        //String rain = String.format("rain: %.1f", weather.getRain());
        Label rainLabel = new Label("rain");
        
        String wind = String.format("wind: %.1f", weather.getWind());
        Label windLabel = new Label(wind);
        
        String humidity = String.format("humidity: %d", weather.getHumidity());
        Label humLabel  = new Label(humidity);

        // Create GridPane
        GridPane currentSection = new GridPane();
        currentSection.setHgap(10);
        currentSection.setVgap(10);

        // Add labels to GridPane with specific column and row
        currentSection.add(titleLabel, 0, 0, 2, 1); // Span 2 columns
        currentSection.add(locationLabel, 0, 1);
        currentSection.add(tempLabel, 1, 2);
        currentSection.add(feelsLikeLabel, 1, 3);
        currentSection.add(rainLabel, 0, 4);
        currentSection.add(windLabel, 1, 4);
        currentSection.add(humLabel, 2, 4);

        // Set style
        currentSection.setStyle("-fx-background-color: lightblue;");
        currentSection.setPrefHeight(220);
        currentSection.setAlignment(Pos.CENTER);

        return currentSection;
    }

    private HBox createHourlySection() {
        // Add elements
        Label footerLabel = new Label("tunti ennuste");
        Label bottomLabel = new Label("Content for the section");
        HBox hourlySection = new HBox(10);
        hourlySection.setPadding(new Insets(10));
        hourlySection.getChildren().addAll(footerLabel, bottomLabel);

        // Set style
        hourlySection.setStyle("-fx-background-color: lightgray;");
        hourlySection.setPrefHeight(220);

        return hourlySection;
    }
    
       private HBox createDailySection() {
        // Add elements
        Label titleLabel = new Label("Lähipäivien ennuste");
        Label topLabel = new Label("Content for thesection");
        HBox dailySection = new HBox(10);
        dailySection.setPadding(new Insets(10));
        dailySection.getChildren().addAll(titleLabel, topLabel);

        // Set style
        dailySection.setStyle("-fx-background-color: lightblue;");
        dailySection.setPrefHeight(220);

        return dailySection;
    }
    
    private Button getQuitButton() {
        //Creating a button.
        Button quitButton = new Button("Quit");
        
        //Adding an event to the button to terminate the application.
        quitButton.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        
        return quitButton;
    }
    
    private Button getSearchButton() {
        // Create a MenuBar
        Button searchButton = new Button("Search and Favorites");
        
        searchButton.setOnAction(e -> openSearchWindow());
        
        return  searchButton;
    }
    
    private void openSearchWindow() {
        
    }
    
    public static void main(String[] args) {
        launch();
    }
}