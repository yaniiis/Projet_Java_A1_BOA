package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Blockchaine;
import com.example.wallet_boa.modele.Investor;
import com.example.wallet_boa.modele.Wallet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface IntefaceFeatures {

    // identifians bdd
    String NAME_DB = "root";
    String MDP_DB = "equipe_BOA3";

    /*
        Toutes les fonctions commencant par layout_
        Permettent la redirection vers une autre page
    */

    static void layout_wallet(Investor investor, Blockchaine blockchaine) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("wallet.fxml"));
        Parent root = fxmlLoader.load();

        ControllerWallet accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor,blockchaine);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Wallet");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();

    }


    static void layout_transaction(Investor investor, Blockchaine blockchaine) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("transactions.fxml"));
        Parent root = fxmlLoader.load();

        ControlleurTransactions accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor, blockchaine);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Transactions");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }



    static void layout_crypto(Investor investor, Blockchaine blockchaine) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("crypto.fxml"));
        Parent root = fxmlLoader.load();

        ControlleurCryptocurrency accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor, blockchaine);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Cryptocurrenci");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


    static void layout_stock(Investor investor, Blockchaine blockchaine) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("action.fxml"));
        Parent root = fxmlLoader.load();

        ControlleurAction accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor,blockchaine);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Stock");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


    static void layout_help(Investor investor, Blockchaine blockchaine) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("help.fxml"));
        Parent root = fxmlLoader.load();

        ControlleurHelp accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor,blockchaine);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Help");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


    static void layout_account(Investor investor, Blockchaine blockchaine) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account.fxml"));
        Parent root = fxmlLoader.load();

        ControlleurAccount accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor, blockchaine);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Account");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


    static String formatPrice(String price) {
        try {
            double priceValue = Double.parseDouble(price);
            return String.format("%.2f", priceValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    static double compter_montant (Investor investor){
        double total = 0;
        for (Wallet wallet : investor.getList_wallet()) {
            total += wallet.getAmount();
        }
        return total;

    }

    static void layout_accueil(Investor investor, Blockchaine blockchaine) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueil.fxml"));
        Parent root = fxmlLoader.load();

        ControllerAccueil accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor, blockchaine);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Accueil");
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

    static boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



    static boolean isValidPhone(String phone) {

        if(phone.length()!=10){
            return false;
        }

        String PHONE_REGEX = "^\\+?\\d{10,15}$";
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    static boolean isValidPassword(String password) {
        if (password.length()<7 ) {
            return false;
        }

        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    static boolean isEmailUnique(String email) {
        String query = "SELECT COUNT(*) AS nb FROM investor WHERE email = ?";
        String url = "jdbc:mysql://localhost:3306/boa_database?serverTimezone=UTC&useSSL=false";

        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("nb") == 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    static int random_id() {

        Random random = new Random();
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < 9; i++) {
            int randomDigit = random.nextInt(10);
            randomString.append(randomDigit);
        }

        String randomIDStr = randomString.toString();
        int randomID = Integer.parseInt(randomIDStr);
        return randomID;
    }

    static double cut_nombre(double nombre) {
        double valeur_dollar = nombre;

        valeur_dollar = Math.round(valeur_dollar * 100.0) / 100.0;
        return valeur_dollar;

    }

}
