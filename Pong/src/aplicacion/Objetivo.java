package aplicacion;

/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	Objetivo, Sorpresa la cual genera dos objetivos blancos uno en cada 
			extremo los cuales brindan puntaje extra entre 2 y la mitad del puntaje
			actual si son golpeados con la Pelota, estos tienen una duracion de 10
			segundos.
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class Objetivo extends Sorpresa{
	
	/**
		Constructor Objetivo, Sorpresa la cual genera dos objetivos blancos uno en cada 
		extremo los cuales brindan puntaje extra entre 2 y la mitad del puntaje
		actual si son golpeados con la Pelota, estos tienen una duracion de 10
		segundos.
		@param pocisionX: Pocision x en la cual se creara la Sorpresa
		@param pocisionY: Pocision y en la cual se creara la Sorpresa
		@param ancho: Corresponde a la medida de ancho de la sorpresa
		@param ancho: Corresponde a la medida de alto de la sorpresa
	*/	
	public Objetivo(int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
		super.setPocisionX(50);
		
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
		if((pelota!=null)){
			if (pelota.ultimoGolpe()!=null){
				int diferencia =pelota.getPocisionX()-super.getPocisionX();
				if (diferencia ==0){jugador.setPuntaje(jugador.getPuntaje()+(jugador.getPuntaje()/2));}
				else if ((diferencia>0)&&(diferencia<11)){jugador.setPuntaje(jugador.getPuntaje()+6);}
				else if ((diferencia>10)&&(diferencia<21)){jugador.setPuntaje(jugador.getPuntaje()+5);}
				else if ((diferencia>20)&&(diferencia<31)){jugador.setPuntaje(jugador.getPuntaje()+4);}
				else if ((diferencia>30)&&(diferencia<41)){jugador.setPuntaje(jugador.getPuntaje()+3);}
				else if ((diferencia>40)&&(diferencia<52)){jugador.setPuntaje(jugador.getPuntaje()+2);}
			}
		}
	}
	
	
	/**
		Encargado de poner a correr el tiempo del poder, correspondiente 
		a este tipo de sorpresa
	*/
	@Override
	public void iniciarSorpresa(){
		super.setDuracionPoder(10000);
	}
	
	/**
		Encargado de verificar si el elemento es Fisico o es intangible
		@return si el elemento es fisico o no
	*/
	@Override
	public boolean isFisica(){
		return false;
	}
}