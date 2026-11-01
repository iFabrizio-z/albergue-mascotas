public class Comida extends Donacion {
    private Double cantidadComidaKg;

    /**
    * Construye una donación de comida inicializando los atributos
    * heredados de Donacion y la cantidad de comida donada.
    *
    * @param idDonacion identificador único de la donación.
    * @param donante nombre de la persona que realiza la donación.
    * @param cantidadComidaKg cantidad de comida donada en kilogramos.
    */
    public Comida(int idDonacion, String donante, Double cantidadComidaKg) {
        super(idDonacion, donante);
        this.cantidadComidaKg = cantidadComidaKg;
    }

    //Getter y setter
    public Double getCantidadComidaKg() {
        return cantidadComidaKg;
    }

    public void setCantidadComidaKg(Double cantidadComidaKg) {
        this.cantidadComidaKg = cantidadComidaKg;
    }
    
    /**
    * Sobrescribe el método mostrarDetalle() de la clase Donacion
    * para mostrar la información específica de una donación de comida.
    */
    @Override 
    public void mostrarDetalle(){
        String detalleComida = String.format(
                    "============ DONACIÓN [%d] ============\n" +
                    "Fecha: %s\n" +
                    "Donante: %s\n" +
                    "Tipo: Comida\n" +
                    "Cantidad: S/ %.2f\n" +
                    "=======================================",
                    getIdDonacion(), getFecha(), getDonante(), getCantidadComidaKg());

        System.out.println(detalleComida);
    }

}