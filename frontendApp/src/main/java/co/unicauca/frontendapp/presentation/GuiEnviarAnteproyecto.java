/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package co.unicauca.frontendapp.presentation;

/**
 *
 * @author juan
 */
import co.unicauca.frontendapp.entities.ProjectModel;
import co.unicauca.frontendapp.entities.StatusEnum;
import co.unicauca.frontendapp.service.ServiceProyecto;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

public class GuiEnviarAnteproyecto extends JFrame {

    private JTable tablaProyectos;
    private String emailProfesor;
    private ServiceProyecto service;
    private List<ProjectModel> proyectos;

    public GuiEnviarAnteproyecto(ServiceProyecto service, String emailProfesor) {
        this.service = service;
        this.emailProfesor = emailProfesor;

        setTitle("Enviar Anteproyecto");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Encabezado
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(70, 130, 180));
        JLabel lblTitulo = new JLabel("Proyectos Aceptados - Envío de Anteproyecto");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(lblTitulo, BorderLayout.WEST);

        JLabel lblCorreo = new JLabel(emailProfesor);
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        header.add(lblCorreo, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        tablaProyectos = new JTable();
        tablaProyectos.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(tablaProyectos);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarDatos() {
        // Listar proyectos aceptados o en estado previo al envío de anteproyecto
        proyectos = service.listarPorEstadoYCorreo(StatusEnum.ACEPTADO_COMITE, emailProfesor);

        tablaProyectos.setModel(new AbstractTableModel() {
            private final String[] columnas = {"Título", "Modalidad", "Estado", "Observaciones", "Acción"};

            @Override
            public int getRowCount() {
                return proyectos.size();
            }

            @Override
            public int getColumnCount() {
                return columnas.length;
            }

            @Override
            public String getColumnName(int column) {
                return columnas[column];
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                ProjectModel p = proyectos.get(rowIndex);
                return switch (columnIndex) {
                    case 0 ->
                        p.getAtrTitle();
                    case 1 ->
                        p.getAtrModality().getName();
                    case 2 ->
                        p.getAtrStatus().toString();
                    case 3 ->
                        p.getAtrObservations();
                    case 4 ->
                        "Enviar Anteproyecto";
                    default ->
                        null;
                };
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex == 4;
            }
        });

        tablaProyectos.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        tablaProyectos.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    // Renderer para el botón
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Editor del botón
    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private String label;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener((ActionEvent e) -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            clicked = true;
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                ProjectModel proyecto = proyectos.get(row);
                enviarAnteproyecto(proyecto);
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        private void enviarAnteproyecto(ProjectModel proyecto) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Selecciona el archivo del anteproyecto");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 

            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Documentos (*.pdf, *.doc, *.docx)", "pdf", "doc", "docx"));

            int result = chooser.showOpenDialog(GuiEnviarAnteproyecto.this);
            if (result != JFileChooser.APPROVE_OPTION) {
                return; // usuario canceló
            }

            File selectedFile = chooser.getSelectedFile();
            if (selectedFile == null || !selectedFile.exists()) {
                JOptionPane.showMessageDialog(GuiEnviarAnteproyecto.this,
                        "Archivo no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String rutaArchivo = selectedFile.getAbsolutePath();
            System.out.println("Archivo seleccionado: " + rutaArchivo);

            try {
                proyecto.setRutaAnteproyecto(rutaArchivo.replace("\\", "/"));
                proyecto.setAtrStatus(StatusEnum.PRESENTADO_JEFATURA);

                boolean ok = service.actualizarProyecto(proyecto);

                if (ok) {
                    JOptionPane.showMessageDialog(GuiEnviarAnteproyecto.this,
                            "Anteproyecto enviado correctamente");
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(GuiEnviarAnteproyecto.this,
                            "No se pudo enviar el anteproyecto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(GuiEnviarAnteproyecto.this,
                        "Error al enviar anteproyecto: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Ejemplo de prueba
            // new GuiEnviarAnteproyecto(new ServiceProyecto(), "profesor@unicauca.edu.co").setVisible(true);
        });
    }
    /*
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

*/
}
