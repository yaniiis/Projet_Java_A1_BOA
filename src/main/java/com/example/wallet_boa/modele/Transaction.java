package com.example.wallet_boa.modele;

import com.example.wallet_boa.controleur.IntefaceFeatures;

import java.security.MessageDigest;

public class Transaction {

    private String transactionId;
    private Wallet originWallet;
    private String value;
    private double amount;

    public Transaction(Wallet sender, String value, double amount){
        this.transactionId = calculateTransactionHash();
        this.originWallet = sender;
        this.value = value;
        this.amount = amount;
    }


    public Transaction(String transactionId, Wallet sender, String value, double amount){
        this.transactionId = transactionId;
        this.originWallet = sender;
        this.value = value;
        this.amount = amount;
    }


    public String calculateTransactionHash() {
        String dataToHash = String.valueOf(IntefaceFeatures.random_id());
        MessageDigest digest;
        byte[] bytes;

        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes("UTF-8"));
        } catch (Exception ex) {
            throw new RuntimeException("Erreur lors du calcul du hash", ex);
        }

        StringBuilder buffer = new StringBuilder();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }

        return buffer.toString();
    }


    public Wallet getOriginWallet() {
        return originWallet;
    }

    public void setOriginWallet(Wallet originWallet) {
        this.originWallet = originWallet;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
