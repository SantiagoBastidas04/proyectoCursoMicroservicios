/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package co.unicauca.frontendapp.presentation;

import co.unicauca.frontendapp.access.IProjectRepositorio;
import co.unicauca.frontendapp.access.ProjectRepositorio;
import co.unicauca.frontendapp.entities.ModalityEnum;
import co.unicauca.frontendapp.entities.ProjectModel;
import co.unicauca.frontendapp.entities.StatusEnum;
import co.unicauca.frontendapp.service.ServiceProyecto;
import co.unicauca.frontendapp.utility.EmailValidator;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.time.LocalDate;

/**
 *
 * @author Acer
 */
public class GuiProfesor extends javax.swing.JFrame {
    
    private final ServiceProyecto serviceProyecto;
    //private final ServiceEvaluacionFormato serviceEvaluacion;
    //private static IFormatoRepositorio  formatoRepositorio;
    //private static IEvaluacionFormatoRepositorio formatoEvaluacionRepositorio;
    public static String email;
    public static String rol;
    
    // Declaración de componentes
    private JTextField txtTituloTrabajo, txtDirector, txtCodirector;
    private JTextArea txtObjetivosEspecificos, txtObjetivoGeneral;
    private JTextField txtFormatoPdf, txtCartaAceptacion;
    private JComboBox<ModalityEnum> comboModalidad;
    private JTextField txtCorreo, txtCorreo2;;
    private JButton btnBrowseFormato, btnBrowseCarta;
    private JPanel cartaPanel; 
    private JDateChooser dateChooser; 
   
    public GuiProfesor(String rol , String email) {
        IProjectRepositorio projectRepositorio = new ProjectRepositorio();
        //formatoEvaluacionRepositorio = ServiceLocator.getInstance().getEvaluacionRepository();
        this.serviceProyecto = new ServiceProyecto(projectRepositorio); 
        //this.serviceEvaluacion = new ServiceEvaluacionFormato(formatoEvaluacionRepositorio);
        init(rol,email);
    }

    private void init(String rol,String email) {
        setTitle("Formato A - Universidad del Cauca");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // === Header superior ===
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(106, 153, 148));
        panelHeader.setBorder(new EmptyBorder(5, 10, 5, 10));

        JLabel lblFormatoA = new JLabel("Rol : "+ rol + "  "+"Formato A" );
        lblFormatoA.setForeground(Color.WHITE);
        lblFormatoA.setFont(lblFormatoA.getFont().deriveFont(Font.BOLD, 16f));

        JLabel lblUsuario = new JLabel(email);
        lblUsuario.setForeground(Color.WHITE);
        // Icono usuario
        JLabel lblIcono = new JLabel(UIManager.getIcon("OptionPane.informationIcon"));

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        userPanel.setOpaque(false);
        userPanel.add(lblUsuario);
        userPanel.add(lblIcono);

        panelHeader.add(lblFormatoA, BorderLayout.WEST);
        panelHeader.add(userPanel, BorderLayout.EAST);

        // === Panel lateral (menú desplegable) ===
        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(new Color(106, 153, 148));
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
        panelLateral.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel lblMenu = new JLabel("Menú");
        lblMenu.setForeground(Color.WHITE);
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMenu.setFont(lblMenu.getFont().deriveFont(Font.BOLD, 16f));

         String[] opciones = {"Seleccione", "Consultar/Actualizar Proyectos","Ver Formatos Enviados"};
        JComboBox<String> comboMenu = new JComboBox<>(opciones);
        comboMenu.addActionListener(e -> {
        String opcion = (String) comboMenu.getSelectedItem();
    if ("Ver Formatos Enviados".equals(opcion)) {
        //new GuiFormatoEnviado(service, email).setVisible(true);
    }
    if("Consultar/Actualizar Proyectos".equals(opcion)){
        //new GuiActualizarFormatos(serviceEvaluacion,email).setVisible(true);
    }
});


