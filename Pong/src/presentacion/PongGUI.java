package presentacion;

import aplicacion.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import javax.imageio.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE: Clase principal de la capa de presentacion ya que aqui se da el manejo
		 a las diferentes pantallas presentadas para el juego, adicionalmente da
		 el manejo completo de la ventana con sus diferentes configuraciones, y 
		 es la clase que habla con el paquete de aplicacion, es la conexion a dicha
		 capa por lo cual se realiza todo el manejo grafico de lo que la capa de 
		 aplicacion desee o requiera mostrar
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/


public class PongGUI extends JFrame implements ActionListener{

	private JFrame pongG,configuracion,acerca;
	private pantallaInicioPong pantallaInicio;
	private pantallaConfiguracionPong pantallaConfiguracion;
	private pantallaJuegoPong pantallaJuego;
	private JMenuBar menu;
	private JMenu menu1,menu2,menu3;
	private JMenuItem nuevo,reiniciar,abrir,guardar,guardarComo,salir,cambiarColor,configuracionJuego;
	private JFileChooser fileChooser;
	private Container contenedor;
	private Graphics2D graphic;
	private static int width= 1500;
	private static int height= 1000;
	private Color backgroundColour;
	private Color colores;
	private Pong juegoPong;
	private JColorChooser seleccionarColor;
	private Color colorTablero;
	private JButton okey,botonIniciar,botonIniciarJuego,botonHome,acercaBoton,botonHomePeque;
	private String modoJuego;
	private transient Clip sonido;
	
	/**
	Constructor de la clase PongGUI, Clase principal de la capa de presentacion ya que aqui se da el manejo
	a las diferentes pantallas presentadas para el juego, adicionalmente da
	el manejo completo de la ventana con sus diferentes configuraciones, y 
	es la clase que habla con el paquete de aplicacion, es la conexion a dicha
	capa por lo cual se realiza todo el manejo grafico de lo que la capa de 
	aplicacion desee o requiera mostrar
	*/
	public PongGUI(){
		prepareElementos();
		prepareAcciones();
		prepareElementosMenu();
		prepareElementosPantallaInicio();
	}
	
	/**
		Encargado de devolver la ventana principal que es esta clase
	*/
	public PongGUI getFramePrincipal(){
		return this;
	}
	
