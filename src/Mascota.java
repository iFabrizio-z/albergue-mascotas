
import java.util.ArrayList;
import java.util.List;

public abstract class Mascota {

    // Atributos
    private int idMascota;
    private String raza;
    private int edad;
    private String genero;
    private double peso;
    private String tamaño;
    private String estadoAdopcion;
    private String nombre;

    private List<HistorialClinico> historialClinico = new ArrayList<>();

    // Constructor vacío (útil para Gson)
    public Mascota() {
    }

    // Constructor con parámetros
    public Mascota(int idMascota, String raza, int edad,
                   String genero, double peso, String tamaño,
                   String estadoAdopcion, String nombre) {

        this.idMascota = idMascota;
        this.raza = raza;
        this.nombre = nombre;

        setEdad(edad);
        setGenero(genero);
        setPeso(peso);
        setTamaño(tamaño);
        setEstadoAdopcion(estadoAdopcion);
    }

    // Getters y setters

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        if (idMascota <= 0) {
            throw new IllegalArgumentException("El ID debe ser positivo.");
        }
        this.idMascota = idMascota;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        if (raza == null || raza.isBlank()) {
            throw new IllegalArgumentException("La raza no puede estar vacía.");
        }
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public boolean setEdad(int edad) {
        if (edad < 0 || edad > 30) {
            throw new IllegalArgumentException("La edad debe estar entre 0 y 30 años.");
        }
        this.edad = edad;
        return true;
    }

    public String getGenero() {
        return genero;
    }

    public boolean setGenero(String genero) {
        if (genero == null ||
            (!genero.equalsIgnoreCase("Macho") &&
             !genero.equalsIgnoreCase("Hembra"))) {
            throw new IllegalArgumentException("El género debe ser Macho o Hembra.");
        }
        this.genero = genero;
        return true;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (!Double.isFinite(peso) || peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero.");
        }
        this.peso = peso;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        if (tamaño == null || tamaño.isBlank()) {
            throw new IllegalArgumentException("El tamaño no puede estar vacío.");
        }
        this.tamaño = tamaño;
    }

    public String getEstadoAdopcion() {
        return estadoAdopcion;
    }

    public boolean setEstadoAdopcion(String estadoAdopcion) {
        if (estadoAdopcion == null ||
            (!estadoAdopcion.equalsIgnoreCase("Refugiado") &&
             !estadoAdopcion.equalsIgnoreCase("Tratamiento") &&
             !estadoAdopcion.equalsIgnoreCase("Adoptado"))) {
            throw new IllegalArgumentException("Estado de adopción no válido.");
        }
        this.estadoAdopcion = estadoAdopcion;
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public List<HistorialClinico> getHistorialClinico() {
        return historialClinico;
    }

    // Agregar historial clínico
    public void agregarHistorialMedico(HistorialClinico historial) {
        if (historial == null) {
            throw new IllegalArgumentException("El historial no puede ser nulo.");
        }
        historialClinico.add(historial);
    }

    // Mostrar datos generales de la mascota
    public void mostrarDatosMascota() {
        System.out.println("============ DATOS DE LA MASCOTA ============");
        System.out.println("ID: " + idMascota);
        System.out.println("Nombre: " + nombre);
        System.out.println("Raza: " + raza);
        System.out.println("Edad: " + edad + " años");
        System.out.println("Género: " + genero);
        System.out.println("Peso: " + peso + " kg");
        System.out.println("Tamaño: " + tamaño);
        System.out.println("Estado de adopción: " + estadoAdopcion);
        System.out.println("=============================================");
    }
}
