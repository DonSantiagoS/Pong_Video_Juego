package persistencia;

import aplicacion.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.awt.Color;
/**
*		------------------------------------------------------------------------
*		------------------------ PONG ------------------------------------------
*		------------------------------------------------------------------------
*
* CLASE:	PongDAO, responsable de llevar acabo el proceso de guardar y abrir 
			juegos en proceso.
*
* @author: Santiago Buitrago
* @author: Brayan Macias
*
* @version 4.5 Final	
*/
public class PongDAO {
	/**
	*	Constructor PongDAO
	*/
	public PongDAO(){
		
	}
	
	/**
	* Abre el juego guardado en un archivo dentro del sistema en el cual se esta utilizando el juego.
	*
	* @param file: Archivo que contiene el juego guardado debe ser de extension dat
	* @return pg: El juego contenido dentro del archivo el cual se abrira y permitira continuar desde el
	*			  estado guardado	
	*/
	public Pong abrir(File file) throws PongException{
		Pong pg = null;
		if (!file.getName().endsWith(".dat")) throw new PongException(PongException.TYPE_DAT_ERROR);
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			pg = (Pong) in.readObject();
			in.close();
		}catch(ClassNotFoundException e) {
			throw new PongException(PongException.FILE_NOT_FOUND_ERROR);
		}catch (IOException e) {
			throw new PongException("Ocurrio un error al abrir el archivo" + file.getName());
		}
		return pg;
	}
	/**
	* Guarda el juego en un archivo dentro del sistema en el cual se esta utilizando el juego.
	*
	* @param file: Archivo en el cual se guardara el juego, debe ser de extension dat
	* @param pg: El juego que se guardara dentro del archivo desde el estado guardado actual
	*/	
	public void guardar(Pong pg, File file) throws PongException{
		if (!file.getName().endsWith(".dat")) throw new PongException(PongException.TYPE_DAT_ERROR);
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(pg);
			out.close();
		}catch (IOException e) {
			throw new PongException("Ocurrio un error al salvar " + file.getName());
		}
	}
}