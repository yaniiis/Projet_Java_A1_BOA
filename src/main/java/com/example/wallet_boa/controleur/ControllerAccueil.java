package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Investor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerAccueil {

    private Investor investor;

    @FXML
    Label label_name;
    @FXML
    Button btn_wallet;
    @FXML
    Button btn_transaction;
    @FXML
    Button btn_action;
    @FXML
    Button btn_account;
    @FXML
    Button btn_cryptocurrency;


    public void setInvestor(Investor investor) {
        this.investor = investor;
        setLabel();
    }


    public void setLabel() {
        label_name.setText(investor.getName());
    }

    @FXML
    public void log_out() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("connexion.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Connexion");
        stage.setScene(new Scene(root, 900, 600));
    }

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
    public void layout_crypto() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("crypto.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Cryptocurrency");
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
