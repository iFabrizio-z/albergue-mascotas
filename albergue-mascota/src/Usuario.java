public class Usuario {
    private String usuario;
    private String contrasena;
    private String nombre;

    public Usuario(String usuario, String contrasena, String nombre) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean autenticar(String usuarioIngresado, String contrasenaIngresada) {
        return this.usuario.equals(usuarioIngresado) && this.contrasena.equals(contrasenaIngresada);
        
    }
}
