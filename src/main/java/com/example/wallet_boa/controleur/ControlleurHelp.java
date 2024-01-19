package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Blockchaine;
import com.example.wallet_boa.modele.Faq;
import com.example.wallet_boa.modele.Investor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class ControlleurHelp {

    private Investor investor;
    private Blockchaine blockchain;

    @FXML
    Label label_name;
    @FXML
    ImageView imageView;
    @FXML
    Label label_solde;
    @FXML
    VBox vbox_q_r;
    @FXML
    VBox vbox_q;
    @FXML
    TextArea textAreaAnswer;

    @FXML
    private void initialize() {
        loadFAQData();
    }

    private void loadFAQData() {
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

        try {
            Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB,IntefaceFeatures.MDP_DB);
            String query = "SELECT question, answer FROM faq";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String question = resultSet.getString("question");
                String answer = resultSet.getString("answer");

                Label questionLabel = new Label("Q :     " + question);
                Label answerLabel = new Label("A :      " + answer);
                VBox vbox = new VBox(questionLabel, answerLabel);
                VBox.setMargin(vbox, new Insets(20, 0, 0, 250));

                vbox_q_r.getChildren().add(vbox);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void layout_q(){
        vbox_q_r.setVisible(false);
        vbox_q.setVisible(true);

    }

    public void send_question(){

        String query = "INSERT INTO faq (question, answer) VALUES (?, '')";
        String url = "jdbc:mysql://localhost:3306/database_boa_java?serverTimezone=UTC&useSSL=false";

        try (
                Connection connection = DriverManager.getConnection(url, IntefaceFeatures.NAME_DB, IntefaceFeatures.MDP_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {

            String question= textAreaAnswer.getText();
            preparedStatement.setString(1, question);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("FAQ");
                    alert.setHeaderText("send");
                    alert.setContentText("Votre question a été envoyé, le service BOA se charge de vous répondre au plus vite");
                    alert.showAndWait();

                    String q = "Q :     " + question;
                    Label questionLabel = new Label(q);
                    Label answerLabel = new Label("");
                    VBox vbox = new VBox(questionLabel, answerLabel);
                    VBox.setMargin(vbox, new Insets(20, 0, 0, 250));
                    vbox_q_r.getChildren().add(vbox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        vbox_q_r.setVisible(true);
        vbox_q.setVisible(false);
    }


    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */
    public void l_logout() throws IOException {
        IntefaceFeatures.log_out();
    }
    public void l_wallet() throws Exception{
        IntefaceFeatures.layout_wallet(this.investor, blockchain);
    }
    public void l_action() throws Exception{
        IntefaceFeatures.layout_stock(this.investor,blockchain);
    }
    public void l_crytpo() throws Exception{
        IntefaceFeatures.layout_crypto(investor,blockchain);
    }
    public void l_account() throws Exception{
        IntefaceFeatures.layout_account(this.investor,blockchain);
    }
    public void l_accueil() throws Exception{
        IntefaceFeatures.layout_accueil(this.investor,blockchain);
    }
    public void setInvestor(Investor investor, Blockchaine blockchaine) {
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
    }

}
