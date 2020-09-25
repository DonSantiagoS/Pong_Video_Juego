package aplicacion;

/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	Bloque, Sorpresa la cual genera un bloque sobre la malla para evitar
			el paso de la Pelota, o tambien pueden aparecer en el campo del oponente
			.
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class Bloque extends Sorpresa{
	/**
		Constructor Energy, Sorpresa la cual otorga al jugador quien golpeo un 50% de la
		fortaleza perdida.
		@param pocisionX: Pocision x en la cual se creara la Sorpresa
		@param pocisionY: Pocision y en la cual se creara la Sorpresa
		@param ancho: Corresponde a la medida de ancho de la sorpresa
		@param ancho: Corresponde a la medida de alto de la sorpresa
	*/	
	public Bloque(int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
	}
	
	@Override
	public boolean isFisica(){
		
		return true;
	}	
	/**
		Encargado de calcular el angulo con el cual la pelota se desplazara, 
		con base en las colisiones que la pelota correspondiente pueda tener 
		con diferentes elementos del campo de juego
		@param pelota: Pelota a la cual se le calculara el angulo de movimientoHorizontal
		@param elemento: Elemento con el cual la pelota colisiono y por lo cual se debe 
						 calcular el angulo correspondiente
		@return direccion: Devuleve el angulo en terminos de x,y como pendiente para 
						   definir el siguiente movimiento de la pelota
	*/
	@Override 
	public void comportamiento(Jugador jugador,Pelota pelota){
		
			if((jugador!=null)&&(pelota==null)){
				jugador.setFortaleza((jugador.getFortaleza()/2));
			}
			if (pelota!=null){
			
			int[] direccionMovimiento= super.getPong().calcularAngulo(pelota,this);
			super.getPong().rebotePelota(direccionMovimiento[0],direccionMovimiento[1],pelota);
			}
		
			
		
	}
}

