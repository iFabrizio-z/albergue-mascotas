/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.albergue.modelo;
import java.time.LocalDate;

/**
 *
 * @author Fabricioo
 */
public abstract class Donacion{

    // Atributos

    private int idDonacion;
    private String donante;
    private LocalDate fecha;


    /**
     * Constructor para registrar una donación.
     *
     * @param idDonacion identificador de la donación.
     * @param donante nombre de la persona que realiza la donación.
     */
    public Donacion(int idDonacion, String donante) {

        this.idDonacion = idDonacion;
        this.donante = donante;
        this.fecha = LocalDate.now();
    }

    // Getters

    public int getIdDonacion() {
        return idDonacion;
    }

    public String getDonante() {
        return donante;
    }

    public LocalDate getFecha() {
        return fecha;
    }


    /**
     * Creación de un método abstracto para mostrar el detalle
     * de la donación.
     * Las clases hijas deberán sobreescribir este método
     * para mostrar el detalle según tipo de donación
     */
    public abstract void mostrarDetalle();

}
