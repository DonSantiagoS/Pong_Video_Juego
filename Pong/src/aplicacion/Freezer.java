package aplicacion;

/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	Freezer, Sorpresa la cual Congela al jugador rival por un tiempo de 
			2 segundos.
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class Freezer extends Sorpresa{
	/**
		Constructor Freezer, Sorpresa la cual Congela al jugador rival por un tiempo de 
		2 segundos.
		@param pocisionX: Pocision x en la cual se creara la Sorpresa
		@param pocisionY: Pocision y en la cual se creara la Sorpresa
		@param ancho: Corresponde a la medida de ancho de la sorpresa
		@param ancho: Corresponde a la medida de alto de la sorpresa
	*/	
	public Freezer(int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
	}
	
	/**
		Encargado de realizar el comportamiento correcto para esta clase, ya que
		es una sorpresa que realiza diferente accion o se comporta diferente a las
		otras sorpresas, en este caso realiza su funcion vital
		@param jugador: Jugador quien tomo la Sopresa
		@param pelota: Pelota que tomo la Sorpresa
	*/
	@Override 
	public void comportamiento(Jugador jugador,Pelota pelota){
			Jugador rival= jugador.getRival();
			while(super.getDuracionPoder().isRunning()){
				rival.retraso(5,0);
			}
	}
	
	/**
		Encargado de poner a correr el tiempo del poder, correspondiente 
		a este tipo de sorpresa
	*/
	@Override
	public void iniciarPoder(){
		super.setDuracionPoder(2000);
	}
}