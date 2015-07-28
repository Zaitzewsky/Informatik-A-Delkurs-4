package mice;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import se.oru.informatik.InformatikDB;
import se.oru.informatik.InformatikException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import static mice.LäggTillAllt.pathway;

public class VisaSpecialist extends javax.swing.JFrame {

private InformatikDB idb;
private static String rubrikNamn;

    public VisaSpecialist(String rubrikNamn) {
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
         setSpecialistNamn();
         laggTillAvslutadeProjekt();
         laggTillPlattformsKompetensComboBox();
         setMail();
         setTelefon();
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
    private void setSpecialistNamn()
    {
        lblRubrik.setText(rubrikNamn);
    }
    
    /**
     * Metoden hämtar alla värden från kolumnen "beteckning" från tabellen "Spelprojekt"
     * samt matchar de värden som finns i tabellen "Anstalld" och tabellen "Arbetar_i" där de värden från kolumnen
     * "releasedatum" är tidigare än dagens datum samt att kolumnen "namn" matchar fältet "rubrikNamn".
     * Om det faktiskt är några värden som hämtas så raderas alla värden i den angivna comboBoxen som visar avslutade projekt
     * och sätts i en lokal ArrayList. Därefter körs en for-loop som hämtar alla indexvärden
     * från arraylistan och sätter in dessa i den angivna comboBoxen som visar avslutade projekt.
     */
    private void laggTillAvslutadeProjekt()
    {
        String sqlFraga = "select beteckning from SPELPROJEKT\n" +
"join ARBETAR_I on SPELPROJEKT.SID=ARBETAR_I.SID\n" +
"join ANSTALLD on anstalld.AID=ARBETAR_I.AID\n" +
"where releasedatum < '"+VisaAllt.getDatum()+"'\n" +
"and namn = '"+rubrikNamn+"'";
       try
        {
            ArrayList<String> avslutadeProjektLista = idb.fetchColumn(sqlFraga);
            if(!(avslutadeProjektLista.isEmpty()))
            {
                cbAvslutadeProjekt.removeAllItems();
                for(int i=0; i < avslutadeProjektLista.size(); i++)
            {
                    cbAvslutadeProjekt.addItem(avslutadeProjektLista.get(i));
            }
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
   
    /**
     * Metoden hämtar alla värden från kolumnerna benamning (från tabellen plattform), benamning (från tabellen kompetensdoman)
     * och kompetensniva (från har_kompetens) som matchar de PIDn, AIDn och KIDn som finns i tabellen "Har_kompetens".
     * Om det faktiskt är några värden som hämtas så raderas alla värden i den angivna comboBoxen som visar plattformskompetens
     * och sätts i en lokal ArrayList. Därefter körs en for-loop som hämtar alla indexvärden
     * från arraylistan och sätter in dessa i den angivna comboBoxen som visar plattformskompetens.
     */
    private void laggTillPlattformsKompetensComboBox()
    {
        String sqlFraga = "select plattform.BENAMNING, KOMPETENSDOMAN.BENAMNING, kompetensniva from plattform\n" +
"join HAR_KOMPETENS on plattform.PID=HAR_KOMPETENS.PID\n" +
"join anstalld on ANSTALLD.AID=HAR_KOMPETENS.AID\n" +
"join KOMPETENSDOMAN on KOMPETENSDOMAN.KID=HAR_KOMPETENS.KID\n" +
"where namn = '"+rubrikNamn+"'";
        try
        {
            ArrayList<ArrayList<String>> kompetenslista = idb.fetchMatrix(sqlFraga);
            if(!(kompetenslista.isEmpty()))
            {
                cbKompetens.removeAllItems();
                for(int i=0; i < kompetenslista.size(); i++)
            {
                    cbKompetens.addItem(kompetenslista.get(i));
            }
            }
        }
        catch (InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
            
        }
    }
    
     /**
     * Metoden hämtar alla värden från kolumnen "telefon", från tabellen "Anstalld"
     * som är på samma där kolumnen "namn" matchar fältet "rubrikNamn".
     * Därefter tilldelas den angivna JLabeln, som visar telefonnummret, det värde som hämtats från databasen.
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
     * Därefter tilldelas den angivna JLabeln, som visar mejladressen, det värde som hämtats från databasen.
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

        lblRubrik = new javax.swing.JLabel();
        lblAvslutadeProjekt = new javax.swing.JLabel();
        cbAvslutadeProjekt = new javax.swing.JComboBox();
        lblPlattformskompetens = new javax.swing.JLabel();
        cbKompetens = new javax.swing.JComboBox();
        lblTelefon = new javax.swing.JLabel();
        lblTelefonResultat = new javax.swing.JLabel();
        lblMail = new javax.swing.JLabel();
        lblMailResultat = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblRubrik.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblRubrik.setText("jLabel1");

        lblAvslutadeProjekt.setText("Avslutade projekt:");

        cbAvslutadeProjekt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Inga projekt" }));

        lblPlattformskompetens.setText("Kompetenser:");

        cbKompetens.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ingen kompetens" }));

        lblTelefon.setText("Telefon:");

        lblTelefonResultat.setText("jLabel2");

        lblMail.setText("Mail:");

        lblMailResultat.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(lblRubrik))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPlattformskompetens)
                            .addComponent(lblAvslutadeProjekt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbKompetens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbAvslutadeProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMail)
                            .addComponent(lblTelefon))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefonResultat)
                            .addComponent(lblMailResultat))))
                .addContainerGap(168, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblRubrik)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAvslutadeProjekt)
                    .addComponent(cbAvslutadeProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPlattformskompetens)
                    .addComponent(cbKompetens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefon)
                    .addComponent(lblTelefonResultat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMail)
                    .addComponent(lblMailResultat))
                .addGap(45, 45, 45))
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
            java.util.logging.Logger.getLogger(VisaSpecialist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisaSpecialist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisaSpecialist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisaSpecialist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisaSpecialist(rubrikNamn).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbAvslutadeProjekt;
    private javax.swing.JComboBox cbKompetens;
    private javax.swing.JLabel lblAvslutadeProjekt;
    private javax.swing.JLabel lblMail;
    private javax.swing.JLabel lblMailResultat;
    private javax.swing.JLabel lblPlattformskompetens;
    private javax.swing.JLabel lblRubrik;
    private javax.swing.JLabel lblTelefon;
    private javax.swing.JLabel lblTelefonResultat;
    // End of variables declaration//GEN-END:variables
}
