package app.view;

import com.sun.source.tree.Tree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class ServerTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {

    public ServerTreeCellRenderer renderer;
    private JCheckBox checkBox;
    private ServerInfo info;

    public ServerTreeCellEditor() {
        this.renderer = new ServerTreeCellRenderer();
    }

    /**
     * Sets an initial <I>value</I> for the editor.  This will cause
     * the editor to stopEditing and lose any partially edited value
     * if the editor is editing when this method is called. <p>
     * <p>
     * Returns the component that should be added to the client's
     * Component hierarchy.  Once installed in the client's hierarchy
     * this component will then be able to draw and receive user input.
     *
     * @param tree       the JTree that is asking the editor to edit;
     *                   this parameter can be null
     * @param value      the value of the cell to be edited
     * @param isSelected true if the cell is to be rendered with
     *                   selection highlighting
     * @param expanded   true if the node is expanded
     * @param leaf       true if the node is a leaf node
     * @param row        the row index of the node being edited
     * @return the component for editing
     */
    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {

        Component component = renderer.getTreeCellRendererComponent(tree, value, isSelected,expanded, leaf, row, true);

        if (leaf) {
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;

            info = (ServerInfo) treeNode.getUserObject();

            checkBox = (JCheckBox) component;

            ItemListener itemListener = new ItemListener() {
                /**
                 * Invoked when an item has been selected or deselected by the user.
                 * The code written for this method performs the operations
                 * that need to occur when an item is selected (or deselected).
                 *
                 * @param e the event to be processed
                 */
                @Override
                public void itemStateChanged(ItemEvent e) {
                    fireEditingStopped();
                    checkBox.removeItemListener(this);
                }
            };

        }

        return component;
    }

    /**
     * Returns true.
     *
     * @param e an event object
     * @return true
     */
    @Override
    public boolean isCellEditable(EventObject e) {
        if (!(e instanceof MouseEvent)) return false;

        MouseEvent mouseEvent = (MouseEvent) e;

        JTree tree = (JTree) e.getSource();

        TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());

        if (path == null) return false;

        Object lastComponent = path.getLastPathComponent();

        if (lastComponent == null) return false;

        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) lastComponent;

        return treeNode.isLeaf();
    }

    /**
     * Returns the value contained in the editor.
     *
     * @return the value contained in the editor
     */
    @Override
    public Object getCellEditorValue() {
        info.setCheck(checkBox.isSelected());
        return info;
    }
}
