package challange.java_swing.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel implements ActionListener {

    private final JButton helloButton;
    private final JButton goodByeButton;
    private StringListener textListener;

    public ToolBar() {
        setBorder(BorderFactory.createEtchedBorder());
        helloButton = new JButton("Hello");
        goodByeButton = new JButton("Goodbye");

        helloButton.addActionListener(this);
        goodByeButton.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(helloButton);
        add(goodByeButton);
    }

    public void setStringListener(StringListener listener) {
        this.textListener = listener;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (clicked == helloButton) {
            if (textListener != null) {
                textListener.textEmitted("Hello\n");
            }
        }else {
            if (textListener != null) {
                textListener.textEmitted("Goodbye\n");
            }
        }
    }
}
