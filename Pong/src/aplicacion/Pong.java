package aplicacion;

import persistencia.*;
import java.io.Serializable;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import javax.swing.filechooser.*;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.Timer;
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
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;



/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE: 	Pong es un juego creado por la compañía Atari Corporations en el año
			de 1972 en Estados Unidos. Este es un juego de dos dimensiones que
			simula el famoso tenis de mesa. En Pong, un jugador controla una de
			las paletas moviéndose verticalmente, y la otra paleta es controlada por
			la máquina o por otro jugador. El objetivo es que uno de los jugadores
			consiga la mayor de las puntuaciones posibles (10 puntos). estos
			puntos se obtienes cuando el jugador rival falla el devolver la pelota.
*
* @author : Santiago Buitrago
* @author : Brayan Macias
*
* @version 4.5 Final	
*
* Invariante: Siempre existiran solo dos Lados, lo que implica que solo existiran dos Puntajes 
*/

public class Pong implements Serializable{
	private int puntajeLado1;
	private int puntajeLado2;
	private int puntajeGanador;
	private boolean estadoActivo;
	private transient PongDAO dao;
	private static Pong pongActual=null;
	private static final long serialVersionUID = 8799656478674716638L;
	private HashMap<String,ArrayList<Pelota>> pelotasEnJuego;
	private HashMap<String, Jugador> jugadoresEnJuego;
	private HashMap<String,ArrayList<Sorpresa>> sorpresasEnJuego;
	private String modoJuego;
	private ArrayList<String> sorpresasHabilitadas;
	private int arista1=1500;
	private int arista2=1000;
	private String ultimoAnotado;
	private Pelota mm;
	private javax.swing.Timer tiempoAparicionSorpresa;
	private int fortalezaTotal;
	private String personajeNum1;
	private String personajeNum2;
	private ArrayList<Jugador> maquinasActivas;
	
	/**
		Contructor del Juego Pong
		@param personaje1: Cadena en el cual se especifica el personaje que fue elejido para el jugador numero 1
		@param personaje2: Cadena en el cual se especifica el personaje que fue elejido para el jugador numero 2
		@param mJuego: Indica como se llevara acabo el Juego de Pong entre sus distintos 3 modos de juego (JugadorVSJugador, MaquinaVSJugador, MaquinaVSMaquina)
		@param fortaleza: Numero que indica la fortaleza o energia que tiene un jugador para la partida desde el inicio,esta se reinicia con la anotacion de cada punto,
						  y disminuye segun los movimientos que el jugador realice
		@param tipoPelota: Cadena que indica la pelota con la cual se llevara acabo el juego, si rapida, lenta, o normal
		@param sorpresas: Lista de Sorpresas que indica cuales estan habilitadas para el uso de la partida
		@param puntajeTotal: Indica el numero de puntos que un jugador necesita obtener para ganar la partida
		
	*/
	public Pong(String personaje1, String Personaje2,String mJuego,int fortaleza,String tipoPelota, ArrayList<String> sorpresas, int puntajeTotal){
		estadoActivo=true;
		pelotasEnJuego= new HashMap<String,ArrayList<Pelota>>();
		jugadoresEnJuego= new HashMap<String, Jugador>();
		sorpresasEnJuego= new HashMap<String,ArrayList<Sorpresa>>();
		maquinasActivas= new ArrayList<Jugador>();
		sorpresasHabilitadas = sorpresas;
		ultimoAnotado=null;
		puntajeLado1=0;
		puntajeLado2=0;
		puntajeGanador=puntajeTotal;
		dao = new PongDAO();
		modoJuego= mJuego;
		personajeNum1=personaje1;
		personajeNum2=Personaje2;
		prepareJugadores(personaje1,Personaje2);
		ponerFortaleza(fortaleza);	
		addSorpresa();
		addPelota(tipoPelota);
		tiempoAparicionSorpresa= new javax.swing.Timer (5000, new ActionListener(){
								public void actionPerformed(ActionEvent e)
									{if (tiempoAparicionSorpresa.isRepeats()){addSorpresa();}}});
		tiempoAparicionSorpresa.start();
		fortalezaTotal=fortaleza;
	}
	
