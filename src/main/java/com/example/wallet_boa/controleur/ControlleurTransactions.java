package com.example.wallet_boa.controleur;

import com.example.wallet_boa.HelloApplication;
import com.example.wallet_boa.modele.Blockchaine;
import com.example.wallet_boa.modele.Investor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ControlleurTransactions {

    private Investor investor;
    private Blockchaine blockchain;

    @FXML
    Label label_name;
    @FXML
    ImageView imageView;
    @FXML
    Label label_solde;

    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */
    public void l_logout() throws IOException {
        IntefaceFeatures.log_out();
    }
    public void l_accueil() throws Exception{
        IntefaceFeatures.layout_accueil(investor, blockchain);
    }
    public void l_help() throws Exception{
        IntefaceFeatures.layout_help(investor,blockchain);
    }
    public void l_wallet() throws Exception{
        IntefaceFeatures.layout_wallet(investor,blockchain);
    }
    public void l_action() throws Exception{
        IntefaceFeatures.layout_stock(investor,blockchain);
    }
    public void l_crytpo() throws Exception{
        IntefaceFeatures.layout_crypto(investor,blockchain);
    }
    public void l_account() throws Exception{
        IntefaceFeatures.layout_account(investor,blockchain);
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
