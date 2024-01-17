package com.example.wallet_boa.modele;

import com.example.wallet_boa.controleur.IntefaceFeatures;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Faq {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty question;
    private final SimpleStringProperty answer;

    public Faq(int id, String question, String answer) {
        this.id = new SimpleIntegerProperty(id);
        this.question = new SimpleStringProperty(question);
        this.answer = new SimpleStringProperty(answer);
    }


}
