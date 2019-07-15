package es.uned.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *    
 *  @author Juan Periscal Porteiro
 *  @email: jperiscal1@alumno.uned.es
 */
public interface ServicioAutenticacionInterface  extends Remote{

	public static final String nombre = "ServicioAutenticacion";

	/**
	 * Regsitra un usuario en la base de datos
	 * @param nombreUsuario
	 * @param password
	 * @return true, si se ha registrado el usuario con exito
	 * @throws RemoteException
	 */
	public boolean registrar(String nombreUsuario, String password) throws RemoteException ;

	/**Este método remoto permite a un cliente de objeto 
	 * registrarse para callback
	 * @param objCallbackUsuario es una referencia al cliente de objeto; 
	 * el servidor lo utiliza para realizar los callbacks
	 * @throws RemoteException
	 */
	public boolean login(String nombreUsuario,String password,CallbackUsuarioInterface objCallbackUsuario) throws RemoteException ;

	/**
	 *  Este método remoto permite a un cliente de objeto
	 *  cancelar su registro para callback
	 * @param nickUsuario
	 * @return
	 * @throws RemoteException
	 */
	public boolean logout(String nickUsuario) throws RemoteException ;
}
