package muehle;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Mühle");
		MuehleFeld feld = new MuehleFeld();
		
		try {
            Image icon = ImageIO.read(new File("res/muehle.png"));
            frame.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		frame.add(feld);
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	

}

