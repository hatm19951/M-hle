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

    private MuehleLogik logik;

    public MuehleFeld() {
        initialisiereUI();
        initialisiereInteraktion();
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

    private void zeichneSpielerBereich(Graphics2D g2d, int x, Color farbe) {
        g2d.setColor(farbe);
        for (int i = 0; i < 9; i++) {
            int y = RAND + i * SPIELER_PUNKT_ABSTAND;
            zeichneKreis(g2d, x, y, SPIELER_PUNKT_RADIUS);
        }
    }
}
