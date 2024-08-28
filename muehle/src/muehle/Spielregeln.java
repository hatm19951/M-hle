package muehle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

public class Spielregeln {

    private Stein[] steine = new Stein[24]; 
    private Set<Set<Integer>> gebildeteMuehlen = new HashSet<>();
    public static boolean giebtMuehle = false;
    public boolean esGiebtRemis = false;

    public Spielregeln() {
        for (int i = 0; i < steine.length; i++) {
            steine[i] = null;
        }
    }

    public Stein[] getSteine() {
        return steine;
    }
    
    private int gesetzteRotenSteine = 0; 
    private int gesetzteBlauenSteine = 0; 
    private boolean imSetzeModus;
    
    public boolean setzeStein(int position, char farbe) {
        if (position < 0 || position >= steine.length || steine[position] != null) {
            return false; 
        }

        
        if (farbe == 'r' && gesetzteRotenSteine >= 9) {
            System.out.println("Keine schwarze Steine mehr zum Setzen übrig.");
            imSetzeModus = false;
            return false;
        } else if (farbe == 'b' && gesetzteBlauenSteine >= 9) {
            System.out.println("Keine weisse Steine mehr zum Setzen übrig.");
            imSetzeModus = false;
            return false;
        }

       
        steine[position] = new Stein(position, position, farbe);

        if (farbe == 'r') {
            gesetzteRotenSteine++;
        } else if (farbe == 'b') {
            gesetzteBlauenSteine++;
        }

        return true;
    }

    public boolean zieheStein(int vonPosition, int zuPosition, Spieler aktuellerSpieler, Spielregeln spielregeln, Spieler spieler1, Spieler spieler2) {
        if (vonPosition < 0 || vonPosition >= steine.length || zuPosition < 0 || zuPosition >= steine.length) {
            return false; 
        }

        if (steine[vonPosition] == null || steine[zuPosition] != null) {
            return false; // Kein Stein an der Startposition oder Zielposition bereits belegt
        } 
        if(!kannSteinZiehendarf(vonPosition, zuPosition,aktuellerSpieler,spielregeln)) {
        	return false;
        } 

        
        steine[zuPosition] = steine[vonPosition];
        steine[vonPosition] = null;
        
        gebildeteMuehlen.removeIf(muehle -> muehle.contains(vonPosition));
        
        aktuellerSpieler = (aktuellerSpieler == spieler1) ? spieler2 : spieler1;
        if (hatVerloren(aktuellerSpieler)|| kannZiehen(aktuellerSpieler)) {
        	zeigeSpielEndeNachricht(aktuellerSpieler, spieler1, spieler2);
            
        }

        return true;
    }
    
   public boolean kannSteinZiehen(char farbe) {
        // Logik zur Überprüfung, ob der Spieler mit der angegebenen Farbe noch ziehen kann
        // Dies kann z.B. prüfen, ob es noch gültige Züge gibt
    	 if (farbe == 'r' && gesetzteRotenSteine >= 9) {
              return true;
         } else if (farbe == 'b' && gesetzteBlauenSteine >= 9) {
               return true;
         }
    	
        return false; 
    }
   
   public boolean hatNochZuege(char farbe) {
	    // Schleife durch alle Positionen
	    for (int i = 0; i < steine.length; i++) {
	        if (steine[i] != null && steine[i].getFarbe() == farbe) {
	            // Überprüfen, ob der Stein an dieser Position bewegt werden kann
	            if (kannSteinZiehen(farbe)) {
	                return true; // Es gibt einen gültigen Zug
	            }
	        }
	    }
	    return false; // Keine gültigen Züge gefunden
	}

