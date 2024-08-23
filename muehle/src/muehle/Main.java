package muehle;

import muehle.Spielreglen;

public class Main {
	public static void main(String[] args) {
		
		/*test Stein Klasse von Hatem
		Spielreglen game = new Spielreglen();
		game.steinen[0].setSteinPosition(50, 50);
		game.steinen[1].setSteinPosition(100, 50);
		game.steinen[2].setSteinPosition(250, 50);
		
		game.steinen[3].setSteinPosition(250, 100);
		game.steinen[4].setSteinPosition(250, 150);
		System.out.println(game.gleichX());
		
		// test von Yafet
		System.err.println("HI");	
		
		*/
		Spieler spieler1 = new Spieler("jdhf",'d');
		spieler1.steinSetzen("dfd", 3,3);
		Stein ersterStein = spieler1.getSteine().get(0);
		System.out.println(ersterStein.id);


		JFrame frame = new JFrame("Mühle");
		MuehleFeld feld = new MuehleFeld();

		frame.add(feld);
		frame.setSize(700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	

}

