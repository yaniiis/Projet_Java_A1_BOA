package com.example.wallet_boa.controleur;
import javafx.fxml.FXML;
import javafx.scene.media.*;

import java.io.File;

public class ControllerPresentation {

    @FXML
    MediaView mediaView;

    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */


    public void initialize() {
        /*
        Cette fonction permet de charger une video automatiquement au chargement de la page
         */

        Media media = new Media(new File("src/main/resources/galerie/videotest.mp4").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.play();
    }

    @FXML
    public void l_connection() throws Exception {
        IntefaceFeatures.layout_connexion();
    }

    @FXML
    public void l_inscription() throws Exception {
        IntefaceFeatures.layout_inscription();
    }
}
