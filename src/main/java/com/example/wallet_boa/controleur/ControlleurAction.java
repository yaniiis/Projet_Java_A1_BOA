package com.example.wallet_boa.controleur;

import com.binance.connector.client.utils.JSONParser;
import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class ControlleurAction {

    private Investor investor;
    private Blockchaine blockchain;


    @FXML
    Button btn_back_graphe;
    @FXML
    ImageView imageView;
    @FXML
    Label label_name;
    @FXML
    Label label_solde;
    @FXML
    NumberAxis xAxis;
    @FXML
    TableView tableview_value;
    @FXML
    VBox layout_sell;
    @FXML
    VBox layout_buy;
    @FXML
    Button btn_back;
    @FXML
    ComboBox md_wallet_sell;
    @FXML
    ComboBox md_wallet;
    @FXML
    ComboBox md_crypto_sell;
    @FXML
    TextField txt_amount_sell;
    @FXML
    LineChart lineChart;
    @FXML
    TextField txt_amount;
    @FXML
    ComboBox md_crypto;


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
    public void l_crytpo() throws Exception{
        IntefaceFeatures.layout_crypto(investor, blockchain);
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
        String solde = "Solde : " + IntefaceFeatures.compter_montant(investor) + " $";
        label_solde.setText(solde);
        this.investor = investor;
        this.blockchain = blockchaine;
        label_name.setText(investor.getName());
        remplirWallet();
        charger_graphique();


        md_wallet_sell.valueProperty().addListener((obs, oldVal, newVal) -> {
            md_crypto_sell.getItems().clear();
            remplirvalue();

        });


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

        String selected = (String) md_wallet_sell.getSelectionModel().getSelectedItem();
        Stock stock = new Stock();

        for(Wallet wallet : investor.getList_wallet()){
            if(wallet.getName()==selected){
                stock = wallet.getList_action();
            }
        }

        if(stock.getAAPL()!=0){
            md_crypto_sell.getItems().add(
                    "AAPL"
            );
        }
        if(stock.getAMSZN()!=0){
            md_crypto_sell.getItems().add(
                    "AMSZN"
            );                }
        if(stock.getGOOGL()!=0){
            md_crypto_sell.getItems().add(
                    "GOOGL"
            );                }
        if(stock.getMSFT()!=0){
            md_crypto_sell.getItems().add(
                    "MSFT"
            );
        }

    }



    public void charger_graphique() throws Exception {

        String[] symbols = {"AAPL", "MSFT", "AMZN", "GOOGL"};
        String apiKey = "O2VSXG62XNBFFJDL";

        ObservableList<LigneStock> list = FXCollections.observableArrayList();

        for (String symbol : symbols) {
            String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&outputsize=compact&apikey=" + apiKey;

            URL url = new URL(apiUrl);
            URLConnection request = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {};

            HashMap<String, Object> root = mapper.readValue(response.toString(), typeRef);
            HashMap<String, HashMap<String, String>> timeSeries = (HashMap<String, HashMap<String, String>>) root.get("Time Series (Daily)");

            Button button = new Button("G");
            button.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    try {
                        lineChart.setVisible(true);
                        tableview_value.setVisible(false);
                        graphe_action(symbol);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            Map.Entry<String, HashMap<String, String>> latestEntry = timeSeries.entrySet().iterator().next(); // Récupère la première entrée (la plus récente)

            HashMap<String, String> data = latestEntry.getValue();
            String closePrice = data.get("4. close");
            double closePriceAsDouble = Double.parseDouble(closePrice);
            LigneStock ligne = new LigneStock(symbol, closePriceAsDouble, button);
            list.add(ligne);

        }
        tableview_value.setItems(list);

    }

    public void graphe_action(String symbol) throws Exception {

        String apiKey = "RGMOA4EPC2N6MFF9";

        String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&outputsize=compact&apikey=" + apiKey;

        URL url = new URL(apiUrl);
        URLConnection request = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {};

        HashMap<String, Object> root = mapper.readValue(response.toString(), typeRef);
        HashMap<String, HashMap<String, String>> timeSeries = (HashMap<String, HashMap<String, String>>) root.get("Time Series (Daily)");

        TreeMap<String, HashMap<String, String>> sortedData = new TreeMap<>(Collections.reverseOrder());
        sortedData.putAll(timeSeries);

        List<Double> closingPrices = new ArrayList<>();
        int count = 0;

        for (Map.Entry<String, HashMap<String, String>> entry : sortedData.entrySet()) {
            if (count++ == 10) {
                break;
            }
            double closePrice = Double.parseDouble(entry.getValue().get("4. close"));
            closingPrices.add(closePrice);
        }

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(symbol);
        Collections.reverse(closingPrices);


        for (int i = 0; i < closingPrices.size(); i++) {
            series.getData().add(new XYChart.Data<>(i , closingPrices.get(i)));
        }

        xAxis = new NumberAxis(0, 10, 1);

        Platform.runLater(() -> {
            lineChart.getData().clear();
            lineChart.getData().add(series);
        });

    }

    public void layout_buy(){
        tableview_value.setVisible(false);
        layout_buy.setVisible(true);
        layout_sell.setVisible(false);
    }

    public void layout_sell(){
        tableview_value.setVisible(false);
        layout_buy.setVisible(false);
        layout_sell.setVisible(true);
    }

    @FXML
    public void back_layout(){
        layout_buy.setVisible(false);
        layout_sell.setVisible(false);
        tableview_value.setVisible(true);
        lineChart.setVisible(false);
        btn_back.setVisible(false);
    }


    public void buy_crypto() throws Exception {

        String amount = txt_amount.getText();
        String selected = (String) md_crypto.getSelectionModel().getSelectedItem();
        String wallet_selected = (String) md_wallet.getSelectionModel().getSelectedItem();

        if (wallet_selected != null && !wallet_selected.isEmpty() && selected != null && !selected.isEmpty()) {

            boolean nb = false;
            double numberAmount = 0;
            double montant_investor = 0;
            Wallet wallet_select = new Wallet();

            try {
                numberAmount = Double.parseDouble(amount);
                nb = true;
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur sell");
                alert.setHeaderText("Amount not valided");
                alert.setContentText(null);
                alert.showAndWait();
            }

            if (nb) {

                String apiKey = "RGMOA4EPC2N6MFF9";
                String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + selected + "&outputsize=compact&apikey=" + apiKey;

                URL url = new URL(apiUrl);
                URLConnection request = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    String json = response.toString();
                    JSONObject jsonObject = new JSONObject(json);

                    JSONObject timeSeries = jsonObject.getJSONObject("Time Series (Daily)");
                    // Assuming that the first key is the most recent date
                    String latestDate = timeSeries.keys().next();
                    JSONObject latestData = timeSeries.getJSONObject(latestDate);
                    double price = latestData.getDouble("4. close");


                    double numberPrice = Double.parseDouble(String.valueOf(price));

                    double part = numberAmount / numberPrice;

                    for (Wallet wallet : investor.getList_wallet()) {
                        if (wallet.getName() == wallet_selected) {
                            montant_investor = wallet.getAmount();
                            wallet_select = wallet;
                        }
                    }

                    if (numberAmount > montant_investor) {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur buy");
                        alert.setHeaderText("Not enough amount");
                        alert.setContentText(null);
                        alert.showAndWait();

                    } else {
                        double stock_v;
                        double new_value;

                        switch (selected) {

                            case "AMSZN":
                                stock_v = wallet_select.getList_value().getBTC();
                                new_value = stock_v + part;
                                wallet_select.getList_action().setAMSZN(new_value);
                                update_value_bdd(new_value, "AMSZN", wallet_select);
                                break;
                            case "AAPL":
                                stock_v = wallet_select.getList_value().getETH();
                                new_value = stock_v + part;
                                wallet_select.getList_action().setAAPL(new_value);
                                update_value_bdd(new_value, "AAPL", wallet_select);
                                break;
                            case "MSFT":
                                stock_v = wallet_select.getList_value().getBNB();
                                new_value = stock_v + part;
                                wallet_select.getList_action().setMSFT(new_value);
                                update_value_bdd(new_value, "MSFT", wallet_select);
                                break;
                            case "GOOGL":
                                stock_v = wallet_select.getList_value().getADA();
                                new_value = stock_v + part;
                                wallet_select.getList_action().setGOOGL(new_value);
                                update_value_bdd(new_value, "GOOGL", wallet_select);
                                break;
                        }

                        double new_montant = montant_investor - numberAmount;
                        wallet_select.setAmount(new_montant);
                        double montant_label = IntefaceFeatures.compter_montant(investor);
                        String mont = "Solede : " + montant_label + " $";
                        label_solde.setText(mont);
                        update_montant_bdd(wallet_select, new_montant);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Payment");
                        alert.setHeaderText("Purchase validated");
                        alert.setContentText(null);
                        alert.showAndWait();
                        back_layout();

                    }

            }else{

            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur sell");
            alert.setHeaderText("Wallet ou Stock not selected");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }

    private void update_montant_bdd(Wallet wallet, double value_amount) {

        String updateQuery = "UPDATE wallet SET amount = ? WHERE id_wallet = ?";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";

        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        ) {

            preparedStatement.setDouble(1, value_amount);
            preparedStatement.setInt(2, wallet.getId_wallet());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update_value_bdd(double value_amount, String value_name, Wallet wallet) {

        if(value_amount<0){
            value_amount = 0;
        }

        String updateQuery = "UPDATE actions SET " + value_name + " = ? WHERE id_list_valeur = ?";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";

        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        ) {

            preparedStatement.setDouble(1, value_amount);
            preparedStatement.setInt(2, wallet.getList_action().getId_stock());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sell_crypto() throws Exception{
        String amount = txt_amount_sell.getText();
        String wallet_selected = (String) md_wallet_sell.getSelectionModel().getSelectedItem();
        String selected = (String) md_crypto_sell.getSelectionModel().getSelectedItem();

        if (wallet_selected != null && !wallet_selected.isEmpty() && selected != null && !selected.isEmpty()) {


            boolean nb = false;
            double numberAmount = 0;
            Wallet wallet_select = new Wallet();
            double montant_investor = 0;
            double new_value;
            double part_dispo = 0;
            double stock_v = 0;

            try {
                numberAmount = Double.parseDouble(amount);
                nb = true;
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur sell");
                alert.setHeaderText("Amount not valided");
                alert.setContentText(null);
                alert.showAndWait();
            }

            if(nb) {


                String apiKey = "RGMOA4EPC2N6MFF9";
                String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + selected + "&outputsize=compact&apikey=" + apiKey;

                URL url = new URL(apiUrl);
                URLConnection request = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                String json = response.toString();
                JSONObject jsonObject = new JSONObject(json);

                JSONObject timeSeries = jsonObject.getJSONObject("Time Series (Daily)");
                // Assuming that the first key is the most recent date
                String latestDate = timeSeries.keys().next();
                JSONObject latestData = timeSeries.getJSONObject(latestDate);
                double price = latestData.getDouble("4. close");

                double numberPrice = Double.parseDouble(String.valueOf(price));

                double part_vendre = numberAmount / numberPrice;


                for(Wallet wallet : investor.getList_wallet()){
                    if(wallet.getName()==wallet_selected){
                        wallet_select = wallet;
                    }
                }


                switch (selected){

                    case "AMSZN":

                        stock_v = wallet_select.getList_value().getBTC();
                        new_value = (stock_v - part_vendre) * 0.995;

                        if(part_vendre<stock_v){
                            update_value_bdd(new_value,"AMSZN",wallet_select);
                            wallet_select.getList_action().setAMSZN(new_value);
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur sell");
                            alert.setHeaderText("not enough cryptocurrency");
                            alert.setContentText(null);
                            alert.showAndWait();
                        }

                        break;
                    case "AAPL":

                        stock_v = wallet_select.getList_value().getETH();
                        new_value = (stock_v - part_vendre) * 0.995;
                        if(part_vendre<stock_v){
                            wallet_select.getList_action().setAAPL(new_value);
                            update_value_bdd(new_value,"AAPL",wallet_select);
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur sell");
                            alert.setHeaderText("not enough cryptocurrency");
                            alert.setContentText(null);
                            alert.showAndWait();
                        }
                        break;
                    case "MSFT":
                        stock_v = wallet_select.getList_value().getBNB();
                        new_value = (stock_v - part_vendre) * 0.995;
                        if(part_vendre<stock_v){
                            wallet_select.getList_action().setMSFT(new_value);
                            update_value_bdd(new_value,"MSFT",wallet_select);
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur sell");
                            alert.setHeaderText("not enough cryptocurrency");
                            alert.setContentText(null);
                            alert.showAndWait();
                        }
                        break;
                    case "GOOGL":
                        stock_v = wallet_select.getList_value().getADA();
                        new_value = (stock_v - part_vendre) * 0.995;
                        if(part_vendre<stock_v){
                            wallet_select.getList_value().setADA(new_value);
                            update_value_bdd(new_value,"GOOGL",wallet_select);
                            wallet_select.getList_action().setGOOGL(new_value);
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur sell");
                            alert.setHeaderText("not enough cryptocurrency");
                            alert.setContentText(null);
                            alert.showAndWait();
                        }
                        break;


                }

                double new_montant = montant_investor + (numberAmount * 0.995) ;
                wallet_select.setAmount(new_montant);
                double montant_label = IntefaceFeatures.compter_montant(investor);
                String mont = "Solde " + montant_label + " $";
                label_solde.setText(mont);
                update_montant_bdd(wallet_select, new_montant);


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("sell");
                alert.setHeaderText("Sale validated");
                alert.setContentText(null);
                alert.showAndWait();
                back_layout();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur sell");
            alert.setHeaderText("Wallet ou Cryptoccurency not selected");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }

}
