package com.example.wallet_boa.modele;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LigneStock {
    private String nom;
    private double prix;
    private Button graphique;

    public LigneStock(String nom, double prix, Button graphique) {
        this.nom = nom;
        this.prix = prix;
        this.graphique = graphique;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Button getGraphique() {
        return graphique;
    }

    public void setGraphique(Button graphe) {
        this.graphique = graphe;
    }

}
