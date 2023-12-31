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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;


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

    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */


    @FXML
    public void l_connection() throws Exception {
        IntefaceFeatures.layout_connexion();
    }

    @FXML
    public void insert_inscription () throws Exception {
        /*
            Cette fonction permet de d'insérer un nouvelle utilisateur dans la base de données
         */

        String email = i_email.getText();

        String phone = i_phone.getText();
        String surname = i_surname.getText();
        String name = i_name.getText();

        String mdp = IntefaceFeatures.encryptPassword(i_mdp.getText());

        String query = "INSERT INTO investor (name, surname, email, mdp, phone_number) VALUES (?, ?, ?, ?, ?)";
        String url = "jdbc:mysql://localhost:3306/boa_database?serverTimezone=UTC&useSSL=false";

        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, mdp);
            preparedStatement.setString(5, phone);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                IntefaceFeatures.layout_connexion();
            } else {
                System.out.println("L'insertion a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
