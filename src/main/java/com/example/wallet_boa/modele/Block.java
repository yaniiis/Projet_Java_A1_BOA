package com.example.wallet_boa.modele;

import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.*;

public class Block {

    private int index;
    private Timestamp timestamp;
    private final Set<Transaction> transactions ;
    private String previousHash;
    private String hash;

    public Block(int index, String previousHash) {
        this.index = index;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.transactions = new HashSet<>();
        this.previousHash = previousHash;
        this.hash = calculateBlockHash();
    }

    public Block(int index,Timestamp timestamp,Set<Transaction> transactions,String previousHash, String hash) {
        this.index = index;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.hash = hash;
    }

    public String calculateBlockHash() {
        String dataToHash = Integer.toString(index) + Long.toString(timestamp.getTime()) + transactions.toString() + previousHash ;
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


    public Set<Transaction> getTransactions(){
        return this.transactions;
    }

    public String getHash(){
        return this.hash;
    }

    public int getIndex(){
        return this.index;
    }

    public Timestamp getTimestamp(){
        return this.timestamp;
    }

    public String getPreviousHash(){
        return this.previousHash;
    }




}
