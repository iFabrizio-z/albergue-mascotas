import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Objects;

public class SistemaAlbergueMascota {

    List<Mascota> mascotas = new ArrayList<>();
    List<Adoptante> adoptantes = new ArrayList<>();
    List<SolicitudAdopcion> solicitudes = new ArrayList<>();
    List<Donacion> donaciones = new ArrayList<>();

    public SistemaAlbergueMascota() {
        this.mascotas = new ArrayList<>();
        this.adoptantes = new ArrayList<>();
        this.solicitudes = new ArrayList<>();
        this.donaciones = new ArrayList<>();
    }

    /**
     * Configura Gson con adaptadores polimórficos para Perro, Efectivo y Comida,
     * además de gestionar LocalDate correctamente[cite: 13].
     */
    private static Gson crearGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(
                        LocalDate.class,
                        (JsonSerializer<LocalDate>) (fecha, tipo, contexto) ->
                                fecha == null
                                        ? null
                                        : new com.google.gson.JsonPrimitive(
                                                fecha.toString())
                )
                .registerTypeAdapter(
                        LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, tipo, contexto) ->
                                json == null || json.isJsonNull()
                                        ? null
                                        : LocalDate.parse(json.getAsString())
                )
                .create();
    }
    
    /**
     * Guarda las mascotas, adoptantes, solicitudes y donaciones en JSON.
     */
    public void guardarDatos() {
        Path archivo = Path.of("albergue.json");
        Gson gson = crearGson();
    
        try (Writer escritor = Files.newBufferedWriter(archivo)) {
            gson.toJson(this, escritor);
            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }
    
    /**
     * Recupera el sistema desde albergue.json.
     */
    public static SistemaAlbergueMascota cargarDatos() {
        Path archivo = Path.of("albergue.json");
    
        if (!Files.exists(archivo)) {
            System.out.println("No existe albergue.json. Se inicia vacío.");
            return new SistemaAlbergueMascota();
        }
    
        try (Reader lector = Files.newBufferedReader(archivo)) {
            Gson gson = crearGson();
        
            SistemaAlbergueMascota sistema =
                    gson.fromJson(lector, SistemaAlbergueMascota.class);
        
            if (sistema == null) {
                return new SistemaAlbergueMascota();
            }
        
            sistema.reconectarSolicitudes();
            System.out.println("Datos cargados correctamente.");
            return sistema;
        
        } catch (IOException | JsonParseException e) {
            System.err.println("Error al cargar JSON: " + e.getMessage());
            return new SistemaAlbergueMascota();
        }
    }
    
    /**
     * Reconecta las solicitudes con las mascotas y los adoptantes
     * que pertenecen a las listas principales del sistema.
     */
    private void reconectarSolicitudes() {
        if (solicitudes == null || mascotas == null || adoptantes == null) {
            return;
        }
    
        for (SolicitudAdopcion solicitud : solicitudes) {
            if (solicitud == null) {
                continue;
            }
        
            if (solicitud.getMascota() != null) {
                int id = solicitud.getMascota().getIdMascota();
            
                for (Mascota mascota : mascotas) {
                    if (mascota != null
                            && mascota.getIdMascota() == id) {
                        solicitud.setMascota(mascota);
                        break;
                    }
                }
            }
        
            if (solicitud.getAdoptante() != null) {
                String dni = solicitud.getAdoptante().getDni();
            
                for (Adoptante adoptante : adoptantes) {
                    if (adoptante != null
                            && Objects.equals(adoptante.getDni(), dni)) {
                        solicitud.setAdoptante(adoptante);
                        break;
                    }
                }
            }
        }
    }
    

    /**
     * Registra una nueva mascota utilizando los datos ingresados por el usuario.
     */
    public void registrarMascota(Scanner scanner) {
        try {
            int nuevoId = mascotas.size() + 1;
            Perro nuevoPerro = new Perro();

            System.out.println("\n===== REGISTRO DE MASCOTA =====");
            nuevoPerro.setIdMascota(nuevoId);

            System.out.print("Ingrese el nombre: ");
            nuevoPerro.setNombre(scanner.nextLine());

            System.out.print("Ingrese la raza: ");
            nuevoPerro.setRaza(scanner.nextLine());

            System.out.print("Ingrese la edad: ");
            nuevoPerro.setEdad(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Ingrese el género (Macho/Hembra): ");
            nuevoPerro.setGenero(scanner.nextLine());

            System.out.print("Ingrese el peso en kg: ");
            nuevoPerro.setPeso(scanner.nextDouble());
            scanner.nextLine();

            System.out.print("Ingrese el tamaño: ");
            nuevoPerro.setTamaño(scanner.nextLine());

            nuevoPerro.setEstadoAdopcion("Refugiado");

            System.out.print("Ingrese el nivel de entrenamiento (Básico/Intermedio/Avanzado): ");
            nuevoPerro.setNivelEntrenamiento(scanner.nextLine());

            System.out.print("¿Convive con otros perros? (true/false): ");
            nuevoPerro.setConviveConOtrosPerros(scanner.nextBoolean());
            scanner.nextLine();

            mascotas.add(nuevoPerro);

            System.out.println("\n¡Perro registrado correctamente!");
            nuevoPerro.mostrarDatosMascota();

        } catch (InputMismatchException e) {
            System.out.println("Error: ingresó un dato con formato incorrecto.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }   

    /**
     * Busca una mascota registrada mediante su identificador.
     */
    public void buscarMascotaPorId(Scanner scanner) {
        System.out.println("\n===== BUSCAR MASCOTA POR ID =====");
        try {
            System.out.print("Ingrese el ID de la mascota: ");
            int idBuscado = scanner.nextInt();
            scanner.nextLine();

            for (Mascota mascota : mascotas) {
                if (mascota.getIdMascota() == idBuscado) {
                    System.out.println("\n¡Mascota encontrada!");
                    mascota.mostrarDatosMascota();
                    return;
                }
            }
            System.out.println("No se encontró una mascota con ese ID.");
        } catch (InputMismatchException e) {
            System.out.println("Error: debe ingresar un número entero.");
            scanner.nextLine();
        }
    }      

    /**
     * Busca un adoptante registrado mediante su DNI.
     */
    public Adoptante buscarAdoptante(String dni) {
        return adoptantes.stream()
                .filter(a -> a.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }

    /**
     * Registra un nuevo adoptante.
     */
    public Adoptante registrarAdoptante(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Ingrese el DNI: ");
                String dni = scanner.nextLine();

                Adoptante adoptanteExistente = buscarAdoptante(dni);
                if (adoptanteExistente != null) {
                    System.out.println("El adoptante ya se encuentra registrado.");
                    return adoptanteExistente;
                }

                System.out.print("Ingrese el nombre: ");
                String nombre = scanner.nextLine();

                System.out.print("Ingrese los apellidos: ");
                String apellidos = scanner.nextLine();

                System.out.print("Ingrese la dirección: ");
                String direccion = scanner.nextLine();

                System.out.print("Ingrese el email: ");
                String email = scanner.nextLine();

                System.out.print("Ingrese el teléfono móvil: ");
                String telefono = scanner.nextLine();

                Adoptante nuevoAdoptante = new Adoptante(nombre, apellidos, dni, direccion, email, telefono);
                adoptantes.add(nuevoAdoptante);

                System.out.println("Adoptante registrado correctamente.");
                return nuevoAdoptante;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Crea una nueva solicitud de adopción.
     */
    public void crearSolicitudAdopcion(Scanner scanner) {
        System.out.print("Ingrese el ID de la mascota que desean adoptar: ");
        int idBuscar = scanner.nextInt();
        scanner.nextLine();

        Mascota mascota = mascotas.stream()
                .filter(e -> e.getIdMascota() == idBuscar)
                .findFirst()
                .orElse(null);

        if (mascota == null) {
            System.out.println("ID de mascota incorrecto.");
            return;
        }

        if (mascota.getEstadoAdopcion().equalsIgnoreCase("Adoptado")) {
            System.out.println("La mascota ya fue adoptada.");
            return;
        }

        Adoptante adoptante = registrarAdoptante(scanner);
        int nuevoIndice = solicitudes.size() + 1;

        SolicitudAdopcion nuevaSolicitud = new SolicitudAdopcion(nuevoIndice, adoptante, mascota);
        solicitudes.add(nuevaSolicitud);

        System.out.println("\nEl registro de la solicitud fue exitoso...");
        System.out.println("ID de solicitud registrado: " + nuevoIndice);
    }   
    
    /**
     * Permite evaluar una solicitud de adopción mediante su identificador.
     */
    public void evaluarSolicitudPorId(Scanner scanner) {
        try {
            System.out.print("Ingresa el id de la solicitud para evaluarla: ");
            int idBuscar = scanner.nextInt();
            scanner.nextLine();

            SolicitudAdopcion solicitud = solicitudes.stream()
                    .filter(e -> e.getIdSolicitud() == idBuscar)
                    .findFirst()
                    .orElse(null);

            if (solicitud == null) {
                System.out.println("ID de solicitud incorrecto");
                return;
            }

            if (!solicitud.getEstado().equals("Pendiente")) {
                System.out.println("Esta solicitud ya fue procesada anteriormente. Estado: " + solicitud.getEstado());
                return;
            }

            System.out.println(solicitud.toString());
            System.out.print("\n¿Aprobar solicitud de adopción? (1: Aprobar, 2: Rechazar): ");

            int decision = scanner.nextInt();
            scanner.nextLine();

            if (decision == 1) {
                solicitud.setEstado("Aprobada");
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

        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número válido.");
            scanner.nextLine();
        }
    }

    /**
     * Agrega un registro al historial clínico de una mascota.
     */
    public void agregarHistorialPorId(Scanner scanner) {
        System.out.print("Ingrese el ID de mascota: ");
        int idBuscar = scanner.nextInt();
        scanner.nextLine();

        Mascota mascota = mascotas.stream()
                .filter(e -> e.getIdMascota() == idBuscar)
                .findFirst()
                .orElse(null);

        if (mascota == null) {
            System.out.println("ID de mascota incorrecto.");
            return;
        }

        String procedimiento;
        while (true) {
            System.out.print("Ingrese el procedimiento: ");
            procedimiento = scanner.nextLine();
            if (!procedimiento.isBlank()) break;
            System.out.println("Error: El procedimiento no puede estar vacío.");
        }

        String diagnostico;
        while (true) {
            System.out.print("Ingrese el diagnóstico: ");
            diagnostico = scanner.nextLine();
            if (!diagnostico.isBlank()) break;
            System.out.println("Error: El diagnóstico no puede estar vacío.");
        }

        String tratamiento;
        while (true) {
            System.out.print("Ingrese el tratamiento: ");
            tratamiento = scanner.nextLine();
            if (!tratamiento.isBlank()) break;
            System.out.println("Error: El tratamiento no puede estar vacío.");
        }

        String veterinario;
        while (true) {
            System.out.print("Ingrese el nombre del veterinario: ");
            veterinario = scanner.nextLine();
            if (!veterinario.isBlank()) break;
            System.out.println("Error: El nombre del veterinario no puede estar vacío.");
        }

        int nuevoIndice = mascota.getHistorialClinico().size() + 1;
        HistorialClinico historial = new HistorialClinico(nuevoIndice, procedimiento, diagnostico, tratamiento, veterinario);
        mascota.agregarHistorialMedico(historial);

        System.out.println("\n¡El registro del historial fue exitoso!");
    }   

    /**
     * Muestra el historial de una mascota a partir de su ID.
     */
    public void mostrarHistorialPorId(Scanner scanner) {
        System.out.print("Ingresa el ID de la mascota para buscar su historial: ");
        int idBuscar = scanner.nextInt();
        scanner.nextLine();

        Mascota mascota = mascotas.stream()
                .filter(e -> e.getIdMascota() == idBuscar)
                .findFirst()
                .orElse(null);

        if (mascota == null) {
            System.out.println("ID de mascota incorrecto.");
            return;
        }

        if (mascota.getHistorialClinico().isEmpty()) {
            System.out.println("La mascota no tiene historial clínico registrado.");
            return;
        }

        System.out.println("\n===== HISTORIAL CLÍNICO =====");
        mascota.getHistorialClinico().forEach(e -> System.out.println(e));
        System.out.println("=============================");
    }   

    /**
     * Agrega una donación a la lista.
     */
    public void agregarDonacion(Donacion donacion) {
        if (donacion != null) {
            donaciones.add(donacion);
        }
    }

    /**
     * Registra una nueva donación.
     */
    public void registrarDonacion(Scanner scanner) {
        System.out.println("\n------- REGISTRAR DONACIÓN -------");
        System.out.print("Ingrese el nombre del donante: ");
        String donante = scanner.nextLine();

        System.out.println("Seleccione el tipo de donación:");
        System.out.println("1. Efectivo");
        System.out.println("2. Comida para mascotas (Kg)");
        System.out.print("Opción: ");

        int tipo;
        try {
            tipo = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número válido.");
            scanner.nextLine();
            return;
        }

        int nuevoId = donaciones.size() + 1;

        if (tipo == 1) {
            System.out.print("Ingrese el monto en efectivo (S/): ");
            try {
                double monto = scanner.nextDouble();
                scanner.nextLine();

                if (monto > 0) {
                    Donacion donacion = new Efectivo(nuevoId, donante, monto);
                    agregarDonacion(donacion);
                    System.out.println("Donación en efectivo registrada exitosamente.");
                } else {
                    System.out.println("El monto ingresado debe ser mayor a 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un valor numérico.");
                scanner.nextLine();
            }
        } else if (tipo == 2) {
            System.out.print("Ingrese la cantidad de comida en kilogramos (Kg): ");
            try {
                Double cantidad = scanner.nextDouble();
                scanner.nextLine();

                if (cantidad > 0) {
                    Donacion donacion = new Comida(nuevoId, donante, cantidad);
                    agregarDonacion(donacion);
                    System.out.println("Donación de comida registrada exitosamente.");
                } else {
                    System.out.println("La cantidad ingresada debe ser mayor a 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un valor numérico.");
                scanner.nextLine();
            }
        } else {
            System.out.println("Tipo de donación no válido.");
        }
    }

    /**
     * Muestra todas las donaciones registradas y los totales.
     */
    public void listarDonaciones() {
        System.out.println("\n------- HISTORIAL DE DONACIONES -------");

        if (donaciones.isEmpty()) {
            System.out.println("No hay donaciones registradas en el sistema.");
            return;
        }

        System.out.println("\n--- LISTA DE DONACIONES ---");
        for (Donacion donacion : donaciones) {
            donacion.mostrarDetalle();
            System.out.println();
        }

        Double totalEfectivo = 0.0;
        Double totalComida = 0.0;

        for (Donacion donacion : donaciones) {
            if (donacion instanceof Comida) {
                Comida comida = (Comida) donacion;
                totalComida += comida.getCantidadComidaKg();
            } else if (donacion instanceof Efectivo) {
                Efectivo efectivo = (Efectivo) donacion;
                totalEfectivo += efectivo.getMontoEfectivo();
            }
        }

        System.out.println();
        System.out.printf("Total recaudado en Efectivo: S/ %.2f\n", totalEfectivo);
        System.out.printf("Total recaudado en Comida: %.2f Kg\n", totalComida);
    }

    /**
     * Permite iniciar sesión utilizando el contrato de la interfaz Autenticable[cite: 1, 13].
     */
    public void iniciarSesion(Scanner scanner) {
        Autenticable admin = new Usuario("admin", "1234", "Administrador");

        boolean autenticado = false;

        while (!autenticado) {
            System.out.println("=== INICIO DE SESIÓN ===");
            System.out.print("Ingrese usuario: ");
            String us = scanner.nextLine();

            System.out.print("Ingrese contraseña: ");
            String pass = scanner.nextLine();

            if (admin.autenticar(us, pass)) {
                System.out.println("\nBienvenido al sistema!");
                autenticado = true;
            } else {
                System.out.println("\nUsuario o contraseña incorrectos. Intente nuevamente.\n");
            }
        }
    }
}