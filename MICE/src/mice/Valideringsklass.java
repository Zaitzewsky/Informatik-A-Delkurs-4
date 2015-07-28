/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mice;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import se.oru.informatik.InformatikDB;
import se.oru.informatik.InformatikException;
import java.util.Calendar;
import javax.swing.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import com.toedter.calendar.JDateChooser;

public class Valideringsklass {
    
    public Valideringsklass()
    {
        
    }
    
    /**
     * Metoden kollar om den valda comboBoxen är tom.
     * Om comboBoxen är tom sätts den valda jLabeln med en röd text
     * och texten "*Textfältet är tomt!" samt sätter comboBoxen i fokus.
     * @param cb
     * @param lbl
     * @return
     */
    static public boolean comboBoxEmpty(JComboBox cb, JLabel lbl)
    {
        int namn = cb.getItemCount();
        
        if(namn==0)
        {
            lbl.setForeground(Color.red);
            lbl.setText("*Textfältet är tomt!");
            cb.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }
    
    static public boolean removeCompEmpty(JComboBox cb, JTextField tf, JLabel lbl)
    {
        int namn = cb.getItemCount();
        String txt = tf.getText().toString();
        
        if(namn==0 && txt.isEmpty())
        {
            lbl.setForeground(Color.red);
            lbl.setText("*Tomma fält!");
            cb.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }
    
    /**
     * Metoden kollar om den valda comboBoxen är tom.
     * Om comboBoxen är tom instansieras en JOptionPane med meddelandet
     * "Listan är tom!" samt sätter comboBoxen i fokus.
     * @param cb
     * @return
     */
    static public boolean comboBoxEmpty(JComboBox cb)
    {
        int namn = cb.getItemCount();
        
        if(namn==0)
        {
            JOptionPane.showMessageDialog(null, "Listan är tom!");
            cb.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }
    
    /**
     * Metoden kollar om den valda jDateChoosern är tom.
     * Om den är tom sätts den valda jLabeln med en röd text
     * och texten "*Textfältet är tomt!" samt sätter jDateChoosern i fokus.
     * @param jd
     * @param lbl
     * @return
     */
    static public boolean jDateChooserEmpty(JDateChooser jd, JLabel lbl)
    {
        Date date;
        date=jd.getDate();
        
        if(date==null)
        {
            lbl.setForeground(Color.red);
            lbl.setText("*Textfältet är tomt!");
            jd.requestFocus();
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Metoden kollar om den valda jDateChoosern är tom.
     * Om den är tom instansieras en JOptionPane med meddelandet "Textfältet är tomt!"
     * samt sätter jDateChoosern i fokus.
     * @param jd
     * @return
     */
    static public boolean jDateChooserEmpty(JDateChooser jd)
    {
        Date date;
        date=jd.getDate();
        
        if(date==null)
        {
            JOptionPane.showMessageDialog(null, "Textfältet är tomt!");
            jd.requestFocus();
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Metoden kollar om det valda textfältet är tomt.
     * Om det är tomt sätts den valda jLabeln med en röd text
     * och texten "*Textfältet är tomt!" samt sätter textfältet i fokus.
     * @param tf
     * @param lbl
     * @return
     */
    static public boolean textNotEmpty(JTextField tf, JLabel lbl)
    {
        if(tf.getText().isEmpty()) 
                {
                    lbl.setForeground(Color.red);
                    lbl.setText("*Textfältet är tomt!");
                    tf.requestFocus();
                    return false;

                }
        else
        {
            return true;
        }
    }
    
    static public boolean textNotEmptyMail(JTextField tf, JLabel lbl)
    {
        if(tf.getText().isEmpty()) 
                {
                    lbl.setForeground(Color.red);
                    lbl.setText("*Inga textfält får vara tomma!");
                    tf.requestFocus();
                    return false;

                }
        else
        {
            return true;
        }
    }
    
    /**
     * Metoden kollar om det valda textfältet är tomt.
     * Om det är tomt instansieras en JOptionPane med meddelandet "Textfältet är tomt!"
     * samt sätter textfältet i fokus.
     * @param tf
     * @return
     */
    static public boolean textNotEmpty(JTextField tf)
    {
        if(tf.getText().isEmpty()) 
                {
                    JOptionPane.showMessageDialog(null, "Textfältet är tomt!");
                    tf.requestFocus();
                    return false;

                }
        else
        {
            return true;
        }
    }
    
    /**
     * Metoden kollar om den valda textarean är tom.
     * Om den är tom sätts den valda jLabeln med en röd text
     * och texten "*Textfältet är tomt!" samt sätter textarean i fokus.
     * @param tf
     * @param lbl
     * @return
     */
    static public boolean textAreaNotEmpty(JTextArea tf, JLabel lbl)
    {
        if(tf.getText().isEmpty()) 
                {
                    lbl.setText("*Textfältet är tomt!");
                    tf.requestFocus();
                    return false;

                }
        else
        {
            return true;
        }
    }
}


