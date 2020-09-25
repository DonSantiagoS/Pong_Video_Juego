package aplicacion;

/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	Flash, Sorpresa la cual acelera la pelota hasta que el jugador contrario
			la devuelva o la deje ir. Luego de eso seguirá teniendo la velocidad que 
			tenía antes de ser acelerada.
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class Flash extends Sorpresa{
	
	private Jugador ultimoGolpeOriginal;
	private Pelota pelotaFlash;
	/**
		Constructor Flash, Sorpresa la cual acelera la pelota hasta que el jugador contrario
		la devuelva o la deje ir. Luego de eso seguirá teniendo la velocidad que 
		tenía antes de ser acelerada.
		@param pocisionX: Pocision x en la cual se creara la Sorpresa
		@param pocisionY: Pocision y en la cual se creara la Sorpresa
		@param ancho: Corresponde a la medida de ancho de la sorpresa
		@param ancho: Corresponde a la medida de alto de la sorpresa
	*/	
	public Flash(int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
		ultimoGolpeOriginal=null;
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
		ultimoGolpeOriginal= pelota.ultimoGolpe();
		while  (pelota.ultimoGolpe()==ultimoGolpeOriginal){
			pelota.setVelocidadX(pelota.getVelocidadX()+1);
			pelota.setVelocidadY(pelota.getVelocidadY()+1);
			pelotaFlash= pelota;
		
		}
	}
	
	/**
		Encargado de poner a correr el tiempo del poder, correspondiente 
		a este tipo de sorpresa
	
	@Override
	public void iniciarPoder(){
		super.setDuracionPoder(300);
	}*/
	
	@Override
	public void acabo(){
		if (!(pelotaFlash.ultimoGolpe().equals(ultimoGolpeOriginal))){
			pelotaFlash.setVelocidadX(1);
			pelotaFlash.setVelocidadY(1);
		}
		else{
			iniciarPoder();
		}
	}
}