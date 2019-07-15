package es.uned.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import es.uned.common.CallbackUsuarioInterface;
import es.uned.common.ServicioAutenticacionInterface;

/**
 *    
 *  @author Juan Periscal Porteiro
 *  @email: jperiscal1@alumno.uned.es
 */
public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {

	protected ServicioAutenticacionImpl() throws RemoteException {
		super();
	}

	//----------------------------//
	//------ METODOS REMOTOS -----//
	//----------------------------//
	@Override
	public boolean registrar(String nombreUsuario, String password) throws RemoteException  {
		//No se puede registrar con un nick ya existente
		boolean exito = Servidor.objImportadoDatos.registroUsuario(nombreUsuario,password);
		if(exito) System.out.println("\n Nuevo usuario '"+nombreUsuario+"' registrado");
		return exito;
	}

	@Override
	public boolean login(String nombreUsuario, String password, CallbackUsuarioInterface objCallbackUsuario) throws RemoteException  {

		//No se puede logear un usario no registrado
		if(!Servidor.objImportadoDatos.existe(nombreUsuario)) return false;
		
		//No se puede loguear si la contraseña no es correcta
		if(!Servidor.objImportadoDatos.validar(nombreUsuario, password))return false;
		
		// almacena el objeto callback en la lista
		if (!Servidor.logeados.containsKey(nombreUsuario)){
			Servidor.logeados.put(nombreUsuario, objCallbackUsuario);
			System.out.println("\n Usuario '"+nombreUsuario+"' logeado");
		}
		objCallbackUsuario.trinos(Servidor.objImportadoDatos.getPendientes(nombreUsuario));
		return true;
	}

	@Override
	public boolean logout(String nombreUsuario) throws RemoteException  {
		if (!(Servidor.logeados.containsKey(nombreUsuario))) return false;

		Servidor.logeados.remove(nombreUsuario);
		System.out.println("\n Usuario '"+nombreUsuario+"' deslogeado");
		return true;
	}
}
