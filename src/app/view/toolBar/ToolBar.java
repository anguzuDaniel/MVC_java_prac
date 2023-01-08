package app.view.toolBar;

import app.view.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel implements ActionListener {

    private final JButton saveBtn;
    private final JButton refreshBtn;
    private ToolBarListener toolBarListener;


    public ToolBar() {
//        setBorder(BorderFactory.createEtchedBorder());

        refreshBtn = new JButton();
//        Dimension toolBarBtnDim = refreshBtn.getPreferredSize();

        saveBtn = new JButton();
//        saveBtn.setPreferredSize(toolBarBtnDim);
        saveBtn.setIcon(Utils.createIcon("/images/Save16.gif"));


        saveBtn.addActionListener(this);
        refreshBtn.addActionListener(this);
        refreshBtn.setIcon(Utils.createIcon("/images/Refresh16.gif"));

        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(saveBtn);
        add(refreshBtn);
    }


    public void setToolBarListener(ToolBarListener listener) {
        this.toolBarListener = listener;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (clicked == saveBtn) {
            if (toolBarListener != null) {
                toolBarListener.saveEventOccurred();
            }
        }else {
            if (toolBarListener != null) {
                toolBarListener.refreshEventOccurred();
            }
        }
    }
}
