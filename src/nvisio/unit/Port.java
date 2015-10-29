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
import javax.swing.JLabel;
import nvisio.VPanel;
import nvisio.menu.PortMenu;
import nvisio.mouse.PortMouseAdapter;
import nvisio.unit.beans.PortBean;

/**
 *
 * @author Sinan
 */
public class Port extends JLabel{

    public enum Position {
        LEFT,
        RIGHT
    }
    public enum PortType {
        MASTER,
        SLAVE
    }
    
    private final int portIndex;
    VPanel parent;
    Unit unit;
    PortMenu menu;
    Point position = new Point();
    Port remotePort = null;
    PortMouseAdapter mouseAdapter;
    PortType type;
    
    public Port(VPanel parent, Unit unit, int portIndex){
        this.portIndex = portIndex;
        this.parent = parent;
        this.unit = unit;
        this.type = PortType.MASTER;
        
        this.setDefaultIcon();
        parent.add(this);
        this.setBounds(unit.getPortBounds(this));
        this.position = this.getLocationOnScreen();
        
        this.menu = new PortMenu(this);

        this.mouseAdapter = new PortMouseAdapter(this);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }
    
    public void updatePort(PortBean pb){
        this.type = pb.getPortType();
        if (pb.getRemotePort() != null && pb.getRemotePort().getPortName() != null){
            String remotePortUnitName = pb.getRemotePort().getPortUnitName();
            int remotePortIndex = pb.getRemotePort().getPortIndex();
            this.remotePort = this.parent.getUnitByName(remotePortUnitName).getPorts().get(remotePortIndex);
            this.unit.updatePortLocations();
        }
    }
    
    public int getPortIndex(){
        return portIndex;
    }
    
    public PortMenu getPortMenu(){
        return this.menu;
    }

    public Point getCenterLocation(){
        Point p = this.getLocation();
        
        return new Point(
                (int)p.getX()+this.getWidth()/2,
                (int)p.getY()+this.getHeight()/2
        );
    }
    
    public void setRemotePort(Port p){
        this.remotePort = p;
    }
    
    public Port getRemotePort(){
        return this.remotePort;
    }
    
    public boolean areConnectable(Port remotePort){
        if (this.getUnit() == remotePort.getUnit()){
            //System.out.println("Not connectable; same unit");
            return false;
        }
        if (remotePort.getUnit().getUnitType() == Unit.UnitType.DIGITAL_UNIT &&
            this.getUnit().getUnitType() == Unit.UnitType.RADIO_UNIT){
            //System.out.println("Not connectable; DU cant be connected to DU");
            return false;
        }
        return true;
    }
    
    public void disconnectPort(){
        this.remotePort = null;
    }
    
    public boolean isConnected(){
        return this.remotePort != null;
    }
    
    public PortType getPortType(){
        return this.type;
    }
    public void setPortType(PortType type){
        this.type = type;
    }
    
    public Unit getUnit(){
        return unit;
    }
    
    public Position getPosition(){
        if (this.getX() < this.unit.getX()){
            return Position.LEFT;
        } else {
            return Position.RIGHT;
        }
    }
    
    public String getPortName(){
        switch(this.getPortIndex()){
            case 0: return "A";
            case 1: return "B";
            case 2: return "C";
            case 3: return "D";
            case 4: return "E";
            case 5: return "F";
            default: return "X";
        }
    }
    
    public void drawPortInfo(Graphics g){
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        g.setColor(Color.decode("0xFF6E00"));
        if (this.getUnit().getUnitType() == Unit.UnitType.DIGITAL_UNIT){
            if (this.getPosition() == Port.Position.RIGHT){
                g.drawString(this.getPortName(), this.getX() - 15, this.getY() + 11);
            } else {
                g.drawString(this.getPortName(), this.getUnit().getX() + 5, this.getY() + 11);
            }
        }
    }
    
    public void drawPortConnection(Graphics g) {
        Point connectedPortL = this.getRemotePort().getCenterLocation();
        Point connectingPortL = this.getCenterLocation();
        g.setColor(Color.decode("0xFF6E00"));
        drawArrowLine(g, 
                (int) connectingPortL.getX(), 
                (int) connectingPortL.getY(), 
                (int) connectedPortL.getX(), 
                (int) connectedPortL.getY(), 
                15, 5);
    }
    
    /**
      * Draw an arrow line between two point 
      * @param g the graphic component
      * @param x1 x-position of first point
      * @param y1 y-position of first point
      * @param x2 x-position of second point
      * @param y2 y-position of second point
      * @param d  the width of the arrow
      * @param h  the height of the arrow
      */
     private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h){
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy/D, cos = dx/D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
     }
     

    public void setDefaultIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/port_b_2.png")));
    }
    public void setSelectedIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/port_red.png")));
    }
    public void setHoverIcon() {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/nvisio/resources/port_o.png")));
    }
}

