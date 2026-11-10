/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.albergue;

import com.albergue.sistema.SistemaAlbergue;
import com.albergue.persistencia.PersistenciaDatos;


public class App {

    public static void main(String[] args) {

        com.formdev.flatlaf.FlatLightLaf.setup();

        SistemaAlbergue sistema = PersistenciaDatos.cargarDatos();

        java.awt.EventQueue.invokeLater(() -> {
            com.albergue.interfaz.GestionHistorialClinico ventana
                    = new com.albergue.interfaz.GestionHistorialClinico(
                            null, true, sistema);

            ventana.setVisible(true);
        });
    
    }
}