	public Pong(){}
	
	/**
		Encargado de preparar todo lo necesario para el correcto funcionamiento del Juego
	*/
	
	public void iniciarJuego(){
			
			moverPelotas();
			if (tiempoAparicionSorpresa.isRepeats()){
				addSorpresa();
			}
	}
	
	/**
		Encargado de Asignar la fortaleza indicada a cada Jugador del Juego
		@param fortaleza: Entero que indica la fortaleza que se le asignara
						  jugador que tendra durante la partida
	*/
	public void ponerFortaleza(int fortaleza){
		int i=0;
		for (Jugador value : jugadoresEnJuego.values()) {
			value.setFortaleza(fortaleza);
			value.setPong(this);
		}
	}
		
	/**
		Encargado de marcar los limites del campo
		@return Devuelve el Rectangulo correspondiente a los limites del campo de juego
	*/
	public Rectangle limites(){
		return new Rectangle(0,0,arista1,arista2);
	}

	/**
		Encargado de Evaluar si el juego a Terminado, para que en el momento
		que haya acabado pueda detenerlo y dar la opcion de reiniciar
		@return Devuelve el estado del Juego, si ya finalizo o si la partida aun
				esta activa debido a que no ha ganado ningun jugador
	*/
	public boolean gameOver(){
		for (Map.Entry<String, Jugador> entry : jugadoresEnJuego.entrySet()) {
			String key = entry.getKey();
			Jugador value = entry.getValue();
			if (value.getFortaleza()<fortalezaTotal/2){
				detenerJuego();
				return true;}	
		}	
		if ((puntajeLado1>puntajeGanador)||(puntajeLado2>puntajeGanador)){
			
			detenerJuego();
			return true;}
		return false;
		
	}
	/**
		Encargado de finalizar los procesos para dar fin al Juego
	*/
	public void detenerJuego(){
		//jugadoresEnJuego.clear();
		pelotasEnJuego.clear();
		sorpresasEnJuego.clear();
		tiempoAparicionSorpresa.stop();
	}
	/**
		Define el comportamiento correspondiente para la sorpresa capturada por el jugador
		quien la golpeo, se da el manejo correspondiente
		@param sorpresaCapturada: Indica la sorpresa que fue Caputara
		@param jugador: Indica el jugador que capturo la sorpresa
		@param sorpresa: Indica el tipo de sorpresa que atrapo dihco jugador
	*/
	public void capturaSorpresa(Sorpresa sorpresaCapturada, Jugador jugador,String sorpresa,Pelota pelota){
		if (!(sorpresaCapturada.isFisica())){
			int indice=sorpresasEnJuego.get(sorpresa).indexOf(sorpresaCapturada);
			sorpresasEnJuego.get(sorpresa).remove(indice);
			}
		sorpresaCapturada.iniciarPoder();
		sorpresaCapturada.comportamiento(jugador,pelota);
		
	}
	
