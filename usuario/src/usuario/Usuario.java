package usuario;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

import es.uned.common.CallbackUsuarioInterface;
import es.uned.common.ServicioAutenticacionInterface;
import es.uned.common.ServicioGestorInterface;
import es.uned.common.Trino;

/** Esta clase representa el cliente de los objetos distribuidos de las
 *  clases 'ServicioAutenticacionImpl' y 'ServicioGestorImpl', que 
 *  implementan las interfaces remotas 'ServicioAutenticacionInterface' 
 *  y 'ServicioGestorInterface', respectivamente.
 *  También acepta callbacks del servidor.
 *  
 *  @author Juan Periscal Porteiro
 *  @email: jperiscal1@alumno.uned.es
 */
public class Usuario {

	// Interfaz remota del servicio de autenticacion
	private static ServicioAutenticacionInterface objImportadoAutenticacion;
	// Interfaz remota del servicio gestor
	private static ServicioGestorInterface objImportadoGestor;
	// Objeto del servicio de callback de usuario
	private static CallbackUsuarioInterface callbackUsuario;
	// Nick del usuario que se está utilizando en el cliente
	private static String nombreUsuario;

	public static void main(String[] args){

		try {
			int numPuertoRMI; 
			String URLRegistroAutenticacion;
			String URLRegistroGestor;

			// Código que permite obtener el valor del número de puerto
			String host = (args.length < 1) ? "localhost" : args[0];//direccion del servidor
			numPuertoRMI = 1099; // direccion del puerto

			/* Búsqueda de los objetos remotos y cast de las referencias con las correspondientes
			 *  clases de las interfaces remotas – reemplazar “localhost por el nombre del nodo del objeto remoto.
			 */
			// registrar los objetos bajos los nombres indicados en sus interfaces
			URLRegistroAutenticacion = "rmi://"+host +":"+numPuertoRMI+"/"+ServicioAutenticacionInterface.nombre;
			URLRegistroGestor 		 = "rmi://"+host +":"+numPuertoRMI+"/"+ServicioGestorInterface.nombre;

			objImportadoAutenticacion = (ServicioAutenticacionInterface) Naming.lookup(URLRegistroAutenticacion);
			objImportadoGestor = (ServicioGestorInterface) Naming.lookup(URLRegistroGestor);

			callbackUsuario = new CallbackUsuarioImpl();

			// ============= MENU ================//
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			int opcion = 0;

			do {
				System.out.print("\n -----------------------------------------------------\n"
						+ "=-=-=-=-= Bienvenido al sistema básico de microblogging =-=-=-=-=\n"
						+"[1] Registrar un nuevo usuario \n"
						+"[2] Hacer login \n"
						+"[3] Salir\n"
						+ "Opción del menú escogida: ");

				try {opcion = Integer.parseInt(reader.readLine().trim());}
				catch(NumberFormatException e) {opcion = 0;}	

				switch(opcion) {
				case 1:
					System.out.print("\n Introduce el nick del nuevo usuario: ");
					nombreUsuario = reader.readLine();
					System.out.print("\n Introduce la contraseña: ");
					String password = reader.readLine();

					if(!objImportadoAutenticacion.registrar(nombreUsuario,password)) {
						System.out.println("El nick introducido ya existe");
						continue;
					}
					//Una vez registrado, nos logeamos automaticamente
					objImportadoAutenticacion.login(nombreUsuario,password, callbackUsuario);
					break;

				case 2: 
					System.out.print("\n Introduce el nick para logearte: ");
					nombreUsuario = reader.readLine();
					System.out.print("\n Introduce la contraseña: ");

					if(!objImportadoAutenticacion.login(nombreUsuario,reader.readLine(), callbackUsuario)) {
						System.out.println("El nick y/o la constraseña no son correctos");
						continue;
					}
					break;

				case 3: return;

				default:
					System.out.println("Opción incorrecta. Elija una de las opciones del menú");continue;
				}

				do {
					System.out.println("\n\n ------------------------------------------------- \n"
							+ " \t Estás conectado con el nick: "+ nombreUsuario);
					System.out.print("\n<===="+ "Menú Usuario"+"====>\n"
							+"[1] Información del Usuario \n"
							+"[2] Enviar Trino \n"
							+"[3] Listar Usuarios del Sistema \n"
							+"[4] Seguir a \n"
							+"[5] Dejar de seguir a \n"
							+"[6] Borrar trino a los usuarios que todavía no lo han recibido \n"
							+"[7] Salir \"Logout\"\n"
							+ "Opción del menú escogida: ");
					try {opcion = Integer.parseInt(reader.readLine().trim());}
					catch(NumberFormatException e) {opcion = 0;}

					switch(opcion) {
					case 1: 
						System.out.println("Estas logeado con el nick: "+nombreUsuario);
						System.out.println("El objeto remoto 'objImportadoAutenticacion' implementa el servicio '"
								+ ServicioAutenticacionInterface.nombre+ "con la URL:\n"+URLRegistroAutenticacion);
						System.out.println("El objeto remoto 'objImportadoGestor' implementa el servicio '"
								+ ServicioGestorInterface.nombre+ "con la URL:\n"+URLRegistroGestor);
						break;

					case 2: 
						System.out.print("\n Enviar Trino: ");
						boolean exito =objImportadoGestor.enviarTrino(new Trino(reader.readLine(), nombreUsuario));
						if(!exito)System.out.print("... No se ha podido enviar el trino!!!");	
						else System.out.print("... Trino enviado");
						break;

					case 3: 
						System.out.print("\n Los usuarios existentes son: ");
						for(String usuario: objImportadoGestor.listarUsuariosSistema())
							System.out.print(usuario+" | ");
						break;

					case 4: 
						System.out.print("\n Introduzca el nick del usuario al que desea seguir: ");
						objImportadoGestor.seguir(nombreUsuario, reader.readLine());
						break;

					case 5: 
						System.out.print("\n Introduzca el nick del usuario al que desea dejar de seguir: ");
						objImportadoGestor.dejarSeguir(nombreUsuario, reader.readLine());
						break;

					case 6: 
						objImportadoGestor.borrarTrino();
						break;

					case 7: 
						System.out.println("\n Logout realizado");
						objImportadoAutenticacion.logout(nombreUsuario);
						break;
					default:
						System.out.println("Opción incorrecta. Elija una de las opciones del menú");break;
					}
				}while(opcion!=7);
			}while(opcion!=3);
			reader.close();
		}// fin try
		catch (Exception exc) {
			exc.printStackTrace();
		} // fin catch
	} // fin main
}// fin clase
