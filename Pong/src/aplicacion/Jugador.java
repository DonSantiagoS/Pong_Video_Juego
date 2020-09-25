package aplicacion;

import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.event.ActionListener;

/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class Jugador extends Elemento{
	
	private int fortaleza;
	private int puntaje;
	private Jugador rival;
	private boolean goLeft;
    private boolean goRight;
    private boolean goUp;
    private boolean goDown;
	private String direccionMovimiento;
	protected int velocidadX;
	protected int velocidadY;
	private String poder;
	private int pocisionOriginalX;
	private int pocisionOriginalY;
	private javax.swing.Timer reloj;
	private Pong pong;
	private int lado;
	
	public Jugador(int pocisionX, int pocisionY,int ancho, int alto){
	
		super(pocisionX,pocisionY, ancho, alto);
		fortaleza=0;
		puntaje=0;
		lado=0;
		velocidadX=20;
		velocidadY=20;
		pocisionOriginalX=pocisionX;
		pocisionOriginalY=pocisionY;
		rival=null;
	}
	public void setLado(int ladoNuevo){
		lado=ladoNuevo;
	}
	public void setPong(Pong p){
		pong=p;
	}
	public Pong getPong(){
		return pong;
	}
	public int getFortaleza(){
		return fortaleza;
	}
	public void setFortaleza(int actualizacion){
		fortaleza=actualizacion;
	}
	public String getPoder(){
		return poder;
	}
	public void setPoder(String p){
		poder=p;
	}
	public void setRival(Jugador jugador){
		rival= jugador;
	}
	public Jugador getRival(){
		return rival;
	}
	public boolean habilidad(Pelota pelota){
		return false;
	}
	
	public void retraso(int tiempoRetraso,int velocidad){
		reloj = new Timer (tiempoRetraso, new ActionListener (){
									public void actionPerformed(ActionEvent e)
									{if(reloj.isRunning()){
											velocidadX=velocidad;
											velocidadY=velocidad;
										}
										else{
											velocidadX=20;
											velocidadY=20;
										}
										
									 }
								});
		reloj.start();
		
	}
	public void movimiento(){}
	public void mover(){	
		super.movimientoHorizontal(super.getDeltaX()*velocidadX);
		super.movimientoVertical(super.getDeltaY()*velocidadY);
	}
	public void setPuntaje(int pu){
		puntaje=pu;
		pong.setPuntajeMio(lado,pu);
	}
	public int getPuntaje(){
		
		return pong.getPuntajeMio(lado);
		
	}
	public int getPocisionOriginalX(){
		return pocisionOriginalX;
	}
	public void setPocisionOriginalX(int pX){
		pocisionOriginalX=pX;
	}
	public int getPocisionOriginalY(){
		return pocisionOriginalY;
	}
}