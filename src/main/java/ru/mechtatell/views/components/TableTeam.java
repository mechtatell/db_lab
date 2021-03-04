package ru.mechtatell.views.components;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class TableTeam extends DefaultTableModel {

    public TableTeam() {
        super(new String[]{"Id", "Название", "Штат", "Выбран?"}, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class clazz = String.class;
        switch (columnIndex) {
            case 0:
                clazz = Integer.class;
                break;
            case 3:
                clazz = Boolean.class;
                break;
        }
        return clazz;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 3;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        if (aValue instanceof Boolean && column == 3) {
            System.out.println(aValue);
            Vector rowData = (Vector) getDataVector().get(row);
            rowData.set(3, (boolean) aValue);
            fireTableCellUpdated(row, column);
        }
    }
}
