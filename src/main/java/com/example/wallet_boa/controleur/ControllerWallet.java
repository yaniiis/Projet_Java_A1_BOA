package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Investor;
import com.example.wallet_boa.modele.Wallet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerWallet {

    private Investor investor;
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



    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */

    public void ajoutWallet(Investor _investor){

        int indice_wallet = 1;
        String query = "SELECT id_wallet, name, date, description, id_investor, id_list_valeur, amount FROM wallet WHERE id_investor = ? ;";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";
        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, _investor.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) { // Change this to while
                    String name = resultSet.getString("name");

                    if (name != null && !name.isEmpty()) {

                        Date date = resultSet.getDate("date");
                        int id_wallet = resultSet.getInt("id_wallet");

                        String description = resultSet.getString("description");
                        int id_investor = resultSet.getInt("id_investor");
                        int id_list_valeur = resultSet.getInt("id_list_valeur");
                        double amount = resultSet.getDouble("amount");
                        create_walet(name,date, id_wallet, description, id_investor, id_list_valeur, amount, indice_wallet);
                        indice_wallet +=1;
                    } else {
                        System.out.println("Mettre une alerte pour créer un wallet");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void create_walet(String name, Date date, int id_wallet, String description, int id_investor, int id_list_valeur, double amount, int i){

        Wallet new_vallet = new Wallet(name, date, description, amount, id_wallet, id_investor, id_list_valeur);
        switch (i){
            case 1 :
                pane_1.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                label_1.setText(name);
                amount_1.setText(String.valueOf(amount));
                break;

            case 2 :
                pane_2.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                label_2.setText(name);
                amount_2.setText(String.valueOf(amount));
                break;
            case 3 :
                pane_3.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                label_3.setText(name);
                amount_3.setText(String.valueOf(amount));
                break;
            case 4 :
                pane_4.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                label_4.setText(name);
                amount_4.setText(String.valueOf(amount));
                break;
            case 5 :
                pane_5.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                label_5.setText(name);
                amount_5.setText(String.valueOf(amount));
                break;
            case 6 :
                pane_6.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                label_6.setText(name);
                amount_6.setText(String.valueOf(amount));
                break;
            case 7 :
                pane_7.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                label_7.setText(name);
                amount_7.setText(String.valueOf(amount));
                break;
            case 8 :
                pane_8.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                label_8.setText(name);
                amount_8.setText(String.valueOf(amount));
                break;
            case 9 :
                pane_9.setStyle("-fx-background-color: orange; -fx-background-radius: 10;");
                label_9.setText(name);
                amount_9.setText(String.valueOf(amount));
                break;


        }






    }

    public void l_logout() throws IOException {
        IntefaceFeatures.log_out();
    }

    public void l_help() throws Exception{
        Investor investor = new Investor(this.investor.getName(),this.investor.getSurname(),this.investor.getEmail(),this.investor.getPhone_number(),this.investor.getId());
        IntefaceFeatures.layout_help(investor);
    }
    public void l_action() throws Exception{
        Investor investor = new Investor(this.investor.getName(),this.investor.getSurname(),this.investor.getEmail(),this.investor.getPhone_number(),this.investor.getId());
        IntefaceFeatures.layout_stock(investor);
    }
    public void l_transaction() throws Exception{
        Investor investor = new Investor(this.investor.getName(),this.investor.getSurname(),this.investor.getEmail(),this.investor.getPhone_number(),this.investor.getId());
        IntefaceFeatures.layout_transaction(investor);
    }
    public void l_crytpo() throws Exception{
        Investor investor = new Investor(this.investor.getName(),this.investor.getSurname(),this.investor.getEmail(),this.investor.getPhone_number(),this.investor.getId());
        IntefaceFeatures.layout_crypto(investor);
    }
    public void l_account() throws Exception{
        Investor investor = new Investor(this.investor.getName(),this.investor.getSurname(),this.investor.getEmail(),this.investor.getPhone_number(),this.investor.getId());
        IntefaceFeatures.layout_account(investor);
    }
    public void l_accueil() throws Exception{
        Investor investor = new Investor(this.investor.getName(),this.investor.getSurname(),this.investor.getEmail(),this.investor.getPhone_number(),this.investor.getId());
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

    vbox_clone.setVisible(false);
    vbox_wallet.setVisible(false);
    vbox_new_wallet.setVisible(true);
    hbox_crypto.setVisible(false);

    }
    @FXML
    public void layout_clone_wallet(){
        vbox_clone.setVisible(true);
        vbox_wallet.setVisible(false);
        vbox_new_wallet.setVisible(false);
        hbox_crypto.setVisible(false);
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

        Wallet wallet = new Wallet(wallet_name, dateSQL, description_wallet, 0 );

        insert_wallet_bdd(wallet);
    }

    public void insert_wallet_bdd(Wallet wallet) throws Exception {


        try {
            String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";

            Connection connexion = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);

            int id_list_value = IntefaceFeatures.random_id();
            String sql = "INSERT INTO list_value (id_list_valeur, BTC, ETH, BNB, ADA, SOL, XRP, DOT, DOGE, AVAX, LINK) VALUES (?, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)";
            PreparedStatement pstmt = connexion.prepareStatement(sql);
            pstmt.setInt(1, id_list_value);
            int affectedRows = pstmt.executeUpdate();



            String requeteSQL = "INSERT INTO wallet (id_wallet ,name, description, amount, date, id_investor, id_list_valeur) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL);
            int id = IntefaceFeatures.random_id();
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, wallet.getName());
            preparedStatement.setString(3, wallet.getDescription());
            preparedStatement.setDouble(4, wallet.getAmount());
            preparedStatement.setDate(5, wallet.getDate());
            preparedStatement.setInt(6, this.investor.getId());
            preparedStatement.setInt(7, id_list_value);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connexion.close();
            wallet.setId_wallet(id);
            wallet.setId_list_valeur(this.investor.getId());
            wallet.setId_investor(id_list_value);

            System.out.println("L'objet Wallet a été inséré dans la base de données.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
