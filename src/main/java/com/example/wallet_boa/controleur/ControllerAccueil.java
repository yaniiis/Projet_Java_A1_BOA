package com.example.wallet_boa.controleur;

import com.example.wallet_boa.modele.Blockchaine;
import com.example.wallet_boa.modele.Evenements;
import com.example.wallet_boa.modele.Investor;
import com.example.wallet_boa.modele.Wallet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class ControllerAccueil {

    private Investor investor;
    private Blockchaine blockchain;

    @FXML
    Label label_name;
    @FXML
    Button btn_wallet;
    @FXML
    Button btn_transaction;
    @FXML
    ImageView imageView;
    @FXML
    Button btn_action;
    @FXML
    Button btn_account;
    @FXML
    Button btn_cryptocurrency;
    @FXML
    Label label_welcolm;
    @FXML
    Label label_balance;
    @FXML
    ImageView image1;
    @FXML
    ImageView image2;
    @FXML
    ImageView image3;
    @FXML
    ImageView image4;
    @FXML
    ImageView image5;
    @FXML
    ImageView image6;
    @FXML
    Label label_cac;
    @FXML
    VBox vbox_wallet;
    @FXML
    Label label_solde;

    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */


    public void setInvestor(Investor investor, Blockchaine blockchaine) throws Exception {
        /*
            Affection d'un objet Investor
         */

        Evenements.lance();

        Image image = new Image(new File("src/main/resources/galerie/logo.png").toURI().toString());
        imageView.setImage(image);
        this.investor = investor;
        this.blockchain = blockchaine;
        label_name.setText(investor.getName());
        String title = "Welcolm " + investor.getSurname() + " " + investor.getName();
        label_welcolm.setText(title);
        String solde = "Solde : " + IntefaceFeatures.compter_montant(investor) + " $";
        label_solde.setText(solde);
        remplir_image();
        remplir_wallet();
    }

    public void remplir_wallet(){

        double total = 0;
        for (Wallet wallet : investor.getList_wallet()) {
            total += wallet.getAmount();

            Label i = new Label(wallet.getName());
            vbox_wallet.getChildren().add(i);

        }
        String balance = "Your balance : " + total + " $";
        label_balance.setText(balance);



    }
    public void remplir_image() throws IOException {
        List<String> list_crypto = new ArrayList<>();

        list_crypto.add("btc.svg");
        list_crypto.add("eth.svg");
        list_crypto.add("bnb.png");
        list_crypto.add("ada.png");
        list_crypto.add("sol.png");
        list_crypto.add("xrp.png");
        list_crypto.add("dot.png");
        list_crypto.add("doge.svg");
        list_crypto.add("avax.png");
        list_crypto.add("link.png");

        List<String> list_crypto_ = new ArrayList<>();

        list_crypto_.add("BTC");
        list_crypto_.add("ETH");
        list_crypto_.add("BNB");
        list_crypto_.add("ADA");
        list_crypto_.add("SOL");
        list_crypto_.add("XRP");
        list_crypto_.add("DOGE");
        list_crypto_.add("DOGE");
        list_crypto_.add("AVAX");
        list_crypto_.add("LINK");

        Random random = new Random();

        int randomIndex = random.nextInt(list_crypto.size());

        String imageUrl = "src/main/resources/galerie/" + list_crypto.get(randomIndex);
        Image imageun = new Image(new File(imageUrl).toURI().toString());
        image1.setImage(imageun);
        list_crypto.remove(randomIndex);


        randomIndex = random.nextInt(list_crypto.size());

        String imageUrl1 = "src/main/resources/galerie/" + list_crypto.get(randomIndex);
        Image imagedeux = new Image(new File(imageUrl1).toURI().toString());
        image2.setImage(imagedeux);
        list_crypto.remove(randomIndex);

        randomIndex = random.nextInt(list_crypto.size());
        String imageUrl2 = "src/main/resources/galerie/" + list_crypto.get(randomIndex);
        Image imagetrois = new Image(new File(imageUrl2).toURI().toString());
        image3.setImage(imagetrois);
        list_crypto.remove(randomIndex);

        randomIndex = random.nextInt(list_crypto.size());
        String imageUrl3 = "src/main/resources/galerie/" + list_crypto.get(randomIndex);
        Image imagequatre = new Image(new File(imageUrl3).toURI().toString());
        image4.setImage(imagequatre);
        list_crypto.remove(randomIndex);

        randomIndex = random.nextInt(list_crypto.size());
        String imageUrl4 = "src/main/resources/galerie/" + list_crypto.get(randomIndex);
        Image imagecinq = new Image(new File(imageUrl4).toURI().toString());
        image5.setImage(imagecinq);
        list_crypto.remove(randomIndex);

        randomIndex = random.nextInt(list_crypto.size());
        String imageUrl5 = "src/main/resources/galerie/" + list_crypto.get(randomIndex);
        Image imagesix = new Image(new File(imageUrl5).toURI().toString());
        image6.setImage(imagesix);
        list_crypto.remove(randomIndex);


        randomIndex = random.nextInt(9);


        String api_url = "https://api.binance.com/api/v3/ticker/price?symbol=" + list_crypto_.get(randomIndex) + "USDT";
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
        String price_ = json.getString("price");
        String t = IntefaceFeatures.formatPrice(price_);
        String l = "" + list_crypto_.get(randomIndex) + " : " + t + " $";

        label_cac.setText(l);


    }



    public void l_logout() throws IOException {
        IntefaceFeatures.log_out();
    }
    public void l_help() throws Exception{

        IntefaceFeatures.layout_help(this.investor, this.blockchain);
    }
    public void l_wallet() throws Exception{
        IntefaceFeatures.layout_wallet(this.investor, this.blockchain);
    }
    public void l_action() throws Exception{
        IntefaceFeatures.layout_stock(this.investor, this.blockchain);
    }
    public void l_transaction() throws Exception{
        IntefaceFeatures.layout_transaction(this.investor, this.blockchain);
    }
    public void l_crytpo() throws Exception{
        IntefaceFeatures.layout_crypto(this.investor, this.blockchain);
    }
    public void l_account() throws Exception{
        IntefaceFeatures.layout_account(this.investor, blockchain);
    }



}
