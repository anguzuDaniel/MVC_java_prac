package app.view.fileInput;

import app.view.Utils;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.io.File;

public class PersonFileFilter extends FileFilter {
    /**
     * Whether the given file is accepted by this filter.
     *
     * @param file the File to test
     * @return true if the file is to be accepted
     */
    @Override
    public boolean accept(File file) {
        String name = file.getName();

        if (file.isDirectory()) {
            return true;
        }

        String extension = Utils.getFileExtension(name);

        if (extension == null) {
            return false;
        }

        return extension.equals(".per");
    }

    /**
     * The description of this filter. For example: "JPG and GIF Images"
     *
     * @return the description of this filter
     * @see FileView#getName
     */
    @Override
    public String getDescription() {
        return "Person database files (*.per)";
    }
}
