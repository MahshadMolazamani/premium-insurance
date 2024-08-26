package com.insurance.premium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Die Hauptklasse der Insurance Premium Application.
 * Dies ist der Einstiegspunkt der Spring Boot-Anwendung.
 */
@SpringBootApplication
public class PremiumApplication {

    /**
     * Die main-Methode startet die Spring Boot-Anwendung.
     *
     * @param args Kommandozeilenargumente, die beim Starten der Anwendung übergeben werden.
     */
    public static void main(String[] args) {
        SpringApplication.run(PremiumApplication.class, args);
    }

}
