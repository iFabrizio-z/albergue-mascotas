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
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblEfectivo.setModel(modeloEfectivo);
        sorterEfectivo = new TableRowSorter<>(modeloEfectivo);
        tblEfectivo.setRowSorter(sorterEfectivo);

        modeloAlimento = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código", "Donante", "Fecha", "Cantidad (kg)"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblAlimento.setModel(modeloAlimento);
        sorterAlimento = new TableRowSorter<>(modeloAlimento);
        tblAlimento.setRowSorter(sorterAlimento);
    }

    private void configurarBuscadores() {
        txtBuscarEfectivo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarEfectivo();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarEfectivo();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarEfectivo();
            }
        });

        txtBuscarAlimento.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarAlimento();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarAlimento();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarAlimento();
            }
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
        if (sistema == null) {
            return;
        }

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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        pnlRegistro = new javax.swing.JPanel();
        lblDonante = new javax.swing.JLabel();
        txtDonante = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        lblMonto = new javax.swing.JLabel();
        txtMonto = new javax.swing.JSpinner();
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
        lblHistTitulo = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlPrincipal.setBackground(new java.awt.Color(238, 243, 250));
        pnlPrincipal.setToolTipText("");
        pnlPrincipal.setName("pnlPrincipal"); // NOI18N

        pnlRegistro.setBackground(new java.awt.Color(247, 250, 252));
        pnlRegistro.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registrar Donación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnlRegistro.setForeground(new java.awt.Color(247, 250, 252));
        pnlRegistro.setName("pnlRegistro"); // NOI18N

        lblDonante.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblDonante.setForeground(new java.awt.Color(38, 52, 69));
        lblDonante.setText("Donante:");
        lblDonante.setName("lblDonante"); // NOI18N

        txtDonante.setName("JTextField"); // NOI18N
        txtDonante.addActionListener(this::txtDonanteActionPerformed);

        lblTipo.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTipo.setForeground(new java.awt.Color(38, 52, 69));
        lblTipo.setText("Tipo:");
        lblTipo.setName("lblTipo"); // NOI18N

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alimento", "Efectivo" }));
        cmbTipo.setName("cmbTipo"); // NOI18N
        cmbTipo.addActionListener(this::cmbTipoActionPerformed);

        lblFecha.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(38, 52, 69));
        lblFecha.setText("Fecha:");
        lblFecha.setName("lblFecha"); // NOI18N

        txtFecha.setEditable(false);
        txtFecha.setName("txtFecha"); // NOI18N
        txtFecha.addActionListener(this::txtFechaActionPerformed);

        lblMonto.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblMonto.setForeground(new java.awt.Color(38, 52, 69));
        lblMonto.setText("Monto:");
        lblMonto.setName("lblMonto"); // NOI18N

        txtMonto.setName("txtMonto"); // NOI18N

        btnGuardar.setBackground(new java.awt.Color(36, 75, 112));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setName("btnGuardar"); // NOI18N
        btnGuardar.setPreferredSize(new java.awt.Dimension(91, 27));
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnLimpiar.setBackground(new java.awt.Color(226, 232, 240));
        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setName("btnLimpiar"); // NOI18N
        btnLimpiar.setPreferredSize(new java.awt.Dimension(91, 27));
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        btnCancelar.setBackground(new java.awt.Color(226, 232, 240));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setName("btnCancelar"); // NOI18N
        btnCancelar.setPreferredSize(new java.awt.Dimension(91, 27));
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        javax.swing.GroupLayout pnlRegistroLayout = new javax.swing.GroupLayout(pnlRegistro);
        pnlRegistro.setLayout(pnlRegistroLayout);
        pnlRegistroLayout.setHorizontalGroup(
            pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistroLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegistroLayout.createSequentialGroup()
                        .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDonante)
                            .addComponent(lblTipo)
                            .addComponent(lblFecha)
                            .addComponent(lblMonto))
                        .addGap(41, 41, 41)
                        .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDonante)
                                .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlRegistroLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRegistroLayout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))))
        );
        pnlRegistroLayout.setVerticalGroup(
            pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistroLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegistroLayout.createSequentialGroup()
                        .addComponent(lblDonante)
                        .addGap(18, 18, 18)
                        .addComponent(lblTipo)
                        .addGap(18, 18, 18)
                        .addComponent(lblFecha)
                        .addGap(18, 18, 18)
                        .addComponent(lblMonto))
                    .addGroup(pnlRegistroLayout.createSequentialGroup()
                        .addComponent(txtDonante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63))
        );

        pnlRegistros.setBackground(new java.awt.Color(247, 250, 252));
        pnlRegistros.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Donaciones registradas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
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

        btnVerDetalle.setBackground(new java.awt.Color(36, 75, 112));
        btnVerDetalle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnVerDetalle.setForeground(new java.awt.Color(255, 255, 255));
        btnVerDetalle.setText("Detalle");
        btnVerDetalle.setName("btnVerDetalle"); // NOI18N
        btnVerDetalle.setPreferredSize(new java.awt.Dimension(91, 27));
        btnVerDetalle.addActionListener(this::btnVerDetalleActionPerformed);

        btnEliminar.setBackground(new java.awt.Color(220, 53, 69));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setName("btnEliminar"); // NOI18N
        btnEliminar.setPreferredSize(new java.awt.Dimension(91, 27));
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnActualizar.setBackground(new java.awt.Color(36, 75, 112));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.setName("btnActualizar"); // NOI18N
        btnActualizar.setPreferredSize(new java.awt.Dimension(91, 27));
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        javax.swing.GroupLayout pnlRegistrosLayout = new javax.swing.GroupLayout(pnlRegistros);
        pnlRegistros.setLayout(pnlRegistrosLayout);
        pnlRegistrosLayout.setHorizontalGroup(
            pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistrosLayout.createSequentialGroup()
                .addGroup(pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegistrosLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(lblEfectivo)
                        .addGap(18, 18, 18)
                        .addComponent(txtBuscarEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlRegistrosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnVerDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlRegistrosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblAlimento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarAlimento, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlRegistrosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistrosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistrosLayout.createSequentialGroup()
                        .addGroup(pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlRegistrosLayout.createSequentialGroup()
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlRegistrosLayout.createSequentialGroup()
                                .addComponent(lblTotalAlimento)
                                .addGap(25, 25, 25)))
                        .addGap(107, 107, 107))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistrosLayout.createSequentialGroup()
                        .addComponent(lblTotalEfectivo)
                        .addGap(135, 135, 135))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarAlimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAlimento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalAlimento)
                .addGap(18, 18, 18)
                .addGroup(pnlRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVerDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblHistTitulo.setBackground(new java.awt.Color(36, 75, 112));
        lblHistTitulo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblHistTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblHistTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHistTitulo.setText("Gestión de Donaciones");
        lblHistTitulo.setAlignmentX(15.0F);
        lblHistTitulo.setAlignmentY(35.0F);
        lblHistTitulo.setOpaque(true);
        lblHistTitulo.setPreferredSize(new java.awt.Dimension(830, 35));

        lblLogo.setText("jLabel2");
        lblLogo.setPreferredSize(new java.awt.Dimension(65, 65));

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(pnlRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHistTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHistTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addComponent(pnlRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addComponent(pnlRegistros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 931, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
                    JOptionPane.showMessageDialog(this, "=== DETALLE DE EFECTIVO ===\n"
                            + "Código: " + ef.getIdDonacion() + "\n"
                            + "Donante: " + ef.getDonante() + "\n"
                            + "Fecha: " + ef.getFecha() + "\n"
                            + "Monto: S/ " + ef.getMontoEfectivo(), "Detalle", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        } else if (filaAl != -1) {
            int filaReal = tblAlimento.convertRowIndexToModel(filaAl);
            int id = Integer.parseInt(modeloAlimento.getValueAt(filaReal, 0).toString());
            for (Donacion d : sistema.getDonaciones()) {
                if (d.getIdDonacion() == id && d instanceof Comida co) {
                    JOptionPane.showMessageDialog(this, "=== DETALLE DE COMIDA ===\n"
                            + "Código: " + co.getIdDonacion() + "\n"
                            + "Donante: " + co.getDonante() + "\n"
                            + "Fecha: " + co.getFecha() + "\n"
                            + "Cantidad: " + co.getCantidadComidaKg() + " kg", "Detalle", JOptionPane.INFORMATION_MESSAGE);
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

        if (filaEf != -1) {
            int filaReal = tblEfectivo.convertRowIndexToModel(filaEf);

            idSeleccionado = Integer.parseInt(
                    modeloEfectivo.getValueAt(filaReal, 0).toString()
            );

        } else if (filaAl != -1) {
            int filaReal = tblAlimento.convertRowIndexToModel(filaAl);

            idSeleccionado = Integer.parseInt(
                    modeloAlimento.getValueAt(filaReal, 0).toString()
            );
        }

        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una donación para eliminar.",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar la donación con código "
                + idSeleccionado + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            try {
                sistema.eliminarDonacion(idSeleccionado);

                cargarDonaciones();

                JOptionPane.showMessageDialog(this,
                        "Donación eliminada correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IllegalArgumentException e) {

                JOptionPane.showMessageDialog(this,
                        e.getMessage(),
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
            }
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
            SistemaAlbergue sistema = cargarSistema();
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAlimento;
    private javax.swing.JLabel lblDonante;
    private javax.swing.JLabel lblEfectivo;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblHistTitulo;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTotalAlimento;
    private javax.swing.JLabel lblTotalEfectivo;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JPanel pnlRegistro;
    private javax.swing.JPanel pnlRegistros;
    private javax.swing.JTable tblAlimento;
    private javax.swing.JTable tblEfectivo;
    private javax.swing.JTextField txtBuscarAlimento;
    private javax.swing.JTextField txtBuscarEfectivo;
    private javax.swing.JTextField txtDonante;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JSpinner txtMonto;
    // End of variables declaration//GEN-END:variables
}
