/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.mouse;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import nvisio.VPanel;
import nvisio.unit.Unit;
import nvisio.unit.beans.UnitBean;

/**
 *
 * @author Sinan
 */
public class UnitMouseAdapter extends MouseAdapter{
    
    private final Unit unit;
    private boolean isPressed = false;
    private Point dragPoint;
    
    static protected Unit selectedUnit;
    
    public UnitMouseAdapter(Unit unit){
        this.unit = unit;
    }

    @Override
    public void mouseMoved(MouseEvent e) { 
        this.unit.getPanel().repaint();
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("UnitMouseAdapter.mouseEntered");
        
        this.unit.setHoverIcon();
    }
    
    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        //System.out.println("UnitMouseAdapter.mouseExited");
        
        if (UnitMouseAdapter.selectedUnit == this.unit){
            UnitMouseAdapter.selectedUnit.setSelectedIcon();
        }else { 
            this.unit.setDefaultIcon();
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("UnitMouseAdapter.mousePressed");
        isPressed = true;
        dragPoint = e.getPoint();
        
        this.unit.setSelectedIcon();
        if (UnitMouseAdapter.selectedUnit != null && UnitMouseAdapter.selectedUnit != this.unit){
            // previosly selected unit is not selected anymore
            UnitMouseAdapter.selectedUnit.setDefaultIcon();
        }
        
        UnitMouseAdapter.selectedUnit = this.unit;
        
        final UnitBean bean = new UnitBean(this.unit);
        VPanel.getPropertyFrame(bean).show();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        //System.out.println("UnitMouseAdapter.mouseDragged");
        if (this.isPressed){
            updatePosition(e);
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println("UnitMouseAdapter.mouseReleased");
        isPressed = false;
        
        if (SwingUtilities.isRightMouseButton(e)){
            if (this.unit instanceof Unit){
                this.unit.getMenu().show(this.unit, e.getX(), e.getY());
            }
        }
    }
    
    public void updatePosition(MouseEvent e){
        Rectangle unitArea = new Rectangle(
                (int)(unit.getX() + e.getPoint().getX() - dragPoint.getX()),
                (int)(unit.getY() + e.getPoint().getY() - dragPoint.getY()),
                this.unit.getWidth(), 
                this.unit.getHeight()
        );
        Rectangle unitAreaX = new Rectangle(
                (int)(unit.getX() + e.getPoint().getX() - dragPoint.getX()),
                (int)(unit.getY()),
                this.unit.getWidth(), 
                this.unit.getHeight()
        );
        Rectangle unitAreaY = new Rectangle(
                (int)(unit.getX()),
                (int)(unit.getY() + e.getPoint().getY() - dragPoint.getY()),
                this.unit.getWidth(), 
                this.unit.getHeight()
        );
            
        for (Unit u: this.unit.getPanel().getUnits()){
            if (this.unit != u){
                if (unitArea.intersects(u.getBounds())){
                    if (unitAreaX.intersects(u.getBounds()) == false){
                        this.unit.setBounds(unitAreaX);
                    } else {
                        this.unit.setBounds(unitAreaY);
                    }
                    //updatePosition(e); // TODO check multiple collisions
                    this.unit.updatePortLocations();
                    return;
                }
            }
        }
        
        this.unit.setBounds(unitArea);
        this.unit.updatePortLocations();
    }
}
