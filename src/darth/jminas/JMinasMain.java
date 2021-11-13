package darth.jminas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import darth.jminas.gui.AcercaFrame;
import darth.jminas.tools.ErrorReporter;
import darth.jminas.tools.Sonido;

public class JMinasMain extends JFrame implements ActionListener {
    private static final long serialVersionUID = 5263848303195260404L;
    
    private JMenuBar menuBar;
    private JMenu menuOpciones, menuMas, menuNivel;
    private JMenuItem menuItemNuevo, menuItemSalir, menuItemEstadisticas, menuItemAcerca,
    	menuItemNivel0, menuItemNivel1, menuItemNivel2, menuItemNivel3;
    
    private ErrorReporter errorReporter;
    
    private PanelSuperior panelSuperior;
    private PanelCentral panelCentral;
    private PanelInferior panelInferior;
    
    private static Cronometro cronometro;
    
    private boolean jugando = false;
    public boolean Ganador = false;
    
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
        panelSuperior = new PanelSuperior(this);
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
    
    public void StartGame() {
        cronometro = new Cronometro();
        cronometro.start();
        jugando = true;
        Ganador = false;
    }
    
    public void RestartGame() {
        panelCentral.restart();
        panelSuperior.restart();
        if(cronometro != null)
            cronometro.setActivo(false);
        jugando = false;
        Ganador = false;
    }
    
    public void LostGame() {
        cronometro.setActivo(false);
        panelCentral.Perdio();
        jugando = false;
        Ganador = false;
        new Sonido(Variables.SonidoExplosion, Ganador).start();
    }
    
    public void WinGame() {
        StopChron();
        panelCentral.Gano();
        Ganador = true;
        new Sonido(Variables.SonidoGanador, Ganador).start();
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
    
    public boolean isPlaying() {
        return jugando;
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
