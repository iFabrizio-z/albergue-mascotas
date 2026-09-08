public class Mascota {
    private String raza;
    private int edad;
    private String genero;
    private double peso;
    private double tamaño;

    public Mascota(String raza, int edad, String genero, double peso, double tamaño) {
        this.raza = raza;
        this.edad = edad;
        this.genero = genero;
        this.peso = peso;
        this.tamaño = tamaño;
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

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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

    
    

    

}
