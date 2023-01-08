package app.view.toolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

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
        saveBtn.setIcon(createIcon("/images/Save16.gif"));


        saveBtn.addActionListener(this);
        refreshBtn.addActionListener(this);
        refreshBtn.setIcon(createIcon("/images/Refresh16.gif"));

        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(saveBtn);
        add(refreshBtn);
    }


    private ImageIcon createIcon(String path) {
        URL url = getClass().getResource(path);

        if (url == null) {
            System.err.println("Unable to find Icon image provided ");
        }

        ImageIcon icon = new ImageIcon(url);

        return icon;
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
