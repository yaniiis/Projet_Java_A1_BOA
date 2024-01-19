package com.example.wallet_boa.controleur;


import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
    @FXML
    ImageView imageView;
    @FXML
    Label label_erreur;
    @FXML
    Label label_erreur_email;

    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */

    public void initialize() {
        /*
        Cette fonction permet de charger une video automatiquement au chargement de la page
         */

        Image image = new Image(new File("src/main/resources/galerie/logo.png").toURI().toString());
        imageView.setImage(image);

    }

    public void layout_accueil(String name, String surname, String email, String phone_number, int id) throws Exception {

        ArrayList<Wallet> list_wallet = charger_wallet(id);

        Investor investor = new Investor(name, surname, email, phone_number, id, list_wallet);


        List<Block> blockchain = chargerBlockchaine(investor);
        Blockchaine blockchaine = new Blockchaine(blockchain);


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueil.fxml"));
        Parent root = fxmlLoader.load();

        ControllerAccueil accueilController = fxmlLoader.getController();

        accueilController.setInvestor(investor, blockchaine);

        Stage stage = HelloApplication.getPrimaryStage();
        stage.setTitle("Accueil");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }

    public List<Block> chargerBlockchaine(Investor investor) throws Exception {

        List<Block> blockchaine = new ArrayList<>();

        String query = "SELECT * FROM blocks;";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";
        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                int index = resultSet.getInt("indice");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                String previousHash = resultSet.getString("previousHash");
                String hash = resultSet.getString("hash");

                Set<Transaction> list_transaction = recuper_transaction_block(index, investor);


                Block block = new Block(index, timestamp, list_transaction, previousHash, hash);
                blockchaine.add(block);
            }

        }

        return blockchaine;
    }

    public Set<Transaction> recuper_transaction_block(int index, Investor investor) throws Exception {

        Set<Transaction> list_transaction = new HashSet<>();

        String query = "SELECT * FROM transactions where indice = ?;";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";
        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, index); // Set the value of the parameter in the query
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                String transactionId = resultSet.getString("transactionId");
                int originWalletId = resultSet.getInt("originWalletId");
                String value = resultSet.getString("value");
                double amount = resultSet.getDouble("amount");

                ArrayList<Wallet> list_wallet = investor.getList_wallet();

                Wallet wallet_select = null;

                for (Wallet wallet : list_wallet) {
                    if (wallet.getId_wallet() == originWalletId) {
                        wallet_select = wallet;
                        break; // Exit the loop once the wallet is found
                    }
                }

                if (wallet_select != null) {
                    Transaction transaction = new Transaction(transactionId, wallet_select, value, amount);
                    list_transaction.add(transaction);
                }
            }
        }

        return list_transaction;
    }



    public ArrayList<Wallet> charger_wallet(int id){

        ArrayList <Wallet> list_wallets = new ArrayList<>();

        String query = "SELECT id_wallet, name, date, description, id_list_valeur, amount, clone, id_list_action FROM wallet WHERE id_investor = ? ;";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";
        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");

                    if (name != null && !name.isEmpty()) {

                        Date date = resultSet.getDate("date");
                        int id_wallet = resultSet.getInt("id_wallet");
                        int id_list_value = resultSet.getInt("id_list_valeur");
                        int id_list_action = resultSet.getInt("id_list_action");

                        String description = resultSet.getString("description");

                        int amount = resultSet.getInt("amount");
                        boolean clone = resultSet.getBoolean("clone");



                        Cryptocurrency cryptocurrency = charger_value(id_list_value);
                        Stock stock = charger_action(id_list_action);
                        Wallet new_wallet = new Wallet(id_wallet, name, date, description, amount, clone, cryptocurrency, stock);
                        list_wallets.add(new_wallet);

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list_wallets;

    }

    public Stock charger_action(int id_list_action){
        Stock stock = new Stock();
        String query = "SELECT id_list_valeur, AMSZN, GOOGL, AAPL, MSFT FROM actions WHERE id_list_valeur = ? ;";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";
        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, id_list_action);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int AMSZN = resultSet.getInt("AMSZN");
                    int AAPL = resultSet.getInt("AAPL");
                    int GOOGL = resultSet.getInt("GOOGL");
                    int MSFT = resultSet.getInt("MSFT");
                    int id_list_valeur = resultSet.getInt("id_list_valeur");

                    stock = new Stock(id_list_valeur, AMSZN,AAPL,MSFT,GOOGL);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }

    public Cryptocurrency charger_value(int id_list_value){

        Cryptocurrency cryptocurrency = new Cryptocurrency();
        String query = "SELECT id_list_valeur, BTC, ETH, BNB, ADA, SOL, XRP, DOT, DOGE, AVAX, LINK FROM list_value WHERE id_list_valeur = ? ;";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";
        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, id_list_value);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int btc = resultSet.getInt("BTC");
                    int eth = resultSet.getInt("ETH");
                    int bnb = resultSet.getInt("BNB");
                    int ada = resultSet.getInt("ADA");
                    int sol = resultSet.getInt("SOL");
                    int xrp = resultSet.getInt("XRP");
                    int dot = resultSet.getInt("DOT");
                    int doge = resultSet.getInt("DOGE");
                    int avax = resultSet.getInt("AVAX");
                    int link = resultSet.getInt("LINK");
                    int id = resultSet.getInt("id_list_valeur");

                    cryptocurrency = new Cryptocurrency(id, btc,eth,bnb,ada, sol,xrp, doge, dot, avax, link);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cryptocurrency;

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentification");
            alert.setHeaderText(null);
            alert.setContentText("Fields empthy");
            alert.showAndWait();
        }else{
            mdp = IntefaceFeatures.encryptPassword(mdp);

            String query = "SELECT id_investor, name, surname, phone_number FROM investor WHERE email = ? AND mdp = ?";
            String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
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
                        }
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Authentification");
                        alert.setHeaderText(null);
                        alert.setContentText("Error password or email");
                        alert.showAndWait();
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
        label_erreur.setText("");
        pane_password.setVisible(true);
    }

    @FXML
    public void sendMail() throws Exception{

        label_erreur.setText("");

        String toEmail = field_forget.getText();
        if(!IntefaceFeatures.isValidEmail(toEmail)){
            label_erreur_email.setText("Format email incorrecte !");
        }else {
            List<String> infor_user = trouver_email(toEmail);
            if (infor_user.get(0) == "") {
                label_erreur_email.setText("Aucun email correspondant !");
            } else {

                String mdpp = IntefaceFeatures.decryptPassword(infor_user.get(1));

                final String username = "boa75000@outlook.com";
                final String password = "boa12345";

                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp-mail.outlook.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

                try {

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                    message.setSubject("Subject: Bienvenue sur notre plateforme de crypto-monnaies et d'actions !");
                    message.setText("Cher(e)" + infor_user.get(0) + ",\n\n"

                            + "\n\n"
                            + "Vous avez demandé la réinitialisation de votre mot de passe pour notre plateforme.\n\n"
                            + "Votre mot de passe : " + mdpp + "\n\n"
                            + "Pensez à créer un nouveau mot de passe.\n\n"
                            + "\n\n"
                            + "Revolutionize your investment strategy ! \n\n"
                            + "BOA Team.");

                    Transport.send(message);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("E-mail envoyé");
                    alert.setHeaderText(null);
                    alert.setContentText("L'e-mail a été envoyé avec succès !");
                    alert.showAndWait();

            } catch(MessagingException e){
                e.printStackTrace();
            }
            pane_password.setVisible(false);
            }
        }

    }

    public List<String> trouver_email(String email){

        List<String> l = new ArrayList<>();


        String query = "SELECT name, mdp FROM investor WHERE email = ? ;";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    String name = resultSet.getString("name");
                    String mdp = resultSet.getString("mdp");
                    l.add(name);
                    l.add(mdp);

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        l.add("");
        l.add("");
        return l;
    }



}