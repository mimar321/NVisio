/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.unit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import nvisio.VPanel;
import nvisio.menu.UnitMenu;
import nvisio.mouse.UnitMouseAdapter;

/**
 *
 * @author esinhep
 */

abstract public class Unit extends JLabel{
    public enum UnitType {
        DIGITAL_UNIT,
        RADIO_UNIT
    }
    
    List<Port> ports;
    protected int numberOfPorts;
    VPanel parent;
    UnitType unitType = UnitType.DIGITAL_UNIT;
    UnitMenu menu;
    protected String unitName;
    protected UnitMouseAdapter unitMouseAdapter;
    
    public void init(){
        this.ports = new ArrayList<>();

        for(int i = 0; i < numberOfPorts; i++){
            Port p = new Port(parent, this, i);
            this.ports.add(p);
        }
        
        this.unitMouseAdapter = new UnitMouseAdapter(this);
        addMouseListener(unitMouseAdapter);
        addMouseMotionListener(unitMouseAdapter);
        
        this.menu = new UnitMenu(this);
    }
    
    public void drawUnitInfo(Graphics g){
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        g.setColor(Color.decode("0xC0C0C0"));
        g.drawString(unitName, this.getX(), this.getY() - 2);
        
        g.setColor(Color.decode("0xFF6E00"));
        if (this.getUnitType() == UnitType.DIGITAL_UNIT){
            DigitalUnit du = (DigitalUnit)this;
            g.drawString(du.getDuTypeString(), this.getX() + this.getWidth()/3, this.getY() + this.getHeight()/2);
        }
    }

    public UnitMenu getMenu(){
        return menu;
    }
    
    public VPanel getPanel(){
        return this.parent;
    }

    public UnitType getUnitType(){
        return this.unitType;
    }
    
    public String getUnitName(){
        return this.unitName;
    }
    public void setUnitName(String unitName){
        this.unitName = unitName;
    }
    
    public Rectangle getPortBounds(Port p){
        return new Rectangle(getPortLocation(p), p.getPreferredSize());
    }
    
    public List<Port> getPorts(){
        return this.ports;
    }
    
    public void updatePortLocations(){
        for (Port port : ports) {
            port.setLocation(this.getPortLocation(port));
        }
        this.parent.repaint();
    }
    
    abstract public Point getPortLocation(Port p);
    abstract public void setDefaultIcon();
    abstract public void setSelectedIcon();
    abstract public void setHoverIcon();
}
