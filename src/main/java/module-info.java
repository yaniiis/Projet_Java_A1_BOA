module com.example.wallet_boa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;
    requires java.scripting;
    requires binance.connector.java;
    requires org.json;
    requires mysql.connector.java;



    opens com.example.wallet_boa to javafx.fxml;
    exports com.example.wallet_boa;
    exports com.example.wallet_boa.controleur;
    opens com.example.wallet_boa.controleur to javafx.fxml;
    exports com.example.wallet_boa.modele;
    opens com.example.wallet_boa.modele to javafx.fxml;
}