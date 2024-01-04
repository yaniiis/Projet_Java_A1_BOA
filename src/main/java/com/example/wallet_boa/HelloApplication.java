package com.example.wallet_boa;

import com.binance.connector.client.utils.JSONParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;


import java.sql.Connection;


public class HelloApplication extends Application {

    private static Stage primaryStage;

    private static Connection connection;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        HelloApplication.primaryStage = primaryStage;

//        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("connexion.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Votre Application");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();


        URL url = new URL("https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT");
        URLConnection y = url.openConnection();
        System.out.println("Connection ok");

        BufferedReader i = new BufferedReader(new InputStreamReader(y.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        // Lire chaque ligne de la réponse et les accumuler dans un StringBuilder
        while ((inputLine = i.readLine()) != null) {
            response.append(inputLine);
        }
        i.close();

        JSONObject json = new JSONObject(response.toString());

        String price = json.getString("price");
        System.out.println("Price: " + price);

    }


    public static Stage getPrimaryStage() {
        return primaryStage;
    }

}