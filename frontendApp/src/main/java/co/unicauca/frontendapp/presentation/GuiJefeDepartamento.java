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
import co.unicauca.frontendapp.entities.User;
import co.unicauca.frontendapp.entities.enumRol;
import co.unicauca.frontendapp.service.Service;
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
    private final Service serviceUser;
    private JTable tablaProyectos;
    private JButton btnEnviar;
    private List<User> listaEvaluadoresGlobal;

    public GuiJefeDepartamento(String rol, String email) {
        this.service = new ServiceProyecto(Factory.getInstance().getProjectRepository());
        this.serviceUser = new Service(Factory.getInstance().getUserRepository());
        GuiJefeDepartamento.rol = rol;
        GuiJefeDepartamento.email = email;

        setTitle("Proyectos de Grado - Evaluación de Jefatura");
        setSize(1100, 400);
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

        btnEnviar.addActionListener(e -> guardarAsignaciones());
    }

    private void cargarDatos() {
        List<ProjectModel> lista = service.listarPorEstado(StatusEnum.PRESENTADO_JEFATURA);

        // obtener profesores (lista usada también en guardarAsignaciones)
        listaEvaluadoresGlobal = serviceUser.listarPorRol(enumRol.Profesor);

        // Preparar array de emails para el combo. Primera opción es un placeholder.
        String[] emails = new String[listaEvaluadoresGlobal.size() + 1];
        emails[0] = "--Seleccione--";
        for (int k = 0; k < listaEvaluadoresGlobal.size(); k++) {
            emails[k + 1] = listaEvaluadoresGlobal.get(k).getEmail();
        }

        String[] columnas = {"Proyecto", "Estado", "Evaluador 1", "Evaluador 2", "Observaciones"};
        Object[][] datos = new Object[lista.size()][5];

        for (int i = 0; i < lista.size(); i++) {
            ProjectModel p = lista.get(i);
            datos[i][0] = p; // guardamos el objeto para usarlo luego
            datos[i][1] = p.getAtrStatus() != null ? p.getAtrStatus().getDisplayName() : "Desconocido";
            // Inicializar con el placeholder para que aparezca el combo vacío
            datos[i][2] = emails[0]; // Evaluador 1 -> "--Seleccione--"
            datos[i][3] = emails[0]; // Evaluador 2 -> "--Seleccione--"
            datos[i][4] = p.getAtrObservations();
        }

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // permitir editar solo evaluadores y observaciones (si quieres)
                return column == 2 || column == 3 || column == 4;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // mantener Object.class para las columnas de combo (usamos Strings)
                return Object.class;
            }
        };

        tablaProyectos.setModel(modelo);

        // Render para mostrar solo el título del proyecto en la columna 0
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

        tablaProyectos.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String estado = value != null ? value.toString() : "";
                setHorizontalAlignment(SwingConstants.CENTER);
                if (estado.contains("ACEPTADO")) {
                    c.setBackground(Color.GREEN);
                    c.setForeground(Color.WHITE);
                } else if (estado.contains("RECHAZADO")) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });

        // Crear combobox con los emails para usarlo como editor
        JComboBox<String> comboEditor = new JComboBox<>(emails);
        tablaProyectos.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboEditor));

        JComboBox<String> comboEditor2 = new JComboBox<>(emails);
        tablaProyectos.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboEditor2));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tablaProyectos.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tablaProyectos.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        //Doble click en columna 0 para abrir el archivo 
        tablaProyectos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaProyectos.rowAtPoint(e.getPoint());
                int col = tablaProyectos.columnAtPoint(e.getPoint());
                if (fila >= 0 && col == 0) {
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

    private void guardarAsignaciones() {
        if (tablaProyectos.isEditing()) {
            tablaProyectos.getCellEditor().stopCellEditing();
        }

        DefaultTableModel modelo = (DefaultTableModel) tablaProyectos.getModel();
        boolean cambiosRealizados = false;

        for (int i = 0; i < modelo.getRowCount(); i++) {
            ProjectModel proyecto = (ProjectModel) modelo.getValueAt(i, 0);

            // Las celdas contienen Strings: el email o "--Seleccione--"
            String emailEv1 = (String) modelo.getValueAt(i, 2);
            String emailEv2 = (String) modelo.getValueAt(i, 3);
            String observaciones = (String) modelo.getValueAt(i, 4);

            // Si no se seleccionó evaluador (placeholder) => ignorar asignación
            boolean ev1Seleccionado = emailEv1 != null && !emailEv1.equals("--Seleccione--");
            boolean ev2Seleccionado = emailEv2 != null && !emailEv2.equals("--Seleccione--");

            // Validación: no permitir mismo evaluador en ambas posiciones
            if (ev1Seleccionado && ev2Seleccionado && emailEv1.equals(emailEv2)) {
                JOptionPane.showMessageDialog(this,
                        "Error en fila " + (i + 1) + ": Evaluador 1 y Evaluador 2 no pueden ser la misma persona.");
                return; // cancela todo el guardado; si prefieres, puedes saltar solo esta fila
            }

            // Asignar solo si hay selección
            if (ev1Seleccionado || ev2Seleccionado) {
                // Buscar User por email dentro de la lista cargada
                User ev1 = null;
                User ev2 = null;
                if (ev1Seleccionado) {
                    for (User u : listaEvaluadoresGlobal) {
                        if (u.getEmail().equals(emailEv1)) {
                            ev1 = u;
                            break;
                        }
                    }
                }
                if (ev2Seleccionado) {
                    for (User u : listaEvaluadoresGlobal) {
                        if (u.getEmail().equals(emailEv2)) {
                            ev2 = u;
                            break;
                        }
                    }
                }

                if(ev1 != null){
                    System.out.println("Email Evaluador 1: " + ev1.getEmail());
                    proyecto.setAtrEvaluator1Email(ev1.getEmail());
                }
                if(ev2 != null){
                    System.out.println("Email Evaluador 2: " + ev2.getEmail());
                    proyecto.setAtrEvaluator2Email(ev2.getEmail());
                }
                proyecto.setAtrObservations(observaciones);
                proyecto.setAtrStatus(StatusEnum.EVALUACION_DEPARTAMENTO);

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
            JOptionPane.showMessageDialog(this, "Evaluadores asignados correctamente.");
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "No se realizaron cambios (no se seleccionaron evaluadores).");
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

