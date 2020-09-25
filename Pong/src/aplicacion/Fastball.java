package aplicacion;

/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	Fastball, Sorpresa la cual aumenta la velocidad de la pelota en un 20%
			basado en la velocidad normal.
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class Fastball extends Sorpresa{
	/**
		Constructor Fastball, Sorpresa la cual aumenta la velocidad de la pelota en un 20%
		basado en la velocidad normal.
		@param pocisionX: Pocision x en la cual se creara la Sorpresa
		@param pocisionY: Pocision y en la cual se creara la Sorpresa
		@param ancho: Corresponde a la medida de ancho de la sorpresa
		@param ancho: Corresponde a la medida de alto de la sorpresa
	*/	
	public Fastball(int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
	}
	
	/**
		Encargado de realizar el comportamiento correcto para esta clase, ya que
		es una sorpresa que realiza diferente accion o se comporta diferente a las
		otras sorpresas, en este caso realiza su funcion vital
	*/
	@Override 
	public void comportamiento(Jugador jugador,Pelota pelota){
		if(pelota!=null){
			pelota.setVelocidadX(pelota.getVelocidadX()+(pelota.getVelocidadX()+1));
			pelota.setVelocidadY(pelota.getVelocidadY()+(pelota.getVelocidadY()+1));
		}
	}
}