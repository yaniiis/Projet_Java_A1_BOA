package com.example.wallet_boa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("connexion.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Votre Application");
        primaryStage.setScene(new Scene(root, 800, 600));
        connect_db();
        primaryStage.show();
    }


    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Connection getDB() {
        return connection;
    }


    public static void connect_db() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/boa_database?serverTimezone=UTC&useSSL=false";
        connection = DriverManager.getConnection(url, "root", "equipe_BOA3");


    }


}