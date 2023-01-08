package app.view;

import javax.swing.*;
import java.net.URL;

public class Utils {

    public static String getFileExtension(String name) {
        int pointIndex = name.lastIndexOf(".");

        if (pointIndex == name.length()-1) {
            return null;
        }

        return  name.substring(pointIndex+1, name.length());
    }

    public static ImageIcon createIcon(String path) {
        URL url = Utils.class.getResource(path);

        if (url == null) {
            System.err.println("Unable to find Icon image provided ");
        }

        ImageIcon icon = new ImageIcon(url);

        return icon;
    }
}
