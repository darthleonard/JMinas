package darth.jminas;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import darth.jminas.tools.ErrorReporter;

public class PanelCentral extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	private static final long serialVersionUID = 5390823312595822624L;
	
	private int dx;
	private int dy;
	private int imgDx;
	private int imgDy;
	
	private JMinasMain minasMain;
	private Point mira;
	private Mapa mapa;
	private int contMinasMarcadas;
	
	private Graphics2D g;
	private Image imgBandera;
	private Image imgExplosion;
	private ImageIcon iconNormal;
	private ImageIcon iconClick;
	private ImageIcon iconMarca;
	private ImageIcon iconLooser;
	private ImageIcon iconWiner;
	private ImageIcon iconRiendo;
	
	public PanelCentral(JMinasMain minasMain) {
		this.minasMain = minasMain;
		mapa = new Mapa();
		contMinasMarcadas = Variables.numeroMinas;
		PanelSuperior.UpdateMinas(contMinasMarcadas);
		mira = new Point(0,0);
		
		cargarImagenes();
		
		if(iconNormal != null)
			PanelSuperior.UpdateIconStart(iconNormal, Variables.txtNormal);
		
		if(imgBandera != null) {
			imgDx = (imgBandera.getWidth(this)/4);
			imgDy = (imgBandera.getHeight(this)/4);
		}
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void restart() {
		mapa = new Mapa();
		contMinasMarcadas = Variables.numeroMinas;
		PanelSuperior.UpdateMinas(contMinasMarcadas);
		PanelSuperior.UpdateIconStart(iconNormal, " :) ");
		mira = new Point(0,0);
		repaint();
	}
	
	public void paint(Graphics aux) {
		g = (Graphics2D) aux;
		g.setFont(new Font("Serif", Font.BOLD, 18));
		FontMetrics fm=g.getFontMetrics();
		int numCerc;
		String strNum;
		dx = getWidth()/Variables.ancho;
		dy = getHeight()/Variables.alto;
		
		g.setColor(Color.gray);
		for (int i = 0; i < Variables.alto; i++)
			g.drawLine(0, i*dy, getWidth(), i*dy);
		
		g.setColor(Color.lightGray);
		for (int i = 0; i < Variables.ancho+1; i++)
			g.drawLine(i*dx, 0, i*dx, getHeight());
		
		for (int i = 0; i < Variables.ancho; i++) {
			for (int j = 0; j < Variables.alto; j++) {
				//g.setColor(Color.gray);
		        g.setPaint(new GradientPaint(11, 10, Color.gray, 25, 25, Color.lightGray, true));
				//if(mapa.TieneMina(i, j))
				//	g.setColor(Color.red);
				g.fillRect(i*dx+1, j*dy+1, dx-1, dy-1);
				
				if(mapa.Abierta(i, j)) {
					if(mapa.TieneMina(i, j)) {
						if(mapa.Marcada(i, j))
							g.setPaint(new GradientPaint(11, 10, new Color(0,255,0), 25, 25, new Color(0,200,0), true));
						else
							g.setPaint(new GradientPaint(11, 10, new Color(255,0,0), 25, 25, new Color(200,0,0), true));
						g.fillRect(i*dx+1, j*dy+1, dx-1, dy-1);
						if(imgExplosion != null)
							g.drawImage(imgExplosion, i*dx+(dx/4), j*dy+(dy/4), dx-(dx/2), dy-(dy/2), this);
					}else {
						g.setPaint(new GradientPaint(11, 10, new Color(200,200,200), 25, 25, new Color(230,230,230), true));
						g.fillRect(i*dx+1, j*dy+1, dx-1, dy-1);
					}
					
					numCerc = mapa.getCercanas(i, j);
					if(numCerc != 0 && !mapa.TieneMina(i, j)){
						strNum = "" + numCerc;
						g.setColor(Variables.getColorCantidad(mapa.getCercanas(i, j)));
						g.drawString(strNum, i*dx+dx/2-fm.stringWidth(strNum)/3, j*dy+dy/2+fm.getHeight()/3);
					}
				}else if(mapa.Marcada(i, j)) {
					if(imgBandera != null)
						g.drawImage(imgBandera, i*dx+imgDx, j*dy+imgDy, dx-(dx/3), dy-(dy/3), this);
					else {
						g.setPaint(new GradientPaint(11, 10, new Color(0,255,0), 25, 25, new Color(0,200,0), true));
						g.fillRect(i*dx, j*dy, dx, dy);
					}
				}
			}
		}
		g.setColor(Color.black);
		g.drawRect(mira.x*dx+1, mira.y*dy+1, dx-2, dy-2);
	}
	
	public void Perdio() {
		PanelSuperior.UpdateIconStart(iconLooser,Variables.txtLooser);
		mapa.AbrirTodo();
		repaint();
	}
	
	public void Gano() {
		PanelSuperior.UpdateIconStart(iconWiner,Variables.txtWinner);
	}
	
	private void abrir(int x, int y, int op) {
		if(x < 0 || x >= Variables.ancho || y < 0 || y >= Variables.alto)
			return;
		if(minasMain.Ganador)
			return;
		if(!minasMain.isPlaying()){
			minasMain.StartGame();
			
			// se reliza una pequeña pausa por si la primer casilla abierta
			// contiene una mina, o el cronometro podría continuar la ejecucion
			try {
				Thread.sleep(5);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		switch(op) {
		case 0:
			if(!mapa.Marcada(x, y)) {
				if(!mapa.Abrir(x,y)) {
					minasMain.LostGame();
					mapa.AbrirMina(x, y);
				}
				if(valida()) {
					minasMain.WinGame();
				}
			}
			break;
		case 1:
			if(!mapa.Abierta(x, y)){
				if(mapa.MarcaMina(x, y))
					--contMinasMarcadas;
				else
					++contMinasMarcadas;
				PanelSuperior.UpdateMinas(contMinasMarcadas);
			}
			break;
		}
		repaint();
	}
	
	private boolean valida() {
		return mapa.getCeldasAbiertas() == (Variables.ancho * Variables.alto - Variables.numeroMinas);
	}
	
	private void cargarImagenes() {
		imgBandera = cargaImagenIndividual(Variables.pathBandera).getImage();
		imgExplosion = cargaImagenIndividual(Variables.pathExplosion).getImage();
		iconNormal = cargaImagenIndividual(Variables.pathNormal);
		iconClick = cargaImagenIndividual(Variables.pathClick);
		iconMarca = cargaImagenIndividual(Variables.pathMarca);
		iconLooser = cargaImagenIndividual(Variables.pathLooser);
		iconWiner = cargaImagenIndividual(Variables.pathWinner);
		iconRiendo = cargaImagenIndividual(Variables.pathRiendo);
	}
	
	private ImageIcon cargaImagenIndividual(String path) {
		try {
			ImageIcon icon = new ImageIcon(getClass().getResource(path));
			return icon;
		} catch(NullPointerException e) {
			new ErrorReporter().CreateLog(path);
			return null;
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1)
			PanelSuperior.UpdateIconStart(iconClick,Variables.txtClick);
		else
			PanelSuperior.UpdateIconStart(iconMarca,Variables.txtMarca);
		
		int x = e.getX()/dx;
		int y = e.getY()/dy;
		if(x != mira.getX() || y != mira.getY())
			mira = new Point(x,y);
	}
	
	public void mouseReleased(MouseEvent e) {
		PanelSuperior.UpdateIconStart(iconNormal,Variables.txtNormal);
		int x = e.getX()/dx;
		int y = e.getY()/dy;
		if(mira.x != x || mira.y != y)
			return;
		
		if(e.getButton() == 3 || e.getButton() == 2)
			abrir(x, y, 1);
		else if(e.getButton() == 1)
			abrir(x, y, 0);
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {

		int x = e.getX()/dx;
		int y = e.getY()/dy;
		if(x != mira.getX() || y != mira.getY()){
			mira = new Point(x,y);
			if(minasMain.isPlaying()) {
				repaint();
			}
		}
	}
	public void mouseDragged(MouseEvent e) {}

	public void keyPressed(KeyEvent e) {
		int x = (int) mira.getX();
		int y = (int) mira.getY();
		
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			if(x > 0) {
				mira = new Point(x-1, y);
				repaint();
			}else {
				mira = new Point(Variables.ancho-1, y);
				repaint();
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(x < Variables.ancho-1) {
				mira = new Point(x+1, y);
				repaint();
			}else {
				mira = new Point(0, y);
				repaint();
			}
			break;
		case KeyEvent.VK_UP:
			if(y > 0) {
				mira = new Point(x,y-1);
				repaint();
			}else {
				mira = new Point(x,Variables.alto-1);
				repaint();
			}
			break;
		case KeyEvent.VK_DOWN:
			if(y < Variables.alto-1) {
				mira = new Point(x,y+1);
				repaint();
			}else {
				mira = new Point(x,0);
				repaint();
			}
			break;
		case KeyEvent.VK_ENTER:PanelSuperior.UpdateIconStart(iconClick,Variables.txtClick);
			abrir(x, y, 0);
			break;
		case KeyEvent.VK_SPACE:PanelSuperior.UpdateIconStart(iconClick,Variables.txtClick);
			abrir(x, y, 0);
			break;
		case KeyEvent.VK_CONTROL:
			PanelSuperior.UpdateIconStart(iconMarca,Variables.txtMarca);
			abrir(x, y, 1);
			break;	
		case KeyEvent.VK_N:
			PanelSuperior.UpdateIconStart(iconRiendo,Variables.txtRiendo);
			minasMain.RestartGame();
			break;
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		if(minasMain.Ganador) {
			PanelSuperior.UpdateIconStart(iconWiner,Variables.txtWinner);
		} else {
			PanelSuperior.UpdateIconStart(iconNormal,Variables.txtNormal);
		}
	}
}
