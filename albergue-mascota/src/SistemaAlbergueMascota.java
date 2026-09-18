// Lógica de programación relacionados con la clase (Métodos de cada clase)

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class SistemaAlbergueMascota {

    List<Mascota> mascotas = new ArrayList<>();
    List<SolicitudAdopcion> solicitudes = new ArrayList<>();
    List<Donacion> donaciones = new ArrayList<>(); // Nueva lista para donaciones

    public void agregarHistorialPorId(Scanner scanner){

        System.out.println("Ingrese el id de mascota: ");
        int idBuscar = scanner.nextInt();
        scanner.nextLine();

        Mascota mascota = mascotas.stream().filter(e-> e.getIdMascota() == idBuscar).findFirst().orElse(null);

        if(mascota != null){
            System.out.println("Ingrese el procedimiento (vacunación o cirugía): ");
            String procedimiento = scanner.nextLine();

            System.out.println("Ingrese el tratamiento: ");
            String tratamiento = scanner.nextLine();

            System.out.println("Ingrese el diagnóstico: ");
            String diagnostico = scanner.nextLine();

            System.out.println("Ingrese el nombre del veterinario: ");
            String nombreVeterinario = scanner.nextLine();

            int nuevoIndice = mascotas.size() + 1;

            HistorialClinico historial = new HistorialClinico(nuevoIndice, procedimiento, diagnostico, tratamiento, nombreVeterinario);
            mascota.agregarHistorialMedico(historial);
            System.out.println("El registro del historial fue exitoso...");
        }
        else{
            System.out.println("ID de mascota incorrecto");
        }

    }

    public void mostrarHistorialPorId(Scanner scanner){
        System.out.println("Ingresa el id de la mascota para buscar historial: ");
        int idBuscar = scanner.nextInt();
        scanner.nextLine();

        Mascota mascota = mascotas.stream().filter(e-> e.getIdMascota() == idBuscar)
                                            .findFirst()
                                            .orElse(null);
                                        
        if(mascota != null){
            
            mascota.getHistorialClinico().forEach(e -> System.out.println(e));
            
        }

        else{
            
            System.out.println("ID de mascota incorrecto");

        }
    }
    //Solicitud de adopción
    public void crearSolicitudAdopcion(Scanner scanner) {
        System.out.println("Ingrese el id de la mascota que desean adoptar: ");
        int idBuscar = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        // Busca la mascota usando el formato stream
        Mascota mascota = mascotas.stream().filter(e -> e.getIdMascota() == idBuscar).findFirst().orElse(null);

        if (mascota != null) {
            System.out.println("Ingrese el nombre del adoptante: ");
            String nombre = scanner.nextLine();

            System.out.println("Ingrese los apellidos: ");
            String apellidos = scanner.nextLine();

            System.out.println("Ingrese el DNI: ");
            String dni = scanner.nextLine();

            System.out.println("Ingrese la dirección: ");
            String direccion = scanner.nextLine();

            System.out.println("Ingrese el email: ");
            String email = scanner.nextLine();

            System.out.println("Ingrese el teléfono móvil: ");
            String telefono = scanner.nextLine();

            int nuevoIndice = solicitudes.size() + 1; // Genera el ID automáticamente

            // Instancia el objeto y lo guarda en la lista
            SolicitudAdopcion nuevaSolicitud = new SolicitudAdopcion(nuevoIndice, nombre, apellidos, dni, direccion, email, telefono, mascota);
            solicitudes.add(nuevaSolicitud);
            
            System.out.println("El registro de la solicitud fue exitoso...");
        } else {
            System.out.println("ID de mascota incorrecto");
        }
    }

    public void evaluarSolicitudPorId(Scanner scanner) {
        System.out.println("Ingresa el id de la solicitud para evaluarla: ");
        int idBuscar = scanner.nextInt();
        scanner.nextLine();

        // Busca la solicitud usando streams
        SolicitudAdopcion solicitud = solicitudes.stream().filter(e -> e.getIdSolicitud() == idBuscar).findFirst().orElse(null);

        if (solicitud != null) {
            if (!solicitud.getEstado().equals("Pendiente")) {
                System.out.println("Esta solicitud ya fue procesada anteriormente. Estado: " + solicitud.getEstado());
                return;
            }

            System.out.println(solicitud.toString());
            System.out.println("\n¿Aprobar solicitud de adopción? (1: Aprobar, 2: Rechazar): ");
            int decision = scanner.nextInt();
            scanner.nextLine();

            if (decision == 1) {
                solicitud.setEstado("Aprobada"); // Usa el nuevo setter de tu clase
                
                // Cambia el estado de la mascota a Adoptado
                if (solicitud.getMascota() != null) {
                    solicitud.getMascota().setEstadoAdopcion("Adoptado"); 
                }
                System.out.println("Solicitud aprobada con éxito. Mascota adoptada.");
                
            } else if (decision == 2) {
                solicitud.setEstado("Rechazada");
                System.out.println("Solicitud rechazada con éxito.");
            } else {
                System.out.println("Opción no válida.");
            }
        } else {
            System.out.println("ID de solicitud incorrecto");
        }
    }

    // MÉTODOS DE DONACIONES
// MÉTODOS DE DONACIONES CON VALIDACIÓN DE NÚMEROS
    public void registrarDonacion(Scanner scanner) {
        System.out.println("\n------- REGISTRAR DONACIÓN -------");
        System.out.print("Ingrese el nombre del donante: ");
        String donante = scanner.nextLine();

        System.out.println("Seleccione el tipo de donación:");
        System.out.println("1. Efectivo");
        System.out.println("2. Comida para mascotas (Kg)");
        System.out.print("Opción: ");
        
        int tipo = 0;
        try {
            tipo = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error: Debe ingresar un número válido.");
            scanner.nextLine(); // Limpiar el buffer
            return;
        }

        int nuevoId = donaciones.size() + 1;

        if (tipo == 1) {
            System.out.print("Ingrese el monto en efectivo (S/): ");
            try {
                double monto = scanner.nextDouble();
                scanner.nextLine();

                if (monto > 0) {
                    Donacion donacion = new Donacion(nuevoId, donante, monto);
                    donaciones.add(donacion);
                    System.out.println("Donación en efectivo registrada exitosamente.");
                } else {
                    System.out.println("El monto ingresado debe ser mayor a 0.");
                }
            } catch (Exception e) {
                System.out.println("Error: No se pueden ingresar letras en el monto. Debe ingresar un valor numérico.");
                scanner.nextLine(); // Limpiar entrada incorrecta
            }

        } else if (tipo == 2) {
            System.out.print("Ingrese la cantidad de comida en kilogramos (Kg): ");
            try {
                double cantidad = scanner.nextDouble();
                scanner.nextLine();

                if (cantidad > 0) {
                    Donacion donacion = new Donacion(nuevoId, donante, cantidad, true);
                    donaciones.add(donacion);
                    System.out.println("Donación de comida registrada exitosamente.");
                } else {
                    System.out.println("La cantidad ingresada debe ser mayor a 0.");
                }
            } catch (Exception e) {
                System.out.println("Error: No se pueden ingresar letras en los Kg. Debe ingresar un valor numérico.");
                scanner.nextLine(); // Limpiar entrada incorrecta
            }

        } else {
            System.out.println("Tipo de donación no válido.");
        }
    }

    public void listarDonaciones() {
        System.out.println("\n------- HISTORIAL DE DONACIONES -------");
        if (donaciones.isEmpty()) {
            System.out.println("No hay donaciones registradas en el sistema.");
            return;
        }

        double totalEfectivo = 0;
        double totalComida = 0;

        for (Donacion d : donaciones) {
            System.out.println(d);
            if (d.getTipoDonacion().equalsIgnoreCase("Efectivo")) {
                totalEfectivo += d.getMontoEfectivo();
            } else {
                totalComida += d.getCantidadComidaKg();
            }
        }

        System.out.println("\n--- RESUMEN TOTAL ---");
        System.out.printf("Total recaudado en Efectivo: S/ %.2f\n", totalEfectivo);
        System.out.printf("Total recaudado en Comida: %.2f Kg\n", totalComida);
    }
}