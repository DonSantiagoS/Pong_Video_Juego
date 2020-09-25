package aplicacion;

/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	PhantomRacket, Sorpresa la cual permite a la raqueta del jugador quien
			golpeo lanzar dos pelotas en el siguiente golpe, de estas una sera falsa
			y visible, la otra sera invisible y verdadera la cual ser volvera visible
			cuando la falsa salga del campo.
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	

*/
public class PhantomRacket extends Sorpresa{
	/**
		Constructor PhantomRacket, Sorpresa la cual permite a la raqueta del jugador quien
		golpeo lanzar dos pelotas en el siguiente golpe, de estas una sera falsa
		y visible, la otra sera invisible y verdadera la cual ser volvera visible
		cuando la falsa salga del campo.
		@param pocisionX: Pocision x en la cual se creara la Sorpresa
		@param pocisionY: Pocision y en la cual se creara la Sorpresa
		@param ancho: Corresponde a la medida de ancho de la sorpresa
		@param ancho: Corresponde a la medida de alto de la sorpresa
	*/	
	public PhantomRacket(int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
	}
	/**
		Encargado de realizar el comportamiento correcto para esta clase, ya que
		es una sorpresa que realiza diferente accion o se comporta diferente a las
		otras sorpresas, en este caso realiza su funcion vital
	*/
	@Override 
	public void comportamiento(Jugador jugador,Pelota pelota){
		pelota.setFisica(false);
		Pelota pelotaFantasma= pelota.clonar();
		pelotaFantasma.setDeltaX(-1*pelotaFantasma.getDeltaX());
		pelotaFantasma.setDeltaY(-1*pelotaFantasma.getDeltaY());
		super.getPong().addPelotaDirecta(pelotaFantasma,"Fantasma");
	}
}