/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import nvisio.ProperyFrame;
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
        propItem.addActionListener(new ActionListener() { 
                @Override
                public void actionPerformed(ActionEvent e) {  unitShowProperties(); } });
        this.add(propItem);
    }
    
    private void unitShowProperties(){
        UnitBean bean = new UnitBean(this.unit);
        VPanel.getPropertyFrame(bean).show();
    }
}
