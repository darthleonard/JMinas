package darth.jminas.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;

import javax.swing.JComponent;

public class AcercaContentPane extends JComponent {
    private static final long serialVersionUID = 8668944310336850671L;
    
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        Color[] colors = new Color[]{
            new Color(0,0,0,0),
            new Color(0.3f,0.3f,0.3f,1f),
            new Color(0.3f,0.3f,0.3f,1f),
            new Color(0,0,0,0)};
        float[] stops = new float[]{0,0.2f,0.8f,1f};
        LinearGradientPaint paint = new LinearGradientPaint(
                new Point(0,0),
                new Point(500,0),
                stops,colors);
        g.setPaint(paint);
        g.fillRect(0, 0, 500, 200);
        g.setPaint(Color.WHITE);
        g.drawString("Darth Leonard", 200, 90);
        g.drawString("leolinuxmx@gmail.com", 185, 110);
    }
}