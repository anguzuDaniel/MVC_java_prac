package app.view.toolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel implements ActionListener {

    private final JButton saveBtn;
    private final JButton refreshBtn;
    private ToolBarListener toolBarListener;

    public ToolBar() {
        setBorder(BorderFactory.createEtchedBorder());

        refreshBtn = new JButton("Refresh");
        Dimension toolBarBtnDim = refreshBtn.getPreferredSize();

        saveBtn = new JButton("Save");
        saveBtn.setPreferredSize(toolBarBtnDim);


        saveBtn.addActionListener(this);
        refreshBtn.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(saveBtn);
        add(refreshBtn);
    }

    public void setStringListener(ToolBarListener listener) {
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
            }
        }else {
            if (toolBarListener != null) {
            }
        }
    }
}
