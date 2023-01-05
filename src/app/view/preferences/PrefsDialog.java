package app.view.preferences;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrefsDialog extends JDialog {

    private final JButton okButton;
    private final JButton cancelButton;
    private final JSpinner portSpinner;
    private final JPasswordField passwordField;
    private final JTextField userField;
    private PrefListener prefListener;


    public PrefsDialog(Frame parent) {
        super(parent, "Preferences", false);

        okButton = new JButton("Okay");
        cancelButton = new JButton("Cancel");
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerModel);

        passwordField = new JPasswordField(10);
        userField = new JTextField(10);



        // shows what user sees when they are typing
        passwordField.setEchoChar('*');

        // adds the layouts
        this.layoutControls();

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer port = (Integer) portSpinner.getValue();

                String user = userField.getText();

                // passwordField.getPassword returns an array of characters
                char[] password = passwordField.getPassword();
                System.out.println(user + ": " + new String(password));


                if (prefListener != null) {

                    // we use new String to convert the password to a string since we retrieve it as characters
                    prefListener.preferenceSet(user, new String(password), port);
                }

                System.out.println(port);

                setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    // set default preference credentials
    public void setDefault(String user, String password, int port) {
        userField.setText(user);
        passwordField.setText(password);
        portSpinner.setValue(port);
    }

    public void setPrefsListener(PrefListener prefListener) {
        this.prefListener = prefListener;
    }

    public void layoutControls() {
        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        controlsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        controlsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridy = 0;

        // first row
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        controlsPanel.add(new JLabel("User: "), gc);

        gc.gridx++;
        controlsPanel.add(userField, gc);


        // next row
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        controlsPanel.add(new JLabel("Password: "), gc);

        gc.gridx++;
        controlsPanel.add(passwordField, gc);


        // next row
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        controlsPanel.add(new JLabel("Port :"), gc);


        gc.gridx++;
        controlsPanel.add(portSpinner, gc);

        // the buttons are added to the buttons panel
        // next row
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okButton, gc);
        buttonsPanel.add(cancelButton, gc);

        // makes the buttons the same size
        // get the size of cancel button and sets same size to okay button
        Dimension btnSize = cancelButton.getPreferredSize();
        okButton.setPreferredSize(btnSize);

        // set the layout of the parent panel
        setLayout(new BorderLayout());
        add(controlsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
