/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.menu;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import nvisio.VPanel;
import nvisio.unit.DigitalUnit;
import nvisio.unit.Port;
import nvisio.unit.RadioUnit;
import nvisio.unit.Unit;
import nvisio.unit.beans.DigitalUnitBean;
import nvisio.unit.beans.NetworkConfigBean;
import nvisio.unit.beans.PortBean;
import nvisio.unit.beans.UnitBean;

/**
 *
 * @author Sinan
 */
public class VPanelMenu extends JPopupMenu{
    
    VPanel panel;
    
    public VPanelMenu(VPanel panel){
        this.panel = panel;
        
        JMenuItem addDuItem = new JMenuItem("Add new DU");
        JMenuItem addRuItem = new JMenuItem("Add new RU");
        JMenuItem saveItem  = new JMenuItem("Save");
        addDuItem.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { addDU(); } });
        addRuItem.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { addRU(); } });
        saveItem.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { save(); } });
        
        this.add(addDuItem);
        this.add(addRuItem);
        this.add(saveItem);
    }
    
    public void addDU(){
        Point m = panel.getMouseLocation();
        panel.getUnits().add(new DigitalUnit(panel, (int)m.getX(), (int)m.getY()));
    }
    public void addRU(){
        Point m = panel.getMouseLocation();
        panel.getUnits().add(new RadioUnit(panel, (int)m.getX(), (int)m.getY()));
    }
    
    public void save(){
        JFileChooser fc = new JFileChooser();
        int retVal = fc.showSaveDialog(this.panel.getParent());
        
        if (retVal == JFileChooser.APPROVE_OPTION){
            File fi = fc.getSelectedFile();
            FileWriter fw;
            try {
                fw = new FileWriter(fi.getPath());
                getXmlFile(fw);
                fw.flush();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(VPanelMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void open(){
        JFileChooser fc = new JFileChooser();
        int retVal = fc.showOpenDialog(this.panel.getParent());
        
        if (retVal == JFileChooser.APPROVE_OPTION){
            File fi = fc.getSelectedFile();
            XMLToJava(fi);
        }
    }
    
    private void getXmlFile(FileWriter fw){
        NetworkConfigBean ncfgBean = new NetworkConfigBean();
        List<UnitBean> ubl = new ArrayList<>();
        List<DigitalUnitBean> dul = new ArrayList<>();
        for (Unit u: this.panel.getUnits()){
            if (u.getUnitType() == Unit.UnitType.DIGITAL_UNIT){
                dul.add(new DigitalUnitBean(u));
            } else {
                ubl.add(new UnitBean(u));
            }
        }
        
        ncfgBean.setRadioUnits(ubl);
        ncfgBean.setDigitalUnits(dul);
        
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(NetworkConfigBean.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter sw = new StringWriter();
            
            //jaxbMarshaller.marshal(ncfgBean, file);
            jaxbMarshaller.marshal(ncfgBean, fw);
            //jaxbMarshaller.marshal(ncfgBean, sw);
        } catch (JAXBException ex) {
            Logger.getLogger(VPanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void XMLToJava(File f){
        try {
            JAXBContext jc = JAXBContext.newInstance(NetworkConfigBean.class);
            Unmarshaller u = jc.createUnmarshaller();
            NetworkConfigBean ncb = (NetworkConfigBean)u.unmarshal(f);
            for(DigitalUnitBean dub: ncb.getDigitalUnits()){
                DigitalUnit du = new DigitalUnit(panel, dub);
                panel.getUnits().add(du);
            }
            for(UnitBean ub: ncb.getRadioUnits()){
                RadioUnit ru = new RadioUnit(panel, ub);
                panel.getUnits().add(ru);
            }
            // unit update is done; now update port connections
            int duIndex = 0;
            for(DigitalUnitBean dub: ncb.getDigitalUnits()){
                DigitalUnit du = (DigitalUnit)panel.getUnits().get(duIndex++);
                int portIndex = 0;
                for (PortBean pb: dub.getPorts()){
                    Port p = du.getPorts().get(portIndex++);
                    p.updatePort(pb);
                }
            }
            int ruIndex = duIndex; // continue traversing RUs
            for (UnitBean ub: ncb.getRadioUnits()){
                RadioUnit ru = (RadioUnit)panel.getUnits().get(ruIndex++);
                int portIndex = 0;
                for (PortBean pb: ub.getPorts()){
                    Port p = ru.getPorts().get(portIndex++);
                    p.updatePort(pb);
                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(VPanelMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
