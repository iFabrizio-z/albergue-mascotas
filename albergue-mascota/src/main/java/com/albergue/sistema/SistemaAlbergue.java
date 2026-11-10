/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.albergue.sistema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.albergue.modelo.*;
import com.albergue.persistencia.PersistenciaDatos;

public class SistemaAlbergue {

    private List<Mascota> mascotas = new ArrayList<>();
    private List<Adoptante> adoptantes = new ArrayList<>();
    private List<SolicitudAdopcion> solicitudes = new ArrayList<>();
    private List<Donacion> donaciones = new ArrayList<>();

    private transient boolean datosModificados = false;

    public SistemaAlbergue() {
        mascotas = new ArrayList<>();
        adoptantes = new ArrayList<>();
        solicitudes = new ArrayList<>();
        donaciones = new ArrayList<>();
    }

    // ==========================================
    // CONTROL DE CAMBIOS Y PERSISTENCIA
    // ==========================================
    public boolean isDatosModificados() {
        return datosModificados;
    }

    public void setDatosModificados(boolean datosModificados) {
        this.datosModificados = datosModificados;
    }

    private void guardarCambios() {
        PersistenciaDatos.guardarDatos(this);
    }

    // ==========================================
    // LISTAS PARA LA INTERFAZ GRÁFICA
    // ==========================================
    public List<Mascota> getMascotas() {
        return new ArrayList<>(mascotas);
    }

    public List<Adoptante> getAdoptantes() {
        return new ArrayList<>(adoptantes);
    }

    public List<SolicitudAdopcion> getSolicitudes() {
        return new ArrayList<>(solicitudes);
    }

    public List<Donacion> getDonaciones() {
        return new ArrayList<>(donaciones);
    }

