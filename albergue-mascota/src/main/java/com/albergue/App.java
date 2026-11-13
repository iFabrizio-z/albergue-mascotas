package com.albergue;

import com.albergue.sistema.SistemaAlbergue;
import com.albergue.persistencia.PersistenciaDatos;
import com.albergue.interfaz.InicioSesion;

public class App {

    public static void main(String[] args) {

        com.formdev.flatlaf.FlatLightLaf.setup();

        SistemaAlbergue sistema = PersistenciaDatos.cargarDatos();

        java.awt.EventQueue.invokeLater(() -> {

            InicioSesion ventana =
                    new InicioSesion(null, true, sistema);

            ventana.setVisible(true);
        });
    }
}