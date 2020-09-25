package presentacion;

import aplicacion.*;
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
* CLASE: Tercera Pantalla Del Juego en donde se llevara acabo todos los procesos 
		 de visualizacion sobre los elementos necesarios que requiere Pong desde
		 la capa de aplicacion para la comunicacion con el usuario que hace uso 
		 del mismo
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class pantallaJuegoPong extends    JPanel   implements   Runnable {
	
	
	private Pong juegoPong;
	private Image fondoInicio;
	private Timer tiempo;
	private Thread hilo;
	private final int retraso = 10;
	private ArrayList<Integer> keysDown;
	private HashMap<String,Image> imagenes;
	private String jugador1;
	private ArrayList<String> elementosEnPantalla;
	private ArrayList<String> jugadoresEnPantalla;
	private boolean gameOver;
	private Font fuente;
	private JTextArea puntaje1, puntaje2, fortaleza1, fortaleza2;
	private boolean acabo;
	
	/**
		Contructor Pantalla de Juego:
		Tercera Pantalla Del Juego en donde se llevara acabo todos los procesos 
		de visualizacion sobre los elementos necesarios que requiere Pong desde
		la capa de aplicacion para la comunicacion con el usuario que hace uso 
		del mismo
	*/
	public pantallaJuegoPong(Pong p){
		imagenes= new HashMap<String,Image>();
		fuente = new Font("Comic Sans MS", Font. BOLD, 20);
		juegoPong= p;
		acabo=false;
		setPreferredSize(new Dimension(1500,1000)); 
		setBackground(Color.white);
		setLayout(null);
		prepareElementos();
		prepareImagenes();
		prepareTextos();
		iniciarJuego();
	}
	

	/**
		Metodo que lleva acabo el manejo del hilo, que corresponde al llamado de las 
		acciones principales y fundamentales del juego Pong
	*/
	public void run() {
		repaint();
		try {
			while (!(acabo)){
				repaint();
				if ((juegoPong.getEstadoDeJuego())){
					hilo.sleep(20);
					juegoPong.moverPelotas();
					actualizarTexto();
					juegoPong.moverMaquinas();
					
					}
			}
			
		}
		
		catch (InterruptedException ex){
				repaint();}
		
		
	}
	public void pintarGameOver(){
		repaint();
	}
	/**
		Encargado de actualizar la pantlla en caso de que se requiera cambiar el pong
		que se tenia actualmente por uno nuevo o guardado
	*/
	public void actualicePantalla(){
		juegoPong=juegoPong.getPong();
		repaint();
	}
	
	/**
		Encargado de iniciar y preparar los elementos principales para el juego
	*/
	public void iniciarJuego(){
		
		gameOver=juegoPong.gameOver();
		addKeyListener(new TAdapter());
		setFocusable(true);		
		prepareElementosJuego();
		hilo = new Thread(this);
		hilo.start();
		
	}
	/**
		Encargado de tener los jugadores para posteriormente darles el
		manejo respectivo segun las teclas
	*/
	public void prepareElementosJuego(){
		jugadoresEnPantalla= juegoPong.getJugadores();
	}
	
	/**
		Encargado de actualizar el puntaje y fortaleza que entrega
		el juego, para tener informados a los usuarios
	*/
	public void actualizarTexto(){
		puntaje1.setText("Puntos:" +juegoPong.getPuntajeLado1());
		puntaje2.setText("Puntos:" +juegoPong.getPuntajeLado2());
		fortaleza1.setText("F:"+ juegoPong.getFortaleza1());
		fortaleza2.setText("F:"+juegoPong.getFortaleza2());
	}
	/*
		Encargado de preparar los textos, necesarios que se observaran
		en el juego como lo es el puntaje y la fortaleza
	*/
	public void prepareTextos(){
		puntaje1= new JTextArea();
		puntaje2= new JTextArea();
		fortaleza1= new JTextArea();
		fortaleza2= new JTextArea();
		puntaje1.setBounds(670,10,90,30);
		puntaje2.setBounds(830,10,90,30);	
		fortaleza1.setBounds(670,40,90,30);
		fortaleza2.setBounds(830,40,90,30);
		puntaje1.setFont(fuente);
		puntaje2.setFont(fuente);
		fortaleza1.setFont(fuente);
		fortaleza2.setFont(fuente);	
		this.add(puntaje1);
		this.add(puntaje2);
		this.add(fortaleza1);
		this.add(fortaleza2);
	}
	
	/**
		Encargado de preparar los elementos necesarios y correspondientes
		a la ventana del Juego a nivel de esta tercera pantalla
	*/
	public void prepareElementos(){
		fondoInicio=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/CamposDeJuego/CampoDeJuego.jpg")).getImage());
		Dimension size = new Dimension(1500,1000);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setOpaque(false);
	} 
	
	/**
		Encargado de Pintar los componentes solicitados, este metodo se sobre escribe
		del Panel que se esta utilizando como extencion
		@param Componente de Graficos que se utilizara
	*/
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(fondoInicio,0,0,1500,1000,this);
        doDrawing(g);
		Toolkit.getDefaultToolkit().sync();   
    }
	
	/**
		Componenente complementario encargado de pintar imagenes sobrepuestas y secundarias que
		tienen su funcion correspondiente para el juego de Pong
	*/
	private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
		if (juegoPong.gameOver()==true){
			acabo=true;
			g2d.drawImage(imagenes.get("GameOver"), 50,50,this);}
		if (!(juegoPong.getEstadoDeJuego())){g2d.drawImage(imagenes.get("Pause"), 50,50,this);}
		ArrayList<String> jugadoresEnPantalla= juegoPong.getJugadores();					
		for (int i=0;i< jugadoresEnPantalla.size();i++ ){
			g2d.drawImage(imagenes.get(jugadoresEnPantalla.get(i)), juegoPong.pocisionXElemento(jugadoresEnPantalla.get(i),i),
							juegoPong.pocisionYElemento(jugadoresEnPantalla.get(i),i),100,100,this);
		}
		ArrayList<String> sorpresasEnPantalla= juegoPong.getSorpresas();
		for (int i=0;i< sorpresasEnPantalla.size();i++ ){
			g2d.drawImage(imagenes.get(sorpresasEnPantalla.get(i)), juegoPong.pocisionXElemento(sorpresasEnPantalla.get(i),i),
							juegoPong.pocisionYElemento(sorpresasEnPantalla.get(i),i),50,50,this);
		}
		ArrayList<String> pelotasEnPantalla= juegoPong.getPelotas();
		for (int i=0;i< pelotasEnPantalla.size();i++ ){
			g2d.drawImage(imagenes.get(pelotasEnPantalla.get(i)), juegoPong.pocisionXElemento(pelotasEnPantalla.get(i),i),
							juegoPong.pocisionYElemento(pelotasEnPantalla.get(i),i),50,50,this);
		}
    }
	
	/**
		Encargado de preparar todas las imagenes que se utilizaran a nivel
		del Juego, como se requiere para cada uno de sus componentes
	*/
	public void prepareImagenes(){
		Image pause= (new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/Pause.png"))).getImage();
		imagenes.put("Pause",pause);
		Image finDeJuego= (new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/GameOver.png"))).getImage();
		imagenes.put("GameOver",finDeJuego);
		Image fuerza=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Bloques/Fuerza.png")).getImage());
		imagenes.put("Fuerza",fuerza);
		Image bloque=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Bloques/Bloque.png")).getImage());
		imagenes.put("Bloque",bloque);
		Image extreme=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Maquinas/Extreme.png")).getImage());
		imagenes.put("Extreme",extreme);
		Image greedy=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Maquinas/Greedy.png")).getImage());
		imagenes.put("Greedy",greedy);
		Image lazy=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Maquinas/Lazy.png")).getImage());
		imagenes.put("Lazy",lazy);
		Image sniper=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Maquinas/Snipper.png")).getImage());
		imagenes.put("Sniper",sniper);
		Image objetivo=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Objetivos/Objetivo.png")).getImage());
		imagenes.put("Objetivo",objetivo);
		Image incremental=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Pelotas/Incremental.png")).getImage());
		imagenes.put("Incremental",incremental);
		Image lenta=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Pelotas/Lenta.png")).getImage());
		imagenes.put("Lenta",lenta);
		Image rapida=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Pelotas/Rapida.png")).getImage());
		imagenes.put("Rapida",rapida);
		Image chucky=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/PersonajesDeTerror/Chucky.png")).getImage());
		imagenes.put("Chucky",chucky);
		Image freddy=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/PersonajesDeTerror/Freddy.png")).getImage());
		imagenes.put("Freddy",freddy);
		Image jason=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/PersonajesDeTerror/Jason.png")).getImage());
		imagenes.put("Jason",jason);
		Image pennywise=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/PersonajesDeTerror/Pennywise.png")).getImage());
		imagenes.put("Pennywise",pennywise);
		Image cristiano=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/PersonajesLibres/Messi.png")).getImage());
		imagenes.put("Cristiano",cristiano);
		Image messi=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/PersonajesLibres/Cristiano.png")).getImage());
		imagenes.put("Messi",messi);
		Image batman=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/SuperHeroes/BatMan.png")).getImage());
		imagenes.put("Batman",batman);
		Image bromas=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/SuperHeroes/Bromas.png")).getImage());
		imagenes.put("Bromas",bromas);
		Image deadpool=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/SuperHeroes/DeadPool.png")).getImage());
		imagenes.put("Deadpool",deadpool);
		Image hulk=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/SuperHeroes/Hulk.png")).getImage());
		imagenes.put("Hulk",hulk);
		Image ironman=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/SuperHeroes/IronMan.png")).getImage());
		imagenes.put("Ironman",ironman);
		Image spiderman=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/SuperHeroes/SpiderMan.png")).getImage());
		imagenes.put("Spiderman",spiderman);
		Image superman=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/SuperHeroes/SuperMan.png")).getImage());
		imagenes.put("Superman",superman);
		Image wonderwoman=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/SuperHeroes/WonderWoman.png")).getImage());
		imagenes.put("Wonderwoman",wonderwoman);
		Image coldracket=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Poderes/ColdRacket.png")).getImage());
		imagenes.put("Coldracket",coldracket);
		Image energy=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Poderes/Energy.png")).getImage());
		imagenes.put("Energy",energy);
		Image fastball=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Poderes/FastBall.png")).getImage());
		imagenes.put("Fastball",fastball);
		Image flash=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Poderes/Flash.png")).getImage());
		imagenes.put("Flash",flash);
		Image freezer=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Poderes/Freezer.png")).getImage());
		imagenes.put("Freezer",freezer);
		Image Phantomracket=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Poderes/PhantomRacket.png")).getImage());
		imagenes.put("Phantomracket",Phantomracket);
		Image turtle=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Poderes/Turtle.png")).getImage());
		imagenes.put("Turtle",turtle);
		Image normal=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Pelotas/Normal.png")).getImage());
		imagenes.put("Normal",normal);
		Image fantasma=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Pelotas/Fantasma.png")).getImage());
		imagenes.put("Fantasma",fantasma);
		Image congelado=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Pelotas/Congelado.png")).getImage());
		imagenes.put("Congelado",congelado);
	}
	
	/**
		CLASE ENCARGADA DE RECIBIR LAS ACCIONES DEL TECLADO Y DARLE UNA
		FUNCION A UN LLAMADO DE CADA TECLA QUE SE DESTINO A USAR PARA EL 
		JUEGO DE PONG
	*/
	private class TAdapter extends KeyAdapter{
		@Override
		public void keyReleased(KeyEvent e){}
		/**
			Implementacion de lo que se hara cuando se opriman las teclas 
			respectivas
		*/
		@Override
		public void keyPressed(KeyEvent e){
		int key=e.getKeyCode();
		if (key== KeyEvent.VK_LEFT){
			juegoPong.moverJugador("izquierda",jugadoresEnPantalla.get(0));
		}
		if (key== e.VK_RIGHT){
			juegoPong.moverJugador("derecha",jugadoresEnPantalla.get(0));
		}
		if (key== e.VK_UP){
			juegoPong.moverJugador("arriba",jugadoresEnPantalla.get(0));
		}
		if (key== e.VK_DOWN){
			juegoPong.moverJugador("abajo",jugadoresEnPantalla.get(0));
		}
		if (key== KeyEvent.VK_A){
			juegoPong.moverJugador("izquierda",jugadoresEnPantalla.get(1));
		}
		if (key== e.VK_D){
			juegoPong.moverJugador("derecha",jugadoresEnPantalla.get(1));
		}
		if (key== e.VK_W){
			juegoPong.moverJugador("arriba",jugadoresEnPantalla.get(1));
		}
		if (key== e.VK_S){
			juegoPong.moverJugador("abajo",jugadoresEnPantalla.get(1));
		}
		if (key== e.VK_P){
			juegoPong.pause();
		}
	}
	}
}
	