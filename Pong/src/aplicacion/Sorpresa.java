package aplicacion;

import java.io.Serializable;

import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.lang.Thread;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.*;
import java.awt.event.*;
import java.lang.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

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

public class Sorpresa extends Elemento implements Serializable{
	
	private javax.swing.Timer tiempoDuracionSorpresa;
	private javax.swing.Timer tiempoDuracionPoder;
	private Pelota pelota;
	private Jugador jugador;
	private Pong pong;
	private Sorpresa s;
	
	public Sorpresa(int pocisionX, int pocisionY,int ancho, int alto){
		super(pocisionX,pocisionY, ancho, alto);
		pelota=null;
		jugador=null;
		pong=null;
		s=this;
		tiempoDuracionSorpresa= new javax.swing.Timer (10000, new ActionListener(){
								public void actionPerformed(ActionEvent e)
									{if (!(tiempoDuracionSorpresa.isRunning())){pong.removeSorpresa(s);}}});
		tiempoDuracionSorpresa.start();
	}
	
	public void setJugador(Jugador j){
		jugador=j;
	}
	public void setDuracionPoder(int duracion){
		tiempoDuracionPoder= new javax.swing.Timer (duracion, new ActionListener(){
								public void actionPerformed(ActionEvent e)
									{if (!(tiempoDuracionPoder.isRunning())){acabo();}}});
		tiempoDuracionPoder.start();
	}
	public void acabo(){}
	public javax.swing.Timer getDuracionPoder(){
		return tiempoDuracionPoder;
	}
	public void iniciarSorpresa(){
		tiempoDuracionSorpresa.start();
	}
	public Jugador getJugador(){
		return jugador;
	}
	public void setPelota(Pelota p){
		pelota=p;
	}
	public Pelota getPelota(){
		return pelota;
	}
	
	/**
		Encargado de verificar si el elemento es Fisico o es intangible
		@return si el elemento es fisico o no
	*/
	@Override
	public boolean isFisica(){
		return false;
	}
	public Objetivo clonar(){
		Objetivo sorpresaNueva = new Objetivo(super.getPocisionX(),super.getPocisionY(),super.getAncho(),super.getAlto());
		return sorpresaNueva;
	}
	/**
		Encargado de realizar el comportamiento correcto para esta clase, ya que
		es una sorpresa que realiza diferente accion o se comporta diferente a las
		otras sorpresas, en este caso realiza su funcion vital
		@param jugador: Jugador quien tomo la Sopresa
		@param pelota: Pelota que tomo la Sorpresa
	*/
	public void comportamiento(Jugador jugador,Pelota pelota){}
	public void setPong(Pong p){
		pong=p;
	}
	public Pong getPong(){
		return pong;
	}

	/**
		Encargado de poner a correr el tiempo del poder, correspondiente 
		a este tipo de sorpresa
	*/
	
	public void iniciarPoder(){}
}