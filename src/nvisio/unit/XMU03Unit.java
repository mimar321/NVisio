/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.unit;

import java.awt.Dimension;
import java.awt.Point;
import nvisio.VPanel;

/**
 *
 * @author esinhep
 */
public class XMU03Unit extends Unit{

    public XMU03Unit(VPanel panel, int x, int y){
        this.numberOfPorts = 10;
        this.parent = panel;
        this.parent.add(this);
        this.unitName = "XMU03";
        this.unitType = UnitType.XMU03;
        
        this.setDefaultIcon();
        Dimension size = this.getPreferredSize();
        this.setBounds(x, y, (int)(size.getWidth()), (int)(size.getHeight()));
        
        super.init();
    }
    
    @Override
    public Point getPortLocation(Port p) {
        double portDistance = 1.1;
        double portSeparation = 5;
        int h = (int)p.getPreferredSize().getHeight();
        Port c = p.getRemotePort();
        int yOffset = (int)((this.getHeight() - (int)(this.numberOfPorts*h*portDistance-(portDistance-1)*h)) /2);
        
        if (p.getPortIndex() > 4){
            yOffset += portSeparation*1.5;
        } else {
            yOffset -= portSeparation;
        }
        
        if (c != null){
            if (c.getX() < this.getX() + this.getWidth()/2){
                return new Point(this.getX() - (int)p.getWidth(), 
                         this.getY() + yOffset + (int)(p.getPortIndex()*h*portDistance));
            }
        }
        
        return new Point(this.getX() + (int)this.getWidth(), 
                         this.getY() + yOffset + (int)(p.getPortIndex()*h*portDistance));
    }

    @Override
    public void setDefaultIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/xmu03_b.png")));
    }

    @Override
    public void setSelectedIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/xmu03_b.png")));
    }

    @Override
    public void setHoverIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/xmu03_b.png")));
    }
    
}
