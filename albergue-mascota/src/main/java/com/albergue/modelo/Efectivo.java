/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.albergue.modelo;

/**
 *
 * @author Fabricioo
 */
public class Efectivo extends Donacion{
    private Double montoEfectivo;

    /**
    * Construye una donación en efectivo inicializando los atributos
    * heredados de Donacion y el monto correspondiente.
    *
    * @param idDonacion identificador único de la donación.
    * @param donante nombre de la persona que realiza la donación.
    * @param montoEfectivo cantidad de dinero donada.
    */
    public Efectivo(int idDonacion, String donante, Double montoEfectivo) {
        super(idDonacion, donante);
        this.montoEfectivo = montoEfectivo;
    }

    //Getter y Setter
    public Double getMontoEfectivo() {
        return montoEfectivo;
    }

    public void setMontoEfectivo(Double montoEfectivo) {
        this.montoEfectivo = montoEfectivo;
    }

    /**
    * Sobrescribe el método mostrarDetalle() de la clase Donacion
    * para mostrar la información específica de una donación en efectivo.
    */
    @Override
    public void mostrarDetalle(){
        String detalleEfectivo = String.format(
                    "============ DONACIÓN [%d] ============\n" +
                    "Fecha: %s\n" +
                    "Donante: %s\n" +
                    "Tipo: Efectivo\n" +
                    "Monto: S/ %.2f\n" +
                    "=======================================",
                    getIdDonacion(), getFecha(), getDonante(), getMontoEfectivo());

        System.out.println(detalleEfectivo);
    }

}
