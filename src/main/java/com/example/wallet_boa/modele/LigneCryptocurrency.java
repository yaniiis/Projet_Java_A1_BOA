package com.example.wallet_boa.modele;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LigneCryptocurrency {

    private String nom;
    private double prix;
    private Label variation;
    private Button graphique;

    public LigneCryptocurrency(String nom, double prix, Label variation, Button graphique) {
        this.nom = nom;
        this.prix = prix;
        this.variation = variation;
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

    public Label getVariation() {
        return this.variation;
    }

    public void setVariation(Label variation) {
        this.variation = variation;
    }

}
