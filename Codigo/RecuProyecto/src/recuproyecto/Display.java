/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recuproyecto;

import java.io.FileNotFoundException;
import java.io.IOException;
import static recuproyecto.RecuProyecto.merge;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;

/**
 *
 * @author Pc
 */
public class Display extends javax.swing.JFrame {

    /**
     * Creates new form Display
     */
    public Display() {
        initComponents();
        jTextField2.setEnabled(false);
        setVisible(true);
        jTextField1.getDocument().addDocumentListener(new DocumentListener()
        {
            public void changedUpdate(DocumentEvent arg0) 
            {
                
            }
            public void insertUpdate(DocumentEvent arg0) 
            {
                actualizarSugerencias();                
            }

            public void removeUpdate(DocumentEvent arg0) 
            {
                actualizarSugerencias();
                //panel.setPrice(panel.countTotalPrice(TabPanel.this));
            }
        });
        
    }
    
    
    
    /*public static void checkeo() {
    final JFrame frame = new JFrame("Default Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField textField = new JTextField();
        frame.add(textField, BorderLayout.NORTH);

        DocumentListener documentListener = new DocumentListener() {
        public void changedUpdate(DocumentEvent documentEvent) {
          printIt(documentEvent);
        }
        public void insertUpdate(DocumentEvent documentEvent) {
          printIt(documentEvent);
        }
        public void removeUpdate(DocumentEvent documentEvent) {
          printIt(documentEvent);
        }
        private void printIt(DocumentEvent documentEvent) {
          DocumentEvent.EventType type = documentEvent.getType();
          String typeString = null;
          if (type.equals(DocumentEvent.EventType.CHANGE)) {
            typeString = "Change";
          }  else if (type.equals(DocumentEvent.EventType.INSERT)) {
            typeString = "Insert";
          }  else if (type.equals(DocumentEvent.EventType.REMOVE)) {
            typeString = "Remove";
          }
          System.out.print("Type : " + typeString);
          Document source = documentEvent.getDocument();
          int length = source.getLength();
          System.out.println("Length: " + length);
        }
      };
      jTextField1.getDocument().addDocumentListener(documentListener);

      frame.setSize(250, 150);
      frame.setVisible(true);
    }*/

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setBackground(new java.awt.Color(102, 255, 102));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Type here for search");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.JLabel() {
            public javax.swing.Icon getIcon() {
                try {
                    return new javax.swing.ImageIcon(
                        new java.net.URL("http://i.imgur.com/Bo5El74.png")
                    );
                } catch (java.net.MalformedURLException e) {
                }
                return null;
            }
        }.getIcon());

        jRadioButton3.setBackground(new java.awt.Color(204, 255, 153));
        jRadioButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRadioButton3.setText("Without stopwords");

        jRadioButton1.setBackground(new java.awt.Color(153, 204, 255));
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRadioButton1.setText("Jaccard Coeficient");

        jCheckBox1.setBackground(new java.awt.Color(153, 204, 255));
        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox1.setText("Relevancia Esperada");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Introduzca un valor entre 1 y 10");

        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(45, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)))
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCheckBox1)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jRadioButton1))
                                .addGap(4, 4, 4)
                                .addComponent(jLabel3))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(223, 223, 223)
                        .addComponent(jLabel1)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        long duracion= 0;
        long T1 = System.nanoTime();
        limpiar();
        String result = "";
        RecuProyecto controlador = new RecuProyecto(); 
        controlador.inicializar();
        String consulta = jTextField1.getText();
        
        try{
            /*boolean and = jRadioButton1.isSelected();
            boolean or = jRadioButton2.isSelected();
            boolean sw = jRadioButton3.isSelected();
                    
            if (and){
                result = controlador.procesarAnd(consulta);
            } else {
                if (sw) {
                    result = controlador.procesarOrSinSW(consulta);
                } else {
                    result = controlador.procesarOr(consulta);
                }
            }*/
            boolean sw = jRadioButton3.isSelected();
            boolean jc = jRadioButton1.isSelected();
            boolean ck = jCheckBox1.isSelected();
            double relevancia = -1;
            if(ck){
                try{
                relevancia = Double.parseDouble(jTextField2.getText());
                }catch(Exception e){
                    jCheckBox1.setSelected(false);
                    jTextField2.setEnabled(false);
                    jTextField2.setText("");
                }
            }
            if(sw){
                consulta = controlador.procesarOrSinSW(consulta);//elimina stopwords de la consulta
            }
            //System.out.println(consulta);
            if(jc){
                result = controlador.getRelevantDocsJaccard(consulta,relevancia);
            } else {
                result = controlador.getRelevantDocs(consulta,relevancia);
            }
            
            
            
        }catch(IOException e){ e.printStackTrace();};
        long T2 = System.nanoTime();
        duracion = T2-T1;
        ImprimirTiempo(duracion);
        imprima(result);
    }//GEN-LAST:event_jButton1ActionPerformed
    public static void ImprimirTiempo(long X) {
        long seg= 0; 
        seg= (X/1000000000);
        long mili= 0;
        mili  = (X/1000000) - ((seg)*(1000));
        long micro= (X/1000) -((seg)*(1000000)) - ((mili)*(1000));
        System.out.println( "Su duracion desglosada es:");
        System.out.println( "Segundos: " +seg);
        System.out.println( "Milisegundos: " +mili);
        System.out.println( "Microsegundos: " +micro);
    }
    public void imprima(String string) {
        String result = string.replace('-', '/');
        String newline = "\n";
        jTextArea1.append(result+newline);        
    }
    
    public void limpiar() {
        jTextArea1.setText("");       
    }
    
    public void actualizarSugerencias(){
        //Lo que debe de hacer este metodo es siempre llamar a algun método en Recuproyecto, para que busque en
        //sugerencias.txt las sugerencias que posean lo que se ha escrito hasta el momento
        //Esto lo hace letra x letra, entonces habria que hacer algun .split por espacios y si no hay espacios
        //Entoncs que busque lo que está escrito y ya, cmo palabra unica :B
        String palabra ="Prueba";
        DefaultListModel<String> sugerencias = new DefaultListModel<>();
        sugerencias.addElement(palabra);
        //sugerencias = obtenerSugerencias(jTextField1.getText());
        jList1.setModel(sugerencias);
        if(jTextField1.getText().equals("")){//Aqui lo que hace es vaciar las sugerencias si el textfield está vacio
            DefaultListModel listmodel = (DefaultListModel)jList1.getModel();
            listmodel.removeAllElements();
        }
    
    }
    
    
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        
        if(jCheckBox1.isSelected()){
            jTextField2.setEnabled(true);
        }
        else{
            jTextField2.setEnabled(false);
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(Display.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Display.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Display.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Display.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Display().setVisible(true);
            }
        });*/
        
         Display interfaz = new Display();
         
         
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}

    class MyDocumentListener implements DocumentListener 
    {
        public void insertUpdate(DocumentEvent e) {

        }
        public void removeUpdate(DocumentEvent e) {
            System.out.println("change -" + e);
        }
        public void changedUpdate(DocumentEvent e) {
        }
    }

    class MyTextActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }
    }

