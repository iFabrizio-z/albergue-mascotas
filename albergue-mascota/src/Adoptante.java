public class Adoptante {

    // Atributos

    private String nombre;
    private String apellidos;
    private String dni;
    private String direccion;
    private String email;
    private String telefono;

    /**
     * Constructor vacío de la clase Adoptante.
     */
    public Adoptante() {
    }

    /**
     * Crea un nuevo adoptante con sus datos personales.
     *
     * @param nombre nombre del adoptante.
     * @param apellidos apellidos del adoptante.
     * @param dni documento de identidad del adoptante.
     * @param direccion dirección del adoptante.
     * @param email correo electrónico del adoptante.
     * @param telefono número de teléfono del adoptante.
     */
    public Adoptante(String nombre, String apellidos, String dni,
                      String direccion, String email, String telefono) {

        setNombre(nombre);
        setApellidos(apellidos);
        setDni(dni);
        setDireccion(direccion);
        setEmail(email);
        setTelefono(telefono);
    }

    // Setters

    /**
     * Establece el nombre del adoptante.
     *
     * @param nombre nombre del adoptante, no puede estar vacío.
     * @throws IllegalArgumentException si el nombre está vacío.
     */
    public void setNombre(String nombre) {
        if (nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacío");
        }

        this.nombre = nombre;
    }

    /**
     * Establece los apellidos del adoptante.
     *
     * @param apellidos apellidos del adoptante, no pueden estar vacíos.
     * @throws IllegalArgumentException si los apellidos están vacíos.
     */
    public void setApellidos(String apellidos) {
        if (apellidos.isBlank()) {
            throw new IllegalArgumentException(
                    "Los apellidos no pueden estar vacíos");
        }

        this.apellidos = apellidos;
    }

    /**
     * Establece el DNI del adoptante.
     *
     * @param dni documento de identidad, debe tener 8 dígitos.
     * @throws IllegalArgumentException si el DNI está vacío,
     * tiene una longitud diferente de 8 o contiene caracteres no numéricos.
     */
    public void setDni(String dni) {
        if (dni.isBlank()) {
            throw new IllegalArgumentException(
                    "El DNI no puede estar vacío");
        }

        if (dni.length() != 8) {
            throw new IllegalArgumentException(
                    "El DNI debe tener 8 dígitos");
        }

        for (int i = 0; i < dni.length(); i++) {
            if (!Character.isDigit(dni.charAt(i))) {
                throw new IllegalArgumentException(
                        "El DNI solo debe contener números");
            }
        }

        this.dni = dni;
    }

    /**
     * Establece la dirección del adoptante.
     *
     * @param direccion dirección del adoptante, no puede estar vacía.
     * @throws IllegalArgumentException si la dirección está vacía.
     */
    public void setDireccion(String direccion) {
        if (direccion.isBlank()) {
            throw new IllegalArgumentException(
                    "La dirección no puede estar vacía");
        }

        this.direccion = direccion;
    }

    /**
     * Establece el correo electrónico del adoptante.
     *
     * @param email correo electrónico, no puede estar vacío.
     * @throws IllegalArgumentException si el correo está vacío.
     */
    public void setEmail(String email) {
        if (email.isBlank()) {
            throw new IllegalArgumentException(
                    "El email no puede estar vacío");
        }

        this.email = email;
    }

    /**
     * Establece el número de teléfono del adoptante.
     *
     * @param telefono número de teléfono, debe tener 9 dígitos.
     * @throws IllegalArgumentException si el teléfono está vacío,
     * tiene una longitud diferente de 9 o contiene caracteres no numéricos.
     */
    public void setTelefono(String telefono) {
        if (telefono.isBlank()) {
            throw new IllegalArgumentException(
                    "El teléfono no puede estar vacío");
        }

        if (telefono.length() != 9) {
            throw new IllegalArgumentException(
                    "El teléfono debe tener 9 dígitos");
        }

        for (int i = 0; i < telefono.length(); i++) {
            if (!Character.isDigit(telefono.charAt(i))) {
                throw new IllegalArgumentException(
                        "El teléfono solo debe contener números");
            }
        }

        this.telefono = telefono;
    }

    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDni() {
        return dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    /**
     * Devuelve la información del adoptante.
     *
     * @return datos del adoptante en formato de texto.
     */
    @Override
    public String toString() {
        return "============ DATOS DEL ADOPTANTE ============" +
                "\nNombre: " + nombre +
                "\nApellidos: " + apellidos +
                "\nDNI: " + dni +
                "\nDirección: " + direccion +
                "\nEmail: " + email +
                "\nTeléfono: " + telefono +
                "\n=============================================";
    }
}