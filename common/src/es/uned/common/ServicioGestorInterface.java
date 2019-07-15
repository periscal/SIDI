package es.uned.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *    
 *  @author Juan Periscal Porteiro
 *  @email: jperiscal1@alumno.uned.es
 */
public interface ServicioGestorInterface  extends Remote{
	public static final String nombre = "ServicioGestor";

	/**
	 * Envia el trino a todos los seguidores, para los que no estan logeados 
	 * el trino quedara almacenado como pendiente
	 * @param trino
	 * @throws RemoteException
	 */
	public boolean enviarTrino(Trino trino)  throws RemoteException;

	/**
	 * Proporciona la coleccion de todos los nicks de los usuarios
	 *  registrados en el sistema
	 * @return coleccion de nicks
	 */
	public List<String> listarUsuariosSistema()  throws RemoteException;

	/**
	 * Establece la relación entre el seguidor y el usuario al que seguira
	 * @param seguidor
	 * @param seguido
	 * @return true, si se ha podido realizar el alta de seguimiento,
	 *  false en caso contrario
	 * @throws RemoteException
	 */
	public boolean seguir(String seguidor, String seguido)  throws RemoteException;

	/**
	 * Si ambos nicks existen, elimina la relación de seguimiento, si esta existe
	 * @param seguidor
	 * @param seguido
	 * @return true, si se ha podido realizar la baja de seguimiento,
	 *  false en caso contrario
	 * @throws RemoteException
	 */
	public boolean dejarSeguir(String seguidor, String seguido)  throws RemoteException;

	/**
	 * 
	 */
	public boolean borrarTrino()  throws RemoteException;
}
