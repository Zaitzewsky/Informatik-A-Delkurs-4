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

public class VisaProjekt extends javax.swing.JFrame {

    private InformatikDB idb;
    private static String rubrikNamn;
    
    public VisaProjekt(String rubrikNamn) {
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
         laggTillRubrik();
         laggTillAnstallda();
         laggTillProjektLedare();
         laggTillPlattformar();
         setReleasedatum();
         setStartdatum();
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
private void laggTillRubrik()
{
    lblRubrikNamn.setText(rubrikNamn);
}


/**
 * Metoden hämtar alla värden från kolumnen "releasedatum", från tabellen "Spelprojekt",
 * från samma rad där kolumnen "beteckning" matchar fältet "rubrikNamn".
 * Därefter sätts den jLabel som visar releasedatum med det värde som hämtats från databasen.
 */
private void setReleasedatum()
{
    String sqlFraga = "select releasedatum from SPELPROJEKT\n" +
"where beteckning = '"+rubrikNamn+"'";
    
    try
    {
        String releasedatum = idb.fetchSingle(sqlFraga);
        lblReleasedatumResultat.setText(releasedatum);
    }
    catch(InformatikException e)
    {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}


/**
 * Metoden hämtar alla värden från kolumnen "startdatum", från tabellen "Spelprojekt",
 * från samma rad där kolumnen "beteckning" matchar fältet "rubrikNamn".
 * Därefter sätts den jLabel som visar startdatum med det värde som hämtats från databasen.
 */
private void setStartdatum()
{
        String sqlFraga = "select startdatum from SPELPROJEKT\n" +
"where beteckning = '"+rubrikNamn+"'";
    
    try
    {
        String releasedatum = idb.fetchSingle(sqlFraga);
        lblStartdatumResultat.setText(releasedatum);
    }
    catch(InformatikException e)
    {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}

/**
 * Metoden hämtar alla värden från kolumnen "namn" från tabellen "Anstalld"
 * som matchar de värden som finns i tabellen "Spelprojekt" som är på
 * samma rad som fältet "rubrikNamn".
 * Därefter sätts den jLabel som visar projektledaren det värde som hämtats från databasen.
 */
private void laggTillProjektLedare()
{
    String sqlFraga = "select namn from anstalld\n" +
"join SPELPROJEKT on SPELPROJEKT.aid=ANSTALLD.AID\n" +
"where beteckning = '"+rubrikNamn+"'";
    
   try
   {
       String projektLedare = idb.fetchSingle(sqlFraga);
       lblProjektledareResultat.setText(projektLedare);
   }
   catch(InformatikException e)
   {
       JOptionPane.showMessageDialog(null, e.getMessage());
   }
}

/**
 * Metoden hämtar alla värden från kolumnen "namn" från tabellen "Anstalld"
 * som matchar de AIDn som finns i tabellen "Arbetar_i" och SIDn i tabellen "Spelprojekt"
 * som finns på samma rad som "rubrikNamn". Ifall några värden faktiskt hämtas så
 * raderas alla befintliga värden i den comboBox som visar anställda samt att 
 * värdena sätts in i en lokal ArrayList. Därefter körs en for-loop
 * som hämtar alla indexvärden i arraylistan och sätter in dessa i den angivna
 * comboBoxen som visar anställda.
 */
private void laggTillAnstallda()
{
    String sqlFraga = "select namn from anstalld\n" +
"join arbetar_i on anstalld.aid=arbetar_i.aid\n" +
"join SPELPROJEKT on SPELPROJEKT.SID=ARBETAR_I.SID\n" +
"where beteckning = '"+rubrikNamn+"'";
    
    try
    {
        ArrayList<String> anstalldaLista = idb.fetchColumn(sqlFraga);
        if(!(anstalldaLista.isEmpty()))
        {
            cbAnstalldaProj.removeAllItems();
            
            for(int i=0; i < anstalldaLista.size(); i++)
        {
            cbAnstalldaProj.addItem(anstalldaLista.get(i));
        }
        }
    }
    catch(InformatikException e)
    {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
    
}


/**
 * Metoden hämtar alla värden från kolumnen "benamning" från tabellen "Plattform"
 * som matchar de PIDn som finns i tabellen "Innefattar" och SIDn i tabellen "Spelprojekt"
 * som finns på samma rad som "rubrikNamn". Ifall några värden faktiskt hämtas så
 * raderas alla befintliga värden i den angivna comboBoxen som visar plattformar samt att 
 * värdena sätts in i en lokal ArrayList. Därefter körs en for-loop
 * som hämtar alla indexvärden i arraylistan och sätter in dessa i den angivna
 * comboBoxen som visar plattformar.
 */
private void laggTillPlattformar()
{
    String sqlFraga = "select benamning from PLATTFORM\n" +
"join INNEFATTAR on plattform.pid=INNEFATTAR.PID\n" +
"join SPELPROJEKT on SPELPROJEKT.SID=INNEFATTAR.SID\n" +
"where SPELPROJEKT.BETECKNING= '"+rubrikNamn+"'";
    
    try
    {
        ArrayList<String> plattformLista = idb.fetchColumn(sqlFraga);
        if(!(plattformLista.isEmpty()))
        {
            cbPlattformar.removeAllItems();
            for(int i=0; i < plattformLista.size(); i++)
        {
            cbPlattformar.addItem(plattformLista.get(i));
        }
        }
    }
    catch(InformatikException e)
    {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblRubrikNamn = new javax.swing.JLabel();
        lblAnstalldaProj = new javax.swing.JLabel();
        cbAnstalldaProj = new javax.swing.JComboBox();
        lblProjektledare = new javax.swing.JLabel();
        lblProjektledareResultat = new javax.swing.JLabel();
        lblPlattformar = new javax.swing.JLabel();
        cbPlattformar = new javax.swing.JComboBox();
        lblStartdatum = new javax.swing.JLabel();
        lblStartdatumResultat = new javax.swing.JLabel();
        lblReleasedatum = new javax.swing.JLabel();
        lblReleasedatumResultat = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblRubrikNamn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblRubrikNamn.setText("jLabel1");

        lblAnstalldaProj.setText("Anställda som arbetar på detta projekt:");

        cbAnstalldaProj.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Inga anställda" }));

        lblProjektledare.setText("Projektledare:");

        lblProjektledareResultat.setText("jLabel2");

        lblPlattformar.setText("Plattformar:");

        cbPlattformar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Inga plattformar" }));

        lblStartdatum.setText("Startdatum:");

        lblStartdatumResultat.setText("jLabel1");

        lblReleasedatum.setText("Releasedatum:");

        lblReleasedatumResultat.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProjektledare)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblProjektledareResultat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAnstalldaProj)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbAnstalldaProj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(165, 165, 165))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(lblRubrikNamn))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblStartdatum)
                            .addComponent(lblPlattformar)
                            .addComponent(lblReleasedatum))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbPlattformar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStartdatumResultat)
                            .addComponent(lblReleasedatumResultat))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblRubrikNamn)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjektledare)
                    .addComponent(lblProjektledareResultat))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbAnstalldaProj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAnstalldaProj))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPlattformar)
                    .addComponent(cbPlattformar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStartdatum)
                    .addComponent(lblStartdatumResultat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReleasedatum)
                    .addComponent(lblReleasedatumResultat))
                .addGap(0, 141, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(VisaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisaProjekt(rubrikNamn).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbAnstalldaProj;
    private javax.swing.JComboBox cbPlattformar;
    private javax.swing.JLabel lblAnstalldaProj;
    private javax.swing.JLabel lblPlattformar;
    private javax.swing.JLabel lblProjektledare;
    private javax.swing.JLabel lblProjektledareResultat;
    private javax.swing.JLabel lblReleasedatum;
    private javax.swing.JLabel lblReleasedatumResultat;
    private javax.swing.JLabel lblRubrikNamn;
    private javax.swing.JLabel lblStartdatum;
    private javax.swing.JLabel lblStartdatumResultat;
    // End of variables declaration//GEN-END:variables
}
