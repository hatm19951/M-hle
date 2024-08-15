package spieler;


import muehle.Stein; 
import java.util.ArrayList;
import java.util.List;

public class Spieler {
    private String name;
    private char farbe; 
    private List<Stein> steine;

    public Spieler(String name, char farbe) {
        this.name = name;
        this.farbe = farbe;
        this.steine = new ArrayList<>();
    }

    public void steinSetzen(String steinName, int x, int y) {
        
        Stein stein = new Stein(steinName, x, y, farbe);
        steine.add(stein);
        System.out.println("Stein gesetzt: Name = " + steinName + ", X = " + x + ", Y = " + y);
    }

    public void steinZiehen(String steinName, int neuesX, int neuesY) {
        for (Stein stein : steine) {
            if (stein.name.equals(steinName)) {
                stein.setSteinPositionX(neuesX);
                stein.setSteinPositionY(neuesY);
                System.out.println("Stein gezogen: Name = " + steinName + ", Neues X = " + neuesX + ", Neues Y = " + neuesY);
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
