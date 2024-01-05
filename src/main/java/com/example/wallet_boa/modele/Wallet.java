package com.example.wallet_boa.modele;

import java.time.LocalDate;

public class Wallet {
    private String name;
    private LocalDate date;
    private String description;

    public Wallet(String name, LocalDate date, String description){
        this.name =name;
        this.date = date;
        this.description = description;
    }

}
