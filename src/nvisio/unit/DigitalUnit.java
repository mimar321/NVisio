/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.unit;

import java.awt.Dimension;
import java.awt.Point;
import nvisio.VPanel;
import nvisio.unit.beans.DigitalUnitBean;
import nvisio.unit.beans.PortBean;

/**
 *
 * @author Sinan
 */
public class DigitalUnit extends Unit{
    
    public enum DigitalUnitType {
        DUS41,
        DUW30,
        DUL20
    }
    
    private static int UNIT_INDEX = 0;
    
    private DigitalUnitType duType;
    
    public DigitalUnit(VPanel panel, int x, int y){

        this.numberOfPorts = 6;
        this.parent = panel;
        this.parent.add(this);
        this.duType = DigitalUnitType.DUS41;
        
        this.setDefaultIcon();
        Dimension size = this.getPreferredSize();
        this.setBounds(x, y, (int)(size.getWidth()), (int)(size.getHeight()));

        this.unitName = String.format("DU%s", DigitalUnit.UNIT_INDEX);
        DigitalUnit.UNIT_INDEX++;
        
        super.init();
    }
    
    public DigitalUnit(VPanel panel, DigitalUnitBean dub){
        this.numberOfPorts = dub.getNumberOfPorts();
        this.parent = panel;
        this.parent.add(this);
        this.duType = dub.getDuType();
        this.unitName = dub.getUnitName();
        
        this.setDefaultIcon();
        Dimension size = this.getPreferredSize();
        this.setBounds(dub.getLocation().getX(), dub.getLocation().getY(), (int)(size.getWidth()), (int)(size.getHeight()));
        
        super.init(); // init ports and mouse listeners
    }
    
    public DigitalUnitType getDuType(){
        return this.duType;
    }
    public String getDuTypeString(){
        switch (this.duType){
            case DUL20: return "DUL20";
            case DUS41: return "DUS41";
            case DUW30: return "DUW30";
            default: return "FFF";
        }
    }
    
    @Override
    public Point getPortLocation(Port p){
        int h = (int)p.getPreferredSize().getHeight();
        Port c = p.getRemotePort();
        int yOffset = (int)((this.getHeight() - (int)(8.5*h)) /2);
        
        if (c != null){
            if (c.getX() < this.getX() + this.getWidth()/2){
                return new Point(this.getX() - (int)p.getWidth(), 
                         this.getY() + yOffset + (int)(p.getPortIndex()*h*1.5));
            }
        }
        
        return new Point(this.getX() + (int)this.getWidth(), 
                         this.getY() + yOffset + (int)(p.getPortIndex()*h*1.5));

    }

    @Override
    public void setDefaultIcon() {        
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/du_b.png")));
    }

    @Override
    public void setSelectedIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/du_o.png")));
    }

    @Override
    public void setHoverIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/du_r_w.png")));
    }
    
 
}
