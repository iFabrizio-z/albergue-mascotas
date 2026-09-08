public class App {
    public static void main(String[] args) throws Exception {
        Mascota mascota_1 = new Mascota("Pastor Aleman", 2, "Macho", 35.7, 1.05);
        Mascota mascota_2 = new Mascota("Pitbull", 3, "Hembra", 25, 50);

        IO.println("============REGISTRO DE MASCOTA RESCATADA - PERRO============");
        IO.println("Raza: "+ mascota_1.getRaza());
        IO.println("Edad: " + mascota_1.getEdad() + " años");
        IO.println("Genero: " + mascota_1.getGenero());
        IO.println("Peso: " + mascota_1.getPeso() + " kg");
        IO.println("Tamaño: " + mascota_1.getTamaño() + " m");
        IO.println("==============================================================\n");
        IO.println("============REGISTRO DE MASCOTA RESCATADA - PERRO============");
        IO.println("Raza: "+ mascota_2.getRaza());
        IO.println("Edad: " + mascota_2.getEdad() + " años");
        IO.println("Genero: " + mascota_2.getGenero());
        IO.println("Peso: " + mascota_2.getPeso() + " kg");
        IO.println("Tamaño: " + mascota_2.getTamaño() + " m");
        IO.println("==============================================================");


    }
}
