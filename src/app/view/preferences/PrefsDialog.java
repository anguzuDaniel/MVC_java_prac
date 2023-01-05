package app.view.preferences;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class PrefsDialog extends JDialog {

    private final JButton okButton;
    private JButton cancelButton;
    private JSpinner portSpinner;
    private SpinnerNumberModel spinnerModel;
    private JPasswordField passwordField;
    private JTextField userField;
    private PrefListener prefListener;


    public PrefsDialog(Frame parent) {
        super(parent, "Preferences", false);

        okButton = new JButton("Okay");
        cancelButton = new JButton("Cancel");
        spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerModel);

        passwordField = new JPasswordField(10);
        userField = new JTextField(10);



        // shows what user sees when they are typing
        passwordField.setEchoChar('*');

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.gridy = 0;

        // first row
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        add(new JLabel("User: "), gc);

        gc.gridx++;
        add(userField, gc);


        // next row
        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        add(new JLabel("Password: "), gc);

        gc.gridx++;
        add(passwordField, gc);


        // next row
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        add(new JLabel("Port :"), gc);


        gc.gridx++;
        add(portSpinner, gc);

        // next row
        gc.gridy++;
        add(okButton, gc);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer port = (Integer) portSpinner.getValue();

                String user = userField.getText();

                // passwordField.getPassword returns an array of characters
                char[] password = passwordField.getPassword();
                System.out.println(user + ": " + new String(password));


                if (prefListener != null) {

                    // we use new String to convert the password to a string since we retrieve it as as characters
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

        gc.gridx = 0;
        add(cancelButton, gc);

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
}
