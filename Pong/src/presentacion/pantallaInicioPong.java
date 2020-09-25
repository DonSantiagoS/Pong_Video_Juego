package presentacion;

import aplicacion.*;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.lang.Thread;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE: Primera pantalla que se muestra en el juego Pong, en donde se da inicio al juego
		 alli se muestra la imagen principal del juego para poder iniciar
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class pantallaInicioPong extends JPanel{
	
	private Image fondo;
	
	/** 
		Contructor: Primera pantalla que se muestra en el juego Pong, en donde se da inicio al juego
		alli se muestra la imagen principal del juego para poder iniciar
	*/
	public pantallaInicioPong(){
		setPreferredSize(new Dimension(1500,1000));  
		setBackground(Color.white);
		prepareElementos();
		setLayout(null);
	}
	
	/**
		Encargado de preparar los elementos necesarios para mostrar la pantalla de Inicio correctamente
		como lo es el fondo, el tamaño entre otros
	*/
	public void prepareElementos(){
		fondo=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/FondosInterfaz/Fondo7.gif")).getImage());
		Dimension size = new Dimension(1500,1000);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setOpaque(false);
	} 
	
	/**
		Metodo Sobre escrito, para poder visualizar lo que se desea pintar en la pantalla 
		inicial del juego
	*/
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(fondo,0,0,1500,1000,this);
    }
}