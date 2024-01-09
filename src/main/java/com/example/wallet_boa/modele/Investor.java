package com.example.wallet_boa.modele;

import java.util.ArrayList;

public class Investor {

    private String name;
    private String surname;
    private String email;
    private String phone_number;
    private int id;
    private ArrayList<Wallet> list_wallet;

    public Investor(String name,String surname,String email,String phone_number, int id, ArrayList list_wallet){
        this.surname = surname;
        this.email = email;
        this.phone_number = phone_number;
        this.name = name;
        this.id = id;
        this.list_wallet = list_wallet;
    }

    public void ajouterWallet(Wallet nouveauWallet) {
        list_wallet.add(nouveauWallet);

    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getPhone_number(){
        return this.phone_number;
    }
    public void setPhone_number(String Phone_number){
        this.phone_number = Phone_number;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    public ArrayList<Wallet> getList_wallet(){
        return this.list_wallet;
    }
}