    // ==========================================
    // RECONEXIÓN DE SOLICITUDES
    // ==========================================
    public void reconectarSolicitudes() {

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

                    if (mascota != null && Objects.equals(mascota.getIdMascota(), id)) {

                        solicitud.setMascota(mascota);
                        break;
                    }
                }
            }

            if (solicitud.getAdoptante() != null) {

                String dni = solicitud.getAdoptante().getDni();

                for (Adoptante adoptante : adoptantes) {

                    if (adoptante != null && Objects.equals(adoptante.getDni(), dni)) {
                        solicitud.setAdoptante(adoptante);
                        break;
                    }
                }
            }
        }
    }

    // ==========================================
    // MASCOTAS
    // ==========================================
    private int generarNuevoIdMascota() {

        int mayorId = 0;

        for (Mascota mascota : mascotas) {

            if (mascota != null && mascota.getIdMascota() != null) {

                String id = mascota.getIdMascota();

                if (id.matches("M\\d+")) {

                    int numero = Integer.parseInt(id.substring(1));
                    mayorId = Math.max(mayorId, numero);
                }
            }
        }

        return mayorId + 1;
    }

    /**
     * Registra una nueva mascota de tipo Perro en el sistema. Genera
     * automáticamente un identificador único y guarda los cambios en el archivo
     * de persistencia.
     *
     * @param nombre nombre de la mascota.
     * @param raza raza de la mascota.
     * @param edad edad de la mascota en años.
     * @param genero género de la mascota.
     * @param peso peso de la mascota en kilogramos.
     * @param tamano tamaño de la mascota.
     * @param nivelEntrenamiento nivel de entrenamiento del perro.
     * @param conviveConOtrosPerros indica si convive con otros perros.
     * @return el objeto Perro que fue registrado.
     * @throws IllegalArgumentException si alguno de los datos no es válido.
     */
    public Perro registrarMascota(String nombre, String raza, int edad, String genero,
            double peso, String tamano, String nivelEntrenamiento,
            boolean conviveConOtrosPerros) {

        Perro nuevoPerro = new Perro();

        String idGenerado = String.format("M%03d", generarNuevoIdMascota());

        nuevoPerro.setIdMascota(idGenerado);
        nuevoPerro.setNombre(nombre);
        nuevoPerro.setRaza(raza);
        nuevoPerro.setEdad(edad);
        nuevoPerro.setGenero(genero);
        nuevoPerro.setPeso(peso);
        nuevoPerro.setTamaño(tamano);
        nuevoPerro.setEstadoAdopcion("Refugiado");
        nuevoPerro.setNivelEntrenamiento(nivelEntrenamiento);
        nuevoPerro.setConviveConOtrosPerros(conviveConOtrosPerros);

        mascotas.add(nuevoPerro);

        datosModificados = true;
        guardarCambios();

        return nuevoPerro;
    }

    public Mascota buscarMascotaPorId(String idBuscado) {

        if (idBuscado == null || idBuscado.isBlank()) {
            return null;
        }

        String id = idBuscado.trim();

        return mascotas.stream()
                .filter(m -> m != null && Objects.equals(m.getIdMascota(), id))
                .findFirst()
                .orElse(null);
    }

    // ==========================================
    // ADOPTANTES
    // ==========================================
    /**
     * Registra los datos de un nuevo adoptante en el sistema. Comprueba que sus
     * datos sean válidos y que no exista otro adoptante con el mismo DNI.
     *
     * @param dni número de documento de identidad del adoptante
     * @param nombre nombres del adoptante
     * @param apellidos apellidos del adoptante
     * @param direccion domicilio del adoptante
     * @param email correo electrónico del adoptante
     * @param telefono número telefónico del adoptante
     * @return el adoptante registrado o el que ya existe con ese DNI
     */
    public Adoptante registrarAdoptante(String dni, String nombre, String apellidos,
            String direccion, String email, String telefono) {

        // Validar DNI.
        Adoptante validacion = new Adoptante();
        validacion.setDni(dni);

        Adoptante existente = buscarAdoptante(dni);

        if (existente != null) {
            return existente;
        }

        // Validar los demás datos mediante los setters.
        validacion.setNombre(nombre);
        validacion.setApellidos(apellidos);
        validacion.setDireccion(direccion);
        validacion.setEmail(email);
        validacion.setTelefono(telefono);

        Adoptante nuevoAdoptante = new Adoptante(nombre, apellidos, dni, direccion, email, telefono);

        adoptantes.add(nuevoAdoptante);

        datosModificados = true;
        guardarCambios();

        return nuevoAdoptante;
    }

    /**
     * Busca un adoptante mediante su número de documento de identidad.
     *
     * @param dni DNI del adoptante que se desea buscar
     * @return el adoptante encontrado o null si no existe
     */
    public Adoptante buscarAdoptante(String dni) {

        if (dni == null) {
            return null;
        }

        return adoptantes.stream()
                .filter(a -> a != null && Objects.equals(a.getDni(), dni.trim()))
                .findFirst()
                .orElse(null);
    }

    // ==========================================
    // SOLICITUDES DE ADOPCIÓN
    // ==========================================
    private int generarNuevoIdSolicitud() {

        int mayorId = 0;

        for (SolicitudAdopcion solicitud : solicitudes) {

            if (solicitud != null) {
                mayorId = Math.max(mayorId, solicitud.getIdSolicitud());
            }
        }

        return mayorId + 1;
    }

    /**
     * Crea una nueva solicitud de adopción asociando una mascota disponible con
     * un adoptante previamente registrado.
     *
     * @param idMascota identificador de la mascota solicitada.
     * @param adoptante adoptante que realiza la solicitud.
     * @return la solicitud de adopción creada.
     * @throws IllegalArgumentException si la mascota no existe, si ya fue
     * adoptada o si el adoptante no está registrado.
     */
    public SolicitudAdopcion crearSolicitudAdopcion(String idMascota, Adoptante adoptante) {

        Mascota mascota = buscarMascotaPorId(idMascota);

        if (mascota == null) {
            throw new IllegalArgumentException("No se encontró la mascota indicada");
        }

        if ("Adoptado".equalsIgnoreCase(mascota.getEstadoAdopcion())) {
            throw new IllegalArgumentException("La mascota ya fue adoptada");
        }

        if (adoptante == null) {
            throw new IllegalArgumentException("Debe seleccionar o registrar un adoptante");
        }

        // Utilizar el adoptante que ya está guardado en la lista.
        Adoptante adoptanteRegistrado = buscarAdoptante(adoptante.getDni());

        if (adoptanteRegistrado == null) {
            throw new IllegalArgumentException("El adoptante no está registrado");
        }

        int nuevoId = generarNuevoIdSolicitud();

        SolicitudAdopcion nuevaSolicitud = new SolicitudAdopcion(nuevoId, adoptanteRegistrado,
                mascota);

        solicitudes.add(nuevaSolicitud);

        datosModificados = true;
        guardarCambios();

        return nuevaSolicitud;
    }

    /**
     * Evalúa una solicitud de adopción y actualiza su estado. Si se aprueba, la
     * mascota pasa al estado "Adoptado".
     *
     * @param idBuscar identificador de la solicitud.
     * @param aprobar true para aprobar y false para rechazar.
     * @throws IllegalArgumentException si la solicitud no existe o ya fue
     * procesada.
     */
    public void evaluarSolicitudPorId(int idBuscar, boolean aprobar) {

        SolicitudAdopcion solicitud = solicitudes.stream()
                .filter(s -> s != null && s.getIdSolicitud() == idBuscar)
                .findFirst()
                .orElse(null);

        if (solicitud == null) {
            throw new IllegalArgumentException("No se encontró la solicitud indicada.");
        }

        if (!"Pendiente".equals(solicitud.getEstado())) {
            throw new IllegalArgumentException("Esta solicitud ya fue procesada. Estado: "
                    + solicitud.getEstado());
        }

        if (aprobar) {

            solicitud.setEstado("Aprobada");

            if (solicitud.getMascota() != null) {
                solicitud.getMascota().setEstadoAdopcion("Adoptado");
            }

        } else {

            solicitud.setEstado("Rechazada");
        }

        datosModificados = true;
        guardarCambios();
    }

    // ==========================================
    // HISTORIAL CLÍNICO
    // ==========================================
    /**
     * Agrega un nuevo registro al historial clínico de una mascota.
     *
     * @param idMascota identificador de la mascota
     * @param procedimiento procedimiento médico realizado
     * @param diagnostico diagnóstico del veterinario
     * @param tratamiento tratamiento indicado para la mascota
     * @param veterinario nombre del veterinario responsable
     * @return el registro clínico agregado
     */
    public HistorialClinico agregarHistorialPorId(String idMascota, String procedimiento,
            String diagnostico, String tratamiento,
            String veterinario) {

        Mascota mascota = buscarMascotaPorId(idMascota);

        if (mascota == null) {
            throw new IllegalArgumentException("No se encontró la mascota indicada.");
        }

        if (procedimiento == null || procedimiento.isBlank()
                || diagnostico == null || diagnostico.isBlank()
                || tratamiento == null || tratamiento.isBlank()
                || veterinario == null || veterinario.isBlank()) {

            throw new IllegalArgumentException("Todos los campos del historial son obligatorios");
        }

        int nuevoIndice = mascota.getHistorialClinico().size() + 1;

        HistorialClinico historial = new HistorialClinico(nuevoIndice, procedimiento, diagnostico,
                tratamiento, veterinario);

        mascota.agregarHistorialMedico(historial);

        datosModificados = true;
        guardarCambios();

        return historial;
    }

    /**
     * Obtiene los registros del historial clínico de una mascota.
     *
     * @param idMascota identificador de la mascota
     * @return lista con los registros del historial clínico
     */
    public List<HistorialClinico> obtenerHistorialPorId(String idMascota) {

        Mascota mascota = buscarMascotaPorId(idMascota);

        if (mascota == null) {
            throw new IllegalArgumentException("No se encontró la mascota indicada.");
        }

        return new ArrayList<>(mascota.getHistorialClinico());
    }

    public void editarHistorialPorId(
            String idMascota,
            int idHistorial,
            String procedimiento,
            String diagnostico,
            String tratamiento,
            String veterinario) {

        Mascota mascota = buscarMascotaPorId(idMascota);

        if (mascota == null) {
            throw new IllegalArgumentException("No se encontró la mascota.");
        }

        if (procedimiento == null || procedimiento.isBlank()
                || diagnostico == null || diagnostico.isBlank()
                || tratamiento == null || tratamiento.isBlank()
                || veterinario == null || veterinario.isBlank()) {

            throw new IllegalArgumentException(
                    "Todos los campos son obligatorios.");
        }

        HistorialClinico historialEncontrado = null;

        for (HistorialClinico historial : mascota.getHistorialClinico()) {
            if (historial.getIdHistorial() == idHistorial) {
                historialEncontrado = historial;
                break;
            }
        }

        if (historialEncontrado == null) {
            throw new IllegalArgumentException(
                    "No se encontró el historial seleccionado.");
        }

        historialEncontrado.setTipoProcedimiento(procedimiento);
        historialEncontrado.setDiagnosticoMedico(diagnostico);
        historialEncontrado.setTratamientoMedico(tratamiento);
        historialEncontrado.setNombreVeterinario(veterinario);

        datosModificados = true;
        guardarCambios();
    }

    /**
     * Elimina un historial clínico de una mascota.
     */
    public void eliminarHistorialPorId(String idMascota, int idHistorial) {

        Mascota mascota = buscarMascotaPorId(idMascota);

        if (mascota == null) {
            throw new IllegalArgumentException(
                    "No se encontró la mascota indicada.");
        }

        HistorialClinico historialEncontrado = null;

        for (HistorialClinico historial : mascota.getHistorialClinico()) {

            if (historial.getIdHistorial() == idHistorial) {
                historialEncontrado = historial;
                break;
            }
        }

        if (historialEncontrado == null) {
            throw new IllegalArgumentException(
                    "No se encontró el historial seleccionado.");
        }

        mascota.getHistorialClinico().remove(historialEncontrado);

        datosModificados = true;
        guardarCambios();
    }

    // ==========================================
    // DONACIONES
    // ==========================================
    public void agregarDonacion(Donacion donacion) {

        if (donacion == null) {
            throw new IllegalArgumentException("La donación no puede ser nula.");
        }

        donaciones.add(donacion);

        datosModificados = true;
        guardarCambios();
    }

    /**
     * Registra una donación monetaria realizada por un donante.
     *
     * @param donante nombre de la persona que realiza la donación
     * @param monto cantidad de dinero donada
     * @return la donación en efectivo registrada
     */
    public Efectivo registrarDonacionEfectivo(String donante, double monto) {

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }

        int nuevoId = donaciones.size() + 1;

        Efectivo donacion = new Efectivo(nuevoId, donante, monto);

        agregarDonacion(donacion);

        return donacion;
    }

    /**
     * Registra una donación de comida para las mascotas del albergue.
     *
     * @param donante nombre de la persona que realiza la donación
     * @param cantidadKg cantidad de comida donada en kilogramos
     * @return la donación de comida registrada
     */
    public Comida registrarDonacionComida(String donante, double cantidadKg) {

        if (cantidadKg <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        int nuevoId = donaciones.size() + 1;

        Comida donacion = new Comida(nuevoId, donante, cantidadKg);

        agregarDonacion(donacion);

        return donacion;
    }

    /**
     * Calcula la suma de todas las donaciones monetarias recibidas.
     *
     * @return monto total de las donaciones en efectivo
     */
    public double obtenerTotalEfectivo() {

        double total = 0.0;

        for (Donacion donacion : donaciones) {

            if (donacion instanceof Efectivo efectivo) {
                total += efectivo.getMontoEfectivo();
            }
        }

        return total;
    }

    /**
     * Calcula la cantidad total de comida recibida mediante donaciones.
     *
     * @return cantidad total de comida en kilogramos
     */
    public double obtenerTotalComida() {

        double total = 0.0;

        for (Donacion donacion : donaciones) {

            if (donacion instanceof Comida comida) {
                total += comida.getCantidadComidaKg();
            }
        }

        return total;
    }

    // ==========================================
    // INICIO DE SESIÓN
    // ==========================================
    /**
     * Verifica las credenciales de acceso del usuario administrador.
     *
     * @param usuario nombre de usuario ingresado
     * @param contrasena contraseña ingresada
     * @return true si las credenciales son correctas; false en caso contrario
     */
    public boolean iniciarSesion(String usuario, String contrasena) {

        Autenticable admin = new Usuario("admin", "1234", "Administrador");

        return admin.autenticar(usuario, contrasena);
    }
}
