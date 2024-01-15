package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
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
import java.util.*;

public class ControlleurCryptocurrency {

    private Investor investor;
    private Blockchaine blockchain;

    @FXML
    Label label_name;
    @FXML
    ImageView imageView;
    @FXML
    Button btn_buy_crypto;
    @FXML
    Button btn_sell_crypto;
    @FXML
    ComboBox<String> md_crypto;
    @FXML
    Label label_solde;
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
    @FXML
    LineChart lineChartcrypto;
    @FXML
    NumberAxis xAxis;

    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */

    public void l_logout() throws IOException {
        IntefaceFeatures.log_out();
    }

    public void l_help() throws Exception{
        IntefaceFeatures.layout_help(investor, blockchain);
    }
    public void l_wallet() throws Exception{
        IntefaceFeatures.layout_wallet(investor, blockchain);
    }
    public void l_action() throws Exception{
        IntefaceFeatures.layout_stock(investor,blockchain);
    }
    public void l_transaction() throws Exception{
        IntefaceFeatures.layout_transaction(investor,blockchain);
    }
    public void l_account() throws Exception{
        IntefaceFeatures.layout_account(investor,blockchain);
    }
    public void l_accueil() throws Exception{
        IntefaceFeatures.layout_accueil(investor,blockchain);
    }
    public void setInvestor(Investor investor, Blockchaine blockchaine) throws Exception {
        /*
            Affection d'un objet Investor
         */

        Image image = new Image(new File("src/main/resources/galerie/logo.png").toURI().toString());
        imageView.setImage(image);

        md_wallet_sell.valueProperty().addListener((obs, oldVal, newVal) -> {
            md_crypto_sell.getItems().clear();
            remplirvalue();

        });

        String solde = "Solde : " + IntefaceFeatures.compter_montant(investor) + " $";
        label_solde.setText(solde);
        this.investor = investor;
        this.blockchain = blockchaine;
        remplirWallet();
        remplirtableau();

        label_name.setText(investor.getName());

    }


