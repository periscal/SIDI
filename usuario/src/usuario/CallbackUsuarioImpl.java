package usuario;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import es.uned.common.CallbackUsuarioInterface;
import es.uned.common.Trino;

/**
 *    
 *  @author Juan Periscal Porteiro
 *  @email: jperiscal1@alumno.uned.es
 */
public class CallbackUsuarioImpl extends UnicastRemoteObject implements CallbackUsuarioInterface {

	protected CallbackUsuarioImpl() throws RemoteException {
		super();
	}

	@Override
	public void trinos(List<Trino> trinos) throws RemoteException {
		for(Trino t : trinos) System.out.println("\n >"
				+ t.ObtenerNickPropietario()+"# "
				+ t.ObtenerTrino());
	}

	@Override
	public void trino(Trino trino) throws RemoteException {
		System.out.println("\n >"
				+ trino.ObtenerNickPropietario()+"# "
				+ trino.ObtenerTrino());
	}
}// final clase CallbackUsuarioImpl
