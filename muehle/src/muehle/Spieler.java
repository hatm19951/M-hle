package muehle;


public class Spieler {
    private String name;       // Name des Spielers
    private char farbe;        // 'r' für Rot oder 'b' für Blau
    public int verbleibendeSteine; // Anzahl der Steine, die der Spieler noch setzen kann
    public int gesetzteSteine;
    private int maximaleSteine = 9;// Anzahl der bereits gesetzten Steine

    public Spieler(String name, char farbe) {
        this.name = name;
        this.farbe = farbe;
        this.verbleibendeSteine = 9; // Jeder Spieler hat 9 Steine zu Beginn
        this.gesetzteSteine = 0;
    }

    // Getter-Methoden
    public String getName() {
        return name;
    }

    public char getFarbe() {
        return farbe;
    }

    public  int getVerbleibendeSteine() {
        return verbleibendeSteine;
    }

    public int getGesetzteSteine() {
        return gesetzteSteine;
    }

    // Methode, um einen Stein zu setzen
    public void steinGesetzt() {
        if (verbleibendeSteine > 0) {
            verbleibendeSteine--;
            gesetzteSteine++;
        } else {
            System.out.println("Keine Steine mehr zum Setzen übrig.");
            
        }
    }
    
    public boolean keineSteineMehr() {
        return gesetzteSteine >= maximaleSteine;
    }

    // Methode, um einen Stein zu verlieren (z.B. wenn der Gegner eine Mühle bildet)
    public void steinVerloren() {
        if (gesetzteSteine > 0) {
            gesetzteSteine--;
        } else {
            System.out.println("Keine gesetzten Steine zum Verlieren.");
        }
    }
    
    public int getAktuelleSteineAufFeld(Spielregeln spielregeln) {
        int count = 0;
        for (Stein stein : spielregeln.getSteine()) {
            if (stein != null && stein.getFarbe() == this.farbe) {
                count++;
            }
        }
        return count;
    }
}
