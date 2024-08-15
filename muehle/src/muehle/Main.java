package muehle;
import spieler.Spieler;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Erstelle einen neuen Spieler mit Namen und Farbe
        Spieler spieler1 = new Spieler("Spieler 1", 'r');

        // Setze einige Steine für den Spieler
        spieler1.steinSetzen("Stein1", 0, 0);
        spieler1.steinSetzen("Stein2", 1, 1);
        spieler1.steinSetzen("Stein3", 2, 2);

        // Zeige die Positionen der gesetzten Steine an
        System.out.println("Positionen der Steine nach dem Setzen:");
        List<Stein> steine = spieler1.getSteine();
        for (Stein stein : steine) {
            System.out.println("Stein: " + stein.name + " Position: x=" + stein.steinX + ", y=" + stein.steinY);
        }

        // Ziehe einen Stein zu einer neuen Position
        spieler1.steinZiehen("Stein1", 3, 3);

        // Zeige die Positionen der Steine nach dem Ziehen an
        System.out.println("\nPositionen der Steine nach dem Ziehen:");
        for (Stein stein : steine) {
            System.out.println("Stein: " + stein.name + " Position: x=" + stein.steinX + ", y=" + stein.steinY);
        }
    }
}

