public class Usuario {

    // Atributos
    private String usuario;
    private String contrasena;
    private String nombre;

    // Método constructor
    public Usuario(String usuario, String contrasena, String nombre) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
    }

    //Getters
    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    /**

    * Verifica si el usuario y la contraseña son correctos.
    *
    * @param usuarioIngresado usuario ingresado.
    * @param contrasenaIngresada contraseña ingresada.
    * @return true si los datos son correctos, false si no.
    
    */
    public boolean autenticar(String usuarioIngresado, String contrasenaIngresada) {
        return this.usuario.equals(usuarioIngresado) && this.contrasena.equals(contrasenaIngresada);
    }

    
    
}
