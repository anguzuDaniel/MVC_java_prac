package app.view.Tables;

import app.model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TablePanel extends JPanel {
    private final PersonTableModel tableModel;
    private final JPopupMenu popup;
    private PersonTableListener personTableListener;

    public TablePanel() {
        tableModel = new PersonTableModel();
        JTable table = new JTable(tableModel);

        JMenuItem removeItem = new JMenuItem("Delete row");

        popup = new JPopupMenu();
        popup.add(removeItem);

        table.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());

                if (e.getButton() == MouseEvent.BUTTON3) {
                    table.getSelectionModel().setSelectionInterval(row, row);

                    popup.show(table, e.getX(), e.getY());
                }
            }
        });

        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();

                if (personTableListener != null) {
                    personTableListener.rowDeleted(row);
                    tableModel.fireTableRowsDeleted(row, row);
                }

                System.out.println(row);
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setData(List<Person> db) {
        tableModel.setData(db);
    }

    public void refresh() {
        tableModel.fireTableDataChanged();
    }

    public void setPersonTableListener(PersonTableListener personTableListener) {
        this.personTableListener = personTableListener;
    }
}
