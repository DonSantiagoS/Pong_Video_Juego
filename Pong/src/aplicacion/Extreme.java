package aplicacion;

import java.util.*;
/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	Extreme, perfil en el cual se busca devolver la pelota al lado contrario
			de donde la recibio.
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class Extreme extends Jugador{
	
	private Pelota pelota;
	private int posXPelota;
	private int cantidadMovimientos;
	public Extreme (int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
	}
	@Override
	public void movimiento(){
		
		alistarPelota();
		if (pelota!=null){
			posXPelota=pelota.getPocisionX();
		if(pelota.getDeltaX()!=0){
			int cantidadMovimientosX=((super.getPocisionX()-posXPelota)/(pelota.getDeltaX()));
			cantidadMovimientos=Math.abs(cantidadMovimientosX);
			int miNuevaPosicionY= pelota.getPocisionY()+(pelota.getDeltaY()*cantidadMovimientosX);
			if (miNuevaPosicionY>1000){miNuevaPosicionY=970;}
			if (miNuevaPosicionY<0){miNuevaPosicionY=10;}
			if (miNuevaPosicionY>super.getPocisionY()){super.setDeltaY(1);}
			else if (miNuevaPosicionY<super.getPocisionY()){super.setDeltaY(-1);}
			else {super.setDeltaY(0);}
			if (miNuevaPosicionY<super.getPocisionY()){
				super.mover();
			}
		}
		}
	}
	
	public void alistarPelota(){
		pelota= super.getPong().getPelotaActiva();
	}
}