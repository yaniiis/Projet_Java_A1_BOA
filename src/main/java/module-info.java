module com.example.wallet_boa {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wallet_boa to javafx.fxml;
    exports com.example.wallet_boa;
}