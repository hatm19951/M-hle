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

    public static Spielregeln spielregeln;
    public static Spieler spieler1, spieler2;
    public static Spieler aktuellerSpieler;

    public static boolean imSetzModus = true;
    public static boolean imZugModus = false;
    public static boolean imLoeschModus = false;
    public static boolean debugModus = false;
    private int ausgewaehltePosition = -1;
    private boolean warSteinAusgewaehlt = false;

    private MuehleLogik logik;
    public static final Point[] points = initialisierePunkte();

    public MuehleFeld() {
        initialisiereUI();
        initialisiereInteraktion();
        setPreferredSize(new Dimension(700, 600));
        initialisiereSpiel();
    }

    private static Point[] initialisierePunkte() {
        return new Point[]{
                new Point(100, 100), new Point(400, 100), new Point(700, 100),
                new Point(200, 200), new Point(400, 200), new Point(600, 200),
                new Point(300, 300), new Point(400, 300), new Point(500, 300),
                new Point(100, 400), new Point(200, 400), new Point(300, 400),
                new Point(500, 400), new Point(600, 400), new Point(700, 400),
                new Point(300, 500), new Point(400, 500), new Point(500, 500),
                new Point(200, 600), new Point(400, 600), new Point(600, 600),
                new Point(100, 700), new Point(400, 700), new Point(700, 700)
        };
    }

    private void initialisiereSpiel() {
        spielregeln = new Spielregeln();
        spieler1 = new Spieler("Spieler 1", 'r');
        spieler2 = new Spieler("Spieler 2", 'b');
        aktuellerSpieler = spieler1;
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
        handleMouseClick(e);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        zeichneBrett((Graphics2D) g);
        zeichneSteine((Graphics2D) g);
        zeichneSchwarzeQuadrat ((Graphics2D) g);
        zeichneAusgewähltKreis((Graphics2D) g);
        zeichneAktuellenSpieler(g);
        zeichneVerbleibendeSteine(g);
        zeichneMuehle(g);
        zeichneGewinner((Graphics2D) g);
        zeichneRemis((Graphics2D) g);
    }

    private void handleMouseClick(MouseEvent e) {
        if (pruefeSpielEnde()) return;
        
        if (imSetzModus) {
            handleSteinSetzen(e);
        } else if (imZugModus) {
            handleSteinZiehen(e);
        } else if (imLoeschModus) {
            handleSteinLoeschen(e);
        }
    }

    private boolean pruefeSpielEnde() {
        if (spielregeln.hatVerloren(aktuellerSpieler) 
        	|| (spielregeln.nichtBewegenKann(aktuellerSpieler) && imZugModus) 
        	|| spielregeln.esGiebtRemis) {
        	
            return true;
        }
        return false;
    }

    private void handleSteinSetzen(MouseEvent e) {
        Point klickPunkt = e.getPoint();
        for (int i = 0; i < points.length; i++) {
            if (klickPunkt.distance(points[i]) < 20) {
                versucheSteinZuSetzen(i);
                break;
            }
        }
    }

    private void versucheSteinZuSetzen(int position) {
        if (spielregeln.setzeStein(position, aktuellerSpieler.getFarbe())) {
            aktuellerSpieler.steinGesetzt();
            Spielsound.Playmusic("src/stein.wav");
            pruefeNachSetzen(position);
        } else {
            System.out.println("Ungültiger setzen an Position " + position);
        }
    }

    private void pruefeNachSetzen(int position) {
        if (spielregeln.hatMuehle(position)) {
            imSetzModus = false;
            imZugModus = false;
            imLoeschModus = true;
        } else if (spieler1.getGesetzteSteine() + spieler2.getGesetzteSteine() == 18) {
            imSetzModus = false;
            imZugModus = true;
            wechsleSpieler();
        } else {
            wechsleSpieler();
        }
        repaint();
    }

    private void handleSteinZiehen(MouseEvent e) {
        Point klickPunkt = e.getPoint();
        for (int i = 0; i < points.length; i++) {
            if (klickPunkt.distance(points[i]) < 20) {
                verarbeiteZug(i);
                break;
            }
        }
    }

    private void verarbeiteZug(int neuePosition) {
        if (!warSteinAusgewaehlt) {
            versucheSteinAuszuwaehlen(neuePosition);
        } else {
            versucheZuZiehen(neuePosition);
        }
    }

    private void versucheSteinAuszuwaehlen(int position) {
        if (spielregeln.getSteine()[position] != null && spielregeln.getSteine()[position].getFarbe() == aktuellerSpieler.getFarbe()) {
            ausgewaehltePosition = position;
            warSteinAusgewaehlt = true;
            spielregeln.ausgewähltStein(position, aktuellerSpieler);
        }
    }

    private void versucheZuZiehen(int zielPosition) {
        if (ausgewaehltePosition != -1 && spielregeln.zieheStein(ausgewaehltePosition, zielPosition, aktuellerSpieler, spielregeln, spieler1, spieler2)) {
            warSteinAusgewaehlt = false;
            ausgewaehltePosition = -1;
            Spielsound.Playmusic("src/ziehen.wav");
            pruefeNachZug(zielPosition);
        } else {
            warSteinAusgewaehlt = false;
            ausgewaehltePosition = -1;
        }
    }

    private void pruefeNachZug(int position) {
        if (spielregeln.hatMuehle(position)) {
            imZugModus = false;
            imLoeschModus = true;
        } else {
            wechsleSpieler();
        }
        repaint();
    }

    private void handleSteinLoeschen(MouseEvent e) {
        Point klickPunkt = e.getPoint();
        for (int i = 0; i < points.length; i++) {
            if (klickPunkt.distance(points[i]) < 20) {
                versucheSteinZuLoeschen(i);
                break;
            }
        }
    }

    private void versucheSteinZuLoeschen(int position) {
        if (spielregeln.getSteine()[position] != null && spielregeln.getSteine()[position].getFarbe() != aktuellerSpieler.getFarbe()) {
            if (spielregeln.loschen(position, aktuellerSpieler.getFarbe(), spieler1, spieler2)) {
                imLoeschModus = false;
                Spielregeln.giebtMuehle = false;
                Spielsound.Playmusic("src/loechen.wav");
                wechsleSpielerNachLoeschen();
            } else {
                System.out.println("Fehler beim Löschen des Steins an Position " + position);
            }
        } else {
            System.out.println("Ungültiger Löschversuch an Position " + position);
        }
    }

    private void wechsleSpielerNachLoeschen() {
        wechsleSpieler();
        if (spielregeln.hatNochZuege(aktuellerSpieler.getFarbe())) {
            imZugModus = true;
        } else {
            imSetzModus = true;
        }
        repaint();
    }

    private void wechsleSpieler() {
        aktuellerSpieler = (aktuellerSpieler == spieler1) ? spieler2 : spieler1;
    }

    private void zeichneBrett(Graphics2D g2d) {
        setzeRenderingHinweise(g2d);
        zeichneVerschachtelteQuadrate(g2d);
        zeichneVerbindungslinien(g2d);
        zeichnePunkte(g2d);
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

    private void zeichneSteine(Graphics2D g2d) {
        for (int i = 0; i < spielregeln.getSteine().length; i++) {
            if (spielregeln.getSteine()[i] != null) {
                g2d.setColor(spielregeln.getSteine()[i].getFarbe() == 'r' ? Color.RED : Color.BLUE);
                g2d.fillOval(points[i].x - 15, points[i].y - 15, 30, 30);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(points[i].x - 15, points[i].y - 15, 30, 30);
            }
        }
    }
    
    private void zeichneSchwarzeQuadrat (Graphics2D g) {
    	g.setColor(Color.black);
    	g.fillRect(315, 315, 172, 172);  
    	}
    

    private void zeichneAusgewähltKreis(Graphics2D g2d) {
        for (int i = 0; i < spielregeln.getSteine().length; i++) {
            if (spielregeln.getSteine()[i] != null && spielregeln.getSteine()[i].ausgewählt) {
                g2d.setColor(Color.YELLOW);
                zeichneKreis(g2d, points[i].x, points[i].y, 30);
                zeigeMöglicheSteine1(g2d,i);
                zeigeMöglicheSteine2(g2d,i);
                
            }
        }
    }
    
    private void zeigeMöglicheSteine1(Graphics2D g2d, int i) {
    	if (imZugModus) {
            int[] benachbartePositionen = spielregeln.getBenachbartePositionen(i);
            for (int pos : benachbartePositionen) {
                if (spielregeln.getSteine()[pos] == null) {
                    g2d.setColor(Color.YELLOW);
                    zeichneKreis(g2d, points[pos].x, points[pos].y, 30);
                }
            }
            spielregeln.getSteine()[i].ausgewählt = false;
        }
    }
    
    private void zeigeMöglicheSteine2(Graphics2D g2d, int i) {
    	if (imZugModus && aktuellerSpieler.getAktuelleSteineAufFeld(spielregeln) == 3) {
   		 for (int index = 0; index < spielregeln.getSteine().length; index++) {
   			 if (spielregeln.getSteine()[index] == null) {
   				 g2d.setColor(Color.YELLOW);
                    zeichneKreis(g2d, points[index].x, points[index].y, 30);
   			 }
   		 }
   		spielregeln.getSteine()[i].ausgewählt = false;
    	}
    }
    

    private void zeichneAktuellenSpieler(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        String text = "Aktueller Spieler: " + aktuellerSpieler.getName();
        g.drawString(text, 275, 50);
    }

    private void zeichneVerbleibendeSteine(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Spieler1: " + spieler1.getVerbleibendeSteine(), 50, 50);
        g.drawString("gesetzte Steine: " + spieler1.getAktuelleSteineAufFeld(spielregeln), 50, 80);

        g.setColor(Color.BLUE);
        g.drawString("Spieler2: " + spieler2.getVerbleibendeSteine(), 600, 50);
        g.drawString("gesetzte Steine: " + spieler2.getAktuelleSteineAufFeld(spielregeln), 600, 80);
    }

    private void zeichneMuehle(Graphics g) {
        if (Spielregeln.giebtMuehle && aktuellerSpieler.getFarbe() == 'r') {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Mühle", 360, 405);
            Spielsound.Playmusic("src/muehle.wav");
        } else if (Spielregeln.giebtMuehle) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Mühle", 360, 405);
            Spielsound.Playmusic("src/muehle.wav");
        }
    }

    private void zeichneGewinner(Graphics2D g) {
        if (spielregeln.hatVerloren(aktuellerSpieler) || (spielregeln.nichtBewegenKann(aktuellerSpieler) && imZugModus)) {
            if (aktuellerSpieler == spieler2) {
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                g.drawString("Spieler1", 353, 370);
                g.drawString("hat", 385, 400);
                g.drawString("gewonnen", 347, 430);
                Spielsound.Playmusic("src/gewinner.wav");
            } else{
                g.setColor(Color.BLUE);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                g.drawString("Spieler2", 353, 370);
                g.drawString("hat", 385, 400);
                g.drawString("gewonnen", 347, 430);
                Spielsound.Playmusic("src/gewinner.wav");
            }
        }
    }

    private void zeichneRemis(Graphics2D g) {
        if (spielregeln.esGiebtRemis) {
        	zeichneSchwarzeQuadrat ((Graphics2D) g);
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("REMIS", 363, 365);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("Alle Steine von Gegner", 340, 385);
            g.drawString("sind Teil einer Muehle", 345, 405);
        }
    }

    public void neuStartSpiel() {
        spielregeln = new Spielregeln();
        spielregeln.giebtMuehle = false;
        spieler1 = new Spieler("Spieler 1", 'r');
        spieler2 = new Spieler("Spieler 2", 'b');
        aktuellerSpieler = spieler1;
        imSetzModus = true;
        imZugModus = false;
        imLoeschModus = false;
        ausgewaehltePosition = -1;
        warSteinAusgewaehlt = false;
        repaint();
    }
}