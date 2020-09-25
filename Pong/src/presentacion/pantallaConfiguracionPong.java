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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE: Pantalla Configuracion en la cual se es la segunda pantalla del juego al iniciar
		 en donde se elegira toda la configuracion correspondiente para iniciar una partidael
		 del Juego Pong
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/

public class pantallaConfiguracionPong extends JPanel implements ActionListener,ChangeListener{
	
	private JCheckBox bloques,objetivos,fastball,freezer,flash,turtle,coldRacket,phantomRacket,energy,fuerza;
	private JRadioButton maquinaVSmaquina,maquinaVSJugador,jugadorVSjugador;
	private ButtonGroup modoJuego;
	private String modoDeJuego;
	private JComboBox personaje1,personaje2,tipoPelota;
	private Image fondoInicio;
	private HashMap<String,ArrayList<String>> nuevaConfiguracion;
	private ArrayList<String> sorpresas;
	private JSeparator separator1, separator2;
	private HashMap<String,String[]> listaDePersonajes;
	private int puntajeTotal,fortaleza;
	private JSpinner puntajeSpinner,fortalezaSpinner;
	private JFormattedTextField textoFortaleza,textoPuntaje;
	private JTextField seleccionPersonaje1,seleccionPersonaje2,seleccionPelota;
	private Font fuente;
	private HashMap<String,Image> imagenes;
	
	/**
		Constructor Pantalla Configuracion en la cual se es la segunda pantalla del juego al iniciar
		en donde se elegira toda la configuracion correspondiente para iniciar una partidael
		del Juego Pong
	*/
	public pantallaConfiguracionPong(){
		setPreferredSize(new Dimension(1500,1000));  
		setBackground(Color.white);
		prepareElementos();
		prepareConfiguracion();
		prepareModoJuego();
		preparePersonajes();
		prepareSpinners();
		preparePelota();
		prepareLetreros();
		repaint();
	}
	
	/**
		Encargado de alistar todos los elementos principales de la pantalla de configuracion
		como lo es el fondo, fuente, entre otros factores principales de controccion de la 
		pantalla
	*/
	public void prepareElementos(){
		fondoInicio=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/FondosInterfaz/Fondo4.gif")).getImage());
		Dimension size = new Dimension(1500,1000);
		fuente = new Font("Comic Sans MS", Font. BOLD, 35);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setOpaque(false);
		setLayout(null);
	} 
	
