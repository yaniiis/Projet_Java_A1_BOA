package com.example.wallet_boa.modele;

import javafx.scene.control.Button;

public class LigneCryptocurrency {

    private String nom;
    private double prix;
    private double variation;
    private Button graphique;

    public LigneCryptocurrency(String nom, double prix, double variation, Button graphique) {
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

    public double getVariation() {
        return variation;
    }

    public void setVariation(double variation) {
        this.variation = variation;
    }


    public Button getGraphique() {
        return graphique;
    }

    public void setGraphique(Button graphe) {
        this.graphique = graphe;
    }
}
