package aplicacion;

import java.io.Serializable;

/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE: Pelota, objeto principal para el concepto del Juego, ya que esta es la que
		 define los puntos, que pueden ser marcados y es el concepto con el cual ser
		 juega la partida. 
		 Exiten 4 tipos:
			- Lenta
			- Rapida
			- Normal
			- Incremental
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class Pelota extends Elemento implements Serializable{
	
	private Jugador ultimoGolpe; 
	private int velocidadX;
	private int velocidadY;
	private static final int diametro=50;
	private boolean fisica;
	private String tipoPelota;
	
	
	/**
		Constructor Pelota, objeto principal para el concepto del Juego, ya que esta es la que
		 define los puntos, que pueden ser marcados y es el concepto con el cual ser
		 juega la partida. 
	*/
	public Pelota(int pocisionX, int pocisionY,int ancho, int alto ){
		super(pocisionX,pocisionY, ancho, alto);
		//velocidadX=1;
		//velocidadY=1;
		ultimoGolpe=null;
		fisica=true;
	}
	/**
		Encargado de informar la Velocidad que la pelota tiene en el Vector X
		@return Velocidad que tiene la Pelota en X
	*/
	public int getVelocidadX(){
		return velocidadX;
	}
	/**
		Encargado de informar la Velocidad que la pelota tiene en el Vector Y
		@return Velocidad que tiene la Pelota en Y
	*/
	public int getVelocidadY(){
		return velocidadY;
	}
	/**
		Encargado de actualizar la Velocidad que la pelota tiene en el Vector X
		@param Velocidad que tendra la Pelota en X
	*/
	public void setVelocidadX(int cambio){
		velocidadX=cambio;
	}
	/**
		Encargado de actualizar la Velocidad que la pelota tiene en el Vector Y
		@param Velocidad que tendra la Pelota en Y
	*/
	public void setVelocidadY(int cambio){
		velocidadY=cambio;
	}
	/**
		Encargado de informar el diametro de la pelota
		@param diametro de la pelota
	*/
	public int getDiametro(){
		return diametro;
	}
	/**
		Encargado de informar quien le dio el ultimo Golpe a esta pelota
		@return jugador: Jugador quien la golpeo por ultima vez
	*/
	public Jugador ultimoGolpe(){
		return ultimoGolpe;
	
	}
	/**
		Encargado de actualizar quien le dio el ultimo Golpe a esta pelota
		@param jugador: Jugador quien la golpeo por ultima vez
	*/
	public void setUltimoGolpe(Jugador jugador){
		ultimoGolpe=jugador;
	}
	/**
		Encargado de reiniciar la Pelota, poniendola en los valores iniciales y por 
		defecto de cuando se crea
	*/
	public void reiniciar(){
		super.setDeltaX(0);
		super.setDeltaY(0);
		super.setPocisionX(750);
		super.setPocisionY(500);
		setTipoPelota(tipoPelota);
		fisica=true;
	}
	/**
		Encargado de manejar el movimiento de la Pelota de manera que 
		se desplace lo indicado en X y Y
	*/
	public void mover(){
		super.movimientoHorizontal(super.getDeltaX()*velocidadX);
		super.movimientoVertical(super.getDeltaY()*velocidadY);
	}
	/**
		Encargado de Crear una Pelota con las mismas caracteristicas que esta
		@return Nueva Pelota con las mismas caracteristicas
	*/
	public Pelota clonar(){
		Pelota pelotaNueva = new Pelota(super.getPocisionX(),super.getPocisionY(),super.getAncho(),super.getAlto());
		pelotaNueva.setDeltaX(super.getDeltaX());
		pelotaNueva.setDeltaY(super.getDeltaY());
		pelotaNueva.setUltimoGolpe(this.ultimoGolpe);
		pelotaNueva.setVelocidadX(this.getVelocidadX());
		pelotaNueva.setVelocidadY(this.getVelocidadY());
		return pelotaNueva;
	}
	/**
		Metodo sobre escrito que define si es Fisica o intangible la Pelota
		@return Si la pelota es Fisica
	*/
	@Override
	public boolean isFisica(){
		return fisica;
	}
	/**
		Encarda de cambiar el estado fisico de la Pelota
		@param esFisica : Valor que define si es Fisica la pelota
	*/
	public void setFisica(boolean esFisica){
		fisica=esFisica;
	}
	/**
		Encargado de informar el tipo de Pelota que es
		@return Tipo de Pelota
	*/
	public String getTipoPelota(){
		return tipoPelota;
	}
	/**
		Encargado de cambiar el tipo de Pelota que a su vez define la velocidadX
		en X y Y de la mismas
		@return tipo de Pelota que se tendra
	*/
	public void setTipoPelota(String tipo){
			 tipoPelota=tipo;
			 if (tipo.equals("Rapida")){
				 
				 velocidadX=5;
				 velocidadY=5;
			 }
			 else if((tipo.equals("Lenta")) ||(tipo.equals("Incremental"))){
				velocidadX=1;
				velocidadY=1; 
			 }
			 else{
				 velocidadX=3;
				 velocidadY=3;
			 }
	}	
}