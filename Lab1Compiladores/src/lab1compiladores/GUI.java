/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1compiladores;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juan-
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    private ArrayList<State> TranD;
    private ArrayList<Character> symbols;
    private AFD afd;

    public GUI() {
        initComponents();
        setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    class MyCanvas extends Canvas {

        private Node root;

        public MyCanvas(Node root) {
            this.root = root;
            setLocationRelativeTo(null);
            setResizable(false);
            setBackground(Color.WHITE);
            setSize(treePanel.getWidth(), treePanel.getHeight());
        }

        public void paint(Graphics g) {
            g.setColor(Color.red);
            g.fillRect(10, 10, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString("PrimeraPos", 40, 25);

            g.setColor(Color.BLUE);
            g.fillRect(10, 40, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString("UltimaPos", 40, 55);
            paintTree(g, root, 0, treePanel.getWidth() / 2 + 30, 0);

        }

        private void paintTree(Graphics g, Node root, int level, int x, int y) {
            if (root == null) {
                return;
            }

            g.setColor(Color.black);
            if (root.getSymbol().equals(".")) {
                g.fillOval(x + 13, y + 10, 6, 6);
            } else {
                Font f = new Font("Helvetica", Font.BOLD, 12);
                g.setFont(f);
                g.drawString(root.getSymbol(), x + 15, y + 15);
            }
            Font f = new Font("Helvetica", Font.BOLD, 10);
            g.setFont(f);
            g.setColor(Color.RED);
            g.drawString(nodeSymbolsToArray(root.getFirstPos()).toString(), x - (root.getFirstPos().size() * 10) + 10, y + 15);
            g.setColor(Color.BLUE);
            g.drawString(nodeSymbolsToArray(root.getLastPos()).toString(), x + 20, y + 15);
            g.setColor(Color.black);
            root.setX(x);
            root.setY(y);
            if (root.getLeftNode() != null) {
                if (root.getRightNode() != null) {
                    g.drawLine(x + 15, y + 15, x - 15, y + 40);
                    g.drawLine(x + 15, y + 15, x + 75, y + 40);
                    paintTree(g, root.getLeftNode(), level + 1, x - 40, y + 40);
                    paintTree(g, root.getRightNode(), level + 1, x + 60, y + 40);

                } else {
                    g.drawLine(x + 15, y + 15, x + 15, y + 40);
                    paintTree(g, root.getLeftNode(), level + 1, x, y + 40);
                    paintTree(g, root.getRightNode(), level + 1, x + 60, y + 40);
                }
            }

        }

        private ArrayList nodeSymbolsToArray(ArrayList<Node> nodes) {
            ArrayList list = new ArrayList();
            for (Node n : nodes) {
                list.add(n.getNodeID());
            }
            return list;
        }

    }

    private void drawTree(Node root) {
        treePanel.removeAll();
        MyCanvas c = new MyCanvas(root);
        treePanel.add(c);
    }

    private void generateTree(String regex) {
        SyntaxTree st = new SyntaxTree();
        Node root = st.constructTree(regex);
        st.traverseTree(root);
        drawTree(root);
        fillIndexTable(st.getLeafNodes());
        afd = new AFD();
        symbols = st.getInputSymbols();
        afd.constructAFD(root, symbols);
        TranD = afd.getTranD();
        printTranD(symbols);
    }

    private boolean isWordInLanguage(String word) {
        return afd.recognizeString(word, TranD, symbols);
    }

    private void printTranD(ArrayList<Character> symbols) {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        Character header[] = symbols.toArray(new Character[0]);
        dtm.setColumnIdentifiers(header);
        TranDTable.setModel(dtm);
        dtm.setRowCount(TranD.size());
        for (int i = 0; i < TranD.size(); i++) {
            for (int j = 0; j < header.length; j++) {
                if (TranD.get(i).getTransitions().get(j) != null) {
                    dtm.setValueAt(TranD.get(i).getTransitions().get(j).getStateID(), i, j);
                }
            }
        }

    }

    private void fillIndexTable(ArrayList<Node> leafNodes) {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String header[] = new String[]{"Index", "SigPos", "PrimPos", "UltPos"};
        dtm.setColumnIdentifiers(header);
        indexTable.setModel(dtm);

        for (Node n : leafNodes) {
            String followPos = nodeSymbolsToArray(n.getFollowPos()).toString();
            String firstPos = nodeSymbolsToArray(n.getFirstPos()).toString();
            String lastPos = nodeSymbolsToArray(n.getLastPos()).toString();

            dtm.addRow(new Object[]{n.getNodeID(), followPos, firstPos, lastPos});
        }
    }

    private ArrayList nodeSymbolsToArray(ArrayList<Node> nodes) {
        ArrayList list = new ArrayList();
        for (Node n : nodes) {
            list.add(n.getNodeID());
        }
        return list;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        indexTable = new javax.swing.JTable();
        treePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        regexTextField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TranDTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        wordTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        checkWordButton = new javax.swing.JButton();

        jScrollPane3.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 600));

        indexTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane1.setViewportView(indexTable);

        javax.swing.GroupLayout treePanelLayout = new javax.swing.GroupLayout(treePanel);
        treePanel.setLayout(treePanelLayout);
        treePanelLayout.setHorizontalGroup(
            treePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        treePanelLayout.setVerticalGroup(
            treePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 574, Short.MAX_VALUE)
        );

        jLabel1.setText("Expresión Regular");

        TranDTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(TranDTable);

        jLabel2.setText("Matriz de Transición");

        jLabel3.setText("Tabla Arbol Sintactico");

        jButton1.setText("Generar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Cadena");

        checkWordButton.setText("Reconocer");
        checkWordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkWordButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(regexTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(wordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(checkWordButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(treePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(regexTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkWordButton)
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(treePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String regex = regexTextField.getText();
        generateTree(regex);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void checkWordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkWordButtonActionPerformed
        // TODO add your handling code here:
        String word = wordTextField.getText();
        System.out.println(isWordInLanguage(word));
    }//GEN-LAST:event_checkWordButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TranDTable;
    private javax.swing.JButton checkWordButton;
    private javax.swing.JTable indexTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextField regexTextField;
    private javax.swing.JPanel treePanel;
    private javax.swing.JTextField wordTextField;
    // End of variables declaration//GEN-END:variables
}
