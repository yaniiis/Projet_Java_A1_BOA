package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Investor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

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


    public void initialize() {
        pane_password.setVisible(false);
    }

    public void enter_app(String name, String surname, String email, String phone_number, int id) throws IOException {

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
    protected void verif_identifiants() throws IOException {
        String email = txt_email.getText();
        String mdp = txt_mdp.getText();

        String query = "SELECT id_investor, name, surnme, phone_number FROM investor WHERE email = ? AND mdp = ?";
        String url = "jdbc:mysql://localhost:3306/boa_database?serverTimezone=UTC&useSSL=false";

        try (
                Connection connection = DriverManager.getConnection(url, "root", "equipe_BOA3");
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, mdp);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surnme");
                    String phone_number = resultSet.getString("phone_number");
                    int id = resultSet.getInt("id_investor");

                    if (name != null && !name.isEmpty()) {
                        enter_app(name, surname, email, phone_number, id);
                    } else {
                        System.out.println("Aucun utilisateur trouvé avec ces identifiants.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void layout_inscription() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inscription.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();

        stage.setTitle("Inscription");
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    protected void display_fields_password() {
        pane_password.setVisible(true);
    }

    @FXML
    public void sendMail(){
        System.out.println("Erreur envoi mail !");
        pane_password.setVisible(false);
    }



}