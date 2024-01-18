package com.example.wallet_boa.modele;

public class Stock {
    private int id_stock;
    private double AMSZN;
    private double AAPL;
    private double MSFT;
    private double GOOGL;

    public Stock(int id_stock, double AMSZN, double AAPL, double MSFT, double GOOGL) {
        this.id_stock = id_stock;
        this.AMSZN = AMSZN;
        this.AAPL = AAPL;
        this.MSFT = MSFT;
        this.GOOGL = GOOGL;
    }

    public Stock(){

    }
    public int getId_stock() {
        return id_stock;
    }

    public void setId_stock(int id_stock) {
        this.id_stock = id_stock;
    }

    public double getAMSZN() {
        return AMSZN;
    }

    public void setAMSZN(double AMSZN) {
        this.AMSZN = AMSZN;
    }

    public double getAAPL() {
        return AAPL;
    }

    public void setAAPL(double AAPL) {
        this.AAPL = AAPL;
    }

    public double getMSFT() {
        return MSFT;
    }

    public void setMSFT(double MSFT) {
        this.MSFT = MSFT;
    }

    public double getGOOGL() {
        return GOOGL;
    }

    public void setGOOGL(double GOOGL) {
        this.GOOGL = GOOGL;
    }
}
