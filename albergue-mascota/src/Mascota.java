public class Mascota {
    private String raza;
    private int edad;
    private String genero;
    private double peso;
    private double tamaño;
    private String estadoAdopcion;

    public Mascota(String raza, int edad, String genero, double peso, double tamaño) {
        this.raza = raza;
        this.edad = edad;
        this.genero = genero;
        this.peso = peso;
        this.tamaño = tamaño;
        this.estadoAdopcion="refugiado";
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
        return false;

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

    public String getestadoAdopcion() {
        return estadoAdopcion;

    }

    public boolean setestadoAdopción(String estadoAdopcion) {
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

    

    

}
