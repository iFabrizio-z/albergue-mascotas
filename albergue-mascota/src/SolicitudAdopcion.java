public class SolicitudAdopcion{
    private String nombreAdoptante;
    private String telefono;
    private Mascota mascota; 
    private String estado; // Iniciará como "Pendiente"

    //Constructores (para crear la solicitud)
    public SolicitudAdopcion(String nombreAdoptante, String telefono, Mascota mascota) {
        this.nombreAdoptante = nombreAdoptante;
        this.telefono = telefono;
        this.mascota = mascota;
        this.estado = "Pendiente"; 
    }

    //Método para cambiar el estado de la solicitud
    public void evaluar(boolean aprobada) {
        if (aprobada) {
            this.estado = "Aprobada";
            System.out.println("Solicitud aprobada.");
        } else {
            this.estado = "Rechazada";
            System.out.println("Solicitud rechazada.");
        }
    }

    //Getters (Métodos para obtener los datos) 
    public String getNombreAdoptante() {
        return nombreAdoptante;
    }

    public String getTelefono() {
        return telefono;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public String getEstado() {
        return estado;
    }
}
