package darth.jminas.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class AcercaFrame {
	
	public void GenerateFrame() {
		final JFrame frame = new JFrame("Frame test");
        frame.setUndecorated(true);
        frame.setBackground(new Color(0,0,0,0));
        frame.setContentPane(new AcercaContentPane());
        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });
        frame.pack();
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Thread t = new Thread(){
            public void run() {
                try {
                    Thread.sleep(3000);
                    frame.dispose();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
	}
}
