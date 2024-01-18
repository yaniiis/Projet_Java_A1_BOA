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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ControlleurAction {

    private Investor investor;
    private Blockchaine blockchain;

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
    public void l_transaction() throws Exception{
        IntefaceFeatures.layout_transaction(investor, blockchain);
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
        //charger_graphique();


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
        String apiKey = "RGMOA4EPC2N6MFF9";
        //O2VSXG62XNBFFJDL

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
                    // Gérer l'action du bouton ici.
                }
            });
            Map.Entry<String, HashMap<String, String>> latestEntry = timeSeries.entrySet().iterator().next(); // Récupère la première entrée (la plus récente)

            HashMap<String, String> data = latestEntry.getValue();
            String closePrice = data.get("4. close");
            double closePriceAsDouble = Double.parseDouble(closePrice);
            System.out.println(closePriceAsDouble);
            LigneStock ligne = new LigneStock(symbol, closePriceAsDouble, button);
            list.add(ligne);
        }

        tableview_value.setItems(list);

    }

    public void layout_buy(){
        tableview_value.setVisible(false);
        layout_buy.setVisible(false);
        layout_sell.setVisible(true);
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
        //lineChartcrypto.setVisible(false);
        btn_back.setVisible(false);
    }

    public void buy_crypto(){
        String amount = txt_amount.getText();
        String selected = md_crypto.getSelectionModel().getSelectedItem();
        String wallet_selected = md_wallet.getSelectionModel().getSelectedItem();
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

            if(nb) {
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

                for(Wallet wallet : investor.getList_wallet()){
                    if(wallet.getName()==wallet_selected){
                        montant_investor = wallet.getAmount();
                        wallet_select = wallet;
                    }
                }

                if(numberAmount  > montant_investor){

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur buy");
                    alert.setHeaderText("Not enough amount");
                    alert.setContentText(null);
                    alert.showAndWait();

                }else{
                    double stock_v;
                    double new_value;



                    switch (selected){

                        case "BTC":
                            stock_v = wallet_select.getList_value().getBTC();
                            new_value = stock_v + part;
                            wallet_select.getList_value().setBTC(new_value);
                            update_value_bdd(new_value,"BTC",wallet_select, part);
                            wallet_select.getList_value().setBTC(new_value);
                            break;
                        case "ETH":
                            stock_v = wallet_select.getList_value().getETH();
                            new_value = stock_v + part;
                            wallet_select.getList_value().setETH(new_value);
                            update_value_bdd(new_value,"ETH",wallet_select, part);
                            wallet_select.getList_value().setETH(new_value);
                            break;
                        case "BNB":
                            stock_v = wallet_select.getList_value().getBNB();
                            new_value = stock_v + part;
                            wallet_select.getList_value().setBNB(new_value);
                            update_value_bdd(new_value,"BNB",wallet_select, part);
                            wallet_select.getList_value().setBNB(new_value);
                            break;
                        case "ADA":
                            stock_v = wallet_select.getList_value().getADA();
                            new_value = stock_v + part;
                            wallet_select.getList_value().setADA(new_value);
                            update_value_bdd(new_value,"ADA",wallet_select, part);
                            wallet_select.getList_value().setADA(new_value);
                            break;
                        case "SOL":
                            stock_v = wallet_select.getList_value().getSOL();
                            new_value = stock_v + part;
                            wallet_select.getList_value().setSOL(new_value);
                            update_value_bdd(new_value,"SOL",wallet_select, part);
                            wallet_select.getList_value().setSOL(new_value);
                            break;
                        case "XRP":
                            stock_v = wallet_select.getList_value().getXRP();
                            new_value = stock_v + part;
                            wallet_select.getList_value().setXRP(new_value);
                            update_value_bdd(new_value,"XRP",wallet_select, part);
                            wallet_select.getList_value().setXRP(new_value);
                            break;
                        case "DOT":
                            stock_v = wallet_select.getList_value().getDOT();
                            new_value = stock_v + part;
                            wallet_select.getList_value().setDOT(new_value);
                            update_value_bdd(new_value,"DOT",wallet_select, part);
                            wallet_select.getList_value().setDOT(new_value);
                            break;
                        case "DOGE":
                            stock_v = wallet_select.getList_value().getDOGE();
                            new_value = stock_v + part;
                            wallet_select.getList_value().setDOGE(new_value);
                            update_value_bdd(new_value,"DOGE",wallet_select, part);
                            wallet_select.getList_value().setDOGE(new_value);
                            break;
                        case "AVAX":
                            stock_v = wallet_select.getList_value().getAVAX();
                            new_value = stock_v + part;
                            wallet_select.getList_value().setAVAX(new_value);
                            update_value_bdd(new_value,"AVAX",wallet_select, part);
                            wallet_select.getList_value().setAVAX(new_value);
                            break;
                        case "LINK":
                            stock_v = wallet_select.getList_value().getLINK();
                            new_value = stock_v + part;
                            wallet_select.getList_value().setLINK(new_value);
                            update_value_bdd(new_value,"LINK",wallet_select, part);
                            wallet_select.getList_value().setLINK(new_value);
                            break;
                    }

                    double new_montant = montant_investor - numberAmount ;
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



            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur sell");
            alert.setHeaderText("Wallet ou Cryptoccurency not selected");
            alert.setContentText(null);
            alert.showAndWait();
        }
    }

    public void sell_crypto(){

    }

}
