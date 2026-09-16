import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
 Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        
        // Variables para guardar la información temporalmente
        Mascota mascota = null;
        SolicitudAdopcion solicitud = null; // ¡Aquí entra tu clase!

        do {
            System.out.println("\n-------SISTEMA ALBERGUE MASCOTAS-------");
            System.out.println("1. Registrar Mascota");
            System.out.println("2. Ver Datos de Mascota");
            System.out.println("3. Crear Solicitud de Adopción"); // Nueva opción
            System.out.println("4. Evaluar Solicitud Pendiente"); // Nueva opción
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    mascota = new Mascota();
                    System.out.println("\n-------REGISTRO DE MASCOTA-------");
                    
                    System.out.print("Ingresa nombre: ");
                    mascota.setNombre(scanner.nextLine());

                    System.out.print("Ingresa raza: ");
                    mascota.setRaza(scanner.nextLine());

                    String genero;
                    do {
                        System.out.print("Ingresa género (Macho/Hembra): ");
                        genero = scanner.nextLine();
                    } while (!mascota.setGenero(genero));

                    int edad;
                    do {
                        System.out.print("Edad: ");
                        edad = scanner.nextInt();
                        scanner.nextLine();
                    } while (!mascota.setEdad(edad));

                    String estado;
                    do {
                        System.out.print("Estado (Refugiado/Tratamiento/Adoptado): ");
                        estado = scanner.nextLine();
                    } while (!mascota.setEstadoAdopcion(estado));

                    System.out.print("Ingresa peso: ");
                    mascota.setPeso(scanner.nextDouble());

                    System.out.print("Ingresa tamaño: ");
                    mascota.setTamaño(scanner.nextDouble());
                    scanner.nextLine(); // Limpiar el buffer del scanner
                    
                    // Eliminé la línea que sobreescribía el objeto mascota aquí
                    System.out.println("\nRegistro de mascota exitoso");
                    break;

                case 2:
                    if (mascota != null) {
                        System.out.println("\n-------DATOS - MASCOTA-------");
                        System.out.println("Nombre: " + mascota.getNombre());
                        System.out.println("Raza: " + mascota.getRaza());
                        System.out.println("Género: " + mascota.getGenero());
                        System.out.println("Edad: " + mascota.getEdad() + " años");
                        System.out.println("Peso: " + mascota.getPeso());
                        System.out.println("Tamaño: " + mascota.getTamaño());
                        System.out.println("Estado: " + mascota.getEstadoAdopcion());
                    } else {
                        System.out.println("No hay mascotas registradas. Registre una primero.");
                    }
                    break;

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
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción incorrecta, intente de nuevo.");
            }
        } while (opcion != 5); // Actualizado al número 5

        scanner.close();
    }
}
