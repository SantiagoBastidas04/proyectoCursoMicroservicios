/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package co.unicauca.frontendapp.presentation;

/**
 *
 * @author juan
 */
import co.unicauca.frontendapp.access.Factory;
import co.unicauca.frontendapp.entities.ProjectModel;
import co.unicauca.frontendapp.entities.StatusEnum;
import co.unicauca.frontendapp.service.ServiceProyecto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class GuiJefeDepartamento extends JFrame {

    private static String rol;
    private static String email;
    private final ServiceProyecto service;
    private JTable tablaProyectos;
    private JButton btnEnviar;

    public GuiJefeDepartamento(String rol, String email) {
        this.service = new ServiceProyecto(Factory.getInstance().getProjectRepository());

        GuiJefeDepartamento.rol = rol;
        GuiJefeDepartamento.email = email;

        setTitle("Proyectos de Grado - Evaluación de Jefatura");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents(rol, email);
        cargarDatos();
    }

    private void initComponents(String rol, String email) {
        setLayout(new BorderLayout());

        // Encabezado
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(80, 120, 180));

        JLabel lblTitulo = new JLabel("Evaluación de Proyectos - Jefatura de Departamento");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        header.add(lblTitulo, BorderLayout.WEST);

        JLabel lblCorreo = new JLabel("Rol " + rol.toUpperCase() + " " + email + " ");
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        header.add(lblCorreo, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Tabla
        tablaProyectos = new JTable();
        tablaProyectos.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tablaProyectos);
        add(scrollPane, BorderLayout.CENTER);

        // Botón enviar
        btnEnviar = new JButton("Enviar decisiones");
        btnEnviar.setBackground(new Color(0, 102, 204));
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnEnviar);
        add(panelBoton, BorderLayout.SOUTH);

        btnEnviar.addActionListener(e -> enviarEvaluaciones());
    }

    private void cargarDatos() {
        List<ProjectModel> lista = service.listarPorEstado(StatusEnum.PRESENTADO_JEFATURA);
        System.out.println("Proyectos PRESENTADO_JEFATURA: " + lista.size());

        String[] columnas = {"Proyecto", "Estado", "Aprobar", "Rechazar", "Observaciones"};
        Object[][] datos = new Object[lista.size()][5];

        for (int i = 0; i < lista.size(); i++) {
            ProjectModel p = lista.get(i);
            datos[i][0] = p;
            datos[i][1] = p.getAtrStatus() != null ? p.getAtrStatus().getDisplayName() : "Desconocido";
            datos[i][2] = Boolean.FALSE;
            datos[i][3] = Boolean.FALSE;
            datos[i][4] = p.getAtrObservations();
        }

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2 || columnIndex == 3) {
                    return Boolean.class;
                }
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 3;
            }
        };

        tablaProyectos.setModel(modelo);

        // Render columna Proyecto (muestra solo el título)
        tablaProyectos.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof ProjectModel) {
                    setText(((ProjectModel) value).getAtrTitle());
                } else {
                    super.setValue(value);
                }
            }
        });

        // Render Estado con color
        tablaProyectos.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String estado = value != null ? value.toString() : "";
                if ("ACEPTADO_JEFATURA".equalsIgnoreCase(estado)) {
                    c.setBackground(Color.GREEN);
                    c.setForeground(Color.WHITE);
                } else if ("RECHAZADO_JEFATURA".equalsIgnoreCase(estado)) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });

        // Evitar que se apruebe y rechace al mismo tiempo
        modelo.addTableModelListener(e -> {
            if (e.getColumn() == 2 || e.getColumn() == 3) {
                int row = e.getFirstRow();
                Boolean aprobar = (Boolean) modelo.getValueAt(row, 2);
                Boolean rechazar = (Boolean) modelo.getValueAt(row, 3);
                if (aprobar && rechazar) {
                    if (e.getColumn() == 2) {
                        modelo.setValueAt(false, row, 3);
                    } else {
                        modelo.setValueAt(false, row, 2);
                    }
                }
            }
        });

        // Click en nombre del proyecto → abrir documento
        tablaProyectos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaProyectos.rowAtPoint(e.getPoint());
                int columna = tablaProyectos.columnAtPoint(e.getPoint());
                if (columna == 0) {
                    ProjectModel p = (ProjectModel) modelo.getValueAt(fila, 0);
                    abrirArchivo(p.getRutaFormatoA());
                }
            }
        });
    }

    private void abrirArchivo(String ruta) {
        try {
            File file = new File(ruta);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(this, "El archivo no existe: " + ruta);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo abrir el archivo: " + ex.getMessage());
        }
    }

    private void enviarEvaluaciones() {
        if (tablaProyectos.isEditing()) {
            tablaProyectos.getCellEditor().stopCellEditing();
        }

        DefaultTableModel modelo = (DefaultTableModel) tablaProyectos.getModel();
        boolean cambiosRealizados = false;

        for (int i = 0; i < modelo.getRowCount(); i++) {
            ProjectModel proyecto = (ProjectModel) modelo.getValueAt(i, 0);
            Boolean aprobar = (Boolean) modelo.getValueAt(i, 2);
            Boolean rechazar = (Boolean) modelo.getValueAt(i, 3);
            String observaciones = (String) modelo.getValueAt(i, 4);

            if (Boolean.TRUE.equals(aprobar) || Boolean.TRUE.equals(rechazar)) {
                StatusEnum nuevoEstado = Boolean.TRUE.equals(aprobar)
                        ? StatusEnum.EVALUADOR_ACEPTA
                        : StatusEnum.EVALUADOR_PIDE_CORRECCIONES;

                proyecto.setAtrStatus(nuevoEstado);
                proyecto.setAtrObservations(observaciones);

                try {
                    service.actualizarProyecto(proyecto);
                    cambiosRealizados = true;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error al actualizar el proyecto '" + proyecto.getAtrTitle() + "': " + ex.getMessage());
                }
            }
        }

        if (cambiosRealizados) {
            JOptionPane.showMessageDialog(this, "Decisiones enviadas correctamente.");
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "No se realizaron cambios.");
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new GuiJefeDepartamento("Jefe", "jefe@unicauca.edu.co").setVisible(true));
    }
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
*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

