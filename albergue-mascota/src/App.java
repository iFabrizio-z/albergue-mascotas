import java.util.InputMismatchException;
import java.util.Scanner;
public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        
        // Instancia principal que controla todo el programa
        SistemaAlbergueMascota sistema = new SistemaAlbergueMascota();
            
        
        do {
            System.out.println("\n-------SISTEMA ALBERGUE MASCOTAS-------");
            System.out.println("1. Registrar Mascota");
            System.out.println("2. Ver Datos de Mascota por Id");
            System.out.println("3. Crear solicitud de adopción");
            System.out.println("4. Evaluar solicitud pendiente");
            System.out.println("5. Agregar historial clínico por id de mascota");
            System.out.println("6. Mostrar historial clínico por id de mascota");
            System.out.println("7. Registrar Donación");
            System.out.println("8. Listar Donaciones");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); 
            try{
                switch (opcion) {
                    case 1:
                        sistema.registrarMascota(scanner);
                        break;
                    
                    case 2:
                        sistema.buscarMascotaPorId(scanner);
                        break;
                        
                
                    case 3:
                        sistema.crearSolicitudAdopcion(scanner);
                        break;
                        
                    case 4:
                        sistema.evaluarSolicitudPorId(scanner);
                        break;
                        
                    case 5:
                        sistema.agregarHistorialPorId(scanner);
                        break;
                
                    case 6:
                        sistema.mostrarHistorialPorId(scanner);
                        break;
                
                    case 7:
                        sistema.registrarDonacion(scanner);
                        break;
                
                    case 8:
                        sistema.listarDonaciones();
                        break;
                
                    case 9:
                        System.out.println("Saliendo del sistema...");
                        break;
                
                    default:
                        System.out.println("Opción incorrecta, intente de nuevo.");
                }
            }catch(InputMismatchException e){
                System.out.println("Solo números enteros");
                scanner.nextLine();
                opcion = 0;
            }

            
        } while (opcion != 9);

        scanner.close();
    }
}