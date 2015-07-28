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
import com.toedter.calendar.JDateChooser;

public class LäggTillAllt extends javax.swing.JFrame {

    private InformatikDB idb;
    private ArrayList<String> aidLista; // ArrayList för att hålla AIDn som ska
                                        // läggas in när man lägger till ett nytt projekt.
    private ArrayList<String> pidLista; // ArrayList för att hålla PIDn som ska
                                        // läggas in när man lägger till ett nytt projekt.
    private ArrayList<String> aidListaUppdatera; // ArrayList för att hålla AIDn som ska
                                                 // läggas in när man uppdaterar ett projekt.
    private ArrayList<String> pidListaUppdatera; // ArrayList för att hålla PIDn som ska
                                                 // läggas in när man uppdaterar ett projekt.
    private String antalLeddaProjekt; // Variabel som ska hålla det värde som hämtas
                                      // från kolumnen "antal_projetledar_uppdrag" från tabellen "Projektledare".    
    public LäggTillAllt() {
        initComponents();
        setResizable(false);
        aidLista = new ArrayList<String>();
        pidLista = new ArrayList<String>();
        aidListaUppdatera = new ArrayList<String>();
        pidListaUppdatera = new ArrayList<String>();
        
         try
        {
            idb = new InformatikDB(pathway());
        }
        catch(InformatikException e)
                {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
         
         laggTillProjektledare();
         laggTillProjektledareUppdatera();
         laggTillSpecialistProjekt();
         laggTillPlattformProjekt();
         laggTillProjektAttVäljaUppdateraProjekt();
         laggTillKompetensdomänerUppdateraKompetensdomän();
         laggTillPlattformLaggTillAnstalld();
         fillCompCbAdd();
         fillCompCbUpd();
         laggTillAnstalldaAttUppdatera();
         laggTillPlattformUppdateraAnstalld();
         laggInPlattformarIFlikenUppdatera();
    }
    
   public static String pathway()
   {
   String path = System.getProperty("user.dir") + "//MICEDB.FDB";
   
   return path;
   
   }
    /**
     * Nollställer alla värden genom att sätta alla textfält under fliken "Lägg till", under fliken "Plattform"
     * med ("").
     */
    private void nollStällAllaVärdenLaggTillPlattform()
    {
        txtNewPlatBen.setText("");
        txtNewPlatBesk.setText("");
        txtNewPlatProd.setText("");
        lblFelmeddelandeLaggTillPlattformBenamning.setText("");
        lblFelmeddelandeLaggTillPlattformBeskrivning.setText("");
        lblFelmeddelandeLaggTillPlattformProducent.setText("");
    }
    
    /**
     * Nollställer alla värden genom att sätta alla textfält under fliken "Uppdatera", under fliken "Plattform"
     * med ("").
     */
    private void nollStällAllaVärdenUppdateraPlattform()
    {
        txtUpdPlatBen.setText("");
        txtUpdPlatBesk.setText("");
        txtUpdPlatProd.setText("");
        lblFelmeddelandeUppdateraPlattformBenaming.setText("");
        lblFelmeddelandeUppdateraPlattformBeskrivning.setText("");
        lblFelmeddelandeUppdateraPlattformProducent.setText("");
    }
    
    /**
     * Metoden hämtar ut alla befintliga plattformar och dess benämningar som är redan existerande i databasen och lägger in dessa
     * i comboboxen under flike "uppdatera plattformar"
     */
    public void laggInPlattformarIFlikenUppdatera() {
        
       String sqlFraga = "select benamning, pid from Plattform";
       try
       {
           ArrayList<HashMap<String,String>> platforms = idb.fetchRows(sqlFraga);
           String laggTillPlattform = null;
            for(int i = 0;i< platforms.size();i++)
            {
                laggTillPlattform = platforms.get(i).get("pid");
                laggTillPlattform = laggTillPlattform + ". " + platforms.get(i).get("benamning");
                cbCurrentPlats.addItem(laggTillPlattform);
            }
       }      
       catch(InformatikException e)
       {
          JOptionPane.showMessageDialog(null, e.getMessage());
       }
        
        
    }
    
    /**
     * Metoden hämtar ut värdet från redigeringsrutan under fliken "uppdatera plattform" för producent och sätter in det nya värdet i en SQL fråga
     * som riktar sig till det valda värdet i comboboxen.
     */
    private void updatePlatformProducer() {
        String newPlatProd = txtUpdPlatProd.getText();
        String pid = cbCurrentPlats.getSelectedItem().toString().substring(0, cbCurrentPlats.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "UPDATE PLATTFORM SET PRODUCENT = '"+newPlatProd+"' WHERE pid="+pid+"";
        
        try {
                   
                idb.update(sqlFraga);             
                
                }
                
                catch(InformatikException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    
                }
}
    
    /**
     * Metoden hämtar ut värdet från redigeringsrutan under fliken "uppdatera plattform" för beskrivning och sätter in det nya värdet i en SQL fråga
     * som riktar sig till det valda värdet i comboboxen.
     */
    public void updatePlatformBeskrivning(){
        
        String newPlatBesk = txtUpdPlatBesk.getText();
        String pid = cbCurrentPlats.getSelectedItem().toString().substring(0, cbCurrentPlats.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "UPDATE PLATTFORM SET BESKRIVNING = '"+newPlatBesk+"' WHERE pid="+pid+"";
        
        try {
                   
                idb.update(sqlFraga);             
                
                }
                
                catch(InformatikException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    
                }
        
        
    }
    
    /**
     * Metoden hämtar ut värdet från redigeringsrutan under fliken "uppdatera plattform" för benämning och sätter in det nya värdet i en SQL fråga
     * som riktar sig till det valda värdet i comboboxen.
     */
    public void updatePlatformBenamning(){
        
        String newPlatName = txtUpdPlatBen.getText();
        String pid = cbCurrentPlats.getSelectedItem().toString().substring(0, cbCurrentPlats.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "UPDATE PLATTFORM SET BENAMNING = '"+newPlatName+"' WHERE pid="+pid+"";
        
        try {
                    
                idb.update(sqlFraga);             
                
                }
                
                catch(InformatikException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    
                }
        
    }

    /**
     * laggTillNyPlattform metoden tar värdena ifrån textfälten inmatade av användaren och sätter in dessa
     * i en SQL fråga. Eftersom databastabellen för Plattform har en primärnyckel används en autoinkrementeringsmetod 
     * för primärnyckelvärdet med tanke att undvika felaktig eller besvärlig input ifrån slutanvändaren.
     * Vid felaktighet ges ett felmeddelande. Validering för denna metoden anser vi inte behövs då plattformar kan ha
     * både alfabetiska såväl som numeriska värden i sitt namn.
     */
    private void laggTillNyPlattform(){
        
        try {
        
        String benamning = txtNewPlatBen.getText();
        String beskrivning = txtNewPlatBesk.getText();
        String producent = txtNewPlatProd.getText();
        String newPid = idb.getAutoIncrement("plattform", "pid");
        String sqlFraga = "INSERT INTO PLATTFORM(PID, BENAMNING, BESKRIVNING, PRODUCENT) "
                + "VALUES('"+newPid+"', '"+benamning+"', '"+beskrivning+"', '"+producent+"') ";
        
        idb.insert(sqlFraga);
        }
        
        catch(InformatikException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                  
                                     
                }
                
                
               
    
    }
    
     /**
      * autoFyllIBenamningPlattform metoden arbetar för den plattform man under fliken "uppdatera plattform" väljer hämtas den redan 
      * befintliga producenten och lägger informationen i redigeringsrutan. Det underlättar då man kan se redan vald information
      * och ändra enstaka sektioner eller helt och hållet om man så önskar.
      */
    private void autoFyllIProducentPlattform(){
        String pid = cbCurrentPlats.getSelectedItem().toString().substring(0, cbCurrentPlats.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "SELECT PRODUCENT FROM PLATTFORM WHERE pid="+pid+"";
         
        try {
                    
               String texten = idb.fetchSingle(sqlFraga);        
               txtUpdPlatProd.setText(texten);
                
                }
                
                catch(InformatikException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    
                }
    }
    
    /**
     * autoFyllIBenamningPlattform metoden arbetar för den plattform man under fliken "uppdatera plattform" väljer hämtas den redan 
     * befintliga benamningen och lägger informationen i redigeringsrutan. Det underlättar då man kan se redan vald information
     * och ändra enstaka sektioner eller helt och hållet om man så önskar.
     */
    private void autoFyllIBenamningPlattform(){
        String pid = cbCurrentPlats.getSelectedItem().toString().substring(0, cbCurrentPlats.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "SELECT BENAMNING FROM PLATTFORM WHERE pid="+pid+"";
         
        try {
                    
               String texten = idb.fetchSingle(sqlFraga);        
               txtUpdPlatBen.setText(texten);
                
                }
                
                catch(InformatikException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    
                }
    }
    
    /**
     * autoFyllIBeskrivningPlattform metoden arbetar för den plattform man under fliken "uppdatera plattform" väljer hämtas den redan 
     * befintliga beskrivningen och lägger informationen i redigeringsrutan. Det underlättar då man kan se redan vald information
     * och ändra enstaka sektioner eller helt och hållet om man så önskar.
     */
    private void autoFyllIBeskrivningPlattform(){
        String pid = cbCurrentPlats.getSelectedItem().toString().substring(0, cbCurrentPlats.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "SELECT BESKRIVNING FROM PLATTFORM WHERE pid="+pid+"";
         
        try {
                    
               String texten = idb.fetchSingle(sqlFraga);        
               txtUpdPlatBesk.setText(texten);
                
                }
                
                catch(InformatikException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    
                }
    }
    
    /**
     * Metoden nollställer alla värden under fliken "Anstalld", under fliken "Uppdatera".
     * Metoden sätter ("") på alla textfält och sätter "setSelected(false)" på alla radioButtons.
     */
    private void nollStällAllaVärdenUppdateraAnstalld()
    {
        txtUpdName.setText("");
        txtUpdPhone.setText("");
        txtUpdMail.setText("");
        txtHotmailUppdatera.setText("");
        txtPunktComUppdatera.setText("");
        txtPlattformUppdateraAnstalld.setText("");
        txtKompetensUppdateraAnstalld.setText("");
        txtNivåUppdateraAnstalld.setText("");
        cbBefintligaKompetenser.removeAllItems();
        cbSpecifikaKompetenser.removeAllItems();
        txtSpecifikLevel.setText("");
        jRadioProjektledareUppdatera.setSelected(false);
        jRadioUppdateraRollNej.setSelected(false);
        lblNuvarandeStatusResultat.setText("");
        lblBefintligMailUppdateraAnstalld.setText("");
        lblFelmeddelandeUppdateraAnstalldNamn.setText("");
        lblFelmeddelandeUppdateraAnstalldTelefon.setText("");
        lblFelmeddelandeUppdateraAnstalldEpost.setText("");
        lblFelmeddelandeKompetensUppdateraAnstalld.setText("");
        lblFelmeddelandePlattformUppdateraAnstalld.setText("");
        lblFelmeddelandeNivaUppdateraAnstalld.setText("");
        lblFelmeddelandeTaBortKompetensUppdateraAnstalld.setText("");
    }
    
    /**
     * Metoden hämtar alla värden från kolumnen "benamning" och "KID" från tabellen "kompetensdoman".
     * Dessa värden sätts in i en lokal HashMap som sätts in i en lokal ArrayList.
     * Därefter sätts arraylistan i en for-loop som strängkoncatenerar "KID" och "Benamning"
     * och sedan sätter in denna sträng i den comboBoxen där användaren kan välja plattform att lägga till som den
     * anställdes kompetens.
     */
    private void fillCompCbAdd(){
        
        String sqlFraga = "select benamning, kid from kompetensdoman";
       try
       {
           ArrayList<HashMap<String,String>> compList = idb.fetchRows(sqlFraga);
           String laggTillKompetens = "";
            
            for(int i = 0;i< compList.size();i++)
            {
                laggTillKompetens = compList.get(i).get("kid");
                laggTillKompetens = laggTillKompetens + ". " + compList.get(i).get("benamning");
                cbCompAreas.addItem(laggTillKompetens);
                
            }
       }      
       catch(InformatikException e)
       {
                   JOptionPane.showMessageDialog(null, e.getMessage());
       }
        
    }
    
    /**
     * Metoden hämtar alla värden från kolumnen "namn", från tabellen "Anstalld",
     * där kolumnen "namn" matchar den lokala variabeln.
     * Därefter sätts det textfält, där användare kan skriva ett nytt namn,
     * med det värde som hämtats från databasen.
     */
    private void laggTillNamnAnstalldUppdatera()
    {
        String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "select namn from anstalld where aid="+aid+"";
        try
        {
            String befintligtNamn = idb.fetchSingle(sqlFraga);
            txtUpdName.setText(befintligtNamn);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Metoden hämtar alla värden från kolumnen "telefon", från tabellen "Anstalld",
     * där kolumnen "namn" matchar den lokala variabeln.
     * Därefter sätts det textfält, där användare kan skriva ett nytt telefonnummer,
     * med det värde som hämtats från databasen.
     */
    private void laggTillTelefonAnstalldUppdatera()
    {
        String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "select telefon from anstalld where aid="+aid+"";
        try
        {
            String befintligTelefon = idb.fetchSingle(sqlFraga);
            txtUpdPhone.setText(befintligTelefon);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Metoden hämtar först AID-värdet genom att ta det valda objektet från
     * comboBoxen där användaren valt sin anställde att uppdatera och gör detta objekt till
     * en sträng. Därefter hämtas AID-värdet med denna sträng från databasen.
     * 
     * Sedan hämtas "namn" från tabellen "Anstalld" som matchar det AID-värde som
     * återfinns i tabellen "Projektledare". Detta sätts in i en lokal variabel. Om
     * variabeln är angiven sätts JLabeln där användaren kan se rollen på den anställde
     * med "Projektledare", annars sätts den med "Specialist".
     */
    private void kollaProjektledareLaggTillRoll()
    {
        String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "select namn from ANSTALLD\n" +
"join PROJEKTLEDARE on PROJEKTLEDARE.AID=ANSTALLD.AID\n" +
"where ANSTALLD.AID="+aid+"";
        
        try
        {
            String projektledare = null;
            projektledare = idb.fetchSingle(sqlFraga);
            
            if(!(projektledare==null))
            {
                lblNuvarandeStatusResultat.setText("Projektledare");
            }
            else
            {
                lblNuvarandeStatusResultat.setText("Specialist");
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Metoden hämtar först AID-värdet genom att ta det valda objektet från
     * comboBoxen där användaren valt sin anställde att uppdatera och gör detta objekt till
     * en sträng. Därefter hämtas AID-värdet med denna sträng från databasen.
     * 
     * Sedan hämtas "namn" från tabellen "Anstalld" som matchar det AID-värde som
     * återfinns i tabellen "Projektledare". Detta sätts in i en lokal variabel. Om
     * radioknappen som säger "Ja" är tryckt så kollar metoden om variabeln är angiven eller inte.
     * Om variabeln inte är angiven så läggs den valda anställdes AID i tabellen "Projektledare".
     * 
     * Om radioknappen som säger "Nej" är tryckt så kollar metoden om den lokala
     * variabeln är angiven. Om variabeln är angiven så raderas den anställdes AID
     * från tabellen "Projektledare".
     */
    private void kollaProjektledareUppdateraRoll()
    {
        String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "select namn from ANSTALLD\n" +
"join PROJEKTLEDARE on PROJEKTLEDARE.AID=ANSTALLD.AID\n" +
"where ANSTALLD.AID="+aid+"";
        
        try
        {
            String projektledare = null;
            projektledare = idb.fetchSingle(sqlFraga);
            if(jRadioProjektledareUppdatera.isSelected()==true)
            {
                if(projektledare==null)
            {
                String insert = "insert into projektledare values ("+aid+", "+0+")";
                idb.insert(insert);
            }   
            }
            if(jRadioUppdateraRollNej.isSelected()==true)
            {
                if(!(projektledare==null))
                {
                    String delete = "delete from projektledare where aid="+aid+"";
                    idb.delete(delete);
                }
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Metoden hämtar alla värden från kolumnen "mail", från tabellen "Anstalld",
     * där kolumnen "namn" matchar den lokala variabeln.
     * Därefter sätts den JLabel som visar användaren den befintliga mejladressen
     * med det värde som hämtats från databasen.
     */
    private void laggTillMailAnstalldUppdatera()
    {
        String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "select mail from anstalld where aid="+aid+"";
        try
        {
            String befintligMail = idb.fetchSingle(sqlFraga);
            lblBefintligMailUppdateraAnstalld.setText(befintligMail);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Metoden hämtar alla värden från kolumnen "namn", från tabellen "Anstalld".
     * Därefter fylls den comboBox där användaren kan välja en anställd att uppdatera.
     */
    private void laggTillAnstalldaAttUppdatera()
    {
        String sqlFraga = "select namn, aid from anstalld";
        
        try
        {
            ArrayList<HashMap<String, String>> specialistLista = idb.fetchRows(sqlFraga);
            String laggTillAnstalld = null;
            
            for(int i=0; i < specialistLista.size(); i++)
            {
                laggTillAnstalld = specialistLista.get(i).get("aid");
                laggTillAnstalld = laggTillAnstalld + ". " + specialistLista.get(i).get("namn");
                cbUpdEmp.addItem(laggTillAnstalld);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Metoden hämtar först AID-värdet genom att ta det valda objektet från
     * comboBoxen där användaren valt sin anställde att uppdatera och gör detta objekt till
     * en sträng. Därefter hämtas AID-värdet med denna sträng från databasen.
     * 
     * Sedan hämtas benämningen från tabellen "Plattform" som matchar
     * det AID som återfinns i tabellen "Har_Kompetens". Därefter sätts de hämtade
     * värdet/värdena i en arraylist, loopas igenom med en for-loop och lägger till dessa
     * värden i den comboBox där användaren kan välja den anställdes befintliga plattformar
     * denne har kompetens för.
     */
    private void laggTillBefintligaKompetenserUppdateraAnstalld()
    {

        String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
        
        
        String sqlFraga = "select distinct benamning, plattform.pid from plattform\n" +
"join HAR_KOMPETENS on HAR_KOMPETENS.PID=PLATTFORM.PID\n" +
"where aid="+aid+"";
        try
        {
            ArrayList<HashMap<String, String>> kompetenslista = idb.fetchRows(sqlFraga);
            String laggTillKompetens = null;
            for(int i=0; i < kompetenslista.size(); i++)
            {
                laggTillKompetens = kompetenslista.get(i).get("pid");
                laggTillKompetens = laggTillKompetens + "." + kompetenslista.get(i).get("benamning");
                cbBefintligaKompetenser.addItem(laggTillKompetens);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    /**
     * Metoden hämtar alla värden från kolumnen "benamning" och "KID" från tabellen "kompetensdoman".
     * Dessa värden sätts in i en lokal HashMap som sätts in i en lokal ArrayList.
     * Därefter sätts arraylistan i en for-loop som strängkoncatenerar "KID" och "Benamning"
     * och sedan sätter in denna sträng i den comboBoxen där användaren kan välja plattform att lägga till
     * eller ta bort från den anställdes kompetens.
     */
    private void fillCompCbUpd(){
        
        String sqlFraga = "select benamning, kid from kompetensdoman";
       try
       {
           ArrayList<HashMap<String,String>> compList = idb.fetchRows(sqlFraga);
           String laggTillKompetens = "";
            
            for(int i = 0;i< compList.size();i++)
            {
                laggTillKompetens = compList.get(i).get("kid");
                laggTillKompetens = laggTillKompetens + ". " + compList.get(i).get("benamning");
                cbUpdComp.addItem(laggTillKompetens);
                
            }
       }      
       catch(InformatikException e)
       {
                   JOptionPane.showMessageDialog(null, e.getMessage());
       }
        
    }
    
    /**
     * Metoden hämtar alla värden från kolumnen "benamning" och "PID" från tabellen "Plattform".
     * Dessa värden sätts in i en lokal HashMap som sätts in i en lokal ArrayList.
     * Därefter sätts arraylistan i en for-loop som strängkoncatenerar "PID" och "benamning"
     * och sedan sätter in denna sträng i den comboBoxen där användaren kan välja den plattform
     * en anställd ska ha kompetens för.
     */
    private void laggTillPlattformLaggTillAnstalld()
    {
        String sqlFraga = "select benamning, pid from plattform";
        try
        {
            ArrayList<HashMap<String, String>> kompetensLista = idb.fetchRows(sqlFraga);
            String laggTillPlattform = "";
            
            for(int i=0; i < kompetensLista.size(); i++)
            {
                laggTillPlattform = kompetensLista.get(i).get("pid");
                laggTillPlattform = laggTillPlattform + ". " + kompetensLista.get(i).get("benamning");
                cbPlatChoice.addItem(laggTillPlattform);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Metoden hämtar alla värden från kolumnen "benamning" och "PID" från tabellen "Plattform".
     * Dessa värden sätts in i en lokal HashMap som sätts in i en lokal ArrayList.
     * Därefter sätts arraylistan i en for-loop som strängkoncatenerar "PID" och "benamning"
     * och sedan sätter in denna sträng i den comboBoxen där användaren kan välja den plattform
     * en anställd ska ha kompetens för.
     */
    private void laggTillPlattformUppdateraAnstalld()
    {
        String sqlFraga = "select benamning, pid from plattform";
        try
        {
            ArrayList<HashMap<String, String>> kompetensLista = idb.fetchRows(sqlFraga);
            String laggTillPlattform = "";
            
            for(int i=0; i < kompetensLista.size(); i++)
            {
                laggTillPlattform = kompetensLista.get(i).get("pid");
                laggTillPlattform = laggTillPlattform + ". " + kompetensLista.get(i).get("benamning");
                cbUpdPlat.addItem(laggTillPlattform);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Metoden hämtar alla värden från kolumnen "namn" och "AID" från tabellen "Anstalld"
     * som matchar de värden som finns i tabellen "Projektledare".
     * Dessa värden sätts in i en lokal HashMap som sätts in i en lokal ArrayList.
     * Därefter sätts arraylistan i en for-loop som strängkoncatenerar "AID" och "namn"
     * och sedan sätter in denna sträng i en comboBoxen där användaren kan se alla projektledare de kan lägga till.
     */
    private void laggTillProjektledare()
    {
        String sqlFraga = "select namn, anstalld.aid from anstalld\n" +
"join PROJEKTLEDARE on PROJEKTLEDARE.AID=ANSTALLD.AID;";
        
        try
        {
            ArrayList<HashMap<String, String>> projektledarLista = idb.fetchRows(sqlFraga);
            String laggTillProjektledare = null;
            
            for(int i=0; i < projektledarLista.size(); i++)
            {
                laggTillProjektledare = projektledarLista.get(i).get("aid");
                laggTillProjektledare = laggTillProjektledare + ". " + projektledarLista.get(i).get("namn");
                cbLaggTillProjektledare.addItem(laggTillProjektledare);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Metoden hämtar alla värden från kolumnen "namn" och "AID" från tabellen "Anstalld"
     * som matchar de värden som finns i tabellen "Projektledare".
     * Dessa värden sätts in i en lokal HashMap som sätts in i en lokal ArrayList.
     * Därefter sätts arraylistan i en for-loop som strängkoncatenerar "AID" och "namn"
     * och sedan sätter in denna sträng i den comboBoxen där användaren kan se alla projektledare de kan uppdatera.
     */
    private void laggTillProjektledareUppdatera()
    {
        String sqlFraga = "select namn, anstalld.aid from anstalld\n" +
"join PROJEKTLEDARE on PROJEKTLEDARE.AID=ANSTALLD.AID;";
        
        try
        {
            ArrayList<HashMap<String, String>> projektledarLista = idb.fetchRows(sqlFraga);
            String laggTillProjektledare = null;
            
            for(int i=0; i < projektledarLista.size(); i++)
            {
                laggTillProjektledare = projektledarLista.get(i).get("aid");
                laggTillProjektledare = laggTillProjektledare + ". " + projektledarLista.get(i).get("namn");
                cbUppdateraProjektledare.addItem(laggTillProjektledare);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Metoden hämtar alla värden från kolumnen "beteckning", från tabellen "Spelprojekt",
     * där kolumnen "SID" matchar den lokala variabeln "sid".
     * Därefter sätts det textfält, där användare kan skriva en ny projektbeteckning,
     * med det värde som hämtats från databasen.
     */
    private void laggTillProjektBeteckningUppdateraProjekt()
    {
        String sid = cbVäljProjektUppdatera.getSelectedItem().toString().substring(0, cbVäljProjektUppdatera.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "select beteckning from SPELPROJEKT\n" +
"where spelprojekt.sid= "+sid+"";
        
        try
        {
            String setBeteckning = idb.fetchSingle(sqlFraga);
            txtUppdateraBeteckning.setText(setBeteckning);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Metoden hämtar alla värden från kolumnen "namn" och "AID" från tabellen "Anstalld"
     * som matchar de värden som finns i tabellen "Arbetar_i" och "Spelprojekt".
     * Dessa värden sätts in i en lokal HashMap som sätts in i en lokal ArrayList.
     * Därefter sätts arraylistan i en for-loop som strängkoncatenerar "AID" och "namn"
     * och sedan sätter in denna sträng i de två comboBoxarna där användaren kan se alla aktuella
     * specialister för det valda projektet.
     */
    private void laggTillAktuellaSpecialisterUppdateraProjekt()
    {
        String sid = cbVäljProjektUppdatera.getSelectedItem().toString().substring(0, cbVäljProjektUppdatera.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "select namn, anstalld.aid from anstalld\n" +
"join ARBETAR_I on ANSTALLD.AID = ARBETAR_I.AID\n" +
"join SPELPROJEKT on SPELPROJEKT.SID=ARBETAR_I.SID\n" +
"where spelprojekt.sid= "+sid+"";
        
        try
        {
            ArrayList<HashMap<String, String>> specialistLista = idb.fetchRows(sqlFraga);
            String laggTillSpecialist = null;
            for(int i=0; i < specialistLista.size(); i++)
            {
                laggTillSpecialist = specialistLista.get(i).get("aid");
                laggTillSpecialist = laggTillSpecialist + ". " + specialistLista.get(i).get("namn");
                cbAktuellaSpecialisterUppdatera.addItem(laggTillSpecialist);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Metoden hämtar alla värden från kolumnen "benamning" och "PID" från tabellen "Plattform"
     * som matchar de värden som finns i tabellen "Innefattar" och "Spelprojekt".
     * Dessa värden sätts in i en lokal HashMap som sätts in i en lokal ArrayList.
     * Därefter sätts arraylistan i en for-loop som strängkoncatenerar "AID" och "namn"
     * och sedan sätter in denna sträng i de två comboBoxarna där användaren kan se de aktuella
     * plattformarna för det valda projektet.
     */
    private void laggTillAktuellaPlattformarUppdateraProjekt()
    {
        String sid = cbVäljProjektUppdatera.getSelectedItem().toString().substring(0, cbVäljProjektUppdatera.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "select benamning, plattform.pid from PLATTFORM\n" +
"join INNEFATTAR on PLATTFORM.PID=INNEFATTAR.PID\n" +
"join SPELPROJEKT on SPELPROJEKT.SID=INNEFATTAR.SID\n" +
"where spelprojekt.sid ="+sid+"";
        
        try
        {
            ArrayList<HashMap<String, String>> plattformLista = idb.fetchRows(sqlFraga);
            String laggTillPlattform = null;
            for(int i=0; i < plattformLista.size(); i++)
            {
                laggTillPlattform = plattformLista.get(i).get("pid");
                laggTillPlattform = laggTillPlattform + ". " + plattformLista.get(i).get("benamning");
                cbAktuellaPlattformarUppdatera.addItem(laggTillPlattform);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Metoden hämtar alla värden från kolumnen "startdatum", från tabellen "Spelprojekt",
     * som är på samma rad som den lokala variabeln "beteckning".
     * Därefter sätts den jLabel, där användaren kan se aktuellt startdatum för valt projekt,
     * med det värde som hämtats från databasen.
     */
    private void laggTillStartdatumUppdateraProjekt()
    {
        String sid = cbVäljProjektUppdatera.getSelectedItem().toString().substring(0, cbVäljProjektUppdatera.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "select startdatum from SPELPROJEKT\n" +
"where spelprojekt.sid= "+sid+"";
        
        try
        {
            String startdatum = idb.fetchSingle(sqlFraga);
            lblAktuelltStartdatumUppdateraResultat.setText("Aktuellt startdatum: " + startdatum);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Metoden hämtar alla värden från kolumnen "namn" och "AID" från tabellen "Anstalld"
     * som matchar de värden som finns i tabellen "Spelprojekt" samt där kolumnen "beteckning"
     * i tabellen "Spelprojekt" matchar den lokala variabeln "beteckning".
     * Dessa värden sätts in i en lokal HashMap som sätts in i en lokal ArrayList.
     * Därefter sätts arraylistan i en for-loop som strängkoncatenerar "AID" och "namn"
     * och sedan sätter in denna sträng i det textfältet där användaren kan välja sin nya
     * projektledare.
     */
    private void laggTillProjektledareUppdateraProjekt()
    {
        String sid = cbVäljProjektUppdatera.getSelectedItem().toString().substring(0, cbVäljProjektUppdatera.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "select namn, anstalld.aid from anstalld\n" +
"join SPELPROJEKT on anstalld.AID = SPELPROJEKT.AID\n" +
"where spelprojekt.sid= "+sid+"";
        
        try
        {
            ArrayList<HashMap<String, String>> projektledarLista = idb.fetchRows(sqlFraga);
            String laggTillProjektledare = null;
            for(int i=0; i < projektledarLista.size();i++)
            {
                laggTillProjektledare = projektledarLista.get(i).get("aid");
                laggTillProjektledare = laggTillProjektledare + ". " + projektledarLista.get(i).get("namn");
                txtUppdateraProjektledareProjekt.setText(laggTillProjektledare);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Metoden hämtar alla värden från kolumnen "releasedatum", från tabellen "Spelprojekt",
     * som är på samma rad som den lokala variabeln "beteckning".
     * Därefter sätts den JLabel, där användaren kan se det aktuella releasedatumet
     * för det valda projektete, med det värde som hämtats från databasen.
     */
    private void laggTillReleasedatumUppdateraProjekt()
            {
        String sid = cbVäljProjektUppdatera.getSelectedItem().toString().substring(0, cbVäljProjektUppdatera.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "select releasedatum from SPELPROJEKT\n" +
"where sid = "+sid+"";
        
        try
        {
            String startdatum = idb.fetchSingle(sqlFraga);
            lblAktuelltReleasedatumUppdateraResultat.setText("Aktuellt releasedatum: " + startdatum);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Metoden hämtar alla värden från kolumnen "beteckning",
     * från tabellen "Spelprojekt", från databasen och lägger in alla dessa värden i en lokal ArrayList.
     * Därefter körs en for-loop som hämtar indexvärdena från arraylistan och lägger in dessa
     * i den comboBox där användaren kan välja vilket projekt denna vill uppdatera.
     */
    private void laggTillProjektAttVäljaUppdateraProjekt()
    {
        String sqlFraga = "select beteckning, sid from SPELPROJEKT";
        
        try
        {
            ArrayList<HashMap<String, String>> projektLista = idb.fetchRows(sqlFraga);
            String laggTillBeteckning = null;
            
            for(int i=0; i < projektLista.size(); i++)
            {
                laggTillBeteckning = projektLista.get(i).get("sid");
                laggTillBeteckning = laggTillBeteckning + ". " + projektLista.get(i).get("beteckning");
                cbVäljProjektUppdatera.addItem(laggTillBeteckning);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Metoden hämtar alla värden från kolumnen "namn" och "AID" från tabellen "Anstalld"
     * som matchar de värden som finns i tabellen "Specialist".
     * Dessa värden sätts in i en lokal HashMap som sätts in i en lokal ArrayList.
     * Därefter sätts arraylistan i en for-loop som strängkoncatenerar "AID" och "namn"
     * och sedan sätter in denna sträng i den comboBox där användaren kan välja specialister
     * att lägga till i detnya projektet.
     */
    private void laggTillSpecialistProjekt()
    {
        String sqlFraga = "select namn, anstalld.aid from anstalld\n" +
"join SPECIALIST on SPECIALIST.AID=ANSTALLD.AID";
        
        try
        {
            ArrayList<HashMap<String, String>> specialistLista = idb.fetchRows(sqlFraga);
            String laggTillSpecialist = null;
            
            for(int i=0; i < specialistLista.size(); i++)
            {
                laggTillSpecialist = specialistLista.get(i).get("aid");
                laggTillSpecialist = laggTillSpecialist + ". " + specialistLista.get(i).get("namn");
                cbLaggTillSpecialister.addItem(laggTillSpecialist);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Metoden laggTillKompetensdomänCombobox() hämtar alla värden från kolumnen "benamning" och "kid",
     * från tabellen Kompetensdoman, från databasen och lägger in alla dessa värden i en lokal ArrayList.
     * Därefter körs en for-loop som hämtar indexvärdena från arraylistan och lägger in dessa i en ComboBox
     * där användaren kan välja kunskapsdomän att uppdatera. 
     */
    private void laggTillKompetensdomänerUppdateraKompetensdomän()
    {
        try
        {
        String sqlFraga = "select benamning, kid from kompetensdoman";
        ArrayList<HashMap<String, String>> benamningsLista = idb.fetchRows(sqlFraga);
        String laggTillKompetens = null;
        for(int i=0; i < benamningsLista.size(); i++)
        {
            laggTillKompetens = benamningsLista.get(i).get("kid");
            laggTillKompetens = laggTillKompetens + ". " + benamningsLista.get(i).get("benamning");
            cbVäljKunskapsdomänUppdatera.addItem(laggTillKompetens);
        }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Metoden hämtar alla värden från kolumnen "benamning" och "kid",
     * från tabellen Kompetensdoman, som matchar den lokala variabeln "kid"
     * Därefter anges det textfält, där användaren kan ändra benämningen
     * på den valda kunskapsdomänen, det värde som hämtats från databasen.
     */
    private void laggTillBenamningUppdateraKompetensdoman()
    {
        try
        {
            String kid = cbVäljKunskapsdomänUppdatera.getSelectedItem().toString().substring(0, cbVäljKunskapsdomänUppdatera.getSelectedItem().toString().indexOf("."));
            String sqlFraga = "select benamning from kompetensdoman where kid="+kid+"";
            String befintligBenamning = idb.fetchSingle(sqlFraga);
            txtBenamningKunskapsdomänUppdatera.setText(befintligBenamning);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Metoden nollställer alla textfält med ("") under fliken "Lägg till", under fliken "Anställd".
     */
    private void nollStällAllaVärdenLaggTillAnstalld()
    {
        txtNewName.setText("");
        txtPhone.setText("");
        txtMail.setText("");
        txtMailNamn.setText("");
        txtPunktCom.setText("");
        txtEmpComp.setText("");
        txtEmpPlat.setText("");
        txtEmpLevel.setText("");
        jRadioProjektledare.setSelected(false);
    }
    
    
    /**
     * Metoden hämtar alla värden från kolumnen "beskrivning" och "kid",
     * från tabellen Kompetensdoman, som matchar den lokala variabeln "kid"
     * Därefter anges den textarea, där användaren kan ändra beskrivningen
     * på den valda kunskapsdomänen, det värde som hämtats från databasen.
     */
    private void laggTillBeskrivingUppdateraKompetensdoman()
    {
        try
        {
            String kid = cbVäljKunskapsdomänUppdatera.getSelectedItem().toString().substring(0, cbVäljKunskapsdomänUppdatera.getSelectedItem().toString().indexOf("."));
            String sqlFraga = "select beskrivning from kompetensdoman where kid="+kid+"";
            String befintligBeskrivning = idb.fetchSingle(sqlFraga);
            txtABeskrivningKunskapsdomänUppdatera.setText(befintligBeskrivning);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Metoden hämtar alla värden från kolumnen "namn" och "AID" från tabellen "Anstalld"
     * som matchar de värden som finns i tabellen "Specialist".
     * Dessa värden sätts in i en lokal HashMap som sätts in i en lokal ArrayList.
     * Därefter sätts arraylistan i en for-loop som strängkoncatenerar "AID" och "namn"
     * och sedan sätter in denna sträng i den comboBoxen där användaren kan välja specialister
     * att lägga till eller ta bort från valt projekt.
     */
    private void laggTillSpecialistUppdateraProjekt()
    {
       String sqlFraga = "select namn, anstalld.aid from anstalld\n" +
"join SPECIALIST on SPECIALIST.AID=ANSTALLD.AID";
        
        try
        {
            ArrayList<HashMap<String, String>> specialistLista = idb.fetchRows(sqlFraga);
            String laggTillSpecialist = null;
            
            for(int i=0; i < specialistLista.size(); i++)
            {
                laggTillSpecialist = specialistLista.get(i).get("aid");
                laggTillSpecialist = laggTillSpecialist + ". " + specialistLista.get(i).get("namn");
                cbUppdateraSpecialister.addItem(laggTillSpecialist);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Metoden hämtar alla värden från kolumnerna "benamning" och "PID" från tabellen "Plattform".
     * Dessa värden sätts in i en lokal HashMap som sätts in i en lokal ArrayList.
     * Därefter sätts arraylistan i en for-loop som strängkoncatenerar "AID" och "namn"
     * och sedan sätter in denna sträng i den angivna comboBoxen där användaren kan välja plattformar
     * att lägga till i det nya projektet.
     */
    private void laggTillPlattformProjekt()
    {
        String sqlFraga = "select benamning, pid from plattform";
        
        try
        {
            ArrayList<HashMap<String, String>> plattformsLista = idb.fetchRows(sqlFraga);
            String laggTillPlattform = null;
            
            for(int i=0; i < plattformsLista.size(); i++)
            {
                laggTillPlattform = plattformsLista.get(i).get("pid");
                laggTillPlattform = laggTillPlattform + ". " + plattformsLista.get(i).get("benamning");
                cbLaggTillPlattformar.addItem(laggTillPlattform);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Metoden hämtar alla värden från kolumnerna "benamning" och "PID" från tabellen "Plattform".
     * Dessa värden sätts in i en lokal HashMap som sätts in i en lokal ArrayList.
     * Därefter sätts arraylistan i en for-loop som strängkoncatenerar "AID" och "namn"
     * och sedan sätter in denna sträng i den angivna comboBoxen där användaren kan välja plattformar
     * att lägga till eller ta bort i valt projekt.
     */
    private void laggTillPlattformUppdateraProjekt()
    {
        String sqlFraga = "select benamning, pid from plattform";
        
        try
        {
            ArrayList<HashMap<String, String>> plattformsLista = idb.fetchRows(sqlFraga);
            String laggTillPlattform = null;
            
            for(int i=0; i < plattformsLista.size(); i++)
            {
                laggTillPlattform = plattformsLista.get(i).get("pid");
                laggTillPlattform = laggTillPlattform + ". " + plattformsLista.get(i).get("benamning");
                cbUppdateraPlattformar.addItem(laggTillPlattform);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    /**
     * Denna metod tömmer alla värden i från de angivna objekten under fliken "Uppdatera", under fliken "Projekt",
     * samt använder sig av metoderna "laggTillPlattformUppdateraProjekt()"
     * och "laggTillSpecialistUppdateraProjekt".
     */
    private void nollStällAllaVärdenUppdateraProjekt()
    {
         txtUppdateraBeteckning.setText("");
         lblAktuelltStartdatumUppdateraResultat.setText("");
         lblAktuelltReleasedatumUppdateraResultat.setText("");
         txtUppdateraProjektledareProjekt.setText("");
         cbUppdateraSpecialisterTillagda.removeAllItems();
         cbUppdateraPlattformarTillagda.removeAllItems();
         cbAktuellaSpecialisterUppdatera.removeAllItems();
         cbAktuellaPlattformarUppdatera.removeAllItems();
         cbUppdateraSpecialister.removeAllItems();
         cbUppdateraPlattformar.removeAllItems();
         jDateChooserStartdatumUppdateraProjekt.setCalendar(null);
         jDateChooserReleasedatumUppdateraProjekt.setCalendar(null);
         laggTillPlattformUppdateraProjekt();
         laggTillSpecialistUppdateraProjekt();
    }
    
    
    /**
     * Denna metod tömmer alla värden i från de angivna objekten under fliken "Lägg till", under fliken "Projekt",
     * samt använder sig av metoderna "laggTillPlattformProjekt()"
     * och "laggTillSpecialistProjekt".
     */
    private void nollStällAllaVärdenLäggTillProjekt()
    {
        txtLaggTillBeteckning.setText("");
        jDateChooserStartdatumProjekt.setCalendar(null);
        jDateChooserReleasedatumProjekt.setCalendar(null);
        txtLaggTillProjektledare.setText("");
        cbLaggTillSpecialister.removeAllItems();
        cbLaggTillTillagdaSpecialister.removeAllItems();
        cbLaggTillPlattformar.removeAllItems();
        cbLaggTillTillagdaPlattformar.removeAllItems();
        laggTillPlattformProjekt();
        laggTillSpecialistProjekt();
        
    }
    
    
    /**
     * Metoden tar in en JDateChooser som parameter. Därefter instansieras
     * ett nytt objekt (SimpleDateFormat). Sedan hämtas det valda datumet 
     * från JDateChoosern. Detta datum formateras till valt format samt
     * konverteras till en sträng som därefter returneras.
     * @param date
     * @return 
     */
    public String getDate(JDateChooser date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String datum = sdf.format(date.getDate()).toString();
        
        return datum;
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        lblLaggTillBeteckning = new javax.swing.JLabel();
        txtLaggTillBeteckning = new javax.swing.JTextField();
        lblLaggTillMinMaxBeteckning = new javax.swing.JLabel();
        lblLaggTillStartdatum = new javax.swing.JLabel();
        lblLaggTillReleasedatum = new javax.swing.JLabel();
        lblProjektledare = new javax.swing.JLabel();
        lblLaggTillSpecialist = new javax.swing.JLabel();
        cbLaggTillSpecialister = new javax.swing.JComboBox();
        cbLaggTillTillagdaSpecialister = new javax.swing.JComboBox();
        btnLaggTillTaBortSpecialist = new javax.swing.JButton();
        btnLaggTillSpecialist = new javax.swing.JButton();
        lblLaggTillPlattformar = new javax.swing.JLabel();
        btnLaggTillTaBortPLattformar = new javax.swing.JButton();
        btnLaggTillPlattformar = new javax.swing.JButton();
        cbLaggTillPlattformar = new javax.swing.JComboBox();
        cbLaggTillTillagdaPlattformar = new javax.swing.JComboBox();
        cbLaggTillProjektledare = new javax.swing.JComboBox();
        btnLaggTillTaBortProjektledare = new javax.swing.JButton();
        btnLaggTillProjektledare = new javax.swing.JButton();
        txtLaggTillProjektledare = new javax.swing.JTextField();
        btnLaggTillProjekt = new javax.swing.JButton();
        jDateChooserStartdatumProjekt = new com.toedter.calendar.JDateChooser();
        jDateChooserReleasedatumProjekt = new com.toedter.calendar.JDateChooser();
        lblFelmeddelandeStartdatumProjektLaggTill = new javax.swing.JLabel();
        lblFelmeddelandeReleasedatumProjektLaggTill = new javax.swing.JLabel();
        lblFelmeddelandeProjektledareProjektLaggTill = new javax.swing.JLabel();
        lblFelmeddelandeSpecialisterProjektLaggTill = new javax.swing.JLabel();
        lblFelmeddelandePlattformarProjektLaggTill = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lblUppdateraBeteckning = new javax.swing.JLabel();
        txtUppdateraBeteckning = new javax.swing.JTextField();
        lblUppdateraMinMaxBokstäver = new javax.swing.JLabel();
        lblUppdateraStartdatum = new javax.swing.JLabel();
        lblUppdateraReleasedatum = new javax.swing.JLabel();
        lblUppdateraProjektledare = new javax.swing.JLabel();
        lblUppdateraSpecialist = new javax.swing.JLabel();
        cbUppdateraSpecialister = new javax.swing.JComboBox();
        cbUppdateraSpecialisterTillagda = new javax.swing.JComboBox();
        btnUppdateraSpecialistTaBort = new javax.swing.JButton();
        btnUppdateraSpecialistLäggTill = new javax.swing.JButton();
        lblUppdateraPlattformar = new javax.swing.JLabel();
        btnUppdateraPlattformarTaBort = new javax.swing.JButton();
        btnUppdateraPlattformarLäggTill = new javax.swing.JButton();
        cbUppdateraPlattformar = new javax.swing.JComboBox();
        cbUppdateraPlattformarTillagda = new javax.swing.JComboBox();
        cbUppdateraProjektledare = new javax.swing.JComboBox();
        btnUppdateraProjektledareTaBort = new javax.swing.JButton();
        btnUppdateraProjektledareLäggTill = new javax.swing.JButton();
        txtUppdateraProjektledareProjekt = new javax.swing.JTextField();
        lblAktuellaSpecialisterUppdatera = new javax.swing.JLabel();
        cbAktuellaSpecialisterUppdatera = new javax.swing.JComboBox();
        lblVäljProjektUppdatera = new javax.swing.JLabel();
        cbVäljProjektUppdatera = new javax.swing.JComboBox();
        btnVäljProjektUppdatera = new javax.swing.JButton();
        lblAktuellaPlattformarUppdatera = new javax.swing.JLabel();
        cbAktuellaPlattformarUppdatera = new javax.swing.JComboBox();
        lblAktuelltStartdatumUppdateraResultat = new javax.swing.JLabel();
        lblAktuelltReleasedatumUppdateraResultat = new javax.swing.JLabel();
        btnUppdateraBeteckningProjekt = new javax.swing.JButton();
        jDateChooserStartdatumUppdateraProjekt = new com.toedter.calendar.JDateChooser();
        jDateChooserReleasedatumUppdateraProjekt = new com.toedter.calendar.JDateChooser();
        btnUppdateraStartdatumProjekt = new javax.swing.JButton();
        btnUppdateraReleasedatumProjekt = new javax.swing.JButton();
        btnUppdateraProjektledareProjekt = new javax.swing.JButton();
        btnUppdateraSpecialisterProjekt = new javax.swing.JButton();
        btnUppdateraPlattformarProjekt = new javax.swing.JButton();
        btnTaBortHeltProjekt = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        lblStepOne = new javax.swing.JLabel();
        lblAddEmpName = new javax.swing.JLabel();
        txtNewName = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        lblAddEmpPhone = new javax.swing.JLabel();
        txtMail = new javax.swing.JTextField();
        btnConfirmNewEmp = new javax.swing.JButton();
        lblStepTwo = new javax.swing.JLabel();
        lblCompAreas = new javax.swing.JLabel();
        lblAddEmpMail = new javax.swing.JLabel();
        cbCompAreas = new javax.swing.JComboBox();
        cbPlatChoice = new javax.swing.JComboBox();
        lblPlatformKnowledge = new javax.swing.JLabel();
        lblLevelComp = new javax.swing.JLabel();
        cbLevelPlattform = new javax.swing.JComboBox();
        txtEmpComp = new javax.swing.JTextField();
        txtEmpPlat = new javax.swing.JTextField();
        txtEmpLevel = new javax.swing.JTextField();
        btnLaggTillKompetensLaggTillAnstalld = new javax.swing.JButton();
        btnTaBortKompetensLaggTillAnstalld = new javax.swing.JButton();
        btnTaBortPlattformLaggTillAnstalld = new javax.swing.JButton();
        btnLaggTillPlattformLaggTillAnstalld = new javax.swing.JButton();
        btnTaBortNivåLaggTillAnstalld = new javax.swing.JButton();
        btnLaggTillNivåLaggTillAnstalld = new javax.swing.JButton();
        lblSnabelA = new javax.swing.JLabel();
        txtMailNamn = new javax.swing.JTextField();
        lblPunkt = new javax.swing.JLabel();
        txtPunktCom = new javax.swing.JTextField();
        lblFelmeddelandeNamnLaggTillAnstalld = new javax.swing.JLabel();
        lblFelmeddelandeTelefonLaggTillAnstalld = new javax.swing.JLabel();
        lblFelmeddelandeEpostLaggTillAnstalld = new javax.swing.JLabel();
        lblFelmeddelandeKompetensLaggTillAnstalld = new javax.swing.JLabel();
        lblFelmeddelandePlattformarLaggTillAnstalld = new javax.swing.JLabel();
        lblFelmeddelandeNivåLaggTillAnstalld = new javax.swing.JLabel();
        jRadioProjektledare = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        lblUpdName = new javax.swing.JLabel();
        txtUpdName = new javax.swing.JTextField();
        txtUpdPhone = new javax.swing.JTextField();
        lblUpdPhone = new javax.swing.JLabel();
        txtUpdMail = new javax.swing.JTextField();
        lblUpdMail = new javax.swing.JLabel();
        cbUpdComp = new javax.swing.JComboBox();
        cbUpdPlat = new javax.swing.JComboBox();
        cbUpdEmpProj = new javax.swing.JComboBox();
        lblUpdPlat = new javax.swing.JLabel();
        lblAssignProj = new javax.swing.JLabel();
        btnLaggTillKompetensUppdateraAnstalld = new javax.swing.JButton();
        cbUpdEmp = new javax.swing.JComboBox();
        btnChooseEmpToUpd = new javax.swing.JButton();
        lblEmpPicker = new javax.swing.JLabel();
        btnChangeName = new javax.swing.JButton();
        btnChangePhone = new javax.swing.JButton();
        btnChangeMail = new javax.swing.JButton();
        lblUpdComp = new javax.swing.JLabel();
        txtKompetensUppdateraAnstalld = new javax.swing.JTextField();
        txtPlattformUppdateraAnstalld = new javax.swing.JTextField();
        txtNivåUppdateraAnstalld = new javax.swing.JTextField();
        cbBefintligaKompetenser = new javax.swing.JComboBox();
        lblBefintligaKompetenser = new javax.swing.JLabel();
        btnTaBortKompetensUppdateraAnstalld = new javax.swing.JButton();
        btnLaggTilKompetensUppdateraAnstalld = new javax.swing.JButton();
        btnTaBortPlattformUppdateraAnstalld = new javax.swing.JButton();
        btnLaggTillPlattformUppdateraAnstalld = new javax.swing.JButton();
        btnTaBortLevelUppdateraAnstalld = new javax.swing.JButton();
        btnLaggTillLevelUppdateraAnstalld = new javax.swing.JButton();
        btnVisaSpecifikKompetens = new javax.swing.JButton();
        cbSpecifikaKompetenser = new javax.swing.JComboBox();
        btnVisaLevelSpecifikKompetens = new javax.swing.JButton();
        txtSpecifikLevel = new javax.swing.JTextField();
        btnTaBortKompetensAnstalld = new javax.swing.JButton();
        lblUppdateraProjektledareUppdateraAnstalld = new javax.swing.JLabel();
        jRadioProjektledareUppdatera = new javax.swing.JRadioButton();
        lblBefintligEpost = new javax.swing.JLabel();
        lblBefintligMailUppdateraAnstalld = new javax.swing.JLabel();
        lblSnabelAUppdatera = new javax.swing.JLabel();
        txtHotmailUppdatera = new javax.swing.JTextField();
        lblPunktCom = new javax.swing.JLabel();
        txtPunktComUppdatera = new javax.swing.JTextField();
        lblNuvarandeStatus = new javax.swing.JLabel();
        lblNuvarandeStatusResultat = new javax.swing.JLabel();
        btnÄndraRollAnstalld = new javax.swing.JButton();
        lblFelmeddelandeUppdateraAnstalldNamn = new javax.swing.JLabel();
        lblFelmeddelandeUppdateraAnstalldTelefon = new javax.swing.JLabel();
        lblFelmeddelandeUppdateraAnstalldEpost = new javax.swing.JLabel();
        lblFelmeddelandeKompetensUppdateraAnstalld = new javax.swing.JLabel();
        lblFelmeddelandePlattformUppdateraAnstalld = new javax.swing.JLabel();
        lblFelmeddelandeNivaUppdateraAnstalld = new javax.swing.JLabel();
        lblFelmeddelandeTaBortKompetensUppdateraAnstalld = new javax.swing.JLabel();
        jRadioUppdateraRollNej = new javax.swing.JRadioButton();
        btnTaBortAnstalldHelt = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        lblNewPlatBen = new javax.swing.JLabel();
        txtNewPlatBen = new javax.swing.JTextField();
        lblNewPlatBesk = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNewPlatBesk = new javax.swing.JTextArea();
        lblNewPlatProd = new javax.swing.JLabel();
        txtNewPlatProd = new javax.swing.JTextField();
        btnAddNewPlat = new javax.swing.JButton();
        lblFelmeddelandeLaggTillPlattformBenamning = new javax.swing.JLabel();
        lblFelmeddelandeLaggTillPlattformBeskrivning = new javax.swing.JLabel();
        lblFelmeddelandeLaggTillPlattformProducent = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblChoosePlatUpd = new javax.swing.JLabel();
        cbCurrentPlats = new javax.swing.JComboBox();
        btnSelectedPlatUpd = new javax.swing.JButton();
        lblUpdPlatBen = new javax.swing.JLabel();
        txtUpdPlatBen = new javax.swing.JTextField();
        lblUpdPlatBesk = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtUpdPlatBesk = new javax.swing.JTextArea();
        lblUpdPlatProd = new javax.swing.JLabel();
        txtUpdPlatProd = new javax.swing.JTextField();
        btnUpdatePlatform = new javax.swing.JButton();
        lblFelmeddelandeUppdateraPlattformBenaming = new javax.swing.JLabel();
        lblFelmeddelandeUppdateraPlattformBeskrivning = new javax.swing.JLabel();
        lblFelmeddelandeUppdateraPlattformProducent = new javax.swing.JLabel();
        btnTaBortPlattform = new javax.swing.JButton();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        lblBenamningKunskapsdomän = new javax.swing.JLabel();
        txtBenamningKunskapsdomän = new javax.swing.JTextField();
        lblBeskrivningKunskapsdomän = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtABeskrivningKunskapsdomän = new javax.swing.JTextArea();
        btnLaggTillKunskapsdomän = new javax.swing.JButton();
        lblFelmeddelandeBenämningKunskapsdomän = new javax.swing.JLabel();
        lblFelmeddelandeBeskrivningKunskapsdomän = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        lblBenamningKunskapsdomänUppdatera = new javax.swing.JLabel();
        txtBenamningKunskapsdomänUppdatera = new javax.swing.JTextField();
        lblBeskrivningKunskapsdomänUppdatera = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtABeskrivningKunskapsdomänUppdatera = new javax.swing.JTextArea();
        btnTaBortHeltKunskapsdomän = new javax.swing.JButton();
        lblFelmeddelandeBenämningKunskapsdomänUppdatera = new javax.swing.JLabel();
        lblFelmeddelandeBeskrivningKunskapsdomänUppdatera = new javax.swing.JLabel();
        cbVäljKunskapsdomänUppdatera = new javax.swing.JComboBox();
        lblVäljKunskapsdomänUppdatera = new javax.swing.JLabel();
        btnÄndraBenämningKompetensdomänUppdatera = new javax.swing.JButton();
        btnÄndraBeskrivningKompetensdomänUppdatera = new javax.swing.JButton();
        btnVäljKompetensdomänUppdatera = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblLaggTillBeteckning.setText("Beteckning:");

        lblLaggTillMinMaxBeteckning.setText("min 1 - max 20 bokstäver");

        lblLaggTillStartdatum.setText("Startdatum:");

        lblLaggTillReleasedatum.setText("Releasedatum:");

        lblProjektledare.setText("Projektledare:");

        lblLaggTillSpecialist.setText("Specialister:");

        btnLaggTillTaBortSpecialist.setText("-");
        btnLaggTillTaBortSpecialist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillTaBortSpecialistMouseClicked(evt);
            }
        });

        btnLaggTillSpecialist.setText("+");
        btnLaggTillSpecialist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillSpecialistMouseClicked(evt);
            }
        });

        lblLaggTillPlattformar.setText("Plattformar:");

        btnLaggTillTaBortPLattformar.setText("-");
        btnLaggTillTaBortPLattformar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillTaBortPLattformarMouseClicked(evt);
            }
        });

        btnLaggTillPlattformar.setText("+");
        btnLaggTillPlattformar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillPlattformarMouseClicked(evt);
            }
        });

        btnLaggTillTaBortProjektledare.setText("-");
        btnLaggTillTaBortProjektledare.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillTaBortProjektledareMouseClicked(evt);
            }
        });

        btnLaggTillProjektledare.setText("+");
        btnLaggTillProjektledare.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillProjektledareMouseClicked(evt);
            }
        });

        txtLaggTillProjektledare.setEditable(false);

        btnLaggTillProjekt.setText("Lägg till");
        btnLaggTillProjekt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillProjektMouseClicked(evt);
            }
        });

        jDateChooserStartdatumProjekt.setDateFormatString("dd.MM.yyyy");

        jDateChooserReleasedatumProjekt.setDateFormatString("dd.MM.yyyy");

        lblFelmeddelandeStartdatumProjektLaggTill.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lblFelmeddelandeReleasedatumProjektLaggTill.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lblFelmeddelandeProjektledareProjektLaggTill.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lblFelmeddelandeSpecialisterProjektLaggTill.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lblFelmeddelandePlattformarProjektLaggTill.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLaggTillBeteckning)
                    .addComponent(lblLaggTillStartdatum)
                    .addComponent(lblProjektledare)
                    .addComponent(lblLaggTillSpecialist)
                    .addComponent(lblLaggTillPlattformar)
                    .addComponent(lblLaggTillReleasedatum))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cbLaggTillSpecialister, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbLaggTillPlattformar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbLaggTillProjektledare, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnLaggTillTaBortProjektledare)
                                    .addComponent(btnLaggTillTaBortSpecialist)
                                    .addComponent(btnLaggTillTaBortPLattformar))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnLaggTillPlattformar)
                                    .addComponent(btnLaggTillSpecialist)
                                    .addComponent(btnLaggTillProjektledare))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtLaggTillProjektledare)
                                    .addComponent(cbLaggTillTillagdaPlattformar, 0, 113, Short.MAX_VALUE)
                                    .addComponent(cbLaggTillTillagdaSpecialister, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtLaggTillBeteckning, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblLaggTillMinMaxBeteckning))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jDateChooserStartdatumProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblFelmeddelandeStartdatumProjektLaggTill)))
                                .addGap(0, 220, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(185, 612, Short.MAX_VALUE)
                                .addComponent(btnLaggTillProjekt)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblFelmeddelandeSpecialisterProjektLaggTill)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFelmeddelandeProjektledareProjektLaggTill))
                            .addComponent(lblFelmeddelandePlattformarProjektLaggTill))
                        .addGap(161, 161, 161))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jDateChooserReleasedatumProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFelmeddelandeReleasedatumProjektLaggTill)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLaggTillBeteckning)
                            .addComponent(txtLaggTillBeteckning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLaggTillMinMaxBeteckning))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(lblLaggTillStartdatum))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFelmeddelandeStartdatumProjektLaggTill))))
                    .addComponent(jDateChooserStartdatumProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblLaggTillReleasedatum)
                    .addComponent(jDateChooserReleasedatumProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeReleasedatumProjektLaggTill))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjektledare)
                    .addComponent(cbLaggTillProjektledare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLaggTillTaBortProjektledare)
                    .addComponent(btnLaggTillProjektledare)
                    .addComponent(txtLaggTillProjektledare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeProjektledareProjektLaggTill))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbLaggTillSpecialister, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLaggTillTaBortSpecialist)
                    .addComponent(btnLaggTillSpecialist)
                    .addComponent(lblLaggTillSpecialist)
                    .addComponent(cbLaggTillTillagdaSpecialister, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeSpecialisterProjektLaggTill))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLaggTillTaBortPLattformar)
                    .addComponent(btnLaggTillPlattformar)
                    .addComponent(cbLaggTillPlattformar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLaggTillPlattformar)
                    .addComponent(cbLaggTillTillagdaPlattformar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandePlattformarProjektLaggTill))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 263, Short.MAX_VALUE)
                .addComponent(btnLaggTillProjekt)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Lägg till", jPanel1);

        lblUppdateraBeteckning.setText("Beteckning:");

        lblUppdateraMinMaxBokstäver.setText("min 1 - max 20 bokstäver");

        lblUppdateraStartdatum.setText("Startdatum:");

        lblUppdateraReleasedatum.setText("Releasedatum:");

        lblUppdateraProjektledare.setText("Projektledare:");

        lblUppdateraSpecialist.setText("Specialister:");

        btnUppdateraSpecialistTaBort.setText("-");
        btnUppdateraSpecialistTaBort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUppdateraSpecialistTaBortMouseClicked(evt);
            }
        });

        btnUppdateraSpecialistLäggTill.setText("+");
        btnUppdateraSpecialistLäggTill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUppdateraSpecialistLäggTillMouseClicked(evt);
            }
        });

        lblUppdateraPlattformar.setText("Plattformar:");

        btnUppdateraPlattformarTaBort.setText("-");
        btnUppdateraPlattformarTaBort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUppdateraPlattformarTaBortMouseClicked(evt);
            }
        });

        btnUppdateraPlattformarLäggTill.setText("+");
        btnUppdateraPlattformarLäggTill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUppdateraPlattformarLäggTillMouseClicked(evt);
            }
        });

        btnUppdateraProjektledareTaBort.setText("-");
        btnUppdateraProjektledareTaBort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUppdateraProjektledareTaBortMouseClicked(evt);
            }
        });

        btnUppdateraProjektledareLäggTill.setText("+");
        btnUppdateraProjektledareLäggTill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUppdateraProjektledareLäggTillMouseClicked(evt);
            }
        });

        lblAktuellaSpecialisterUppdatera.setText("Aktuella specialister:");

        lblVäljProjektUppdatera.setText("Projekt:");

        btnVäljProjektUppdatera.setText("Välj");
        btnVäljProjektUppdatera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVäljProjektUppdateraMouseClicked(evt);
            }
        });
        btnVäljProjektUppdatera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVäljProjektUppdateraActionPerformed(evt);
            }
        });

        lblAktuellaPlattformarUppdatera.setText("Aktuella plattformar:");

        btnUppdateraBeteckningProjekt.setText("Ändra");
        btnUppdateraBeteckningProjekt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUppdateraBeteckningProjektMouseClicked(evt);
            }
        });

        jDateChooserStartdatumUppdateraProjekt.setDateFormatString("dd.MM.yyyy");

        jDateChooserReleasedatumUppdateraProjekt.setDateFormatString("dd.MM.yyyy");

        btnUppdateraStartdatumProjekt.setText("Ändra");
        btnUppdateraStartdatumProjekt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUppdateraStartdatumProjektMouseClicked(evt);
            }
        });

        btnUppdateraReleasedatumProjekt.setText("Ändra");
        btnUppdateraReleasedatumProjekt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUppdateraReleasedatumProjektMouseClicked(evt);
            }
        });

        btnUppdateraProjektledareProjekt.setText("Ändra");
        btnUppdateraProjektledareProjekt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUppdateraProjektledareProjektMouseClicked(evt);
            }
        });

        btnUppdateraSpecialisterProjekt.setText("Ändra");
        btnUppdateraSpecialisterProjekt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUppdateraSpecialisterProjektMouseClicked(evt);
            }
        });

        btnUppdateraPlattformarProjekt.setText("Ändra");
        btnUppdateraPlattformarProjekt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUppdateraPlattformarProjektMouseClicked(evt);
            }
        });

        btnTaBortHeltProjekt.setText("Ta bort helt");
        btnTaBortHeltProjekt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaBortHeltProjektMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUppdateraReleasedatum)
                    .addComponent(lblUppdateraProjektledare, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUppdateraSpecialist, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUppdateraPlattformar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblUppdateraStartdatum)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblVäljProjektUppdatera)
                            .addComponent(lblUppdateraBeteckning))))
                .addGap(38, 38, 38)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jDateChooserStartdatumUppdateraProjekt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooserReleasedatumUppdateraProjekt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbUppdateraSpecialister, javax.swing.GroupLayout.Alignment.LEADING, 0, 139, Short.MAX_VALUE)
                            .addComponent(cbUppdateraProjektledare, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbUppdateraPlattformar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblAktuelltReleasedatumUppdateraResultat))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(btnUppdateraPlattformarTaBort)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnUppdateraPlattformarLäggTill))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(btnUppdateraProjektledareTaBort)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnUppdateraProjektledareLäggTill))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(btnUppdateraSpecialistTaBort)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnUppdateraSpecialistLäggTill))
                                    .addComponent(lblAktuelltStartdatumUppdateraResultat))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(cbUppdateraPlattformarTillagda, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnUppdateraPlattformarProjekt)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblAktuellaPlattformarUppdatera)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbAktuellaPlattformarUppdatera, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(cbUppdateraSpecialisterTillagda, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnUppdateraSpecialisterProjekt)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblAktuellaSpecialisterUppdatera)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbAktuellaSpecialisterUppdatera, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btnUppdateraStartdatumProjekt)
                                            .addComponent(txtUppdateraProjektledareProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnUppdateraReleasedatumProjekt))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnUppdateraProjektledareProjekt)))))
                        .addGap(0, 146, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtUppdateraBeteckning, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblUppdateraMinMaxBokstäver)
                                .addGap(28, 28, 28)
                                .addComponent(btnUppdateraBeteckningProjekt))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(cbVäljProjektUppdatera, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnVäljProjektUppdatera)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTaBortHeltProjekt)
                .addGap(21, 21, 21))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVäljProjektUppdatera)
                    .addComponent(cbVäljProjektUppdatera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVäljProjektUppdatera))
                .addGap(31, 31, 31)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUppdateraBeteckning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUppdateraBeteckning)
                    .addComponent(lblUppdateraMinMaxBokstäver)
                    .addComponent(btnUppdateraBeteckningProjekt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAktuelltStartdatumUppdateraResultat)
                            .addComponent(lblUppdateraStartdatum, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addComponent(jDateChooserStartdatumUppdateraProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnUppdateraStartdatumProjekt))
                .addGap(14, 14, 14)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jDateChooserReleasedatumUppdateraProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblUppdateraReleasedatum))
                    .addComponent(lblAktuelltReleasedatumUppdateraResultat)
                    .addComponent(btnUppdateraReleasedatumProjekt))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUppdateraProjektledareProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUppdateraProjektledareLäggTill)
                    .addComponent(btnUppdateraProjektledareTaBort)
                    .addComponent(cbUppdateraProjektledare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUppdateraProjektledare)
                    .addComponent(btnUppdateraProjektledareProjekt))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbUppdateraSpecialisterTillagda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUppdateraSpecialistLäggTill)
                    .addComponent(btnUppdateraSpecialistTaBort)
                    .addComponent(cbUppdateraSpecialister, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUppdateraSpecialist)
                    .addComponent(lblAktuellaSpecialisterUppdatera)
                    .addComponent(cbAktuellaSpecialisterUppdatera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUppdateraSpecialisterProjekt))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUppdateraPlattformar)
                    .addComponent(cbUppdateraPlattformar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUppdateraPlattformarTaBort)
                    .addComponent(btnUppdateraPlattformarLäggTill)
                    .addComponent(cbUppdateraPlattformarTillagda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAktuellaPlattformarUppdatera)
                    .addComponent(cbAktuellaPlattformarUppdatera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUppdateraPlattformarProjekt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTaBortHeltProjekt)
                .addGap(73, 73, 73))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane2.addTab("Uppdatera", jPanel2);

        jTabbedPane1.addTab("Spelutvecklingsprojekt", jTabbedPane2);

        lblStepOne.setText("Steg 1.");

        lblAddEmpName.setText("Namn:");

        lblAddEmpPhone.setText("Telefon:");

        btnConfirmNewEmp.setText("Lägg till");
        btnConfirmNewEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConfirmNewEmpMouseClicked(evt);
            }
        });

        lblStepTwo.setText("Steg 2.");

        lblCompAreas.setText("Kompetensområden:");

        lblAddEmpMail.setText("E-post:");

        lblPlatformKnowledge.setText("Plattformar:");

        lblLevelComp.setText("Nivå:");

        cbLevelPlattform.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));

        btnLaggTillKompetensLaggTillAnstalld.setText("+");
        btnLaggTillKompetensLaggTillAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillKompetensLaggTillAnstalldMouseClicked(evt);
            }
        });

        btnTaBortKompetensLaggTillAnstalld.setText("-");
        btnTaBortKompetensLaggTillAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaBortKompetensLaggTillAnstalldMouseClicked(evt);
            }
        });

        btnTaBortPlattformLaggTillAnstalld.setText("-");
        btnTaBortPlattformLaggTillAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaBortPlattformLaggTillAnstalldMouseClicked(evt);
            }
        });

        btnLaggTillPlattformLaggTillAnstalld.setText("+");
        btnLaggTillPlattformLaggTillAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillPlattformLaggTillAnstalldMouseClicked(evt);
            }
        });

        btnTaBortNivåLaggTillAnstalld.setText("-");
        btnTaBortNivåLaggTillAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaBortNivåLaggTillAnstalldMouseClicked(evt);
            }
        });

        btnLaggTillNivåLaggTillAnstalld.setText("+");
        btnLaggTillNivåLaggTillAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillNivåLaggTillAnstalldMouseClicked(evt);
            }
        });

        lblSnabelA.setText("@");

        txtMailNamn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMailNamnActionPerformed(evt);
            }
        });

        lblPunkt.setText(".");

        txtPunktCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPunktComActionPerformed(evt);
            }
        });

        lblFelmeddelandeNamnLaggTillAnstalld.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandeTelefonLaggTillAnstalld.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandeEpostLaggTillAnstalld.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandeKompetensLaggTillAnstalld.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandePlattformarLaggTillAnstalld.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandeNivåLaggTillAnstalld.setForeground(new java.awt.Color(255, 0, 0));

        jRadioProjektledare.setText("Projektledare");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblStepTwo)
                            .addComponent(lblStepOne)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(223, 223, 223)
                                .addComponent(btnConfirmNewEmp))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(121, 121, 121)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblCompAreas)
                                            .addComponent(lblPlatformKnowledge)
                                            .addComponent(lblLevelComp))
                                        .addGap(20, 20, 20))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblAddEmpName, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblAddEmpMail, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblAddEmpPhone, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPhone)
                                    .addComponent(txtNewName)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtMail, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbPlatChoice, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbCompAreas, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbLevelPlattform, javax.swing.GroupLayout.Alignment.LEADING, 0, 137, Short.MAX_VALUE))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(23, 23, 23)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                                        .addComponent(btnTaBortNivåLaggTillAnstalld)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(btnLaggTillNivåLaggTillAnstalld))
                                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                            .addComponent(btnTaBortKompetensLaggTillAnstalld)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                            .addComponent(btnLaggTillKompetensLaggTillAnstalld))
                                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                            .addComponent(btnTaBortPlattformLaggTillAnstalld)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(btnLaggTillPlattformLaggTillAnstalld))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtEmpComp, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtEmpPlat, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtEmpLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblSnabelA)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtMailNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblPunkt)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtPunktCom, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblFelmeddelandeEpostLaggTillAnstalld))))
                                    .addComponent(jRadioProjektledare))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFelmeddelandeNamnLaggTillAnstalld)
                            .addComponent(lblFelmeddelandeTelefonLaggTillAnstalld)
                            .addComponent(lblFelmeddelandeKompetensLaggTillAnstalld)
                            .addComponent(lblFelmeddelandePlattformarLaggTillAnstalld)
                            .addComponent(lblFelmeddelandeNivåLaggTillAnstalld))))
                .addContainerGap(307, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblStepOne)
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddEmpName)
                    .addComponent(txtNewName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeNamnLaggTillAnstalld))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAddEmpPhone)
                    .addComponent(lblFelmeddelandeTelefonLaggTillAnstalld))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAddEmpMail)
                    .addComponent(lblSnabelA)
                    .addComponent(txtMailNamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPunkt)
                    .addComponent(txtPunktCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeEpostLaggTillAnstalld))
                .addGap(52, 52, 52)
                .addComponent(lblStepTwo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCompAreas)
                    .addComponent(cbCompAreas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmpComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaBortKompetensLaggTillAnstalld)
                    .addComponent(btnLaggTillKompetensLaggTillAnstalld)
                    .addComponent(lblFelmeddelandeKompetensLaggTillAnstalld))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbPlatChoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPlatformKnowledge)
                    .addComponent(txtEmpPlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaBortPlattformLaggTillAnstalld)
                    .addComponent(btnLaggTillPlattformLaggTillAnstalld)
                    .addComponent(lblFelmeddelandePlattformarLaggTillAnstalld))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbLevelPlattform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLevelComp)
                    .addComponent(txtEmpLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLaggTillNivåLaggTillAnstalld)
                    .addComponent(btnTaBortNivåLaggTillAnstalld)
                    .addComponent(lblFelmeddelandeNivåLaggTillAnstalld))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioProjektledare)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(btnConfirmNewEmp)
                .addGap(61, 61, 61))
        );

        jTabbedPane3.addTab("Lägg till", jPanel3);

        lblUpdName.setText("Namn:");

        lblUpdPhone.setText("Telefon:");

        txtUpdMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUpdMailActionPerformed(evt);
            }
        });

        lblUpdMail.setText("E-post:");

        cbUpdEmpProj.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));
        cbUpdEmpProj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUpdEmpProjActionPerformed(evt);
            }
        });

        lblUpdPlat.setText("Plattformar:");

        lblAssignProj.setText("Nivå:");

        btnLaggTillKompetensUppdateraAnstalld.setText("Lägg till");
        btnLaggTillKompetensUppdateraAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillKompetensUppdateraAnstalldMouseClicked(evt);
            }
        });
        btnLaggTillKompetensUppdateraAnstalld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaggTillKompetensUppdateraAnstalldActionPerformed(evt);
            }
        });

        btnChooseEmpToUpd.setText("Välj");
        btnChooseEmpToUpd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChooseEmpToUpdMouseClicked(evt);
            }
        });

        lblEmpPicker.setText("Välj anställd att uppdatera:");

        btnChangeName.setText("Ändra");
        btnChangeName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChangeNameMouseClicked(evt);
            }
        });

        btnChangePhone.setText("Ändra");
        btnChangePhone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChangePhoneMouseClicked(evt);
            }
        });

        btnChangeMail.setText("Ändra");
        btnChangeMail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChangeMailMouseClicked(evt);
            }
        });

        lblUpdComp.setText("Kompetensområden:");

        txtKompetensUppdateraAnstalld.setEditable(false);
        txtKompetensUppdateraAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtKompetensUppdateraAnstalldMouseClicked(evt);
            }
        });

        txtPlattformUppdateraAnstalld.setEditable(false);
        txtPlattformUppdateraAnstalld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlattformUppdateraAnstalldActionPerformed(evt);
            }
        });

        txtNivåUppdateraAnstalld.setEditable(false);

        lblBefintligaKompetenser.setText("Befintliga kompetenser:");

        btnTaBortKompetensUppdateraAnstalld.setText("-");
        btnTaBortKompetensUppdateraAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaBortKompetensUppdateraAnstalldMouseClicked(evt);
            }
        });

        btnLaggTilKompetensUppdateraAnstalld.setText("+");
        btnLaggTilKompetensUppdateraAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTilKompetensUppdateraAnstalldMouseClicked(evt);
            }
        });
        btnLaggTilKompetensUppdateraAnstalld.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnLaggTilKompetensUppdateraAnstalldItemStateChanged(evt);
            }
        });

        btnTaBortPlattformUppdateraAnstalld.setText("-");
        btnTaBortPlattformUppdateraAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaBortPlattformUppdateraAnstalldMouseClicked(evt);
            }
        });

        btnLaggTillPlattformUppdateraAnstalld.setText("+");
        btnLaggTillPlattformUppdateraAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillPlattformUppdateraAnstalldMouseClicked(evt);
            }
        });

        btnTaBortLevelUppdateraAnstalld.setText("-");
        btnTaBortLevelUppdateraAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaBortLevelUppdateraAnstalldMouseClicked(evt);
            }
        });

        btnLaggTillLevelUppdateraAnstalld.setText("+");
        btnLaggTillLevelUppdateraAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillLevelUppdateraAnstalldMouseClicked(evt);
            }
        });

        btnVisaSpecifikKompetens.setText("Visa mer");
        btnVisaSpecifikKompetens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVisaSpecifikKompetensMouseClicked(evt);
            }
        });

        btnVisaLevelSpecifikKompetens.setText("Visa nivå");
        btnVisaLevelSpecifikKompetens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVisaLevelSpecifikKompetensMouseClicked(evt);
            }
        });

        txtSpecifikLevel.setEditable(false);

        btnTaBortKompetensAnstalld.setText("Ta bort");
        btnTaBortKompetensAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaBortKompetensAnstalldMouseClicked(evt);
            }
        });

        lblUppdateraProjektledareUppdateraAnstalld.setText("Projektledare:");

        jRadioProjektledareUppdatera.setText("Ja");
        jRadioProjektledareUppdatera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioProjektledareUppdateraMouseClicked(evt);
            }
        });

        lblBefintligEpost.setText("Befintlig e-post:");

        lblSnabelAUppdatera.setText("@");

        txtHotmailUppdatera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHotmailUppdateraActionPerformed(evt);
            }
        });

        lblPunktCom.setText(".");

        lblNuvarandeStatus.setText("Nuvarande roll:");

        btnÄndraRollAnstalld.setText("Ändra");
        btnÄndraRollAnstalld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnÄndraRollAnstalldMouseClicked(evt);
            }
        });

        lblFelmeddelandeUppdateraAnstalldNamn.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandeUppdateraAnstalldTelefon.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandeUppdateraAnstalldEpost.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandeKompetensUppdateraAnstalld.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandePlattformUppdateraAnstalld.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandeNivaUppdateraAnstalld.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandeTaBortKompetensUppdateraAnstalld.setForeground(new java.awt.Color(255, 0, 0));

        jRadioUppdateraRollNej.setText("Nej");
        jRadioUppdateraRollNej.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioUppdateraRollNejMouseClicked(evt);
            }
        });

        btnTaBortAnstalldHelt.setText("Ta bort anställd helt");
        btnTaBortAnstalldHelt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaBortAnstalldHeltMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addGap(121, 121, 121)
                            .addComponent(lblAssignProj))
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblUpdName, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblUpdMail, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblEmpPicker, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblUpdPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblBefintligEpost, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                            .addGap(24, 24, 24)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblUpdPlat, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblUpdComp, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblUppdateraProjektledareUppdateraAnstalld)
                            .addComponent(lblBefintligaKompetenser)
                            .addComponent(lblNuvarandeStatus))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUpdPhone)
                                    .addComponent(txtUpdName)
                                    .addComponent(cbUpdEmp, 0, 363, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(txtUpdMail, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSnabelAUppdatera)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHotmailUppdatera, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPunktCom, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPunktComUppdatera, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnChooseEmpToUpd)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(btnChangeName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFelmeddelandeUppdateraAnstalldNamn))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(btnChangePhone)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFelmeddelandeUppdateraAnstalldTelefon))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(btnChangeMail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFelmeddelandeUppdateraAnstalldEpost))))
                    .addComponent(lblBefintligMailUppdateraAnstalld)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel11Layout.createSequentialGroup()
                                    .addComponent(jRadioProjektledareUppdatera)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jRadioUppdateraRollNej)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnÄndraRollAnstalld))
                                .addComponent(cbUpdEmpProj, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbUpdPlat, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbUpdComp, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbBefintligaKompetenser, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblNuvarandeStatusResultat))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(btnVisaSpecifikKompetens)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGap(261, 261, 261)
                                        .addComponent(btnTaBortAnstalldHelt))
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbSpecifikaKompetenser, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnVisaLevelSpecifikKompetens)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSpecifikLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnTaBortKompetensAnstalld)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblFelmeddelandeTaBortKompetensUppdateraAnstalld))))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(btnTaBortLevelUppdateraAnstalld)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLaggTillLevelUppdateraAnstalld)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnLaggTillKompetensUppdateraAnstalld)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(txtNivåUppdateraAnstalld, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblFelmeddelandeNivaUppdateraAnstalld))))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(btnTaBortPlattformUppdateraAnstalld)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLaggTillPlattformUppdateraAnstalld)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPlattformUppdateraAnstalld, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFelmeddelandePlattformUppdateraAnstalld))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(btnTaBortKompetensUppdateraAnstalld)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLaggTilKompetensUppdateraAnstalld)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtKompetensUppdateraAnstalld, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFelmeddelandeKompetensUppdateraAnstalld)))))
                .addContainerGap(160, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbUpdEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChooseEmpToUpd)
                    .addComponent(lblEmpPicker))
                .addGap(21, 21, 21)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdName)
                    .addComponent(txtUpdName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChangeName)
                    .addComponent(lblFelmeddelandeUppdateraAnstalldNamn))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUpdPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChangePhone)
                    .addComponent(lblUpdPhone)
                    .addComponent(lblFelmeddelandeUppdateraAnstalldTelefon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUpdMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUpdMail)
                    .addComponent(btnChangeMail)
                    .addComponent(lblSnabelAUppdatera)
                    .addComponent(txtHotmailUppdatera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPunktCom)
                    .addComponent(txtPunktComUppdatera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeUppdateraAnstalldEpost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBefintligEpost)
                    .addComponent(lblBefintligMailUppdateraAnstalld))
                .addGap(52, 52, 52)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbUpdComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUpdComp)
                    .addComponent(btnTaBortKompetensUppdateraAnstalld)
                    .addComponent(btnLaggTilKompetensUppdateraAnstalld)
                    .addComponent(lblFelmeddelandeKompetensUppdateraAnstalld)
                    .addComponent(txtKompetensUppdateraAnstalld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbUpdPlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUpdPlat)
                    .addComponent(btnTaBortPlattformUppdateraAnstalld)
                    .addComponent(btnLaggTillPlattformUppdateraAnstalld)
                    .addComponent(lblFelmeddelandePlattformUppdateraAnstalld)
                    .addComponent(txtPlattformUppdateraAnstalld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbUpdEmpProj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAssignProj)
                    .addComponent(btnTaBortLevelUppdateraAnstalld)
                    .addComponent(btnLaggTillLevelUppdateraAnstalld)
                    .addComponent(txtNivåUppdateraAnstalld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeNivaUppdateraAnstalld))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLaggTillKompetensUppdateraAnstalld)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbBefintligaKompetenser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBefintligaKompetenser)
                    .addComponent(btnVisaSpecifikKompetens)
                    .addComponent(btnVisaLevelSpecifikKompetens)
                    .addComponent(txtSpecifikLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaBortKompetensAnstalld)
                    .addComponent(lblFelmeddelandeTaBortKompetensUppdateraAnstalld)
                    .addComponent(cbSpecifikaKompetenser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUppdateraProjektledareUppdateraAnstalld)
                    .addComponent(jRadioProjektledareUppdatera)
                    .addComponent(btnÄndraRollAnstalld)
                    .addComponent(jRadioUppdateraRollNej))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNuvarandeStatus)
                    .addComponent(lblNuvarandeStatusResultat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTaBortAnstalldHelt)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Uppdatera", jPanel4);

        jTabbedPane1.addTab("Anställd", jTabbedPane3);

        lblNewPlatBen.setText("Benämning:");

        lblNewPlatBesk.setText("Beskrivning:");

        txtNewPlatBesk.setColumns(20);
        txtNewPlatBesk.setRows(5);
        jScrollPane3.setViewportView(txtNewPlatBesk);

        lblNewPlatProd.setText("Producent:");

        btnAddNewPlat.setText("Lägg till");
        btnAddNewPlat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddNewPlatMouseClicked(evt);
            }
        });

        lblFelmeddelandeLaggTillPlattformBenamning.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandeLaggTillPlattformBeskrivning.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandeLaggTillPlattformProducent.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNewPlatProd)
                            .addComponent(lblNewPlatBesk)
                            .addComponent(lblNewPlatBen))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNewPlatBen)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                            .addComponent(txtNewPlatProd))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFelmeddelandeLaggTillPlattformBenamning)
                            .addComponent(lblFelmeddelandeLaggTillPlattformBeskrivning)
                            .addComponent(lblFelmeddelandeLaggTillPlattformProducent)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(btnAddNewPlat)))
                .addContainerGap(442, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNewPlatBen)
                    .addComponent(txtNewPlatBen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeLaggTillPlattformBenamning))
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNewPlatBesk)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeLaggTillPlattformBeskrivning))
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNewPlatProd)
                    .addComponent(txtNewPlatProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeLaggTillPlattformProducent))
                .addGap(53, 53, 53)
                .addComponent(btnAddNewPlat)
                .addContainerGap(146, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Lägg till", jPanel5);

        lblChoosePlatUpd.setText("Välj plattform att uppdatera:");

        btnSelectedPlatUpd.setText("OK");
        btnSelectedPlatUpd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSelectedPlatUpdMouseClicked(evt);
            }
        });

        lblUpdPlatBen.setText("Ny benämning:");

        txtUpdPlatBen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUpdPlatBenActionPerformed(evt);
            }
        });

        lblUpdPlatBesk.setText("Ny beskrivning:");

        txtUpdPlatBesk.setColumns(20);
        txtUpdPlatBesk.setRows(5);
        jScrollPane4.setViewportView(txtUpdPlatBesk);

        lblUpdPlatProd.setText("Ny producent:");

        btnUpdatePlatform.setText("Uppdatera");
        btnUpdatePlatform.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdatePlatformMouseClicked(evt);
            }
        });

        lblFelmeddelandeUppdateraPlattformBenaming.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandeUppdateraPlattformBeskrivning.setForeground(new java.awt.Color(255, 0, 0));

        lblFelmeddelandeUppdateraPlattformProducent.setForeground(new java.awt.Color(255, 0, 0));

        btnTaBortPlattform.setText("Ta bort helt");
        btnTaBortPlattform.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaBortPlattformMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUpdPlatProd)
                    .addComponent(lblUpdPlatBesk)
                    .addComponent(lblUpdPlatBen)
                    .addComponent(lblChoosePlatUpd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnUpdatePlatform)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                        .addComponent(btnTaBortPlattform))
                    .addComponent(cbCurrentPlats, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUpdPlatBen)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(txtUpdPlatProd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSelectedPlatUpd)
                    .addComponent(lblFelmeddelandeUppdateraPlattformBenaming)
                    .addComponent(lblFelmeddelandeUppdateraPlattformBeskrivning)
                    .addComponent(lblFelmeddelandeUppdateraPlattformProducent))
                .addContainerGap(386, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChoosePlatUpd)
                    .addComponent(cbCurrentPlats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectedPlatUpd))
                .addGap(47, 47, 47)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdPlatBen)
                    .addComponent(txtUpdPlatBen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeUppdateraPlattformBenaming))
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUpdPlatBesk)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeUppdateraPlattformBeskrivning))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpdPlatProd)
                    .addComponent(txtUpdPlatProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeUppdateraPlattformProducent))
                .addGap(53, 53, 53)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdatePlatform)
                    .addComponent(btnTaBortPlattform))
                .addGap(74, 74, 74))
        );

        jTabbedPane4.addTab("Uppdatera", jPanel6);

        jTabbedPane1.addTab("Plattform", jTabbedPane4);

        lblBenamningKunskapsdomän.setText("Benämning:");

        lblBeskrivningKunskapsdomän.setText("Beskrivning:");

        txtABeskrivningKunskapsdomän.setColumns(20);
        txtABeskrivningKunskapsdomän.setRows(5);
        jScrollPane1.setViewportView(txtABeskrivningKunskapsdomän);

        btnLaggTillKunskapsdomän.setText("Lägg till");
        btnLaggTillKunskapsdomän.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaggTillKunskapsdomänMouseClicked(evt);
            }
        });

        lblFelmeddelandeBenämningKunskapsdomän.setForeground(new java.awt.Color(255, 0, 51));

        lblFelmeddelandeBeskrivningKunskapsdomän.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblBeskrivningKunskapsdomän)
                    .addComponent(lblBenamningKunskapsdomän))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                    .addComponent(txtBenamningKunskapsdomän))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFelmeddelandeBeskrivningKunskapsdomän)
                    .addComponent(lblFelmeddelandeBenämningKunskapsdomän))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(692, Short.MAX_VALUE)
                .addComponent(btnLaggTillKunskapsdomän)
                .addGap(201, 201, 201))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFelmeddelandeBenämningKunskapsdomän)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblBenamningKunskapsdomän)
                        .addComponent(txtBenamningKunskapsdomän, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBeskrivningKunskapsdomän)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeBeskrivningKunskapsdomän, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(49, 49, 49)
                .addComponent(btnLaggTillKunskapsdomän)
                .addContainerGap(231, Short.MAX_VALUE))
        );

        jTabbedPane5.addTab("Lägg till", jPanel7);

        lblBenamningKunskapsdomänUppdatera.setText("Benämning:");

        txtBenamningKunskapsdomänUppdatera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBenamningKunskapsdomänUppdateraActionPerformed(evt);
            }
        });

        lblBeskrivningKunskapsdomänUppdatera.setText("Beskrivning:");

        txtABeskrivningKunskapsdomänUppdatera.setColumns(20);
        txtABeskrivningKunskapsdomänUppdatera.setRows(5);
        jScrollPane2.setViewportView(txtABeskrivningKunskapsdomänUppdatera);

        btnTaBortHeltKunskapsdomän.setText("Ta bort helt");
        btnTaBortHeltKunskapsdomän.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaBortHeltKunskapsdomänMouseClicked(evt);
            }
        });

        lblFelmeddelandeBenämningKunskapsdomänUppdatera.setForeground(new java.awt.Color(255, 0, 51));

        lblFelmeddelandeBeskrivningKunskapsdomänUppdatera.setForeground(new java.awt.Color(255, 0, 51));

        lblVäljKunskapsdomänUppdatera.setText("Välj domän:");

        btnÄndraBenämningKompetensdomänUppdatera.setText("Ändra");
        btnÄndraBenämningKompetensdomänUppdatera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnÄndraBenämningKompetensdomänUppdateraMouseClicked(evt);
            }
        });

        btnÄndraBeskrivningKompetensdomänUppdatera.setText("Ändra");
        btnÄndraBeskrivningKompetensdomänUppdatera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnÄndraBeskrivningKompetensdomänUppdateraMouseClicked(evt);
            }
        });

        btnVäljKompetensdomänUppdatera.setText("Välj");
        btnVäljKompetensdomänUppdatera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVäljKompetensdomänUppdateraMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(553, Short.MAX_VALUE)
                .addComponent(btnTaBortHeltKunskapsdomän)
                .addGap(201, 201, 201))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblBeskrivningKunskapsdomänUppdatera)
                    .addComponent(lblBenamningKunskapsdomänUppdatera)
                    .addComponent(lblVäljKunskapsdomänUppdatera))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbVäljKunskapsdomänUppdatera, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                    .addComponent(txtBenamningKunskapsdomänUppdatera))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btnÄndraBenämningKompetensdomänUppdatera)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFelmeddelandeBeskrivningKunskapsdomänUppdatera)
                            .addComponent(lblFelmeddelandeBenämningKunskapsdomänUppdatera)))
                    .addComponent(btnÄndraBeskrivningKompetensdomänUppdatera)
                    .addComponent(btnVäljKompetensdomänUppdatera))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbVäljKunskapsdomänUppdatera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVäljKunskapsdomänUppdatera)
                    .addComponent(btnVäljKompetensdomänUppdatera))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFelmeddelandeBenämningKunskapsdomänUppdatera)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblBenamningKunskapsdomänUppdatera)
                        .addComponent(txtBenamningKunskapsdomänUppdatera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnÄndraBenämningKompetensdomänUppdatera)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBeskrivningKunskapsdomänUppdatera)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(lblFelmeddelandeBeskrivningKunskapsdomänUppdatera)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnÄndraBeskrivningKompetensdomänUppdatera)))
                .addGap(49, 49, 49)
                .addComponent(btnTaBortHeltKunskapsdomän)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 962, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane5.addTab("Uppdatera", jPanel8);

        jTabbedPane1.addTab("Kunskapsdomän", jTabbedPane5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Metoden tar det valda objektet i comboBoxen där användaren kan
     * välja projektledare att lägga till i ett nytt projekt, gör objektet till en sträng
     * och lägger sedan in strängen i det angivna textfältet.
     * @param evt 
     */
    private void btnLaggTillProjektledareMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillProjektledareMouseClicked
        String namn = cbLaggTillProjektledare.getSelectedItem().toString();
        txtLaggTillProjektledare.setText(namn);
    }//GEN-LAST:event_btnLaggTillProjektledareMouseClicked

    /**
     * Metoden "raderar" värdet i det textfält där användaren valt projektledare att lägga till i ett nytt projekt
     * genom att sätta värdet som ("").
     * @param evt 
     */
    private void btnLaggTillTaBortProjektledareMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillTaBortProjektledareMouseClicked
        txtLaggTillProjektledare.setText("");
    }//GEN-LAST:event_btnLaggTillTaBortProjektledareMouseClicked

    /**
     * Metoden tar det valda objektet i den angivna comboBoxen där användare
     * kan välja specialister att lägga till i projektet, gör det till en sträng,
     * raderar det valda objektet och lägger in det i den andra angivna comboBoxen där användaren kan lägga till specialister
     * att ha med i projektet.
     * Dessutom körs en substring på det valda objektet så att man endast får ut siffran i namnet.
     * Denna siffra läggs in i arrayListan som ska hålla alla AIDn för tillägning, för framtida bruk.
     * @param evt 
     */
    private void btnLaggTillSpecialistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillSpecialistMouseClicked
        try
        {
            String namn = cbLaggTillSpecialister.getSelectedItem().toString();
            String aid = cbLaggTillSpecialister.getSelectedItem().toString().substring(0, cbLaggTillSpecialister.getSelectedItem().toString().indexOf("."));
            aidLista.add(aid);
            cbLaggTillTillagdaSpecialister.addItem(namn);
            cbLaggTillSpecialister.removeItem(namn);
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Du kan inte lägga till fler specialister!");
        }
    }//GEN-LAST:event_btnLaggTillSpecialistMouseClicked

    /**
     * Metoden tar det valda objektet i den angivna comboBoxen där användaren lagt till specialister att ha med i projektet, 
     * gör det till en sträng,
     * raderar det valda objektet och lägger in det i den andra angivna comboBoxen där användaren kan välja specialiste att
     * lägga till i sitt nya projekt.
     * Dessutom körs en substring på det valda objektet så att man endast får ut siffran i namnet.
     * Denna siffra raderas från arrayListan som ska hålla alla AIDn för tillägning, för framtida bruk.
     * @param evt 
     */
    private void btnLaggTillTaBortSpecialistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillTaBortSpecialistMouseClicked
        try
        {
            String namn = cbLaggTillTillagdaSpecialister.getSelectedItem().toString();
            String aid = cbLaggTillTillagdaSpecialister.getSelectedItem().toString().substring(0, cbLaggTillSpecialister.getSelectedItem().toString().indexOf("."));
            aidLista.remove(aid);
            cbLaggTillSpecialister.addItem(namn);
            cbLaggTillTillagdaSpecialister.removeItem(namn);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Du kan inte ta bort fler specialister!");
        }
    }//GEN-LAST:event_btnLaggTillTaBortSpecialistMouseClicked

    /**
     * Metoden tar det valda objektet i den angivna comboBoxen där användaren kan välja plattformar
     * att lägga till i sitt nya projekt, gör det till en sträng,
     * raderar det valda objektet och lägger in det i den andra angivna comboBoxen där användaren lagt till
     * de plattformar denne vill ha i sitt nya projekt.
     * Dessutom körs en substring på det valda objektet så att man endast får ut siffran i namnet.
     * Denna siffra läggs in i arrayListan som ska hålla alla PIDn för tillägning, för framtida bruk.
     * @param evt 
     */
    private void btnLaggTillPlattformarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillPlattformarMouseClicked
        try
        {
            String namn = cbLaggTillPlattformar.getSelectedItem().toString();
            String pid = cbLaggTillPlattformar.getSelectedItem().toString().substring(0, cbLaggTillPlattformar.getSelectedItem().toString().indexOf("."));
            pidLista.add(pid);
            cbLaggTillTillagdaPlattformar.addItem(namn);
            cbLaggTillPlattformar.removeItem(namn);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Du kan inte lägga till fler plattformar!");
        }
    }//GEN-LAST:event_btnLaggTillPlattformarMouseClicked

    /**
     * Metoden tar det valda objektet i den angivna comboBoxen där användaren lagt till plattformar att
     * lägga till i sitt nya projekt, gör det till en sträng,
     * raderar det valda objektet och lägger in det i den andra angivna comboBoxen där användaren kan välja plattformar
     * att lägga till i sitt nya projekt.
     * Dessutom körs en substring på det valda objektet så att man endast får ut siffran i namnet.
     * Denna siffra raderas från arrayListan som ska hålla alla PIDn för tillägning, för framtida bruk.
     * @param evt 
     */
    private void btnLaggTillTaBortPLattformarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillTaBortPLattformarMouseClicked
       try
        {
            String namn = cbLaggTillTillagdaPlattformar.getSelectedItem().toString();
            String pid = cbLaggTillTillagdaPlattformar.getSelectedItem().toString().substring(0, cbLaggTillTillagdaPlattformar.getSelectedItem().toString().indexOf("."));
            pidLista.remove(pid);
            cbLaggTillPlattformar.addItem(namn);
            cbLaggTillTillagdaPlattformar.removeItem(namn);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Du kan inte ta bort fler plattformar!");
        }
    }//GEN-LAST:event_btnLaggTillTaBortPLattformarMouseClicked

    /**
     * Metoden tar det valda objektet i den angivna comboBoxen där användaren valt specialister att lägga till
     * eller ta bort, gör det till en sträng,
     * raderar det valda objektet och lägger in det i den andra angivna comboBoxen där användaren kan välja specialister
     * att lägga till eller ta bort i det valda projektet.
     * Dessutom körs en substring på det valda objektet så att man endast får ut siffran i namnet.
     * Denna siffra raderas från arrayListan som ska hålla alla AIDn för uppdatering, för framtida bruk.
     * @param evt 
     */
    private void btnUppdateraSpecialistTaBortMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUppdateraSpecialistTaBortMouseClicked
        try
        {
            String namn = cbUppdateraSpecialisterTillagda.getSelectedItem().toString();
            String aid = cbUppdateraSpecialisterTillagda.getSelectedItem().toString().substring(0, cbUppdateraSpecialisterTillagda.getSelectedItem().toString().indexOf("."));
            aidListaUppdatera.remove(aid);
            cbUppdateraSpecialisterTillagda.removeItem(namn);
            cbUppdateraSpecialister.addItem(namn);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Du kan inte ta bort fler specialister!");
        }
    }//GEN-LAST:event_btnUppdateraSpecialistTaBortMouseClicked

    /**
     * Metoden tar det valda objektet i den angivna comboBoxen där användaren kan välja specialister att
     * lägga till eller ta bort i det valda projektet, gör det till en sträng,
     * raderar det valda objektet och lägger in det i den andra angivna comboBoxen där användaren har valt
     * specialister att lägga till eller ta bort i valt projekt.
     * Dessutom körs en substring på det valda objektet så att man endast får ut siffran i namnet.
     * Denna siffra läggs till i arrayListan som ska hålla alla AIDn för uppdatering, för framtida bruk.
     * @param evt 
     */
    private void btnUppdateraSpecialistLäggTillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUppdateraSpecialistLäggTillMouseClicked
        try
        {
            String namn = cbUppdateraSpecialister.getSelectedItem().toString();
            String aid = cbUppdateraSpecialister.getSelectedItem().toString().substring(0, cbUppdateraSpecialister.getSelectedItem().toString().indexOf("."));
            aidListaUppdatera.add(aid);
            cbUppdateraSpecialisterTillagda.addItem(namn);
            cbUppdateraSpecialister.removeItem(namn);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Du kan inte lägga till fler specialister!");
        }
    }//GEN-LAST:event_btnUppdateraSpecialistLäggTillMouseClicked

    /**
     * Metoden tar det valda objektet i den angivna comboBoxen där användaren valt plattformar att lägga till eller ta bort
     * , gör det till en sträng,
     * raderar det valda objektet och lägger in det i den andra angivna comboBoxen där användaren kan välja plattformar
     * att lägga till eller ta bort i valt projekt.
     * Dessutom körs en substring på det valda objektet så att man endast får ut siffran i namnet.
     * Denna siffra raderas från arrayListan som ska hålla alla PIDn för uppdatering, för framtida bruk.
     * @param evt 
     */
    private void btnUppdateraPlattformarTaBortMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUppdateraPlattformarTaBortMouseClicked
        try
        {
            String namn = cbUppdateraPlattformarTillagda.getSelectedItem().toString();
            String pid = cbUppdateraPlattformarTillagda.getSelectedItem().toString().substring(0, cbUppdateraPlattformarTillagda.getSelectedItem().toString().indexOf("."));
            pidListaUppdatera.remove(pid);
            cbUppdateraPlattformar.addItem(namn);
            cbUppdateraPlattformarTillagda.removeItem(namn);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Du kan inte ta bort fler plattformar!");
        }
    }//GEN-LAST:event_btnUppdateraPlattformarTaBortMouseClicked

    /**
     * Metoden tar det valda objektet i den angivna comboBoxen där användaren kan välja plattformar att lägga till
     * eller ta bort från valt projekt, gör det till en sträng,
     * raderar det valda objektet och lägger in det i den andra angivna comboBoxen där användaren valt plattformar
     * att lägga till eller ta bort från valt projekt.
     * Dessutom körs en substring på det valda objektet så att man endast får ut siffran i namnet.
     * Denna siffra läggs till i arrayListan som ska hålla alla PIDn för uppdatering, för framtida bruk.
     * @param evt 
     */
    private void btnUppdateraPlattformarLäggTillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUppdateraPlattformarLäggTillMouseClicked
        try
        {
            String namn = cbUppdateraPlattformar.getSelectedItem().toString();
            String pid = cbUppdateraPlattformar.getSelectedItem().toString().substring(0, cbUppdateraPlattformar.getSelectedItem().toString().indexOf("."));
            pidListaUppdatera.add(pid);
            cbUppdateraPlattformarTillagda.addItem(namn);
            cbUppdateraPlattformar.removeItem(namn);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Du kan inte lägga till fler plattformar!");
        }
    }//GEN-LAST:event_btnUppdateraPlattformarLäggTillMouseClicked

    /**
     * Metoden "raderar" värdet i det angivna textfältet där användaren valt projektledare att lägga till eller ta bort
     * i valt projekt, genom att sätta värdet som ("").
     * @param evt 
     */
    private void btnUppdateraProjektledareTaBortMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUppdateraProjektledareTaBortMouseClicked
        txtUppdateraProjektledareProjekt.setText("");
    }//GEN-LAST:event_btnUppdateraProjektledareTaBortMouseClicked

    /**
     * Metoden tar det valda objektet i comboBoxen där användaren kan välja projektledare att lägga till eller
     * ta bort för det valda objektet, gör objektet till en sträng och lägger sedan in strängen i det angivna textfältet.
     * @param evt 
     */
    private void btnUppdateraProjektledareLäggTillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUppdateraProjektledareLäggTillMouseClicked
        String namn = cbUppdateraProjektledare.getSelectedItem().toString();
        txtUppdateraProjektledareProjekt.setText(namn);
    }//GEN-LAST:event_btnUppdateraProjektledareLäggTillMouseClicked

    /**
     * Metoden nollställer alla värden under fliken "Uppdatera" som är en subflik av "Projekt".
     * Metoden använder sig av metoderna: nollStällAllaVärdenUppdateraProjekt(), laggTillProjektBeteckningUppdateraProjekt(),
     * laggTillStartdatumUppdateraProjekt(), laggTillReleasedatumUppdateraProjekt(),
     * laggTillProjektledareUppdateraProjekt(), laggTillAktuellaSpecialisterUppdateraProjekt(),
     * laggTillAktuellaPlattformarUppdateraProjekt().
     * @param evt 
     */
    private void btnVäljProjektUppdateraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVäljProjektUppdateraMouseClicked
         nollStällAllaVärdenUppdateraProjekt();
         laggTillProjektBeteckningUppdateraProjekt();
         laggTillStartdatumUppdateraProjekt();
         laggTillReleasedatumUppdateraProjekt();
         laggTillProjektledareUppdateraProjekt();
         laggTillAktuellaSpecialisterUppdateraProjekt();
         laggTillAktuellaPlattformarUppdateraProjekt();
    }//GEN-LAST:event_btnVäljProjektUppdateraMouseClicked

    /**
     * Metoden validerar ifall textfältet och textarean med metoderna "textAreaNotEmpty" och "textNotEmpty".
     * Ifall fälten är ifyllda tas ett inkrementerat Kid-värde fram med metoden getAutoIncrement.
     * Därefter tas värdena i textarean och textfältet ut och sätts in i en sql-fraga.
     * Denna sql-fråga skickas in till databasen med metoden insert.
     * 
     * Efter detta sätts textArean och textfältet med ("") samt de två
     * jLabels som finns sätts som null.
     * @param evt 
     */
    private void btnLaggTillKunskapsdomänMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillKunskapsdomänMouseClicked

        
        boolean santFalskt = true;
        
        if(!(Valideringsklass.textAreaNotEmpty(txtABeskrivningKunskapsdomän, lblFelmeddelandeBeskrivningKunskapsdomän)))
        {
            santFalskt=false;
        }
        if(!(Valideringsklass.textNotEmpty(txtBenamningKunskapsdomän, lblFelmeddelandeBenämningKunskapsdomän)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
            try
            {
                String kid = idb.getAutoIncrement("kompetensdoman", "kid");
                String benamning = txtBenamningKunskapsdomän.getText();
                String beskrivning = txtABeskrivningKunskapsdomän.getText();
                
                String sqlFraga = "insert into kompetensdoman values("+kid+", '"+benamning+"', '"+beskrivning+"')";
                idb.insert(sqlFraga);
            }
            catch(InformatikException e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            
            txtBenamningKunskapsdomän.setText("");
            txtABeskrivningKunskapsdomän.setText("");
            lblFelmeddelandeBeskrivningKunskapsdomän.setText(null);
            lblFelmeddelandeBenämningKunskapsdomän.setText(null);
            cbVäljKunskapsdomänUppdatera.removeAllItems();
            laggTillKompetensdomänerUppdateraKompetensdomän();
        }
    }//GEN-LAST:event_btnLaggTillKunskapsdomänMouseClicked

    private void btnVäljProjektUppdateraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVäljProjektUppdateraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVäljProjektUppdateraActionPerformed

    /**
     * Metoden validerar alla textfält, combBoxar och JDateChoosers med metoderna
     * textNotEmpty, jDateChooserEmpty, comboBoxEmpty.
     * 
     * Därefter skapas ett inkrementerat Sid-värde med metoden getAutoIncrement.
     * Vald beteckning, datum och projektledare görs till strängar. Därefter
     * laggs Sid-värdet, beteckning, datumen och projektledaren till i tabellen "Spelprojekt"
     * och skickas in till databasen med metoden insert.
     * 
     * Sedan loopas fältet aidLista igenom med en for-loop, hämtar indexvärdena och skickar
     * in dessa tillsammans med det inkrementerade Sid-värdet i tabellen "arbetar_i" in till
     * databasen med metoden insert.
     * 
     * Sedan loopas fältet pidLista igenom med en for-loop, hämtar indexvärdena och skickar
     * in dessa tillsammans med det inkrementerade Sid-värdet i tabellen "innefattar" in till
     * databasen med metoden insert.
     * 
     * Därefter nollställs alla värden i fönstret med metoden nollStällAllaVärdenLäggTillProjekt.
     * Fälten aidLista och pidLista rensas med metoden clear.
     * @param evt 
     */
    private void btnLaggTillProjektMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillProjektMouseClicked

        boolean santFalskt=true;
        
        if(!(Valideringsklass.textNotEmpty(txtLaggTillBeteckning, lblLaggTillMinMaxBeteckning)))
        {
            santFalskt=false;
        }
        
        if(!(Valideringsklass.jDateChooserEmpty(jDateChooserStartdatumProjekt, lblFelmeddelandeStartdatumProjektLaggTill)))
        {
            santFalskt=false;
        }
        
        if(!(Valideringsklass.jDateChooserEmpty(jDateChooserReleasedatumProjekt, lblFelmeddelandeReleasedatumProjektLaggTill)))
        {
            santFalskt=false;
        }
        
        if(!(Valideringsklass.textNotEmpty(txtLaggTillProjektledare, lblFelmeddelandeProjektledareProjektLaggTill)))
        {
            santFalskt=false;
        }
        
        if(!(Valideringsklass.comboBoxEmpty(cbLaggTillTillagdaSpecialister, lblFelmeddelandeSpecialisterProjektLaggTill)))
        {
            santFalskt=false;
        }
        
        if(!(Valideringsklass.comboBoxEmpty(cbLaggTillTillagdaPlattformar, lblFelmeddelandePlattformarProjektLaggTill)))
        {
            santFalskt=false;
        }
        
        else
        {
            JOptionPane.showMessageDialog(null, "Succes!");
            try
        
            {
            String sid = idb.getAutoIncrement("spelprojekt", "sid");
            String beteckning = txtLaggTillBeteckning.getText();
            String startdatum = getDate(jDateChooserStartdatumProjekt);
            String releasedatum = getDate(jDateChooserReleasedatumProjekt);
            String projektledare = txtLaggTillProjektledare.getText().substring(0, txtLaggTillProjektledare.getText().indexOf("."));
            
            String sqlFraga = "insert into spelprojekt values ("+sid+", '"+beteckning+"', '"+startdatum+"', '"+releasedatum+"',"+projektledare+")";
            idb.insert(sqlFraga);
            
            for(int i=0; i < aidLista.size(); i++)
            {
                String aid = aidLista.get(i);
                String sqlFragaAid = "insert into arbetar_i values ("+aid+", "+sid+")";
                idb.insert(sqlFragaAid);
            }
            
            for(int i=0; i < pidLista.size();i++)
            {
                String pid = pidLista.get(i);
                String sqlFragaPid = "insert into innefattar values ("+sid+", "+pid+")";
                idb.insert(sqlFragaPid);
            }
            
            String sqlFragaAntalLeddaProjekt="select antal_projetledar_uppdrag from PROJEKTLEDARE where aid="+projektledare+"";
            antalLeddaProjekt = idb.fetchSingle(sqlFragaAntalLeddaProjekt);
            int antalLeddaProjektInteger = Integer.parseInt(antalLeddaProjekt) + 1;
            String sqlFragaUppdateraAntalLeddaProjekt = "update projektledare set antal_projetledar_uppdrag="+antalLeddaProjektInteger+" where aid = "+projektledare+"";
            idb.update(sqlFragaUppdateraAntalLeddaProjekt);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
            
        nollStällAllaVärdenLäggTillProjekt();
        cbVäljProjektUppdatera.removeAllItems();
        laggTillProjektAttVäljaUppdateraProjekt();
        aidLista.clear();
        pidLista.clear();
            
        } 
    }//GEN-LAST:event_btnLaggTillProjektMouseClicked

    /**
     * Metoden tar det valda objektet i comboBoxen, gör det till en sträng och
     * hämtar sedan dess Kid-värde med metoden fetchSingle.
     * 
     * Därefter raderas alla värden i databasen, där Kid-värdet är, med hjälp av
     * metoden delete.
     * 
     * Därefter nollställs alla fält med (""), alla objekt i comboBoxen raderas
     * och läggs in på nytt med metoden laggTillKompetensdomänerUppdateraKompetensdomän.
     * @param evt 
     */
    private void btnTaBortHeltKunskapsdomänMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaBortHeltKunskapsdomänMouseClicked

        String kid = cbVäljKunskapsdomänUppdatera.getSelectedItem().toString().substring(0, cbVäljKunskapsdomänUppdatera.getSelectedItem().toString().indexOf("."));        
        try
        {
            String sqlFraga = "delete from kompetensdoman where kid="+kid+"";
            idb.delete(sqlFraga);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        txtBenamningKunskapsdomänUppdatera.setText("");
        txtABeskrivningKunskapsdomänUppdatera.setText("");
        lblFelmeddelandeBenämningKunskapsdomänUppdatera.setText("");
        lblFelmeddelandeBeskrivningKunskapsdomänUppdatera.setText("");
        cbVäljKunskapsdomänUppdatera.removeAllItems();
        laggTillKompetensdomänerUppdateraKompetensdomän();
    }//GEN-LAST:event_btnTaBortHeltKunskapsdomänMouseClicked

    private void txtBenamningKunskapsdomänUppdateraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBenamningKunskapsdomänUppdateraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBenamningKunskapsdomänUppdateraActionPerformed

    /**
     * Metoden validerar textfältet ifall det är ifyllt med metoden textNotEmpty.
     * 
     * Om tetfältet är ifyllt görs det valda objektet i comboBoxen, där man väljer projektbeteckning,
     * till en sträng. Denna sträng används för att ta fram Sid-värdet där denna sträng finns i tabellen "Spelprojekt".
     * Med detta Sid-värde anges fältet sid.
     * 
     * Därefter hämtas värdet från textfältet där användaren skrivit in en ny beteckning. Detta värde
     * ,tillsammans med sid fältet, används för att uppdatera beteckningen i tabellen "Spelprojekt" med den nya
     * beteckningen där sid fältet återfinns i tabellen.
     * 
     * Därefte nollställs alla värden under fliken "Uppdatera", under fliken "Projekt",
     * med metoden nollStällAllaVärdenUppdateraProjekt.
     * @param evt 
     */
    private void btnUppdateraBeteckningProjektMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUppdateraBeteckningProjektMouseClicked

        boolean santFalskt = true;
        
        if(!(Valideringsklass.textNotEmpty(txtUppdateraBeteckning)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
        
        try
        {
        String sid = cbVäljProjektUppdatera.getSelectedItem().toString().substring(0, cbVäljProjektUppdatera.getSelectedItem().toString().indexOf("."));
        String nyBeteckning = txtUppdateraBeteckning.getText();
        String sqlFraga = "update spelprojekt set beteckning = '"+nyBeteckning+"' where sid="+sid+"";
        idb.update(sqlFraga);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
         nollStällAllaVärdenUppdateraProjekt();
         cbVäljProjektUppdatera.removeAllItems();
         laggTillProjektAttVäljaUppdateraProjekt();
        }
    }//GEN-LAST:event_btnUppdateraBeteckningProjektMouseClicked

    /**
     * Metoden validerar JDateChoosern, där man väljer startdatum, ifall det är ifyllt med metoden jDateChooserEmpty.
     * 
     * Om fältet är ifyllt görs det valda objektet i comboBoxen, där man väljer projektbeteckning,
     * till en sträng. Denna sträng används för att ta fram Sid-värdet där denna sträng finns i tabellen "Spelprojekt".
     * Med detta Sid-värde anges fältet sid.
     * 
     * Därefter hämtas värdet från fältet där användaren valt ett nytt startdatum. Detta värde
     * ,tillsammans med sid fältet, används för att uppdatera beteckningen i tabellen "Spelprojekt" med det nya
     * startdatumet där sid fältet återfinns i tabellen.
     * 
     * Därefte nollställs alla värden under fliken "Uppdatera", under fliken "Projekt",
     * med metoden nollStällAllaVärdenUppdateraProjekt.
     * @param evt 
     */
    private void btnUppdateraStartdatumProjektMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUppdateraStartdatumProjektMouseClicked

        boolean santFalskt = true;
        
        if(Valideringsklass.jDateChooserEmpty(jDateChooserStartdatumUppdateraProjekt))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
        try
        {
        String sid = cbVäljProjektUppdatera.getSelectedItem().toString().substring(0, cbVäljProjektUppdatera.getSelectedItem().toString().indexOf("."));
        String startdatum = getDate(jDateChooserStartdatumUppdateraProjekt);
        String sqlFragaDatum = "update spelprojekt set startdatum='"+startdatum+"' where sid="+sid+"";
        idb.update(sqlFragaDatum);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        nollStällAllaVärdenUppdateraProjekt();
        }
    }//GEN-LAST:event_btnUppdateraStartdatumProjektMouseClicked

    /**
     * Metoden validerar JDateChoosern, där man väljer releasedatum, ifall det är ifyllt med metoden jDateChooserEmpty.
     * 
     * Om fältet är ifyllt görs det valda objektet i comboBoxen, där man väljer projektbeteckning,
     * till en sträng. Denna sträng används för att ta fram Sid-värdet där denna sträng finns i tabellen "Spelprojekt".
     * Med detta Sid-värde anges fältet sid.
     * 
     * Därefter hämtas värdet från fältet där användaren valt ett nytt releasedatum. Detta värde
     * ,tillsammans med sid fältet, används för att uppdatera beteckningen i tabellen "Spelprojekt" med det nya
     * releasedatumet där sid fältet återfinns i tabellen.
     * 
     * Därefte nollställs alla värden under fliken "Uppdatera", under fliken "Projekt",
     * med metoden nollStällAllaVärdenUppdateraProjekt.
     * @param evt 
     */
    private void btnUppdateraReleasedatumProjektMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUppdateraReleasedatumProjektMouseClicked

        boolean santFalskt = true;
        
        if(Valideringsklass.jDateChooserEmpty(jDateChooserReleasedatumUppdateraProjekt))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
        try
        {
            String sid = cbVäljProjektUppdatera.getSelectedItem().toString().substring(0, cbVäljProjektUppdatera.getSelectedItem().toString().indexOf("."));
            String releasedatum = getDate(jDateChooserReleasedatumUppdateraProjekt);
            String sqlFragaDatum = "update spelprojekt set releasedatum='"+releasedatum+"' where sid="+sid+"";
            idb.update(sqlFragaDatum);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        nollStällAllaVärdenUppdateraProjekt();
        }
    }//GEN-LAST:event_btnUppdateraReleasedatumProjektMouseClicked

    /**
     * Metoden validerar textfältet , där användaren väljer en ny projektledare,
     * ifall det är ifyllt med metoden textNotEmpty.
     * 
     * Om tetfältet är ifyllt görs det valda objektet i comboBoxen, där man väljer projektbeteckning,
     * till en sträng. Denna sträng används för att ta fram Sid-värdet där denna sträng finns i tabellen "Spelprojekt".
     * Med detta Sid-värde anges fältet sid.
     * 
     * Därefter hämtas värdet från textfältet där användaren valt en ny projektledare. Detta värde
     * ,tillsammans med sid fältet, används för att uppdatera beteckningen i tabellen "Spelprojekt" med den nya
     * projektledaren där sid fältet återfinns i tabellen.
     * 
     * Därefter nollställs alla värden under fliken "Uppdatera", under fliken "Projekt",
     * med metoden nollStällAllaVärdenUppdateraProjekt.
     * @param evt 
     */
    private void btnUppdateraProjektledareProjektMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUppdateraProjektledareProjektMouseClicked

        boolean santFalskt = true;
        
        if(!(Valideringsklass.textNotEmpty(txtUppdateraProjektledareProjekt)))
        {
            santFalskt = false;
        }
        if(santFalskt==true)
        {
        try
        {
            String sid = cbVäljProjektUppdatera.getSelectedItem().toString().substring(0, cbVäljProjektUppdatera.getSelectedItem().toString().indexOf("."));
            String aid = txtUppdateraProjektledareProjekt.getText().substring(0, txtUppdateraProjektledareProjekt.getText().indexOf("."));
            String sqlFragaAid = "update spelprojekt set aid="+aid+" where sid="+sid+"";
            idb.update(sqlFragaAid);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        nollStällAllaVärdenUppdateraProjekt();
        }
    }//GEN-LAST:event_btnUppdateraProjektledareProjektMouseClicked

    /**
     * Metoden validerar först om comboBoxen, där användaren fått lägga till nya specialister,
     * är ifylld.
     * 
     * Om comboBoxen är fylld görs det valda objektet i comboBoxen, där man väljer projektbeteckning,
     * till en sträng. Denna sträng används för att ta fram Sid-värdet där denna sträng finns i tabellen "Spelprojekt".
     * Med detta Sid-värde anges fältet sid.
     * 
     * Därefter raderas alla värden i tabellen "arbetar_i" med samma värde som fältet "sid"
     * med hjälp av metoden delete.
     * 
     * Sedan loopas fältet aidListaUppdatera igenom med en for-loop, hämtar indexvärdena och skickar
     * in dessa tillsammans med fältet sid i tabellen "arbetar_i" in till
     * databasen med metoden insert.
     * 
     * Sedan töms aidListaUppdatera med metoden clear.
     * Alla värden under fliken "Uppdatera", under fliken "Projekt", nollställs
     * med metoden nollStällAllaVärdenUppdateraProjekt.
     * @param evt 
     */
    private void btnUppdateraSpecialisterProjektMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUppdateraSpecialisterProjektMouseClicked

        boolean santFalskt = true;
        
        if(!(Valideringsklass.comboBoxEmpty(cbUppdateraSpecialisterTillagda)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
        
        String sid = cbVäljProjektUppdatera.getSelectedItem().toString().substring(0, cbVäljProjektUppdatera.getSelectedItem().toString().indexOf("."));
        
        try
        {
            String sqlFraga = "delete from arbetar_i where sid="+sid+"";
            idb.delete(sqlFraga);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        try
        {
            for(int i=0; i < aidListaUppdatera.size(); i++)
            {
                String aid = aidListaUppdatera.get(i);
                String sqlFragaAid = "insert into ARBETAR_I values ("+aid+", "+sid+");";
                idb.insert(sqlFragaAid);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        aidListaUppdatera.clear();
        nollStällAllaVärdenUppdateraProjekt();
        }
    }//GEN-LAST:event_btnUppdateraSpecialisterProjektMouseClicked

    /**
     * Metoden validerar först om comboBoxen, där användaren fått lägga till nya plattformar,
     * är ifylld.
     * 
     * Om comboBoxen är fylld görs det valda objektet i comboBoxen, där man väljer projektbeteckning,
     * till en sträng. Denna sträng används för att ta fram Sid-värdet där denna sträng finns i tabellen "Spelprojekt".
     * Med detta Sid-värde anges fältet sid.
     * 
     * Därefter raderas alla värden i tabellen "innefattar" med samma värde som fältet "sid"
     * med hjälp av metoden delete.
     * 
     * Sedan loopas fältet pidListaUppdatera igenom med en for-loop, hämtar indexvärdena och skickar
     * in dessa tillsammans med fältet "sid" i tabellen "innefattar" in till
     * databasen med metoden insert.
     * 
     * Sedan töms pidListaUppdatera med metoden clear.
     * Alla värden under fliken "Uppdatera", under fliken "Projekt", nollställs
     * med metoden nollStällAllaVärdenUppdateraProjekt.
     * @param evt 
     */
    private void btnUppdateraPlattformarProjektMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUppdateraPlattformarProjektMouseClicked

        boolean santFalskt = true;
        
        if(!(Valideringsklass.comboBoxEmpty(cbUppdateraPlattformarTillagda)))
        {
            santFalskt = false;
        }
        if(santFalskt==true)
        {
        String sid = cbVäljProjektUppdatera.getSelectedItem().toString().substring(0, cbVäljProjektUppdatera.getSelectedItem().toString().indexOf("."));
        
        try
        {
            String sqlFraga = "delete from innefattar where sid="+sid+"";
            idb.delete(sqlFraga);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        try
        {
            for(int i=0; i < pidListaUppdatera.size(); i++)
            {
                String pid = pidListaUppdatera.get(i);
                String sqlFragaPid = "insert into innefattar values ("+sid+", "+pid+")";
                idb.insert(sqlFragaPid);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        pidListaUppdatera.clear();
        nollStällAllaVärdenUppdateraProjekt();
        }
    }//GEN-LAST:event_btnUppdateraPlattformarProjektMouseClicked

    /**
     * Metoden nollställer textfältet, där användaren skriver in en uppdaterad benämning, och
     * textarean, där användaren skriver in en uppdaterad beskrivning, med ("").
     * 
     * Därefter sätts textfältet och textarean med de värden från den benämning användaren valt i comboBoxen
     * med metoderna laggTillBenamningUppdateraKompetensdoman och laggTillBeskrivingUppdateraKompetensdoman.
     * @param evt 
     */
    private void btnVäljKompetensdomänUppdateraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVäljKompetensdomänUppdateraMouseClicked
       
        txtBenamningKunskapsdomänUppdatera.setText("");
        txtABeskrivningKunskapsdomänUppdatera.setText("");
        laggTillBenamningUppdateraKompetensdoman();
        laggTillBeskrivingUppdateraKompetensdoman();
        
    }//GEN-LAST:event_btnVäljKompetensdomänUppdateraMouseClicked

    /**
     * Metoden validerar textfältet , där användaren skriver en ny benämning,
     * ifall det är ifyllt med metoden textNotEmpty.
     * 
     * Om tetfältet är ifyllt görs det valda objektet i comboBoxen, där man väljer domänbenämning,
     * till en sträng. Denna sträng används för att ta fram Kid-värdet där denna sträng finns i tabellen "Kunskapsdoman".
     * Med detta Kid-värde anges fältet kid.
     * 
     * Därefter hämtas värdet från textfältet där användaren skrivit in en ny benämning. Detta värde
     * ,tillsammans med kid fältet, används för att uppdatera benämningen i tabellen "kunskapsdoman" med den nya
     * benämning där kid fältet återfinns i tabellen.
     * 
     * Därefter nollställs alla värden under fliken "Uppdatera", under fliken "Kunskapsdomän",
     * med metoden ("").
     * @param evt 
     */
    private void btnÄndraBenämningKompetensdomänUppdateraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnÄndraBenämningKompetensdomänUppdateraMouseClicked
        boolean santFalskt = true;
        
        if(!(Valideringsklass.textNotEmpty(txtBenamningKunskapsdomänUppdatera, lblFelmeddelandeBenämningKunskapsdomänUppdatera)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
            String kid = cbVäljKunskapsdomänUppdatera.getSelectedItem().toString().substring(0, cbVäljKunskapsdomänUppdatera.getSelectedItem().toString().indexOf("."));
            
            try
            {
                String nyBenamning = txtBenamningKunskapsdomänUppdatera.getText();
                String sqlFragaNyBenamning = "update kompetensdoman set benamning= '"+nyBenamning+"' where kid="+kid+"";
                idb.update(sqlFragaNyBenamning);
            }
            catch(InformatikException e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        txtBenamningKunskapsdomänUppdatera.setText("");
        txtABeskrivningKunskapsdomänUppdatera.setText("");
        lblFelmeddelandeBenämningKunskapsdomänUppdatera.setText("");
        lblFelmeddelandeBeskrivningKunskapsdomänUppdatera.setText("");
        cbVäljKunskapsdomänUppdatera.removeAllItems();
        laggTillKompetensdomänerUppdateraKompetensdomän();
        
        }
    }//GEN-LAST:event_btnÄndraBenämningKompetensdomänUppdateraMouseClicked

    /**
     * Metoden validerar textarean, där användaren skriver en ny beskrivning,
     * ifall det är ifyllt med metoden textAreaNotEmpty.
     * 
     * Om textarean är ifylld görs det valda objektet i comboBoxen, där man väljer domänbenämning,
     * till en sträng. Denna sträng används för att ta fram Kid-värdet där denna sträng finns i tabellen "Kunskapsdoman".
     * Med detta Kid-värde anges fältet kid.
     * 
     * Därefter hämtas värdet från textarean där användaren skrivit in en ny beskrivning. Detta värde
     * ,tillsammans med kid fältet, används för att uppdatera beskrivningen i tabellen "kunskapsdoman" med den nya
     * beskrivning där kid fältet återfinns i tabellen.
     * 
     * Därefter nollställs alla värden under fliken "Uppdatera", under fliken "Kunskapsdomän",
     * med metoden ("").
     * @param evt 
     */
    private void btnÄndraBeskrivningKompetensdomänUppdateraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnÄndraBeskrivningKompetensdomänUppdateraMouseClicked
        boolean santFalskt = true;
        
        if(!(Valideringsklass.textAreaNotEmpty(txtABeskrivningKunskapsdomänUppdatera, lblFelmeddelandeBeskrivningKunskapsdomänUppdatera)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
            String kid = cbVäljKunskapsdomänUppdatera.getSelectedItem().toString().substring(0, cbVäljKunskapsdomänUppdatera.getSelectedItem().toString().indexOf("."));
            
            try
            {
                String nyBeskrivning = txtABeskrivningKunskapsdomänUppdatera.getText();
                String sqlFragaBeskrivning = "update kompetensdoman set beskrivning='"+nyBeskrivning+"' where kid="+kid+"";
                idb.update(sqlFragaBeskrivning);
            }
            catch(InformatikException e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            
        txtBenamningKunskapsdomänUppdatera.setText("");
        txtABeskrivningKunskapsdomänUppdatera.setText("");
        lblFelmeddelandeBenämningKunskapsdomänUppdatera.setText("");
        lblFelmeddelandeBeskrivningKunskapsdomänUppdatera.setText("");
            
        }
    }//GEN-LAST:event_btnÄndraBeskrivningKompetensdomänUppdateraMouseClicked

    /**
     * Metoden tar det valda objektet i comboBoxen,där användaren väljer projektbeteckning, gör det till en sträng och
     * hämtar sedan dess Sid-värde med metoden fetchSingle och anger fältet "sid" med detta värde.
     * 
     * Därefter raderas alla värden i databasen, där Sid fältet är, med hjälp av
     * metoden delete.
     * 
     * Därefter nollställs alla fält med metoden nollStällAllaVärdenUppdateraProjekt, alla objekt i comboBoxen raderas
     * och läggs in på nytt med metoden laggTillProjektAttVäljaUppdateraProjekt.
     * @param evt 
     */
    private void btnTaBortHeltProjektMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaBortHeltProjektMouseClicked
        boolean santFalskt = true;
        
        if(!(Valideringsklass.textNotEmpty(txtUppdateraBeteckning)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
        String sid = cbVäljProjektUppdatera.getSelectedItem().toString().substring(0, cbVäljProjektUppdatera.getSelectedItem().toString().indexOf("."));
        
        try
        {
            String sqlFraga = "delete from spelprojekt where sid="+sid+"";
            idb.delete(sqlFraga);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        nollStällAllaVärdenUppdateraProjekt();
        cbVäljProjektUppdatera.removeAllItems();
        laggTillProjektAttVäljaUppdateraProjekt();
        }
    }//GEN-LAST:event_btnTaBortHeltProjektMouseClicked

    private void btnLaggTillKompetensUppdateraAnstalldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggTillKompetensUppdateraAnstalldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLaggTillKompetensUppdateraAnstalldActionPerformed

    private void txtUpdMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUpdMailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUpdMailActionPerformed

    private void cbUpdEmpProjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUpdEmpProjActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbUpdEmpProjActionPerformed

    /**
     * Metoden validerar alla fält med valideringsmetoderna "textNotEmpty" och "textNotEmptyMail".
     * 
     * Metoden hämtar alla värden från alla fält och skickar in dessa till databsen för tillägning.
     * Metoden lägger alltid till de olika värdena i tabellerna "Anstalld", "Har_kompetens", och "Specialist", men om
     * användaren checkat i radioknappen så läggs även den nye anställde i tabellen "Projektledare".
     * @param evt 
     */
    private void btnConfirmNewEmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmNewEmpMouseClicked
        boolean santFalskt = true;
        
        if(!(Valideringsklass.textNotEmpty(txtNewName, lblFelmeddelandeNamnLaggTillAnstalld)))
        {
            santFalskt = false;
        }
        if(!(Valideringsklass.textNotEmpty(txtPhone, lblFelmeddelandeTelefonLaggTillAnstalld)))
        {
            santFalskt = false;
        }
        if(!(Valideringsklass.textNotEmptyMail(txtMail, lblFelmeddelandeEpostLaggTillAnstalld)))
        {
            santFalskt = false;
        }
        if(!(Valideringsklass.textNotEmptyMail(txtMailNamn, lblFelmeddelandeEpostLaggTillAnstalld)))
        {
            santFalskt = false;
        }
        if(!(Valideringsklass.textNotEmptyMail(txtPunktCom, lblFelmeddelandeEpostLaggTillAnstalld)))
        {
            santFalskt = false;
        }
        if(!(Valideringsklass.textNotEmpty(txtEmpComp, lblFelmeddelandeKompetensLaggTillAnstalld)))
        {
            santFalskt = false;
        }
        if(!(Valideringsklass.textNotEmpty(txtEmpPlat, lblFelmeddelandePlattformarLaggTillAnstalld)))
        {
            santFalskt = false;
        }
        if(!(Valideringsklass.textNotEmpty(txtEmpLevel, lblFelmeddelandeNivåLaggTillAnstalld)))
        {
            santFalskt = false;
        }
        if(santFalskt==true)
        {
        try 
        {
                String newAid = idb.getAutoIncrement("anstalld", "aid");
                String newEmployeeName = txtNewName.getText();
                String newEmployeePhone = txtPhone.getText().toString();
                String newEmployeeComp = txtEmpComp.getText().toString().substring(0, txtEmpComp.getText().toString().indexOf("."));
                String newEmployeePlat = txtEmpPlat.getText().toString().substring(0, txtEmpPlat.getText().toString().indexOf("."));
                String newEmployeeLevel = txtEmpLevel.getText().toString();
                String mejl = txtMail.getText() + "@" + txtMailNamn.getText() + "." + txtPunktCom.getText();
        
        
                String sqlFraga = "INSERT into ANSTALLD(AID, NAMN, TELEFON, MAIL)"
                + "VALUES(" +newAid+ ",'" +newEmployeeName+ "', '"+newEmployeePhone+"', '"+mejl+"');";
 
                String sqlFragaHarKompetens = "insert into HAR_KOMPETENS values"
                + "("+newAid+", "+newEmployeeComp+", "+newEmployeePlat+", "+newEmployeeLevel+")";
                
                String sqlFragaSpecialist = "insert into specialist values("+newAid+")";
                String sqlFragaProjektledare = "insert into projektledare values("+newAid+", "+0+")";
       
                idb.insert(sqlFraga);
                idb.insert(sqlFragaSpecialist);
                idb.insert(sqlFragaHarKompetens);
                
                if(jRadioProjektledare.isSelected()==true)
                {
                    idb.insert(sqlFragaProjektledare);
                }
                
                JOptionPane.showMessageDialog(null, "Personen inlagd!");
                nollStällAllaVärdenLaggTillAnstalld();
                cbUpdEmp.removeAllItems();
                laggTillAnstalldaAttUppdatera();
                }
                
                catch(InformatikException e)
                {
                    JOptionPane.showMessageDialog(null, e.getMessage());   
                }
        cbLaggTillProjektledare.removeAllItems();
        cbUppdateraProjektledare.removeAllItems();
        laggTillProjektledare();
        laggTillProjektledareUppdatera();
        }
    }//GEN-LAST:event_btnConfirmNewEmpMouseClicked

    /**
     * Metoden tar det valda objektet i comboBoxen, där användaren kan välja kompetenser att lägga till vid en 
     * tillägning av en anställd, gör objektet till en sträng och sätter fältet bredvid comboBoxen.
     * @param evt 
     */
    private void btnLaggTillKompetensLaggTillAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillKompetensLaggTillAnstalldMouseClicked
        String kompetens = cbCompAreas.getSelectedItem().toString();
        txtEmpComp.setText(kompetens);
    }//GEN-LAST:event_btnLaggTillKompetensLaggTillAnstalldMouseClicked

    /**
     * Metoden nollställer texfältet bredvid comboBoxen där användaren kan välja kompetenser att
     * lägga till vid en tillägning av en anställd.
     * @param evt 
     */
    private void btnTaBortKompetensLaggTillAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaBortKompetensLaggTillAnstalldMouseClicked
        txtEmpComp.setText("");
    }//GEN-LAST:event_btnTaBortKompetensLaggTillAnstalldMouseClicked

    /**
     * Metoden tar det valda objektet i comboBoxen, där användaren kan välja plattformar att lägga till vid en 
     * tillägning av en anställd, gör objektet till en sträng och sätter fältet bredvid comboBoxen.
     * @param evt 
     */
    private void btnLaggTillPlattformLaggTillAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillPlattformLaggTillAnstalldMouseClicked
        String plattform = cbPlatChoice.getSelectedItem().toString();
        txtEmpPlat.setText(plattform);
    }//GEN-LAST:event_btnLaggTillPlattformLaggTillAnstalldMouseClicked

    /**
     * Metoden nollställer texfältet bredvid comboBoxen där användaren kan välja plattformar att
     * lägga till vid en tillägning av en anställd.
     * @param evt 
     */
    private void btnTaBortPlattformLaggTillAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaBortPlattformLaggTillAnstalldMouseClicked
        txtEmpComp.setText("");
    }//GEN-LAST:event_btnTaBortPlattformLaggTillAnstalldMouseClicked

    /**
     * Metoden tar det valda objektet i comboBoxen, där användaren kan välja nivåer att lägga till vid en 
     * tillägning av en anställd, gör objektet till en sträng och sätter fältet bredvid comboBoxen.
     * @param evt 
     */
    private void btnLaggTillNivåLaggTillAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillNivåLaggTillAnstalldMouseClicked
        String niva = cbLevelPlattform.getSelectedItem().toString();
        txtEmpLevel.setText(niva);
    }//GEN-LAST:event_btnLaggTillNivåLaggTillAnstalldMouseClicked

    /**
     * Metoden nollställer texfältet bredvid comboBoxen där användaren kan välja plattformar att
     * lägga till vid en tillägning av en anställd.
     * @param evt 
     */
    private void btnTaBortNivåLaggTillAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaBortNivåLaggTillAnstalldMouseClicked
        txtEmpLevel.setText("");
    }//GEN-LAST:event_btnTaBortNivåLaggTillAnstalldMouseClicked

    private void txtMailNamnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMailNamnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMailNamnActionPerformed

    private void txtPunktComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPunktComActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPunktComActionPerformed

    private void txtPlattformUppdateraAnstalldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlattformUppdateraAnstalldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlattformUppdateraAnstalldActionPerformed

    /**
     * Metoden använder sig av metoderna nollStällAllaVärdenUppdateraAnstalld(), laggTillBefintligaKompetenserUppdateraAnstalld(),
     * laggTillNamnAnstalldUppdatera(), laggTillTelefonAnstalldUppdatera(), laggTillMailAnstalldUppdatera(),
     * kollaProjektledareLaggTillRoll().
     * @param evt 
     */
    private void btnChooseEmpToUpdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChooseEmpToUpdMouseClicked
        nollStällAllaVärdenUppdateraAnstalld();
        laggTillBefintligaKompetenserUppdateraAnstalld();
        laggTillNamnAnstalldUppdatera();
        laggTillTelefonAnstalldUppdatera();
        laggTillMailAnstalldUppdatera();
        kollaProjektledareLaggTillRoll();
    }//GEN-LAST:event_btnChooseEmpToUpdMouseClicked

    /**
     * Metoden tar AID och PID-värdet från comboBoxen där användaren kan välja en anställd att uppdatera
     * och befintliga plattformar den anställde har kompetens för.
     * 
     * Sedan körs en sql-fråga som hämtar benämningen från tabellen "kompetensdoman" som matchar 
     * AID och PID-värdena i tabellen "Har_Kompetens", lägger dessa värden i en arraylist och sätter de
     * i den comboBox där användaren kan välja befntliga kompetenser hos en anställd.
     * @param evt 
     */
    private void btnVisaSpecifikKompetensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVisaSpecifikKompetensMouseClicked
        cbSpecifikaKompetenser.removeAllItems();
        String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
        String pid = cbBefintligaKompetenser.getSelectedItem().toString().substring(0, cbBefintligaKompetenser.getSelectedItem().toString().indexOf("."));
 
        String sqlFraga = "select KOMPETENSDOMAN.benamning, kompetensdoman.kid from KOMPETENSDOMAN\n" +
