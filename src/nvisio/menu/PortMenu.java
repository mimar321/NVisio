/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.menu;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import nvisio.ProperyFrame;
import nvisio.VPanel;
import nvisio.mouse.PortMouseAdapter;
import nvisio.unit.Port;
import nvisio.unit.beans.PortBean;

/**
 *
 * @author Sinan
 */
public class PortMenu extends JPopupMenu{
    Port port;
    PortMouseAdapter mouseAdapter;

    public PortMenu(Port port){
        this.port = port;

        this.addMenuItem(connectPortAction(),        "Connect port...");
        this.addMenuItem(disconnectPortAction(),     "Disconnect port...");
        this.addMenuItem(showPortPropertiesAction(), "Properties");
    }
    
    public void addMenuItem(ActionListener al, String text){
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(al);
        this.add(item);
    }
    
    private ActionListener connectPortAction(){
        final Port portF = this.port;
        return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    VPanel.setConnectingPort(portF);
                }
            };
    }
    
    private ActionListener disconnectPortAction(){
        final Container c = this.port.getUnit().getParent();
        final Port portF = this.port;
        return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (portF.getRemotePort() != null){
                        portF.getRemotePort().disconnectPort();
                        portF.disconnectPort();
                        c.repaint();
                    }
                }
            };
    }
    
    private ActionListener showPortPropertiesAction(){
        final PortBean pb = new PortBean(this.port); // TODO
        return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    VPanel.getPropertyFrame(pb).show();
                }
            };
    }
    

}
