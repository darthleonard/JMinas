package darth.jminas.tools;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class Sonido extends Thread {
	
	private String clipPath;
	private boolean winnerFlag;
	
	public Sonido(String clipPath, boolean winnerFlag) {
		this.clipPath = clipPath;
		this.winnerFlag = winnerFlag;
	}

	public void run() {
		try {
			final Clip sonido = AudioSystem.getClip();
			URL pathBoom = getClass().getResource(clipPath);
			sonido.open(AudioSystem.getAudioInputStream(pathBoom));
			sonido.start();
			sonido.loop(2);
			while(sonido.getMicrosecondPosition() <= sonido.getMicrosecondLength());
			sonido.stop();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException | NullPointerException e) {
			new ErrorReporter().CreateLog("Error message: " + e.getMessage() + "\n" + e.getLocalizedMessage());
			if(winnerFlag) {
				JOptionPane.showMessageDialog(null, "¡HAS GANADO!", "Error con el archivo de audio", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "¡BOO000000M!", "Error con el archivo de audio", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