	/**
		Encargado de preparar los personajes requeridos por la eleccion en la
		parte de configurarcion dependiendo el modo de Juego elegido, para poder
		usar estos
		@param personaje1: Personaje elegido en la configuracion para el jugador
						   numero 1
		@param personaje2: Personaje elegido en la configuracion para el jugador
						   numero 2
	*/
	public void prepareJugadores(String personaje1, String personaje2){
		
		if ((modoJuego=="MaquinaVSMaquina")||(modoJuego=="MaquinaVSJugador")){		
			if((personaje1.equals("Extreme"))||(personaje2.equals("Extreme"))){
				Extreme jugador1= new Extreme(100,600,100,100);
				maquinasActivas.add(jugador1);
				System.out.println(jugador1);
				jugador1.setLado(1);
				jugadoresEnJuego.put("Extreme",jugador1);}
			if((personaje1.equals("Greedy"))||(personaje2.equals("Greedy"))){
				Greedy jugador1= new Greedy(100,600,100,100);
				maquinasActivas.add(jugador1);
				if (jugadoresEnJuego.size()>0){jugador1.setPocisionX(1400);jugador1.setLado(2);jugador1.setPocisionOriginalX(1400);}
				else{jugador1.setLado(1);}
				jugadoresEnJuego.put("Greedy",jugador1);}
			if((personaje1.equals("Lazy"))||(personaje2.equals("Lazy"))){
				Lazy jugador1= new Lazy(100,600,100,100);
				maquinasActivas.add(jugador1);
				if (jugadoresEnJuego.size()>0){jugador1.setPocisionX(1400);jugador1.setLado(2);jugador1.setPocisionOriginalX(1400);}
				else{jugador1.setLado(1);}
				jugadoresEnJuego.put("Lazy",jugador1);}
			if((personaje1.equals("Sniper"))||(personaje2.equals("Sniper"))){
				Sniper jugador1= new Sniper(100,600,100,100);
				maquinasActivas.add(jugador1);
				if (jugadoresEnJuego.size()>0){jugador1.setPocisionX(1400);jugador1.setLado(2);jugador1.setPocisionOriginalX(1400);}
				else{jugador1.setLado(1);}
				jugadoresEnJuego.put("Sniper",jugador1);}}
			
			
		if (modoJuego=="JugadorVSJugador"){
			Jugador jugador1= new Jugador(100,500,100,100);
			Jugador jugador2= new Jugador(1400,500,100,100);
			jugadoresEnJuego.put(personaje2,jugador2);
			jugadoresEnJuego.put(personaje1,jugador1);
			jugador1.setLado(1);
			jugador2.setLado(2);}
			
		else if(modoJuego=="MaquinaVSJugador"){
			Jugador jugador1= new Jugador(1400,500,100,100);
			jugador1.setLado(2);
			jugadoresEnJuego.put(personaje1,jugador1);}
			rivales();
		
	}
	
	/**
		Encargado de poner Pause al Juego de manera que todo quede estatico mientras
		el juego permanezca en dicho estado, o despausar en su defecto
	*/
	public void pause(){
		if (estadoActivo==true){
			estadoActivo=false;}
		else{
			estadoActivo=true;
			}
	}
	
	public boolean getEstadoDeJuego(){
		
		return estadoActivo;
	}
	/**
		Encargado de añadir las Pelotas correspondientes al Juego que se requiere
		para poder iniciar la partida con dicha pelota
		@param tipoPelota: Tipo de Pelota con la que se jugara la partida de Pong
	*/
	public void addPelota(String tipoPelota){
		if (tipoPelota.length()==0){tipoPelota="Normal";}
		Pelota pelota= new Pelota(750,500,50,50);
		
		
		if (tipoPelota.equals("Incremental")){pelota= new Incremental(750,500,50,50);}
		pelota.setTipoPelota(tipoPelota);
		mm=pelota;
		if (pelotasEnJuego.containsKey(tipoPelota)){
			(pelotasEnJuego.get(tipoPelota)).add(pelota);
		}
		else{
			ArrayList<Pelota> nueva= new ArrayList<Pelota>();
			nueva.add(pelota);
			pelotasEnJuego.put(tipoPelota,nueva);
		}
		moverPelotas(); 
	}
	
	/**
		Encargado de eliminar una pelota determinada en el momento que se requiera
		@param pelota: Pelota la cual se eliminara
		@param tipoPelota: Tipo de Pelota que se eliminara
	*/
	public void eliminarPelotaDirecta(Pelota pelota,String tipoPelota){
		if (pelota.isFisica()==false){
			int indice=pelotasEnJuego.get(tipoPelota).indexOf(pelota);
			pelotasEnJuego.get(tipoPelota).remove(indice);
		} 
	}
	
