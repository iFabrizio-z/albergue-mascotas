import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class SistemaAlbergueMascota {

    List<Mascota> mascotas = new ArrayList<>();
    List<Adoptante> adoptantes = new ArrayList<>();
    List<SolicitudAdopcion> solicitudes = new ArrayList<>();
    List<Donacion> donaciones = new ArrayList<>();

    /**
     * Registra una nueva mascota utilizando los datos ingresados por el usuario.
     *
     * @param scanner objeto Scanner utilizado para leer los datos ingresados por consola.
     */
    public void registrarMascota(Scanner scanner) {

        Mascota nuevaMascota = new Mascota();

        while (true) {
            try {
                System.out.print("Ingrese el id de la mascota: ");
                nuevaMascota.setIdMascota(scanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Ingrese el nombre: ");
                nuevaMascota.setNombre(scanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Ingrese la raza: ");
                nuevaMascota.setRaza(scanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Ingrese la edad: ");
                int edad = scanner.nextInt();
                scanner.nextLine();

                if (nuevaMascota.setEdad(edad)) {
                    break;
                }

                System.out.println("Error: La edad debe estar entre 0 y 12 años.");

            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero.");
                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Ingrese el género (Macho/Hembra): ");
                String genero = scanner.nextLine();

                if (nuevaMascota.setGenero(genero)) {
                    break;
                }

                System.out.println("Error: El género debe ser Macho o Hembra.");

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Ingrese el peso: ");
                double peso = scanner.nextDouble();
                scanner.nextLine();

                nuevaMascota.setPeso(peso);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número.");
                scanner.nextLine();

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Ingrese el tamaño: ");
                double tamaño = scanner.nextDouble();
                scanner.nextLine();

                nuevaMascota.setTamaño(tamaño);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número.");
                scanner.nextLine();

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        nuevaMascota.setEstadoAdopcion("refugiado");

        mascotas.add(nuevaMascota);

        System.out.println("Mascota registrada correctamente.");
    }

    /**
     * Busca una mascota registrada mediante su identificador y muestra
     * la información correspondiente por consola.
     *
     * @param scanner objeto Scanner utilizado para leer el identificador de la mascota.
     */
    public void buscarMascotaPorId(Scanner scanner) {

        System.out.println("=========BUSCAR MASCOTA POR ID=========");

        System.out.print("Ingrese el id a buscar: ");
        String idBuscado = scanner.nextLine();

        Mascota mascota = mascotas.stream()
                .filter(m -> m.getIdMascota().equals(idBuscado))
                .findFirst()
                .orElse(null);

        if (mascota != null) {
            System.out.println(mascota.toString());
        } else {
            System.out.println("No encontrado");
        }
    }

    /**
     * Busca un adoptante registrado mediante su DNI.
     *
     * @param dni DNI del adoptante que se desea buscar.
     * @return el adoptante encontrado o null si no existe un adoptante con ese DNI.
     */
    public Adoptante buscarAdoptante(String dni) {

        return adoptantes.stream()
                .filter(a -> a.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }

    /**
     * Registra un nuevo adoptante a partir de los datos ingresados por el usuario.
     * Si el adoptante ya se encuentra registrado, se utiliza el registro existente.
     *
     * @param scanner objeto Scanner utilizado para leer los datos ingresados por consola.
     * @return el adoptante registrado o el adoptante que ya se encontraba registrado.
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

                Adoptante nuevoAdoptante = new Adoptante(nombre,apellidos,dni,direccion,email,telefono);

                adoptantes.add(nuevoAdoptante);

                System.out.println(
                        "Adoptante registrado correctamente.");

                return nuevoAdoptante;

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Crea una nueva solicitud de adopción utilizando los datos ingresados
     * por el usuario y la mascota seleccionada.
     *
     * @param scanner objeto Scanner utilizado para leer los datos de la solicitud.
     */
    public void crearSolicitudAdopcion(Scanner scanner) {

        System.out.print("Ingrese el id de la mascota que desean adoptar: ");
        String idBuscar = scanner.nextLine();

        Mascota mascota = mascotas.stream()
                .filter(e -> e.getIdMascota().equals(idBuscar))
                .findFirst()
                .orElse(null);

        if (mascota == null) {
            System.out.println("ID de mascota incorrecto");
            return;
        }

        if (mascota.getEstadoAdopcion().equalsIgnoreCase("Adoptado")) {
            System.out.println("La mascota ya fue adoptada.");
            return;
        }

        Adoptante adoptante = registrarAdoptante(scanner);

        int nuevoIndice = solicitudes.size() + 1;

        SolicitudAdopcion nuevaSolicitud = new SolicitudAdopcion(nuevoIndice,adoptante,mascota);

        solicitudes.add(nuevaSolicitud);

        System.out.println("El registro de la solicitud fue exitoso...");
        System.out.println("ID de solicitud registrado: " + nuevoIndice);
    }

    /**
     * Permite evaluar una solicitud de adopción mediante su identificador.
     * La solicitud puede ser aprobada o rechazada según la opción seleccionada.
     *
     * @param scanner objeto Scanner utilizado para leer el identificador y la evaluación.
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

            System.out.print("\n¿Aprobar solicitud de adopción? " + "(1: Aprobar, 2: Rechazar): ");

            int decision = scanner.nextInt();
            scanner.nextLine();

            if (decision == 1) {

                solicitud.setEstado("Aprobada");

                if (solicitud.getMascota() != null) {
                    solicitud.getMascota()
                            .setEstadoAdopcion("Adoptado");
                }

                System.out.println("Solicitud aprobada con éxito. Mascota adoptada.");

            } else if (decision == 2) {

                solicitud.setEstado("Rechazada");

                System.out.println( "Solicitud rechazada con éxito.");

            } else {
                System.out.println("Opción no válida.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar un número válido.");
            scanner.nextLine();
        }
    }

    /**
     * Agrega un registro al historial clínico de una mascota utilizando su identificador.
     *
     * @param scanner objeto Scanner utilizado para leer el identificador y los datos del historial clínico.
     */
    public void agregarHistorialPorId(Scanner scanner) {

        System.out.print("Ingrese el id de mascota: ");
        String idBuscar = scanner.nextLine();

        Mascota mascota = mascotas.stream()
                .filter(e -> e.getIdMascota().equals(idBuscar))
                .findFirst()
                .orElse(null);

        if (mascota == null) {
            System.out.println("ID de mascota incorrecto");
            return;
        }

        String procedimiento;

        while (true) {
            System.out.print("Ingrese el procedimiento: ");
            procedimiento = scanner.nextLine();

            if (!procedimiento.isBlank()) {
                break;
            }

            System.out.println("Error: El procedimiento no puede estar vacío.");
        }

        String diagnostico;

        while (true) {
            System.out.print("Ingrese el diagnóstico: ");
            diagnostico = scanner.nextLine();

            if (!diagnostico.isBlank()) {
                break;
            }

            System.out.println("Error: El diagnóstico no puede estar vacío.");
        }

        String tratamiento;

        while (true) {
            System.out.print("Ingrese el tratamiento: ");
            tratamiento = scanner.nextLine();

            if (!tratamiento.isBlank()) {
                break;
            }

            System.out.println("Error: El tratamiento no puede estar vacío.");
        }

        String veterinario;

        while (true) {
            System.out.print("Ingrese el nombre del veterinario: ");
            veterinario = scanner.nextLine();

            if (!veterinario.isBlank()) {
                break;
            }

            System.out.println("Error: El nombre del veterinario no puede estar vacío.");
        }

        int nuevoIndice =
                mascota.getHistorialClinico().size() + 1;

        HistorialClinico historial = new HistorialClinico(nuevoIndice,procedimiento,diagnostico,tratamiento,veterinario);

        mascota.agregarHistorialMedico(historial);

        System.out.println("El registro del historial fue exitoso...");
    }

    /**
     * Muestra el historial de una mascota a partir de su identificador.
     *
     * @param scanner objeto Scanner utilizado para leer el identificador de la mascota.
     */
    public void mostrarHistorialPorId(Scanner scanner) {

        System.out.print("Ingresa el id de la mascota para buscar historial: ");

        String idBuscar = scanner.nextLine();

        Mascota mascota = mascotas.stream()
                .filter(e -> e.getIdMascota().equals(idBuscar))
                .findFirst()
                .orElse(null);

        if (mascota == null) {
            System.out.println("ID de mascota incorrecto");
            return;
        }

        if (mascota.getHistorialClinico().isEmpty()) {
            System.out.println( "La mascota no tiene historial clínico registrado.");
            return;
        }

        mascota.getHistorialClinico()
                .forEach(e -> System.out.println(e));
    }

    /**
     * Registra una nueva donación utilizando los datos ingresados por el usuario.
     * La donación puede corresponder a dinero o alimentos.
     *
     * @param scanner objeto Scanner utilizado para leer los datos de la donación.
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

                    Donacion donacion =
                            new Donacion(nuevoId, donante, monto);

                    donaciones.add(donacion);

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
                double cantidad = scanner.nextDouble();
                scanner.nextLine();

                if (cantidad > 0) {

                    Donacion donacion = new Donacion(nuevoId,donante,cantidad,true);

                    donaciones.add(donacion);

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
     * Muestra todas las donaciones registradas y calcula los totales
     * de dinero y comida recibidos.
     */
    public void listarDonaciones() {

        System.out.println(
                "\n------- HISTORIAL DE DONACIONES -------");

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

        System.out.printf("Total recaudado en Efectivo: S/ %.2f\n",totalEfectivo);

        System.out.printf("Total recaudado en Comida: %.2f Kg\n",totalComida);
    }

    /**
     * Permite iniciar sesión en el sistema utilizando las credenciales
     * del usuario administrador.
     *
     * @param scanner objeto Scanner utilizado para leer las credenciales.
     */
    public void iniciarSesion(Scanner scanner) {

        Usuario admin = new Usuario("admin", "1234", "Administrador");

        boolean autenticado = false;

        while (!autenticado) {

            System.out.println("=== INICIO DE SESIÓN ===");

            System.out.print("Ingrese usuario: ");
            String us = scanner.nextLine();

            System.out.print("Ingrese contraseña: ");
            String pass = scanner.nextLine();

            if (admin.autenticar(us, pass)) {

                System.out.println("\nBienvenido, " + admin.getNombre() + "!");

                autenticado = true;

            } else {

                System.out.println("\nUsuario o contraseña incorrectos. " + "Intente nuevamente.\n");
            }
        }
    }
}