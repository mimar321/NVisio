/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.mouse;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import nvisio.VPanel;
import nvisio.unit.Port;
import nvisio.unit.beans.PortBean;

/**
 *
 * @author Sinan
 */
public class PortMouseAdapter extends MouseAdapter {
    
    Port port;
    
    public PortMouseAdapter(Port port){
        this.port = port;
    }
    
    @Override
    public void mousePressed(MouseEvent ev) {
        if (VPanel.getConnectingPort() != null && VPanel.getConnectingPort().areConnectable(port)){
           if (VPanel.getConnectingPort().getRemotePort() != null &&
               VPanel.getConnectingPort().getRemotePort() != port) {
               VPanel.getConnectingPort().getRemotePort().disconnectPort();
           }
           if (!port.isConnected()){
               port.setPortType(Port.PortType.SLAVE);
           }
           VPanel.getConnectingPort().setRemotePort(port);
           VPanel.getConnectingPort().setPortType(Port.PortType.MASTER);
           port.setRemotePort(VPanel.getConnectingPort());
           //port.setPortType(Port.PortType.SLAVE);
           port.getUnit().updatePortLocations();
           VPanel.getConnectingPort().getUnit().updatePortLocations();
        }
        VPanel.setConnectingPort(null);
        //System.out.println("mouse pressed on port");
    }

    @Override
    public void mouseReleased(MouseEvent ev) {
        if (SwingUtilities.isRightMouseButton(ev)) {
            this.port.getPortMenu().show(ev.getComponent(), ev.getX(), ev.getY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent ev) {
        PortBean bean = new PortBean(this.port);
        VPanel.getPropertyFrame(bean).setVisible(true);
    }
      
    @Override
    public void mouseEntered(MouseEvent e) {
        this.port.setHoverIcon();
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        this.port.setDefaultIcon();
    }
    
    @Override
    public void mouseMoved(java.awt.event.MouseEvent evt){
        this.port.getUnit().getPanel().repaint();
    }
}
