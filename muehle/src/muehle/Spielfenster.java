package muehle;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Spielfenster extends JFrame {
	MuehleFeld feld = new MuehleFeld();
	
	public Spielfenster() {
		
		setTitle("Mühle");
		try {
            Image icon = ImageIO.read(new File("res/muehle.png"));
            setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
		add(feld);
		setSize(800, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Menu();
		
		this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
             
            }
        });
	}
	
	private void Menu() {
		
        JMenuBar menuBar = new JMenuBar();
        JMenu spielMenu = new JMenu("Spiel");
        JMenu debugModusMenu = new JMenu("Debug");
        menuBar.add(spielMenu);
        menuBar.add(debugModusMenu);
        inhaltSpielMenu(menuBar,spielMenu);
        inhaltDebugModusMenu(menuBar,debugModusMenu);
	}
	
	private void inhaltSpielMenu(JMenuBar menuBar,JMenu spielMenu) {
		JMenuItem neuStartItem = new JMenuItem("Neu Starten");
        JMenuItem exitItem = new JMenuItem("Beenden");
        spielMenu.add(neuStartItem);
        spielMenu.add(exitItem);
        neuStartAction(neuStartItem);
        exitAction(exitItem);
        setJMenuBar(menuBar);
	}
	
	private void exitAction(JMenuItem a){
		a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Programm beenden
            }
        });
	}
	
	private void neuStartAction(JMenuItem a){
		a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	feld.neuStartSpiel();
            }
        });
	}
	
	private void inhaltDebugModusMenu(JMenuBar menuBar,JMenu debugModusMenu) {
		JMenuItem debugModus = new JMenuItem("Debug-Modus akktivieren");
		JMenuItem zeichneBlau = new JMenuItem("blauen platzieren");
        JMenuItem zeichneRot = new JMenuItem("roten platzieren");

        debugModusMenu.add(debugModus);
        debugModusMenu.add(zeichneBlau);
        debugModusMenu.add(zeichneRot);
        debugaktivieren(debugModus);
        zeichneBlauAction(zeichneBlau);
        zeichneRotAction(zeichneRot);
        setJMenuBar(menuBar);
	}
	
	private void debugaktivieren(JMenuItem a){
		a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	if(!MuehleFeld.debugModus) {
            		MuehleFeld.debugModus = true;
            		a.setText("Debug-Modus deakktivieren");
                	System.out.println("Debug-Modus aktiv");
                	}else {
                		MuehleFeld.debugModus = false;	
                		System.out.println("Debug-Modus deaktiv");
                		a.setText("Debug-Modus akktivieren");
                	}
            }
            
        });
	}
	
	private void zeichneBlauAction(JMenuItem a){
		a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!MuehleFeld.blauAdd) {
            	MuehleFeld.aktuellerSpieler = MuehleFeld.spieler2;
            	System.out.println("Blau aktiv");
            	}else {
            		MuehleFeld.blauAdd = false;
            		System.out.println("Blau deaktiv");
            	}
            }
           
            
        });
	}
	
	
	private void zeichneRotAction(JMenuItem a){
		a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                	MuehleFeld.aktuellerSpieler = MuehleFeld.spieler1;
                	
 
            }
        });
	}
	
	
}

	
