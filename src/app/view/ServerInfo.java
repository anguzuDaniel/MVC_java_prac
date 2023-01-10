package app.view;

import java.util.Set;

public class ServerInfo{
    private final String name;
    private final int id;
    private final boolean checked;

    public ServerInfo(String name, int id, boolean checked) {
        this.name = name;
        this.id = id;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return name;
    }

    public boolean isChecked() {
        return checked;
    }

    public Boolean setCheck(boolean selected) {
        return selected;
    }
}