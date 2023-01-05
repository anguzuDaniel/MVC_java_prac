package app.view;

import app.controller.Controller;
import app.view.Tables.PersonTableListener;
import app.view.Tables.TablePanel;
import app.view.fileInput.PersonFileFilter;
import app.view.preferences.PrefListener;
import app.view.preferences.PrefsDialog;
import app.view.toolBar.ToolBarListener;
import app.view.toolBar.ToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class MainFrame extends JFrame {

    private final TextPanel textPanel;
    private final ToolBar toolBar;
    private final FormPanel formPanel;
    private final JFileChooser fileChooser;
    private final Controller controller;
    private final TablePanel tablePanel;
    private final PrefsDialog prefsDialog;
    private final Preferences prefs;


    public MainFrame() {
        super("Hello World");

        textPanel = new TextPanel();
        toolBar = new ToolBar();
        formPanel = new FormPanel();
        tablePanel = new TablePanel();
        prefsDialog = new PrefsDialog(this);

        controller = new Controller();

        tablePanel.setData(controller.getPeople());

        // create a key/node used to get preferences
        prefs = Preferences.userRoot().node("db");

        tablePanel.setPersonTableListener(new PersonTableListener() {
            public void rowDeleted(int row) {
                controller.removePerson(row);
            }
        });

        prefsDialog.setPrefsListener(new PrefListener() {
            @Override
            public void preferenceSet(String user, String password, int port) {
                prefs.put("user", user);
                prefs.put("password", password);
                prefs.put("port", Integer.toString(port));
            }
        });

        // default values for the preference
        String user = prefs.get("user", "");
        String password = prefs.get("password", "");
        int port = prefs.getInt("port", 3306);
        prefsDialog.setDefault(user, password, port);

        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new PersonFileFilter());

        setJMenuBar(createMenuBar());

        toolBar.setToolBarListener(new ToolBarListener() {
            @Override
            public void saveEventOccurred() {
                connect();

                try {
                    controller.save();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to database", "Save error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void refreshEventOccurred() {
                connect();

                try {
                    controller.load();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Unable to load from database", "Database load Error", JOptionPane.ERROR_MESSAGE);
                }

                tablePanel.refresh();
            }
        });

        formPanel.setFormListener(new FormListener() {
            public void formEventOccurred(FormEvent e) {
                controller.addPerson(e);
                tablePanel.refresh();
            }
        });

        setLayout(new BorderLayout());

        add(formPanel, BorderLayout.WEST);
        add(toolBar, BorderLayout.NORTH);
//        add(textPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        setMinimumSize(new Dimension(500, 400));
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void connect() {
        try {
            controller.connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database", "Database connection error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem importDataItem = new JMenuItem("Import Data...");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(exportDataItem);
        fileMenu.add(importDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu windowMenu = new JMenu("Window");

        // shows side table
        JMenu showMenu = new JMenu("Show");

        // adds preference list and dialog
        JMenuItem prefsItem = new JMenuItem("Preferences...");

        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
        showFormItem.setSelected(true);

        showMenu.add(showFormItem);
        windowMenu.add(showMenu);
        windowMenu.add(prefsItem);

        prefsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prefsDialog.setVisible(true);
            }
        });

        showFormItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
                formPanel.setVisible(menuItem.isSelected());
            }
        });

        // adds key commands to application
        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);

        // accelerators add key commands to a section on the page for example exit(CTRL X)
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        // opens the import dialog and to import a file using control i
        importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

        // opens reference dialogue using control p
        prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));


        importDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//              // chooses a file using the file choosers class
                if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.loadFromFile(fileChooser.getSelectedFile());
                        tablePanel.refresh();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file");
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println(fileChooser.getSelectedFile());
                }
            }
        });

        exportDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//              // chooses a file using the file choosers class
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.saveToFile(fileChooser.getSelectedFile());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file");
                    }
                    System.out.println(fileChooser.getSelectedFile());
                }
            }
        });

        // exits the application
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showInputDialog(MainFrame.this, "Please enter your user name.", "Enter user name", JOptionPane.OK_CANCEL_OPTION | JOptionPane.QUESTION_MESSAGE);

                int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit the application?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

                if (action == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }

            }
        });

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        return menuBar;
    }
}
