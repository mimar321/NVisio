/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.unit.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import nvisio.unit.Port;

/**
 *
 * @author Sinan
 */
@XmlRootElement(name = "Port")
public class PortBean {
    
    private String portName;
    private int portIndex;
    private String portUnitName;
    private PortBean remotePort;
    private Port.PortType portType;
    
    public PortBean(){
        this.remotePort = null;
        this.portType = Port.PortType.MASTER;
    }
    
    public PortBean(Port port){
        this.portName = port.getPortName();
        this.portIndex = port.getPortIndex();
        this.portUnitName = port.getUnit().getUnitName();
        this.portType = port.getPortType();
        if (port.isConnected()){
            PortBean remotePortB = new PortBean();
            remotePortB.portName = port.getRemotePort().getPortName();
            remotePortB.portIndex = port.getRemotePort().getPortIndex();
            remotePortB.portUnitName = port.getRemotePort().getUnit().getUnitName();
            this.remotePort = remotePortB;
        }
    }
    
    @XmlElement(name = "portName")
    public String getPortName(){
        return this.portName;
    }
    public void setPortName(String portName){
        this.portName = portName;
    }
    @XmlElement(name = "portIndex")
    public int getPortIndex(){
        return this.portIndex;
    }
    public void setPortIndex(int portIndex){
        this.portIndex = portIndex;
    }
    @XmlElement(name = "portUnitName")
    public String getPortUnitName(){
        return this.portUnitName;
    }
    public void setPortUnitName(String portUnitName){
        this.portUnitName = portUnitName;
    }
    @XmlElement(name = "remotePort")
    public PortBean getRemotePort(){
        return this.remotePort;
    }
    public void setRemotePort(PortBean port){
        this.remotePort = port;
    }
    @XmlElement(name = "portType")
    public Port.PortType getPortType(){
        return this.portType;
    }
    public void setPortType(Port.PortType portType){
        this.portType = portType;
    }
}
