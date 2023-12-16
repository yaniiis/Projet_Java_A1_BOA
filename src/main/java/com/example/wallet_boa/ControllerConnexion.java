package com.example.wallet_boa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    public void initialize() {
        label_forget.setVisible(false);
        field_forget.setVisible(false);
        btn_forget.setVisible(false);
    }

    @FXML
    protected void layout_inscription() throws Exception {
        System.out.println("aa");
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
        label_forget.setVisible(true);
        field_forget.setVisible(true);
        btn_forget.setVisible(true);
        btn_connexion.setVisible(false);
    }
}