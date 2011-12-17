/*
 * DebuggerView.java
 *
 * Created on 15 déc. 2011, 13:05:24
 */
package com.alwex.jboy;

import com.alwex.jboy.hardware.CPU;
import com.alwex.jboy.hardware.MEM;
import com.alwex.jboy.utils.ByteUtil;
import com.alwex.jboy.utils.Debugger;
import com.alwex.jboy.utils.UnsignedByte;
import java.awt.event.KeyEvent;
import javax.swing.DefaultListModel;

/**
 *
 * @author alex
 */
public class DebuggerView extends javax.swing.JFrame
{

    private CPU theCpu;
    private Debugger theDebugger;

    /** Creates new form DebuggerView */
    public DebuggerView()
    {
        theCpu = new CPU();
        MEM theMemory = new MEM();

        theCpu.init();
        theCpu.setRom(theMemory.read("src/main/resources/test2.gb"));

        initComponents();

        this.loadValues();
    }

    public DebuggerView(CPU aCpu)
    {
        theCpu = aCpu;
        initComponents();
    }

    public void loadValues()
    {
        theDebugger = new Debugger(theCpu.memory);

        DefaultListModel memoryModel = new DefaultListModel();
        memoryList.setModel(memoryModel);

        DefaultListModel opCodeModel = new DefaultListModel();
        opCodeList.setModel(opCodeModel);

        int i = 1;
        short adress = 0;
        String aLine = "";
        for (byte b : theCpu.memory)
        {
            // afficahge m�moire
            aLine += " " + new UnsignedByte(b).toString();
            if (i % 16 == 0)
            {
                aLine = ByteUtil.toHex(adress) + ":" + aLine;
                memoryModel.addElement(aLine);
                adress += 16;
                aLine = "";
            }

            // affichage instructions
            opCodeModel.addElement(theDebugger.parse(i - 1));
            i++;
        }

        this.updateValues();
    }

