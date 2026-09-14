import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        Mascota mascota = null;

        do {
            System.out.println("\n-------SISTEMA ALBERGUE MASCOTAS-------");
            System.out.println("1. Registrar Mascota");
            System.out.println("2. Ver Datos de Mascota");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    mascota = new Mascota();
                    System.out.println("\n-------REGISTRO DE MASCOTA-------");
                    
                    System.out.print("Ingresa nombre: ");
                    String nombre = scanner.nextLine();
                    mascota.setNombre(nombre);

                    System.out.print("Ingresa raza: ");
                    String raza = scanner.nextLine();
                    mascota.setRaza(raza);

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
                    Double peso = scanner.nextDouble();
                    mascota.setPeso(peso);

                    System.out.print("Ingresa tamaño: ");
                    Double tamaño = scanner.nextDouble();
                    mascota.setTamaño(tamaño);
                    
                    mascota = new Mascota(nombre, raza, edad, genero, peso, tamaño);
                    System.out.println("\nRegistro exitoso");
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
                        System.out.println("No hay mascotas registradas");
                    }
                    break;
                case 3:
                    System.out.println("Saliendo del sistema");
                    break;
                default:
                    System.out.println("Opción incorrecta");
            }
        } while (opcion != 3);

        scanner.close();
    }
}