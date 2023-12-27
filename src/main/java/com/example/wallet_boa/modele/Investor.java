package com.example.wallet_boa.modele;

public class Investor {

    private String name;
    private String surname;
    private String email;
    private String phone_number;

    public Investor(String name,String surname,String email,String phone_number){
        this.surname = surname;
        this.email = email;
        this.phone_number = phone_number;
        this.name = name;
    }

    public String getName(){
        return name;
    }


    public String getPhone_number(){
        return phone_number;
    }

    public String getEmail(){
        return email;
    }

    public String getSurname(){
        return surname;
    }

}
