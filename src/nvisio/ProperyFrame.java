/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio;

import java.awt.BorderLayout;

/**
 *
 * @author Sinan
 */
public class ProperyFrame extends javax.swing.JFrame {
    private final PropertyPanel sheet;
    /**
     * Creates new form ProperyFrame
     * @param bean
     */
    public ProperyFrame(Object bean) {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-265)/2, (screenSize.height-317)/2, 265, 317);
        
        this.sheet = new PropertyPanel(bean);
        
        this.setTitle("Properties of " + this.sheet.getBean().getClass().getName());
        this.getContentPane().add(this.sheet, BorderLayout.CENTER);
        
        this.setAlwaysOnTop(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        
    public void setBean(Object bean) {
        sheet.setBean(bean);
        this.setTitle("Properties of " + this.sheet.getBean().getClass().getName());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
