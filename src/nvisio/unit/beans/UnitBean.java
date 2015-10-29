/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.unit.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import nvisio.unit.Port;
import nvisio.unit.Unit;

/**
 *
 * @author Sinan
 */
@XmlRootElement(name = "Unit")
public class UnitBean {
    private Unit unit;
    
    private String unitName;
    private Unit.UnitType unitType;
    private int numberOfPorts;
    private UnitLocation location;
    private List<PortBean> ports;
    
    public UnitBean(){
        ports = new ArrayList<>();
    }
    
    public UnitBean(Unit unit){
        this.unit = unit;
        this.unitName = unit.getUnitName();
        this.unitType = unit.getUnitType();
        this.numberOfPorts = unit.getPorts().size();
        this.location = new UnitLocation(unit.getLocation());
        ports = new ArrayList<>();
        for (Port p: this.unit.getPorts()){
            PortBean pb = new PortBean(p);
            this.ports.add(pb);
        }
    }
    
    @XmlElement(name = "unitName")
    public String getUnitName(){
        return this.unitName;
    }
    public void setUnitName(String unitName){
        this.unitName = unitName;
        if (this.unit != null){
            this.unit.setName(unitName);
        }
    }
    @XmlElement(name = "unitType")
    public Unit.UnitType getUnitType(){
        return this.unitType;
    }
    public void setUnitType(Unit.UnitType ut){
        this.unitType = ut;
    }
    @XmlElement(name = "numberOfPorts")
    public int getNumberOfPorts(){
        return this.numberOfPorts;
    }
    public void setNumberOfPorts(int portNo){
        this.numberOfPorts = portNo;
    }
    @XmlElement(name = "port")
    public List<PortBean> getPorts(){
        return this.ports;
    }
    public void setPorts(List<PortBean> ports){
        this.ports = ports;
    }
    @XmlElement(name = "location")
    public UnitLocation getLocation(){
        return this.location;
    }
    public void setLocation(UnitLocation ul){
        this.location = ul;
    }
}
