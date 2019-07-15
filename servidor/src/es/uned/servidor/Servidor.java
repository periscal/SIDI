package es.uned.servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.util.HashMap;
import java.util.Map;

import es.uned.common.CallbackUsuarioInterface;
import es.uned.common.ServicioAutenticacionInterface;
import es.uned.common.ServicioDatosInterface;
import es.uned.common.ServicioGestorInterface;
import es.uned.common.Utils;

/**
 * Esta clase representa el servidor de los objetos distribuidos de las
 *  clases 'ServicioAutenticacionImpl' y 'ServicioGestorImpl', que 
 *  implementan las interfaces remotas 'ServicioAutenticacionInterface' 
 *  y 'ServicioGestorInterface', respectivamente.
 */

public class Servidor{

	static ServicioDatosInterface objImportadoDatos;
	static ServicioAutenticacionImpl objExportadoAutenticacion;
	static ServicioGestorImpl objExportadoGestor;

	// Mapa que contiene los usuarios logeados en el sistema con su repectivo servicio de callback
	public static Map<String, CallbackUsuarioInterface> logeados;

	public static void main(String[] args){
		int numPuertoRMI; 
		String URLRegistroAutenticacion;
		String URLRegistroGestor;
		String URLRegistroDatos;

		Utils.setCodeBase(ServicioAutenticacionInterface.class);
		Utils.setCodeBase(ServicioGestorInterface.class);

		logeados = new HashMap<>();
		
		try{
			// Código que permite obtener el valor del número de puerto
			String host = (args.length < 1) ? "localhost" : args[0];//direccion del servidor
			numPuertoRMI = 1099; // direccion del puerto

			// Arranque Registro
			Utils.arrancarRegistro(numPuertoRMI);

			// ===== IMPORTACION DE OBJETOS REMOTOS =====/
			URLRegistroDatos = "rmi://"+host +":"+numPuertoRMI+"/"+ServicioDatosInterface.nombre;
			objImportadoDatos = (ServicioDatosInterface) Naming.lookup(URLRegistroDatos);
			//=====================================//

			/*Creación de unos objetos de las implementaciones de las interfaces 
			 * remotas 'ServicioAutenticacionImpl' y 'ServicioGestorImpl' respectivamente*/
			objExportadoAutenticacion = new ServicioAutenticacionImpl();
			objExportadoGestor = new ServicioGestorImpl();

			// ==== EXPORTACION DE LOS OBJETOS REMOTOS ===//

			// registrar los objetos bajos los nombres indicados en sus interfaces
			URLRegistroAutenticacion = "rmi://"+host +":"+numPuertoRMI+"/"+ServicioAutenticacionInterface.nombre;
			URLRegistroGestor 		 = "rmi://"+host +":"+numPuertoRMI+"/"+ServicioGestorInterface.nombre;

			/* El método 'rebind' permite almacenar en el registro una referencia a un 
			 * objeto con un URL de la forma:
					rmi://<nombre máquina>:<número puerto>/<nombre referencia>
			 */
			Naming.rebind(URLRegistroAutenticacion	, objExportadoAutenticacion);
			Naming.rebind(URLRegistroGestor			, objExportadoGestor);

			System.out.println("Servidor Autenticacion preparado.\n"
					+ "Servidor Gestor preparado.");

			//=====================================//
			// ============= MENU ================//
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			int opcion = 0;

			do {
				System.out.print("\n\n<===="+ "Menú Servidor"+"====>\n"
						+"[1] Información del Servidor \n"
						+"[2] Listar Usuarios Logeados \n"
						+"[3] Salir \n"
						+ "Opción del menú escogida: ");
				try {opcion = Integer.parseInt(reader.readLine().trim());}
				catch(NumberFormatException e) {opcion = 0;}

				switch(opcion) {
				case 1: 		
					System.out.println("El objeto remoto 'objImportadoDatos' implementa el servicio '"
							+ ServicioDatosInterface.nombre+ "con la URL:\n"+URLRegistroDatos);
					break;

				case 2:
					System.out.println("Los usuarios logeados son: ");
					for(String s:logeados.keySet()) System.out.println("=> "+s);
					break;

				case 3: return;
				default:
					System.out.println("Opción incorrecta. Elija una de las opciones del menú");
				}
			}while(opcion!=3);
			reader.close();
		} // fin try
		catch (Exception excr) {
			System.out.println("Excepción en Servidor.main: " + excr);
		} // fin catch
	} // fin main	
}
