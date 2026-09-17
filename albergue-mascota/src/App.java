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
                    
                case 4:

                case 5:
                    sistema.agregarHistorialPorId(scanner);
                    break;

                case 6:

                    sistema.mostrarHistorialPorId(scanner);
                    break;

                case 7:

                case 8:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción incorrecta");
            }
        } while (opcion != 8);

        scanner.close();
    }
}