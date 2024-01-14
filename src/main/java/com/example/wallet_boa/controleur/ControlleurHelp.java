package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Blockchaine;
import com.example.wallet_boa.modele.Investor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControlleurHelp {

    private Investor investor;
    private Blockchaine blockchain;

    @FXML
    Label label_name;
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
    public void l_transaction() throws Exception{
        IntefaceFeatures.layout_transaction(this.investor,blockchain);
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

        this.investor = investor;
        this.blockchain = blockchaine;
        label_name.setText(investor.getName());
    }

}
