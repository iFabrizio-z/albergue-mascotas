/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.albergue.interfaz;

import com.albergue.sistema.SistemaAlbergue;
import com.albergue.modelo.Mascota;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author Fabricioo
 */
public class GestionRegistroMascota extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GestionRegistroMascota.class.getName());
    private SistemaAlbergue sistema;
    private boolean modoEdicion = false;
    private String idMascotaEnEdicion;

    /**
     * Creates new form GestionRegistroMascota
     */
    public GestionRegistroMascota(
            java.awt.Frame parent,
            boolean modal,
            SistemaAlbergue sistema) {

        super(parent, modal);
        this.sistema = sistema;

        initComponents();

        configurarFormulario();
        configurarEventos();
        mostrarSiguienteId();
        configurarPlaceholder();
        cargarMascotas();
        cargarLogo();
        setLocationRelativeTo(parent);

    }

    private void cargarLogo() {

        java.net.URL ruta = getClass().getResource(
                "/imagenes/logo.jpg"
        );

        if (ruta != null) {

            javax.swing.ImageIcon imagen
                    = new javax.swing.ImageIcon(ruta);

            java.awt.Image imagenAjustada
                    = imagen.getImage().getScaledInstance(
                            65, 65,
                            java.awt.Image.SCALE_SMOOTH
                    );

            lblLogo.setIcon(
                    new javax.swing.ImageIcon(imagenAjustada)
            );

        } else {
            System.out.println("No se encontró el logo.");
        }
    }

    private void configurarEventos() {

        btnHistGuardar.addActionListener(e -> guardarMascota());
        btnHistGuardar1.addActionListener(e -> limpiarFormulario());
        btnHistCancelar.addActionListener(e -> dispose());

        btnHistActualizar.addActionListener(e -> {
            txtMascotaBuscar.setText("Buscar por ID, nombre o raza...");
            txtMascotaBuscar.setForeground(java.awt.Color.GRAY);
            cargarMascotas();
            limpiarFormulario();
            tblHistRegistros.clearSelection();

            JOptionPane.showMessageDialog(this,
                    "La tabla se actualizó correctamente.");
        });
        btnBuscarMascota.addActionListener(e -> buscarMascotas());

        btnHistVerDetalle.addActionListener(e -> verDetalle());

        btnHistSiguiente.addActionListener(e -> siguienteRegistro());
        btnHistAnterior.addActionListener(e -> anteriorRegistro());
        btnHistEditar.addActionListener(e -> editarMascota());
        btnHistEliminar.addActionListener(e -> eliminarMascota());
    }

    private void cargarMascotas() {

        DefaultTableModel modelo
                = (DefaultTableModel) tblHistRegistros.getModel();

        modelo.setRowCount(0);

        List<Mascota> mascotas = sistema.getMascotas();

        for (Mascota mascota : mascotas) {

            if (mascota instanceof com.albergue.modelo.Perro) {

                com.albergue.modelo.Perro perro
                        = (com.albergue.modelo.Perro) mascota;

                Object[] fila = {
                    perro.getIdMascota(),
                    perro.getNombre(),
                    "Perro",
                    perro.getRaza(),
                    perro.getTamaño(),
                    perro.getEdad(),
                    perro.getGenero(),
                    perro.getPeso(),
                    perro.getEstadoAdopcion()
                };

                modelo.addRow(fila);
            }
        }
    }

    private void configurarFormulario() {

        txtIdMascota.setEditable(false);

        txtEspecie.setText("Perro");
        txtEspecie.setEditable(false);

        txtEstado.setText("Refugiado");
        txtEstado.setEditable(false);

        // Configurar sexo
        cmbSexo.removeAllItems();
        cmbSexo.addItem("Macho");
        cmbSexo.addItem("Hembra");
        cmbSexo.setSelectedIndex(-1);

        cmbNivelEntrenamiento.removeAllItems();
        cmbNivelEntrenamiento.addItem("Basico");
        cmbNivelEntrenamiento.addItem("Intermedio");
        cmbNivelEntrenamiento.addItem("Avanzado");
        cmbNivelEntrenamiento.setSelectedIndex(-1);
    }

    private void mostrarSiguienteId() {

        int mayorId = 0;

        for (Mascota mascota : sistema.getMascotas()) {

            String id = mascota.getIdMascota();

            if (id != null && id.matches("M\\d+")) {

                int numero = Integer.parseInt(id.substring(1));
                mayorId = Math.max(mayorId, numero);
            }
        }

        txtIdMascota.setText(
                String.format("M%03d", mayorId + 1)
        );
    }

    private void guardarMascota() {

        try {

            String nombre = txtNombre.getText().trim();
            String raza = txtRaza.getText().trim();
            String sexo = cmbSexo.getSelectedItem() == null? "": cmbSexo.getSelectedItem().toString();
            String tamano = txtTamano.getText().trim();

            if (nombre.isEmpty() || raza.isEmpty()
                    || sexo.isEmpty() || tamano.isEmpty()
                    || txtPeso.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Completa todos los campos.",
                        "Campos obligatorios",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int edad = (int) spnEdad.getValue();
            double peso = Double.parseDouble(txtPeso.getText().trim());

            // Validar que el tamaño sea un número
            double numeroTamano = Double.parseDouble(tamano);

            if (edad < 0 || edad > 30 || peso <= 0 || numeroTamano <= 0) {
                JOptionPane.showMessageDialog(this,
                        "La edad debe estar entre 0 y 30 años, y el peso y tamaño deben ser mayores que cero.");
                return;
            }

            if (cmbNivelEntrenamiento.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this,
                        "Selecciona un nivel de entrenamiento.");
                return;
            }

            String nivelEntrenamiento
                    = cmbNivelEntrenamiento.getSelectedItem().toString();

            boolean conviveConOtrosPerros;

            if (rbSiConvive.isSelected()) {
                conviveConOtrosPerros = true;
            } else if (rbNoConvive.isSelected()) {
                conviveConOtrosPerros = false;
            } else {
                JOptionPane.showMessageDialog(this,
                        "Selecciona si convive con otros perros.");
                return;
            }

            if (modoEdicion) {

                sistema.editarMascotaPorId(
                        idMascotaEnEdicion,
                        nombre,
                        raza,
                        edad,
                        sexo,
                        peso,
                        tamano,
                        nivelEntrenamiento,
                        conviveConOtrosPerros
                );

                JOptionPane.showMessageDialog(this,
                        "Mascota actualizada correctamente.");

            } else {

                sistema.registrarMascota(
                        nombre,
                        raza,
                        edad,
                        sexo,
                        peso,
                        tamano,
                        nivelEntrenamiento,
                        conviveConOtrosPerros
                );

                JOptionPane.showMessageDialog(this,
                        "Mascota registrada correctamente.");
            }

            limpiarFormulario();
            cargarMascotas();
            mostrarSiguienteId();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this,
                    "El peso y el tamaño deben contener valores numéricos.",
                    "Dato inválido",
                    JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Datos inválidos",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarFormulario() {

        txtNombre.setText("");
        txtRaza.setText("");
        cmbSexo.setSelectedIndex(-1);
        txtPeso.setText("");
        txtTamano.setText("");

        spnEdad.setValue(0);

        cmbNivelEntrenamiento.setSelectedIndex(-1);

        bgpConvivencia.clearSelection();

        txtEstado.setText("Refugiado");

        modoEdicion = false;
        idMascotaEnEdicion = null;

        mostrarSiguienteId();
        tblHistRegistros.clearSelection();
        btnHistGuardar.setText("Guardar");

    }

    private void buscarMascotas() {

        String texto = txtMascotaBuscar.getText().trim();

        if (texto.isEmpty() || texto.equals("Buscar por ID, nombre o raza...")) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa un dato para buscar.");
            return;
        }

        DefaultTableModel modelo
                = (DefaultTableModel) tblHistRegistros.getModel();

        modelo.setRowCount(0);

        for (Mascota mascota : sistema.getMascotas()) {

            if (mascota instanceof com.albergue.modelo.Perro) {

                com.albergue.modelo.Perro perro
                        = (com.albergue.modelo.Perro) mascota;

                boolean coincide
                        = perro.getIdMascota().equalsIgnoreCase(texto)
                        || perro.getNombre().equalsIgnoreCase(texto)
                        || perro.getRaza().equalsIgnoreCase(texto);

                if (coincide) {

                    Object[] fila = {
                        perro.getIdMascota(),
                        perro.getNombre(),
                        "Perro",
                        perro.getRaza(),
                        perro.getTamaño(),
                        perro.getEdad(),
                        perro.getGenero(),
                        perro.getPeso(),
                        perro.getEstadoAdopcion()
                    };

                    modelo.addRow(fila);
                }
            }
        }

        if (modelo.getRowCount() == 0) {

            JOptionPane.showMessageDialog(this,
                    "No se encontró ninguna coincidencia.");

        } else {

            tblHistRegistros.setRowSelectionInterval(0, 0);
        }
    }

    private void verDetalle() {

        int fila = tblHistRegistros.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una mascota de la tabla.");
            return;
        }

        String id = tblHistRegistros.getValueAt(fila, 0).toString();

        Mascota mascota = sistema.buscarMascotaPorId(id);

        if (mascota == null || !(mascota instanceof com.albergue.modelo.Perro)) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró la mascota seleccionada.");
            return;
        }

        com.albergue.modelo.Perro perro
                = (com.albergue.modelo.Perro) mascota;

        String detalle
                = "ID: " + perro.getIdMascota()
                + "\nNombre: " + perro.getNombre()
                + "\nEspecie: Perro"
                + "\nRaza: " + perro.getRaza()
                + "\nTamaño: " + perro.getTamaño()
                + "\nEdad: " + perro.getEdad()
                + "\nSexo: " + perro.getGenero()
                + "\nPeso: " + perro.getPeso()
                + "\nEstado: " + perro.getEstadoAdopcion()
                + "\nNivel de entrenamiento: " + perro.getNivelEntrenamiento()
                + "\nConvive con otros perros: "
                + (perro.isConviveConOtrosPerros() ? "Sí" : "No");

        JOptionPane.showMessageDialog(this,
                detalle,
                "Detalle de la mascota",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void editarMascota() {

        int fila = tblHistRegistros.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una mascota de la tabla.");
            return;
        }

        String id = tblHistRegistros.getValueAt(fila, 0).toString();

        Mascota mascota = sistema.buscarMascotaPorId(id);

        if (mascota == null || !(mascota instanceof com.albergue.modelo.Perro)) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró la mascota seleccionada.");
            return;
        }

        com.albergue.modelo.Perro perro
                = (com.albergue.modelo.Perro) mascota;

        // Verificar si la mascota ya fue adoptada.
        if ("Adoptado".equalsIgnoreCase(perro.getEstadoAdopcion())) {
            JOptionPane.showMessageDialog(this,
                    "No se pueden editar los datos de una mascota adoptada.",
                    "Edición no permitida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cargar los datos en el formulario.
        txtIdMascota.setText(perro.getIdMascota());
        txtNombre.setText(perro.getNombre());
        txtRaza.setText(perro.getRaza());
        cmbSexo.setSelectedItem(perro.getGenero());
        txtPeso.setText(String.valueOf(perro.getPeso()));
        txtTamano.setText(perro.getTamaño());

        spnEdad.setValue(perro.getEdad());

        cmbNivelEntrenamiento.setSelectedItem(
                perro.getNivelEntrenamiento());

        txtEstado.setText(perro.getEstadoAdopcion());

        if (perro.isConviveConOtrosPerros()) {
            rbSiConvive.setSelected(true);
        } else {
            rbNoConvive.setSelected(true);
        }

        modoEdicion = true;
        idMascotaEnEdicion = id;

        btnHistGuardar.setText("Actualizar");

        JOptionPane.showMessageDialog(this,
                "Modifica los datos y presiona Actualizar.");
    }

    private void eliminarMascota() {

        int fila = tblHistRegistros.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una mascota de la tabla.");
            return;
        }

        String id = tblHistRegistros.getValueAt(fila, 0).toString();
        String nombre = tblHistRegistros.getValueAt(fila, 1).toString();

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar a " + nombre + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            sistema.eliminarMascotaPorId(id);

            JOptionPane.showMessageDialog(this,
                    "Mascota eliminada correctamente.");

            cargarMascotas();
            limpiarFormulario();

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "No se pudo eliminar",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void siguienteRegistro() {

        int fila = tblHistRegistros.getSelectedRow();

        if (tblHistRegistros.getRowCount() == 0) {
            return;
        }

        if (fila == -1) {
            tblHistRegistros.setRowSelectionInterval(0, 0);
        } else if (fila < tblHistRegistros.getRowCount() - 1) {
            tblHistRegistros.setRowSelectionInterval(fila + 1, fila + 1);
        }
    }

    private void anteriorRegistro() {

        int fila = tblHistRegistros.getSelectedRow();

        if (fila > 0) {
            tblHistRegistros.setRowSelectionInterval(fila - 1, fila - 1);
        }
    }

    private void configurarPlaceholder() {

        String placeholder = "Buscar por ID, nombre o raza...";

        txtMascotaBuscar.setText(placeholder);
        txtMascotaBuscar.setForeground(java.awt.Color.GRAY);

        txtMascotaBuscar.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {

                if (txtMascotaBuscar.getText().equals(placeholder)) {
                    txtMascotaBuscar.setText("");
                    txtMascotaBuscar.setForeground(
                            new java.awt.Color(38, 52, 69)
                    );
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {

                if (txtMascotaBuscar.getText().trim().isEmpty()) {
                    txtMascotaBuscar.setText(placeholder);
                    txtMascotaBuscar.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgpConvivencia = new javax.swing.ButtonGroup();
        lblHistTitulo = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        pnlHistFormulario = new javax.swing.JPanel();
        lblIdMascota = new javax.swing.JLabel();
        lblRazaMascota = new javax.swing.JLabel();
        lblNombreMascota = new javax.swing.JLabel();
        lblPesoMascota = new javax.swing.JLabel();
        lblEstadoMascota = new javax.swing.JLabel();
        txtRaza = new javax.swing.JTextField();
        txtPeso = new javax.swing.JTextField();
        btnHistGuardar = new javax.swing.JButton();
        btnHistGuardar1 = new javax.swing.JButton();
        btnHistCancelar = new javax.swing.JButton();
        lblEdadMascota = new javax.swing.JLabel();
        cmbNivelEntrenamiento = new javax.swing.JComboBox<>();
        txtNombre = new javax.swing.JTextField();
        lblEspecieMascota = new javax.swing.JLabel();
        lblSexoMascota = new javax.swing.JLabel();
        txtIdMascota = new javax.swing.JTextField();
        txtEspecie = new javax.swing.JTextField();
        spnEdad = new javax.swing.JSpinner();
        lblEntrenamientoMascota = new javax.swing.JLabel();
        lblConvivenciaMascota = new javax.swing.JLabel();
        rbSiConvive = new javax.swing.JRadioButton();
        rbNoConvive = new javax.swing.JRadioButton();
        lblTamanoMascota = new javax.swing.JLabel();
        txtTamano = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        cmbSexo = new javax.swing.JComboBox<>();
        pnlHistTabla = new javax.swing.JPanel();
        txtMascotaBuscar = new javax.swing.JTextField();
        btnBuscarMascota = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistRegistros = new javax.swing.JTable();
        btnHistVerDetalle = new javax.swing.JButton();
        btnHistEditar = new javax.swing.JButton();
        btnHistActualizar = new javax.swing.JButton();
        btnHistEliminar = new javax.swing.JButton();
        btnHistSiguiente = new javax.swing.JButton();
        btnHistAnterior = new javax.swing.JButton();
        lblBuscar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Registro de Mascotas");

        lblHistTitulo.setBackground(new java.awt.Color(36, 75, 112));
        lblHistTitulo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblHistTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblHistTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHistTitulo.setText("Gestión de Registro de Mascota");
        lblHistTitulo.setAlignmentX(15.0F);
        lblHistTitulo.setAlignmentY(35.0F);
        lblHistTitulo.setOpaque(true);
        lblHistTitulo.setPreferredSize(new java.awt.Dimension(830, 35));

        lblLogo.setText("jLabel2");
        lblLogo.setPreferredSize(new java.awt.Dimension(65, 65));

        pnlHistFormulario.setBackground(new java.awt.Color(255, 255, 255));
        pnlHistFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registrar Mascota", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnlHistFormulario.setPreferredSize(new java.awt.Dimension(330, 420));

        lblIdMascota.setBackground(new java.awt.Color(255, 255, 255));
        lblIdMascota.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblIdMascota.setForeground(new java.awt.Color(38, 52, 69));
        lblIdMascota.setText("ID:");

        lblRazaMascota.setBackground(new java.awt.Color(255, 255, 255));
        lblRazaMascota.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblRazaMascota.setForeground(new java.awt.Color(38, 52, 69));
        lblRazaMascota.setText("Raza:");

        lblNombreMascota.setBackground(new java.awt.Color(255, 255, 255));
        lblNombreMascota.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblNombreMascota.setForeground(new java.awt.Color(38, 52, 69));
        lblNombreMascota.setText("Nombre:");

        lblPesoMascota.setBackground(new java.awt.Color(255, 255, 255));
        lblPesoMascota.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblPesoMascota.setForeground(new java.awt.Color(38, 52, 69));
        lblPesoMascota.setText("Peso:");

        lblEstadoMascota.setBackground(new java.awt.Color(255, 255, 255));
        lblEstadoMascota.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblEstadoMascota.setForeground(new java.awt.Color(38, 52, 69));
        lblEstadoMascota.setText("Estado:");

        txtRaza.setForeground(new java.awt.Color(38, 52, 69));

        txtPeso.setForeground(new java.awt.Color(38, 52, 69));

        btnHistGuardar.setBackground(new java.awt.Color(36, 75, 112));
        btnHistGuardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHistGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnHistGuardar.setText("Guardar");
        btnHistGuardar.setFocusable(false);

        btnHistGuardar1.setBackground(new java.awt.Color(229, 231, 235));
        btnHistGuardar1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHistGuardar1.setForeground(new java.awt.Color(38, 52, 69));
        btnHistGuardar1.setText("Limpiar");
        btnHistGuardar1.setFocusable(false);

        btnHistCancelar.setBackground(new java.awt.Color(229, 231, 235));
        btnHistCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHistCancelar.setForeground(new java.awt.Color(38, 52, 69));
        btnHistCancelar.setText("Cancelar");
        btnHistCancelar.setFocusable(false);

        lblEdadMascota.setBackground(new java.awt.Color(255, 255, 255));
        lblEdadMascota.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblEdadMascota.setForeground(new java.awt.Color(38, 52, 69));
        lblEdadMascota.setText("Edad:");

        cmbNivelEntrenamiento.setForeground(new java.awt.Color(38, 52, 69));
        cmbNivelEntrenamiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtNombre.setForeground(new java.awt.Color(38, 52, 69));

        lblEspecieMascota.setBackground(new java.awt.Color(255, 255, 255));
        lblEspecieMascota.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblEspecieMascota.setForeground(new java.awt.Color(38, 52, 69));
        lblEspecieMascota.setText("Especie:");

        lblSexoMascota.setBackground(new java.awt.Color(255, 255, 255));
        lblSexoMascota.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblSexoMascota.setForeground(new java.awt.Color(38, 52, 69));
        lblSexoMascota.setText("Sexo:");

        txtIdMascota.setEditable(false);
        txtIdMascota.setForeground(new java.awt.Color(38, 52, 69));

        txtEspecie.setEditable(false);
        txtEspecie.setForeground(new java.awt.Color(38, 52, 69));

        lblEntrenamientoMascota.setBackground(new java.awt.Color(255, 255, 255));
        lblEntrenamientoMascota.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblEntrenamientoMascota.setForeground(new java.awt.Color(38, 52, 69));
        lblEntrenamientoMascota.setText("Nivel de entrenamiento:");

        lblConvivenciaMascota.setBackground(new java.awt.Color(255, 255, 255));
        lblConvivenciaMascota.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblConvivenciaMascota.setForeground(new java.awt.Color(38, 52, 69));
        lblConvivenciaMascota.setText("Convivencia con perros:");

        bgpConvivencia.add(rbSiConvive);
        rbSiConvive.setText("Si");

        bgpConvivencia.add(rbNoConvive);
        rbNoConvive.setText("No");

        lblTamanoMascota.setBackground(new java.awt.Color(255, 255, 255));
        lblTamanoMascota.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTamanoMascota.setForeground(new java.awt.Color(38, 52, 69));
        lblTamanoMascota.setText("Tamaño:");

        txtTamano.setForeground(new java.awt.Color(38, 52, 69));

        txtEstado.setEditable(false);
        txtEstado.setForeground(new java.awt.Color(38, 52, 69));

        cmbSexo.setForeground(new java.awt.Color(38, 52, 69));
        cmbSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlHistFormularioLayout = new javax.swing.GroupLayout(pnlHistFormulario);
        pnlHistFormulario.setLayout(pnlHistFormularioLayout);
        pnlHistFormularioLayout.setHorizontalGroup(
            pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistFormularioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHistFormularioLayout.createSequentialGroup()
                        .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlHistFormularioLayout.createSequentialGroup()
                                .addComponent(lblIdMascota)
                                .addGap(18, 18, 18)
                                .addComponent(txtIdMascota))
                            .addGroup(pnlHistFormularioLayout.createSequentialGroup()
                                .addComponent(lblNombreMascota)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre))
                            .addGroup(pnlHistFormularioLayout.createSequentialGroup()
                                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlHistFormularioLayout.createSequentialGroup()
                                        .addComponent(lblEntrenamientoMascota)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbNivelEntrenamiento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlHistFormularioLayout.createSequentialGroup()
                                        .addComponent(btnHistGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(btnHistGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnHistCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlHistFormularioLayout.createSequentialGroup()
                                        .addComponent(lblRazaMascota)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtRaza, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlHistFormularioLayout.createSequentialGroup()
                                        .addComponent(lblPesoMascota)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlHistFormularioLayout.createSequentialGroup()
                                        .addComponent(lblEstadoMascota)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEstado)
                                        .addGap(11, 11, 11))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlHistFormularioLayout.createSequentialGroup()
                                        .addComponent(lblEspecieMascota)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(pnlHistFormularioLayout.createSequentialGroup()
                        .addComponent(lblSexoMascota)
                        .addGap(18, 18, 18)
                        .addComponent(cmbSexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(134, 134, 134))
                    .addGroup(pnlHistFormularioLayout.createSequentialGroup()
                        .addComponent(lblEdadMascota)
                        .addGap(18, 18, 18)
                        .addComponent(spnEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlHistFormularioLayout.createSequentialGroup()
                        .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlHistFormularioLayout.createSequentialGroup()
                                .addComponent(lblConvivenciaMascota)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbSiConvive)
                                .addGap(28, 28, 28)
                                .addComponent(rbNoConvive))
                            .addGroup(pnlHistFormularioLayout.createSequentialGroup()
                                .addComponent(lblTamanoMascota)
                                .addGap(18, 18, 18)
                                .addComponent(txtTamano, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlHistFormularioLayout.setVerticalGroup(
            pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistFormularioLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdMascota)
                    .addComponent(txtIdMascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreMascota)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEspecieMascota)
                    .addComponent(txtEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRazaMascota))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTamano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTamanoMascota))
                .addGap(12, 12, 12)
                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEdadMascota)
                    .addComponent(spnEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSexoMascota)
                    .addComponent(cmbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstadoMascota)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPesoMascota)
                    .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEntrenamientoMascota)
                    .addComponent(cmbNivelEntrenamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConvivenciaMascota)
                    .addComponent(rbSiConvive)
                    .addComponent(rbNoConvive))
                .addGap(18, 18, 18)
                .addGroup(pnlHistFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHistGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHistGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHistCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlHistTabla.setBackground(new java.awt.Color(255, 255, 255));
        pnlHistTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Historial de registros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnlHistTabla.setPreferredSize(new java.awt.Dimension(490, 420));

        btnBuscarMascota.setBackground(new java.awt.Color(36, 75, 112));
        btnBuscarMascota.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuscarMascota.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarMascota.setText("Buscar");
        btnBuscarMascota.setFocusable(false);

        tblHistRegistros.setForeground(new java.awt.Color(38, 52, 69));
        tblHistRegistros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Especie", "Raza", "Tamaño", "Edad", "Sexo", "Peso", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblHistRegistros.setSelectionBackground(new java.awt.Color(220, 232, 243));
        tblHistRegistros.setSelectionForeground(new java.awt.Color(36, 75, 112));
        jScrollPane1.setViewportView(tblHistRegistros);

        btnHistVerDetalle.setBackground(new java.awt.Color(36, 75, 112));
        btnHistVerDetalle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHistVerDetalle.setForeground(new java.awt.Color(255, 255, 255));
        btnHistVerDetalle.setText("Ver Detalle");
        btnHistVerDetalle.setFocusable(false);

        btnHistEditar.setBackground(new java.awt.Color(36, 75, 112));
        btnHistEditar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHistEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnHistEditar.setText("Editar");
        btnHistEditar.setFocusable(false);

        btnHistActualizar.setBackground(new java.awt.Color(36, 75, 112));
        btnHistActualizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHistActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnHistActualizar.setText("Actualizar");
        btnHistActualizar.setFocusable(false);

        btnHistEliminar.setBackground(new java.awt.Color(220, 53, 69));
        btnHistEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHistEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnHistEliminar.setText("Eliminar");
        btnHistEliminar.setFocusable(false);

        btnHistSiguiente.setBackground(new java.awt.Color(229, 231, 235));
        btnHistSiguiente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHistSiguiente.setForeground(new java.awt.Color(38, 52, 69));
        btnHistSiguiente.setText("Siguiente");
        btnHistSiguiente.setFocusable(false);

        btnHistAnterior.setBackground(new java.awt.Color(229, 231, 235));
        btnHistAnterior.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHistAnterior.setForeground(new java.awt.Color(38, 52, 69));
        btnHistAnterior.setText("Anterior");
        btnHistAnterior.setFocusable(false);

        lblBuscar.setText("Buscar:");

        javax.swing.GroupLayout pnlHistTablaLayout = new javax.swing.GroupLayout(pnlHistTabla);
        pnlHistTabla.setLayout(pnlHistTablaLayout);
        pnlHistTablaLayout.setHorizontalGroup(
            pnlHistTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHistTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHistTablaLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(lblBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMascotaBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(62, Short.MAX_VALUE))
                    .addGroup(pnlHistTablaLayout.createSequentialGroup()
                        .addGroup(pnlHistTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(pnlHistTablaLayout.createSequentialGroup()
                                .addGroup(pnlHistTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnHistAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlHistTablaLayout.createSequentialGroup()
                                        .addComponent(btnHistVerDetalle)
                                        .addGap(53, 53, 53)
                                        .addComponent(btnHistEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnHistEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlHistTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnHistActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnHistSiguiente, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addContainerGap())))
        );
        pnlHistTablaLayout.setVerticalGroup(
            pnlHistTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHistTablaLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(pnlHistTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMascotaBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscar))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlHistTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHistSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHistAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlHistTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHistVerDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHistEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHistActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHistEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(pnlHistFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(pnlHistTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHistTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHistTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHistFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                    .addComponent(pnlHistTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {

            SistemaAlbergue sistema = new SistemaAlbergue();

            GestionRegistroMascota dialog
                    = new GestionRegistroMascota(
                            new javax.swing.JFrame(),
                            true,
                            sistema
                    );

            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });

            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgpConvivencia;
    private javax.swing.JButton btnBuscarMascota;
    private javax.swing.JButton btnHistActualizar;
    private javax.swing.JButton btnHistAnterior;
    private javax.swing.JButton btnHistCancelar;
    private javax.swing.JButton btnHistEditar;
    private javax.swing.JButton btnHistEliminar;
    private javax.swing.JButton btnHistGuardar;
    private javax.swing.JButton btnHistGuardar1;
    private javax.swing.JButton btnHistSiguiente;
    private javax.swing.JButton btnHistVerDetalle;
    private javax.swing.JComboBox<String> cmbNivelEntrenamiento;
    private javax.swing.JComboBox<String> cmbSexo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblConvivenciaMascota;
    private javax.swing.JLabel lblEdadMascota;
    private javax.swing.JLabel lblEntrenamientoMascota;
    private javax.swing.JLabel lblEspecieMascota;
    private javax.swing.JLabel lblEstadoMascota;
    private javax.swing.JLabel lblHistTitulo;
    private javax.swing.JLabel lblIdMascota;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNombreMascota;
    private javax.swing.JLabel lblPesoMascota;
    private javax.swing.JLabel lblRazaMascota;
    private javax.swing.JLabel lblSexoMascota;
    private javax.swing.JLabel lblTamanoMascota;
    private javax.swing.JPanel pnlHistFormulario;
    private javax.swing.JPanel pnlHistTabla;
    private javax.swing.JRadioButton rbNoConvive;
    private javax.swing.JRadioButton rbSiConvive;
    private javax.swing.JSpinner spnEdad;
    private javax.swing.JTable tblHistRegistros;
    private javax.swing.JTextField txtEspecie;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtIdMascota;
    private javax.swing.JTextField txtMascotaBuscar;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPeso;
    private javax.swing.JTextField txtRaza;
    private javax.swing.JTextField txtTamano;
    // End of variables declaration//GEN-END:variables
}
