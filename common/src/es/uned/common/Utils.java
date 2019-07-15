package es.uned.common;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *  Clase con codigo comun a otras clases  
 *  @author Juan Periscal Porteiro
 *  @email: jperiscal1@alumno.uned.es
 */
public class Utils {

	public static final String CODEBASE = "java.rmi.server.codebase";

	public static void setCodeBase(Class<?> c) {
		String ruta = c.getProtectionDomain().getCodeSource().getLocation().toString();

		String path = System.getProperty(CODEBASE);

		if (path != null && !path.isEmpty()) {
			ruta = path + " " + ruta;
		}

		System.setProperty(CODEBASE, ruta);
	}
	
	// Este método arranca un registro RMI en la máquina
	//local, si no existe en el número de puerto especificado
	public static void arrancarRegistro (int numPuertoRMI) throws RemoteException {
		try {
			Registry registro = LocateRegistry.getRegistry(numPuertoRMI);
			registro.list();
			// El método anterior lanza una excepción si el registro no existe.
		}
		catch (RemoteException exc) {
			//No existe un registro válido en este puerto.
			System.out.println("El registro RMI no se puede localizar en el puerto:"+ numPuertoRMI);
			Registry registro =LocateRegistry.createRegistry(numPuertoRMI);
			System.out.println("Registro RMI creado en el puerto " + numPuertoRMI);
		} // fin catch
	} // fin arrancarRegistro
}
