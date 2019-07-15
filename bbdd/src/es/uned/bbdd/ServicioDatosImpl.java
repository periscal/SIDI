package es.uned.bbdd;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.uned.common.ServicioDatosInterface;
import es.uned.common.Trino;

/**
 *    
 *  @author Juan Periscal Porteiro
 *  @email: jperiscal1@alumno.uned.es
 */
public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {

	// Mapa que relaciona los nicks de los usuarios del sistema con sus contraseñas.
	private Map<String,String> usuariosYpasswords;
	// Mapa que relaciona los nicks de los usuarios del sistema con los nicks de sus seguidores.
	private Map<String,List<String>> usuariosYseguidores;
	// Mapa que relaciona los nicks de los usuarios del sistema con los nicks de sus seguidos.
	private Map<String,List<String>> usuariosYseguidos;
	// Mapa que relaciona los nicks de los usuarios del sistema con sus trinos.
	private Map<String,List<Trino>> trinos;
	// Mapa que relaciona los nicks de los usuarios del sistema con sus trinos pendientes.
	private Map<String,List<Trino>> trinosPendientes;

	protected ServicioDatosImpl() throws RemoteException {
		super();
		usuariosYpasswords = new HashMap<>();
		usuariosYseguidores =new HashMap<>();
		usuariosYseguidos = new HashMap<>();
		trinos = new HashMap<>();
		trinosPendientes = new HashMap<>();
	}


	//----------------------------//
	//------ METODOS REMOTOS -----//
	//----------------------------//
	@Override
	public boolean registroUsuario(String usuario, String password)  throws RemoteException {
		//No se puede registrar un usuario con un nick ya existente
		if(existe(usuario)) return false;

		// Se crean las lista de seguidores y seguidos para el nuevo usuario
		usuariosYseguidores.put(usuario, new ArrayList<String>());
		usuariosYseguidos.put(usuario, new ArrayList<String>());
		trinosPendientes.put(usuario, new ArrayList<Trino>());
		trinos.put(usuario, new ArrayList<Trino>());
		usuariosYpasswords.put(usuario, password);
		return true;
	}

	@Override
	public boolean seguir(String seguidor, String seguido)  throws RemoteException {
		//No se puedeseguir a quien no existe
		if(!existe(seguido)) return false;

		List<String> listaSeguidores = usuariosYseguidores.get(seguido);
		// Si el usuario seguido no es seguido ya por el usuario seguidor entonces
		// lo añade a su lista de seguidores
		if(!listaSeguidores.contains(seguidor)) listaSeguidores.add(seguidor);

		// Sse añade el usuario seguido a la lista de usuarios seguidos del usuario seguidor
		usuariosYseguidos.get(seguidor).add(seguido);

		System.out.println(seguidor+" ahora sigue a "+ seguido);
		return true;
	}

	@Override
	public boolean dejarSeguir(String seguidor, String seguido)  throws RemoteException {
		//No se puede dejar de seguir a quien no existe
		if(!existe(seguido)) return false;

		// Si existe se deja de seguir si lo estaba siguiendo
		usuariosYseguidores.get(seguido).remove(seguidor);
		usuariosYseguidos.get(seguidor).remove(seguido);

		System.out.println(seguidor+" ya no sigue a "+ seguido);
		return true;
	}

	@Override
	public void pendiente(String seguidor, Trino trino)  throws RemoteException {
		trinosPendientes.get(seguidor).add(trino);
		trinos.get(trino.ObtenerNickPropietario()).add(trino);
	}

	@Override
	public List<Trino> getPendientes(String seguidor)  throws RemoteException {
		return trinosPendientes.get(seguidor);
	}

	@Override
	public boolean existe(String nick)  throws RemoteException {
		return (trinos.containsKey(nick) &&
				usuariosYseguidores.containsKey(nick) &&
				usuariosYseguidos.containsKey(nick));
	}

	@Override
	public List<String> seguidores(String nickUsuario) throws RemoteException{
		return usuariosYseguidores.get(nickUsuario);
	}

	@Override
	public void trino(Trino trino) throws RemoteException {
		trinos.get(trino.ObtenerNickPropietario()).add(trino);
	}

	@Override
	public List<String> listaUsuarios() throws RemoteException {
		return List.copyOf(usuariosYseguidores.keySet());
	}

	@Override
	public boolean validar(String usuario, String password)  throws RemoteException{
		return usuariosYpasswords.get(usuario).equals(password);
	}

	//------ OTROS METODOS NO REMOTOS-------//

	/** 
	 * @return lista de los trinos de todos los usuarios
	 */
	public List<Trino> getTrinos(){
		List<Trino> t = new ArrayList<Trino>();
		for(List<Trino> l: trinos.values()) t.addAll(l);
		return t;
	}
}
