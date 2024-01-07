package com.example.wallet_boa.modele;

import java.sql.Date;
import java.time.LocalDate;

public class Wallet {
    private int id_wallet;
    private String name;
    private Date date;
    private String description;
    private double amount;
    private int id_investor;
    private int id_list_valeur;


    public Wallet(String name, Date date, String description, double amount, int id_wallet, int id_investor, int id_list_valeur){
        this.name =name;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.id_investor = id_investor;
        this.id_list_valeur = id_list_valeur;
        this.id_wallet = id_wallet;
    }

    public Wallet(String name, Date date, String description, double amount){
        this.name =name;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public String getName(){
        return this.name;
    }
    public Date getDate(){
        return this.date;
    }
    public String getDescription(){
        return this.description;
    }
    public double getAmount(){
        return this.amount;
    }
    public void setId_wallet(int id_wallet){
        this.id_wallet = id_wallet;
    }
    public void setId_list_valeur(int id_list_valeur){
        this.id_list_valeur = id_list_valeur;
    }
    public void setId_investor(int id_investor){
        this.id_investor = id_investor;
    }

}
