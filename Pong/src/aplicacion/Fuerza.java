package aplicacion;

/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	Fuerza, Sorpresa la cual Otorga al jugador que la capture, la 
			habilidad de no perder el 50% de fortaleza si es golpeado por un 
			bloque. Aplica solo una vez.Ademas de esto 1 de cada 2 veces busca a
			la pelota para ser golpeada
.
*
* @author: Santiago Buitrago
*
*
* @version 4.5 Final	
*/

public class Fuerza extends Sorpresa{
	public Pelota pelotaEnJuego;
	
	public Fuerza(int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
		pelotaEnJuego=null;
	}
	public Pelota getPelotaEnJuego(){
		return pelotaEnJuego;
	}
	@Override
	public void setPelota(Pelota p){
		
		pelotaEnJuego=p;
	}
	@Override
	public void comportamiento(Jugador jugador, Pelota pelota){
		
		if (pelotaEnJuego!=null){
		super.setDeltaX(pelotaEnJuego.getDeltaX()-1);
		super.setDeltaY(pelotaEnJuego.getDeltaY());
		super.movimientoHorizontal(super.getDeltaX());
		super.movimientoVertical(super.getDeltaY());
	}}
	
	@Override
	public boolean isFisica(){
		return true;
	}
}