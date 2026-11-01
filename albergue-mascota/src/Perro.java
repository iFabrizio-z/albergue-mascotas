public class Perro extends Mascota {

    // Nuevos atributos específicos de Perro
    private String nivelEntrenamiento;
    private boolean conviveConOtrosPerros;

    // Constructor vacío
    public Perro() {
        super(); // Llama al constructor vacío de la clase padre (Mascota)
    }

    // Constructor con todos los parámetros (los heredados + los nuevos)
    public Perro(String idMascota, String nombre, String raza, int edad, String genero, 
                 double peso, double tamaño, String nivelEntrenamiento, boolean conviveConOtrosPerros) {
        
        // super() envía los datos comunes a la clase padre Mascota
        super(idMascota, nombre, raza, edad, genero, peso, tamaño);
        
        this.nivelEntrenamiento = nivelEntrenamiento;
        this.conviveConOtrosPerros = conviveConOtrosPerros;
    }

    // Getters y Setters de los nuevos atributos
    public String getNivelEntrenamiento() {
        return nivelEntrenamiento;
    }

    public void setNivelEntrenamiento(String nivelEntrenamiento) {
        this.nivelEntrenamiento = nivelEntrenamiento;
    }

    public boolean isConviveConOtrosPerros() {
        return conviveConOtrosPerros;
    }

    public void setConviveConOtrosPerros(boolean conviveConOtrosPerros) {
        this.conviveConOtrosPerros = conviveConOtrosPerros;
    }

    // Sobreescribimos el toString para incluir los nuevos datos
    @Override
    public String toString() {
        String convive = conviveConOtrosPerros ? "Sí" : "No";
        
        return "============ FICHA MASCOTA (PERRO) ============" +
                "\nID de mascota: " + getIdMascota() +
                "\nNombre: " + getNombre() +
                "\nRaza: " + getRaza() +
                "\nEdad: " + getEdad() + " años" +
                "\nGenero: " + getGenero() +
                "\nPeso: " + getPeso() + " kg" +
                "\nTamaño: " + getTamaño() + " m" +
                "\nNivel de Entrenamiento: " + nivelEntrenamiento +
                "\nConvive con otros perros: " + convive +
                "\nEstado: " + getEstadoAdopcion() +
                "\n===============================================";
    }
}