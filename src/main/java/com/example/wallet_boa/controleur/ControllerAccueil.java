package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Investor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerAccueil {

    private Investor investor;

    @FXML
    Label label_name;
    @FXML
    Button btn_wallet;
    @FXML
    Button btn_transaction;
    @FXML
    Button btn_action;
    @FXML
    Button btn_account;
    @FXML
    Button btn_cryptocurrency;

    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */


    public void setInvestor(Investor investor) {
        /*
            Affection d'un objet Investor
         */

        this.investor = investor;
        label_name.setText(investor.getName());
    }


    public void l_logout() throws IOException {
        IntefaceFeatures.log_out();
    }
    public void l_help() throws Exception{

        IntefaceFeatures.layout_help(this.investor);
    }
    public void l_wallet() throws Exception{
        IntefaceFeatures.layout_wallet(this.investor);
    }
    public void l_action() throws Exception{
        IntefaceFeatures.layout_stock(this.investor);
    }
    public void l_transaction() throws Exception{
        IntefaceFeatures.layout_transaction(this.investor);
    }
    public void l_crytpo() throws Exception{
        IntefaceFeatures.layout_crypto(this.investor);
    }
    public void l_account() throws Exception{
        IntefaceFeatures.layout_account(this.investor);
    }



}
