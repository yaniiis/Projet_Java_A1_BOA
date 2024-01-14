package com.example.wallet_boa.modele;

import java.util.ArrayList;
import java.util.List;

public class Blockchaine {

    private static List<Block> blockchain;

    public Blockchaine(List<Block> blockchain){

        this.blockchain = new ArrayList<>(blockchain);

    }

    public void addBlock(Block block){
        blockchain.add(block);
    }
    public Block getLastBlock() {
        return blockchain.get(blockchain.size() - 1);
    }
    
}
