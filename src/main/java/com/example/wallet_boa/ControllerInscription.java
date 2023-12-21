package com.example.wallet_boa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
        Stage stage = new Stage();
        stage.setTitle("Connexion");
        stage.setScene(new Scene(root, 900, 600));

        Stage stage_ = (Stage) btn_back.getScene().getWindow();
        stage_.close();

        stage.show();
    }

    @FXML
    public void insert_inscription(){
        String email = i_email.getText();
        String mdp = i_mdp.getText();
        String phone = i_phone.getText();
        String surname = i_surname.getText();
        String name = i_name.getText();

        String query = "INSERT INTO utilisateurs (email, mot_de_passe, telephone, nom, prenom) VALUES (?, ?, ?, ?, ?)";

        /*
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, mdp);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, surname);
            preparedStatement.setString(5, name);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Insertion réussie.");
                // Ajoutez ici le code pour gérer la suite de votre application après l'insertion réussie.
            } else {
                System.out.println("L'insertion a échoué.");
                // Ajoutez ici le code pour gérer le cas où l'insertion échoue.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

         */

    }
}