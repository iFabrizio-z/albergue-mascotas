import java.time.LocalDate;

public class Donacion {

    // Atributos

    private int idDonacion;
    private String donante;
    private String tipoDonacion;
    private double montoEfectivo;
    private double cantidadComidaKg;
    private LocalDate fecha;

    /**
     * Constructor para registrar una donación en efectivo.
     *
     * @param idDonacion identificador de la donación.
     * @param donante nombre de la persona que realiza la donación.
     * @param montoEfectivo monto de dinero donado.
     */
    public Donacion(int idDonacion, String donante, double montoEfectivo) {

        this.idDonacion = idDonacion;
        this.donante = donante;
        this.tipoDonacion = "Efectivo";
        this.montoEfectivo = montoEfectivo;
        this.cantidadComidaKg = 0;
        this.fecha = LocalDate.now();
    }

    /**
     * Constructor para registrar una donación de comida.
     *
     * @param idDonacion identificador de la donación.
     * @param donante nombre de la persona que realiza la donación.
     * @param cantidadComidaKg cantidad de comida donada en kilogramos.
     * @param esComida indica que la donación corresponde a comida.
     */
    public Donacion(int idDonacion, String donante,
                    double cantidadComidaKg, boolean esComida) {

        this.idDonacion = idDonacion;
        this.donante = donante;
        this.tipoDonacion = "Comida";
        this.montoEfectivo = 0;
        this.cantidadComidaKg = cantidadComidaKg;
        this.fecha = LocalDate.now();
    }

    // Getters

    public int getIdDonacion() {
        return idDonacion;
    }

    public String getDonante() {
        return donante;
    }

    public String getTipoDonacion() {
        return tipoDonacion;
    }

    public double getMontoEfectivo() {
        return montoEfectivo;
    }

    public double getCantidadComidaKg() {
        return cantidadComidaKg;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Devuelve la información de la donación registrada.
     *
     * @return datos de la donación en formato de texto.
     */
    @Override
    public String toString() {

        if (tipoDonacion.equalsIgnoreCase("Efectivo")) {

            return String.format(
                    "============ DONACIÓN [%d] ============\n" +
                    "Fecha: %s\n" +
                    "Donante: %s\n" +
                    "Tipo: Efectivo\n" +
                    "Monto: S/ %.2f\n" +
                    "=======================================",
                    idDonacion, fecha, donante, montoEfectivo);

        } else {

            return String.format(
                    "============ DONACIÓN [%d] ============\n" +
                    "Fecha: %s\n" +
                    "Donante: %s\n" +
                    "Tipo: Comida\n" +
                    "Cantidad: %.2f Kg\n" +
                    "=======================================",
                    idDonacion, fecha, donante, cantidadComidaKg);
        }
    }
}