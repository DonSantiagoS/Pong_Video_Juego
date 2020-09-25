package aplicacion;

import java.util.*;
/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	Pelota Incremental, la cual cada 8  golpes de jugadores, aumenta su
			velocidad en 1.(Pueden ser 8 Golpes consecutivos del mismo jugador)
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class Incremental extends Pelota{
	
	private ArrayList<Jugador> golpesAnteriores;
	
	/**
		Constrcutor:Pelota Incremental, la cual cada 8  golpes de jugadores, aumenta su
		velocidad en 1.(Pueden ser 8 Golpes consecutivos del mismo jugador)
		@param pocisionX: Pocision x en la cual se creara la Sorpresa
		@param pocisionY: Pocision y en la cual se creara la Sorpresa
		@param ancho: Corresponde a la medida de ancho de la sorpresa
		@param ancho: Corresponde a la medida de alto de la sorpresa
	*/	
	public Incremental(int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
		golpesAnteriores= new ArrayList<Jugador>();
	}
	
	/**
		Metodo sobre escrito de la clase Pelota, ya que la pelota incremental en cada
		movimiento debe verificar si tiene que incrementar su velocidad o seguir igual
	*/
	@Override
	public void mover(){
		System.out.println(super.getVelocidadX());
		incrementarVelocidad();
		super.movimientoHorizontal(super.getDeltaX()*super.getVelocidadX());
		super.movimientoVertical(super.getDeltaY()*super.getVelocidadY());
	}
	
	/**
		Incrementa la Velocidad, verificando la condicion de que ha tenido 8 golpes
		por parte de los jugadores (Pueden ser 8 Golpes consecutivos del mismo jugador)
	*/
	public void incrementarVelocidad(){
		if (golpesAnteriores.size()==8){
			super.setVelocidadX(super.getVelocidadX()+1);
			super.setVelocidadY(super.getVelocidadY()+1);
			golpesAnteriores.clear();
		}
	}
	/**
		Encargado de actualizar quien le dio el ultimo Golpe a esta pelota
		Para esta clase, guardamos los jugadores que le han golpeado
		@param jugador: Jugador quien la golpeo por ultima vez
	*/
	@Override
	public void setUltimoGolpe(Jugador jugador){
		golpesAnteriores.add(super.ultimoGolpe());
		super.setUltimoGolpe(jugador);
	}
}