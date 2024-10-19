package muehle;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Spielsound {
	
	public static void Playmusic (String platz) {
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(platz));
			Clip clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.start();
			clip.loop(0);
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}
}
