module com.example.wallet_boa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;
    requires java.scripting;
<<<<<<< HEAD
    requires org.json;
=======
    requires YahooFinanceAPI;
    requires binance.connector.java;
    requires org.json;

>>>>>>> 71757db5b87f228200e160dd4a72ba892b1ff8c0


    opens com.example.wallet_boa to javafx.fxml;
    exports com.example.wallet_boa;
    exports com.example.wallet_boa.controleur;
    opens com.example.wallet_boa.controleur to javafx.fxml;
    exports com.example.wallet_boa.modele;
    opens com.example.wallet_boa.modele to javafx.fxml;
}