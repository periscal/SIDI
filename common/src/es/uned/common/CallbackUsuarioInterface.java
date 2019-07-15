package es.uned.common;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *    
 *  @author Juan Periscal Porteiro
 *  @email: jperiscal1@alumno.uned.es
 */
public interface CallbackUsuarioInterface extends Remote{
	public static final String nombre = "CallbackUsuario";

	/** Este método remoto es invocado por un servidor que 
	 * realice un callback al usuario que implementa esta interfaz.
	 *  El parámetro es un trino enviado por un usuario seguido 
	 *  mientras el seguidor esta logado  
	 * 
	 * @param trinos- trino enviados por un usuario seguido
	 */
	public void trino(Trino trino)throws RemoteException;
	
	/** Este método remoto es invocado por un servidor que 
	 * realice un callback al usuario que implementa esta interfaz.
	 *  El parámetro es una lista que contiene los trinos enviados
	 *  por los seguidos mientras el seguidor no esta logeado. 
	 * 
	 * @param trinos- trinos enviados por los usuarios seguidos
	 */
	public void trinos(List<Trino> trinos)throws RemoteException;
}
