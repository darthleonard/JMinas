package darth.jminas;

import darth.jminas.tools.Sonido;

public class Engine {
	private JMinasMain main;
	private PanelSuperior panelTop;
	private PanelCentral panelCentral;
	private PanelInferior panelBottom;
	private Cronometro chronometer;
	
	private boolean playing = false;
	private boolean winner = false;
	private boolean loser = false;
	
	public Engine(JMinasMain main, PanelSuperior panelSuperior, PanelCentral panelCentral, PanelInferior panelInferior) {
		this.main = main;
		this.panelTop = panelSuperior;
		this.panelCentral = panelCentral;
		this.panelBottom = panelInferior;
	}

	public void StartGame() {
        chronometer = new Cronometro();
        chronometer.start();
        playing = true;
        winner = false;
        loser = false;
    }
	
	public void RestartGame() {
        panelCentral.restart();
        panelTop.restart();
        if(chronometer != null) {
        	chronometer.setActivo(false);
        }
        playing = false;
        winner = false;
        loser = false;
    }
    
    public void LostGame() {
        chronometer.setActivo(false);
        panelCentral.Perdio();
        playing = false;
        winner = false;
        loser = true;
        new Sonido(Variables.SonidoExplosion, true).start();
    }
    
    public void WinGame() {
        StopChron();
        panelCentral.Gano();
        winner = true;
        loser = false;
        new Sonido(Variables.SonidoGanador, false).start();
    }
    
    public void StopChron() {
        chronometer.setActivo(false);
    }
}
