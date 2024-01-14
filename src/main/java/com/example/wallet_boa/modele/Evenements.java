package com.example.wallet_boa.modele;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Evenements<T> {

    private final T data;
    private final String message;
    private final long intervalMinutes;

    public Evenements(T data, String message, long intervalMinutes) {
        this.data = data;
        this.message = message;
        this.intervalMinutes = intervalMinutes;
    }

    public void startLogging() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> logMessage(), 0, intervalMinutes, TimeUnit.MINUTES);
    }

    private void logMessage() {
        System.out.println(message);
    }

    public static void lance() {
        Evenements<Integer> intLogger = new Evenements<>(42, "Message d'entier toutes les 2 minutes", 2);
        intLogger.startLogging();

        Evenements<String> stringLogger = new Evenements<>("Donnée de chaîne", "Message de chaîne toutes les 3 minutes", 3);
        stringLogger.startLogging();
    }
}

