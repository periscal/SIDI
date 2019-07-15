package es.uned.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *    
 *  @author Juan Periscal Porteiro
 *  @email: jperiscal1@alumno.uned.es
 */

public interface ServicioDatosInterface  extends Remote{
	public static final String nombre = "ServicioDatos";

	/**
	 * Registra un nuevo usuario.El metodo devuelve 'true',si ha sido posible
	 * registrar el nuevo usuario con el nick proporcionado; y 'false' si ya
	 * existe el nick en la base de datos.
	 * @param usuario nick del nuevo usuario
	 * @param password constraseña usuario
	 * @return booleano que indica si se ha podido o no realizar el registro
	 * @throws RemoteException
	 */
	public boolean registroUsuario(String usuario, String password)  throws RemoteException ;

	/**
	 * Añade, si existen, el seguidor al Map de usuarios y sus seguidores,
	 * y el seguido al Map de usuarios y los usuarios a los que siguen.
	 * @param seguidor
	 * @param seguido
	 * @return true, si se ha podido realizar el alta de seguimiento,
	 *  false en caso contrario
	 * @throws RemoteException
	 */
	public boolean seguir(String seguidor, String seguido) throws RemoteException ;

	/**
	 * Si ambos nicks existen, elimina la relación de seguimiento, si esta existe
	 * @param seguidor
	 * @param seguido
	 * @return true, si se ha podido realizar la baja de seguimiento,
	 *  false en caso contrario
	 * @throws RemoteException
	 */
	public boolean dejarSeguir(String seguidor, String seguido) throws RemoteException;

	/**
	 * Proporciona la coleccion de todos los nicks de los usuarios
	 *  registrados en el sistema
	 * @return coleccion de nicks
	 */
	public List<String> listaUsuarios() throws RemoteException;

	/**
	 * Determina si un usuario existe en el regitro(si está registrado)
	 * @return true, si esta registrado, false en caso contrario
	 */
	public boolean existe(String nombreUsuario) throws RemoteException;

	/**
	 * Devuelve el numero de seguidores de un usuario
	 * @param nickUsuario usuario del que se necesitan sus seguidores
	 * @return lista de seguidores
	 */
	public List<String> seguidores(String nickUsuario) throws RemoteException;

	/** Añade el trino al mapa de trinos pendientes de los usuarios
	 * seguidores del emisor del trino
	 * @param seguidor 
	 * @param trino
	 */
	public void pendiente(String seguidor, Trino trino) throws RemoteException;

	/**
	 * Almacena cada trino enviado por cada usuario
	 * @param trino
	 * @throws RemoteException
	 */
	public void trino(Trino trino) throws RemoteException;

	/**
	 * Ofrece la lista de trinos pendientes de ser recibidos por 
	 * el seguidor deslogeado
	 * @param seguidor
	 * @return
	 */
	public List<Trino> getPendientes(String seguidor) throws RemoteException;

	/**
	 * Comprueba que la constraseña proporcionada por el usuario 
	 * es correcto
	 * @return true, si la contraseña es correcta, false en caso contrario
	 */
	boolean validar(String usuario, String password) throws RemoteException;
}
