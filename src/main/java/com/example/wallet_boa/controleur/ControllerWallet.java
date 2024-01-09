package com.example.wallet_boa.controleur;

import com.example.wallet_boa.modele.Cryptocurrency;
import com.example.wallet_boa.modele.Investor;
import com.example.wallet_boa.modele.Wallet;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ControllerWallet {

    private Investor investor;
    private int nb_wallet = 0;

    @FXML
    Label label_name;
    @FXML
    VBox vbox_wallet;
    @FXML
    VBox vbox_new_wallet;
    @FXML
    VBox vbox_clone;
    @FXML
    HBox hbox_crypto;
    @FXML
    TextField txt_wallet;
    @FXML
    TextArea txt_description;
    @FXML
    Pane pane_1;
    @FXML
    Pane pane_2;
    @FXML
    Pane pane_3;
    @FXML
    Pane pane_4;
    @FXML
    Pane pane_5;
    @FXML
    Pane pane_6;
    @FXML
    Pane pane_7;
    @FXML
    Pane pane_8;
    @FXML
    Pane pane_9;
    @FXML
    Label label_1;
    @FXML
    Label label_2;
    @FXML
    Label label_3;
    @FXML
    Label label_4;
    @FXML
    Label label_5;
    @FXML
    Label label_6;
    @FXML
    Label label_7;
    @FXML
    Label label_8;
    @FXML
    Label label_9;
    @FXML
    Label amount_1;
    @FXML
    Label amount_2;
    @FXML
    Label amount_3;
    @FXML
    Label amount_4;
    @FXML
    Label amount_5;
    @FXML
    Label amount_6;
    @FXML
    Label amount_7;
    @FXML
    Label amount_8;
    @FXML
    Label amount_9;
    @FXML
    TextField txt_wallet_clone;
    @FXML
    ComboBox<String> cb_wallet_clone;



    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */

    public void ajoutWallet(Investor _investor){
        ArrayList<Wallet> wallets= this.investor.getList_wallet();
        int i =1;
        for(Wallet wallet : wallets) {
            create_walet(wallet.getName(), wallet.getAmount(), i, wallet.getClone());
            cb_wallet_clone.getItems().add(
                    String.valueOf(wallet.getName())
            );
            i++;
        }

        this.nb_wallet = i;
    }


    public void create_walet(String name, double amount, int i, boolean clone){

        switch (i){
            case 1 :
                if(clone==true){
                    pane_1.setStyle("-fx-background-color: red; -fx-background-radius: 10;");
                }else{
                    pane_1.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                }
                label_1.setText(name);
                amount_1.setText(String.valueOf(amount));
                break;

            case 2 :
                if(clone==true){
                    pane_2.setStyle("-fx-background-color: red; -fx-background-radius: 10;");
                }else{
                    pane_2.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                }
                label_2.setText(name);
                amount_2.setText(String.valueOf(amount));
                break;
            case 3 :
                if(clone==true){
                    pane_3.setStyle("-fx-background-color: red; -fx-background-radius: 10;");
                }else{
                    pane_3.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                }
                label_3.setText(name);
                amount_3.setText(String.valueOf(amount));
                break;
            case 4 :
                if(clone==true){
                    pane_4.setStyle("-fx-background-color: red; -fx-background-radius: 10;");
                }else{
                    pane_4.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                }
                label_4.setText(name);
                amount_4.setText(String.valueOf(amount));
                break;
            case 5 :
                if(clone==true){
                    pane_5.setStyle("-fx-background-color: red; -fx-background-radius: 10;");
                }else{
                    pane_5.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                }
                label_5.setText(name);
                amount_5.setText(String.valueOf(amount));
                break;
            case 6 :
                if(clone==true){
                    pane_6.setStyle("-fx-background-color: red; -fx-background-radius: 10;");
                }else{
                    pane_6.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                }
                label_6.setText(name);
                amount_6.setText(String.valueOf(amount));
                break;
            case 7 :
                if(clone==true){
                    pane_7.setStyle("-fx-background-color: red; -fx-background-radius: 10;");
                }else{
                    pane_7.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                }
                label_7.setText(name);
                amount_7.setText(String.valueOf(amount));
                break;
            case 8 :
                if(clone==true){
                    pane_8.setStyle("-fx-background-color: red; -fx-background-radius: 10;");
                }else{
                    pane_8.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                }
                label_8.setText(name);
                amount_8.setText(String.valueOf(amount));
                break;
            case 9 :
                if(clone==true){
                    pane_9.setStyle("-fx-background-color: red; -fx-background-radius: 10;");
                }else{
                    pane_9.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                }
                label_9.setText(name);
                amount_9.setText(String.valueOf(amount));
                break;

        }
    }

    public void l_logout() throws IOException {
        IntefaceFeatures.log_out();
    }

    public void l_help() throws Exception{
        IntefaceFeatures.layout_help(investor);
    }
    public void l_action() throws Exception{
        IntefaceFeatures.layout_stock(investor);
    }
    public void l_transaction() throws Exception{
        IntefaceFeatures.layout_transaction(investor);
    }
    public void l_crytpo() throws Exception{
        IntefaceFeatures.layout_crypto(investor);
    }
    public void l_account() throws Exception{
        IntefaceFeatures.layout_account(investor);
    }
    public void l_accueil() throws Exception{
        IntefaceFeatures.layout_accueil(investor);
    }
    public void setInvestor(Investor investor) {
        /*
            Affection d'un objet Investor
         */
        this.investor = investor;
        label_name.setText(investor.getName());
        ajoutWallet(investor);
    }

    @FXML
    public void layout_new_wallet(){
        if(this.nb_wallet>9){
            System.out.println("Le nombre de wallet maximum est atteint");
        }else{
            vbox_clone.setVisible(false);
            vbox_wallet.setVisible(false);
            vbox_new_wallet.setVisible(true);
            hbox_crypto.setVisible(false);
        }
    }
    @FXML
    public void layout_clone_wallet(){
        if(this.nb_wallet>9){
            System.out.println("Le nombre de wallet maximum est atteint");
        }else{
            vbox_clone.setVisible(true);
            vbox_wallet.setVisible(false);
            vbox_new_wallet.setVisible(false);
            hbox_crypto.setVisible(false);
        }

    }
    @FXML
    public void back_wallet(){
        vbox_clone.setVisible(false);
        vbox_wallet.setVisible(true);
        vbox_new_wallet.setVisible(false);
        hbox_crypto.setVisible(true);
    }

    @FXML
    public void insert_wallet() throws Exception {
        String wallet_name = txt_wallet.getText();
        String description_wallet = txt_description.getText();
        java.util.Date dateActuelle = new java.util.Date();

        Date dateSQL = new Date(dateActuelle.getTime());

        Cryptocurrency cryptocurrency = new Cryptocurrency();

        Wallet wallet = new Wallet(0,wallet_name, dateSQL, description_wallet, 0, false, cryptocurrency );

        insert_wallet_bdd(wallet,0);
    }


    public void insert_wallet_clone() throws Exception {
        /*
            Cette fonction permet de récupérer les valeurs du wallet a cloner
         */

        String name = txt_wallet_clone.getText();
        String selected = cb_wallet_clone.getSelectionModel().getSelectedItem();

        String query = "SELECT description, id_list_valeur, id_wallet,amount FROM wallet WHERE id_investor = ? and name = ?; ";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";
        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, this.investor.getId());
            preparedStatement.setString(2, selected);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {


                    String description = resultSet.getString("description");
                    int amount = resultSet.getInt("amount");
                    int id_list_valeur = resultSet.getInt("id_list_valeur");

                    System.out.println(id_list_valeur);

                    java.util.Date dateActuelle = new java.util.Date();
                    Date dateSQL = new Date(dateActuelle.getTime());

                    Cryptocurrency cryptocurrency = list_value_clone(id_list_valeur);

                    Wallet wallet = new Wallet(0,name, dateSQL, description, amount, true,cryptocurrency);


                    insert_wallet_bdd(wallet, id_list_valeur);
                    System.out.println(cryptocurrency.getId_crypto());

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Cryptocurrency list_value_clone(int id_list_valeur){
        /*
            Cette fonction permet de récupérer les valeurs cryptomonnaie d'un wallet
         */
        String query = "SELECT BTC, ETH, BNB, ADA, SOL, XRP, DOT, DOGE, AVAX, LINK FROM list_value WHERE id_list_valeur = ? ;";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";
        Cryptocurrency crypto = new Cryptocurrency();
        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, id_list_valeur);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = IntefaceFeatures.random_id();
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



                    crypto = new Cryptocurrency(id,btc,eth,bnb,ada, sol,xrp, doge, dot, avax, link);
                    return crypto;

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crypto;

    }


    public void insert_wallet_bdd(Wallet wallet, int id_list_valeur_) throws Exception {
        /*
            Cette fonction permet de créer un wallet
         */

        try {
            String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";

            Connection connexion = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);

            String requeteSQL = "INSERT INTO wallet (id_wallet ,name, description, amount, date, id_investor, clone, id_list_valeur) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL);
            int id = IntefaceFeatures.random_id();
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, wallet.getName());
            preparedStatement.setString(3, wallet.getDescription());
            preparedStatement.setDouble(4, wallet.getAmount());
            preparedStatement.setDate(5, wallet.getDate());
            preparedStatement.setInt(6, this.investor.getId());
            preparedStatement.setBoolean(7, wallet.getClone());

            if(wallet.getClone()==false){
                int id_list_value = IntefaceFeatures.random_id();
                String sql = "INSERT INTO list_value (id_list_valeur, BTC, ETH, BNB, ADA, SOL, XRP, DOT, DOGE, AVAX, LINK) VALUES (?, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)";
                PreparedStatement pstmt = connexion.prepareStatement(sql);
                pstmt.setInt(1, id_list_value);
                pstmt.executeUpdate();
                preparedStatement.setInt(8, id_list_value);

                Cryptocurrency new_values = new Cryptocurrency(0,0,0,0,0,0,0,0,0,0);
                wallet.setList_value(new_values);


            }else{
                preparedStatement.setInt(8, id_list_valeur_);
                wallet.setList_value(wallet.getList_value());

            }


            preparedStatement.executeUpdate();
            preparedStatement.close();
            connexion.close();

            wallet.setId_wallet(id);
            investor.ajouterWallet(wallet);
            System.out.println("L'objet Wallet a été inséré dans la base de données.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
