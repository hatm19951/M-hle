package muehle;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MuehleFeld extends JPanel {

    private static final int BRETT_GROESSE = 800;
    private static final int RAND = 100;
    private static final int GITTER_GROESSE = BRETT_GROESSE - 2 * RAND;
    private static final int PUNKT_RADIUS = 20;
    private static final int SPIELER_PUNKT_RADIUS = 30;
    private static final int SPIELER_PUNKT_ABSTAND = 40;
    private Spielregeln spielregeln; 
    private Spieler spieler1, spieler2; 
    private Spieler aktuellerSpieler; 
    
    private boolean imSetzModus = true;
    private boolean imZugModus = false;
    private boolean imLoeschModus = false;
    
    private int ausgewaehltePosition = -1; 
    private boolean warSteinAusgewaehlt = false;
    

   private MuehleLogik logik;
   
   private final Point[] points = {
		    /*0*/ new Point(100, 100), new Point(400, 100), new Point(700, 100),
		    /*3*/ new Point(200, 200), new Point(400, 200), new Point(600, 200),
		    /*6*/new Point(300, 300), new Point(400, 300), new Point(500, 300),
		    /*9*/ new Point(100, 400), /*10*/ new Point(200, 400),  /*11*/ new Point(300, 400),  /*12*/ new Point(500, 400),  /*13*/ new Point(600, 400),  /*14*/ new Point(700, 400),
		    /*15*/new Point(300, 500), new Point(400, 500), new Point(500, 500),
		    /*18*/new Point(200, 600), new Point(400, 600), new Point(600, 600),
		    /*21*/new Point(100, 700), new Point(400, 700), new Point(700, 700)
		};

    public MuehleFeld() {
        initialisiereUI();
        initialisiereInteraktion();
        
        this.setPreferredSize(new Dimension(700, 600));

        spielregeln = new Spielregeln();
        spieler1 = new Spieler("Spieler 1", 's');
        spieler2 = new Spieler("Spieler 2", 'w');
        aktuellerSpieler = spieler1;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
             
            }
        });
    }
    private void handleMouseClick(MouseEvent e) {
        System.out.println("Handling mouse click at: " + e.getPoint());
        if (imSetzModus) {
            System.out.println("Setzmodus aktiv");
            handleSteinSetzen(e);
        } else if (imZugModus) {
            System.out.println("Zugmodus aktiv");
            handleSteinZiehen(e);
        } else if (imLoeschModus) {
            System.out.println("Löschmodus aktiv");
            handleSteinLoeschen(e);
        }
    }



    private void handleSteinSetzen(MouseEvent e) {
    	
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            if (e.getPoint().distance(p) < 20) { // Klick innerhalb eines Radius von 20 Pixeln
                // Stein setzen
                if (spielregeln.setzeStein(i, aktuellerSpieler.getFarbe())) {
                    System.out.println("Stein gesetzt an Position " + i);
                    aktuellerSpieler.steinGesetzt();

                    // Prüfen, ob eine Mühle gebildet wurde
                    if (spielregeln.hatMuehle(i)) {
                        System.out.println("Mühle gebildet an Position " + i);
                        imSetzModus = false;
                        imZugModus = false;
                        imLoeschModus = true; // Wechsel in den Löschmodus
                    } else {
                        // Prüfen, ob alle Steine gesetzt wurden
                        if (spieler1.getGesetzteSteine() + spieler2.getGesetzteSteine() == 18) { // 18 Steine insgesamt, 9 pro Spieler
                            imSetzModus = false;
                            imZugModus = true;
                            System.out.println("Alle Steine gesetzt. Wechsel in den Zugmodus.");
                            wechsleSpieler();
                         
                        } else {
                            // Wechsel zu nächsten Spieler
                            wechsleSpieler();
                        }
                    }

                    repaint();
                } else {
                    System.out.println("Ungültiger setzen an Position " + i);
                }
                break;
            }
        }
    }


    private void handleSteinZiehen(MouseEvent e) {
    	
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            if (e.getPoint().distance(p) < 20) {
                if (!warSteinAusgewaehlt) {
                	
                    if (spielregeln.getSteine()[i] != null &&
                        spielregeln.getSteine()[i].getFarbe() == aktuellerSpieler.getFarbe()) {
                    	
                        ausgewaehltePosition = i;
                        warSteinAusgewaehlt = true;
                        System.out.println("Stein ausgewählt an Position " + i);
                    }
                } else {
                    if (ausgewaehltePosition != -1) {
                        if (spielregeln.zieheStein(ausgewaehltePosition, i, aktuellerSpieler,spielregeln)) {
                            System.out.println("Stein bewegt von Position " + ausgewaehltePosition + " zu Position " + i);
                            warSteinAusgewaehlt = false;
                            ausgewaehltePosition = -1;

                            // Mühlenprüfung nach dem Ziehen
                            if (spielregeln.hatMuehle(i)) {
                                System.out.println("Mühle gebildet an Position " + i);
                                imZugModus = false;
                                imLoeschModus = true;
                                
                            } else {
                                wechsleSpieler();
                            }
                            repaint();
                        } else {
                            System.out.println("Ungültiger Zug von Position " + ausgewaehltePosition + " zu Position " + i);
                            warSteinAusgewaehlt = false;
                            ausgewaehltePosition = -1;
                        }
                    }
                }
                break;
            }
        }
    }
    

    private void handleSteinLoeschen(MouseEvent e) {
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            if (e.getPoint().distance(p) < 20) {
                if (spielregeln.getSteine()[i] != null &&
                    spielregeln.getSteine()[i].getFarbe() != aktuellerSpieler.getFarbe()) {
                	 
                    if (spielregeln.loschen(i, aktuellerSpieler.getFarbe())) {
                        System.out.println("Stein an Position " + i + " entfernt.");
                        imLoeschModus = false; // Löschmodus beenden
                        wechsleSpieler();
                        if (spielregeln.hatNochZuege(aktuellerSpieler.getFarbe())) {
                            imZugModus = true; // Zurück in den Zugmodus, wenn noch Züge möglich sind
                        } else {
                            imSetzModus = true; // Ansonsten zurück zum Setzen, falls keine Züge mehr möglich
                        }
                        repaint();
                        break;
                    } else {
                        System.out.println("Fehler beim Löschen des Steins an Position " + i);
                    }
                } else {
                    System.out.println("Ungültiger Löschversuch an Position " + i);
                }
            }
        }
    }


    private void wechsleSpieler() {
        aktuellerSpieler = (aktuellerSpieler == spieler1) ? spieler2 : spieler1;
        System.out.println("Wechsle zu " + aktuellerSpieler.getName());
    }



    private void initialisiereUI() {
        setBackground(Color.BLACK);
        logik = new MuehleLogik();
    }

    private void initialisiereInteraktion() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                verarbeiteMausKlick(e);
            }
        });
    }

    private void verarbeiteMausKlick(MouseEvent e) {
        logik.handleClick(e.getX(), e.getY());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        zeichneBrett((Graphics2D) g);
        
        for (int i = 0; i < spielregeln.getSteine().length; i++) {
            if (spielregeln.getSteine()[i] != null) {
                g.setColor(spielregeln.getSteine()[i].getFarbe() == 's' ? Color.RED : Color.BLUE);
                g.fillOval(points[i].x - 15, points[i].y - 15, 30, 30);
                g.setColor(Color.BLACK);
                g.drawOval(points[i].x - 15, points[i].y - 15, 30, 30);
            }
        }
    }

    private void zeichneBrett(Graphics2D g2d) {
        setzeRenderingHinweise(g2d);
        zeichneVerschachtelteQuadrate(g2d);
        zeichnePunkte(g2d);
        zeichneVerbindungslinien(g2d);
        zeichneSpielerBereiche(g2d);
        logik.drawGame(g2d);
    }

    private void setzeRenderingHinweise(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2));
    }

    private void zeichneVerschachtelteQuadrate(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        zeichneQuadrat(g2d, RAND, GITTER_GROESSE);
        zeichneQuadrat(g2d, RAND + GITTER_GROESSE / 6, GITTER_GROESSE * 2 / 3);
        zeichneQuadrat(g2d, RAND + GITTER_GROESSE / 3, GITTER_GROESSE / 3);
    }

    private void zeichneQuadrat(Graphics2D g2d, int offset, int groesse) {
        g2d.drawRect(offset, offset, groesse, groesse);
    }

    private void zeichneVerbindungslinien(Graphics2D g2d) {
        zeichneHorizontaleLinien(g2d);
        zeichneVertikaleLinien(g2d);
    }

    private void zeichneHorizontaleLinien(Graphics2D g2d) {
        int mitteY = RAND + GITTER_GROESSE / 2;
        g2d.drawLine(RAND, mitteY, BRETT_GROESSE - RAND, mitteY);
        g2d.drawLine(RAND + GITTER_GROESSE / 6, mitteY, RAND + GITTER_GROESSE * 5 / 6, mitteY);
    }

    private void zeichneVertikaleLinien(Graphics2D g2d) {
        int mitteX = RAND + GITTER_GROESSE / 2;
        g2d.drawLine(mitteX, RAND, mitteX, BRETT_GROESSE - RAND);
        g2d.drawLine(mitteX, RAND + GITTER_GROESSE / 6, mitteX, RAND + GITTER_GROESSE * 5 / 6);
    }

    private void zeichnePunkte(Graphics2D g2d) {
        int[] aussenPositionen = {RAND, RAND + GITTER_GROESSE / 2, BRETT_GROESSE - RAND};
        int[] mittlerePositionen = {RAND + GITTER_GROESSE / 6, RAND + GITTER_GROESSE / 2, RAND + GITTER_GROESSE * 5 / 6};
        int[] innerePositionen = {RAND + GITTER_GROESSE / 3, RAND + GITTER_GROESSE / 2, RAND + GITTER_GROESSE * 2 / 3};
        zeichneKreiseFuerQuadrat(g2d, aussenPositionen);
        zeichneKreiseFuerQuadrat(g2d, mittlerePositionen);
        zeichneKreiseFuerQuadrat(g2d, innerePositionen);
    }

    private void zeichneKreiseFuerQuadrat(Graphics2D g2d, int[] positionen) {
        for (int x : positionen) {
            for (int y : positionen) {
            	
                zeichneKreis(g2d, x, y, PUNKT_RADIUS);
            
            }
        }
    }
    

    private void zeichneKreis(Graphics2D g2d, int x, int y, int radius) {
        int angepasstesX = x - radius / 2;
        int angepasstesY = y - radius / 2;
        g2d.drawOval(angepasstesX, angepasstesY, radius, radius);
    }

    private void zeichneSpielerBereiche(Graphics2D g2d) {
        zeichneSpielerBereich(g2d, RAND / 2, Color.RED);
        zeichneSpielerBereich(g2d, BRETT_GROESSE - RAND / 2, Color.BLUE);
    }
    
    private void lochenKreisSpielbereich(Graphics2D g2d, Spieler akktulereSpieler) {
    	g2d.setColor(Color.BLACK);
    		int xSp1 = RAND / 2;
    		int xSp2 = BRETT_GROESSE - RAND / 2;
    		
    		if(akktulereSpieler == spieler1) {
    			for (int i = 0; i < 9; i++) {
    				int y = RAND + i * SPIELER_PUNKT_ABSTAND;
    				zeichneKreis(g2d, xSp1, y, SPIELER_PUNKT_RADIUS);
    			}
    		}
    		else {
            	for (int i = 0; i < 9; i++) {
                    int y = RAND + i * SPIELER_PUNKT_ABSTAND;
                    zeichneKreis(g2d, xSp2, y, SPIELER_PUNKT_RADIUS);
            	}
    		}
        
    }

    private void zeichneSpielerBereich(Graphics2D g2d, int x, Color farbe) {
        g2d.setColor(farbe);
        for (int i = 0; i < 9; i++) {
            int y = RAND + i * SPIELER_PUNKT_ABSTAND;
            zeichneKreis(g2d, x, y, SPIELER_PUNKT_RADIUS);
        }
    }
}
