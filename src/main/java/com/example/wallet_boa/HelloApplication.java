package com.example.wallet_boa;

import com.example.wallet_boa.controleur.IntefaceFeatures;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

    }


    public static Stage getPrimaryStage() {
        return primaryStage;
    }

}