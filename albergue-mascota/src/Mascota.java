import java.util.List;
import java.util.ArrayList;

public class Mascota {

    // Atributos

    private String idMascota;
    private String raza;
    private int edad;
    private String genero;
    private double peso;
    private double tamaño;
    private String estadoAdopcion;
    private String nombre;

    private List<HistorialClinico> historialClinico = new ArrayList<>();

    /**
     * Constructor vacío de la clase Mascota.
     */
    public Mascota() {
    }

    /**
     * Crea una nueva mascota con sus datos principales.
     *
     * @param idMascota identificador de la mascota.
     * @param nombre nombre de la mascota.
     * @param raza raza de la mascota.
     * @param edad edad de la mascota.
     * @param genero género de la mascota.
     * @param peso peso de la mascota.
     * @param tamaño tamaño de la mascota.
     */
    public Mascota(String idMascota, String nombre, String raza,
                   int edad, String genero, double peso, double tamaño) {

        setIdMascota(idMascota);
        setNombre(nombre);
        setRaza(raza);
        setEdad(edad);
        setGenero(genero);
        setPeso(peso);
        setTamaño(tamaño);
        setEstadoAdopcion("refugiado");
    }

    // Getters

    public String getIdMascota() {
        return idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRaza() {
        return raza;
    }

    public int getEdad() {
        return edad;
    }

    public String getGenero() {
        return genero;
    }

    public double getPeso() {
        return peso;
    }

    public double getTamaño() {
        return tamaño;
    }

    public String getEstadoAdopcion() {
        return estadoAdopcion;
    }

    public List<HistorialClinico> getHistorialClinico() {
        return historialClinico;
    }

    // Setters

    /**
     * Establece el identificador de la mascota.
     *
     * @param idMascota identificador de la mascota, no puede estar vacío.
     * @throws IllegalArgumentException si el identificador está vacío.
     */
    public void setIdMascota(String idMascota) {
        if (idMascota.isBlank()) {
            throw new IllegalArgumentException(
                    "El id de mascota no puede estar vacío");
        }

        this.idMascota = idMascota;
    }

    /**
     * Establece el nombre de la mascota.
     *
     * @param nombre nombre de la mascota, no puede estar vacío.
     * @throws IllegalArgumentException si el nombre está vacío.
     */
    public void setNombre(String nombre) {
        if (nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la mascota no puede estar vacío");
        }

        this.nombre = nombre;
    }

    /**
     * Establece la raza de la mascota.
     *
     * @param raza raza de la mascota, no puede estar vacía.
     * @throws IllegalArgumentException si la raza está vacía.
     */
    public void setRaza(String raza) {
        if (raza.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la raza no puede estar vacío");
        }

        this.raza = raza;
    }

    /**
     * Establece la edad de la mascota.
     *
     * @param edad edad de la mascota, debe estar entre 0 y 12 años.
     * @return true si la edad es válida, false si está fuera del rango permitido.
     */
    public boolean setEdad(int edad) {
        if (edad >= 0 && edad <= 12) {
            this.edad = edad;
            return true;
        }

        return false;
    }

    /**
     * Establece el género de la mascota.
     *
     * @param genero género de la mascota, debe ser Macho o Hembra.
     * @return true si el género es válido, false si no corresponde a las opciones permitidas.
     */
    public boolean setGenero(String genero) {
        if (genero != null &&
            (genero.equalsIgnoreCase("Macho") ||
             genero.equalsIgnoreCase("Hembra"))) {

            this.genero = genero;
            return true;
        }

        return false;
    }

    /**
     * Establece el peso de la mascota.
     *
     * @param peso peso de la mascota, no puede ser negativo.
     * @throws IllegalArgumentException si el peso es negativo.
     */
    public void setPeso(double peso) {
        if (peso < 0) {
            throw new IllegalArgumentException(
                    "El peso no puede ser negativo");
        }

        this.peso = peso;
    }

    /**
     * Establece el tamaño de la mascota.
     *
     * @param tamaño tamaño de la mascota, no puede ser negativo.
     * @throws IllegalArgumentException si el tamaño es negativo.
     */
    public void setTamaño(double tamaño) {
        if (tamaño < 0) {
            throw new IllegalArgumentException(
                    "El tamaño no puede ser negativo");
        }

        this.tamaño = tamaño;
    }

    /**
     * Establece el estado de adopción de la mascota.
     *
     * @param estadoAdopcion estado de la mascota: Refugiado, Tratamiento o Adoptado.
     * @return true si el estado es válido, false si no corresponde a las opciones permitidas.
     */
    public boolean setEstadoAdopcion(String estadoAdopcion) {
        if (estadoAdopcion.equalsIgnoreCase("Refugiado") ||
            estadoAdopcion.equalsIgnoreCase("Tratamiento") ||
            estadoAdopcion.equalsIgnoreCase("Adoptado")) {

            this.estadoAdopcion = estadoAdopcion;
            return true;
        }

        return false;
    }

    /**
     * Agrega un registro al historial clínico de la mascota.
     *
     * @param registro registro clínico que se desea agregar.
     */
    public void agregarHistorialMedico(HistorialClinico registro) {
        historialClinico.add(registro);
    }

    /**
     * Devuelve la información de la mascota.
     *
     * @return datos de la mascota en formato de texto.
     */
    @Override
    public String toString() {
        return "============ FICHA MASCOTA ============" +
                "\nID de mascota: " + idMascota +
                "\nNombre: " + nombre +
                "\nRaza: " + raza +
                "\nEdad: " + edad + " años" +
                "\nGenero: " + genero +
                "\nPeso: " + peso + " kg" +
                "\nTamaño: " + tamaño + " m" +
                "\nEstado: " + estadoAdopcion +
                "\n=======================================";
    }
}
    
    

    