	/**
		Metodo el cual añade sorpresas de manera aletoria, entre las escogidas en la configuracion y ademas 
		las ubica en una pocision aleatoria
	*/
	public void addSorpresa(){
		if (estadoActivo==true){
			Sorpresa sorpresaNueva=new Sorpresa(0,0,0,0);
			int x=(int) (Math.floor(Math.random()*(0-arista1+1)+arista1));
			int y=(int) (Math.floor(Math.random()*(0-arista2+1)+arista2));
			Random random= new Random();
			String aleatorio= sorpresasHabilitadas.get(random.nextInt(sorpresasHabilitadas.size()));
			if (aleatorio=="Coldracket"){ sorpresaNueva=new ColdRacket(x,y,50,50);}
			else if (aleatorio=="Energy"){ sorpresaNueva=new Energy(x,y,50,50);}
			else if (aleatorio=="Fastball"){ sorpresaNueva=new Fastball(x,y,50,50);}
			else if (aleatorio=="Flash"){ sorpresaNueva=new Flash(x,y,50,50);}
			else if (aleatorio=="Phantomracket"){ sorpresaNueva=new PhantomRacket(x,y,50,50);}
			else if (aleatorio=="Turtle"){ sorpresaNueva=new Turtle(x,y,50,50);}
			else if (aleatorio=="Bloque"){ sorpresaNueva=new Bloque(x,y,50,50);}
			else if (aleatorio=="Objetivo"){ 
				sorpresaNueva=new Objetivo(x,y,50,50);
				Objetivo par= sorpresaNueva.clonar();
				par.setPocisionX(1400);
				par.setPong(this);
				addSorpresaDirecta(par,"Objetivo");
				}
			else if (aleatorio=="Fuerza"){ sorpresaNueva=new Fuerza(x,y,50,50);}
			else if (aleatorio=="Freezer"){ sorpresaNueva=new Freezer(x,y,50,50);}
			if (sorpresasEnJuego.containsKey(aleatorio)){(sorpresasEnJuego.get(aleatorio)).add(sorpresaNueva);}
			else{
				ArrayList<Sorpresa> nueva=new ArrayList<Sorpresa>();
				nueva.add(sorpresaNueva);
				sorpresasEnJuego.put(aleatorio,nueva);}
			sorpresaNueva.setPong(this);
		}
	}
	
	/**
		Encargado de definir quien saca con la pelota en determinado momento,
		lo cual se basa en turnos que se intercala con los jugadores, segun el
		ultimo punto anotado, otorgando el saque al contrincante
		@param pelota: Pelota con la cual se realizo el ultimo punto anotado
	*/
	public void turno(Pelota pelota){
		if (pelota.ultimoGolpe()==null){
			if(ultimoAnotado=="Lado1"){rebotePelota(1,pelota.getDeltaY(),pelota);}
			else{rebotePelota(-1,pelota.getDeltaY(),pelota);}
		}
	}
	
