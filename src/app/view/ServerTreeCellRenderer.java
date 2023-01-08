package app.view;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class ServerTreeCellRenderer implements TreeCellRenderer {

    private JCheckBox leafRenderer;
    private DefaultTreeCellRenderer noneLeafRenderer;
    private Color textForeground;
    private Color textBackground;
    private Color selectionForeground;
    private Color selectionBackground;


    public ServerTreeCellRenderer() {
        this.leafRenderer = new JCheckBox();
        this.noneLeafRenderer = new DefaultTreeCellRenderer();

        this.noneLeafRenderer.setLeafIcon(Utils.createIcon("/images/Server16.gif"));
        this.noneLeafRenderer.setOpenIcon(Utils.createIcon("/images/WebComponent16.gif"));
        this.noneLeafRenderer.setClosedIcon(Utils.createIcon("/images/WebComponentAdd16.gif"));

        textForeground = UIManager.getColor("Tree.textForeground");
        textBackground = UIManager.getColor("Tree.textBackground");
        selectionForeground = UIManager.getColor("Tree.selectionForeground");
        selectionBackground = UIManager.getColor("Tree.selectionBackground");

    }

    /**
     * Sets the value of the current tree cell to <code>value</code>.
     * If <code>selected</code> is true, the cell will be drawn as if
     * selected. If <code>expanded</code> is true the node is currently
     * expanded and if <code>leaf</code> is true the node represents a
     * leaf and if <code>hasFocus</code> is true the node currently has
     * focus. <code>tree</code> is the <code>JTree</code> the receiver is being
     * configured for.  Returns the <code>Component</code> that the renderer
     * uses to draw the value.
     * <p>
     * The <code>TreeCellRenderer</code> is also responsible for rendering the
     * the cell representing the tree's current DnD drop location if
     * it has one. If this renderer cares about rendering
     * the DnD drop location, it should query the tree directly to
     * see if the given row represents the drop location:
     * <pre>
     *     JTree.DropLocation dropLocation = tree.getDropLocation();
     *     if (dropLocation != null
     *             &amp;&amp; dropLocation.getChildIndex() == -1
     *             &amp;&amp; tree.getRowForPath(dropLocation.getPath()) == row) {
     *
     *         // this row represents the current drop location
     *         // so render it specially, perhaps with a different color
     *     }
     * </pre>
     *
     * @param tree     the receiver is being configured for
     * @param value    the value to render
     * @param selected whether node is selected
     * @param expanded whether node is expanded
     * @param leaf     whether node is a lead node
     * @param row      row index
     * @param hasFocus whether node has focus
     * @return the {@code Component} that the renderer uses to draw the value
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        if (leaf) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            ServerInfo nodeInfo = (ServerInfo) node.getUserObject();

            if (selected) {
                leafRenderer.setForeground(selectionForeground);
                leafRenderer.setBackground(selectionBackground);
            }else {
                leafRenderer.setForeground(textForeground);
                leafRenderer.setBackground(textBackground);
            }

            leafRenderer.setText(nodeInfo.toString());
            leafRenderer.setSelected(nodeInfo.isChecked());
            return leafRenderer;
        }else {
            return noneLeafRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        }
    }
}
