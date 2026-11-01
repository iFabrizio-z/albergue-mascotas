import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
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

                // Adaptador para LocalDate
                .registerTypeAdapter(
                        LocalDate.class,
                        (JsonSerializer<LocalDate>) (fecha, tipo, contexto) ->
                                fecha == null
                                        ? null
                                        : new JsonPrimitive(fecha.toString())
                )
                .registerTypeAdapter(
                        LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, tipo, contexto) ->
                                json == null || json.isJsonNull()
                                        ? null
                                        : LocalDate.parse(json.getAsString())
                )

                // Adaptador para Mascota
                .registerTypeAdapter(
                        Mascota.class,
                        (JsonSerializer<Mascota>) (mascota, tipo, contexto) -> {
                            JsonObject objeto = contexto.serialize(
                                    mascota, mascota.getClass()).getAsJsonObject();

                            objeto.addProperty("tipo", "Perro");
                            return objeto;
                        }
                )
                .registerTypeAdapter(
                        Mascota.class,
                        (JsonDeserializer<Mascota>) (json, tipo, contexto) -> {
                            JsonObject objeto = json.getAsJsonObject();
                            objeto.remove("tipo");

                            return contexto.deserialize(objeto, Perro.class);
                        }
                )

                // Adaptador para Donacion
                .registerTypeAdapter(
                        Donacion.class,
                        (JsonSerializer<Donacion>) (donacion, tipo, contexto) -> {
                            JsonObject objeto = contexto.serialize(
                                    donacion, donacion.getClass()).getAsJsonObject();

                            if (donacion instanceof Efectivo) {
                                objeto.addProperty("tipo", "Efectivo");
                            } else if (donacion instanceof Comida) {
                                objeto.addProperty("tipo", "Comida");
                            }

                            return objeto;
                        }
                )
                .registerTypeAdapter(
                        Donacion.class,
                        (JsonDeserializer<Donacion>) (json, tipo, contexto) -> {
                            JsonObject objeto = json.getAsJsonObject();
                            String tipoDonacion = objeto.get("tipo").getAsString();
                            objeto.remove("tipo");

                            if ("Efectivo".equals(tipoDonacion)) {
                                return contexto.deserialize(objeto, Efectivo.class);
                            } else if ("Comida".equals(tipoDonacion)) {
                                return contexto.deserialize(objeto, Comida.class);
                            }

                            throw new JsonParseException(
                                    "Tipo de donación desconocido: " + tipoDonacion);
                        }
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
                String id = solicitud.getMascota().getIdMascota();

                for (Mascota mascota : mascotas) {
                    if (mascota != null
                            && Objects.equals(mascota.getIdMascota(), id)) {
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

        Perro nuevoPerro = new Perro();

        System.out.println("\n===== REGISTRO DE MASCOTA =====");

        // Generar nuevo ID
        int nuevoId = 1;

        for (Mascota mascota : mascotas) {
            if (mascota != null) {
                int idActual = Integer.parseInt(mascota.getIdMascota());

                if (idActual >= nuevoId) {
                    nuevoId = idActual + 1;
                }
            }
        }

        String idGenerado = String.format("%03d", nuevoId);

        nuevoPerro.setIdMascota(idGenerado);

        // Validar nombre
        while (true) {
            try {
                System.out.print("Ingrese el nombre: ");
                nuevoPerro.setNombre(scanner.nextLine());
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Validar raza
        while (true) {
            try {
                System.out.print("Ingrese la raza: ");
                nuevoPerro.setRaza(scanner.nextLine());
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Validar edad
        while (true) {
            try {
                System.out.print("Ingrese la edad: ");
                int edad = Integer.parseInt(scanner.nextLine());

                nuevoPerro.setEdad(edad);
                break;

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número entero.");

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Validar género
        while (true) {
            try {
                System.out.print("Ingrese el género (Macho/Hembra): ");
                nuevoPerro.setGenero(scanner.nextLine());
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Validar peso
        while (true) {
            try {
                System.out.print("Ingrese el peso en kg: ");
                double peso = Double.parseDouble(scanner.nextLine());

                nuevoPerro.setPeso(peso);
                break;

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un valor numérico.");

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Validar tamaño
        while (true) {
            try {
                System.out.print("Ingrese el tamaño: ");
                nuevoPerro.setTamaño(scanner.nextLine());
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        nuevoPerro.setEstadoAdopcion("Refugiado");

        // Validar nivel de entrenamiento
        while (true) {
            try {
                System.out.print("Ingrese el nivel de entrenamiento " + "(Básico/Intermedio/Avanzado): ");

                nuevoPerro.setNivelEntrenamiento(scanner.nextLine());
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Validar convivencia con otros perros
        while (true) {

            System.out.print("¿Convive con otros perros? (si/no): ");

            String respuesta = scanner.nextLine();

            if (respuesta.equalsIgnoreCase("si")) {

                nuevoPerro.setConviveConOtrosPerros(true);
                break;

            } else if (respuesta.equalsIgnoreCase("no")) {

                nuevoPerro.setConviveConOtrosPerros(false);
                break;

            } else {

                System.out.println("Error: debe ingresar si o no.");
            }
        }

        mascotas.add(nuevoPerro);

        System.out.println("\n¡Perro registrado correctamente!");
        nuevoPerro.mostrarDatosMascota();
    }

    /**
     * Busca una mascota registrada mediante su identificador.
     */
    public void buscarMascotaPorId(Scanner scanner) {

        System.out.println("\n===== BUSCAR MASCOTA POR ID =====");

        try {

            System.out.print("Ingrese el ID de la mascota: ");

            String idBuscado = scanner.nextLine().trim();

            if (idBuscado.isEmpty()) {
                System.out.println("Error: el ID no puede estar vacío.");
                return;
            }

            for (Mascota mascota : mascotas) {

                if (mascota != null &&
                    Objects.equals(mascota.getIdMascota(), idBuscado)) {

                    System.out.println("\n¡Mascota encontrada!");

                    mascota.mostrarDatosMascota();

                    return;
                }
            }

            System.out.println("No se encontró una mascota con ese ID.");

        } catch (NumberFormatException e) {

            System.out.println("Error: debe ingresar un ID válido.");
        }
    }      

    /**
     * Registra un nuevo adoptante
     */
    public Adoptante registrarAdoptante(Scanner scanner) {

        String dni;

        // Validar DNI
        while (true) {
            try {
                System.out.print("Ingrese el DNI: ");
                dni = scanner.nextLine();

                Adoptante validacion = new Adoptante();
                validacion.setDni(dni);

                Adoptante adoptanteExistente = buscarAdoptante(dni);

                if (adoptanteExistente != null) {
                    System.out.println("El adoptante ya se encuentra registrado.");
                    return adoptanteExistente;
                }

                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        String nombre;

        // Validar nombre
        while (true) {
            try {
                System.out.print("Ingrese el nombre: ");
                nombre = scanner.nextLine();

                Adoptante validacion = new Adoptante();
                validacion.setNombre(nombre);

                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        String apellidos;

        // Validar apellidos
        while (true) {
            try {
                System.out.print("Ingrese los apellidos: ");
                apellidos = scanner.nextLine();

                Adoptante validacion = new Adoptante();
                validacion.setApellidos(apellidos);

                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        String direccion;

        // Validar dirección
        while (true) {
            try {
                System.out.print("Ingrese la dirección: ");
                direccion = scanner.nextLine();

                Adoptante validacion = new Adoptante();
                validacion.setDireccion(direccion);

                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        String email;

        // Validar email
        while (true) {
            try {
                System.out.print("Ingrese el email: ");
                email = scanner.nextLine();

                Adoptante validacion = new Adoptante();
                validacion.setEmail(email);

                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        String telefono;

        // Validar teléfono
        while (true) {
            try {
                System.out.print("Ingrese el teléfono móvil: ");
                telefono = scanner.nextLine();

                Adoptante validacion = new Adoptante();
                validacion.setTelefono(telefono);

                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        Adoptante nuevoAdoptante = new Adoptante(nombre, apellidos, dni, direccion, email, telefono);

        adoptantes.add(nuevoAdoptante);

        System.out.println("Adoptante registrado correctamente.");

        return nuevoAdoptante;
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
     * Crea una nueva solicitud de adopción.
     */
    public void crearSolicitudAdopcion(Scanner scanner) {
        System.out.print("Ingrese el ID de la mascota que desean adoptar: ");
        String idBuscar = scanner.nextLine().trim();
        
        Mascota mascota = mascotas.stream()
                .filter(e -> Objects.equals(e.getIdMascota(), idBuscar))
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
        String idBuscar = scanner.nextLine().trim();

        Mascota mascota = mascotas.stream()
                .filter(e -> Objects.equals(e.getIdMascota(), idBuscar))
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

        HistorialClinico historial = new HistorialClinico(nuevoIndice,procedimiento,diagnostico,tratamiento,veterinario);

        mascota.agregarHistorialMedico(historial);

        System.out.println("\n¡El registro del historial fue exitoso!");
    }   

    /**
     * Muestra el historial de una mascota a partir de su ID.
     */
    public void mostrarHistorialPorId(Scanner scanner) {
        System.out.print("Ingresa el ID de la mascota para buscar su historial: ");
        String idBuscar = scanner.nextLine().trim();

        Mascota mascota = mascotas.stream()
                .filter(e -> Objects.equals(e.getIdMascota(), idBuscar))
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
