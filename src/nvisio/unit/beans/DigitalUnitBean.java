/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.unit.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import nvisio.unit.DigitalUnit;
import nvisio.unit.Unit;

/**
 *
 * @author esinhep
 */
@XmlRootElement(name = "DigitalUnit")
public class DigitalUnitBean extends UnitBean{
    private DigitalUnit.DigitalUnitType duType;
    
    public DigitalUnitBean(){
        duType = DigitalUnit.DigitalUnitType.DUS41;
    }
    public DigitalUnitBean(Unit unit){
        super(unit);
        duType = DigitalUnit.DigitalUnitType.DUS41;
    }
    
    public DigitalUnitBean(DigitalUnit unit){
        super(unit);
        this.duType = unit.getDuType();
    }
    
    @XmlElement(name = "duType")
    public DigitalUnit.DigitalUnitType getDuType(){
        return this.duType;
    }
    public void setDuType(DigitalUnit.DigitalUnitType duType){
        this.duType = duType;
    }
    
    
}
