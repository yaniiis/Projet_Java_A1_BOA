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
    public void l_crytpo() throws Exception{
        Investor investor = new Investor(this.investor.getName(),this.investor.getSurname(),this.investor.getEmail(),this.investor.getPhone_number(),this.investor.getId());
        IntefaceFeatures.layout_crypto(investor);
    }
    public void l_account() throws Exception{
        Investor investor = new Investor(this.investor.getName(),this.investor.getSurname(),this.investor.getEmail(),this.investor.getPhone_number(),this.investor.getId());
        IntefaceFeatures.layout_account(investor);
    }



}