    /**
     * met à jour les valeurs du débugger
     */
    public void updateValues()
    {
        PCTextfield.setText(ByteUtil.toHex(theCpu.PC));
        ATextfield.setText(ByteUtil.toHex(theCpu.A));
        BTextfield.setText(ByteUtil.toHex(theCpu.B));
        CTextfield.setText(ByteUtil.toHex(theCpu.C));
        DTextfield.setText(ByteUtil.toHex(theCpu.D));
        ETextfield.setText(ByteUtil.toHex(theCpu.E));
        FTextfield.setText(ByteUtil.toHex(theCpu.F));
        FZTextfield.setText(String.valueOf(theCpu.getF(theCpu.F_Z)));
        FNTextfield.setText(String.valueOf(theCpu.getF(theCpu.F_N)));
        FHTextfield.setText(String.valueOf(theCpu.getF(theCpu.F_H)));
        FCTextfield.setText(String.valueOf(theCpu.getF(theCpu.F_C)));

        HLTextfield.setText(ByteUtil.toHex((short) ByteUtil.combine(theCpu.H, theCpu.L)));
        SPTextfield.setText(ByteUtil.toHex((short) theCpu.SP));
        opCodeList.setSelectedIndex(theCpu.PC);
        opCodeList.ensureIndexIsVisible(theCpu.PC);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        opCodesScrollPane = new javax.swing.JScrollPane();
        opCodeList = new javax.swing.JList();
        memoryScrollPane = new javax.swing.JScrollPane();
        memoryList = new javax.swing.JList();
        runButton = new javax.swing.JButton();
        stepButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JToggleButton();
        resetButton = new javax.swing.JButton();
        RegistersPanel = new java.awt.Panel();
        ALabel = new javax.swing.JLabel();
        ATextfield = new javax.swing.JTextField();
        FLabel = new javax.swing.JLabel();
        BLabel = new javax.swing.JLabel();
        PCTextfield = new javax.swing.JTextField();
        BTextfield = new javax.swing.JTextField();
        CLabel = new javax.swing.JLabel();
        DLabel = new javax.swing.JLabel();
        DTextfield = new javax.swing.JTextField();
        ELabel = new javax.swing.JLabel();
        PCLabel = new javax.swing.JLabel();
        FTextfield = new javax.swing.JTextField();
        CTextfield = new javax.swing.JTextField();
        FZTextfield = new javax.swing.JTextField();
        ETextfield = new javax.swing.JTextField();
        FNTextfield = new javax.swing.JTextField();
        FCTextfield = new javax.swing.JTextField();
        FHTextfield = new javax.swing.JTextField();
        FZLabel = new javax.swing.JLabel();
        FNLabel = new javax.swing.JLabel();
        FHLabel = new javax.swing.JLabel();
        FCLabel = new javax.swing.JLabel();
        HLLabel = new javax.swing.JLabel();
        HLTextfield = new javax.swing.JTextField();
        SPTextfield = new javax.swing.JTextField();
        SPLabel = new javax.swing.JLabel();
        stepTextfield = new javax.swing.JTextField();
        runTextfield = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N

        opCodesScrollPane.setName("opCodesScrollPane"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(DebuggerView.class);
        opCodeList.setFont(resourceMap.getFont("opcCodeList.font")); // NOI18N
        opCodeList.setName("opcCodeList"); // NOI18N
        opCodesScrollPane.setViewportView(opCodeList);

        memoryScrollPane.setName("memoryScrollPane"); // NOI18N

        memoryList.setFont(resourceMap.getFont("memoryList.font")); // NOI18N
        memoryList.setName("memoryList"); // NOI18N
        memoryScrollPane.setViewportView(memoryList);

        runButton.setText(resourceMap.getString("runButton.text")); // NOI18N
        runButton.setName("runButton"); // NOI18N
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        stepButton.setText(resourceMap.getString("stepButton.text")); // NOI18N
        stepButton.setName("stepButton"); // NOI18N
        stepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepButtonActionPerformed(evt);
            }
        });

        pauseButton.setText(resourceMap.getString("pauseButton.text")); // NOI18N
        pauseButton.setName("pauseButton"); // NOI18N

        resetButton.setText(resourceMap.getString("resetButton.text")); // NOI18N
        resetButton.setName("resetButton"); // NOI18N
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        RegistersPanel.setName("RegistersPanel"); // NOI18N
        RegistersPanel.setLayout(new java.awt.GridBagLayout());

        ALabel.setText(resourceMap.getString("ALabel.text")); // NOI18N
        ALabel.setName("ALabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        RegistersPanel.add(ALabel, gridBagConstraints);

        ATextfield.setText(resourceMap.getString("ATextfield.text")); // NOI18N
        ATextfield.setName("ATextfield"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 35;
        RegistersPanel.add(ATextfield, gridBagConstraints);

        FLabel.setText(resourceMap.getString("FLabel.text")); // NOI18N
        FLabel.setName("FLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        RegistersPanel.add(FLabel, gridBagConstraints);

        BLabel.setText(resourceMap.getString("BLabel.text")); // NOI18N
        BLabel.setName("BLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        RegistersPanel.add(BLabel, gridBagConstraints);

        PCTextfield.setText(resourceMap.getString("PCTextfield.text")); // NOI18N
        PCTextfield.setName("PCTextfield"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 35;
        RegistersPanel.add(PCTextfield, gridBagConstraints);

        BTextfield.setText(resourceMap.getString("BTextfield.text")); // NOI18N
        BTextfield.setName("BTextfield"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 35;
        RegistersPanel.add(BTextfield, gridBagConstraints);

        CLabel.setText(resourceMap.getString("CLabel.text")); // NOI18N
        CLabel.setName("CLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        RegistersPanel.add(CLabel, gridBagConstraints);

        DLabel.setText(resourceMap.getString("DLabel.text")); // NOI18N
        DLabel.setName("DLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        RegistersPanel.add(DLabel, gridBagConstraints);

        DTextfield.setText(resourceMap.getString("DTextfield.text")); // NOI18N
        DTextfield.setName("DTextfield"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 35;
        RegistersPanel.add(DTextfield, gridBagConstraints);

        ELabel.setText(resourceMap.getString("ELabel.text")); // NOI18N
        ELabel.setName("ELabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        RegistersPanel.add(ELabel, gridBagConstraints);

        PCLabel.setText(resourceMap.getString("PCLabel.text")); // NOI18N
        PCLabel.setName("PCLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        RegistersPanel.add(PCLabel, gridBagConstraints);

        FTextfield.setText(resourceMap.getString("FTextfield.text")); // NOI18N
        FTextfield.setName("FTextfield"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 35;
        RegistersPanel.add(FTextfield, gridBagConstraints);

        CTextfield.setText(resourceMap.getString("CTextfield.text")); // NOI18N
        CTextfield.setName("CTextfield"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 35;
        RegistersPanel.add(CTextfield, gridBagConstraints);

        FZTextfield.setText(resourceMap.getString("FZTextfield.text")); // NOI18N
        FZTextfield.setName("FZTextfield"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        RegistersPanel.add(FZTextfield, gridBagConstraints);

        ETextfield.setText(resourceMap.getString("ETextfield.text")); // NOI18N
        ETextfield.setName("ETextfield"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 35;
        RegistersPanel.add(ETextfield, gridBagConstraints);

        FNTextfield.setText(resourceMap.getString("FNTextfield.text")); // NOI18N
        FNTextfield.setName("FNTextfield"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        RegistersPanel.add(FNTextfield, gridBagConstraints);

        FCTextfield.setText(resourceMap.getString("FCTextfield.text")); // NOI18N
        FCTextfield.setName("FCTextfield"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        RegistersPanel.add(FCTextfield, gridBagConstraints);

        FHTextfield.setText(resourceMap.getString("FHTextfield.text")); // NOI18N
        FHTextfield.setName("FHTextfield"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        RegistersPanel.add(FHTextfield, gridBagConstraints);

        FZLabel.setText(resourceMap.getString("FZLabel.text")); // NOI18N
        FZLabel.setName("FZLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        RegistersPanel.add(FZLabel, gridBagConstraints);

        FNLabel.setText(resourceMap.getString("FNLabel.text")); // NOI18N
        FNLabel.setName("FNLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        RegistersPanel.add(FNLabel, gridBagConstraints);

        FHLabel.setText(resourceMap.getString("FHLabel.text")); // NOI18N
        FHLabel.setName("FHLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        RegistersPanel.add(FHLabel, gridBagConstraints);

        FCLabel.setText(resourceMap.getString("FCLabel.text")); // NOI18N
        FCLabel.setName("FCLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        RegistersPanel.add(FCLabel, gridBagConstraints);

        HLLabel.setText(resourceMap.getString("HLLabel.text")); // NOI18N
        HLLabel.setName("HLLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        RegistersPanel.add(HLLabel, gridBagConstraints);

        HLTextfield.setText(resourceMap.getString("HLTextfield.text")); // NOI18N
        HLTextfield.setName("HLTextfield"); // NOI18N
        HLTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HLTextfieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 35;
        RegistersPanel.add(HLTextfield, gridBagConstraints);

        SPTextfield.setText(resourceMap.getString("SPTextfield.text")); // NOI18N
        SPTextfield.setName("SPTextfield"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 35;
        RegistersPanel.add(SPTextfield, gridBagConstraints);

        SPLabel.setText(resourceMap.getString("SPLabel.text")); // NOI18N
        SPLabel.setName("SPLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        RegistersPanel.add(SPLabel, gridBagConstraints);

        stepTextfield.setText(resourceMap.getString("stepTextfield.text")); // NOI18N
        stepTextfield.setName("stepTextfield"); // NOI18N

        runTextfield.setText(resourceMap.getString("runTextfield.text")); // NOI18N
        runTextfield.setName("runTextfield"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(RegistersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(opCodesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(memoryScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(runButton, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(stepTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(stepButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(pauseButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(resetButton, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(runTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(memoryScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(runButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(runTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(stepButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stepTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pauseButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resetButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(opCodesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RegistersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * on avance d'un pas dans l'execution de la rom
     * 
     * @param evt
     */
    private void stepButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stepButtonActionPerformed
    {//GEN-HEADEREND:event_stepButtonActionPerformed
        int steps = 1;
        if (!"".equals(this.stepTextfield.getText()))
        {
            steps = Integer.parseInt(this.stepTextfield.getText());
        }

        for (int i = 0; i < steps; i++)
        {
            theCpu.processOpCode();
            updateValues();
        }
    }//GEN-LAST:event_stepButtonActionPerformed

    /**
     * 
     * @param evt 
     */
    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_resetButtonActionPerformed
    {//GEN-HEADEREND:event_resetButtonActionPerformed
        theCpu.init();
        updateValues();
    }//GEN-LAST:event_resetButtonActionPerformed

private void HLTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HLTextfieldActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_HLTextfieldActionPerformed

private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
// TODO add your handling code here:
    int runTo = 0;
    if (!"".equals(this.runTextfield.getText()))
    {
        runTo = Integer.parseInt(this.runTextfield.getText(), 16);
    }

    int beforePC = 0;
    if (runTo > 0)
    {
        while (theCpu.PC != runTo)
        {
            beforePC = theCpu.PC;
            theCpu.processOpCode();
            updateValues();
            if (beforePC == theCpu.PC)
            {
                break;
            }
        }
    }

}//GEN-LAST:event_runButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(DebuggerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(DebuggerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(DebuggerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(DebuggerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {

            public void run()
            {
                new DebuggerView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ALabel;
    private javax.swing.JTextField ATextfield;
    private javax.swing.JLabel BLabel;
    private javax.swing.JTextField BTextfield;
    private javax.swing.JLabel CLabel;
    private javax.swing.JTextField CTextfield;
    private javax.swing.JLabel DLabel;
    private javax.swing.JTextField DTextfield;
    private javax.swing.JLabel ELabel;
    private javax.swing.JTextField ETextfield;
    private javax.swing.JLabel FCLabel;
    private javax.swing.JTextField FCTextfield;
    private javax.swing.JLabel FHLabel;
    private javax.swing.JTextField FHTextfield;
    private javax.swing.JLabel FLabel;
    private javax.swing.JLabel FNLabel;
    private javax.swing.JTextField FNTextfield;
    private javax.swing.JTextField FTextfield;
    private javax.swing.JLabel FZLabel;
    private javax.swing.JTextField FZTextfield;
    private javax.swing.JLabel HLLabel;
    private javax.swing.JTextField HLTextfield;
    private javax.swing.JLabel PCLabel;
    private javax.swing.JTextField PCTextfield;
    private java.awt.Panel RegistersPanel;
    private javax.swing.JLabel SPLabel;
    private javax.swing.JTextField SPTextfield;
    private javax.swing.JList memoryList;
    private javax.swing.JScrollPane memoryScrollPane;
    private javax.swing.JList opCodeList;
    private javax.swing.JScrollPane opCodesScrollPane;
    private javax.swing.JToggleButton pauseButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton runButton;
    private javax.swing.JTextField runTextfield;
    private javax.swing.JButton stepButton;
    private javax.swing.JTextField stepTextfield;
    // End of variables declaration//GEN-END:variables
}
