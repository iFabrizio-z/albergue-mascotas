public class Usuario {
    private String idUsuario;
    private String nombre;
    private String correo;
    private String rol;
    private boolean activo;
    private String contrasena;

    // Constructor completo
    public Usuario(String idUsuario, String nombre, String correo, String rol, String contrasena) {
        if (idUsuario == null || idUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de usuario no puede estar vacío.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario no puede estar vacío.");
        }
        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("Debe ingresar un correo electrónico válido.");
        }

        this.idUsuario = idUsuario.trim();
        this.nombre = nombre.trim();
        this.correo = correo.trim();
        this.rol = (rol != null && !rol.trim().isEmpty()) ? rol.trim().toUpperCase() : "VOLUNTARIO";
        this.activo = true;
        setContrasena(contrasena);
    }

    // Sobrecarga de constructor (Rol "VOLUNTARIO" por defecto)
    public Usuario(String idUsuario, String nombre, String correo, String contrasena) {
        this(idUsuario, nombre, correo, "VOLUNTARIO", contrasena);
    }

    // Getters y Setters
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        if (contrasena == null || contrasena.trim().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
        this.contrasena = contrasena.trim();
    }

    // Método para validar el acceso en inicio de sesión
    public boolean validarContrasena(String intento) {
        return this.contrasena != null && this.contrasena.equals(intento);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", rol='" + rol + '\'' +
                ", activo=" + activo +
                '}';
    }
}