        comboMenu.setFont(comboMenu.getFont().deriveFont(12f)); 
        comboMenu.setPreferredSize(new Dimension(160, 26));    
        comboMenu.setMaximumSize(comboMenu.getPreferredSize()); 
        comboMenu.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5)); 

        comboMenu.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });

        panelLateral.add(lblMenu);
        panelLateral.add(Box.createRigidArea(new Dimension(0, 15)));
        panelLateral.add(comboMenu);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        txtTituloTrabajo = new JTextField(20);
        txtDirector = new JTextField(20);
        txtCodirector = new JTextField(20);
        txtObjetivoGeneral = new JTextArea(4, 20);
        txtCorreo = new JTextField(20);
        txtCorreo2 = new JTextField(20);
       

        txtObjetivosEspecificos = new JTextArea(4, 20);
        txtObjetivosEspecificos.setLineWrap(true);
        txtObjetivosEspecificos.setWrapStyleWord(true);
        JScrollPane scrollObjetivos = new JScrollPane(txtObjetivosEspecificos);

        
        //comboModalidad = new JComboBox<>(new String[]{"practica profesional", "investigacion"});
        comboModalidad = new JComboBox<>(ModalityEnum.values());
        comboModalidad.addActionListener(e -> toggleCartaAceptacion());

        
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");

       
        txtFormatoPdf = new JTextField(15);
        btnBrowseFormato = new JButton("Browse...");
        btnBrowseFormato.addActionListener((ActionEvent e) -> {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                txtFormatoPdf.setText(file.getAbsolutePath());
            }
        });

        txtCartaAceptacion = new JTextField(15);
        btnBrowseCarta = new JButton("Browse...");
        btnBrowseCarta.addActionListener((ActionEvent e) -> {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                txtCartaAceptacion.setText(file.getAbsolutePath());
            }
        });

        
        cartaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        cartaPanel.setOpaque(false);
        cartaPanel.add(new JLabel("Carta de aceptación"));
        cartaPanel.add(txtCartaAceptacion);
        cartaPanel.add(btnBrowseCarta);

        
        JLabel lblTituloTrabajo = new JLabel("Título trabajo de grado*");
        JLabel lblDirector = new JLabel("Director del proyecto de grado*");
        JLabel lblCodirector = new JLabel("Codirector del proyecto de grado");
        JLabel lblFecha = new JLabel("Fecha actual propuesta*");
        JLabel lblObjetivoGeneral = new JLabel("Objetivo general*");
        JLabel lblModalidad = new JLabel("Modalidad*");
        JLabel lblCorreo = new JLabel("Correo del estudiante*");
        JLabel lblCorreo2 = new JLabel("Correo del estudiante 2");
        JLabel lblObjetivosEspecificos = new JLabel("Objetivos específicos*");
        JLabel lblFormatoPdf = new JLabel("Formato en PDF*");

    
        GroupLayout layout = new GroupLayout(mainPanel);
mainPanel.setLayout(layout);

layout.setAutoCreateGaps(true);
layout.setAutoCreateContainerGaps(true);

layout.setHorizontalGroup(
    layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(lblTituloTrabajo)
            .addComponent(txtTituloTrabajo)
            .addComponent(lblDirector)
            .addComponent(txtDirector)
            .addComponent(lblCodirector)
            .addComponent(txtCodirector)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblCorreo)
                    .addComponent(txtCorreo))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblCorreo2)
                    .addComponent(txtCorreo2)))
            .addComponent(lblObjetivoGeneral)
            .addComponent(txtObjetivoGeneral)
            .addComponent(lblObjetivosEspecificos)
            .addComponent(scrollObjetivos)
        )
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(lblFecha)
            .addComponent(dateChooser)
            .addComponent(lblModalidad)
            .addComponent(comboModalidad)
            .addComponent(lblFormatoPdf)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txtFormatoPdf)
                .addComponent(btnBrowseFormato))
            .addComponent(cartaPanel)
        )
);

