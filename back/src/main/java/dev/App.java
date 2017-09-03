package dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application démarrée via Spring Boot.
 */
@SpringBootApplication
public class App {

  /**
   * Démarrage de l'application Web.
   *
   * @param args argument du programme
   */
  public static void main(String[] args) {
    SpringApplication.run(App.class);
  }
}
