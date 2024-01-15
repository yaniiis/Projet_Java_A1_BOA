package com.example.wallet_boa.controleur;

import com.example.wallet_boa.modele.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class ControllerWallet {

    private Investor investor;
    private Blockchaine blockchain;
    private int nb_wallet = 0;
    private int indice_wallet_layout;
    private double values_total_wallet;
    private ArrayList<Wallet> list_wallet;

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
    ImageView imageView;
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
    Label label_solde;
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
    @FXML
    Pane pane_crypto_1;
    @FXML
    Pane pane_crypto_2;
    @FXML
    Pane pane_crypto_3;
    @FXML
    Pane pane_crypto_4;
    @FXML
    Pane pane_crypto_5;
    @FXML
    Label label_pane1;
    @FXML
    Label montant_pane1;
    @FXML
    Label label_pane2;
    @FXML
    Label montant_pane2;
    @FXML
    Label label_pane3;
    @FXML
    Label montant_pane3;
    @FXML
    Label label_pane4;
    @FXML
    Label montant_pane4;
    @FXML
    Label label_pane5;
    @FXML
    Label montant_pane5;
    @FXML
    Button btn_new_wallet;
    @FXML
    Button btn_clone_wallet;
    @FXML
    Label label_wallet_name;
    @FXML
    VBox vbox_value_wallet;
    @FXML
    VBox vbox_name_wallet;
    @FXML
    HBox hbox_walet_list_values;
    @FXML
    HBox hbox_btn_action_wallet;
    @FXML
    LineChart<Number, Number> lineChart;
    @FXML
    NumberAxis yAxis;
    @FXML
    Button btn_impot;
    @FXML
    VBox hbox_insert_montant;
    @FXML
    Button btn_profit;
    @FXML
    NumberAxis xAxis;
    @FXML
    Button btn_conseil;
    @FXML
    TextField txt_montant_payment;
    @FXML
    TextField txt_name_payment;
    @FXML
    TextField txt_carde_payment_un;
    @FXML
    TextField txt_carde_payment_deux;
    @FXML
    TextField txt_carde_payment_trois;
    @FXML
    TextField txt_carde_payment_quatre;
    @FXML
    TextField txt_code_payment;
    @FXML
    TextField txt_annee_retraite;
    @FXML
    TextField txt_montant_ptf;
    @FXML
    VBox Vbox_retraite;









    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */

    public void ajoutWallet(){
        list_wallet = new ArrayList<>();
        ArrayList<Wallet> wallets= this.investor.getList_wallet();
        int i =1;
        for(Wallet wallet : wallets) {
            create_walet(wallet.getName(), wallet.getAmount(), i, wallet.getClone());
            list_wallet.add(wallet);
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
        IntefaceFeatures.layout_help(investor, blockchain);
    }
    public void l_action() throws Exception{
        IntefaceFeatures.layout_stock(investor, blockchain);
    }
    public void l_transaction() throws Exception{
        IntefaceFeatures.layout_transaction(investor, blockchain);
    }
    public void l_crytpo() throws Exception{
        IntefaceFeatures.layout_crypto(investor, blockchain);
    }
    public void l_account() throws Exception{
        IntefaceFeatures.layout_account(investor, blockchain);
    }
    public void l_accueil() throws Exception{
        IntefaceFeatures.layout_accueil(investor, blockchain);
    }
    public void setInvestor(Investor investor, Blockchaine blockchaine) throws Exception {
        /*
            Affection d'un objet Investor
         */
        Image image = new Image(new File("src/main/resources/galerie/logo.png").toURI().toString());
        imageView.setImage(image);


        this.investor = investor;
        this.blockchain = blockchaine;
        label_name.setText(investor.getName());
        ajoutWallet();
        remplirCrypto();
        String solde = "Solde : " + IntefaceFeatures.compter_montant(investor) + " $";
        label_solde.setText(solde);
    }

    @FXML
    public void layout_new_wallet(){
        if(this.nb_wallet>9){
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error");
            alert2.setHeaderText(null);
            alert2.setContentText("Le nombre de wallet maximum est atteint \n");
            alert2.showAndWait();
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
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error");
            alert2.setHeaderText(null);
            alert2.setContentText("Le nombre de wallet maximum est atteint \n");
            alert2.showAndWait();
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Create Wallet");
        alert.setHeaderText(null);
        alert.setContentText("Creation done \n");
        alert.showAndWait();

    }

    @FXML
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


                    java.util.Date dateActuelle = new java.util.Date();
                    Date dateSQL = new Date(dateActuelle.getTime());

                    Cryptocurrency cryptocurrency = list_value_clone(id_list_valeur);

                    Wallet wallet = new Wallet(0,name, dateSQL, description, amount, true,cryptocurrency);

                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Clone Wallet");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Clone done \n");
                    alert2.showAndWait();

                    insert_wallet_bdd(wallet, id_list_valeur);

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

    public void charger_graphique(List<String> symbolss) throws Exception {

        String apiKey = "O2VSXG62XNBFFJDL";

        List<Double> closingPricesSum = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            closingPricesSum.add(0.0);
        }

        for (String symbol : symbolss) {
            String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&outputsize=compact&apikey=" + apiKey;
            System.out.println(apiUrl);

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

            int count = 0;
            for (Map.Entry<String, HashMap<String, String>> entry : sortedData.entrySet()) {
                if (count == 10) {
                    break;
                }
                double closePrice = Double.parseDouble(entry.getValue().get("4. close"));
                closingPricesSum.set(count, closingPricesSum.get(count) + closePrice);
                count++;
            }
        }

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("AAPL & BTC Sum");
        for (int i = 0; i < closingPricesSum.size(); i++) {
            series.getData().add(new XYChart.Data<>(i + 1, closingPricesSum.get(i)));
        }

        Platform.runLater(() -> {
            lineChart.getData().add(series);
        });

        xAxis.setLabel("Daily ");
    }


    public void lancement_wallet(List<String> list_crypto) throws Exception {
        vbox_wallet.setVisible(false);
        hbox_crypto.setVisible(false);
        btn_new_wallet.setVisible(false);
        hbox_crypto.setVisible(false);
        btn_clone_wallet.setVisible(false);
        hbox_walet_list_values.setVisible(true);
        hbox_btn_action_wallet.setVisible(true);
        investor.getList_wallet();
        label_wallet_name.setText(list_wallet.get(indice_wallet_layout).getName());
        charger_graphique(list_crypto);

    }

    @FXML
    private void handlePaneClick1(MouseEvent event) throws Exception {
        List<String> list_crypto_presente;
        if (event.getSource() == pane_1) {
            indice_wallet_layout = 0;
            list_crypto_presente = compte_total(list_wallet.get(0));
            lancement_wallet(list_crypto_presente);
        }
    }

    @FXML
    private void handlePaneClick2(MouseEvent event) throws Exception {
        List<String> list_crypto_presente;

        if (event.getSource() == pane_2) {
            indice_wallet_layout = 1;
            list_crypto_presente = compte_total(list_wallet.get(1));
            lancement_wallet(list_crypto_presente);
        }
    }

    @FXML
    private void handlePaneClick3(MouseEvent event) throws Exception {
        List<String> list_crypto_presente;

        if (event.getSource() == pane_3) {
            indice_wallet_layout = 2;
            list_crypto_presente = compte_total(list_wallet.get(2));
            lancement_wallet(list_crypto_presente);
        }
    }
    @FXML
    private void handlePaneClick4(MouseEvent event) throws Exception {
        List<String> list_crypto_presente;

        if (event.getSource() == pane_4) {
            indice_wallet_layout = 3;
            list_crypto_presente = compte_total(list_wallet.get(3));
            lancement_wallet(list_crypto_presente);
        }
    }
    @FXML
    private void handlePaneClick5(MouseEvent event) throws Exception {
        List<String> list_crypto_presente;

        if (event.getSource() == pane_5) {
            indice_wallet_layout = 4;
            list_crypto_presente = compte_total(list_wallet.get(4));
            lancement_wallet(list_crypto_presente);        }
    }
    @FXML
    private void handlePaneClick6(MouseEvent event) throws Exception {
        List<String> list_crypto_presente;

        if (event.getSource() == pane_6) {
            indice_wallet_layout = 5;
            list_crypto_presente = compte_total(list_wallet.get(5));
            lancement_wallet(list_crypto_presente);        }
    }
    @FXML
    private void handlePaneClick7(MouseEvent event) throws Exception {
        List<String> list_crypto_presente;

        if (event.getSource() == pane_7) {
            indice_wallet_layout = 6;
            list_crypto_presente = compte_total(list_wallet.get(6));
            lancement_wallet(list_crypto_presente);
        }
    }
    @FXML
    private void handlePaneClick8(MouseEvent event) throws Exception {
        List<String> list_crypto_presente;

        if (event.getSource() == pane_8) {
            indice_wallet_layout = 7;
            list_crypto_presente = compte_total(list_wallet.get(7));
            lancement_wallet(list_crypto_presente);
        }
    }
    @FXML
    private void handlePaneClick9(MouseEvent event) throws Exception {
        List<String> list_crypto_presente;

        if (event.getSource() == pane_9) {
            indice_wallet_layout = 8;
            list_crypto_presente = compte_total(list_wallet.get(8));
            lancement_wallet(list_crypto_presente);        }
    }


    public void remplirCrypto() throws Exception {

        ArrayList<String> list_value = new ArrayList<String>(Arrays.asList("BTC", "ETH", "BNB", "ADA", "SOL", "XRP", "DOT", "DOGE", "AVAX", "LINK"));
        Random rand = new Random();
        int randomInt;

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
            String price_ = json.getString("price");
            String name = list_value.get(randomInt);
            String price = formatPrice(price_);

            switch (i){
                case 0:
                    label_pane1.setText(name);
                    montant_pane1.setText(price);
                    break;
                case 1:
                    label_pane2.setText(name);
                    montant_pane2.setText(price);
                    break;
                case 2:
                    label_pane3.setText(name);
                    montant_pane3.setText(price);
                    break;
                case 3:
                    label_pane4.setText(name);
                    montant_pane4.setText(price);
                    break;
                case 4:
                    label_pane5.setText(name);
                    montant_pane5.setText(price);
                    break;
            }

        }

    }



    public List<String> compte_total(Wallet wallet) throws Exception {

        Cryptocurrency cryptocurrency = wallet.getList_value();
        double total_montant = 0;
        double montant;
        String total_formater;
        List<String> list_crypto_presente = new ArrayList<>();


        if(cryptocurrency.getADA()!=0){

            double valeur_dollar = cryptocurrency.getADA();
            montant = recupere_valeur_crypto("ADA") * valeur_dollar;
            total_montant += montant ;
            Label label = new Label("ADA");
            montant = IntefaceFeatures.cut_nombre(montant);
            String monString = String.valueOf(montant);
            Label montantt = new Label(monString);
            list_crypto_presente.add("ADA");
            vbox_value_wallet.getChildren().add(label);
            vbox_name_wallet.getChildren().add(montantt);

        }
        if(cryptocurrency.getBTC()!=0){
            double valeur_dollar = cryptocurrency.getBTC();
            montant = recupere_valeur_crypto("BTC") * valeur_dollar;
            total_montant += montant ;
            Label label = new Label("BTC");
            montant = IntefaceFeatures.cut_nombre(montant);
            String monString = String.valueOf(montant);
            Label montantt = new Label(monString);
            vbox_value_wallet.getChildren().add(label);
            vbox_name_wallet.getChildren().add(montantt);
            list_crypto_presente.add("BTC");
        }
        if(cryptocurrency.getBNB()!=0){
            double valeur_dollar = cryptocurrency.getBNB();
            montant = recupere_valeur_crypto("BNB") * valeur_dollar;
            total_montant += montant ;
            Label label = new Label("BNB");
            montant = IntefaceFeatures.cut_nombre(montant);
            String monString = String.valueOf(montant);
            Label montantt = new Label(monString);
            vbox_value_wallet.getChildren().add(label);
            vbox_name_wallet.getChildren().add(montantt);
            list_crypto_presente.add("BNB");

        }
        if(cryptocurrency.getETH()!=0){
            double valeur_dollar = cryptocurrency.getETH();
            montant = recupere_valeur_crypto("ETH") * valeur_dollar;
            total_montant += montant ;
            Label label = new Label("ETH");
            montant = IntefaceFeatures.cut_nombre(montant);
            String monString = String.valueOf(montant);
            Label montantt = new Label(monString);
            vbox_value_wallet.getChildren().add(label);
            vbox_name_wallet.getChildren().add(montantt);
            list_crypto_presente.add("ETH");
        }
        if(cryptocurrency.getSOL()!=0){
            double valeur_dollar = cryptocurrency.getSOL();
            montant = recupere_valeur_crypto("SOL") * valeur_dollar;
            total_montant += montant ;
            Label label = new Label("SOL");
            montant = IntefaceFeatures.cut_nombre(montant);
            String monString = String.valueOf(montant);
            Label montantt = new Label(monString);
            vbox_value_wallet.getChildren().add(label);
            vbox_name_wallet.getChildren().add(montantt);
            list_crypto_presente.add("SOL");
        }
        if(cryptocurrency.getXRP()!=0){
            double valeur_dollar = cryptocurrency.getXRP();
            montant = recupere_valeur_crypto("XRP") * valeur_dollar;
            total_montant += montant ;
            Label label = new Label("XRP");
            montant = IntefaceFeatures.cut_nombre(montant);
            String monString = String.valueOf(montant);
            Label montantt = new Label(monString);
            vbox_value_wallet.getChildren().add(label);
            vbox_name_wallet.getChildren().add(montantt);
            list_crypto_presente.add("XRP");
        }
        if(cryptocurrency.getDOT()!=0){
            double valeur_dollar = cryptocurrency.getDOT();
            montant = recupere_valeur_crypto("DOT") * valeur_dollar;
            total_montant += montant ;
            Label label = new Label("DOT");
            montant = IntefaceFeatures.cut_nombre(montant);
            String monString = String.valueOf(montant);
            Label montantt = new Label(monString);
            vbox_value_wallet.getChildren().add(label);
            vbox_name_wallet.getChildren().add(montantt);
            list_crypto_presente.add("DOT");
        }
        if(cryptocurrency.getDOGE()!=0){
            double valeur_dollar = cryptocurrency.getDOGE();
            montant = recupere_valeur_crypto("DOGE") * valeur_dollar;
            total_montant += montant ;
            Label label = new Label("DOGE");
            montant = IntefaceFeatures.cut_nombre(montant);
            String monString = String.valueOf(montant);
            Label montantt = new Label(monString);
            vbox_value_wallet.getChildren().add(label);
            vbox_name_wallet.getChildren().add(montantt);
            list_crypto_presente.add("DOGE");
        }

        if(cryptocurrency.getAVAX()!=0){
            double valeur_dollar = cryptocurrency.getAVAX();
            montant = recupere_valeur_crypto("AVAX") * valeur_dollar;
            total_montant += montant ;
            Label label = new Label("AVAX");
            montant = IntefaceFeatures.cut_nombre(montant);
            String monString = String.valueOf(montant);
            Label montantt = new Label(monString);
            vbox_value_wallet.getChildren().add(label);
            vbox_name_wallet.getChildren().add(montantt);
            list_crypto_presente.add("AVAX");
        }
        if(cryptocurrency.getLINK()!=0){
            double valeur_dollar = cryptocurrency.getLINK();
            montant = recupere_valeur_crypto("LINK") * valeur_dollar;
            total_montant += montant ;
            Label label = new Label("LINK");
            montant = IntefaceFeatures.cut_nombre(montant);
            String monString = String.valueOf(montant);
            Label montantt = new Label(monString);
            vbox_value_wallet.getChildren().add(label);
            vbox_name_wallet.getChildren().add(montantt);
            list_crypto_presente.add("LINK");
        }
        total_formater = formatPrice(String.valueOf(total_montant));
        lineChart.setTitle(total_formater);
        values_total_wallet = total_montant;
        return list_crypto_presente;
    }

    public double recupere_valeur_crypto(String crypto) throws Exception {

        double montant_dollar = 0;
        String api_url = "https://api.binance.com/api/v3/ticker/price?symbol=" + crypto + "USDT";
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
        montant_dollar = Double.parseDouble(price_);

        return montant_dollar;

    }

    public String formatPrice(String price) {
        try {
            double priceValue = Double.parseDouble(price);
            return String.format("%.2f", priceValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void inserer_montant_layout(){
        lineChart.setVisible(false);
        hbox_insert_montant.setVisible(true);
    }

    public void close_insert_montant(){
        lineChart.setVisible(true);
        hbox_insert_montant.setVisible(false);
        txt_montant_payment.setText("");
        txt_name_payment.setText("");
        txt_carde_payment_un.setText("");
        txt_carde_payment_deux.setText("");
        txt_carde_payment_trois.setText("");
        txt_carde_payment_quatre.setText("");
        txt_code_payment.setText("");
    }

    @FXML
    public void insert_payment() {

        String montant = txt_montant_payment.getText();
        String name = txt_name_payment.getText();
        String carde_un = txt_carde_payment_un.getText();
        String carde_deux = txt_carde_payment_deux.getText();
        String carde_trois = txt_carde_payment_trois.getText();
        String carde_quatre = txt_carde_payment_quatre.getText();
        String code = txt_code_payment.getText();

        int carde_un_, carde_deux_, carde_trois_, carde_quatre_, code_, montant_int;

        if (montant.isEmpty() || name.isEmpty() || carde_un.isEmpty() || carde_deux.isEmpty() || carde_trois.isEmpty() || carde_quatre.isEmpty() || code.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Complete all fields");
            alert.setContentText(null);
            alert.showAndWait();
        } else {
            try {
                montant_int = Integer.parseInt(montant);

                if (carde_un.length() == 4) {
                    carde_un_ = Integer.parseInt(carde_un);
                } else {
                    throw new NumberFormatException();
                }

                if (carde_deux.length() == 4) {
                    carde_deux_ = Integer.parseInt(carde_deux);
                } else {
                    throw new NumberFormatException();
                }

                if (carde_trois.length() == 4) {
                    carde_trois_ = Integer.parseInt(carde_trois);
                } else {
                    throw new NumberFormatException();
                }

                if (carde_quatre.length() == 4) {
                    carde_quatre_ = Integer.parseInt(carde_quatre);
                } else {
                    throw new NumberFormatException();
                }

                if (code.length() == 3) {
                    code_ = Integer.parseInt(code);
                } else {
                    throw new NumberFormatException();
                }

                update_montant(montant);

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Verify your informations");
                alert.setContentText(null);
                alert.showAndWait();
            }
        }


    }

    public void update_montant(String montant){

        String updateQuery = "UPDATE Wallet SET amount = ? WHERE id_wallet = ?;";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";

        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
        ) {
            ArrayList<Wallet> list_wallet = investor.getList_wallet();
            Wallet wallet = list_wallet.get(indice_wallet_layout);

            updateStmt.setDouble(1, Double.parseDouble(montant));
            updateStmt.setInt(2, wallet.getId_wallet());

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Insertion");
                alert.setHeaderText("Payment validated");
                alert.setContentText(null);
                alert.showAndWait();
            }
            Double new_amount_ = investor.getList_wallet().get(indice_wallet_layout).getAmount() + Double.parseDouble(montant);
            investor.getList_wallet().get(indice_wallet_layout).setAmount(new_amount_);
            close_insert_montant();
            String solde = "Solde : " + IntefaceFeatures.compter_montant(investor) + " $";
            label_solde.setText(solde);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void retraite_layout(){
        lineChart.setVisible(false);
        hbox_insert_montant.setVisible(false);
        Vbox_retraite.setVisible(true);


    }


    @FXML
    public void calcul_retraite() {

        String txt_annee_retrait = txt_annee_retraite.getText();
        String txt_montant_pt = txt_montant_ptf.getText();
        int montant_final;

        if (txt_annee_retrait.isEmpty() || txt_montant_pt.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs");
        } else if (!txt_montant_pt.matches("\\d*(\\.\\d*)?")) {
            System.out.println("Veuillez rentrer une valeur correcte dans le montant");
        } else if (!txt_annee_retrait.matches("\\d*")) {
            System.out.println("Veuillez entrer un nombre entier dans le nombre d'années");
        } else {

            double montant = Double.parseDouble(txt_montant_pt);
            double annee = Double.parseDouble(txt_annee_retrait);

            montant_final = (int) ((annee * 12 * montant * 1.001 * 1.03 * 0.98 + values_total_wallet ) * 0.98);

            System.out.println(montant_final);


        }
    }



}
