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
        
        this.setDefaultIcon();
        Dimension size = this.getPreferredSize();
        this.setBounds(x, y, (int)(size.getWidth()), (int)(size.getHeight()));
        
        super.init();
    }
    
    @Override
    public Point getPortLocation(Port p) {
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