"join HAR_KOMPETENS on HAR_KOMPETENS.KID=KOMPETENSDOMAN.KID\n" +
"join PLATTFORM on HAR_KOMPETENS.PID=PLATTFORM.PID\n" +
"join anstalld on HAR_KOMPETENS.AID=ANSTALLD.AID\n" +
"where PLATTFORM.pid="+pid+" and anstalld.aid="+aid+"";
        try
        {
            ArrayList<HashMap<String, String>> kompetenser = idb.fetchRows(sqlFraga);
            String laggTillKompetens = null;
            for(int i=0; i < kompetenser.size(); i++)
            {
                laggTillKompetens = kompetenser.get(i).get("kid");
                laggTillKompetens = laggTillKompetens + ". " + kompetenser.get(i).get("benamning");
                cbSpecifikaKompetenser.addItem(laggTillKompetens);
            }
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btnVisaSpecifikKompetensMouseClicked

    /**
     * Metoden tar AID, PID och KID -värdena från comboBoxen där användaren kan välja en anställd att uppdatera,
     * befintliga plattformar den anställde har kompetens för och befntliga kompetenser för en anställd.
     * 
     * Sedan körs en sql-fråga som hämtar benämningen från tabellen "kompetensdoman" som matchar 
     * AID och PID-värdena i tabellen "Har_Kompetens", lägger dessa värden i en arraylist och sätter de
     * i den comboBox där användaren kan välja befntlig nivå för en anställd.
     * @param evt 
     */
    private void btnVisaLevelSpecifikKompetensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVisaLevelSpecifikKompetensMouseClicked
        txtSpecifikLevel.setText("");
        String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
        String pid = cbBefintligaKompetenser.getSelectedItem().toString().substring(0, cbBefintligaKompetenser.getSelectedItem().toString().indexOf("."));
        String kid = cbSpecifikaKompetenser.getSelectedItem().toString().substring(0, cbSpecifikaKompetenser.getSelectedItem().toString().indexOf("."));
        String sqlFraga = "select kompetensniva from HAR_KOMPETENS\n" +
"join KOMPETENSDOMAN on HAR_KOMPETENS.KID=KOMPETENSDOMAN.KID\n" +
"join PLATTFORM on HAR_KOMPETENS.PID=PLATTFORM.PID\n" +
"join anstalld on HAR_KOMPETENS.AID=ANSTALLD.AID\n" +
"where PLATTFORM.pid="+pid+" and anstalld.aid="+aid+"\n" +
"and KOMPETENSDOMAN.kid="+kid+"";
        
        try
        {
            String nivå = idb.fetchSingle(sqlFraga);
            txtSpecifikLevel.setText(nivå);   
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btnVisaLevelSpecifikKompetensMouseClicked

    /**
     * Metoden validerar ifall comboBoxend där användaren kan välja befintlig kompetens och fältet där användaren
     * kan se befintlig nivå för en anställd.
     * 
     * Sedan hämtas de valda värdena i comboBoxarna och textfältet där användaren valt befintliga kompetenser
     * och skickas in till databasen för att radera de rader där dessa värden finns i tabellen "har_kompetens".
     * 
     * Metoden använder sig av nollStällAllaVärdenUppdateraAnstalld().
     * @param evt 
     */
    private void btnTaBortKompetensAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaBortKompetensAnstalldMouseClicked
        boolean santFalskt = true;
        
        if(!(Valideringsklass.comboBoxEmpty(cbSpecifikaKompetenser, lblFelmeddelandeTaBortKompetensUppdateraAnstalld)))
        {
            santFalskt=false;
        }
        if(!(Valideringsklass.textNotEmpty(txtSpecifikLevel, lblFelmeddelandeTaBortKompetensUppdateraAnstalld)))
        {
            santFalskt=false;
        }
        
        if(santFalskt==true)
        {    
        String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
        String pid = cbBefintligaKompetenser.getSelectedItem().toString().substring(0, cbBefintligaKompetenser.getSelectedItem().toString().indexOf("."));
        String kid = cbSpecifikaKompetenser.getSelectedItem().toString().substring(0, cbSpecifikaKompetenser.getSelectedItem().toString().indexOf("."));
        String level = txtSpecifikLevel.getText().toString();
        
        try
        {
            String sqlFragaRadera = "delete from har_kompetens where aid="+aid+" and pid="+pid+" and kid="+kid+""
                    + "and kompetensniva="+level+"";
            idb.delete(sqlFragaRadera);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        nollStällAllaVärdenUppdateraAnstalld();
        }
    }//GEN-LAST:event_btnTaBortKompetensAnstalldMouseClicked

    private void txtHotmailUppdateraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHotmailUppdateraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHotmailUppdateraActionPerformed

    /**
     * Metoden använder sig av metoderna kollaProjektledareUppdateraRoll()
     * och nollStällAllaVärdenUppdateraAnstalld().
     * @param evt 
     */
    private void btnÄndraRollAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnÄndraRollAnstalldMouseClicked
       kollaProjektledareUppdateraRoll();
       nollStällAllaVärdenUppdateraAnstalld();
       cbLaggTillProjektledare.removeAllItems();
       cbUppdateraProjektledare.removeAllItems();
       laggTillProjektledare();
       laggTillProjektledareUppdatera();
    }//GEN-LAST:event_btnÄndraRollAnstalldMouseClicked

    /**
     * Metoden validerar fältet där användaren kan skriva in ett nytt namn för en anställd.
     * 
     * Sedan hämtas AID-värdet från det valda objektet i comboBoxen där användaren valt en anställd att uppdatera
     * och uppdaterar den anställde med en sträng från fältet och med AID-värdet.
     * 
     * Metoden använder sig av nollStällAllaVärdenUppdateraAnstalld(),
     * tömmer comboBoxen där användaren kan välja anställda att uppdatera
     * och metoden laggTillAnstalldaAttUppdatera().
     * @param evt 
     */
    private void btnChangeNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChangeNameMouseClicked
        boolean santFalskt = true;
        
        if(!(Valideringsklass.textNotEmpty(txtUpdName, lblFelmeddelandeUppdateraAnstalldNamn)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
        String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
            
        try    
        {   
            String nyttNamn = txtUpdName.getText();
            String sqlFraga = "update anstalld set namn = '"+nyttNamn+"' where aid="+aid+"";
            idb.update(sqlFraga);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        nollStällAllaVärdenUppdateraAnstalld();
        cbUpdEmp.removeAllItems();
        laggTillAnstalldaAttUppdatera();
        }
    }//GEN-LAST:event_btnChangeNameMouseClicked

    /**
     * Metoden validerar fältet där användaren kan skriva in ett nytt telefonnummer för en anställd.
     * 
     * Sedan hämtas AID-värdet från det valda objektet i comboBoxen där användaren valt en anställd att uppdatera
     * och uppdaterar den anställde med en sträng från fältet och med AID-värdet.
     * 
     * Metoden använder sig av nollStällAllaVärdenUppdateraAnstalld()
     * @param evt 
     */
    private void btnChangePhoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChangePhoneMouseClicked
        boolean santFalskt = true;
        
        if(!(Valideringsklass.textNotEmpty(txtUpdPhone, lblFelmeddelandeUppdateraAnstalldTelefon)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
            String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
            
        try    
        {   
            String nyTelefon = txtUpdPhone.getText().toString();
            String sqlFraga = "update anstalld set telefon = '"+nyTelefon+"' where aid="+aid+"";
            idb.update(sqlFraga);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        nollStällAllaVärdenUppdateraAnstalld();
        }
    }//GEN-LAST:event_btnChangePhoneMouseClicked

    /**
     * Metoden validerar fälten där användaren kan skriva in en ny mejladress för en anställd.
     * 
     * Sedan hämtas AID-värdet från det valda objektet i comboBoxen där användaren valt en anställd att uppdatera
     * och uppdaterar den anställde med en sträng från fältet och med AID-värdet.
     * 
     * Metoden använder sig av nollStällAllaVärdenUppdateraAnstalld()
     * @param evt 
     */
    private void btnChangeMailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChangeMailMouseClicked
        boolean santFalskt = true;
        
        if(!(Valideringsklass.textNotEmptyMail(txtUpdMail, lblFelmeddelandeUppdateraAnstalldEpost)))
        {
            santFalskt=false;
        }
        if(!(Valideringsklass.textNotEmptyMail(txtHotmailUppdatera, lblFelmeddelandeUppdateraAnstalldEpost)))
        {
            santFalskt=false;
        }
        if(!(Valideringsklass.textNotEmptyMail(txtPunktComUppdatera, lblFelmeddelandeUppdateraAnstalldEpost)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
            try
            {
            String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
            String mejl = txtUpdMail.getText() + "@" + txtHotmailUppdatera.getText() + "." + txtPunktComUppdatera.getText();
            String sqlFraga = "update anstalld set mail='"+mejl+"' where aid="+aid+"";
            idb.update(sqlFraga);
            }
            catch(InformatikException e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            nollStällAllaVärdenUppdateraAnstalld();
        }
    }//GEN-LAST:event_btnChangeMailMouseClicked

    /**
     * Metoden validerar de fält där användaren skrivit in plattform, kompetens och nivå som ska läggas till för en anställd.
     * 
     * Sedan hämtas alla värdena från textfälten, görs till strängar och läggs till i tabellen "har_kompetens".
     * 
     * Metoden använder sig av metoden nollStällAllaVärdenUppdateraAnstalld().
     * @param evt 
     */
    private void btnLaggTillKompetensUppdateraAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillKompetensUppdateraAnstalldMouseClicked
        boolean santFalskt = true;
        
        if(!(Valideringsklass.textNotEmpty(txtPlattformUppdateraAnstalld, lblFelmeddelandeKompetensUppdateraAnstalld)))
        {
            santFalskt=false;
        }
        if(!(Valideringsklass.textNotEmpty(txtKompetensUppdateraAnstalld, lblFelmeddelandePlattformUppdateraAnstalld)))
        {
            santFalskt=false;
        }
        if(!(Valideringsklass.textNotEmpty(txtNivåUppdateraAnstalld, lblFelmeddelandeNivaUppdateraAnstalld)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
                try
                {
                    String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
                    String updComp = txtKompetensUppdateraAnstalld.getText().toString().substring(0, txtKompetensUppdateraAnstalld.getText().toString().indexOf("."));
                    String updPlat = txtPlattformUppdateraAnstalld.getText().toString().substring(0, txtPlattformUppdateraAnstalld.getText().toString().indexOf("."));
                    String updLevel = txtNivåUppdateraAnstalld.getText().toString();
                    String sqlFraga = "insert into har_kompetens values("+aid+", "+updComp+", "+updPlat+", "+updLevel+")";
                    idb.insert(sqlFraga);
                }
                catch(InformatikException e)
                {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                nollStällAllaVärdenUppdateraAnstalld();
        }
    }//GEN-LAST:event_btnLaggTillKompetensUppdateraAnstalldMouseClicked

    /**
     * Metoden tar det valda objektet i comboBoxen, där användaren kan välja kompetenser att lägga till vid en 
     * uppdatering av en anställd, gör objektet till en sträng och sätter fältet bredvid comboBoxen.
     * @param evt 
     */
    private void btnLaggTilKompetensUppdateraAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTilKompetensUppdateraAnstalldMouseClicked
        String kompetens = cbUpdComp.getSelectedItem().toString();
        txtKompetensUppdateraAnstalld.setText(kompetens);
    }//GEN-LAST:event_btnLaggTilKompetensUppdateraAnstalldMouseClicked

    /**
     * Metoden nollställer texfältet bredvid comboBoxen där användaren kan välja kompetenser att
     * lägga till vid en uppdatering av en anställd.
     * @param evt 
     */
    private void btnTaBortKompetensUppdateraAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaBortKompetensUppdateraAnstalldMouseClicked
        txtKompetensUppdateraAnstalld.setText("");
    }//GEN-LAST:event_btnTaBortKompetensUppdateraAnstalldMouseClicked

    /**
     * Metoden tar det valda objektet i comboBoxen, där användaren kan välja plattformar att lägga till vid en 
     * uppdatering av en anställd, gör objektet till en sträng och sätter fältet bredvid comboBoxen.
     * @param evt 
     */
    private void btnLaggTillPlattformUppdateraAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillPlattformUppdateraAnstalldMouseClicked
        String plattform = cbUpdPlat.getSelectedItem().toString();
        txtPlattformUppdateraAnstalld.setText(plattform);
    }//GEN-LAST:event_btnLaggTillPlattformUppdateraAnstalldMouseClicked

    private void txtKompetensUppdateraAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtKompetensUppdateraAnstalldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKompetensUppdateraAnstalldMouseClicked

    /**
     * Metoden nollställer texfältet bredvid comboBoxen där användaren kan välja plattformar att
     * lägga till vid en uppdatering av en anställd.
     * @param evt 
     */
    private void btnTaBortPlattformUppdateraAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaBortPlattformUppdateraAnstalldMouseClicked
        txtPlattformUppdateraAnstalld.setText("");
    }//GEN-LAST:event_btnTaBortPlattformUppdateraAnstalldMouseClicked

    /**
     * Metoden tar det valda objektet i comboBoxen, där användaren kan välja nivåer att lägga till vid en 
     * uppdatering av en anställd, gör objektet till en sträng och sätter fältet bredvid comboBoxen.
     * @param evt 
     */
    private void btnLaggTillLevelUppdateraAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaggTillLevelUppdateraAnstalldMouseClicked
        String level = cbUpdEmpProj.getSelectedItem().toString();
        txtNivåUppdateraAnstalld.setText(level);
    }//GEN-LAST:event_btnLaggTillLevelUppdateraAnstalldMouseClicked

    /**
     * Metoden nollställer texfältet bredvid comboBoxen där användaren kan välja plattformar att
     * lägga till vid en uppdatering av en anställd.
     * @param evt 
     */
    private void btnTaBortLevelUppdateraAnstalldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaBortLevelUppdateraAnstalldMouseClicked
        txtNivåUppdateraAnstalld.setText("");
    }//GEN-LAST:event_btnTaBortLevelUppdateraAnstalldMouseClicked

    /**
     * När denna knapp trycks så sätts knappen där det står "Ja" som otryckt.
     * @param evt 
     */
    private void jRadioUppdateraRollNejMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioUppdateraRollNejMouseClicked
        jRadioProjektledareUppdatera.setSelected(false);
    }//GEN-LAST:event_jRadioUppdateraRollNejMouseClicked

    /**
     * När denna knapp trycks så sätts knappen där det står "Nej" som otryckt.
     * @param evt 
     */
    private void jRadioProjektledareUppdateraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioProjektledareUppdateraMouseClicked
        jRadioUppdateraRollNej.setSelected(false);
    }//GEN-LAST:event_jRadioProjektledareUppdateraMouseClicked

    private void txtUpdPlatBenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUpdPlatBenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUpdPlatBenActionPerformed

    /**
     * Nedan tillämpar metoden för att lägga till en ny plattform i databasen vid knapptryck samt ger bekräftelse
     * på att det är utfört.
     * @param evt 
     */
    private void btnAddNewPlatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddNewPlatMouseClicked
        boolean santFalskt=true;
        if(!(Valideringsklass.textNotEmpty(txtNewPlatProd, lblFelmeddelandeLaggTillPlattformProducent)))
        {
            santFalskt=false;
        }
        if(!(Valideringsklass.textAreaNotEmpty(txtNewPlatBesk, lblFelmeddelandeLaggTillPlattformBeskrivning)))
        {
            santFalskt=false;
        }
        if(!(Valideringsklass.textNotEmpty(txtNewPlatBen, lblFelmeddelandeLaggTillPlattformBenamning)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
        laggTillNyPlattform();
        JOptionPane.showMessageDialog(null, "Uppdaterat!");
        nollStällAllaVärdenLaggTillPlattform();
        cbCurrentPlats.removeAllItems();
        laggInPlattformarIFlikenUppdatera();
        }
    }//GEN-LAST:event_btnAddNewPlatMouseClicked

    /**
     * Nedan tillämpar metoderna för att hämta ut information från databasen till redigeringsrutorna för 
     * den valda plattformen att uppdatera i comboboxen
     * @param evt 
     */
    private void btnSelectedPlatUpdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSelectedPlatUpdMouseClicked
        nollStällAllaVärdenUppdateraPlattform();
        autoFyllIProducentPlattform();
        autoFyllIBenamningPlattform();
        autoFyllIBeskrivningPlattform();
    }//GEN-LAST:event_btnSelectedPlatUpdMouseClicked

    /**
     * tillämpar samtliga updatemetoder vid musklick på knappen "uppdatera" och ger en bekräftelse på att det är gjort.
     * @param evt 
     */
    private void btnUpdatePlatformMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdatePlatformMouseClicked
        boolean santFalskt=true;
        if(!(Valideringsklass.textNotEmpty(txtUpdPlatBen, lblFelmeddelandeUppdateraPlattformBenaming)))
        {
            santFalskt=false;
        }
        if(!(Valideringsklass.textAreaNotEmpty(txtUpdPlatBesk, lblFelmeddelandeUppdateraPlattformBeskrivning)))
        {
            santFalskt=false;
        }
        if(!(Valideringsklass.textNotEmpty(txtUpdPlatProd, lblFelmeddelandeUppdateraPlattformProducent)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
        updatePlatformBenamning();
        updatePlatformBeskrivning();
        updatePlatformProducer();
        JOptionPane.showMessageDialog(null, "Uppdaterat!");
        nollStällAllaVärdenUppdateraPlattform();
        cbCurrentPlats.removeAllItems();
        laggInPlattformarIFlikenUppdatera();
        }
    }//GEN-LAST:event_btnUpdatePlatformMouseClicked

    /**
     * Metoden hämtar först det valda objektet i comboBoxen, där användaren kan välja en plattform att uppdatera,
     * och hämtar sedan ut PID-värdet från tabellen "Plattform" där objektet finns.
     * 
     * Sedan raderas det objekt användaren vill radera med en SQL-fråga som raderar alla värden
     * där det hämtade PID-värdet finns i tabellen "Plattform".
     * @param evt 
     */
    private void btnTaBortPlattformMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaBortPlattformMouseClicked
        boolean santFalskt=true;
        if(!(Valideringsklass.textNotEmpty(txtUpdPlatBen, lblFelmeddelandeUppdateraPlattformBenaming)))
        {
            santFalskt=false;
        }
        if(!(Valideringsklass.textAreaNotEmpty(txtUpdPlatBesk, lblFelmeddelandeUppdateraPlattformBeskrivning)))
        {
            santFalskt=false;
        }
        if(!(Valideringsklass.textNotEmpty(txtUpdPlatProd, lblFelmeddelandeUppdateraPlattformProducent)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
        
        String pid = cbCurrentPlats.getSelectedItem().toString().substring(0, cbCurrentPlats.getSelectedItem().toString().indexOf("."));
        
        String sqlFragaRadera = "delete from plattform where pid="+pid+"";
        try
        {
            idb.delete(sqlFragaRadera);
        }
        catch(InformatikException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        nollStällAllaVärdenUppdateraPlattform();
        cbCurrentPlats.removeAllItems();
        laggInPlattformarIFlikenUppdatera();
        }
    }//GEN-LAST:event_btnTaBortPlattformMouseClicked

    /**
     * Metoden validerar fälten där användaren kan uppdatera namn och telefonnummer.
     * 
     * Sedan hämtas AID-värdet från det valda objektet i comboBoxen där användaren kan välja en anställd att uppdatera
     * och raderar den anställde från tabellen "Anstalld" där AID-värdet återfinns.
     * 
     * Metoden använder sig av metoden nollStällAllaVärdenUppdateraAnstalld(), tömmer comboBoxen där användaren
     * kanvälja en anställd att uppdatera och fyller comboBoxen igen med metoden laggTillAnstalldaAttUppdatera().
     * @param evt 
     */
    private void btnTaBortAnstalldHeltMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaBortAnstalldHeltMouseClicked
        boolean santFalskt = true;
        
        if(!(Valideringsklass.textNotEmpty(txtUpdName, lblFelmeddelandeUppdateraAnstalldNamn)))
        {
            santFalskt=false;
        }
        if(!(Valideringsklass.textNotEmpty(txtUpdPhone, lblFelmeddelandeUppdateraAnstalldTelefon)))
        {
            santFalskt=false;
        }
        if(santFalskt==true)
        {
            String aid = cbUpdEmp.getSelectedItem().toString().substring(0, cbUpdEmp.getSelectedItem().toString().indexOf("."));
            
            String sqlFragaRadera="delete from anstalld where aid="+aid+"";
            try
            {
                idb.delete(sqlFragaRadera);
            }
            catch(InformatikException e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
                nollStällAllaVärdenUppdateraAnstalld();
                cbUpdEmp.removeAllItems();
                laggTillAnstalldaAttUppdatera();
                cbLaggTillProjektledare.removeAllItems();
        cbUppdateraProjektledare.removeAllItems();
        laggTillProjektledare();
        laggTillProjektledareUppdatera();
        }
    }//GEN-LAST:event_btnTaBortAnstalldHeltMouseClicked

    private void btnLaggTilKompetensUppdateraAnstalldItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnLaggTilKompetensUppdateraAnstalldItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLaggTilKompetensUppdateraAnstalldItemStateChanged

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
            java.util.logging.Logger.getLogger(LäggTillAllt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LäggTillAllt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LäggTillAllt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LäggTillAllt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LäggTillAllt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNewPlat;
    private javax.swing.JButton btnChangeMail;
    private javax.swing.JButton btnChangeName;
    private javax.swing.JButton btnChangePhone;
    private javax.swing.JButton btnChooseEmpToUpd;
    private javax.swing.JButton btnConfirmNewEmp;
    private javax.swing.JButton btnLaggTilKompetensUppdateraAnstalld;
    private javax.swing.JButton btnLaggTillKompetensLaggTillAnstalld;
    private javax.swing.JButton btnLaggTillKompetensUppdateraAnstalld;
    private javax.swing.JButton btnLaggTillKunskapsdomän;
    private javax.swing.JButton btnLaggTillLevelUppdateraAnstalld;
    private javax.swing.JButton btnLaggTillNivåLaggTillAnstalld;
    private javax.swing.JButton btnLaggTillPlattformLaggTillAnstalld;
    private javax.swing.JButton btnLaggTillPlattformUppdateraAnstalld;
    private javax.swing.JButton btnLaggTillPlattformar;
    private javax.swing.JButton btnLaggTillProjekt;
    private javax.swing.JButton btnLaggTillProjektledare;
    private javax.swing.JButton btnLaggTillSpecialist;
    private javax.swing.JButton btnLaggTillTaBortPLattformar;
    private javax.swing.JButton btnLaggTillTaBortProjektledare;
    private javax.swing.JButton btnLaggTillTaBortSpecialist;
    private javax.swing.JButton btnSelectedPlatUpd;
    private javax.swing.JButton btnTaBortAnstalldHelt;
    private javax.swing.JButton btnTaBortHeltKunskapsdomän;
    private javax.swing.JButton btnTaBortHeltProjekt;
    private javax.swing.JButton btnTaBortKompetensAnstalld;
    private javax.swing.JButton btnTaBortKompetensLaggTillAnstalld;
    private javax.swing.JButton btnTaBortKompetensUppdateraAnstalld;
    private javax.swing.JButton btnTaBortLevelUppdateraAnstalld;
    private javax.swing.JButton btnTaBortNivåLaggTillAnstalld;
    private javax.swing.JButton btnTaBortPlattform;
    private javax.swing.JButton btnTaBortPlattformLaggTillAnstalld;
    private javax.swing.JButton btnTaBortPlattformUppdateraAnstalld;
    private javax.swing.JButton btnUpdatePlatform;
    private javax.swing.JButton btnUppdateraBeteckningProjekt;
    private javax.swing.JButton btnUppdateraPlattformarLäggTill;
    private javax.swing.JButton btnUppdateraPlattformarProjekt;
    private javax.swing.JButton btnUppdateraPlattformarTaBort;
    private javax.swing.JButton btnUppdateraProjektledareLäggTill;
    private javax.swing.JButton btnUppdateraProjektledareProjekt;
    private javax.swing.JButton btnUppdateraProjektledareTaBort;
    private javax.swing.JButton btnUppdateraReleasedatumProjekt;
    private javax.swing.JButton btnUppdateraSpecialistLäggTill;
    private javax.swing.JButton btnUppdateraSpecialistTaBort;
    private javax.swing.JButton btnUppdateraSpecialisterProjekt;
    private javax.swing.JButton btnUppdateraStartdatumProjekt;
    private javax.swing.JButton btnVisaLevelSpecifikKompetens;
    private javax.swing.JButton btnVisaSpecifikKompetens;
    private javax.swing.JButton btnVäljKompetensdomänUppdatera;
    private javax.swing.JButton btnVäljProjektUppdatera;
    private javax.swing.JButton btnÄndraBenämningKompetensdomänUppdatera;
    private javax.swing.JButton btnÄndraBeskrivningKompetensdomänUppdatera;
    private javax.swing.JButton btnÄndraRollAnstalld;
    private javax.swing.JComboBox cbAktuellaPlattformarUppdatera;
    private javax.swing.JComboBox cbAktuellaSpecialisterUppdatera;
    private javax.swing.JComboBox cbBefintligaKompetenser;
    private javax.swing.JComboBox cbCompAreas;
    private javax.swing.JComboBox cbCurrentPlats;
    private javax.swing.JComboBox cbLaggTillPlattformar;
    private javax.swing.JComboBox cbLaggTillProjektledare;
    private javax.swing.JComboBox cbLaggTillSpecialister;
    private javax.swing.JComboBox cbLaggTillTillagdaPlattformar;
    private javax.swing.JComboBox cbLaggTillTillagdaSpecialister;
    private javax.swing.JComboBox cbLevelPlattform;
    private javax.swing.JComboBox cbPlatChoice;
    private javax.swing.JComboBox cbSpecifikaKompetenser;
    private javax.swing.JComboBox cbUpdComp;
    private javax.swing.JComboBox cbUpdEmp;
    private javax.swing.JComboBox cbUpdEmpProj;
    private javax.swing.JComboBox cbUpdPlat;
    private javax.swing.JComboBox cbUppdateraPlattformar;
    private javax.swing.JComboBox cbUppdateraPlattformarTillagda;
    private javax.swing.JComboBox cbUppdateraProjektledare;
    private javax.swing.JComboBox cbUppdateraSpecialister;
    private javax.swing.JComboBox cbUppdateraSpecialisterTillagda;
    private javax.swing.JComboBox cbVäljKunskapsdomänUppdatera;
    private javax.swing.JComboBox cbVäljProjektUppdatera;
    private com.toedter.calendar.JDateChooser jDateChooserReleasedatumProjekt;
    private com.toedter.calendar.JDateChooser jDateChooserReleasedatumUppdateraProjekt;
    private com.toedter.calendar.JDateChooser jDateChooserStartdatumProjekt;
    private com.toedter.calendar.JDateChooser jDateChooserStartdatumUppdateraProjekt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioProjektledare;
    private javax.swing.JRadioButton jRadioProjektledareUppdatera;
    private javax.swing.JRadioButton jRadioUppdateraRollNej;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JLabel lblAddEmpMail;
    private javax.swing.JLabel lblAddEmpName;
    private javax.swing.JLabel lblAddEmpPhone;
    private javax.swing.JLabel lblAktuellaPlattformarUppdatera;
    private javax.swing.JLabel lblAktuellaSpecialisterUppdatera;
    private javax.swing.JLabel lblAktuelltReleasedatumUppdateraResultat;
    private javax.swing.JLabel lblAktuelltStartdatumUppdateraResultat;
    private javax.swing.JLabel lblAssignProj;
    private javax.swing.JLabel lblBefintligEpost;
    private javax.swing.JLabel lblBefintligMailUppdateraAnstalld;
    private javax.swing.JLabel lblBefintligaKompetenser;
    private javax.swing.JLabel lblBenamningKunskapsdomän;
    private javax.swing.JLabel lblBenamningKunskapsdomänUppdatera;
    private javax.swing.JLabel lblBeskrivningKunskapsdomän;
    private javax.swing.JLabel lblBeskrivningKunskapsdomänUppdatera;
    private javax.swing.JLabel lblChoosePlatUpd;
    private javax.swing.JLabel lblCompAreas;
    private javax.swing.JLabel lblEmpPicker;
    private javax.swing.JLabel lblFelmeddelandeBenämningKunskapsdomän;
    private javax.swing.JLabel lblFelmeddelandeBenämningKunskapsdomänUppdatera;
    private javax.swing.JLabel lblFelmeddelandeBeskrivningKunskapsdomän;
    private javax.swing.JLabel lblFelmeddelandeBeskrivningKunskapsdomänUppdatera;
    private javax.swing.JLabel lblFelmeddelandeEpostLaggTillAnstalld;
    private javax.swing.JLabel lblFelmeddelandeKompetensLaggTillAnstalld;
    private javax.swing.JLabel lblFelmeddelandeKompetensUppdateraAnstalld;
    private javax.swing.JLabel lblFelmeddelandeLaggTillPlattformBenamning;
    private javax.swing.JLabel lblFelmeddelandeLaggTillPlattformBeskrivning;
    private javax.swing.JLabel lblFelmeddelandeLaggTillPlattformProducent;
    private javax.swing.JLabel lblFelmeddelandeNamnLaggTillAnstalld;
    private javax.swing.JLabel lblFelmeddelandeNivaUppdateraAnstalld;
    private javax.swing.JLabel lblFelmeddelandeNivåLaggTillAnstalld;
    private javax.swing.JLabel lblFelmeddelandePlattformUppdateraAnstalld;
    private javax.swing.JLabel lblFelmeddelandePlattformarLaggTillAnstalld;
    private javax.swing.JLabel lblFelmeddelandePlattformarProjektLaggTill;
    private javax.swing.JLabel lblFelmeddelandeProjektledareProjektLaggTill;
    private javax.swing.JLabel lblFelmeddelandeReleasedatumProjektLaggTill;
    private javax.swing.JLabel lblFelmeddelandeSpecialisterProjektLaggTill;
    private javax.swing.JLabel lblFelmeddelandeStartdatumProjektLaggTill;
    private javax.swing.JLabel lblFelmeddelandeTaBortKompetensUppdateraAnstalld;
    private javax.swing.JLabel lblFelmeddelandeTelefonLaggTillAnstalld;
    private javax.swing.JLabel lblFelmeddelandeUppdateraAnstalldEpost;
    private javax.swing.JLabel lblFelmeddelandeUppdateraAnstalldNamn;
    private javax.swing.JLabel lblFelmeddelandeUppdateraAnstalldTelefon;
    private javax.swing.JLabel lblFelmeddelandeUppdateraPlattformBenaming;
    private javax.swing.JLabel lblFelmeddelandeUppdateraPlattformBeskrivning;
    private javax.swing.JLabel lblFelmeddelandeUppdateraPlattformProducent;
    private javax.swing.JLabel lblLaggTillBeteckning;
    private javax.swing.JLabel lblLaggTillMinMaxBeteckning;
    private javax.swing.JLabel lblLaggTillPlattformar;
    private javax.swing.JLabel lblLaggTillReleasedatum;
    private javax.swing.JLabel lblLaggTillSpecialist;
    private javax.swing.JLabel lblLaggTillStartdatum;
    private javax.swing.JLabel lblLevelComp;
    private javax.swing.JLabel lblNewPlatBen;
    private javax.swing.JLabel lblNewPlatBesk;
    private javax.swing.JLabel lblNewPlatProd;
    private javax.swing.JLabel lblNuvarandeStatus;
    private javax.swing.JLabel lblNuvarandeStatusResultat;
    private javax.swing.JLabel lblPlatformKnowledge;
    private javax.swing.JLabel lblProjektledare;
    private javax.swing.JLabel lblPunkt;
    private javax.swing.JLabel lblPunktCom;
    private javax.swing.JLabel lblSnabelA;
    private javax.swing.JLabel lblSnabelAUppdatera;
    private javax.swing.JLabel lblStepOne;
    private javax.swing.JLabel lblStepTwo;
    private javax.swing.JLabel lblUpdComp;
    private javax.swing.JLabel lblUpdMail;
    private javax.swing.JLabel lblUpdName;
    private javax.swing.JLabel lblUpdPhone;
    private javax.swing.JLabel lblUpdPlat;
    private javax.swing.JLabel lblUpdPlatBen;
    private javax.swing.JLabel lblUpdPlatBesk;
    private javax.swing.JLabel lblUpdPlatProd;
    private javax.swing.JLabel lblUppdateraBeteckning;
    private javax.swing.JLabel lblUppdateraMinMaxBokstäver;
    private javax.swing.JLabel lblUppdateraPlattformar;
    private javax.swing.JLabel lblUppdateraProjektledare;
    private javax.swing.JLabel lblUppdateraProjektledareUppdateraAnstalld;
    private javax.swing.JLabel lblUppdateraReleasedatum;
    private javax.swing.JLabel lblUppdateraSpecialist;
    private javax.swing.JLabel lblUppdateraStartdatum;
    private javax.swing.JLabel lblVäljKunskapsdomänUppdatera;
    private javax.swing.JLabel lblVäljProjektUppdatera;
    private javax.swing.JTextArea txtABeskrivningKunskapsdomän;
    private javax.swing.JTextArea txtABeskrivningKunskapsdomänUppdatera;
    private javax.swing.JTextField txtBenamningKunskapsdomän;
    private javax.swing.JTextField txtBenamningKunskapsdomänUppdatera;
    private javax.swing.JTextField txtEmpComp;
    private javax.swing.JTextField txtEmpLevel;
    private javax.swing.JTextField txtEmpPlat;
    private javax.swing.JTextField txtHotmailUppdatera;
    private javax.swing.JTextField txtKompetensUppdateraAnstalld;
    private javax.swing.JTextField txtLaggTillBeteckning;
    private javax.swing.JTextField txtLaggTillProjektledare;
    private javax.swing.JTextField txtMail;
    private javax.swing.JTextField txtMailNamn;
    private javax.swing.JTextField txtNewName;
    private javax.swing.JTextField txtNewPlatBen;
    private javax.swing.JTextArea txtNewPlatBesk;
    private javax.swing.JTextField txtNewPlatProd;
    private javax.swing.JTextField txtNivåUppdateraAnstalld;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPlattformUppdateraAnstalld;
    private javax.swing.JTextField txtPunktCom;
    private javax.swing.JTextField txtPunktComUppdatera;
    private javax.swing.JTextField txtSpecifikLevel;
    private javax.swing.JTextField txtUpdMail;
    private javax.swing.JTextField txtUpdName;
    private javax.swing.JTextField txtUpdPhone;
    private javax.swing.JTextField txtUpdPlatBen;
    private javax.swing.JTextArea txtUpdPlatBesk;
    private javax.swing.JTextField txtUpdPlatProd;
    private javax.swing.JTextField txtUppdateraBeteckning;
    private javax.swing.JTextField txtUppdateraProjektledareProjekt;
    // End of variables declaration//GEN-END:variables
}
