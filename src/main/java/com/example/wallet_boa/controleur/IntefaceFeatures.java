package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Investor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;

public interface IntefaceFeatures {

    // identifians bdd
    String NAME_DB = "root";
    String MDP_DB = "equipe_BOA3";

    /*
        Toutes les fonctions commencant par layout_
        Permettent la redirection vers une autre page
    */

    static void layout_wallet(Investor investor) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("wallet.fxml"));
        Parent root = fxmlLoader.load();

        ControllerWallet accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Wallet");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();

    }


    static void layout_transaction(Investor investor) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("transactions.fxml"));
        Parent root = fxmlLoader.load();

        ControlleurTransactions accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Transactions");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


    static void layout_crypto(Investor investor) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("crypto.fxml"));
        Parent root = fxmlLoader.load();

        ControlleurCryptocurrency accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Cryptocurrenci");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


    static void layout_stock(Investor investor) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("action.fxml"));
        Parent root = fxmlLoader.load();

        ControlleurAction accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Stock");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


    static void layout_help(Investor investor) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("help.fxml"));
        Parent root = fxmlLoader.load();

        ControlleurHelp accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Help");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


    static void layout_account(Investor investor) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account.fxml"));
        Parent root = fxmlLoader.load();

        ControlleurAccount accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Account");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


    static void layout_accueil(Investor investor) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account.fxml"));
        Parent root = fxmlLoader.load();

        ControlleurAccount accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Account");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }
    @FXML
    static void layout_inscription() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inscription.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();

        stage.setTitle("Inscription");
        stage.setScene(new Scene(root, 900, 600));
    }
    @FXML
    static void layout_connexion() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("connexion.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();

        stage.setTitle("Connexion");
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    static void layout_presentation() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("presentation.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();

        stage.setTitle("Presentation");
        stage.setScene(new Scene(root, 900, 600));
    }
    static String encryptPassword(String password) throws Exception {

        /*
            Cette fonction permet de crypter un mot de passe sur 50 caractères avec le module de cryptage
            fourni par Intellij
        */
        String key = "@213_DRX281SKAL!";
        byte[] keyValue = key.getBytes();
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(128);
        SecretKey secretKey = new SecretKeySpec(keyValue, "AES");

        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encVal = c.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encVal);
    }


    static void log_out() throws IOException {
        /*
        Cette fonction permet la deconnexion
         */
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("connexion.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Connexion");
        stage.setScene(new Scene(root, 900, 600));
    }


}
