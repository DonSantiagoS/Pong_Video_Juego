package aplicacion;

/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	Energy, Sorpresa la cual otorga al jugador quien golpeo un 50% de la
			fortaleza perdida.
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class Energy extends Sorpresa{
	/**
		Constructor Energy, Sorpresa la cual otorga al jugador quien golpeo un 50% de la
		fortaleza perdida.
		@param pocisionX: Pocision x en la cual se creara la Sorpresa
		@param pocisionY: Pocision y en la cual se creara la Sorpresa
		@param ancho: Corresponde a la medida de ancho de la sorpresa
		@param ancho: Corresponde a la medida de alto de la sorpresa
	*/
	public Energy(int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
	}
	
	/**
		Encargado de realizar el comportamiento correcto para esta clase, ya que
		es una sorpresa que realiza diferente accion o se comporta diferente a las
		otras sorpresas, en este caso realiza su funcion vital
	*/
	@Override 
	public void comportamiento(Jugador jugador,Pelota pelota){
		int fortalezaInicial= super.getPong().getFortaleza();
		int fortalezaJugadorActual= jugador.getFortaleza();
		jugador.setFortaleza(fortalezaJugadorActual+((fortalezaInicial-fortalezaJugadorActual)/2));
	}
	
}