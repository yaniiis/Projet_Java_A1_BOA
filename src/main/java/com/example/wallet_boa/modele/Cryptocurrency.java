package com.example.wallet_boa.modele;

import java.time.LocalDate;
import java.util.Date;

public class Cryptocurrency {

    private String name;
    private double value;
    private double part;

    public Cryptocurrency(String name, double value, double part){
        this.name = name;
        this.value = value;
        this.part = part;
    }

}
