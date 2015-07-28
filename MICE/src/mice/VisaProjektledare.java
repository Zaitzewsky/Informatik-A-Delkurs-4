/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mice;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import static mice.LäggTillAllt.pathway;
import se.oru.informatik.InformatikDB;
import se.oru.informatik.InformatikException;

public class VisaProjektledare extends javax.swing.JFrame {

private InformatikDB idb;
private static String rubrikNamn;

    public VisaProjektledare(String rubrikNamn) {
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
         setAntalUppdrag();
         setPågåendeUppdrag();
         setAvslutadeUppdrag();
         setTelefon();
         setMail();
    }
    
   public static String pathway()
   {
   String path = System.getProperty("user.dir") + "//MICEDB.FDB";
   
   return path;
   
   }
    
    /**
     * Metoden tar fältet rubrikNamn och sätter JLabeln, som ska agera
     * rubrik för detta fönster, med fältets värde.
     */
    private void setRubrik()
    {
        lblAnstalldNamn.setText(rubrikNamn);
    }
    
    /**
     * Metoden hämtar alla värden från kolumnen "antal_projetledar_uppdrag", från tabellen "Projektledare",
     * som matchar de värden som finns i tabellen "Anstalld" samt från samma rad där "namn" (från tabellen Anstalld)
     * matchar fältet "rubrikNamn".
     * Därefter sätts den jLabel som visar antal uppdrag med det värde som hämtats från databasen.
     */
    private void setAntalUppdrag()
    {
        String sqlFraga = "select antal_projetledar_uppdrag from projektledare\n" +
"join anstalld on projektledare.aid=anstalld.aid\n" +
"where namn='"+rubrikNamn+"'";
        
        try
        {
            String uppdrag = idb.fetchSingle(sqlFraga);
            lblAntalUppdragResultat.setText(uppdrag);
        }
        catch (InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    

    /**
     * Metoden hämtar alla värden från kolumnen "beteckning" från tabellen "Spelprojekt"
     * samt matchar de värden som finns i tabellen "Anstalld" där de värden från kolumnen
     * "releasedatum" är senare än dagens datum samt att kolumnen "namn" matchar fältet "rubrikNamn".
     * Om det faktiskt är några värden som hämtas så raderas alla värden i den angivna comboBoxen som visar pågående uppdrag
     * och sätts i en lokal ArrayList. Därefter körs en for-loop som hämtar alla indexvärden
     * från arraylistan och sätter in dessa i den angivna comboBoxen som visar pågående uppdrag.
     */
    private void setPågåendeUppdrag()
    {
        String sqlFraga = "select beteckning from spelprojekt\n" +
"join anstalld on spelprojekt.aid=anstalld.aid\n" +
"where releasedatum > '"+VisaAllt.getDatum()+"' and\n" +
"namn = '"+rubrikNamn+"'";
        
        try
        {
            ArrayList<String> pågåendeUppdragLista = idb.fetchColumn(sqlFraga);
            if(!(pågåendeUppdragLista.isEmpty()))
            {
                cbPågåendeUppdrag.removeAllItems();
                for(int i=0; i < pågåendeUppdragLista.size(); i++)
            {
                cbPågåendeUppdrag.addItem(pågåendeUppdragLista.get(i));
            }
                
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    

    /**
     * Metoden hämtar alla värden från kolumnen "beteckning" från tabellen "Spelprojekt"
     * samt matchar de värden som finns i tabellen "Anstalld" där de värden från kolumnen
     * "releasedatum" är tidigare än dagens datum samt att kolumnen "namn" matchar fältet "rubrikNamn".
     * Om det faktiskt är några värden som hämtas så raderas alla värden i den angivna comboBoxen som visar avslutade uppdrag
     * och sätts i en lokal ArrayList. Därefter körs en for-loop som hämtar alla indexvärden
     * från arraylistan och sätter in dessa i den angivna comboBoxen som visar avslutade uppdrag
     */
    private void setAvslutadeUppdrag()
    {
        String sqlFraga = "select beteckning from spelprojekt\n" +
"join anstalld on spelprojekt.aid=anstalld.aid\n" +
"where releasedatum < '"+VisaAllt.getDatum()+"' and\n" +
"namn = '"+rubrikNamn+"'";
        try
        {
            ArrayList<String> avslutadeUppdragLista = idb.fetchColumn(sqlFraga);
            if(!(avslutadeUppdragLista.isEmpty()))
            {
                cbAvslutadeUppdrag.removeAllItems();
                
                for(int i=0; i < avslutadeUppdragLista.size(); i++)
            {
                cbAvslutadeUppdrag.addItem(avslutadeUppdragLista.get(i));
            }
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Metoden hämtar alla värden från kolumnen "telefon", från tabellen "Anstalld"
     * som är på samma där kolumnen "namn" matchar fältet "rubrikNamn".
     * Därefter tilldelas den angivna JLabeln som visar telefonnummer det värde som hämtats från databasen.
     */
    private void setTelefon()
    {
        String sqlFraga = "select telefon from anstalld \n" +
"where namn = '"+rubrikNamn+"'";
        
        try
        {
            String telefon = idb.fetchSingle(sqlFraga);
            lblTelefonResultat.setText(telefon);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Metoden hämtar alla värden från kolumnen "mail", från tabellen "Anstalld"
     * som är på samma där kolumnen "namn" matchar fältet "rubrikNamn".
     * Därefter tilldelas den angivna JLabeln som visar mejladressen det värde som hämtats från databasen.
     */
    private void setMail()
    {
        String sqlFraga = "select mail from anstalld\n" +
"where namn = '"+rubrikNamn+"'";
        
        try
        {
            String mail = idb.fetchSingle(sqlFraga);
            lblMailResultat.setText(mail);
        }
        catch (InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblAnstalldNamn = new javax.swing.JLabel();
        lblAntalUppdrag = new javax.swing.JLabel();
        lblAntalUppdragResultat = new javax.swing.JLabel();
        lblPågendeUppdrag = new javax.swing.JLabel();
        cbPågåendeUppdrag = new javax.swing.JComboBox();
        lblAvslutadeUppdrag = new javax.swing.JLabel();
        cbAvslutadeUppdrag = new javax.swing.JComboBox();
        lblTelefon = new javax.swing.JLabel();
        lblTelefonResultat = new javax.swing.JLabel();
        lblMail = new javax.swing.JLabel();
        lblMailResultat = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblAnstalldNamn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblAnstalldNamn.setText("jLabel1");

        lblAntalUppdrag.setText("Antal uppdrag:");

        lblAntalUppdragResultat.setText("jLabel2");

        lblPågendeUppdrag.setText("Pågående uppdrag:");

        cbPågåendeUppdrag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Inga uppdrag" }));

        lblAvslutadeUppdrag.setText("Avslutade uppdrag:");

        cbAvslutadeUppdrag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Inga uppdrag" }));

        lblTelefon.setText("Telefon:");

        lblTelefonResultat.setText("jLabel2");

        lblMail.setText("Mail:");

        lblMailResultat.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAvslutadeUppdrag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbAvslutadeUppdrag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblAntalUppdrag)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAntalUppdragResultat))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(lblAnstalldNamn))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblPågendeUppdrag)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbPågåendeUppdrag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMail)
                            .addComponent(lblTelefon))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefonResultat, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMailResultat)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(61, 61, 61))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblAnstalldNamn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAntalUppdrag)
                    .addComponent(lblAntalUppdragResultat))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPågendeUppdrag)
                    .addComponent(cbPågåendeUppdrag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAvslutadeUppdrag)
                    .addComponent(cbAvslutadeUppdrag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefon)
                    .addComponent(lblTelefonResultat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMail)
                    .addComponent(lblMailResultat))
                .addGap(40, 40, 40))
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VisaProjektledare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisaProjektledare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisaProjektledare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisaProjektledare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisaProjektledare(rubrikNamn).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbAvslutadeUppdrag;
    private javax.swing.JComboBox cbPågåendeUppdrag;
    private javax.swing.JLabel lblAnstalldNamn;
    private javax.swing.JLabel lblAntalUppdrag;
    private javax.swing.JLabel lblAntalUppdragResultat;
    private javax.swing.JLabel lblAvslutadeUppdrag;
    private javax.swing.JLabel lblMail;
    private javax.swing.JLabel lblMailResultat;
    private javax.swing.JLabel lblPågendeUppdrag;
    private javax.swing.JLabel lblTelefon;
    private javax.swing.JLabel lblTelefonResultat;
    // End of variables declaration//GEN-END:variables
}
