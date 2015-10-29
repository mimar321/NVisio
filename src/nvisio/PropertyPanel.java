/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nvisio;

import com.l2fprod.common.swing.renderer.*;
import com.l2fprod.common.propertysheet.*;
import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheet;
import com.l2fprod.common.propertysheet.PropertySheetPanel;

import java.beans.*;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 *
 * @author Sinan
 */
public class PropertyPanel extends PropertySheetPanel {
    
    /** Creates a new instance of PropertyPanel
     * @param bean */
    public PropertyPanel(Object bean) {
        this.bean = bean;
        
        BeanInfo beanInfo = null;

        try {
            beanInfo = Introspector.getBeanInfo(bean.getClass());
        } catch (Exception exp) {
            System.out.println("No bean found");
        }
                
        this.getTable().setEditorFactory(new PropertyEditorRegistryEx());
        PropertyEditorRegistry editor = (PropertyEditorRegistry)this.getTable().getEditorFactory();
        PropertyRendererRegistry renderer = (PropertyRendererRegistry)this.getTable().getRendererFactory();
    
        DefaultCellRenderer r = new DefaultCellRenderer();
        r.setShowOddAndEvenRows(false);    
        renderer.registerRenderer(Enum.class, r);

        this.setMode(PropertySheet.VIEW_AS_FLAT_LIST);
        this.setToolBarVisible(false);
        this.setDescriptionVisible(false);    
        this.setToolBarVisible(true);
        if (beanInfo != null)
            this.setBeanInfo(beanInfo);        

        Property[] properties = this.getProperties();
        for (int i = 0, c = properties.length; i < c; i++) {
            properties[i].readFromObject(bean);            
        }
        
        PropertyChangeListener listener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Property prop = (Property)evt.getSource();
                prop.writeToObject(PropertyPanel.this.bean);
            }
        };
        
        this.addPropertySheetChangeListener(listener);
    }

    PropertyPanel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    private static class PropertyEditorRegistryEx extends PropertyEditorRegistry {
        // We will try to get the "nearest" super class.        
        public synchronized PropertyEditor getEditor(Class type) {
            PropertyEditor editor = super.getEditor(type);

            Class c = type;
        
            while(editor == null) {
                c = c.getSuperclass();

                if(c == null)
                    return editor;

                editor = super.getEditor(c);
            }

            return editor;
        }
    }  
    
    public void setBean(Object bean) {
        this.bean = bean;
        
        BeanInfo beanInfo = null;

        try {
            beanInfo = Introspector.getBeanInfo(bean.getClass());
        } catch (IntrospectionException exp) {
        }
        
        this.setBeanInfo(beanInfo);        

        Property[] properties = this.getProperties();
        for (int i = 0, c = properties.length; i < c; i++) {
            properties[i].readFromObject(bean);            
        }
        
        //sheet.revalidate();
    }
    
    public Object getBean() {
        return bean;
    }
    
    private Object bean;   
}
