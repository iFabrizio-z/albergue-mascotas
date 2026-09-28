import java.time.LocalDate;

public class SolicitudAdopcion {

    // Atributos
    private int idSolicitud;
    private LocalDate fechaSolicitud;
    private String estado;

    private Adoptante adoptante;
    private Mascota mascota;

    // Método constructor
    public SolicitudAdopcion(int idSolicitud, Adoptante adoptante,
                             Mascota mascota) {

        this.idSolicitud = idSolicitud;
        this.fechaSolicitud = LocalDate.now();
        this.estado = "Pendiente";

        setAdoptante(adoptante);
        setMascota(mascota);
    }

    // Setters

    /**
     * Establece el estado de la solicitud.
     *
     * @param estado estado de la solicitud, no puede estar vacío.
     * @throws IllegalArgumentException si el estado está vacío.
     */
    public void setEstado(String estado) {
        if (estado.isBlank()) {
            throw new IllegalArgumentException(
                    "El estado no puede estar vacío");
        }

        this.estado = estado;
    }

    /**
     * Establece el adoptante de la solicitud.
     *
     * @param adoptante adoptante de la solicitud, no puede ser nulo.
     * @throws IllegalArgumentException si el adoptante es nulo.
     */
    public void setAdoptante(Adoptante adoptante) {
        if (adoptante == null) {
            throw new IllegalArgumentException(
                    "El adoptante no puede ser nulo");
        }

        this.adoptante = adoptante;
    }

    /**
     * Establece la mascota de la solicitud.
     *
     * @param mascota mascota de la solicitud, no puede ser nula.
     * @throws IllegalArgumentException si la mascota es nula.
     */
    public void setMascota(Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException(
                    "La mascota no puede ser nula");
        }

        this.mascota = mascota;
    }

    // Getters

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public Adoptante getAdoptante() {
        return adoptante;
    }

    public Mascota getMascota() {
        return mascota;
    }

    /**
     * Devuelve la información de la solicitud.
     *
     * @return información de la solicitud en formato de texto.
     */
    @Override
    public String toString() {
        return String.format(
                "============ SOLICITUD DE ADOPCIÓN [%d] ============",
                idSolicitud) +
                "\nFecha registrada: " + fechaSolicitud +
                "\nEstado: " + estado +
                "\nAdoptante: " + adoptante.getNombre() + " "
                + adoptante.getApellidos() +
                "\nDNI: " + adoptante.getDni() +
                "\nTeléfono/Móvil: " + adoptante.getTelefono() +
                "\nMascota solicitada: " + mascota.getNombre();
    }
}