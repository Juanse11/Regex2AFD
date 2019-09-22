
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import lab1compiladores.Node;

class TreeCanvas extends Canvas {

        private Node root;
        private JPanel treePanel;
        public TreeCanvas(Node root, JPanel treePanel) {
            this.root = root;
            this.treePanel = treePanel;
            setBackground(Color.WHITE);
            setSize(treePanel.getWidth(), treePanel.getHeight());
        }

        public void paint(Graphics g) {

            paintTree(g, root, 0, treePanel.getWidth() / 2 + 30, 0);

        }

        private void paintTree(Graphics g, Node root, int level, int x, int y) {
            if (root == null) {
                return;
            }
            paintTree(g, root.getLeftNode(), level + 1, x - 40, y + 40);
            paintTree(g, root.getRightNode(), level + 1, x + 40, y + 40);

            g.setColor(Color.black);
            if (root.getSymbol().equals(".")) {
                g.fillOval(x + 13, y + 10, 6, 6);
            } else {
                Font f = new Font("Helvetica", Font.BOLD, 12);
                g.setFont(f);
                g.drawString(root.getSymbol(), x + 15, y + 15);
            }

            g.drawString(nodeSymbolsToArray(root.getFirstPos()).toString(), x - 20, y + 15);
            g.drawString(nodeSymbolsToArray(root.getLastPos()).toString(), x + 20, y + 15);
            root.setX(x);
            root.setY(y);
            if (root.getLeftNode() != null) {
                if (root.getRightNode() != null) {
                    g.drawLine(x + 15, y + 15, root.getLeftNode().getX() + 15, root.getLeftNode().getY() + 15);
                    g.drawLine(x + 15, y + 15, root.getRightNode().getX() + 15, root.getRightNode().getY() + 15);
                } else {
                    g.drawLine(x + 15, y + 15, root.getLeftNode().getX() + 15, root.getLeftNode().getY() + 15);
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