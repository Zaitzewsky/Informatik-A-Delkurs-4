package mice;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import static mice.LäggTillAllt.pathway;
import se.oru.informatik.InformatikDB;
import se.oru.informatik.InformatikException;

public class VisaSpecifikPlattform extends javax.swing.JFrame {

private InformatikDB idb;
private static String rubrikNamn;

    public VisaSpecifikPlattform(String rubrikNamn) {
        initComponents();
        
        this.rubrikNamn=rubrikNamn; // Det värde användaren valt i comboBoxen i det föregående fönstret "VisaAllt".
        
        try
        {
            idb = new InformatikDB(pathway());
        }
        catch(InformatikException e)
                {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
        setRubrik();
        setProducent();
        setBeskrivning();
        setAnstalld();
    }
    
   public static String pathway()
   {
   String path = System.getProperty("user.dir") + "//MICEDB.FDB";
   
   return path;
   
   }
 
/**
 * Metoden setRubrik använder fältet rubrikNamn för att ange den jLabel som ska agera rubrik.
 */  
private void setRubrik()
{
    lblBenamning.setText(rubrikNamn);
}

/**
 * Metoden setProducent hämtar värdet i databasen
 * från kolumnen "Producent" där värdet i kolumnen "Benamning"
 * matchar värdet i fältet "rubrikNamn".
 * Sedan anges "lblProducent" det hämtade värdet.
 */
private void setProducent()
{
    String sqlFraga = "Select producent from plattform where benamning = '"+rubrikNamn+"'";
    try
    {
        String producentNamn = idb.fetchSingle(sqlFraga);
        lblProducent.setText(producentNamn);
    }
    catch (InformatikException e)
    {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
    
}

/**
 * Metoden setBeskrivning hämtar värdet i tabellen "Plattform" i den givna databasen
 * från kolumnen "Beskrivning" där värdet i kolumnen "Benamning"
 * matchar värdet i fältet "rubrikNamn".
 * Sedan anges "txtABeskrivning" det hämtade värdet.
 */
private void setBeskrivning()
{
    String sqlFraga = "select beskrivning from plattform where benamning='"+rubrikNamn+"'";
    try
    {
        String beskrivning = idb.fetchSingle(sqlFraga);
        txtABeskrivning.setText(beskrivning);
        txtABeskrivning.setEditable(false);
    }
    catch (InformatikException e)
    {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}

/**
 * Metoden setAnstalld hämtar alla värden i tabellen "Anstalld" i den givna databasen
 * från kolumnen "Namn" där dessa värdens primärnycklar matchar med tabellen "Plattform"s primärnycklar.
 * Denna matchning kollas i tabellen "Har_Kompetens" där det kollas vilka "Anstalld"s primärnycklar 
 * matchar med "Plattform"s primärnycklar.
 * Sedan sätts de hämtade värdena in i en ArrayList som sedan sätts in i den comboBox där användaren kan se anställda.
 */
private void setAnstalld()
{
    String sqlFraga = "select distinct namn from anstalld join har_kompetens on anstalld.aid=har_kompetens.aid "
            + "join plattform on plattform.pid=har_kompetens.pid where benamning = '"+rubrikNamn+"'";
    try
    {
        ArrayList<String> anstalldaLista = idb.fetchColumn(sqlFraga);
        if(!(anstalldaLista.isEmpty()))
        {
            cbAnstalld.removeAllItems();
            for(int i=0; i < anstalldaLista.size(); i++)
            {
                    cbAnstalld.addItem(anstalldaLista.get(i));
            }
        }
    }
    catch (InformatikException e)
    {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBenamning = new javax.swing.JLabel();
        lblProducent = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtABeskrivning = new javax.swing.JTextArea();
        cbAnstalld = new javax.swing.JComboBox();
        lblAnstalldaKompetens = new javax.swing.JLabel();
        lblPlattformBeskrivning = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblBenamning.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblBenamning.setText("Wii");

        lblProducent.setText("jLabel1");

        txtABeskrivning.setColumns(20);
        txtABeskrivning.setRows(5);
        jScrollPane1.setViewportView(txtABeskrivning);

        cbAnstalld.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Inga anställda" }));
        cbAnstalld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAnstalldActionPerformed(evt);
            }
        });

        lblAnstalldaKompetens.setText("Anställda med kompetens för denna plattform:");

        lblPlattformBeskrivning.setText("Beskrivning av plattform:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 8, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(181, 181, 181)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProducent)
                                    .addComponent(lblBenamning)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblAnstalldaKompetens)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbAnstalld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblPlattformBeskrivning)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblBenamning, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lblProducent)
                .addGap(13, 13, 13)
                .addComponent(lblPlattformBeskrivning)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAnstalldaKompetens)
                    .addComponent(cbAnstalld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbAnstalldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAnstalldActionPerformed
        //String namn = cbAnstalld.getSelectedItem().toString();
        //VisaSpecifikPlattform nyPlattform = new VisaSpecifikPlattform(namn);
        //nyPlattform.setVisible(true);
    }//GEN-LAST:event_cbAnstalldActionPerformed

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
            java.util.logging.Logger.getLogger(VisaSpecifikPlattform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisaSpecifikPlattform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisaSpecifikPlattform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisaSpecifikPlattform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisaSpecifikPlattform(rubrikNamn).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbAnstalld;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAnstalldaKompetens;
    private javax.swing.JLabel lblBenamning;
    private javax.swing.JLabel lblPlattformBeskrivning;
    private javax.swing.JLabel lblProducent;
    private javax.swing.JTextArea txtABeskrivning;
    // End of variables declaration//GEN-END:variables
}
