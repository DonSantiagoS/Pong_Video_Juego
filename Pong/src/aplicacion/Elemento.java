package aplicacion;

import java.io.Serializable;
import java.awt.Rectangle;
/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public abstract class Elemento implements Serializable{
   
	protected int posicionX; 
	protected int posicionY; 
	private int deltaX;
	private int deltaY;
	protected int alto;
	protected int ancho; 
	private int pocisionOX;
	private int pocisionOY;


   /**
    * Constructor para la clase Elemento
    * @param x posicion en coordenada x
    * @param y posicion en coordenada y
    * @param h alto del objeto
    * @param w ancho del objeto
    */
   public Elemento(int x, int y, int h, int w) {
       posicionX = x;
       posicionY = y;
       alto = h;
       ancho = w;
	   deltaX=0;
	   deltaY=0;
	   pocisionOX=x;
	   pocisionOY=y;
   }
   public void movimientoHorizontal(int deltaPocisionX){
		posicionX += deltaPocisionX;
	}
	public void movimientoVertical(int deltaPocisionY){
		posicionY += deltaPocisionY;
	}
   public int getPocisionX()
   {
       return posicionX;
   }
   
   public void setPocisionX(int x)
   {
       posicionX = x;
   }
   
   public int getPocisionY()
   {
       return posicionY;
   }
   
   public void setPocisionY(int y)
   {
       posicionY = y;
   }
   public Rectangle getArea(){
	   return new Rectangle (posicionX,posicionY,ancho, alto);
   }
   public int getAlto()
   {
       return alto;
   }
   
   public int getAncho()
   {
       return ancho;
   }   
   	public void setDeltaX(int x){
		deltaX=x;
	}
	public void setDeltaY(int x){
		deltaY=x;
	}
	public int getDeltaX(){
		return deltaX;
	}
	public int getDeltaY(){
		return deltaY;
	}
	public int pocisionOriginalX(){
		return pocisionOX;
	}
	public int pocisionOriginalY(){
		return pocisionOY;
	}
	public boolean isFisica(){
		return true;
	}
	
	
}