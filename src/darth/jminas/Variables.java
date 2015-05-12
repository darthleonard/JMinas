package darth.jminas;

import java.awt.Color;

public class Variables {
	private static int nivel = 1;
	public static int alto = 9;
	public static int ancho = 9;
	public static int numeroMinas = 18;
	
	public static final Color color1 = Color.blue, color2 = new Color(00,99,00), color3 = Color.red,
							  color4 = Color.magenta, color5 = Color.yellow, color6 = Color.cyan,
							  color7 = Color.orange, color8 = Color.black;
	
	public static final String pathIcoCronometro = "/img/cronometro.png";
	public static final String pathIcoMinas = "/img/mina.png";
	public static final String pathBandera = "/img/bandera.png";
	public static final String pathExplosion = "/img/boom.png";
	public static final String pathNormal = "/img/cool_32x32.png";
	public static final String pathClick = "/img/surprised_32x32.png";
	public static final String pathMarca = "/img/question_32x32.png";
	public static final String pathLooser = "/img/crying_32x32.png";
	public static final String pathWinner = "/img/party_32x32.png";
	public static final String pathRiendo = "/img/laughtingoutloud_32x32.png";
	public static final String SonidoExplosion = "boom.wav";
	public static final String SonidoGanador = "win.wav";
	
	public static final String txtNormal = " :) ";
	public static final String txtClick = " :O ";
	public static final String txtMarca = " :| ";
	public static final String txtWinner = " XD ";
	public static final String txtLooser = " :( ";
	public static final String txtRiendo = " ... ";
	
	public static void setNivel(int val) {
		nivel = val;
		switch(val) {
		case 0:
			alto = 9;
			ancho = 9;
			numeroMinas = 9;
			break;
		case 1:
			alto = 9;
			ancho = 9;
			numeroMinas = 18;
			break;
		case 2:
			alto = 16;
			ancho = 16;
			numeroMinas = 40;
			break;
		case 3:
			alto = 16;
			ancho = 30;
			numeroMinas = 99;
			break;
		}
	}
	
	public static int getNivel() {
		return nivel;
	}
	
	public static Color getColorCantidad(int op) {
		switch(op) {
			case 1: return color1; case 2: return color2; case 3: return color3; case 4: return color4;
			case 5: return color5; case 6: return color6; case 7: return color7; case 8: return color8;
			default: return Color.black;
		}
	}
}
