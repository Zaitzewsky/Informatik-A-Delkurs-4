/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

public class VisaAllt extends javax.swing.JFrame {

 private InformatikDB idb;
 
 
 public VisaAllt() {
        initComponents();
        
try
        {
            idb = new InformatikDB(pathway());
        }
        catch(InformatikException e)
                {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
         laggTillPlattformCombobox();
         laggTillKompetensdomänComboBox();
         laggTillProjektledareComboBox();
         laggTillSpecialistComboBox();
         laggTillPågåendeSpelProjektComboBox();
         laggTillAvslutadeSpelProjektComboBox();
        
    }
 
 public static String pathway()
   {
   String path = System.getProperty("user.dir") + "//MICEDB.FDB";
   
   return path;
   
   }
 
 /**
  * Metoden laggTillPlattformCombobox() hämtar alla värden från kolumnen "benamning",
  * från tabellen Plattform, från databasen och lägger in alla dessa värden i en lokal ArrayList.
  * Därefter körs en for-loop som hämtar indexvärdena från arraylistan och lägger in dessa i comboBoxen
  * där användaren kan välja plattformar. 
  */ 
  private void laggTillPlattformCombobox()
    {
        String sqlFraga = "select benamning from plattform;";
        try
        {
            ArrayList<String> plattformslista = idb.fetchColumn(sqlFraga);
            
            for(int i=0; i < plattformslista.size(); i++)
            {
                cbPlattformBeteckning.addItem(plattformslista.get(i));
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
  
 /**
  * Metoden laggTillKompetensdomänCombobox() hämtar alla värden från kolumnen "benamning",
  * från tabellen Kompetensdoman, från databasen och lägger in alla dessa värden i en lokal ArrayList.
  * Därefter körs en for-loop som hämtar indexvärdena från arraylistan och lägger in dessa i comboBoxen
  * där användaren kan välja kompetenserdomäner. 
  */ 
  private void laggTillKompetensdomänComboBox()
  {
      String sqlFraga = "select benamning from KOMPETENSDOMAN";
      
      try
      {
          ArrayList<String> kompetensLista = idb.fetchColumn(sqlFraga);
          
          for(int i=0; i < kompetensLista.size(); i++)
          {
              cbKompetens.addItem(kompetensLista.get(i));
          }
      }
      catch (InformatikException e)
      {
          JOptionPane.showMessageDialog(null, e.getMessage());
      }
  }
  
 /**
  * Metoden laggTillProjektledareCombobox() hämtar alla värden från kolumnen "namn",
  * från tabellen Anstalld som matchar de namn som finns i tabellen Projektledare,
  * från databasen och lägger in alla dessa värden i en lokal ArrayList.
  * Därefter körs en for-loop som hämtar indexvärdena från arraylistan och lägger in dessa i
  * den comboBoxen där användaren kan välja projektledare. 
  */ 
  private void laggTillProjektledareComboBox()
  {
      String sqlFraga = "select namn from anstalld join PROJEKTLEDARE on ANSTALLD.aid=PROJEKTLEDARE.aid";
      try
      {
          ArrayList<String> projektledarLista = idb.fetchColumn(sqlFraga);
          
          for (int i=0; i < projektledarLista.size(); i++)
          {
              cbProjektledare.addItem(projektledarLista.get(i));
          }
      }
      catch(InformatikException e)
      {
          JOptionPane.showMessageDialog(null, e.getMessage());
      }
  }
  
    /**
     * Metoden laggTillProjektledareCombobox() hämtar alla värden från kolumnen "namn",
     * från tabellen Anstalld som matchar de namn som finns i tabellen Projektledare,
     * databasen och lägger in alla dessa värden i en lokal ArrayList.
     * Därefter körs en for-loop som hämtar indexvärdena från arraylistan och lägger in dessa i
     * den comboBox där användaren kan välja specialister. 
     */ 
  public void laggTillSpecialistComboBox()
  {
      String sqlFraga = "select namn from anstalld\n" +
"join SPECIALIST on SPECIALIST.AID=ANSTALLD.AID";
      try
      {
          ArrayList<String> specialistLista = idb.fetchColumn(sqlFraga);
          
          for(int i=0; i < specialistLista.size(); i++)
          {
              cbSpecialist.addItem(specialistLista.get(i));
          }
      }
      catch(InformatikException e)
      {
          JOptionPane.showMessageDialog(null, e.getMessage());
      }
  }

    /**
     * Metoden getDatum() instansierar två lokala variabler (Date och SimpleDateFormat)
     * och returnerar dagens datum med manuellt formaterad utskrift av dagens datum.
     * @return
     */
    public static String getDatum()
  {
      Date datum = new Date();
      SimpleDateFormat datumFormat = new SimpleDateFormat("dd.MM.yyyy");
      
      return datumFormat.format(datum);
  }
    
  
    /**
     * Metoden laggTillPågåendeSpelProjektCombobox() hämtar alla värden från kolumnen "beteckning",
     * från tabellen Spelprojekt som har datum i kolumnen "releasedatum" senare än dagens datum.
     * Detta kollas genom att använda metoden getDatum(). Värdena hämtas från
     * databasen och lägger in alla dessa värden i en lokal ArrayList.
     * Därefter körs en for-loop som hämtar indexvärdena från arraylistan och lägger in dessa i den
     * comboBox där användaren kan välja pågående spelprojekt. 
     */
  private void laggTillPågåendeSpelProjektComboBox()
  {
      String sqlFraga = "select beteckning from SPELPROJEKT\n" +
"where releasedatum > '"+getDatum()+"'";
      try
      {
          ArrayList<String> spelProjektLista = idb.fetchColumn(sqlFraga);
          
          for(int i=0; i < spelProjektLista.size(); i++)
          {
              cbPågåendeProj.addItem(spelProjektLista.get(i));
          }
      }
      catch(InformatikException e)
      {
          JOptionPane.showMessageDialog(null, e.getMessage());
      }
  }
  
  /**
   * Metoden laggTillAvslutadeSpelProjektCombobox() hämtar alla värden från kolumnen "beteckning",
   * från tabellen Spelprojekt som har datum i kolumnen "releasedatum" tidigare än dagens datum.
   * Detta kollas genom att använda metoden getDatum(). Värdena hämtas från
   * databasen och lägger in alla dessa värden i en lokal ArrayList.
   * Därefter körs en for-loop som hämtar indexvärdena från arraylistan och lägger in dessa i den
   * comboBox där användaren kan välja pågående spelprojekt
   */
  private void laggTillAvslutadeSpelProjektComboBox()
  {
     String sqlFraga = "select beteckning from SPELPROJEKT\n" +
"where releasedatum < '"+getDatum()+"'";
     try
     {
         ArrayList<String> spelProjektLista = idb.fetchColumn(sqlFraga);
         
         for(int i=0; i < spelProjektLista.size(); i++)
         {
             cbAvslutadeProj.addItem(spelProjektLista.get(i));
         }
     }
     catch(InformatikException e)
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

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        lblPågendeProj = new javax.swing.JLabel();
        cbPågåendeProj = new javax.swing.JComboBox();
        cbAvslutadeProj = new javax.swing.JComboBox();
        lblAvslutadeProj = new javax.swing.JLabel();
        btnAvslutadeProjekt = new javax.swing.JButton();
        btnPågåendeProjekt = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        cbSpecialist = new javax.swing.JComboBox();
        btnVisaSpecialist = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cbProjektledare = new javax.swing.JComboBox();
        btnProjektledare = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        cbKompetens = new javax.swing.JComboBox();
        btnKompetens = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        cbPlattformBeteckning = new javax.swing.JComboBox();
        btnPlattform = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblPågendeProj.setText("Pågende projekt:");

        lblAvslutadeProj.setText("Avslutade projekt:");

        btnAvslutadeProjekt.setText("Visa");
        btnAvslutadeProjekt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAvslutadeProjektMouseClicked(evt);
            }
        });

        btnPågåendeProjekt.setText("Visa");
        btnPågåendeProjekt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPågåendeProjektMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPågendeProj)
                    .addComponent(lblAvslutadeProj))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbAvslutadeProj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPågåendeProj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPågåendeProjekt)
                    .addComponent(btnAvslutadeProjekt))
                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPågendeProj)
                            .addComponent(cbPågåendeProj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnPågåendeProjekt)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbAvslutadeProj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAvslutadeProjekt))
                    .addComponent(lblAvslutadeProj))
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Projekt", jPanel2);

        btnVisaSpecialist.setText("Visa");
        btnVisaSpecialist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVisaSpecialistMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(cbSpecialist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(250, Short.MAX_VALUE)
                .addComponent(btnVisaSpecialist)
                .addGap(94, 94, 94))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(cbSpecialist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnVisaSpecialist)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Specialister", jPanel3);

        cbProjektledare.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbProjektledareMouseClicked(evt);
            }
        });

        btnProjektledare.setText("Visa");
        btnProjektledare.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProjektledareMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(cbProjektledare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(262, Short.MAX_VALUE)
                .addComponent(btnProjektledare)
                .addGap(82, 82, 82))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(cbProjektledare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btnProjektledare)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Projektledare", jPanel4);

        btnKompetens.setText("Visa");
        btnKompetens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKompetensMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(cbKompetens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(247, Short.MAX_VALUE)
                .addComponent(btnKompetens)
                .addGap(97, 97, 97))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(cbKompetens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(btnKompetens)
                .addGap(59, 59, 59))
        );

        jTabbedPane2.addTab("Kompetensdomäner", jPanel5);

        btnPlattform.setText("Visa");
        btnPlattform.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPlattformMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPlattform)
                .addGap(55, 55, 55))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(cbPlattformBeteckning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(249, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(cbPlattformBeteckning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(btnPlattform)
                .addGap(53, 53, 53))
        );

        jTabbedPane2.addTab("Plattformar", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * När användaren klickar på knappen så hämtas det valda värdet från comboBoxen där användaren valt plattform.
     * Därefter instansieras ett nytt objekt som visar den valda plattformen där värdet från comboBoxen sätts in i det nya
     * objektets paramaterlista.
     * @param evt 
     */
    private void btnPlattformMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPlattformMouseClicked
        String namn = cbPlattformBeteckning.getSelectedItem().toString();
        VisaSpecifikPlattform nyPlattform = new VisaSpecifikPlattform(namn);
        nyPlattform.setVisible(true);
    }//GEN-LAST:event_btnPlattformMouseClicked

    /**
     * När användaren klickar på knappen så hämtas det valda värdet från comboBoxen där användaren valt kompetensdomän.
     * Därefter instansieras ett nytt objekt som visar vald kompetensdomän där värdet från comboBoxen sätts in i det nya
     * objektets paramaterlista.
     * @param evt 
     */
    private void btnKompetensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKompetensMouseClicked
        String namn = cbKompetens.getSelectedItem().toString();
        VisaKompetensDomän nyKompetens = new VisaKompetensDomän(namn);
        nyKompetens.setVisible(true);
    }//GEN-LAST:event_btnKompetensMouseClicked

    private void cbProjektledareMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbProjektledareMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cbProjektledareMouseClicked

    /**
     * När användaren klickar på knappen så hämtas det valda värdet från comboBoxen där användaren valt projektledare.
     * Därefter instansieras ett nytt objekt som visar vald projektledare där värdet från comboBoxen sätts in i det nya
     * objektets paramaterlista.
     * @param evt 
     */
    private void btnProjektledareMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProjektledareMouseClicked
        String namn = cbProjektledare.getSelectedItem().toString();
        VisaProjektledare nyProjektledare = new VisaProjektledare(namn);
        nyProjektledare.setVisible(true);
    }//GEN-LAST:event_btnProjektledareMouseClicked

    /**
     * När användaren klickar på knappen så hämtas det valda värdet från comboBoxen där användaren valt specialist.
     * Därefter instansieras ett nytt objekt som visar vald specialist där värdet från comboBoxen sätts in i det nya
     * objektets paramaterlista.
     * @param evt 
     */
    private void btnVisaSpecialistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVisaSpecialistMouseClicked
        String namn = cbSpecialist.getSelectedItem().toString();
        VisaSpecialist nySpecialist = new VisaSpecialist(namn);
        nySpecialist.setVisible(true);
    }//GEN-LAST:event_btnVisaSpecialistMouseClicked

    /**
     * När användaren klickar på knappen så hämtas det valda värdet från comboBoxen där användaren valt pågående projekt.
     * Därefter instansieras ett nytt objekt som visar valt pågående projekt där värdet från comboBoxen sätts in i det nya
     * objektets paramaterlista.
     * @param evt 
     */
    private void btnPågåendeProjektMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPågåendeProjektMouseClicked
        String namn = cbPågåendeProj.getSelectedItem().toString();
        VisaProjekt nyProjekt = new VisaProjekt(namn);
        nyProjekt.setVisible(true);
    }//GEN-LAST:event_btnPågåendeProjektMouseClicked

    /**
     * När användaren klickar på knappen så hämtas det valda värdet från comboBoxen där användaren valt avslutade projekt.
     * Därefter instansieras ett nytt objekt som visar valt avslutat projekt där värdet från comboBoxen sätts in i det nya
     * objektets paramaterlista.
     * @param evt 
     */
    private void btnAvslutadeProjektMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAvslutadeProjektMouseClicked
        String namn = cbAvslutadeProj.getSelectedItem().toString();
        VisaProjekt nyProjekt = new VisaProjekt(namn);
        nyProjekt.setVisible(true);
    }//GEN-LAST:event_btnAvslutadeProjektMouseClicked

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
            java.util.logging.Logger.getLogger(VisaAllt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisaAllt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisaAllt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisaAllt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisaAllt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvslutadeProjekt;
    private javax.swing.JButton btnKompetens;
    private javax.swing.JButton btnPlattform;
    private javax.swing.JButton btnProjektledare;
    private javax.swing.JButton btnPågåendeProjekt;
    private javax.swing.JButton btnVisaSpecialist;
    private javax.swing.JComboBox cbAvslutadeProj;
    private javax.swing.JComboBox cbKompetens;
    private javax.swing.JComboBox cbPlattformBeteckning;
    private javax.swing.JComboBox cbProjektledare;
    private javax.swing.JComboBox cbPågåendeProj;
    private javax.swing.JComboBox cbSpecialist;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblAvslutadeProj;
    private javax.swing.JLabel lblPågendeProj;
    // End of variables declaration//GEN-END:variables
}
