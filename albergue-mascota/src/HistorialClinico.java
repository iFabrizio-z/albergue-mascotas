import java.time.LocalDate;

public class HistorialClinico {

    // Atributos privados
    private int idHistorial;
    private LocalDate fechaAtencion;
    private String tipoProcedimiento;
    private String diagnosticoMedico;
    private String tratamientoMedico;
    private String nombreVeterinario;

    /**
     * Constructor vacío de la clase HistorialClinico.
     */
    public HistorialClinico() {
    }

    /**
     * Crea un nuevo registro de historial clínico.
     *
     * @param idHistorial identificador del registro médico.
     * @param tipoProcedimiento procedimiento realizado a la mascota.
     * @param diagnosticoMedico diagnóstico realizado por el veterinario.
     * @param tratamientoMedico tratamiento indicado para la mascota.
     * @param nombreVeterinario nombre del veterinario que realizó la atención.
     */
    public HistorialClinico(int idHistorial, String tipoProcedimiento,
            String diagnosticoMedico, String tratamientoMedico,
            String nombreVeterinario) {

        this.idHistorial = idHistorial;
        this.fechaAtencion = LocalDate.now();
        this.tipoProcedimiento = tipoProcedimiento;
        this.diagnosticoMedico = diagnosticoMedico;
        this.tratamientoMedico = tratamientoMedico;
        this.nombreVeterinario = nombreVeterinario;
    }

    // Getters

    public int getIdHistorial() {
        return idHistorial;
    }

    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }

    public String getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public String getDiagnosticoMedico() {
        return diagnosticoMedico;
    }

    public String getTratamientoMedico() {
        return tratamientoMedico;
    }

    public String getNombreVeterinario() {
        return nombreVeterinario;
    }

    /**
     * Devuelve la información del registro de atención médica.
     *
     * @return datos del historial clínico en formato de texto.
     */
    @Override
    public String toString() {
        return String.format(
                "============ATENCIÓN MÉDICA [%03d]============",
                idHistorial) +
                "\nFecha registrada: " + fechaAtencion +
                "\nProcedimiento: " + tipoProcedimiento +
                "\nTratamiento: " + tratamientoMedico +
                "\nDiagnóstico: " + diagnosticoMedico +
                "\nVeterinario: " + nombreVeterinario;
    }
}
