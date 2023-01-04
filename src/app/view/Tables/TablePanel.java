package app.view.Tables;

import app.model.Person;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel {
    private final PersonTableModel tableModel;

    public TablePanel() {
        tableModel = new PersonTableModel();
        JTable table = new JTable(tableModel);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setData(List<Person> db) {
        tableModel.setData(db);
    }

    public void refresh() {
        tableModel.fireTableDataChanged();
    }
}
