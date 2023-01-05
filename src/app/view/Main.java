package app.view;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                    MainFrame mainFrame = new MainFrame();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
