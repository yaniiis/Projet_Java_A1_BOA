package com.example.wallet_boa.modele;

import java.time.LocalDate;
import java.util.Date;

public class Cryptocurrency {

    private int id_crypto;
    private double BTC;
    private double ETH;
    private double BNB;
    private double ADA;
    private double SOL;
    private double XRP;
    private double DOT;
    private double DOGE;
    private double AVAX;
    private double LINK;

    public Cryptocurrency(double BTC,double ETH,double BNB,double ADA,double SOL,double XRP,double DOGE,double DOT,double AVAX,double LINK){
        this.BTC = BTC;
        this.ETH = ETH;
        this.BNB = BNB;
        this.ADA = ADA;
        this.SOL = SOL;
        this.XRP = XRP;
        this.DOT = DOT;
        this.DOGE = DOGE;
        this.AVAX = AVAX;
        this.LINK = LINK;
    }
    public Cryptocurrency(int id_crypto, double BTC,double ETH,double BNB,double ADA,double SOL,double XRP,double DOGE,double DOT,double AVAX,double LINK){
        this.BTC = BTC;
        this.ETH = ETH;
        this.BNB = BNB;
        this.ADA = ADA;
        this.SOL = SOL;
        this.XRP = XRP;
        this.DOT = DOT;
        this.DOGE = DOGE;
        this.AVAX = AVAX;
        this.LINK = LINK;
        this.id_crypto = id_crypto;

    }
    public Cryptocurrency(){

    }
    public double getLINK() {
        return LINK;
    }

    public void setLINK(double LINK) {
        this.LINK = LINK;
    }

    public double getAVAX() {
        return AVAX;
    }

    public void setAVAX(double AVAX) {
        this.AVAX = AVAX;
    }

    public double getDOGE() {
        return DOGE;
    }

    public void setDOGE(double DOGE) {
        this.DOGE = DOGE;
    }

    public double getDOT() {
        return DOT;
    }

    public void setDOT(double DOT) {
        this.DOT = DOT;
    }

    public double getXRP() {
        return XRP;
    }

    public void setXRP(double XRP) {
        this.XRP = XRP;
    }

    public double getSOL() {
        return SOL;
    }

    public void setSOL(double SOL) {
        this.SOL = SOL;
    }

    public double getADA() {
        return ADA;
    }

    public void setADA(double ADA) {
        this.ADA = ADA;
    }

    public double getBNB() {
        return BNB;
    }

    public void setBNB(double BNB) {
        this.BNB = BNB;
    }

    public double getETH() {
        return ETH;
    }

    public void setETH(double ETH) {
        this.ETH = ETH;
    }

    public double getBTC() {
        return BTC;
    }

    public void setBTC(double BTC) {
        this.BTC = BTC;
    }

    public int getId_crypto() {
        return id_crypto;
    }

    public void setId_crypto(int id_crypto) {
        this.id_crypto = id_crypto;
    }
}
