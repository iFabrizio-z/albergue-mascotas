// Lógica de programación relacionados con la clase (Métodos de cada clase)

import java.util.List;
import java.util.Scanner;

import java.util.ArrayList;

public class SistemaAlbergueMascota {

    List<Mascota> mascotas = new ArrayList<>();


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

    
    
}