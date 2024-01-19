package com.example.wallet_boa.controleur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.*;

import java.io.File;

public class ControllerPresentation {

    @FXML
    MediaView mediaView;
    @FXML
    ImageView imageView;

    /*
        Toutes les fonctions commencant par l_
        Permettent la redirection vers une autre page
    */

    public void initialize() {
        /*
        Cette fonction permet de charger une video automatiquement au chargement de la page
         */

        Media media = new Media(new File("src/main/resources/galerie/video.mp4").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.play();
        Image image = new Image(new File("src/main/resources/galerie/logo.png").toURI().toString());
        imageView.setImage(image);


    }

    @FXML
    public void back() throws Exception {
        IntefaceFeatures.layout_connexion();
    }


}
