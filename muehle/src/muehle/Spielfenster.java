package muehle;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Spielfenster extends JFrame {
	
	public Spielfenster() {
		
		setTitle("Mühle");
		MuehleFeld feld = new MuehleFeld();
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
	}
}
