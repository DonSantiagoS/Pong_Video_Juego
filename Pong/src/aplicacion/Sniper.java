package aplicacion;

import java.util.*;

/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	Sniper, perfil en el cual se es especialista en pegarle a los blancos;
			si no hay obetivos se comporta como un perfil Extreme.
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class Sniper extends Extreme{
	
	private ArrayList<Sorpresa> hayObjetivos;
	
	public Sniper(int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
		hayObjetivos = new ArrayList<Sorpresa>();
		
	}
	
	@Override
	public boolean habilidad(Pelota pelota){
		hayObjetivos= super.getPong().getSorEspe("Objetivo");
		if (hayObjetivos.size()!=0){
			pelota.setDeltaX(calcularDelta(pelota.getPocisionX(),hayObjetivos.get(0).getPocisionX()));
			pelota.setDeltaY(calcularDelta(pelota.getPocisionY(),hayObjetivos.get(0).getPocisionY()));
		}
		return true;
	}
	public int calcularDelta(int n1, int n2){
		return (n2-n1);
	}
}