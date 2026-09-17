import java.util.List;
import java.util.ArrayList;

public class Mascota {
    private int idMascota;
    private String raza;
    private int edad;
    private String genero;
    private double peso;
    private double tamaño;
    private String estadoAdopcion;
    private String nombre;
    private List<HistorialClinico> historialClinico;

    public Mascota(int idMascota, String nombre, String raza, int edad, String genero, double peso, double tamaño) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.raza = raza;
        setEdad(edad);
        setGenero(genero);
        this.peso = peso;
        this.tamaño = tamaño;
        setEstadoAdopcion("refugiado");
        this.historialClinico = new ArrayList<>();
    }

    public int getIdMascota(){
        return idMascota;
    }

    public void setIdMascota(int idMascota){
        this.idMascota = idMascota;
    }


    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }
    
    public boolean setEdad(int edad) {
        if (edad >= 0 && edad <= 12) {
            this.edad = edad;
            return true; 
        }
        else{
            System.out.println("Ingresa nuevamente la edad");
            return false;
        }

    }

    public String getGenero() {
        return genero;
    }

    public boolean setGenero(String genero) {
        if (genero != null && (genero.equalsIgnoreCase("Macho") || genero.equalsIgnoreCase("Hembra"))) {
            this.genero = genero;
            return true;
        }
        else{
            System.out.println("Genero no permitido, ingresa otra vez");
            return false;
        }
}
    
    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getTamaño() {
        return tamaño;
    }

    public void setTamaño(double tamaño) {
        this.tamaño = tamaño;
    }

    public String getEstadoAdopcion() {
        return estadoAdopcion;

    }

    public boolean setEstadoAdopcion(String estadoAdopcion) {
        if (estadoAdopcion.equalsIgnoreCase("Refugiado") || 
            estadoAdopcion.equalsIgnoreCase("Tratamiento") || 
            estadoAdopcion.equalsIgnoreCase("Adoptado")) {
            
            this.estadoAdopcion = estadoAdopcion;
            return true;
        } else {
            System.out.println("Ingrese otra vez el estado");
            return false;
        }
    
    }

    public List<HistorialClinico> getHistorialClinico(){
        return historialClinico;
    }

    public void agregarHistorialMedico(HistorialClinico registro){
        historialClinico.add(registro);
    }

	@Override
	public String toString() {
		return "============ FICHA MASCOTA ============" + 
                "\n ID de mascota: "+ idMascota +
                "\n Nombre: " + nombre +
                "\n Raza: " + raza + 
                "\n Edad: " + edad + 
                "\n Genero: " + genero +
				"\n Peso: " + peso + 
                "\n Tamaño: " + tamaño + 
                "\n Estado: " + estadoAdopcion +
                "=======================================";

    }

}
    
    

    


