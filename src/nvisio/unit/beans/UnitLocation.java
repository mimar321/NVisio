/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.unit.beans;

import java.awt.Point;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author esinhep
 */
@XmlRootElement
public class UnitLocation {
    private int x;
    private int y;
    
    public UnitLocation(){
        this.x = 0;
        this.y = 0;
    }
    public UnitLocation(int x, int y){
        this.x = x;
        this.y = y;
    }
    public UnitLocation(Point p){
        this.x = (int)p.getX();
        this.y = (int)p.getY();
    }
    
    @XmlElement(name = "x")
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }
    @XmlElement(name = "y")
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
}