	/**
		Encragado de verificar si en algun momento algun jugador tiene algun
		choque con algun elemento del juego, ya sea para capturarlo o golpearlo
		@param jugador: Jugador para el cual se determina si choco con algo 
	*/
	public void checkColisionesJugador(Jugador jugador){
		Rectangle areaPersonaje= jugador.getArea();
		for (Map.Entry<String, ArrayList<Sorpresa>> entry : sorpresasEnJuego.entrySet()) {
			String key = entry.getKey();
			ArrayList<Sorpresa> value = entry.getValue();
			for (int i=0; i< value.size();i++){
				Rectangle areaSorpresa= value.get(i).getArea();
				if (areaPersonaje.intersects(areaSorpresa)){
					capturaSorpresa(value.get(i),jugador,key,null);}
			}
		}	
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
	public int[] calcularAngulo(Pelota pelota, Elemento elemento){
		int[] direccion= new int[2];
		int direccionX= (elemento.getPocisionX()-pelota.getPocisionX())/10;
		if (((pelota.getDeltaX()>0)&&(elemento.getDeltaX()>0))||((pelota.getDeltaX()<0)&&(elemento.getDeltaX()<0))){direccion[0]= direccionX;}
		else{direccion[0]= -(direccionX);}
		int direccionY= (elemento.getPocisionY()-pelota.getPocisionY())/10;
		if (((pelota.getDeltaY()>0)&&(elemento.getDeltaY()>0))||((pelota.getDeltaY()<0)&&(elemento.getDeltaY()<0))){direccion[1]= direccionY;}
		else{direccion[1]= -(direccionY);}
		return direccion;
	}
	
	/**
		Encargado de verificar las posibles colisiones que puede tener determinada pelota
		con lso diferentes elementos del campo de Juego
		@param pelota: Pelota para la cual se verifica si colisiono con algun elemento
	*/
	public void checkColisionesPelota(Pelota pelota){
		if (pelota.isFisica()){
			Rectangle areaPelota= pelota.getArea();
			if ((pelota.getPocisionY()<0)||(pelota.getPocisionY()+(pelota.getAlto()*2)>arista2)){rebotePelota(pelota.getDeltaX(),-(pelota.getDeltaY()),pelota);}
			for (Map.Entry<String, ArrayList<Sorpresa>> entry : sorpresasEnJuego.entrySet()) {
				String key = entry.getKey();
				ArrayList<Sorpresa> value = entry.getValue();
				for (int i=0; i< value.size();i++){
				Rectangle areaSorpresa= value.get(i).getArea();
					if (areaPelota.intersects(areaSorpresa)){
						Jugador jugador=pelota.ultimoGolpe();
						capturaSorpresa(value.get(i),pelota.ultimoGolpe(),key,pelota);}}}
			for (Map.Entry<String, Jugador> entry : jugadoresEnJuego.entrySet()) {
				String key = entry.getKey();
				Jugador value = entry.getValue();
				Rectangle areaPersonaje= value.getArea();
				if (areaPelota.intersects(areaPersonaje)){
						if (value.habilidad(pelota)==false){
							int[] direccionMovimiento= calcularAngulo(pelota,value);
							rebotePelota(direccionMovimiento[0],direccionMovimiento[1],pelota);
						}
						else{
							value.habilidad(pelota);
						}
						pelota.setUltimoGolpe(value);}}
		}
	}
	
	/**
		Encargado de asignar el nuevo movimiento que tendra la pelota especifica
		por algun choque que obtuvo que origino el rebote de la misma
		@param deltaX: Variacion que tendra la pelota en X
		@param deltaY: Variacion que tendra la pelota en Y
		@param pelota: Pelota que tendra dicha variacion por el rebote
	*/
	public void rebotePelota(int deltaX,int deltaY,Pelota pelota){
		pelota.setDeltaY(deltaY);
		pelota.setDeltaX(deltaX);
	}
	
	/**
		Encargado de mover las pelotas que existen en el campo del Juego, 
		cada una se mueve con base en sus configuraciones y especificaciones
		que pueden llegar a tener
	*/
	public void moverPelotas(){
		for (Map.Entry<String, ArrayList<Pelota>> entry : pelotasEnJuego.entrySet()) {
			String key = entry.getKey();
			ArrayList<Pelota> value = entry.getValue();
			for (int i=0; i< value.size();i++){
				turno(value.get(i));
				checkColisionesPelota(value.get(i));
				value.get(i).mover();
				puntajeObtenido(value.get(i));
			}	
		}	
	}
	
	/**
		Encargado de darle movilidad a las Sopresas, en caso de requerirse con base
		en las configuraciones y funcionamiento que tengan correspondientemente
	*/
	public void moverSorpresas(){
		/**
		for (Map.Entry<String, ArrayList<Sorpresa>> entry : sorpresasEnJuego.entrySet()) {
			String key = entry.getKey();
			ArrayList<Sorpresa> value = entry.getValue();
			for (int i=0; i< value.size();i++){
				value.get(i).setPelota(mm);
				value.get(i).comportamiento();
			}	
		}	*/
	}
	
	/**
		Encargado de Verificar si alguien obtuvo un punto, para lo cual reiniciara el turno
		y ademas de esto, pondra los valores requeridos para el nuevo round
		@param pelota: Pelota la cual excedio los limites del campo de Juego
	*/
	public void puntajeObtenido(Pelota pelota){
		boolean huboPuntaje=false;
		 if (pelota.getPocisionX()<0){
			 puntajeLado2+=1;
			 pelota.reiniciar();
			 pelota.setUltimoGolpe(null);
			 huboPuntaje=true;}
		 else if(pelota.getPocisionX()>arista1){
			 puntajeLado1+=1;
			 pelota.reiniciar();
			 pelota.setUltimoGolpe(null);
			 huboPuntaje=true;}
		if (huboPuntaje){
		 ponerFortaleza(fortalezaTotal);
		 reiniciarJugadores();
		 sumarPuntaje();
		}
	}
	public void setPuntajeMio(int lado,int puntaje){
		if (lado==1){puntajeLado1=puntaje;}
		else if (lado==2){puntajeLado2=puntaje;}
	}
	public int getPuntajeMio(int lado){
		if (lado==1){return puntajeLado1;}
		else if (lado==2){return puntajeLado2;}
		return 0;
	}
	/**
		Depues de cada puntaje Obtenido ubica a los jugadores desde cero en la pocision
		inicial para que no existan ventajas
	*/
	public void reiniciarJugadores(){
			for (Jugador value : jugadoresEnJuego.values()) {
			value.setPocisionX(value.getPocisionOriginalX());
			value.setPocisionY(value.getPocisionOriginalY());
		}
	}
	
	/**
		Encargado de llevar el Puntaje actualizado
		@return devuelve los puntajes que se tienen actualmente en la partida
				para que sean acutalizados en pantalla
	*/
	public int[] sumarPuntaje(){
		int[] puntajes= new int[2];
		return puntajes;
	}
	
	/**
		Encargado de cambiar la fortaleza de determinado jugador
		@param jugador: Jugador al cual se le cambiara la fortaleza
		@param cambio: Variacion que tendra el jugador en su fortaleza
	*/
	public void cambioFortaleza(Jugador jugador,int cambio){
		jugador.setFortaleza(cambio);
	}
	
	/**
		Encargado de mover los jugadores correspondientes que actualmente
		se encuentran en la zona de juego
		@param direccion: Direccion en la cual el jugador se movera
		@param jugadorAMover: jugador el cual se mover segun la disponcision del este
	*/
	public void moverJugador(String direccion,String jugadorAMover ){
		if (estadoActivo==true){
			Jugador seleccionado = jugadoresEnJuego.get(jugadorAMover);
			cambioFortaleza(seleccionado,seleccionado.getFortaleza()-1);
			if (direccion=="izquierda"){
				seleccionado.setDeltaX(-1);
				seleccionado.setDeltaY(0);
			}
			else if (direccion=="derecha"){
				seleccionado.setDeltaX(1);
				seleccionado.setDeltaY(0);
			}
			else if (direccion=="arriba"){
				seleccionado.setDeltaY(-1);
				seleccionado.setDeltaX(0);
			}
			else if (direccion=="abajo"){
				seleccionado.setDeltaY(1);
				seleccionado.setDeltaX(0);
			}
			seleccionado.mover();
			checkColisionesJugador(seleccionado);
		}
	}
	public void moverMaquinas(){
		
		
		for (int i=0; i<maquinasActivas.size();i++){
			maquinasActivas.get(i).movimiento();
		}
		
	}
	/**
		Encargado de devolver el jugador solicitado por el nombre
		solicitado del personaje
		@param jugadorEnJuego: Nombre del persona del jugador que se requiere
		@return personaje solicitado en jugador
	*/
	public Jugador getJugador(String jugadorEnJuego){
		
		return jugadoresEnJuego.get(jugadorEnJuego);
	}
	
	/**
		Encargado de devolver el nombre de las sorpesas que estan acutalmente en el juego,
		las que estan en pantalla NO las habilitadas
		@return nombres de las sorpresas en pantalla
	*/
	public ArrayList<String> getSorpresas(){
		ArrayList<String> sorpresasNombres= new ArrayList<String>();
		for (Map.Entry<String, ArrayList<Sorpresa>> entry : sorpresasEnJuego.entrySet()) {
			String key = entry.getKey();
			ArrayList<Sorpresa> value = entry.getValue();
			for (int i=0;i<value.size();i++){
					sorpresasNombres.add(key);}}
		return sorpresasNombres;
	}
	
	public ArrayList<Sorpresa> getSorEspe(String tipo){
		if (sorpresasEnJuego.containsKey(tipo)){
			return sorpresasEnJuego.get(tipo);
		}
		return null;
	}
	/**
		Encargado de devolver el nombre de los jugadores que estan acutalmente en el juego,
		los que estan en pantalla NO los habilitados
		@return nombres de los jugadores en pantalla
	*/
	public ArrayList<String> getJugadores(){
		ArrayList<String> jugadoresNombres= new ArrayList<String>();
		for (String key : jugadoresEnJuego.keySet()) {
			jugadoresNombres.add(key);
		}
		return jugadoresNombres;
	}
	
	/**
		Encargado de devolver el nombre de las pelotas que estan acutalmente en el juego,
		las que estan en pantalla NO las habilitadas
		@return nombres de las pelotas en pantalla
	*/
	public ArrayList<String> getPelotas(){
		ArrayList<String> pelotasNombres= new ArrayList<String>();
		for (Map.Entry<String, ArrayList<Pelota>> entry : pelotasEnJuego.entrySet()) {
			String key = entry.getKey();
			ArrayList<Pelota> value = entry.getValue();
			for (int i=0;i<value.size();i++){
					pelotasNombres.add(key);}}
		return pelotasNombres;
	}
	
	/**
	*	Cambia el puntaje necesario para ganar la partida, el cual por defecto es 10 
		en aso de que no se haga el cambio	
		@param nuevoPuntaje: El nuevo puntaje para ganar la partida
	*/
	public void setPuntajeGanador(int nuevoPuntaje){
		puntajeGanador=nuevoPuntaje;
	}
	
	/**
	*	Retorna el puntaje necesario para ganar la partida
		@return
	*/
	public int getPuntajeGanador(int nuevoPuntaje){
		return puntajeGanador;
	}
	
	/**
	*	Retorna el juego de Pong actual
	*@return Juego actual de Pong
	*/
	public static Pong getPong(){
		if (pongActual==null){
			pongActual= new Pong();
		}
		return pongActual;
	}
	
	/**
		Encargado de devolver la Pocision en X de determinado elementos ubicado en 
		el juego dependiendo el tipo de elemento y su clase
		@param elemento: Nombre del elemento
		@param i: pocision del elemento detro de las existencias de su topo
		@return devuelve la pocision en X del determinado elemento
	*/
	public int pocisionXElemento(String elemento, int i){
		int pocisionX=0;
		if (jugadoresEnJuego.containsKey(elemento)){pocisionX =jugadoresEnJuego.get(elemento).getPocisionX();}
		if (pelotasEnJuego.containsKey(elemento)){pocisionX = pelotasEnJuego.get(elemento).get(i).getPocisionX();}
		if (sorpresasEnJuego.containsKey(elemento)){pocisionX = sorpresasEnJuego.get(elemento).get(i).getPocisionX();}
		return pocisionX;
	}
	
	/**
		Encargado de devolver la Pocision en Y de determinado elementos ubicado en 
		el juego dependiendo el tipo de elemento y su clase
		@param elemento: Nombre del elemento
		@param i: pocision del elemento detro de las existencias de su topo
		@return devuelve la pocision en Y del determinado elemento
	*/
	public int pocisionYElemento(String elemento, int i){
		int pocisionY=0;
		if (jugadoresEnJuego.containsKey(elemento)){pocisionY =jugadoresEnJuego.get(elemento).getPocisionY();}
		if (pelotasEnJuego.containsKey(elemento)){pocisionY = pelotasEnJuego.get(elemento).get(i).getPocisionY();}
		if (sorpresasEnJuego.containsKey(elemento)){pocisionY = sorpresasEnJuego.get(elemento).get(i).getPocisionY();}
		return pocisionY;
	}
	
	/**
		Encargado de Reiniciar totalmente el Juego de Pong, desde su ultima patnalla de Juego
	*/
	public void reiniciar(){
			reiniciarJugadores();
			reiniciarPelotas();
			reiniciarSorpresas();
			puntajeLado1=0;
			puntajeLado2=0;
	}
	
	/**
		Encargado de reiniciar las sorpresas
	*/
	public void reiniciarSorpresas(){
		sorpresasEnJuego.clear();
	}
	
	/**
		Encargado de reiniciar las pelotas
	*/
	public void reiniciarPelotas(){
		for (Map.Entry<String, ArrayList<Pelota>> entry : pelotasEnJuego.entrySet()) {
			String key = entry.getKey();
			ArrayList<Pelota> value = entry.getValue();
			for (int i=0;i<value.size();i++){
					value.get(i).setPocisionX(750);
					value.get(i).setPocisionY(500);}}
	}
	
	/**
	*	Revisar si es necesario
	*/
	public static void nuevoPong(){
		pongActual= new Pong();
	}
	
	
	/**
	*	Guarda el estao actual de la automata, pero debe estar en pausa
		@param archivo: Archivo en el cual se guardara el juego actual
	*/
	public void guardar(File archivo) throws PongException{
		dao.guardar(this,archivo);
	}
	
	/**
	*	Abre un archivo el cual contiene un Juego anteriormente guardado en
		determinado estado
		@param archivo: Archivo el cual abrira un juego anteriormente guardado
		@return archivo: Archivo en el cual se encuentra el juego guardado con
						 anterioridad
	*/
	public Pong abrir(File archivo) throws PongException{
		return dao.abrir(archivo);
	}
	
	/**
		Cambia el Pong que se esta usando por uno determinado	
		@param Nuevo Pong que se usara
	*/
	public void cambiarPong(Pong pongNueva){
		pongActual=pongNueva;
	}
	
	/**
		Asigna el Rival a los jugadores en pantalla
	*/
	public void rivales(){
		jugadoresEnJuego.get(personajeNum1).setRival(jugadoresEnJuego.get(personajeNum2));
		jugadoresEnJuego.get(personajeNum2).setRival(jugadoresEnJuego.get(personajeNum1));
	}
	/**
		Encargado de devolver la informacion de la fortaleza inicial del juego
	*/
	public int getFortaleza(){
		return fortalezaTotal;
	}
	/**
		Agrega una pelota al Juego de manera directa teniendo la pelota ya creada
		@param pelota: Pelota que se Agregara al Juego
		@param tipoPelota: Tipo de pelota que se agregara al juego
	*/
	public void addPelotaDirecta(Pelota pelota, String tipoPelota){
		if (pelotasEnJuego.containsKey(tipoPelota)){
				(pelotasEnJuego.get(tipoPelota)).add(pelota);
			}
			else{
				ArrayList<Pelota> nueva= new ArrayList<Pelota>();
				nueva.add(pelota);
				pelotasEnJuego.put(tipoPelota,nueva);
			}
	}
	/**
		Agrega una sorpresa al Juego de manera directa teniendo la sorpresa ya creada
		@param pelota: sorpresa que se Agregara al Juego
		@param tipoSorpresa: Tipo de sorpresa que se agregara al juego
	*/
	public void addSorpresaDirecta(Sorpresa sorpresa, String tipoSorpresa){
		
		if (sorpresasEnJuego.containsKey(tipoSorpresa)){
				(sorpresasEnJuego.get(tipoSorpresa)).add(sorpresa);
			}
			else{
				ArrayList<Sorpresa> nueva= new ArrayList<Sorpresa>();
				nueva.add(sorpresa);
				sorpresasEnJuego.put(tipoSorpresa,nueva);
			}
		
	}
	/**
		Encargado de eliminar una Sorpresa determinada en el momento que se requiera
		@param Sorpresa: Sorpresa la cual se eliminara
	*/
	public void removeSorpresa(Sorpresa sorpresa){
		String tipoSorpresa= sorpresa.getClass().toString();
		int indice=sorpresasEnJuego.get(tipoSorpresa).indexOf(sorpresa);
		sorpresasEnJuego.get(tipoSorpresa).remove(indice);		
	}
	
	public String getPuntajeLado1(){
		String puntaje=puntajeLado1+"";
		return puntaje;
	}
	public String getPuntajeLado2(){
		String puntaje=puntajeLado2+"";
		return puntaje;
	}
	public String getFortaleza1(){
		int fortaleza= jugadoresEnJuego.get(personajeNum1).getFortaleza();
		String f=fortaleza+"";
		return f;
	}
	public String getFortaleza2(){
		int fortaleza= jugadoresEnJuego.get(personajeNum2).getFortaleza();
		String f=fortaleza+"";
		return f;
	}
	public Pelota getPelotaActiva(){
		for (Map.Entry<String, ArrayList<Pelota>> entry : pelotasEnJuego.entrySet()) {
			String key = entry.getKey();
			ArrayList<Pelota> value = entry.getValue();
			return value.get(0);
			
		}
		return null;
	}
}