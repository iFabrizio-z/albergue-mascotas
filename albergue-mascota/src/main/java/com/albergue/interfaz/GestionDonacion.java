/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.albergue.interfaz;


import com.albergue.modelo.Comida;
import com.albergue.modelo.Donacion;
import com.albergue.modelo.Efectivo;
import com.albergue.persistencia.PersistenciaDatos;
import com.albergue.sistema.SistemaAlbergue;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;



/**
 *
 * @author Fabricioo
 */

public class GestionDonacion extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GestionDonacion.class.getName());
    private SistemaAlbergue sistema;
    private DefaultTableModel modeloEfectivo;
    private DefaultTableModel modeloAlimento;
    private TableRowSorter<DefaultTableModel> sorterEfectivo;
    private TableRowSorter<DefaultTableModel> sorterAlimento;
    private int idSeleccionadoGlobal = -1;
    private String tipoTablaSeleccionada = ""; // "Efectivo" o "Alimento"

    /**
     * Creates new form GestionDonacion
     */
    public GestionDonacion(java.awt.Frame parent, boolean modal) {
        this(parent, modal, cargarSistema());
    }

    public GestionDonacion(java.awt.Frame parent, boolean modal, SistemaAlbergue sistema) {
        super(parent, modal);
        this.sistema = sistema;
        initComponents();
        setLocationRelativeTo(parent);
        configurarVentana();
        configurarTablas();
        cargarDonaciones();
        configurarBuscadores();
        cargarLogo();

        // Listener para capturar la selección de la tabla Efectivo sin perder el foco al hacer clic en botones
        tblEfectivo.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblEfectivo.getSelectedRow() != -1) {
                int filaReal = tblEfectivo.convertRowIndexToModel(tblEfectivo.getSelectedRow());
                try {
                    idSeleccionadoGlobal = Integer.parseInt(modeloEfectivo.getValueAt(filaReal, 0).toString());
                    tipoTablaSeleccionada = "Efectivo";
                    tblAlimento.clearSelection(); // Deselecciona la otra tabla
                } catch (Exception ex) {
                    idSeleccionadoGlobal = -1;
                }
            }
        });

        // Listener para capturar la selección de la tabla Alimento
        tblAlimento.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblAlimento.getSelectedRow() != -1) {
                int filaReal = tblAlimento.convertRowIndexToModel(tblAlimento.getSelectedRow());
                try {
                    idSeleccionadoGlobal = Integer.parseInt(modeloAlimento.getValueAt(filaReal, 0).toString());
                    tipoTablaSeleccionada = "Alimento";
                    tblEfectivo.clearSelection(); // Deselecciona la otra tabla
                } catch (Exception ex) {
                    idSeleccionadoGlobal = -1;
                }
            }
        });
        
        // Establecer fecha actual por defecto en el campo de texto
        txtFecha.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    private static SistemaAlbergue cargarSistema() {
        try {
            return PersistenciaDatos.cargarDatos();
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error al cargar los datos del albergue", ex);
            return new SistemaAlbergue();
        }
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

    private void configurarVentana() {
        setTitle("Gestión de Donaciones");
    }

    private void configurarTablas() {
        modeloEfectivo = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Código", "Donante", "Fecha", "Monto (S/.)"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblEfectivo.setModel(modeloEfectivo);
        sorterEfectivo = new TableRowSorter<>(modeloEfectivo);
        tblEfectivo.setRowSorter(sorterEfectivo);

        modeloAlimento = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Código", "Donante", "Fecha", "Cantidad (kg)"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblAlimento.setModel(modeloAlimento);
        sorterAlimento = new TableRowSorter<>(modeloAlimento);
        tblAlimento.setRowSorter(sorterAlimento);
    }

    private void configurarBuscadores() {
        txtBuscarEfectivo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrarEfectivo(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrarEfectivo(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrarEfectivo(); }
        });

        txtBuscarAlimento.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrarAlimento(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrarAlimento(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrarAlimento(); }
        });
    }

    private void filtrarEfectivo() {
        String texto = txtBuscarEfectivo.getText().trim();
        if (texto.isEmpty()) {
            sorterEfectivo.setRowFilter(null);
        } else {
            sorterEfectivo.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto)));
        }
    }

    private void filtrarAlimento() {
        String texto = txtBuscarAlimento.getText().trim();
        if (texto.isEmpty()) {
            sorterAlimento.setRowFilter(null);
        } else {
            sorterAlimento.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto)));
        }
    }

    private void cargarDonaciones() {
        if (sistema == null) return;

        modeloEfectivo.setRowCount(0);
        modeloAlimento.setRowCount(0);

        for (Donacion d : sistema.getDonaciones()) {
            if (d instanceof Efectivo ef) {
                modeloEfectivo.addRow(new Object[]{
                    ef.getIdDonacion(),
                    ef.getDonante(),
                    ef.getFecha(),
                    String.format("S/. %.2f", ef.getMontoEfectivo())
                });
            } else if (d instanceof Comida co) {
                modeloAlimento.addRow(new Object[]{
                    co.getIdDonacion(),
                    co.getDonante(),
                    co.getFecha(),
                    co.getCantidadComidaKg() + " kg"
                });
            }
        }

        lblTotalEfectivo.setText(String.format("Total: S/. %.2f", sistema.obtenerTotalEfectivo()));
        lblTotalAlimento.setText(String.format("Total: %.1f kg", sistema.obtenerTotalComida()));
    }

    private void limpiarFormulario() {
        txtDonante.setText("");
        txtMonto.setValue(0.0);
        txtDescripcion.setText("");
        txtFecha.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        if (cmbTipo.getItemCount() > 0) {
            cmbTipo.setSelectedIndex(0);
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        pnlCabecera = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        pnlRegistro = new javax.swing.JPanel();
        pnlTituloRegistro = new javax.swing.JPanel();
        lblTituloRegistro = new javax.swing.JLabel();
        lblDonante = new javax.swing.JLabel();
        txtDonante = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        lblMonto = new javax.swing.JLabel();
        txtMonto = new javax.swing.JSpinner();
        lblDescripcion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        btnGuardar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        pnlRegistros = new javax.swing.JPanel();
        lblEfectivo = new javax.swing.JLabel();
        txtBuscarEfectivo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEfectivo = new javax.swing.JTable();
        lblTotalEfectivo = new javax.swing.JLabel();
        lblAlimento = new javax.swing.JLabel();
        txtBuscarAlimento = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblAlimento = new javax.swing.JTable();
        lblTotalAlimento = new javax.swing.JLabel();
        btnVerDetalle = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlPrincipal.setBackground(new java.awt.Color(238, 243, 250));
        pnlPrincipal.setName("pnlPrincipal"); // NOI18N

        pnlCabecera.setBackground(new java.awt.Color(220, 230, 245));
        pnlCabecera.setName("pnlCabecera"); // NOI18N

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTitulo.setText("Registra y consulta las donaciones recibidas");
        lblTitulo.setName("lblSubtitulo"); // NOI18N

        lblSubtitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblSubtitulo.setText("Gestión de Donaciones");
        lblSubtitulo.setName("lblTitulo"); // NOI18N

        lblLogo.setPreferredSize(new java.awt.Dimension(65, 65));

        javax.swing.GroupLayout pnlCabeceraLayout = new javax.swing.GroupLayout(pnlCabecera);
        pnlCabecera.setLayout(pnlCabeceraLayout);
        pnlCabeceraLayout.setHorizontalGroup(
            pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCabeceraLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSubtitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitulo))
                .addGap(564, 564, 564))
        );
        pnlCabeceraLayout.setVerticalGroup(
            pnlCabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCabeceraLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblSubtitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitulo)
                .addGap(47, 47, 47))
            .addGroup(pnlCabeceraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlRegistro.setBackground(new java.awt.Color(247, 250, 252));
        pnlRegistro.setForeground(new java.awt.Color(247, 250, 252));
        pnlRegistro.setName("pnlRegistro"); // NOI18N

        pnlTituloRegistro.setName("pnlTituloRegistro"); // NOI18N

        lblTituloRegistro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTituloRegistro.setText("Registrar Donación");
        lblTituloRegistro.setName("lblTituloRegistro"); // NOI18N

        javax.swing.GroupLayout pnlTituloRegistroLayout = new javax.swing.GroupLayout(pnlTituloRegistro);
        pnlTituloRegistro.setLayout(pnlTituloRegistroLayout);
        pnlTituloRegistroLayout.setHorizontalGroup(
            pnlTituloRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTituloRegistroLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblTituloRegistro)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTituloRegistroLayout.setVerticalGroup(
            pnlTituloRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTituloRegistroLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTituloRegistro)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        lblDonante.setText("Donante:");
        lblDonante.setName("lblDonante"); // NOI18N

        txtDonante.setName("JTextField"); // NOI18N
        txtDonante.addActionListener(this::txtDonanteActionPerformed);

        lblTipo.setText("Tipo:");
        lblTipo.setName("lblTipo"); // NOI18N

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alimento", "Efectivo" }));
        cmbTipo.setName("cmbTipo"); // NOI18N
        cmbTipo.addActionListener(this::cmbTipoActionPerformed);

        lblFecha.setText("Fecha:");
        lblFecha.setName("lblFecha"); // NOI18N

        txtFecha.setText("14/04/2025");
        txtFecha.setName("txtFecha"); // NOI18N
        txtFecha.addActionListener(this::txtFechaActionPerformed);

        lblMonto.setText("Monto:");
        lblMonto.setName("lblMonto"); // NOI18N

        txtMonto.setName("txtMonto"); // NOI18N

        lblDescripcion.setText("Descripción:");
        lblDescripcion.setName("lblDescripcion"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(3);
        txtDescripcion.setName("txtDescripcion"); // NOI18N
        jScrollPane1.setViewportView(txtDescripcion);

        btnGuardar.setBackground(new java.awt.Color(24, 86, 158));
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setName("btnGuardar"); // NOI18N
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnLimpiar.setBackground(new java.awt.Color(226, 232, 240));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setName("btnLimpiar"); // NOI18N
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        btnCancelar.setBackground(new java.awt.Color(226, 232, 240));
        btnCancelar.setText("Cancelar");
        btnCancelar.setName("btnCancelar"); // NOI18N
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        javax.swing.GroupLayout pnlRegistroLayout = new javax.swing.GroupLayout(pnlRegistro);
        pnlRegistro.setLayout(pnlRegistroLayout);
        pnlRegistroLayout.setHorizontalGroup(
            pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTituloRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlRegistroLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegistroLayout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addGap(59, 59, 59)
                        .addComponent(btnLimpiar))
                    .addGroup(pnlRegistroLayout.createSequentialGroup()
                        .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDonante)
                            .addComponent(lblTipo)
                            .addComponent(lblFecha)
                            .addComponent(lblMonto))
                        .addGap(42, 42, 42)
                        .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDonante)
                                .addComponent(cmbTipo, 0, 194, Short.MAX_VALUE))
                            .addGroup(pnlRegistroLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnCancelar)
                                    .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(lblDescripcion))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        pnlRegistroLayout.setVerticalGroup(
            pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTituloRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDonante)
                    .addComponent(txtDonante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipo)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFecha)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMonto)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDescripcion)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnCancelar))
                .addGap(14, 14, 14))
        );

        pnlRegistros.setBackground(new java.awt.Color(247, 250, 252));
        pnlRegistros.setName("pnlRegistros"); // NOI18N

        lblEfectivo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEfectivo.setText("Donaciones en Efectivo (S/.)");
        lblEfectivo.setName("lblEfectivo"); // NOI18N

        txtBuscarEfectivo.setName("txtBuscarEfectivo"); // NOI18N
        txtBuscarEfectivo.addActionListener(this::txtBuscarEfectivoActionPerformed);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tblEfectivo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Donante", "Fecha", "Monto (S/.)"
            }
        ));
        tblEfectivo.setName("tblEfectivo"); // NOI18N
        jScrollPane2.setViewportView(tblEfectivo);

        lblTotalEfectivo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotalEfectivo.setText("Total: S/. 0.00");
        lblTotalEfectivo.setName("lblTotalEfectivo"); // NOI18N

        lblAlimento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAlimento.setText("Donaciones de Alimento (kg)");
        lblAlimento.setName("lblAlimento"); // NOI18N

        txtBuscarAlimento.setName("txtBuscarAlimento"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tblAlimento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Donante", "Fecha", "Cantidad (kg)"
            }
        ));
        tblAlimento.setName("tblAlimento"); // NOI18N
        jScrollPane3.setViewportView(tblAlimento);

        lblTotalAlimento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotalAlimento.setText("Total: 0.0 kg");
        lblTotalAlimento.setName("lblTotalAlimento"); // NOI18N

        btnVerDetalle.setBackground(new java.awt.Color(24, 86, 158));
        btnVerDetalle.setForeground(new java.awt.Color(255, 255, 255));
        btnVerDetalle.setText("Ver Detalle");
        btnVerDetalle.setName("btnVerDetalle"); // NOI18N
        btnVerDetalle.addActionListener(this::btnVerDetalleActionPerformed);

        btnEliminar.setBackground(new java.awt.Color(197, 48, 48));
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setName("btnEliminar"); // NOI18N
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnActualizar.setBackground(new java.awt.Color(24, 86, 158));
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.setName("btnActualizar"); // NOI18N
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        javax.swing.GroupLayout pnlRegistrosLayout = new javax.swing.GroupLayout(pnlRegistros);
        pnlRegistros.setLayout(pnlRegistrosLayout);
        pnlRegistrosLayout.setHorizontalGroup(
            pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistrosLayout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addGroup(pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalAlimento))
                .addGap(107, 107, 107))
            .addGroup(pnlRegistrosLayout.createSequentialGroup()
                .addGroup(pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegistrosLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTotalEfectivo, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlRegistrosLayout.createSequentialGroup()
                                        .addComponent(lblEfectivo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtBuscarEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pnlRegistrosLayout.createSequentialGroup()
                                .addComponent(lblAlimento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscarAlimento, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlRegistrosLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(btnVerDetalle)
                        .addGap(73, 73, 73)
                        .addComponent(btnEliminar)
                        .addGap(70, 70, 70)
                        .addComponent(btnActualizar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlRegistrosLayout.setVerticalGroup(
            pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEfectivo)
                    .addComponent(txtBuscarEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalEfectivo)
                .addGroup(pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegistrosLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lblAlimento))
                    .addGroup(pnlRegistrosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscarAlimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalAlimento)
                .addGap(18, 18, 18)
                .addGroup(pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVerDetalle)
                    .addComponent(btnEliminar)
                    .addComponent(btnActualizar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCabecera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(pnlRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlCabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addComponent(pnlRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addComponent(pnlRegistros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 969, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            String donante = txtDonante.getText().trim();
            String tipo = cmbTipo.getSelectedItem().toString();
            double valor = Double.parseDouble(txtMonto.getValue().toString());
            
            if (donante.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete el nombre del donante.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "El valor o cantidad debe ser mayor a 0.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (tipo.equalsIgnoreCase("Efectivo")) {
                sistema.registrarDonacionEfectivo(donante, valor);
            } else {
                sistema.registrarDonacionComida(donante, valor);
            }

            PersistenciaDatos.guardarDatos(sistema);
            cargarDonaciones();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "¡Donación guardada con éxito!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar: Verifique que el monto sea numérico.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtDonanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonanteActionPerformed

    private void txtBuscarEfectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarEfectivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarEfectivoActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarFormulario();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnVerDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetalleActionPerformed
        // TODO add your handling code here:
    int filaEf = tblEfectivo.getSelectedRow();
        int filaAl = tblAlimento.getSelectedRow();

        if (filaEf != -1) {
            int filaReal = tblEfectivo.convertRowIndexToModel(filaEf);
            int id = Integer.parseInt(modeloEfectivo.getValueAt(filaReal, 0).toString());
            for (Donacion d : sistema.getDonaciones()) {
                if (d.getIdDonacion() == id && d instanceof Efectivo ef) {
                    JOptionPane.showMessageDialog(this, "=== DETALLE DE EFECTIVO ===\n" +
                            "Código: " + ef.getIdDonacion() + "\n" +
                            "Donante: " + ef.getDonante() + "\n" +
                            "Fecha: " + ef.getFecha() + "\n" +
                            "Monto: S/ " + ef.getMontoEfectivo(), "Detalle", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        } else if (filaAl != -1) {
            int filaReal = tblAlimento.convertRowIndexToModel(filaAl);
            int id = Integer.parseInt(modeloAlimento.getValueAt(filaReal, 0).toString());
            for (Donacion d : sistema.getDonaciones()) {
                if (d.getIdDonacion() == id && d instanceof Comida co) {
                    JOptionPane.showMessageDialog(this, "=== DETALLE DE COMIDA ===\n" +
                            "Código: " + co.getIdDonacion() + "\n" +
                            "Donante: " + co.getDonante() + "\n" +
                            "Fecha: " + co.getFecha() + "\n" +
                            "Cantidad: " + co.getCantidadComidaKg() + " kg", "Detalle", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de alguna tabla para ver su detalle.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnVerDetalleActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
 int filaEf = tblEfectivo.getSelectedRow();
        int filaAl = tblAlimento.getSelectedRow();
        int idSeleccionado = -1;

        System.out.println("=== INTENTO DE ELIMINACIÓN ===");
        System.out.println("Fila seleccionada en Efectivo (vista): " + filaEf);
        System.out.println("Fila seleccionada en Alimento (vista): " + filaAl);

        if (filaEf != -1) {
            try {
                int filaReal = tblEfectivo.convertRowIndexToModel(filaEf);
                idSeleccionado = Integer.parseInt(modeloEfectivo.getValueAt(filaReal, 0).toString());
                System.out.println("ID detectado en Efectivo (Modelo fila " + filaReal + "): " + idSeleccionado);
            } catch (Exception e) {
                System.out.println("Error al leer ID de Efectivo: " + e.getMessage());
            }
        } else if (filaAl != -1) {
            try {
                int filaReal = tblAlimento.convertRowIndexToModel(filaAl);
                idSeleccionado = Integer.parseInt(modeloAlimento.getValueAt(filaReal, 0).toString());
                System.out.println("ID detectado en Alimento (Modelo fila " + filaReal + "): " + idSeleccionado);
            } catch (Exception e) {
                System.out.println("Error al leer ID de Alimento: " + e.getMessage());
            }
        }

        if (idSeleccionado != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(
                this, 
                "¿Está seguro de eliminar la donación con código " + idSeleccionado + "?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                final int idEliminar = idSeleccionado;
                
                // Ver cuántas donaciones hay antes de borrar
                System.out.println("Total donaciones antes de borrar: " + sistema.getDonaciones().size());
                
                sistema.eliminarDonacion(idEliminar);
                boolean eliminado = true;
                
                System.out.println("¿Se removió de la lista?: " + eliminado);
                System.out.println("Total donaciones después de borrar: " + sistema.getDonaciones().size());
                
                if (eliminado) {
                    sistema.setDatosModificados(true);
                    PersistenciaDatos.guardarDatos(sistema);
                    cargarDonaciones();
                    JOptionPane.showMessageDialog(this, "Donación eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el registro con ID " + idEliminar + " en la lista del sistema.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, haga clic en una fila de la tabla de Efectivo o de Alimentos antes de presionar Eliminar.", "Selección requerida", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        txtBuscarEfectivo.setText("");
        txtBuscarAlimento.setText("");
        cargarDonaciones();
        JOptionPane.showMessageDialog(this, "Tablas actualizadas correctamente.");
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

     /**
     * @param args the command line arguments
     */
     public static void main(String args[]) {
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

        java.awt.EventQueue.invokeLater(() -> {
            SistemaAlbergue sistema = PersistenciaDatos.cargarDatos();
            GestionDonacion dialog = new GestionDonacion(new javax.swing.JFrame(), true, sistema);
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
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnVerDetalle;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAlimento;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblDonante;
    private javax.swing.JLabel lblEfectivo;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloRegistro;
    private javax.swing.JLabel lblTotalAlimento;
    private javax.swing.JLabel lblTotalEfectivo;
    private javax.swing.JPanel pnlCabecera;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlRegistro;
    private javax.swing.JPanel pnlRegistros;
    private javax.swing.JPanel pnlTituloRegistro;
    private javax.swing.JTable tblAlimento;
    private javax.swing.JTable tblEfectivo;
    private javax.swing.JTextField txtBuscarAlimento;
    private javax.swing.JTextField txtBuscarEfectivo;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtDonante;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JSpinner txtMonto;
    // End of variables declaration//GEN-END:variables
}