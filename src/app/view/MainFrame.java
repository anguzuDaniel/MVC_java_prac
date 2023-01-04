package app.view;

import app.controller.Controller;
import app.view.Tables.PersonTableListener;
import app.view.Tables.TablePanel;
import app.view.fileInput.PersonFileFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class MainFrame extends JFrame {

    private final TextPanel textPanel;
    private final ToolBar toolBar;
    private final FormPanel formPanel;
    private final JFileChooser fileChooser;
    private Controller controller;
    private TablePanel tablePanel;


    public MainFrame() {
        super("Hello World");

        textPanel = new TextPanel();
        toolBar = new ToolBar();
        formPanel = new FormPanel();
        tablePanel = new TablePanel();

        controller = new Controller();

        tablePanel.setData(controller.getPeople());

        tablePanel.setPersonTableListener(new PersonTableListener() {
            public void rowDeleted(int row) {
                controller.removePerson(row);
            }
        });

        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new PersonFileFilter());

        setJMenuBar(createMenuBar());

        toolBar.setStringListener(new StringListener() {
            @Override
            public void textEmitted(String text) {
                textPanel.appendText(text);
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
        JMenu showMenu = new JMenu("Show");

        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
        showFormItem.setSelected(true);

        showMenu.add(showFormItem);
        windowMenu.add(showMenu);

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

        // opens the import dialog and to import a file
        importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

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
