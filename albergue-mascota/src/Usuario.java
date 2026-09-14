public class Usuario {
    private String idUsuario;
    private String nombre;
    private String correo;
    private String rol;
    private boolean activo;

    // Constructor completo
    public Usuario(String idUsuario, String nombre, String correo, String rol) {
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
    }

    // Sobrecarga de constructor (Rol "VOLUNTARIO" por defecto)
    public Usuario(String idUsuario, String nombre, String correo) {
        this(idUsuario, nombre, correo, "VOLUNTARIO");
    }

    // Getters y Setters
    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo != null && correo.contains("@")) {
            this.correo = correo.trim();
        } else {
            throw new IllegalArgumentException("Correo no válido.");
        }
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        if (rol != null && !rol.trim().isEmpty()) {
            this.rol = rol.trim().toUpperCase();
        }
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Método para mostrar resumen
    public String obtenerResumen() {
        String estadoStr = activo ? "ACTIVO" : "INACTIVO";
        return String.format("[%s] %s | Rol: %s | Correo: %s | Estado: %s",
                idUsuario, nombre, rol, correo, estadoStr);
    }

    @Override
    public String toString() {
        return obtenerResumen();
    }
}
