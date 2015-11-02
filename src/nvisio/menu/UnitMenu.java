/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import nvisio.VPanel;
import nvisio.unit.Unit;
import nvisio.unit.beans.UnitBean;

/**
 *
 * @author Sinan
 */
public class UnitMenu extends JPopupMenu {
    Unit unit;
    
    public UnitMenu(Unit unit){
        this.unit = unit;
        
        JMenuItem propItem = new JMenuItem("Properties");
        JMenuItem addHist = new JMenuItem("Import SOCPRI history");
        
        propItem.addActionListener(new ActionListener() { 
                @Override
                public void actionPerformed(ActionEvent e) {  unitShowProperties(); } });
        addHist.addActionListener(new ActionListener() { 
                @Override
                public void actionPerformed(ActionEvent e) {  importSocpriHistory(); } });
        
        this.add(propItem);
        this.add(addHist);
    }
    
    private void unitShowProperties(){
        UnitBean bean = new UnitBean(this.unit);
        VPanel.getPropertyFrame(bean).show();
    }
    
    private void importSocpriHistory(){
        JFileChooser fc = new JFileChooser();
        int retVal = fc.showOpenDialog(this.unit.getPanel().getParent());
        
        if (retVal == JFileChooser.APPROVE_OPTION){
            File fi = fc.getSelectedFile();
            try{
                BufferedReader br = new BufferedReader(new FileReader(fi));
                String line;
                while ((line = br.readLine()) != null){
                    //System.out.println(line);
                    parseHistoryEntry(line);
                }
            } catch (Exception e){
                // TODO
                System.out.println("ERROR;" + e.getMessage());
            }
        }
    }
    
    // TODO move to other class or something
    private void parseHistoryEntry(String line){
        if (line.startsWith("[")){
            parseTimestamp(line);
            parseEvent(line);
            parseSyncPortStatus(line);
        }
    }
    
    private boolean parseTimestamp(String line){
        try{
            DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n");
            LocalDateTime dateTime = LocalDateTime.parse(line.substring(1, line.indexOf("]")), formater);
            System.out.println(dateTime.toString());
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    private boolean parseEvent(String line){
        try{
            System.out.println(getSubstring(line, "event = ", ", "));
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    private boolean parseSyncPortStatus(String line){
        try{
            System.out.println(getSubstring(line, "syncPortStatus = [ ", " ],"));
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    private String getSubstring(String input, String firstDelimiter, String lastDelimiter){
        int indexOfFirstDelimiter = input.indexOf(firstDelimiter);
        int indexOfSecondDelimiter = input.indexOf(lastDelimiter, indexOfFirstDelimiter);
        return input.substring(
                indexOfFirstDelimiter + firstDelimiter.length(), 
                indexOfSecondDelimiter);
    }
}
