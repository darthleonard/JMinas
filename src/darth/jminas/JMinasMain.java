package darth.jminas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import darth.jminas.gui.AcercaFrame;
import darth.jminas.tools.ErrorReporter;

public class JMinasMain extends JFrame implements ActionListener {
    private static final long serialVersionUID = 5263848303195260404L;
    
    private JMenuBar menuBar;
    private JMenu menuOpciones, menuMas, menuNivel;
    private JMenuItem menuItemNuevo, menuItemSalir, menuItemEstadisticas, menuItemAcerca,
    	menuItemNivel0, menuItemNivel1, menuItemNivel2, menuItemNivel3;
    
    private ErrorReporter errorReporter;
    
    static PanelSuperior panelSuperior;
    static PanelCentral panelCentral;
    static PanelInferior panelInferior;
    
    private static Cronometro cronometro;
    
    private static boolean jugando = false;
    public static boolean Ganador = false;
    public static boolean Perdedor = false;
    
    public JMinasMain() {
    	errorReporter = new ErrorReporter();
    	limpiaErrores();
    	
        setTitle("JMinas");
        setSize(300, 396);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        createMenu();
        initComponents();
        addComponents();
        addKeyListener(panelCentral);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        validaErroresAlCargar();
    }
    
    private void initComponents() {
        panelSuperior = new PanelSuperior();
        panelCentral = new PanelCentral(this);
        panelInferior = new PanelInferior();
    }
    
    private void addComponents() {
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void createMenu() {
        menuBar=new JMenuBar();
        setJMenuBar(menuBar);
        
        menuOpciones = new JMenu("Opciones");
        menuBar.add(menuOpciones);
        menuItemNuevo = new JMenuItem("Nuevo");
        menuItemNuevo.addActionListener(this);
        menuOpciones.add(menuItemNuevo);
        menuOpciones.add(new JSeparator());
        menuNivel = new JMenu("Nivel");
        {
            menuItemNivel0 = new JMenuItem("Nivel 0");
            menuItemNivel0.addActionListener(this);
            menuNivel.add(menuItemNivel0);
            menuItemNivel1 = new JMenuItem("Principiante");
            menuItemNivel1.addActionListener(this);
            menuNivel.add(menuItemNivel1);
            menuItemNivel2 = new JMenuItem("Avanzado");
            menuItemNivel2.addActionListener(this);
            menuNivel.add(menuItemNivel2);
            menuItemNivel3 = new JMenuItem("Experto");
            menuItemNivel3.addActionListener(this);
            menuNivel.add(menuItemNivel3);
        }
        menuOpciones.add(menuNivel);
        menuItemEstadisticas = new JMenuItem("Estadisticas");
        menuOpciones.add(menuItemEstadisticas);
        menuOpciones.add(new JSeparator());
        menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.addActionListener(this);
        menuOpciones.add(menuItemSalir);
        
        menuMas = new JMenu("Mas");
        menuBar.add(menuMas);
        menuItemAcerca = new JMenuItem("Acerca de");
        menuItemAcerca.addActionListener(this);
        menuMas.add(menuItemAcerca);
    }
    
    public static void StartGame() {
        cronometro = new Cronometro();
        cronometro.start();
        jugando = true;
        Ganador = false;
        Perdedor = false;
    }
    
    public static void RestartGame() {
        panelCentral.restart();
        panelSuperior.restart();
        if(cronometro != null)
            cronometro.setActivo(false);
        jugando = false;
        Ganador = false;
        Perdedor = false;
    }
    
    public static void LostGame() {
        cronometro.setActivo(false);
        panelCentral.Perdio();
        jugando = false;
        Ganador = false;
        Perdedor = true;
        new Sonido(Variables.SonidoExplosion).start();
    }
    
    public static void WinGame() {
        StopChron();
        panelCentral.Gano();
        Ganador = true;
        Perdedor = false;
        new Sonido(Variables.SonidoGanador).start();
    }
    
    public static void StopChron() {
        cronometro.setActivo(false);
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==menuItemNuevo)
            RestartGame();
        else if(e.getSource()==menuItemSalir)
            System.exit(0);
        else if(e.getSource()==menuItemNivel0) {
            Variables.setNivel(0);
            setSize(300, 395);
            setLocationRelativeTo(null);
            RestartGame();
        }else if(e.getSource()==menuItemNivel1) {
            Variables.setNivel(1);
            setSize(300, 395);
            setLocationRelativeTo(null);
            RestartGame();
        }else if(e.getSource()==menuItemNivel2) {
            Variables.setNivel(2);
            setSize(468, 500);
            setLocationRelativeTo(null);
            RestartGame();
        }else if(e.getSource()==menuItemNivel3) {
            Variables.setNivel(3);
            setSize(842, 548);
            setLocationRelativeTo(null);
            RestartGame();
        }else if(e.getSource()==menuItemAcerca) {
        	new AcercaFrame().GenerateFrame();
        }
    }
    
    public static boolean isPlaying() {
        return jugando;
    }
    
    static class Sonido extends Thread {
        String str;
        public Sonido(String str) {
            this.str = str;
        }
        
        public void run() {
            try {
                final Clip sonido = AudioSystem.getClip();
                URL pathBoom = getClass().getResource(str);
                sonido.open(AudioSystem.getAudioInputStream(pathBoom));
                sonido.start();
                sonido.loop(2);
                while(sonido.getMicrosecondPosition() <= sonido.getMicrosecondLength());
                sonido.stop();
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | NullPointerException e) {
            	errorReporter("Error message: " + e.getMessage() + "\n" + e.getLocalizedMessage());
                if(Perdedor)
                    JOptionPane.showMessageDialog(null, "¡BOO000000M!", "Error con el archivo de audio", JOptionPane.ERROR_MESSAGE);
                else if(Ganador)
                    JOptionPane.showMessageDialog(null, "¡HAS GANADO!", "Error con el archivo de audio", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private static void errorReporter(String message) {
        try {
            File f = new File("JMinas_errorlog.txt");
            PrintWriter pw = new PrintWriter(f);
            String str = "Error en el archivo de audio:\n" + message;
            if(f.exists()) {
                pw.append(str);
            } else {
                pw.println(str);
            }
            pw.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }
    
    private void limpiaErrores() {
    	errorReporter.LimpiaErrores();
    }
    
    private void validaErroresAlCargar() {
    	if(!errorReporter.ExistenErrores()) {
    		return;
    	}
    	JOptionPane.showMessageDialog(null,
    			"No se pudieron cargar algunos recursos,\n" +
				"se creo el archivo JMinas_errorlog.txt con\n" +
				"informacion al respecto",
				"ERROR",
				JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String[] args) throws InterruptedException {
        new JMinasMain();
    }
}
