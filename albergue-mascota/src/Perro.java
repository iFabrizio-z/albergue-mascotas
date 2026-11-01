public class Perro extends Mascota {

    // Atributos específicos del perro
    private String nivelEntrenamiento;
    private boolean conviveConOtrosPerros;

    // Constructor vacío 
    public Perro() {
        super();
    }

    // Constructor con parámetros
    public Perro(String idMascota, String raza, int edad,
                 String genero, double peso, String tamaño,
                 String estadoAdopcion, String nombre,
                 String nivelEntrenamiento,
                 boolean conviveConOtrosPerros) {

        super(idMascota, raza, edad, genero, peso,
              tamaño, estadoAdopcion, nombre);

        setNivelEntrenamiento(nivelEntrenamiento);
        setConviveConOtrosPerros(conviveConOtrosPerros);
    }

    // Getter y setter del nivel de entrenamiento
    public String getNivelEntrenamiento() {
        return nivelEntrenamiento;
    }

    public void setNivelEntrenamiento(String nivelEntrenamiento) {
        if (nivelEntrenamiento == null ||
            (!nivelEntrenamiento.equalsIgnoreCase("Basico") &&
             !nivelEntrenamiento.equalsIgnoreCase("Intermedio") &&
             !nivelEntrenamiento.equalsIgnoreCase("Avanzado"))) {
            throw new IllegalArgumentException(
                "El nivel debe ser Basico, Intermedio o Avanzado"
            );
        }
        this.nivelEntrenamiento = nivelEntrenamiento;
    }

    // Getter y setter de convivencia
    public boolean isConviveConOtrosPerros() {
        return conviveConOtrosPerros;
    }

    public void setConviveConOtrosPerros(boolean conviveConOtrosPerros) {
        this.conviveConOtrosPerros = conviveConOtrosPerros;
    }

    // Sobrescritura del método de Mascota
    @Override
    public void mostrarDatosMascota() {

        // Mostrar los atributos heredados
        super.mostrarDatosMascota();

        // Mostrar los atributos propios de Perro
        System.out.println("Nivel de entrenamiento: " + nivelEntrenamiento);
        System.out.println("Convive con otros perros: " +
                (conviveConOtrosPerros ? "Sí" : "No"));

        System.out.println("=============================================");
    }
}
