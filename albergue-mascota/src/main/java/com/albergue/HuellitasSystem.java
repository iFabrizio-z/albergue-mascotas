package com.upn.huellitas;

import com.upn.huellitas.logica.SistemaAlbergueMascota;
import com.upn.huellitas.vista.Tema;
import com.upn.huellitas.vista.LoginView;
import com.upn.huellitas.vista.VentanaPrincipal;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Punto de entrada de la versión con interfaz gráfica de Huellitas System.
 * (La versión de consola sigue disponible en com.upn.huellitas.consola.App).
 */
public class HuellitasSystem {

    private SistemaAlbergueMascota sistema;

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        SwingUtilities.invokeLater(() -> new HuellitasSystem().mostrarLogin());
    }

    /** Inicio de sesión (Dannaé Miranda): ventana Swing estándar con el aspecto nativo del sistema operativo. */
    private void mostrarLogin() {
        aplicarAspectoNativo();
        new LoginView(this::abrirSistema).setVisible(true);
    }

    private void abrirSistema() {
        Tema.aplicarLookAndFeel();      // el resto del sistema usa la paleta de Huellitas
        prepararArchivoDatos();
        sistema = SistemaAlbergueMascota.cargarDatos();
        new VentanaPrincipal(sistema, "Administrador", this::mostrarLogin).setVisible(true);
    }

    /**
     * Restaura el aspecto nativo de Java para la ventana de inicio de sesión
     * (al volver de «Cerrar sesión» se quitan los colores del tema).
     */
    private static void aplicarAspectoNativo() {
        UIManager.put("control", null);
        UIManager.put("text", null);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // si falla, se mantiene el aspecto por defecto de Java
        }
    }

    /**
     * Si no existe albergue.json en la carpeta de ejecución, crea uno con los
     * datos de ejemplo incluidos en el proyecto.
     */
    static void prepararArchivoDatos() {
        Path archivo = Path.of(SistemaAlbergueMascota.ARCHIVO_DATOS);
        if (Files.exists(archivo)) {
            return;
        }
        try (InputStream datos = HuellitasSystem.class.getResourceAsStream("/datos/albergue.json")) {
            if (datos != null) {
                Files.copy(datos, archivo);
            }
        } catch (IOException e) {
            System.err.println("No se pudieron copiar los datos de ejemplo: " + e.getMessage());
        }
    }
}
