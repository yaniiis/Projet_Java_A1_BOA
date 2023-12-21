package com.example.wallet_boa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    @FXML
    protected void verif_identifiants(){
        String email = txt_email.getText();
        String mdp = txt_mdp.getText();

        String query = "SELECT COUNT(*) FROM utilisateurs WHERE email = ? AND mdp = ?";

        /*
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, mdp);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int nombreUtilisateurs = resultSet.getInt(1);
                    boolean identifiantsCorrects = nombreUtilisateurs > 0;

                    if (identifiantsCorrects) {
                        System.out.println("Présent !");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

         */
    }


    @FXML
    protected void layout_inscription() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inscription.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Inscription");
        stage.setScene(new Scene(root, 900, 600));

        Stage stage_ = (Stage) btn_layout_inscription.getScene().getWindow();
        stage_.close();

        stage.show();
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