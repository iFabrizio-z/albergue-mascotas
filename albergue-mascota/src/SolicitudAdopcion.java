import java.time.LocalDate;

public class SolicitudAdopcion {
    
    // Atributos de control 
    private int idSolicitud;
    private LocalDate fechaSolicitud;
    private Adoptante adoptante;
    private String estado; 

    /*  Formulario (Información Personal)
    private String nombreAdoptante;
    private String apellidosAdoptante;
    private String dni;
    private String direccion;
    private String email;
    private String telefono;
    */

    public SolicitudAdopcion(int idSolicitud, LocalDate fechaSolicitud, String estado, Mascota mascota) {
        this.idSolicitud = idSolicitud;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
        this.mascota = mascota;
    }

    // Atributo de la mascota
    private Mascota mascota;

    // Constructor
    public SolicitudAdopcion(int idSolicitud, Adoptante adoptante,/*String nombreAdoptante, String apellidosAdoptante, 
                            String dni, String direccion, String email, String telefono,*/ Mascota mascota) {
        this.idSolicitud = idSolicitud;
        this.fechaSolicitud = LocalDate.now(); 
        this.estado = "Pendiente"; 
        
        /* 
        this.nombreAdoptante = nombreAdoptante;
        this.apellidosAdoptante = apellidosAdoptante;
        this.dni = dni;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        */
        this.mascota = mascota;
        
        
    }




    // Setter exclusivo para que el Sistema cambie el estado
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdSolicitud() { return idSolicitud; }
    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public String getEstado() { return estado; }
    public void setIdSolicitud(int idSolicitud) { this.idSolicitud = idSolicitud; }
    public void setFechaSolicitud(LocalDate fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota;}
    /*
    public String getNombreAdoptante() { return nombreAdoptante; }
    public String getApellidosAdoptante() { return apellidosAdoptante; }
    public String getDni() { return dni; }
    public String getDireccion() { return direccion; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }

    */

    @Override
    public String toString() {
        return String.format("============SOLICITUD DE ADOPCIÓN [%d]============", idSolicitud) +
                "\nFecha registrada: " + fechaSolicitud +
                "\nEstado: " + estado +
                /*
                "\nAdoptante: " + nombreAdoptante + " " + apellidosAdoptante +
                "\nDNI: " + dni +
                "\nTeléfono/Móvil: " + telefono +
                */
                "\nMascota solicitada: " + (mascota != null ? mascota.getNombre() : "Ninguna");
    }
}

