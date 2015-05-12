package darth.jminas;

public class Cronometro extends Thread {
	private boolean activo = false;
	
	public void run() {
		int min = 0, seg = 0;
		activo = true;
		while(activo) {
			try {
				PanelSuperior.UpdateTime(min, seg);
				Thread.sleep(999);
				seg++;
				if(seg == 60) {
					seg = 0;
					min += 1;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
