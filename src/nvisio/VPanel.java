/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio;

import nvisio.mouse.VPanelMouseAdapter;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import nvisio.menu.VPanelMenu;
import nvisio.unit.Port;
import nvisio.unit.Unit;

/**
 *
 * @author Sinan
 */
public class VPanel extends JPanel {

    VPanelMenu menu;
    VPanelMouseAdapter mouseAdapter;
    List<Unit> units = new ArrayList<>();
    static Port connectingPort = null;
    
    static ProperyFrame pf;
    
    public static double scale = 0.5;

    public VPanel() {
        mouseAdapter = new VPanelMouseAdapter(this);
        menu = new VPanelMenu(this);
    }

    public void init() {
        // register mouse listener
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        //g.drawRect(0, 0, this.getWidth(), this.getHeight());
        super.paint(g);
        //g2.scale(VPanel.scale, VPanel.scale);
        //super.paint(g);

        if (VPanel.isPortConnectingOngoing()) {
            drawOngoingPortConnection(g);
        }
        // draw port connections and port info
        for (Unit u : getUnits()) {
            u.drawUnitInfo(g);
            for (Port port : u.getPorts()) {
                if (port.isConnected() && port.getPortType() == Port.PortType.MASTER) {
                    port.drawPortConnection(g);
                }
                port.drawPortInfo(g);
            }
        }
    }

    public List<Unit> getUnits() {
        return this.units;
    }
    
    public void clearUnits(){
        for (Unit u: this.getUnits()){
            for (Port p: u.getPorts()){
                this.remove(p);
            }
            this.remove(u);
        }
        this.getUnits().clear();
    }
    
    public Unit getUnitByName(String unitName){
        for (Unit u: this.units){
            if (u.getUnitName().equals(unitName)){
                return u;
            }
        }
        return null;
    }

    static public void setConnectingPort(Port p) {
        VPanel.connectingPort = p;
    }

    static public Port getConnectingPort() {
        return VPanel.connectingPort;
    }

    static boolean isPortConnectingOngoing() {
        return VPanel.connectingPort != null;
    }

    private void drawOngoingPortConnection(Graphics g) {
        Point mouseL = MouseInfo.getPointerInfo().getLocation();
        Point panelL = this.getLocationOnScreen();
        Point portCenterLocation = VPanel.getConnectingPort().getCenterLocation();

        g.drawLine((int) portCenterLocation.getX(),
                (int) portCenterLocation.getY(),
                (int) (mouseL.getX() - panelL.getX()),
                (int) (mouseL.getY() - panelL.getY()));
    }

    public Point getMouseLocation() {
        Point m1 = MouseInfo.getPointerInfo().getLocation();
        Point m2 = this.getLocationOnScreen();
        return new Point((int) (m1.x - m2.x), (int) (m1.y - m2.y));
    }

    public VPanelMenu getVPanelMenu() {
        return menu;
    }
    
    public static ProperyFrame getPropertyFrame(Object bean){
        if (VPanel.pf == null){
            VPanel.pf = new ProperyFrame(bean);
        } else {
            VPanel.pf.setBean(bean);
        }
        return VPanel.pf;
    }
    public static ProperyFrame getPropertyFrame(){
        return VPanel.pf;
    }

}