	/**
		Encargado de preparar los elementos principales de la ventana, en donde se 
		llevara acabo todo el manejo y visualizacion de los elementos que aplicacion
		requiere, se inicializan las variables de los atributos principales para darles
		el manejo correspondiente
	*/
	public void prepareElementos() {
		setTitle("Pong");
		setLayout(null);
		setResizable(false);	
		setSize(1500,1000);
		setIconImage((new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/IconoPong.png"))).getImage());
		fileChooser=new JFileChooser();
		contenedor= getContentPane();
		seleccionarColor= new JColorChooser();
		pantallaInicio= new pantallaInicioPong();
		pantallaConfiguracion= new pantallaConfiguracionPong(); 
		add(pantallaInicio);
		add(pantallaConfiguracion);
		pantallaInicio.setVisible(false);
		pantallaConfiguracion.setVisible(false);
	}	
	
	/**
		Encargado de preparar los elementos de la pantalla inicial del juego donde
		se da inicio a la experiencia del Juego realizado, alli se da manejo a los 
		elementos de dicha pantalla
	*/
	public void prepareElementosPantallaInicio(){
		pantallaInicio.setVisible(true);
		ImageIcon iconoBotonIniciar =(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/Jugar.png")));
		botonIniciar= new JButton(iconoBotonIniciar);
		botonIniciar.setOpaque(false);
		botonIniciar.setBackground(new Color(0,0,0,0));
		botonIniciar.setBorderPainted(false);
		botonIniciar.addActionListener(this);
		botonIniciar.setBounds(425,0,600,200);
		pantallaInicio.add(botonIniciar);
		ImageIcon iconoBotonAcerca =(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/BotonAcerca.png")));
		acercaBoton= new JButton(iconoBotonAcerca);
		acercaBoton.setOpaque(false);
		acercaBoton.setBackground(new Color(0,0,0,0));
		acercaBoton.setBorderPainted(false);
		acercaBoton.addActionListener(this);
		acercaBoton.setBounds(1400,830,70,70);
		pantallaInicio.add(botonIniciar);
		pantallaInicio.add(acercaBoton);
	}
	
	/**
		Encargo de preparar los elementos necesarios para mostrar la
		pantalla Configuracion que posteriormente por si sola dara la 
		opcion de elegir lo que quiere elegir para la partida a iniciar
	*/
	public void prepareElementosPantallaConfiguracion(){
		pantallaConfiguracion.setVisible(true);
		ImageIcon iconoIniciar =(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/Continuar.png")));
		botonIniciarJuego= new JButton(iconoIniciar);
		botonIniciarJuego.setOpaque(false);
		botonIniciarJuego.setBackground(new Color(0,0,0,0));
		botonIniciarJuego.setBorderPainted(false);
		botonIniciarJuego.addActionListener(this);
		botonIniciarJuego.setBounds(500,0,600,150);
		ImageIcon iconoHome =(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/Home.png")));
		botonHome= new JButton(iconoHome);
		botonHome.setOpaque(false);
		botonHome.setBackground(new Color(0,0,0,0));
		botonHome.setBorderPainted(false);
		botonHome.addActionListener(this);
		botonHome.setBounds(650,700,200,200);
		pantallaConfiguracion.add(botonIniciarJuego);
		pantallaConfiguracion.add(botonHome);
	}
	
	/**
		Encardo de preparar los elementos necesarios para la pantalla del
		Juego a iniciar, la cual mostrara lo principal que requiere la capa
		de aplicacion que es el Juego de Pong, aqui se solicita toda la
		configuracion elegida por el usuario en la pantalla de Configuracion
	*/
	public void prepareElementosPantallaJuego(){
		juegoPong = new Pong(pantallaConfiguracion.getJugador1(), pantallaConfiguracion.getJugador2(),pantallaConfiguracion.getModo(),pantallaConfiguracion.getFortaleza(),pantallaConfiguracion.getPelota(),pantallaConfiguracion.getSorpresas(), pantallaConfiguracion.getPuntajeTotal());	
		pantallaJuego= new pantallaJuegoPong(juegoPong); 
		add(pantallaJuego);
		pantallaJuego.setVisible(true);
		ImageIcon iconoHomeJuego =(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/Fuentes/HomePeque.png")));
	}
	
	/**
		Encargado de preparar las posibles acciones que se podran realizar en la ventana
	*/
	public void prepareAcciones(){
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE );
		addWindowListener (new WindowAdapter(){			
			public void windowClosing(WindowEvent e){
				salir();
			}
		});		
	}
	
	/**
		Encargado de preparar todos los elementos correspondientes a la ventana
		en la barra del menu, los cuales contienen diferentes opciones
	*/
	public void prepareElementosMenu(){
		menu= new JMenuBar();
		setJMenuBar(menu);
		menu1= new JMenu("Opciones");
		menu2= new JMenu("Configuracion");
		menu3= new JMenu("Acerca De...");
		menu.add(menu1);
		menu.add(menu2);	
		menu.add(menu3);	
		nuevo= new JMenuItem("Nuevo");
		abrir= new JMenuItem("Abrir");
		guardar= new JMenuItem("Guardar");
		guardarComo= new JMenuItem("Guardar Como..");
		salir= new JMenuItem("Salir");
		reiniciar= new JMenuItem("Reiniciar");
		cambiarColor= new JMenuItem("Cambiar de Color");
		configuracionJuego= new JMenuItem("Configuracion Juego");
		nuevo.addActionListener(this);
		reiniciar.addActionListener(this);
		abrir.addActionListener(this);
		guardar.addActionListener(this);
		guardarComo.addActionListener(this);
		salir.addActionListener(this);
		cambiarColor.addActionListener(this);
		configuracionJuego.addActionListener(this);
		menu1.add(nuevo);
		menu1.add(reiniciar);
		menu1.add(abrir);
		menu1.add(guardar);
		menu1.add(guardarComo);
		menu1.add(salir);
		menu2.add(cambiarColor);
		menu2.add(configuracionJuego);
		menu3.addActionListener(this);
	}
	
	/**
		Meotodo Correspondiente a verificar las acciones que pueden
		ocurrir con los diferentes botones y opciones de la ventana
		que posteriormente llevaran a su funcionamiento correspondiente
		al oprimirlas
	*/
	public void actionPerformed(ActionEvent e){
		
		if (e.getSource()==nuevo){reiniciar();}
		else if(e.getSource()==abrir){abrir();}
		else if(e.getSource()==guardar){guardar();}
		else if(e.getSource()==guardarComo){guardarComo();}
		else if(e.getSource()==salir){salir();}
		else if(e.getSource()==configuracionJuego){configuracionJuego();}
		else if(e.getSource()==menu3){acerca();}
		else if(e.getSource()==okey){okey();}
		else if(e.getSource()==reiniciar){reiniciar();}
		else if(e.getSource()==botonIniciar){prepareElementosPantallaConfiguracion();pantallaInicio.setVisible(false);}
		else if(e.getSource()==botonIniciarJuego){prepareElementosPantallaJuego();pantallaConfiguracion.setVisible(false);}
		else if(e.getSource()==botonHome){prepareElementosPantallaInicio();pantallaConfiguracion.setVisible(false);}
	}
	
	/**
		Accion correspondiente al Boton de Okey que cierra la ventana de configuracion del menu
	*/
	public void okey(){
		configuracion.setVisible(false);
		configuracion.dispose();	
	}
	
	/**
		Encargado de reiniciar en totalidad la ventana y el juego de Pong
	*/
	public void reiniciar(){
		juegoPong.reiniciar();
		//pantallaJuego.actualice();
	}
	
	/**
		Encargado de preparar y alistar todos los elementos correspondientes
	*/
	public void configuracionJuego(){
	
	}
	
	/**
	*	Ventana en la cual se muestran las especificaciones del Juego y demas informacion 
		de interes sobre el mismo
	*/
	public void acerca(){
		/**
		acerca= new JFrame();
		acerca.setVisible(true);
		acerca.setResizable(false);
		acerca.setTitle("Acerca de Pong");
		//acerca.setLayout(new FlowLayout());
		acerca.setSize(600,600);
		JLabel l1= new JComponent("Acerca..");
		JPanel acercaPanel= new JPanel();
		acerca.add(acercaPanel);
		Graphics ga;
		acercaPanel.setPreferredSize(new Dimension(600,600));  
		acercaPanel.setBackground(Color.white);
		Image fondoAcerca=(new ImageIcon(getClass().getResource("/configuracion/ComponentesMultimedia/FondosInterfaz/Fondo2.gif")).getImage());
		Dimension size = new Dimension(600,600);
		acercaPanel.setPreferredSize(size);
		acercaPanel.setMinimumSize(size);
		acercaPanel.setMaximumSize(size);
		acercaPanel.setSize(size);
		acercaPanel.setOpaque(false);
		acercaPanel.paintComponent(ga);
		ga.drawImage(fondoAcerca,0,0,600,600,acercaPanel);
		*/
	}
	
	/**
		Encargado de salir, y cerrar la Ventana del Juego cuando se oprime la X500Principal
		o se da en la opcion de salir
	*/
	public void salir (){
		int siNo=JOptionPane.showConfirmDialog(this,"Desea Salir?");
		if (siNo == 0){
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			System.exit(0);
		}
		else{
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}		
	}
	
	/**
		Guardar el Juego en el archivo que ya estaba guardado anteriormente
	*/
	public void guardar(){
		
	}
	
	/**
		Encargado de Guardar el juego en un archivo de extencion dat
		que posteriormente se podra abrir para continuar jugando
	*/
	private void guardarComo(){
		fileChooser.setVisible(true);
		fileChooser.setDialogTitle("Guardar Como");
    	fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo DAT","dat"));
    	int confirmado = fileChooser.showSaveDialog(this);
    	try {
    		if(confirmado==fileChooser.APPROVE_OPTION) {
    			juegoPong.guardar(fileChooser.getSelectedFile());
    		}
    	}
    	catch(PongException e) {
    		JOptionPane.showMessageDialog(this, e.getMessage());
    	}
	}
	
	/**
		Encargado de abrir un juego anteriormente guardado que esta ubicado en un 
		determinado directorio para poder continuar jugando en este
	*/
	private void abrir(){
		fileChooser.setVisible(true);
		fileChooser.setDialogTitle("Abrir");
    	fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo DAT","dat"));
    	int confirmado = fileChooser.showOpenDialog(this);
    	try {
    		if(confirmado==fileChooser.APPROVE_OPTION) {
    			Pong juegoPong2 = juegoPong.abrir(fileChooser.getSelectedFile());
    			juegoPong.cambiarPong(juegoPong2);
				pantallaJuego.actualicePantalla();
    		}
    	}
    	catch(PongException e) {
    		JOptionPane.showMessageDialog(this, e.getMessage());
    	}
	}
	
	/**
		Metodo que Inicia la ventana y da Inicio al juego Pong
	*/
	public static void main(String args[])throws IOException, InstantiationException{
       PongGUI gui=new PongGUI();
       gui.setVisible(true);
   }   
   
}