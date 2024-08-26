package muehle;

import java.awt.Color;

public class Stein {
    private int x; // X-Position auf dem Brett
    private int y; // Y-Position auf dem Brett
    private char farbe; // 'r' für Rot (Spieler 1) und 'b' für Blau (Spieler 2)
    public Color ColorRand = Color.black;
    

    public Stein(int x, int y, char farbe) {
        this.x = x;
        this.y = y;
        this.farbe = farbe;
        
    }

    // Getter und Setter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getFarbe() {
        return farbe;
    }

    public Color getRandFarbe () {
		return this.ColorRand;
    	
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void RandzuBlau(){
    	ColorRand = Color.BLUE;
    }
}
