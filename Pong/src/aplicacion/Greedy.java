package aplicacion;

import java.util.*;
/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	Greedy, perfil en el cual se es especialist en pegarle a los premios
			que aparecen. Si no hay premio se comporta como un Extreme
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class Greedy extends Extreme{
	
	public ArrayList<Sorpresa> haySorpresas;
	
	public Greedy(int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
		haySorpresas= new ArrayList<Sorpresa>();
	}
	
	@Override
	public boolean habilidad(Pelota pelota){
		String tipo= super.getPong().getSorpresas().get(0); 
		haySorpresas= super.getPong().getSorEspe(tipo);
		if (haySorpresas.size()!=0){
			System.out.println("haysorpresas");
			System.out.println(haySorpresas.get(0));
			int X=calcularDelta(pelota.getPocisionX(),haySorpresas.get(0).getPocisionX());
			int Y=calcularDelta(pelota.getPocisionY(),haySorpresas.get(0).getPocisionY());
			
			System.out.println(X);
			System.out.println(Y);
			pelota.setDeltaX((calcularDelta(pelota.getPocisionX(),haySorpresas.get(0).getPocisionX()))/1000);
			pelota.setDeltaY((calcularDelta(pelota.getPocisionY(),haySorpresas.get(0).getPocisionY()))/1000);
		}
		return true;
	}
	
	public int calcularDelta(int n1, int n2){
		return (n2-n1);
	}
}