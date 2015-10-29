/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.unit.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author esinhep
 */
@XmlRootElement
public class NetworkConfigBean implements Serializable{
    private static final long serialVersionUID = 1L;
    List<UnitBean> radioUnits;
    List<DigitalUnitBean> duBeans;
    
    public NetworkConfigBean(){
        radioUnits = new ArrayList<>();
    }
    
    @XmlElement(name = "radioUnit")
    public List<UnitBean> getRadioUnits(){
        return this.radioUnits;
    }
    public void setRadioUnits(List<UnitBean> ub){
        this.radioUnits = ub;
    }
    @XmlElement(name = "digitalUnit")
    public List<DigitalUnitBean> getDigitalUnits(){
        return this.duBeans;
    }
    public void setDigitalUnits(List<DigitalUnitBean> ub){
        this.duBeans = ub;
    }
    
     @XmlElement(name = "name")
    public String getName(){
        return "Sinan was here";
    }
    public void setName(){
        
    }
}
