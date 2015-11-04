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
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import nvisio.sim.InputParser;
import nvisio.unit.Unit;

/**
 *
 * @author Sinan
 */
public class UnitMenu extends JPopupMenu {
    Unit unit;
    
    public UnitMenu(Unit unit){
        this.unit = unit;

        if (this.unit.getUnitType() == Unit.UnitType.DIGITAL_UNIT) {
            JMenuItem addHist = new JMenuItem("Import SOCPRI history");
            addHist.addActionListener(new ActionListener() { 
                @Override public void actionPerformed(ActionEvent e) {  importSocpriHistory(); } });
        
            this.add(addHist);
        } else if (this.unit.getUnitType() == Unit.UnitType.XMU03){
            JMenuItem changeDir = new JMenuItem("Change direction");
            changeDir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    actionChangeDirection();
                }
            });
        }
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
                    InputParser.parseHistoryEntry(line);
                }
            } catch (Exception e){
                // TODO
                System.out.println("ERROR;" + e.getMessage());
            }
        }
    }
    
    private void actionChangeDirection(){
        
    }
    

}
