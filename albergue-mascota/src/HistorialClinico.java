import java.time.LocalDate;

public class HistorialClinico {

    private int idHistorial; // Código del registro
    private LocalDate fechaAtencion; // Se coloca fecha actual de registro
    private String tipoProcedimiento; // Vacunación, Desparasitación, Cirugía
    private String diagnosticoMedico; // Mensaje breve del estado clínico después del procedimiento
    private String tratamientoMedico; // Receta médica
    private String nombreVeterinario; // Nombre del veterinario

    public HistorialClinico(int idHistorial, String tipoProcedimiento,
            String diagnosticoMedico, String tratamientoMedico, String nombreVeterinario) {
        this.idHistorial = idHistorial;
        this.fechaAtencion = LocalDate.now();
        this.tipoProcedimiento = tipoProcedimiento;
        this.diagnosticoMedico = diagnosticoMedico;
        this.tratamientoMedico = tratamientoMedico;
        this.nombreVeterinario = nombreVeterinario;
    }
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
    @Override
    public String toString() {
        return String.format("============ATENCIÓN MÉDICA [%03d]============", idHistorial) +
                            "\nFecha registrada: " + fechaAtencion + 
                            "\nProcedimiento: " + tipoProcedimiento +
                            "\nTratamiento: " + tratamientoMedico +
                            "\nDiagnóstico: " + diagnosticoMedico +
                            "\nVeterinario: " + nombreVeterinario;
    }

}
