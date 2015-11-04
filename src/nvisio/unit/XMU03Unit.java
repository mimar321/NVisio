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

    public enum XMUDirection {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }
    
    private XMUDirection direction;
    
    public XMU03Unit(VPanel panel, int x, int y){
        this.numberOfPorts = 10;
        this.parent = panel;
        this.parent.add(this);
        this.unitName = "XMU03";
        this.unitType = UnitType.XMU03;
        this.direction = XMUDirection.LEFT_TO_RIGHT;
        
        this.setDefaultIcon();
        Dimension size = this.getPreferredSize();
        this.setBounds(x, y, (int)(size.getWidth()), (int)(size.getHeight()));
        
        super.init();
    }
    
    @Override
    public Point getPortLocation(Port p) {
        if (p.getPortIndex() <= 7){
            return getOutputPortLocation(p);
        } else {
            return getInputPortLocation(p);
        }
    }
    private Point getOutputPortLocation(Port p){
        double portDistance = 1.1;
        double portSeparation = 5;
        int numberOfOutputPorts = this.numberOfPorts - 2;
        int portIndex = p.getPortIndex();
        int h = (int)p.getPreferredSize().getHeight();
        Port c = p.getRemotePort();
        int yOffset = (int)((this.getHeight() - (int)(numberOfOutputPorts*h*portDistance-(portDistance-1)*h)) /2);
        
        if (portIndex >= 4){
            yOffset += portSeparation*1.5;
        } else {
            yOffset -= portSeparation;
        }
        
        int x = (this.direction == XMUDirection.RIGHT_TO_LEFT) ?
                this.getX() - (int)p.getWidth() :
                this.getX() + (int)this.getWidth();

        return new Point(x, this.getY() + yOffset + (int)(portIndex*h*portDistance));
    }
    private Point getInputPortLocation(Port p) {
        Point point = getOutputPortLocation(p);
        double portDistance = 1.1;
        int x = (this.direction == XMUDirection.LEFT_TO_RIGHT) ?
            this.getX() - (int)p.getWidth() :
            this.getX() + (int)this.getWidth();
        
        if (p.getPortIndex() == 8){
            int yOffset = (int)(4*p.getHeight()*portDistance);
            point.setLocation(x, point.getY() - yOffset);
        } else {
            int yOffset = (int)(7*p.getHeight()*portDistance -2);
            point.setLocation(x, point.getY() - yOffset);
        }
        return point;
    }

    @Override
    public void setDefaultIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/xmu03_b.png")));
    }

    @Override
    public void setSelectedIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/xmu03_o.png")));
    }

    @Override
    public void setHoverIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/xmu03_r_w.png")));
    }
    
}
