package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControlleurCryptocurrency {
    @FXML
    public void layout_wallet() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("wallet.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Wallet");
        stage.setScene(new Scene(root, 900, 600));
    }


    @FXML
    public void layout_transaction() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("transactions.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Transactions");
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    public void layout_accueil() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueil.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Accueil");
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    public void layout_stock() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("action.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Stock");
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    public void layout_help() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("help.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Help");
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    public void layout_account() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Account");
        stage.setScene(new Scene(root, 900, 600));
    }
}
