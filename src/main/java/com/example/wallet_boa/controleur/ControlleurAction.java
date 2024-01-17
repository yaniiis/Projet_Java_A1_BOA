package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Blockchaine;
import com.example.wallet_boa.modele.Investor;
import com.example.wallet_boa.modele.LigneCryptocurrency;
import com.example.wallet_boa.modele.LigneStock;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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
        //charger_graphique();
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

}
