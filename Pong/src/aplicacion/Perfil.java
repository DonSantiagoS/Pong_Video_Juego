package aplicacion;

import java.io.Serializable;
/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	Perfil, los perfiles son las diferentes formas en las cuales el jugador 
* 			correspondiente a la maquina se puede comportar.
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public abstract class Perfil extends Jugador {

	public Perfil(int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
	}
	@Override
	public boolean habilidad(Pelota pelota){
		return false;
	}
	public void movimiento(){
		System.out.println("PERFIL ENTRO");
	}
}