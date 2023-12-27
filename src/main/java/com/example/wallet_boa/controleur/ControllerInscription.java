package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControllerInscription {

    @FXML
    Button btn_back;
    @FXML
    TextField i_email;
    @FXML
    TextField i_phone;
    @FXML
    TextField i_name;
    @FXML
    TextField i_surname;
    @FXML
    TextField i_mdp;

    @FXML
    public void back_connect() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("connexion.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Connexion");
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    public void insert_inscription () throws IOException {
        String email = i_email.getText();
        String mdp = i_mdp.getText();
        String phone = i_phone.getText();
        String surname = i_surname.getText();
        String name = i_name.getText();

        String query = "INSERT INTO investor (name, surnme, email, mdp, phone_number) VALUES (?, ?, ?, ?, ?)";
        String url = "jdbc:mysql://localhost:3306/boa_database?serverTimezone=UTC&useSSL=false";


        try (
                Connection connection = DriverManager.getConnection(url, "root", "equipe_BOA3");
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, mdp);
            preparedStatement.setString(5, phone);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                back_connect();
            } else {
                System.out.println("L'insertion a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