layout.setVerticalGroup(
    layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(lblTituloTrabajo)
            .addComponent(lblFecha))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(txtTituloTrabajo)
            .addComponent(dateChooser))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(lblDirector)
            .addComponent(lblModalidad))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(txtDirector)
            .addComponent(comboModalidad))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(lblCodirector)
            .addComponent(lblFormatoPdf))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(txtCodirector)
            .addComponent(txtFormatoPdf)
            .addComponent(btnBrowseFormato))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(lblCorreo)
            .addComponent(lblCorreo2))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(txtCorreo)
            .addComponent(txtCorreo2))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(lblObjetivoGeneral))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(txtObjetivoGeneral))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(lblObjetivosEspecificos))
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(scrollObjetivos)
            .addComponent(cartaPanel))
);
        JButton btnSubir = new JButton("Subir");
        btnSubir.setBackground(new Color(106, 153, 148));
        btnSubir.setForeground(Color.WHITE);
        btnSubir.setFont(btnSubir.getFont().deriveFont(Font.BOLD));
        
        btnSubir.addActionListener(this::btnSubirActionPerformed);
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(btnSubir);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelHeader, BorderLayout.NORTH);
        getContentPane().add(panelLateral, BorderLayout.WEST);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        toggleCartaAceptacion(); 

        pack();
        setLocationRelativeTo(null);

        
    }

    private void toggleCartaAceptacion() {
        ModalityEnum modalidad =  (ModalityEnum) comboModalidad.getSelectedItem();
        if (modalidad != null && modalidad.equals(ModalityEnum.PROFESSIONAL)) {
            cartaPanel.setVisible(true);
        } else {
            cartaPanel.setVisible(false);
        }
        cartaPanel.revalidate();
        cartaPanel.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSubir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnSubir.setText("ENVIAR");
        btnSubir.setToolTipText("");
        btnSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(273, Short.MAX_VALUE)
                .addComponent(btnSubir)
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(256, Short.MAX_VALUE)
                .addComponent(btnSubir)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirActionPerformed
        // TODO add your handling code here:
        String titulo = txtTituloTrabajo.getText();
        String director = txtDirector.getText();
        String codirector = txtCodirector.getText();
        String correoEstudiante = txtCorreo.getText();
        String correoEstudiante2 = txtCorreo2.getText();
        ModalityEnum modalidad = (ModalityEnum) comboModalidad.getSelectedItem();   
        String objetivoGeneral = txtObjetivoGeneral.getText();
        String objetivosEspecificos = txtObjetivosEspecificos.getText();
        java.util.Date fecha = dateChooser.getDate();
        StatusEnum estado = StatusEnum.INICIO;
        Integer intentosIniciales = 0;
        String observaciones = null;
        String formatoPdf = txtFormatoPdf.getText().replace("\\", "/");
        String cartaAceptacion = txtCartaAceptacion.getText();
        
        
        LocalDate fechaActual = fecha.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        
        if (!EmailValidator.esCorreoInstitucional(correoEstudiante) || !EmailValidator.esCorreoInstitucional(correoEstudiante2) && !correoEstudiante2.isBlank()) {
            JOptionPane.showMessageDialog(this, 
                "El dominio del correo debe ser @unicauca.edu.co");
            return; // salir si no cumple
        }
        
        ProjectModel proyecto = new ProjectModel(
                null,
                titulo,
                director,
                codirector,
                correoEstudiante,
                correoEstudiante2,                
                modalidad,
                fechaActual,
                objetivoGeneral,
                objetivosEspecificos,
                estado,
                intentosIniciales,
                observaciones,
                formatoPdf,
                cartaAceptacion,
                null
        );
        
        // Guardar usando el repositorio
        boolean ok = serviceProyecto.registrarProyecto(proyecto);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Proyecto guardado correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el proyecto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSubirActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuiProfesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiProfesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiProfesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiProfesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new GuiProfesor(rol,email).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSubir;
    // End of variables declaration//GEN-END:variables
}
