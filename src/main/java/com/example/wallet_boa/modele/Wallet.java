package com.example.wallet_boa.modele;

import java.sql.Date;
import java.time.LocalDate;

public class Wallet {
    private int id_wallet;
    private String name;
    private Date date;
    private String description;
    private double amount;
    private boolean clone;
    private Cryptocurrency list_value;

    public Wallet(){

    }

    public Wallet(int id_wallet, String name, Date date, String description, double amount, boolean clone, Cryptocurrency list_value){
        this.id_wallet = id_wallet;
        this.name = name;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.clone = clone;
        this.list_value = list_value;
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
    public void setAmount(double amount){this.amount = amount;}
    public void setId_wallet(int id_wallet){
        this.id_wallet = id_wallet;
    }
    public int getId(){return this.id_wallet;}
    public boolean getClone(){
        return this.clone;
    }
    public void setList_value(Cryptocurrency list){
        this.list_value = list;
    }

    public Cryptocurrency getList_value(){
        return this.list_value ;
    }


    public int getId_wallet() {
        return id_wallet;
    }
}
