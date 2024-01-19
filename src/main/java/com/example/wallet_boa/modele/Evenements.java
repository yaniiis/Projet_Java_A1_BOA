package com.example.wallet_boa.modele;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Evenements<T> {

    private final T data;
    private final String message;

    public Evenements(T data, String message) {
        this.data = data;
        this.message = message;
    }



    private void logMessage() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle((String) data);
            alert.setHeaderText(null);
            alert.setContentText(message);

            alert.showAndWait();
            lance();
        });

    }

    public static void lance() {
        List<String> list_crypto_ = new ArrayList<>();
        list_crypto_.add("BTC");
        list_crypto_.add("ETH");
        list_crypto_.add("BNB");
        list_crypto_.add("ADA");
        list_crypto_.add("SOL");
        list_crypto_.add("XRP");
        list_crypto_.add("DOGE");
        list_crypto_.add("AVAX");
        list_crypto_.add("LINK");

        List<String> list_people = new ArrayList<>();
        list_people.add("Elon Musk");
        list_people.add("Bill Gates");
        list_people.add("Steve Jobs");
        list_people.add("Jeff Bezos");

        ArrayList<String> topCompanies = new ArrayList<>();
        topCompanies.add("Apple");
        topCompanies.add("Microsoft");
        topCompanies.add("Amazon");
        topCompanies.add("Alphabet");
        topCompanies.add("Meta Platforms");
        topCompanies.add("Tesla");

        List<String> cryptoInvestors = new ArrayList<>();
        cryptoInvestors.add("Michael Saylor");
        cryptoInvestors.add("Barry Silbert");
        cryptoInvestors.add("Tim Draper");
        cryptoInvestors.add("Brian Armstrong");

        Random random = new Random();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        int eventType = random.nextInt(5);
        String msg = "";

        switch (eventType) {
            case 0:
                int cryptoIndex = random.nextInt(list_crypto_.size());
                msg = "La valeur de " + list_crypto_.get(cryptoIndex) + " a baissé !";

                Evenements<String> intLogger = new Evenements<>("312", msg);
                random = new Random();
                int intervalMinutes = random.nextInt(4) + 2;
                scheduler.schedule(() -> {
                    intLogger.logMessage();
                }, intervalMinutes, TimeUnit.MINUTES);
                break;
            case 1:
                int cryptoIndex2 = random.nextInt(list_crypto_.size());
                msg = "La valeur de " + list_crypto_.get(cryptoIndex2) + " a augmenté !";

                Evenements<String> intLogger1 = new Evenements<>("444", msg);
                random = new Random();
                int intervalMinutes2 = random.nextInt(4) + 2;
                scheduler.schedule(() -> {
                    intLogger1.logMessage();
                }, intervalMinutes2, TimeUnit.MINUTES);
                break;
            case 2:
                Random random3 = new Random();
                int intervalMinutes3 = random3.nextInt(4);

                msg = list_people.get(intervalMinutes3) + " a posté un tweet sur " + list_crypto_.get(intervalMinutes3);

                Evenements<Integer> intLogger2 = new Evenements<>(123, msg);
                random = new Random();
                int intervalMinut = random.nextInt(4) + 2;
                scheduler.schedule(() -> {
                    intLogger2.logMessage();
                }, intervalMinut, TimeUnit.MINUTES);
                break;
            case 3:
                Random random4 = new Random();
                int intervalMin4 = random4.nextInt(5);
                msg = topCompanies.get(intervalMin4) + " va faire des annonces dans quelques instants";

                Evenements<String> intLogger3 = new Evenements<>("724", msg);
                random = new Random();
                int intervalMinutes4 = random.nextInt(4) + 2;
                scheduler.schedule(() -> {
                    intLogger3.logMessage();
                }, intervalMinutes4, TimeUnit.MINUTES);
                break;
            case 4:
                Random random5 = new Random();
                int ervalMinutes5 = random5.nextInt(4);
                msg = cryptoInvestors.get(ervalMinutes5) + " a posté des conseils sur Youtube";

                Evenements<Double> intLogger4 = new Evenements<>(12.2, msg);
                random = new Random();
                int intervalMinutes5 = random.nextInt(4) + 2;
                scheduler.schedule(() -> {
                    intLogger4.logMessage();
                }, intervalMinutes5, TimeUnit.MINUTES);
                break;
        }
    }
}
