package challange.java_swing.gui;

public class Utils {

    public static String getFileExtension(String name) {
        int pointIndex = name.lastIndexOf(".");

        if (pointIndex == name.length()-1) {
            return null;
        }

        return  name.substring(pointIndex+1, name.length());
    }
}
