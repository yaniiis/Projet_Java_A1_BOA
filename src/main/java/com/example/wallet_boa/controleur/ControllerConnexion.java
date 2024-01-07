package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Investor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;




public class ControllerConnexion {

    @FXML
    private Button btn_layout_inscription;
    @FXML
    private TextField field_forget;
    @FXML
    private Label label_forget;
    @FXML
    private Button btn_connexion;
    @FXML
    private Button btn_forget;
    @FXML
    private TextField txt_mdp;
    @FXML
    private TextField txt_email;
    @FXML
    private Pane pane_password;

    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */



    public void layout_accueil(String name, String surname, String email, String phone_number, int id) throws IOException {

        Investor investor = new Investor(name, surname, email, phone_number, id);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueil.fxml"));
        Parent root = fxmlLoader.load();

        ControllerAccueil accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Accueil");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


    @FXML
    protected void verif_identifiants() throws Exception {
        /*
            Cette fonction permet de vérifier les identifiants les entrées par l'utilisateur
            Avec une requete dans la base de données
         */

        String email = txt_email.getText();
        String mdp = txt_mdp.getText();

        if(mdp.equals("") || email.equals("") ){
            System.out.println("Veuillez remplir les champs");
        }else{
            mdp = IntefaceFeatures.encryptPassword(mdp);

            String query = "SELECT id_investor, name, surname, phone_number FROM investor WHERE email = ? AND mdp = ?";
            String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";
            try (
                    Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
            ) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, mdp);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {

                        String name = resultSet.getString("name");
                        String surname = resultSet.getString("surname");
                        String phone_number = resultSet.getString("phone_number");

                        int id = resultSet.getInt("id_investor");
                        if (name != null && !name.isEmpty()) {
                            layout_accueil(name, surname, email, phone_number, id);
                        } else {
                            System.out.println("Aucun utilisateur trouvé avec ces identifiants.");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    @FXML
    protected void l_inscription() throws Exception {
        IntefaceFeatures.layout_inscription();
    }

    @FXML
    protected void l_presentation() throws Exception {
        IntefaceFeatures.layout_presentation();
    }


    @FXML
    protected void display_fields_password() {

        // afficher le Pane pour le mot de passe oublié

        pane_password.setVisible(true);
    }

    @FXML
    public void sendMail(){

        // Envoi d'un email in progress

        System.out.println("Erreur envoi mail !");
        pane_password.setVisible(false);
    }



}