    public void remplirtableau() throws Exception {

        ArrayList<String> list_value = new ArrayList<String>(Arrays.asList("BTC", "ETH", "BNB", "ADA", "SOL", "XRP", "DOT", "DOGE", "AVAX", "LINK"));

        ObservableList<LigneCryptocurrency> list = FXCollections.observableArrayList();

        for(int i = 0; i < 10; i++) {


            String value = list_value.get(i);

            String api_url = "https://api.binance.com/api/v3/ticker/price?symbol=" + value + "USDT";
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
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    tableview_value.setVisible(false);
                    try {
                        affiche_graphique(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            LigneCryptocurrency ligne = new LigneCryptocurrency(value, priceAsDouble, 10,button );
            list.add(ligne);
        }

        tableview_value.setItems(list);
    }

    public void affiche_graphique(String value)throws Exception{
        try {
            lineChartcrypto.setVisible(true);
            String url = "https://api.binance.com/api/v3/klines?symbol="+ value + "USDT&interval=1d&limit=10";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<List<Object>>> typeRef = new TypeReference<>() {};
            List<List<Object>> data = mapper.readValue(response.toString(), typeRef);

            List<Double> closingPrices = new ArrayList<>();
            for (List<Object> dayData : data) {
                closingPrices.add(Double.parseDouble(dayData.get(4).toString()));
            }

            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(value);
            Collections.reverse(closingPrices);
            xAxis = new NumberAxis(0, 11, 1);
            xAxis.setLabel("Daily ");

            for (int i = 0; i < closingPrices.size(); i++) {
                series.getData().add(new XYChart.Data<>(i , closingPrices.get(i)));
            }

            Platform.runLater(() -> {
                lineChartcrypto.getData().add(series);
            });

        }catch (Exception e){
            System.out.println(e);
        }
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

            if(numberAmount  > montant_investor){
                System.out.println("Vous n'avez pas assez de sous !");
            }else{
                double stock_v;
                double new_value;



                switch (selected){

                    case "BTC":
                        stock_v = wallet_select.getList_value().getBTC();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setBTC(new_value);
                        update_value_bdd(new_value,"BTC",wallet_select, part);
                        break;
                    case "ETH":
                        stock_v = wallet_select.getList_value().getETH();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setETH(new_value);
                        update_value_bdd(new_value,"ETH",wallet_select, part);
                        break;
                    case "BNB":
                        stock_v = wallet_select.getList_value().getBNB();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setBNB(new_value);
                        update_value_bdd(new_value,"BNB",wallet_select, part);
                        break;
                    case "ADA":
                        stock_v = wallet_select.getList_value().getADA();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setADA(new_value);
                        update_value_bdd(new_value,"ADA",wallet_select, part);
                        break;
                    case "SOL":
                        stock_v = wallet_select.getList_value().getSOL();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setSOL(new_value);
                        update_value_bdd(new_value,"SOL",wallet_select, part);
                        break;
                    case "XRP":
                        stock_v = wallet_select.getList_value().getXRP();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setXRP(new_value);
                        update_value_bdd(new_value,"XRP",wallet_select, part);
                        break;
                    case "DOT":
                        stock_v = wallet_select.getList_value().getDOT();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setDOT(new_value);
                        update_value_bdd(new_value,"DOT",wallet_select, part);
                        break;
                    case "DOGE":
                        stock_v = wallet_select.getList_value().getDOGE();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setDOGE(new_value);
                        update_value_bdd(new_value,"DOGE",wallet_select, part);
                        break;
                    case "AVAX":
                        stock_v = wallet_select.getList_value().getAVAX();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setAVAX(new_value);
                        update_value_bdd(new_value,"AVAX",wallet_select, part);
                        break;
                    case "LINK":
                        stock_v = wallet_select.getList_value().getLINK();
                        new_value = stock_v + part;
                        wallet_select.getList_value().setLINK(new_value);
                        update_value_bdd(new_value,"LINK",wallet_select, part);
                        break;
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Payment");
                alert.setHeaderText("Purchase validated");
                alert.setContentText(null);
                alert.showAndWait();

            }



        }
    }

    public void update_value_bdd(double value_amount, String value_name, Wallet wallet, double part) {

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
            preparedStatement.setInt(2, wallet.getList_value().getId_crypto());

            preparedStatement.executeUpdate();

            createTransaction(wallet, value_name, part);

            System.out.println("modif ok");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createTransaction(Wallet wallet, String value_name, double part){

        Transaction transaction = new Transaction(wallet, value_name, part);
        Block block_last = blockchain.getLastBlock();
        if (block_last.getTransactions().size() < 10) {
            block_last.getTransactions().add(transaction);

        }else{
            String ancien_hash = block_last.getHash();
            int ancien_index = block_last.getIndex() + 1;

            Block new_block = new Block(ancien_index,ancien_hash );
            blockchain.addBlock(new_block);
            insert_block_bdd(new_block);

        }
        insert_transaction_bdd(transaction);

    }

    public void insert_block_bdd(Block new_block){

        String query = "INSERT INTO blocks (indice, timestamp, previousHash, hash) VALUES ( ?, ?, ?, ?)";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";

        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, new_block.getIndex());
            preparedStatement.setTimestamp(2, new_block.getTimestamp());
            preparedStatement.setString(3, new_block.getPreviousHash());
            preparedStatement.setString(4, new_block.getHash());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("L'insertion réussi.");
            } else {
                System.out.println("L'insertion a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert_transaction_bdd(Transaction transaction){
        String query = "INSERT INTO transactions (transactionId, originWalletId, value, amount, indice) VALUES (?, ?, ?, ?, ?)";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";

        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, transaction.getTransactionId());
            preparedStatement.setInt(2, transaction.getOriginWallet().getId_wallet());
            preparedStatement.setString(3, transaction.getValue());
            preparedStatement.setDouble(4, transaction.getAmount());
            preparedStatement.setInt(5, blockchain.getLastBlock().getIndex());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("L'insertion réussi.");
            } else {
                System.out.println("L'insertion a échoué.");
            }
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
        double new_value;
        double part_dispo = 0;
        double stock_v = 0;



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
                new_value = (stock_v - part_vendre) * 0.995;

                if(part_vendre<stock_v){
                    wallet_select.getList_value().setBTC(new_value);
                    update_value_bdd(new_value,"BTC",wallet_select, part_vendre);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur sell");
                    alert.setHeaderText("not enough cryptocurrency");
                    alert.setContentText(null);
                    alert.showAndWait();
                }

                break;
            case "ETH":

                stock_v = wallet_select.getList_value().getETH();
                new_value = (stock_v - part_vendre) * 0.995;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setETH(new_value);
                    update_value_bdd(new_value,"ETH",wallet_select, part_vendre);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur sell");
                    alert.setHeaderText("not enough cryptocurrency");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
                break;
            case "BNB":
                stock_v = wallet_select.getList_value().getBNB();
                new_value = (stock_v - part_vendre) * 0.995;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setBNB(new_value);
                    update_value_bdd(new_value,"BNB",wallet_select, part_vendre);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur sell");
                    alert.setHeaderText("not enough cryptocurrency");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
                break;
            case "ADA":
                stock_v = wallet_select.getList_value().getADA();
                new_value = (stock_v - part_vendre) * 0.995;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setADA(new_value);
                    update_value_bdd(new_value,"ADA",wallet_select, part_vendre);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur sell");
                    alert.setHeaderText("not enough cryptocurrency");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
                break;
            case "SOL":
                stock_v = wallet_select.getList_value().getSOL();
                new_value = (stock_v - part_vendre) * 0.995;

                if(part_vendre<stock_v){
                    wallet_select.getList_value().setSOL(new_value);
                    update_value_bdd(new_value,"SOL",wallet_select, part_vendre);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur sell");
                    alert.setHeaderText("not enough cryptocurrency");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
                break;
            case "XRP":
                stock_v = wallet_select.getList_value().getXRP();
                new_value = (stock_v - part_vendre) * 0.995;

                if(part_vendre<stock_v){
                    wallet_select.getList_value().setXRP(new_value);
                    update_value_bdd(new_value,"XRP",wallet_select, part_vendre);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur sell");
                    alert.setHeaderText("not enough cryptocurrency");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
                break;
            case "DOT":
                stock_v = wallet_select.getList_value().getDOT();
                new_value = (stock_v - part_vendre) * 0.995;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setDOT(new_value);
                    update_value_bdd(new_value,"DOT",wallet_select, part_vendre);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur sell");
                    alert.setHeaderText("not enough cryptocurrency");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
                break;
            case "DOGE":
                stock_v = wallet_select.getList_value().getDOGE();
                new_value = (stock_v - part_vendre) * 0.995;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setDOGE(new_value);
                    update_value_bdd(new_value,"DOGE",wallet_select, part_vendre);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur sell");
                    alert.setHeaderText("not enough cryptocurrency");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
                break;
            case "AVAX":
                stock_v = wallet_select.getList_value().getAVAX();
                new_value = (stock_v - part_vendre) * 0.995;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setAVAX(new_value);
                    update_value_bdd(new_value,"AVAX",wallet_select, part_vendre);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur sell");
                    alert.setHeaderText("not enough cryptocurrency");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
                break;
            case "LINK":
                stock_v = wallet_select.getList_value().getLINK();
                new_value = (stock_v - part_vendre) * 0.995;
                if(part_vendre<stock_v){
                    wallet_select.getList_value().setLINK(new_value);
                    update_value_bdd(new_value,"LINK",wallet_select, part_vendre);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur sell");
                    alert.setHeaderText("not enough cryptocurrency");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
                break;

            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("sell");
            alert.setHeaderText("Sale validated");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }



}
