package muehle;

import java.awt.Color;
import java.awt.Graphics;

public class Stein {
    private int x;
    private int y; 
    private char farbe;
    public boolean ausgewählt = false;
    public Color ColorRand = Color.black;
    

    public Stein(int x, int y, char farbe) {
        this.x = x;
        this.y = y;
        this.farbe = farbe;   
    }

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
}
