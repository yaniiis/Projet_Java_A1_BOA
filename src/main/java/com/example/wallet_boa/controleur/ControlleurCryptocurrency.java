package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Cryptocurrency;
import com.example.wallet_boa.modele.Investor;
import com.example.wallet_boa.modele.LigneCryptocurrency;
import com.example.wallet_boa.modele.Wallet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ControlleurCryptocurrency {

    private Investor investor;

    @FXML
    Label label_name;
    @FXML
    Button btn_buy_crypto;
    @FXML
    Button btn_sell_crypto;
    @FXML
    ComboBox<String> md_crypto;
    @FXML
    ComboBox<String> md_crypto_sell;
    @FXML
    ComboBox<String> md_wallet;
    @FXML
    ComboBox<String> md_wallet_sell;
    @FXML
    Pane layout_buy;
    @FXML
    Pane layout_sell;
    @FXML
    Button btn_buy_crypto_connect;
    @FXML
    TextField txt_amount;
    @FXML
    TableView<LigneCryptocurrency> tableview_value;
    @FXML
    TextField txt_amount_sell;

    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */

    public void l_logout() throws IOException {
        IntefaceFeatures.log_out();
    }

    public void l_help() throws Exception{
        IntefaceFeatures.layout_help(investor);
    }
    public void l_wallet() throws Exception{
        IntefaceFeatures.layout_wallet(investor);
    }
    public void l_action() throws Exception{
        IntefaceFeatures.layout_stock(investor);
    }
    public void l_transaction() throws Exception{
        IntefaceFeatures.layout_transaction(investor);
    }
    public void l_account() throws Exception{
        IntefaceFeatures.layout_account(investor);
    }
    public void l_accueil() throws Exception{
        IntefaceFeatures.layout_accueil(investor);
    }
    public void setInvestor(Investor investor) throws Exception {
        /*
            Affection d'un objet Investor
         */


        md_wallet_sell.valueProperty().addListener((obs, oldVal, newVal) -> {
            md_crypto_sell.getItems().clear();
            remplirvalue();

        });

        this.investor = investor;
        remplirWallet();
        remplirtableau();

        label_name.setText(investor.getName());

    }


    public void remplirtableau() throws Exception {

        ArrayList<String> list_value = new ArrayList<String>(Arrays.asList("BTC", "ETH", "BNB", "ADA", "SOL", "XRP", "DOT", "DOGE", "AVAX", "LINK"));
        Random rand = new Random();
        int randomInt;
        ObservableList<LigneCryptocurrency> list = FXCollections.observableArrayList();

        for(int i = 0; i < 5; i++) {

            randomInt = rand.nextInt(10 - i);

            String api_url = "https://api.binance.com/api/v3/ticker/price?symbol=" + list_value.get(randomInt) + "USDT";
            URL url = new URL(api_url);
            URLConnection conn = url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            String price = json.getString("price");
            double priceAsDouble = Double.parseDouble(price);

            Button button = new Button("G");

            LigneCryptocurrency ligne = new LigneCryptocurrency(list_value.get(randomInt), priceAsDouble, 10,button );
            list.add(ligne);
            list_value.remove(randomInt);
        }

        tableview_value.setItems(list);
    }

    public void alimenterbouton(){

    }


    public void remplirWallet(){
        ArrayList<Wallet> list_wallet = this.investor.getList_wallet();
        for(Wallet wallet : list_wallet) {
            md_wallet.getItems().add(
                    String.valueOf(wallet.getName())
            );
            md_wallet_sell.getItems().add(
                    String.valueOf(wallet.getName())
            );
        }
    }

    public void remplirvalue(){

        String selected = md_wallet_sell.getSelectionModel().getSelectedItem();
        Cryptocurrency cryptocurrency = new Cryptocurrency();

        for(Wallet wallet : investor.getList_wallet()){
            if(wallet.getName()==selected){
                cryptocurrency = wallet.getList_value();
            }
        }


        if(cryptocurrency.getADA()!=0){
            md_crypto_sell.getItems().add(
                    "ADA"
            );
        }
        if(cryptocurrency.getAVAX()!=0){
            md_crypto_sell.getItems().add(
                    "AVAX"
            );                }
        if(cryptocurrency.getBNB()!=0){
            md_crypto_sell.getItems().add(
                    "BNB"
            );                }
        if(cryptocurrency.getBTC()!=0){
            md_crypto_sell.getItems().add(
                    "BTC"
            );                }
        if(cryptocurrency.getDOGE()!=0){
            md_crypto_sell.getItems().add(
                    "DOGE"
            );                }
        if(cryptocurrency.getDOT()!=0){
            md_crypto_sell.getItems().add(
                    "DOT"
            );                }
        if(cryptocurrency.getETH()!=0){
            md_crypto_sell.getItems().add(
                    "ETH"
            );                }
        if(cryptocurrency.getLINK()!=0){
            md_crypto_sell.getItems().add(
                    "LINK"
            );                }
        if(cryptocurrency.getSOL()!=0){
            md_crypto_sell.getItems().add(
                    "SOL"
            );                }
        if(cryptocurrency.getXRP()!=0){
            md_crypto_sell.getItems().add(
                    "XRP"
            );


        }
    }



    @FXML
    public void back_layout(){
        layout_buy.setVisible(false);layout_sell.setVisible(false);
        tableview_value.setVisible(true);
    }
    @FXML
    public void buy_crypto_layout() {
        layout_buy.setVisible(true);layout_sell.setVisible(false);
        tableview_value.setVisible(false);
    }

    @FXML
    public void sell_crypto_layout(){
        layout_sell.setVisible(true);layout_buy.setVisible(false);
        tableview_value.setVisible(false);
    }

    @FXML
    public void buy_crypto() throws Exception {
        String amount = txt_amount.getText();
        boolean nb = false;
        double numberAmount = 0;
        double montant_investor = 0;
        Wallet wallet_select = new Wallet();

        // Essayer de convertir le montant en double
        try {
            numberAmount = Double.parseDouble(amount);
            nb = true;
        } catch (NumberFormatException e) {
            System.out.println("Le montant n'est pas un nombre valide.");
        }

        if(nb) {
            String selected = md_crypto.getSelectionModel().getSelectedItem();
            String api_url = "https://api.binance.com/api/v3/ticker/price?symbol=";
            api_url += selected + "USDT";


            URL url = new URL(api_url);
            URLConnection conn = url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            String price = json.getString("price");
            double numberPrice = Double.parseDouble(price);

            double part = numberAmount / numberPrice;
            String wallet_selected = md_wallet.getSelectionModel().getSelectedItem();

            for(Wallet wallet : investor.getList_wallet()){
                if(wallet.getName()==wallet_selected){
                    montant_investor = wallet.getAmount();
                    wallet_select = wallet;
                }
            }

            if(montant_investor<numberPrice){
                System.out.println("Vous n'avez pas assez de sous !");
            }else{
                double stock_v;
                double new_value;
                switch (selected){

                    case "BTC":
                        stock_v = wallet_select.getList_value().getBTC();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setBTC(new_value);
                        update_value_bdd(new_value,"BTC",wallet_select.getList_value().getId_crypto());
                        break;
                    case "ETH":
                        stock_v = wallet_select.getList_value().getETH();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setETH(new_value);
                        update_value_bdd(new_value,"ETH",wallet_select.getList_value().getId_crypto());
                        break;
                    case "BNB":
                        stock_v = wallet_select.getList_value().getBNB();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setBNB(new_value);
                        update_value_bdd(new_value,"BNB",wallet_select.getList_value().getId_crypto());
                        break;
                    case "ADA":
                        stock_v = wallet_select.getList_value().getADA();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setADA(new_value);
                        update_value_bdd(new_value,"ADA",wallet_select.getList_value().getId_crypto());
                        break;
                    case "SOL":
                        stock_v = wallet_select.getList_value().getSOL();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setSOL(new_value);
                        update_value_bdd(new_value,"SOL",wallet_select.getList_value().getId_crypto());
                        break;
                    case "XRP":
                        stock_v = wallet_select.getList_value().getXRP();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setXRP(new_value);
                        update_value_bdd(new_value,"XRP",wallet_select.getList_value().getId_crypto());
                        break;
                    case "DOT":
                        stock_v = wallet_select.getList_value().getDOT();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setDOT(new_value);
                        update_value_bdd(new_value,"DOT",wallet_select.getList_value().getId_crypto());
                        break;
                    case "DOGE":
                        stock_v = wallet_select.getList_value().getDOGE();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setDOGE(new_value);
                        update_value_bdd(new_value,"DOGE",wallet_select.getList_value().getId_crypto());
                        break;
                    case "AVAX":
                        stock_v = wallet_select.getList_value().getAVAX();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setAVAX(new_value);
                        update_value_bdd(new_value,"AVAX",wallet_select.getList_value().getId_crypto());
                        break;
                    case "LINK":
                        stock_v = wallet_select.getList_value().getLINK();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setLINK(new_value);
                        update_value_bdd(new_value,"LINK",wallet_select.getList_value().getId_crypto());
                        break;


                }
            }



        }
    }

    public void update_value_bdd(double value_amount, String value_name, int id_list_value) {

        if(value_amount<0){
            value_amount = 0;
        }

        String updateQuery = "UPDATE list_value SET " + value_name + " = ? WHERE id_list_valeur = ?";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";

        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        ) {

            preparedStatement.setDouble(1, value_amount);
            preparedStatement.setInt(2, id_list_value);

            preparedStatement.executeUpdate();
            System.out.println("modif ok");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void sell_crypto() throws Exception {
        String amount = txt_amount_sell.getText();
        boolean nb = false;
        double numberAmount = 0;
        Wallet wallet_select = new Wallet();
        double montant_investor = 0;
        double stock_v;
        double new_value;
        double part_dispo = 0;

        // Essayer de convertir le montant en double
        try {
            numberAmount = Double.parseDouble(amount);
            nb = true;
        } catch (NumberFormatException e) {
            System.out.println("Le montant n'est pas un nombre valide.");
        }

        if(nb) {
            String selected = md_crypto_sell.getSelectionModel().getSelectedItem();
            String api_url = "https://api.binance.com/api/v3/ticker/price?symbol=";
            api_url += selected + "USDT";

            URL url = new URL(api_url);
            URLConnection conn = url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            String price = json.getString("price");
            double numberPrice = Double.parseDouble(price);

            double part_vendre = numberAmount / numberPrice;



            String wallet_selected = md_wallet_sell.getSelectionModel().getSelectedItem();

            for(Wallet wallet : investor.getList_wallet()){
                if(wallet.getName()==wallet_selected){
                    wallet_select = wallet;
                }
            }

            switch (selected){

            case "BTC":
                stock_v = wallet_select.getList_value().getBTC();
                new_value = stock_v - part_vendre;

                if(part_vendre<stock_v){
                    wallet_select.getList_value().setBTC(new_value);
                    update_value_bdd(new_value,"BTC",wallet_select.getList_value().getId_crypto());
                }else{
                    System.out.println("Part insufisante");
                }

                break;
            case "ETH":
                stock_v = wallet_select.getList_value().getETH();
                new_value = stock_v - part_vendre;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setETH(new_value);
                    update_value_bdd(new_value,"ETH",wallet_select.getList_value().getId_crypto());
                }else{
                    System.out.println("Part insufisante");
                }
                break;
            case "BNB":
                stock_v = wallet_select.getList_value().getBNB();
                new_value = stock_v - part_vendre;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setBNB(new_value);
                    update_value_bdd(new_value,"BNB",wallet_select.getList_value().getId_crypto());
                }else{
                    System.out.println("Part insufisante");
                }
                break;
            case "ADA":
                stock_v = wallet_select.getList_value().getADA();
                new_value = stock_v - part_vendre;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setADA(new_value);
                    update_value_bdd(new_value,"ADA",wallet_select.getList_value().getId_crypto());
                }else{
                    System.out.println("Part insufisante");
                }
                break;
            case "SOL":
                stock_v = wallet_select.getList_value().getSOL();
                new_value = stock_v - part_vendre;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setSOL(new_value);
                    update_value_bdd(new_value,"SOL",wallet_select.getList_value().getId_crypto());
                }else{
                    System.out.println("Part insufisante");
                }
                break;
            case "XRP":
                stock_v = wallet_select.getList_value().getXRP();
                new_value = stock_v - part_vendre;


                if(part_vendre<stock_v){
                    wallet_select.getList_value().setXRP(new_value);
                    update_value_bdd(new_value,"XRP",wallet_select.getList_value().getId_crypto());
                }else{
                    System.out.println("Part insufisante");
                }
                break;
            case "DOT":
                stock_v = wallet_select.getList_value().getDOT();
                new_value = stock_v - part_vendre;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setDOT(new_value);
                    update_value_bdd(new_value,"DOT",wallet_select.getList_value().getId_crypto());
                }else{
                    System.out.println("Part insufisante");
                }
                break;
            case "DOGE":
                stock_v = wallet_select.getList_value().getDOGE();
                new_value = stock_v - part_vendre;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setDOGE(new_value);
                    update_value_bdd(new_value,"DOGE",wallet_select.getList_value().getId_crypto());
                }else{
                    System.out.println("Part insufisante");
                }
                break;
            case "AVAX":
                stock_v = wallet_select.getList_value().getAVAX();
                new_value = stock_v - part_vendre;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setAVAX(new_value);
                    update_value_bdd(new_value,"AVAX",wallet_select.getList_value().getId_crypto());
                }else{
                    System.out.println("Part insufisante");
                }
                break;
            case "LINK":
                stock_v = wallet_select.getList_value().getLINK();
                new_value = stock_v - part_vendre;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setLINK(new_value);
                    update_value_bdd(new_value,"LINK",wallet_select.getList_value().getId_crypto());
                }else{
                    System.out.println("Part insufisante");
                }
                break;

        }

        }
    }



}
