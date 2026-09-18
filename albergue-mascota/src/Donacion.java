import java.time.LocalDate;

public class Donacion {
    private int idDonacion;
    private String donante;
    private String tipoDonacion; // "Efectivo" o "Comida"
    private double montoEfectivo;
    private double cantidadComidaKg;
    private LocalDate fecha;

    // Constructor para Efectivo
    public Donacion(int idDonacion, String donante, double montoEfectivo) {
        this.idDonacion = idDonacion;
        this.donante = donante;
        this.tipoDonacion = "Efectivo";
        this.montoEfectivo = montoEfectivo;
        this.cantidadComidaKg = 0;
        this.fecha = LocalDate.now();
    }

    // Constructor sobrecargado para Comida
    public Donacion(int idDonacion, String donante, double cantidadComidaKg, boolean esComida) {
        this.idDonacion = idDonacion;
        this.donante = donante;
        this.tipoDonacion = "Comida";
        this.montoEfectivo = 0;
        this.cantidadComidaKg = cantidadComidaKg;
        this.fecha = LocalDate.now();
    }

    public int getIdDonacion() { return idDonacion; }
    public String getDonante() { return donante; }
    public String getTipoDonacion() { return tipoDonacion; }
    public double getMontoEfectivo() { return montoEfectivo; }
    public double getCantidadComidaKg() { return cantidadComidaKg; }
    public LocalDate getFecha() { return fecha; }

    @Override
    public String toString() {
        if (tipoDonacion.equalsIgnoreCase("Efectivo")) {
            return String.format("============ DONACIÓN [%d] ============\nFecha: %s\nDonante: %s\nTipo: Efectivo\nMonto: S/ %.2f\n=======================================", 
                    idDonacion, fecha, donante, montoEfectivo);
        } else {
            return String.format("============ DONACIÓN [%d] ============\nFecha: %s\nDonante: %s\nTipo: Comida\nCantidad: %.2f Kg\n=======================================", 
                    idDonacion, fecha, donante, cantidadComidaKg);
        }
    }
}