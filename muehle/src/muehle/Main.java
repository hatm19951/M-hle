package muehle;

import java.awt.Point;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Mühle");
		MuehleFeld feld = new MuehleFeld();
		frame.add(feld);
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	

}