	/**
		Encargado de preparar todas las imagenes correspondientes a los letreros y botones que 
		se usan en esta pantalla de Configuracion
	*/
	public void prepareLetreros(){
		imagenes= new HashMap<String,Image>();
		Image modo= (new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/Modo.png"))).getImage();
		imagenes.put("modo",modo);
		Image j1= (new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/J1.png"))).getImage();
		imagenes.put("j1",j1);
		Image j2= (new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/J2.png"))).getImage();
		imagenes.put("j2",j2);
		Image ss= (new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/Sorpresas.png"))).getImage();
		imagenes.put("ss",ss);
		Image pelotaPalabra= (new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/Pelota.png"))).getImage();
		imagenes.put("pelota",pelotaPalabra);
		Image fortalezaPalabra= (new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/Fortaleza.png"))).getImage();
		imagenes.put("fortaleza",fortalezaPalabra);
		Image puntajePalabra= (new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/Puntos.png"))).getImage();
		imagenes.put("puntaje",puntajePalabra);
	}
	
	
	/**
		Metodo Sobre escrito, para poder pintar en la pantalla los elementos correspondientes a la misma, 
		para esto se utiliza Graphics y se pinta el fondo de la pantalla
	*/
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(fondoInicio,0,0,1500,1000,this);
		Toolkit.getDefaultToolkit().sync();
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(imagenes.get("modo"),80,130,200,100,this);
		g2d.drawImage(imagenes.get("j1"),480,500,50,50,this);
		g2d.drawImage(imagenes.get("j2"),480,560,50,50,this);
		g2d.drawImage(imagenes.get("ss"),1140,180,70,380,this);
		g2d.drawImage(imagenes.get("pelota"),80,620,200,80,this);
		g2d.drawImage(imagenes.get("fortaleza"),980,713,200,80,this);
		g2d.drawImage(imagenes.get("puntaje"),1000,608,200,80,this);
	}
	
	/**
		Encargado de alistar los Spinner usados para variar el valor a elegir correspondiente a 
		la fortaleza y puntos que se utilizaran para la partida del juego que iniciara
	*/
	public void prepareSpinners(){
		textoFortaleza= new JFormattedTextField (new Integer(999));
		textoPuntaje= new JFormattedTextField (new Integer(10));
		textoFortaleza.setValue(999);
		textoPuntaje.setValue(10);
		puntajeSpinner= new JSpinner();
		puntajeSpinner.setValue(10);
		fortalezaSpinner= new JSpinner();
		fortalezaSpinner.setValue(999);
		puntajeSpinner.setBounds(1200,630,170,50);
		fortalezaSpinner.setBounds(1200,730,170,50);
		puntajeSpinner.setFont(fuente);
		fortalezaSpinner.setFont(fuente);
		this.add(fortalezaSpinner);
		this.add(puntajeSpinner);	
		puntajeSpinner.addChangeListener(this);
		fortalezaSpinner.addChangeListener(this);
	}
	
	/**
		Metodo Sobre Escrito en el cual se da manejo a los cambios de los 
		Spinners utilizados
	*/
	@Override
	public void stateChanged(ChangeEvent e){
		if (e.getSource()==puntajeSpinner){
			textoPuntaje.setValue(puntajeSpinner.getValue());
			}
		if (e.getSource()==fortalezaSpinner){
			textoFortaleza.setValue(fortalezaSpinner.getValue());
			}
	}
	
	/**
		Encargado de preparar la configuracion correspondiente a las sorpresas
		que se ecogeran para ser utilizar en la partida a comenzar, se configuran
		todos los checks correspondientes
	*/
	public void prepareConfiguracion(){
		sorpresas= new ArrayList<String>();
		ArrayList<JCheckBox> configSorpresas= new ArrayList<JCheckBox>();
		fuerza= new JCheckBox("Fuerza");
		configSorpresas.add(fuerza);
		bloques= new JCheckBox("Bloques");
		configSorpresas.add(bloques);
		objetivos= new JCheckBox("Objetivos");
		configSorpresas.add(objetivos);
		fastball= new JCheckBox("Fastball");
		configSorpresas.add(fastball);
		freezer= new JCheckBox("Freezer");
		configSorpresas.add(freezer);
		flash= new JCheckBox("Flash");
		configSorpresas.add(flash);
		turtle= new JCheckBox("Turtle");
		configSorpresas.add(turtle);
		coldRacket= new JCheckBox("ColdRacket");
		configSorpresas.add(coldRacket);
		phantomRacket= new JCheckBox("Phantomracket");
		configSorpresas.add(phantomRacket);
		energy= new JCheckBox("Energy");
		configSorpresas.add(energy);
		
		for(int i=0;i<configSorpresas.size();i++){
			configSorpresas.get(i).setBounds(1200,((i*40)+150),400,80);
			configSorpresas.get(i).setFont(fuente);
			configSorpresas.get(i).setForeground(Color.WHITE);
			configSorpresas.get(i).setBackground(new Color(0,0,0,0));
			configSorpresas.get(i).setBorderPainted(false);
			configSorpresas.get(i).addActionListener(this);
			this.add(configSorpresas.get(i));
		}
	}

	/**
		Encargo de preparar las opciones presentadas para elegir el
		modo de juego de la partida de juego que iniciara
	*/
	public void prepareModoJuego(){
		modoJuego= new ButtonGroup();
		maquinaVSmaquina= new JRadioButton("Maquina VS Maquina");
		maquinaVSmaquina.setBounds(60,240,500,60);
		maquinaVSmaquina.setFont(fuente);
		maquinaVSmaquina.setForeground(Color.WHITE);
		maquinaVSmaquina.setBackground(new Color(0,0,0,0));
		maquinaVSmaquina.setBorderPainted(false);
		maquinaVSmaquina.addActionListener(this);
		this.add(maquinaVSmaquina);
		maquinaVSJugador= new JRadioButton("Maquina VS Jugador");
		maquinaVSJugador.setBounds(60,310,500,60);
		maquinaVSJugador.setFont(fuente);
		maquinaVSJugador.setForeground(Color.WHITE);
		maquinaVSJugador.setBackground(new Color(0,0,0,0));
		maquinaVSJugador.setBorderPainted(false);
		maquinaVSJugador.addActionListener(this);
		this.add(maquinaVSJugador);
		jugadorVSjugador= new JRadioButton("Jugador VS Jugador");
		jugadorVSjugador.setBounds(60,380,500,60);
		jugadorVSjugador.setFont(fuente);
		jugadorVSjugador.setForeground(Color.WHITE);
		jugadorVSjugador.setBackground(new Color(0,0,0,0));
		jugadorVSjugador.setBorderPainted(false);
		jugadorVSjugador.addActionListener(this);
		this.add(jugadorVSjugador);
		modoJuego.add(jugadorVSjugador);
		modoJuego.add(maquinaVSJugador);
		modoJuego.add(maquinaVSmaquina);
	}
	
	/**
		Metodo Sobre escrito para revisar y tener al tanto todas las acciones
		que puedan ocurrir en la pantalla de configuracion
	*/
	@Override
	public void actionPerformed(ActionEvent e){
		if (e.getSource()==jugadorVSjugador){
			modoJugadorVSJugador();
		}
		else if(e.getSource()==maquinaVSJugador){
			modoMaquinaVSJugador();
		}
		else if(e.getSource()==maquinaVSmaquina){
			modoMaquinaVSMaquina();
		}
		else if(e.getSource()==personaje1){
			seleccionPersonaje1.setText(personaje1.getSelectedItem().toString());
		}
		else if(e.getSource()==personaje2){
			seleccionPersonaje2.setText(personaje2.getSelectedItem().toString());
		}
		else if (e.getSource()==tipoPelota){
			seleccionPelota.setText(tipoPelota.getSelectedItem().toString());
		}
		else{
			if (objetivos.isSelected()){sorpresas.add("Objetivo");}
			if (bloques.isSelected()){sorpresas.add("Bloque");}
			if (fuerza.isSelected()){sorpresas.add("Fuerza");}
			if (fastball.isSelected()){sorpresas.add("Fastball");}
			if (freezer.isSelected()){sorpresas.add("Freezer");}
			if (flash.isSelected()){sorpresas.add("Flash");}
			if (turtle.isSelected()){sorpresas.add("Turtle");}
			if (coldRacket.isSelected()){sorpresas.add("Coldracket");}
			if (phantomRacket.isSelected()){sorpresas.add("Phantomracket");}
			if (energy.isSelected()){sorpresas.add("Energy");}
		}
	}
	
	/**
		Encargo de preparar las opciones de pelota que se podra elegir para 
		la partida de juego Pong que comenzara posteriormente
	*/
	public void preparePelota(){
		tipoPelota= new JComboBox();
		tipoPelota.setBounds(80,700,400,50);
		tipoPelota.setFont(fuente);
		tipoPelota.addItem("Normal");
		tipoPelota.addItem("Rapida");
		tipoPelota.addItem("Lenta");
		tipoPelota.addItem("Incremental");
		seleccionPelota= new JTextField(20);
		tipoPelota.addActionListener(this);
		this.add(tipoPelota);
	}
	
	/**
		Encargado de preparar los personajes dependiendo el tipo del cual pertenecen
		lo cual esta definidio por el tipo de jugador si es natural o si es maquina,
		se organizan dejando preparado para el momento en el cual se elija el modo de juego
	*/
	public void preparePersonajes(){
		listaDePersonajes= new HashMap<String,String[]> ();
		String[] tipoPersonaje1={"Batman","Ironman","Superman","Wonderwoman","Deadpool","Bromas","Hulk","Spiderman"};
		listaDePersonajes.put("SuperHeroes",tipoPersonaje1);
		String[] tipoPersonaje2={"Cristiano","Messi"};
		listaDePersonajes.put("Futbol",tipoPersonaje2);
		String[] tipoPersonaje3={"Pennywise","Jason","Freddy","Chucky"};
		listaDePersonajes.put("Terror",tipoPersonaje3);
		String[] tipoPersonaje4={"Extreme","Greedy","Lazy","Snipper"};
		listaDePersonajes.put("Maquinas",tipoPersonaje4);
		personaje1= new JComboBox();
		personaje2=new JComboBox();
		personaje1.setBounds(80,500,400,50);
		personaje1.setFont(fuente);
		personaje2.setBounds(80,560,400,50);
		personaje2.setFont(fuente);
		personaje1.setSelectedItem("a");
		personaje2.setSelectedItem("b");
		seleccionPersonaje1= new JTextField(20);
		seleccionPersonaje2= new JTextField(20);
		personaje1.addActionListener(this);
		personaje2.addActionListener(this);
		this.add(personaje1);
		this.add(personaje2);
	}
	
	/**
		Encargado de asginar y mostrar los personajes correspondientes al 
		modo de juego seleccionado en este caso MaquinaVSMaquina
	*/
	public void modoMaquinaVSMaquina(){
		modoDeJuego= "MaquinaVSMaquina";
		personaje1.removeAllItems();
		personaje2.removeAllItems();
		personajes(personaje1,"Maquinas");
		personajes(personaje2,"Maquinas");
	}
	
	/**
		Encargado de asginar y mostrar los personajes correspondientes al 
		modo de juego seleccionado en este caso MaquinaVSJugador
	*/
	public void modoMaquinaVSJugador(){
		modoDeJuego= "MaquinaVSJugador";
		personaje1.removeAllItems();
		personaje2.removeAllItems();
		personajes(personaje1,"SuperHeroes");
		personajes(personaje1,"Terror");
		personajes(personaje1,"Futbol");
		personajes(personaje2,"Maquinas");
	}
	
	/**
		Encargado de asginar y mostrar los personajes correspondientes al 
		modo de juego seleccionado en este caso JugadorVSJugador
	*/
	public void modoJugadorVSJugador(){
		modoDeJuego= "JugadorVSJugador";
		personaje1.removeAllItems();
		personaje2.removeAllItems();
		personajes(personaje1,"SuperHeroes");
		personajes(personaje1,"Terror");
		personajes(personaje1,"Futbol");
		personajes(personaje2,"Terror");
		personajes(personaje2,"SuperHeroes");
		personajes(personaje2,"Futbol");
		
	}
	
	/**
		Agrega a los desplegables de ambos jugadores, los personajes correspondientes
		al modo de Juego seleccionado
	*/
	public void personajes(JComboBox espacio, String tipoPersonajes){
		String[] value= listaDePersonajes.get(tipoPersonajes);
		for (int i=0; i < value.length;i++){
				espacio.addItem(value[i]);
			}
	}
	
	/**
		Encargado de informar el metodo de juego seleccionado
	*/
	public String getModo(){
		return modoDeJuego;
	}
	
	/**
		Encargado de informas las sorpresas elegidas para el juego 
	*/
	public ArrayList<String> getSorpresas(){
		return sorpresas;
	}
	
	/**
		Encargado de informar el puntaje total para ganar la partida el juego
		que iniciara
	*/
	public int getPuntajeTotal(){
		puntajeTotal=Integer.parseInt(textoPuntaje.getText());
		return puntajeTotal;
	}
	
	/**
		Encargado de informar la fortaleza del jugador que obtendra en la partida
		del juego  a comenzar
	*/
	public int getFortaleza(){
		fortaleza=Integer.parseInt(textoFortaleza.getText());
		return fortaleza;
	}
	
	/**
		Encargado de informar el personaje seleccionado para el jugador numero 1
	*/
	public String getJugador1(){
		String jugador=seleccionPersonaje1.getText();
		return jugador;
	}
	
	/**
		Encargado de informar el personaje seleccionado para el jugador numero 2
	*/
	public String getJugador2(){
		return seleccionPersonaje2.getText();
	}
	
	/**
		Encargado de informar el tipo de pelota seleccionado para iniciar el Juego
	*/
	public String getPelota(){
		String pelota=seleccionPelota.getText();
		return pelota;
	}
}