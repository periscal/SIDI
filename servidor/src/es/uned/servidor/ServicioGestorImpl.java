package es.uned.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import es.uned.common.ServicioGestorInterface;
import es.uned.common.Trino;

/**
 *    
 *  @author Juan Periscal Porteiro
 *  @email: jperiscal1@alumno.uned.es
 */
public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {

	protected ServicioGestorImpl() throws RemoteException {
		super();
	}

	//----------------------------//
	//------ METODOS REMOTOS -----//
	//----------------------------//

	@Override
	public synchronized boolean enviarTrino(Trino trino) throws RemoteException {
		for(String seguidor : Servidor.objImportadoDatos.seguidores(trino.ObtenerNickPropietario())) {
			if(Servidor.logeados.containsKey(seguidor)) {
				Servidor.logeados.get(seguidor).trino(trino);
				Servidor.objImportadoDatos.trino(trino);
				System.out.println("El usuario '"+trino.ObtenerNickPropietario()+"' ha enviado un trino");
			}else Servidor.objImportadoDatos.pendiente(seguidor,trino);
		}
		return true;
	}

	@Override
	public List<String>  listarUsuariosSistema() throws RemoteException {
		return Servidor.objImportadoDatos.listaUsuarios();
	}

	@Override
	public boolean seguir(String seguidor, String seguido) throws RemoteException {
		return Servidor.objImportadoDatos.seguir(seguidor, seguido);
	}

	@Override
	public boolean dejarSeguir(String seguidor, String seguido) throws RemoteException {
		return Servidor.objImportadoDatos.dejarSeguir(seguidor, seguido);
	}

	@Override
	public boolean borrarTrino() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
}
