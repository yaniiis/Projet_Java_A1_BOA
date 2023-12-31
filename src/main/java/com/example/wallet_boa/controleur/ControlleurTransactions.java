package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Investor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControlleurTransactions {

    private Investor investor;
    @FXML
    Label label_name;

    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */
    public void l_logout() throws IOException {
        IntefaceFeatures.log_out();
    }
    public void l_accueil() throws Exception{
        IntefaceFeatures.layout_accueil(investor);
    }
    public void l_help() throws Exception{
        IntefaceFeatures.layout_help(investor);
    }
    public void l_wallet() throws Exception{
        IntefaceFeatures.layout_wallet(investor);
    }
    public void l_action() throws Exception{
        IntefaceFeatures.layout_stock(investor);
    }
    public void l_crytpo() throws Exception{
        IntefaceFeatures.layout_crypto(investor);
    }
    public void l_account() throws Exception{
        IntefaceFeatures.layout_account(investor);
    }

    public void setInvestor(Investor investor) {
        /*
            Affection d'un objet Investor
         */

        this.investor = investor;
        label_name.setText(investor.getName());
    }

}
