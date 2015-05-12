package darth.jminas;

public class Mapa {
	private Celda mapa[][];
	private int celdasAbiertas;
	
	public Mapa() {
		mapa = new Celda[Variables.ancho][Variables.alto];
		for (int i = 0; i < Variables.ancho; i++) {
			for (int j = 0; j < Variables.alto; j++) {
				mapa[i][j] = new Celda();
			}
		}
		colocaMinas();
		celdasAbiertas = 0;
	}
	
	private void colocaMinas() {
		int x,y,cont=0;
		while(cont < Variables.numeroMinas) {
			x = (int) (Math.random()*Variables.ancho-0);
			y = (int) (Math.random()*Variables.alto-0);
			if(mapa[x][y].mina)
				continue;
			mapa[x][y].mina = true;
			sumaMina(x-1, y-1);sumaMina(x, y-1);sumaMina(x+1, y-1);
			sumaMina(x-1, y);					sumaMina(x+1, y);
			sumaMina(x-1, y+1);sumaMina(x, y+1);sumaMina(x+1, y+1);
			//System.out.println(x+"-"+y);
			cont++;
		}
	}
	
	private void sumaMina(int x, int y) {
		if(x < 0 || y < 0 || x >= Variables.ancho || y >= Variables.alto)
			return;
		else
			mapa[x][y].cantidadCercanas++;
	}
	
	public boolean TieneMina(int x, int y) {
		return mapa[x][y].mina;
	}
	
	public boolean Abierta(int x, int y) {
		return mapa[x][y].abierta;
	}
	
	/**
	 * Regresa <b>false</b> si contiene una mina
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean Abrir(int x, int y) {
		if(mapa[x][y].mina)
			return false;
		
		abrir(x,y);
		return true;
	}
	
	private void abrir(int x, int y) {
		if(x < 0 || y < 0 || x >= Variables.ancho || y >= Variables.alto || mapa[x][y].abierta)
			return;
		else
			if(!mapa[x][y].mina && !mapa[x][y].marcada){
				mapa[x][y].abierta=true;
				celdasAbiertas++;
				
				if(mapa[x][y].cantidadCercanas == 0){
					abrir(x-1, y-1);abrir(x, y-1);abrir(x+1, y-1);
					abrir(x-1, y);				  abrir(x+1, y);
					abrir(x-1, y+1);abrir(x, y+1);abrir(x+1, y+1);					
				}
			}
	}
	
	public boolean Marcada(int x, int y) {
		return mapa[x][y].marcada;
	}
	
	public boolean MarcaMina(int x, int y) {
		mapa[x][y].marcada = !mapa[x][y].marcada;
		return mapa[x][y].marcada; 
	}
	
	public void AbrirMina(int x, int y) {
		mapa[x][y].abierta=true;
	}
	
	public void AbrirTodo() {
		for (int i = 0; i < Variables.ancho; i++) {
			for (int j = 0; j < Variables.alto; j++) {
				mapa[i][j].abierta = true;
			}
		}
	}
	
	public int getCercanas(int x, int y) {
		return mapa[x][y].cantidadCercanas;
	}
	
	public int getCeldasAbiertas() {
		return celdasAbiertas;
	}
	
	class Celda {
		boolean abierta = false;
		boolean mina = false;
		boolean marcada = false;
		int cantidadCercanas = 0;
	}
}
