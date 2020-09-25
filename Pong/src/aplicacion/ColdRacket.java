package aplicacion;

//import javax.swing.Timer;
/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	ColdRacket, Sorpresa la cual permite que la raqueta del jugador quien
			golpeo convertir la Pelota en Hielo, lo que generara que el oponente 
			se congele.
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class ColdRacket extends Sorpresa{
		
	private Jugador ultimoGolpeOriginal;
	private Pelota pelotaFlash;
	
	/**
		Constructor Energy, Sorpresa la cual otorga al jugador quien golpeo un 50% de la
		fortaleza perdida.
		@param pocisionX: Pocision x en la cual se creara la Sorpresa
		@param pocisionY: Pocision y en la cual se creara la Sorpresa
		@param ancho: Corresponde a la medida de ancho de la sorpresa
		@param ancho: Corresponde a la medida de alto de la sorpresa
	*/	
	public ColdRacket(int pocisionX, int pocisionY,int ancho, int alto){
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
		if (!(pelota==null)){
			Pelota congelada= pelota.clonar();
			congelada.setTipoPelota("Congelada");
			
			
			super.getPong().addPelotaDirecta(congelada,"Congelada");
			//super.getPong().eliminarPelotaDirecta(pelota,pelota.getTipoPelota());
		}
		
	}
	
	/**
		Encargado de poner a correr el tiempo del poder, correspondiente 
		a este tipo de sorpresa
	
	*/
	@Override
	public void iniciarPoder(){
		super.setDuracionPoder(300);
	}
	
	@Override
	public void acabo(){
		if (!(pelotaFlash.ultimoGolpe().equals(ultimoGolpeOriginal))){
			ultimoGolpeOriginal.retraso(3000,0);
		}
		else{
			iniciarPoder();
		}
	}
}