package es.uned.bbdd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.util.List;
import es.uned.common.ServicioDatosInterface;
import es.uned.common.Trino;
import es.uned.common.Utils;

/**
 * Esta clase representa la base de datos, que contiene el objeto distribuido de la
 *  clase 'ServicioDatosImpl', que implementa la interfaz remota 'ServicioDatosInterface' 
 *    
 *  @author Juan Periscal Porteiro
 *  @email: jperiscal1@alumno.uned.es
 */
public class Basededatos {
	private static ServicioDatosImpl objExportadoDatos;

	public static void main(String[] args){
		int numPuertoRMI; 
		String URLRegistroBasedeDatos;

		Utils.setCodeBase(ServicioDatosImpl.class);

		try{
			// Código que permite obtener el valor del número de puerto
			String host = (args.length < 1) ? "localhost" : args[0];//direccion del servidor
			numPuertoRMI = 1099; // direccion del puerto

			/*Creación de un objeto de la implementacion de la interfaz 
			 * remota 'ServicioDatosImpl'*/
			objExportadoDatos = new ServicioDatosImpl();


			// ======= EXPORTACION DE LOS OBJETOS =======//

			// Arranque Registro
			Utils.arrancarRegistro(numPuertoRMI);

			// registrar el objeto bajo el nombre indicado en su interfaz
			URLRegistroBasedeDatos = "rmi://"+host +":"+numPuertoRMI+"/"+ServicioDatosInterface.nombre;

			/* El método 'rebind' permite almacenar en el registro una referencia a un 
			 * objeto con un URL de la forma:
				rmi://<nombre máquina>:<número puerto>/<nombre referencia>
			 */
			Naming.rebind(URLRegistroBasedeDatos	, objExportadoDatos);
			//=====================================//

			// ============= MENU ================//
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			int opcion = 0;

			do {
				System.out.print("\n\n<===="+ "Menú Base de Datos"+"====>\n"
						+"[1] Información de la base de datos \n"
						+"[2] Listar Usuarios Registrados \n"
						+"[3] Listar Trinos \n"
						+"[4] Salir\n"
						+ "Opción del menú escogida: ");
				try {opcion = Integer.parseInt(reader.readLine().trim());}
				catch(NumberFormatException e) {opcion = 0;}
				
				switch(opcion) {
				case 1: 	
					System.out.println("El servicio Datos es implementado por el objeto remoto: "
							+"objExportadoDatos");
					break;

				case 2: 		
					for(String s:objExportadoDatos.listaUsuarios())
						System.out.println(s);
					break;

				case 3: 		
					for(Trino t: (List<Trino>)objExportadoDatos.getTrinos())
						System.out.println(t.toString());
					break;
				case 4: return;
				default:
					System.out.println("Opción incorrecta. Elija una de las opciones del menú");
				}
			}while(opcion!=4);
			reader.close();
			//======================================//
		}catch (Exception excr) {System.out.println("Excepción en Servidor.main: " + excr);}
	}
} // fin main

