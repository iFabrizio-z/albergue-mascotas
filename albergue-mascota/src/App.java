import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        SistemaAlbergueMascota sistema = new SistemaAlbergueMascota();
        

        do {
            System.out.println("\n-------SISTEMA ALBERGUE MASCOTAS-------");
            System.out.println("1. Registrar Mascota");
            System.out.println("2. Ver Datos de Mascota");
            System.out.println("3. Crear solicitud de adopción");
            System.out.println("4. Evaluar solicitud pendiente");
            System.out.println("5. Agregar historial clínico por id de mascota");
            System.out.println("6. Mostrar historial clínico por id de mascota");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    

                    
                case 2:
                    
                case 3:
                    // TUVISTE QUE HABER REGISTRADO UNA MASCOTA PRIMERO
                    if (mascota != null) {
                        System.out.println("\n-------NUEVA SOLICITUD DE ADOPCIÓN-------");
                        System.out.print("Nombre del adoptante: ");
                        String nombreAdoptante = scanner.nextLine();
                        
                        System.out.print("Teléfono de contacto: ");
                        String telefono = scanner.nextLine();

                        // Aquí usamos el constructor de la clase que tú creaste
                        solicitud = new SolicitudAdopcion(nombreAdoptante, telefono, mascota);
                        System.out.println("\n¡Solicitud creada exitosamente! Estado actual: " + solicitud.getEstado());
                    } else {
                        System.out.println("Error: No puedes crear una solicitud si no hay una mascota registrada.");
                    }
                    break;
                
                  
                    
                case 4:
                   if (solicitud != null) {
                       System.out.println("\n-------EVALUAR SOLICITUD-------");
                       System.out.println("Adoptante: " + solicitud.getNombreAdoptante());
                       System.out.println("Teléfono: " + solicitud.getTelefono());
                       System.out.println("Mascota pedida: " + solicitud.getMascota().getNombre());
                       System.out.println("Estado actual: " + solicitud.getEstado());
                        
                       System.out.print("\n¿Aprobar solicitud? (1 para Sí, 2 para No): ");
                       int decision = scanner.nextInt();
                       scanner.nextLine();

                       // Usamos tu método evaluar
                       if (decision == 1) {
                           solicitud.evaluar(true);
                           mascota.setEstadoAdopcion("Adoptado"); // Actualizamos a la mascota también
                       } else if (decision == 2) {
                            solicitud.evaluar(false);
                       } else {
                            System.out.println("Opción no válida.");
                       }
                   } else {
                       System.out.println("No hay ninguna solicitud pendiente para evaluar.");
                   }
                   break;
                case 5:
                
                
                 

                case 6:

                    sistema.mostrarHistorialPorId(scanner);
                    break;

                case 7:

                case 8:
                    System.out.println("Saliendo del sistema...");
                    break;


                default:
                    System.out.println("Opción incorrecta, intente de nuevo.");
            }
        } while (opcion != 8);

        scanner.close();
    }
}
