package muehle;


public class Spieler {
    private String name;       
    private char farbe;       
    public int verbleibendeSteine; 
    public int gesetzteSteine;
    private int maximaleSteine = 9;

    public Spieler(String name, char farbe) {
        this.name = name;
        this.farbe = farbe;
        this.verbleibendeSteine = 9;
        this.gesetzteSteine = 0;
    }

  
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
