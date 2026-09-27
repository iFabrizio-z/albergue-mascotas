public class Adoptante {

    // Formulario (Información Personal)
    private String nombreAdoptante;
    private String apellidosAdoptante;
    private String dni;
    private String direccion;
    private String email;
    private String telefono;    
    
    public Adoptante(String nombreAdoptante, String apellidosAdoptante, String dni, String direccion, String email,
            String telefono) {
        this.nombreAdoptante = nombreAdoptante;
        this.apellidosAdoptante = apellidosAdoptante;
        this.dni = dni;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }

    public String getNombreAdoptante() { return nombreAdoptante; }
    public void setNombreAdoptante(String nombreAdoptante) { this.nombreAdoptante = nombreAdoptante; }
    public String getApellidosAdoptante() { return apellidosAdoptante; }
    public void setApellidosAdoptante(String apellidosAdoptante) { this.apellidosAdoptante = apellidosAdoptante; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return "Adoptante{" +
                "nombre='" + nombreAdoptante + '\'' +
                ", apellidos='" + apellidosAdoptante + '\'' +
                ", dni='" + dni + '\'' +
                ", direccion='" + direccion + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
