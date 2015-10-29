/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.unit;

import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JPanel;
import nvisio.VPanel;
import nvisio.unit.beans.UnitBean;

/**
 *
 * @author Sinan
 */
public class RadioUnit extends Unit{
    
    private static int UNIT_INDEX = 0;
    
    public RadioUnit(VPanel panel, int x, int y){
        this.numberOfPorts = 2;
        this.parent = panel;
        this.parent.add(this);
        this.unitType = UnitType.RADIO_UNIT;
        this.unitName = String.format("RU%s", RadioUnit.UNIT_INDEX);
        RadioUnit.UNIT_INDEX++;
        
        this.setDefaultIcon();
        Rectangle r = new Rectangle(new Point(x,y), this.getPreferredSize());
        this.setBounds(r);
        
        super.init();
    }
    public RadioUnit(VPanel panel, UnitBean ub){
        this.numberOfPorts = ub.getNumberOfPorts();
        this.parent = panel;
        this.parent.add(this);
        this.unitType = ub.getUnitType();
        this.unitName = ub.getUnitName();
        
        this.setDefaultIcon();
        Rectangle r = new Rectangle(
                new Point(ub.getLocation().getX(), ub.getLocation().getY()), 
                this.getPreferredSize());
        this.setBounds(r);
        
        super.init();
    }
    
    
    @Override
    public Point getPortLocation(Port p){
        int w = (int)p.getPreferredSize().getWidth();
        int h = (int)p.getPreferredSize().getHeight();

        int x = p.getPortIndex() == 0 ? 
                this.getX()- w: // left port
                this.getX()+ this.getWidth();  // right port
        int y = this.getY() + (int)this.getHeight()/2 - h/2;
        
        // TODO: refactor
        Port c = p.getRemotePort();
        if (c != null && 
            c.unit.unitType == UnitType.DIGITAL_UNIT &&
            p.unit.getUnitType() == UnitType.RADIO_UNIT){
                c.setLocation(c.unit.getPortLocation(c));
        }
        
        return new Point(x, y);
    }

    @Override
    public void setDefaultIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/ru_b.png")));
    }

    @Override
    public void setSelectedIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/ru_o.png")));
    }

    @Override
    public void setHoverIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/ru_r_w.png")));
    }

}
