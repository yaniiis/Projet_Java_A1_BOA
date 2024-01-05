package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Cryptocurrency;
import com.example.wallet_boa.modele.Investor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.time.LocalDate;

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
    ComboBox<String> md_wallet;
    @FXML
    Pane layout_buy;
    @FXML
    Pane layout_sell;
    @FXML
    Button btn_buy_crypto_connect;
    @FXML
    TextField txt_amount;


    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */

    public void l_logout() throws IOException {
        IntefaceFeatures.log_out();
    }

    public void l_help() throws Exception{
        Investor investor = new Investor(this.investor.getName(),this.investor.getSurname(),this.investor.getEmail(),this.investor.getPhone_number(),this.investor.getId());
        IntefaceFeatures.layout_help(investor);
    }
    public void l_wallet() throws Exception{
        Investor investor = new Investor(this.investor.getName(),this.investor.getSurname(),this.investor.getEmail(),this.investor.getPhone_number(),this.investor.getId());
        IntefaceFeatures.layout_wallet(investor);
    }
    public void l_action() throws Exception{
        Investor investor = new Investor(this.investor.getName(),this.investor.getSurname(),this.investor.getEmail(),this.investor.getPhone_number(),this.investor.getId());
        IntefaceFeatures.layout_stock(investor);
    }
    public void l_transaction() throws Exception{
        Investor investor = new Investor(this.investor.getName(),this.investor.getSurname(),this.investor.getEmail(),this.investor.getPhone_number(),this.investor.getId());
        IntefaceFeatures.layout_transaction(investor);
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
    }
    @FXML
    private void initialize() {

        String a = "Wallet 1";
        md_wallet.getItems().addAll(
                a,
                a,
                a

        );
    }

    @FXML
    public void buy_crypto_layout() {
        layout_buy.setVisible(true);layout_sell.setVisible(false);
    }

    @FXML
    public void sell_crypto_layout(){
        layout_sell.setVisible(true);layout_buy.setVisible(false);
    }

    @FXML
    public void buy_crypto() throws Exception {
        String amount = txt_amount.getText();
        boolean nb = false;
        double numberAmount = 0;

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
            Cryptocurrency cryptocurrency = new Cryptocurrency(selected, numberAmount,part);



        }
    }



}