   public boolean kannSteinZiehendarf(int vonPosition, int zuPosition, Spieler aktuellerSpieler, Spielregeln spielregeln) {
	    if (aktuellerSpieler.getAktuelleSteineAufFeld(spielregeln) == 3) {
	        return spielregeln.getSteine()[zuPosition] == null; // Spieler darf auf jede freie Position springen
	    } else {
	        int[] benachbartePositionen = getBenachbartePositionen(vonPosition);
	        for (int pos : benachbartePositionen) {
	            if (spielregeln.getSteine()[pos] == null && pos == zuPosition) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

   public int[] getBenachbartePositionen(int position) {
	    switch (position) {
	        case 0: return new int[]{1, 9}; 
	        case 1: return new int[]{0, 2, 4}; 
	        case 2: return new int[]{1, 14}; 
	        case 3: return new int[]{4,10}; 
	        case 4: return new int[]{1, 3, 5, 7}; 
	        case 5: return new int[]{4,13}; 
	        case 6: return new int[]{7,11}; 
	        case 7: return new int[]{4,6,8}; 
	        case 8: return new int[]{7,12}; 
	        case 9: return new int[]{0,10,21}; 
	        case 10: return new int[]{3,9,11,18}; 
	        case 11: return new int[]{6,10,15}; 
	        case 12: return new int[]{8,13,17}; 
	        case 13: return new int[]{5,12,14,20}; 
	        case 14: return new int[]{2,13,23}; 
	        case 15: return new int[]{11,16}; 
	        case 16: return new int[]{15,17,19}; 
	        case 17: return new int[]{12,16}; 
	        case 18: return new int[]{10,19}; 
	        case 19: return new int[]{16, 18,20, 22}; 
	        case 20: return new int[]{13,19}; 
	        case 21: return new int[]{9,22}; 
	        case 22: return new int[]{19,21,23}; 
	        case 23: return new int[]{14,22}; 
	        default: return new int[]{};
	    }
	}

    public boolean hatMuehle(int position) {
        int[][] muehlen = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, 
            {9, 10, 11}, {12, 13, 14}, {15, 16, 17},
            {18, 19, 20}, {21, 22, 23},
            {0, 9, 21}, {3, 10, 18}, {6, 11, 15}, 
            {1, 4, 7}, {16, 19, 22},
            {8, 12, 17}, {5, 13, 20}, {2, 14, 23}
        };

        for (int[] muehle : muehlen) {
            if (steine[muehle[0]] != null && steine[muehle[1]] != null && steine[muehle[2]] != null) {
                if (steine[muehle[0]].getFarbe() == steine[muehle[1]].getFarbe() &&
                    steine[muehle[0]].getFarbe() == steine[muehle[2]].getFarbe()) {

                    
                	Set<Integer> aktuelleMuehle = new HashSet<>(Arrays.asList(muehle[0], muehle[1], muehle[2]));

       
                    if (!gebildeteMuehlen.contains(aktuelleMuehle)) {
                        gebildeteMuehlen.add(aktuelleMuehle); // Speichere die Mühle als erkannt
                        giebtMuehle = true;
                        return true;
                    }
                    

                  
                }
            }
        }
        giebtMuehle = false;
        return false;
    }


    public boolean loschen(int position, char aktuellerSpielerFarbe, Spieler spieler1, Spieler spieler2) {
        // Überprüfen, ob der ausgewählte Stein ein gegnerischer Stein ist
        if (steine[position] != null && steine[position].getFarbe() != aktuellerSpielerFarbe) {
            // Überprüfen, ob alle gegnerischen Steine Teil einer Mühle sind
            if (alleGegnerischenSteineInMuehle(aktuellerSpielerFarbe)) {
                System.out.println("Stein an Position " + position + " entfernt (obwohl Teil einer Mühle, da keine andere Wahl besteht).");
                return false; 
            } else if (positionTeileinMuehle(position)) {
                System.out.println("Der Stein an Position " + position + " ist Teil einer Mühle und kann nicht gelöscht werden.");
                return false; // Stein kann nicht gelöscht werden
            } else {
                steine[position] = null; 
                Spieler gegner = (aktuellerSpielerFarbe == spieler1.getFarbe()) ? spieler2 : spieler1;
                if (hatVerloren(gegner) && !imSetzeModus) {
                    zeigeVerlustNachricht(gegner);
                    return false;
                }

                return true; 
            }
        } else {
            System.out.println("Ungültiger Löschvorgang an Position " + position);
            return false; // Fehler: Kein gültiger gegnerischer Stein gefunden
        }
    }

    
    public boolean positionTeileinMuehle(int position) {
        for (Set<Integer> muehle : gebildeteMuehlen) {
            if (muehle.contains(position)) {
                return true; // Die Position ist Teil einer Mühle
            }
        }
        return false; // Die Position ist nicht Teil einer Mühle
    }
    
    
    
    public void ausgewähltStein(int vonPosition, Spieler aktuellerSpieler) {
    	if(steine[vonPosition].getFarbe() == aktuellerSpieler.getFarbe()) {
    		steine[vonPosition].ausgewählt = true;
    	}
    	
    }

    private boolean alleGegnerischenSteineInMuehle(char aktuellerSpielerFarbe) {
        for (int i = 0; i < steine.length; i++) {
            if (steine[i] != null && steine[i].getFarbe() != aktuellerSpielerFarbe) {
                if (!positionTeileinMuehle(i)) {
                    return false; // Es gibt mindestens einen gegnerischen Stein, der nicht in einer Mühle ist
                }
            }
        }
        esGiebtRemis = true;
        return true; // Alle gegnerischen Steine sind Teil einer Mühle
    }
    
    public boolean hatVerloren(Spieler spieler) {
        // Zähle die Steine des Spielers
        int steineAufFeld = 0;
        for (Stein stein : steine) {
            if (stein != null && stein.getFarbe() == spieler.getFarbe()) {
                steineAufFeld++;
            }
        }

        if (steineAufFeld < 3 && spieler.getVerbleibendeSteine() == 0) {
            System.out.println(spieler.getName() + " hat verloren, da er nur noch " + steineAufFeld + " Steine hat.");
            return true;
        }
		return false;
    }
        // Überprüfe, ob der Spieler keine gültigen Züge mehr machen kann
    public boolean kannZiehen(Spieler spieler) {
        boolean kannZiehen = false;
        for (int i = 0; i < steine.length; i++) {
            if (steine[i] != null && steine[i].getFarbe() == spieler.getFarbe()) {
                int[] benachbartePositionen = getBenachbartePositionen(i);
                for (int pos : benachbartePositionen) {
                    if (steine[pos] == null) {
                        kannZiehen = true;
                        break;
                    }
                }
            }
            if (kannZiehen) break;
        }

        if (!kannZiehen) {
            System.out.println(spieler.getName() + " hat verloren, da er keine gültigen Züge mehr machen kann.");
            return true;
        }

        return false;
    }

    public void zeigeSpielEndeNachricht(Spieler verlierer, Spieler spieler1, Spieler spieler2) {
        String gewinnerName = verlierer == spieler1 ? spieler2.getName() : spieler1.getName();
        JOptionPane.showMessageDialog(null, "Spiel beendet! " + verlierer.getName() + " hat verloren.\n" + gewinnerName + " gewinnt!", 
                                      "Spiel beendet", JOptionPane.INFORMATION_MESSAGE);

        // Beenden des Spiels durch Deaktivieren der Oberfläche oder Schließen des Fensters
        System.exit(0); // Beendet die gesamte Anwendung, kann auch anders gehandhabt werden
    }
    public void zeigeVerlustNachricht(Spieler verlierer) {
        System.out.println("Spiel beendet! " + verlierer.getName() + " hat verloren.");
        // Hier kannst du zusätzlich GUI-Komponenten verwenden, um eine Nachricht anzuzeigen oder das Spiel zu beenden
        JOptionPane.showMessageDialog(null, "Spiel beendet! " + verlierer.getName() + " hat verloren.");
        System.exit(0); // Beendet das Spiel
    }

    
}