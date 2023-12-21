package com.example.wallet_boa;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("connexion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();
        connect_db();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void connect_db() throws ClassNotFoundException {

//        Class.forName("com.mysql.cj.jdbc.Driver");

//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/boa_database", "root", "equipe_BOA3");

    }
}