package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Investor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

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




}
