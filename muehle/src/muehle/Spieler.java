package muehle;

import java.util.ArrayList;
import java.util.List;

public class Spieler {
    private String name;
    private char farbe; // 'r' für Rot, 'b' für Blau
    public List<Stein> steine;

    public Spieler(String name, char farbe) {
        this.name = name;
        this.farbe = farbe;
        this.steine = new ArrayList<Stein>();
    }

    public void steinSetzen(String steinName, int x, int y) {
        // Neuer Stein wird erstellt und der Liste hinzugefügt
        Stein stein = new Stein(steinName, x, y, farbe);
        steine.add(stein);
        System.out.println("Stein gesetzt: Name = " + steinName + ", X = " + x + ", Y = " + y);
    }

    public void steinZiehen(String SteinId, int neuesX, int neuesY) {
        for (Stein stein : steine) {
            if (stein.id.equals(SteinId)) {
                stein.setSteinPosition(neuesX,neuesY);
               // stein.setSteinPositionY(neuesY);
                System.out.println("Stein gezogen: Name = " + SteinId + ", Neues X = " + neuesX + ", Neues Y = " + neuesY);
                break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public char getFarbe() {
        return farbe;
    }

    public List<Stein> getSteine() {
        return steine;
    }
}
