/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.mouse;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.SwingUtilities;
import nvisio.VPanel;

/**
 *
 * @author Sinan
 */
public class VPanelMouseAdapter extends MouseAdapter {

     private final VPanel panel;

     public VPanelMouseAdapter(VPanel panel){
        this.panel = panel;
        
        MouseMotionAdapter mmAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent evt) { 
                repaintPanel(evt);
            }};
        
        panel.addMouseMotionListener(mmAdapter);
     }
     
    private void repaintPanel(MouseEvent e){
        panel.repaint();
    }
    
    /**
     * {@inheritDoc}
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("VPanelMouseAdapter.mousePressed");

        VPanel.setConnectingPort(null);
        if (VPanel.getPropertyFrame() != null){
            VPanel.getPropertyFrame().hide();
        }

        if (UnitMouseAdapter.selectedUnit != null ){
            UnitMouseAdapter.selectedUnit.setDefaultIcon();
            UnitMouseAdapter.selectedUnit = null;
        }
        // TODO: handle selection of ports
    }

    /**
     * {@inheritDoc}
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)){
            panel.getVPanelMenu().show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
