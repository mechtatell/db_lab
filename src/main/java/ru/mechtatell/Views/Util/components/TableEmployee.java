package ru.mechtatell.Views.Util.components;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class TableEmployee extends DefaultTableModel {

    public TableEmployee() {
        super(new String[]{"Id", "Имя", "Фамилия", "Должность", "Выбран?"}, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class clazz = String.class;
        switch (columnIndex) {
            case 0:
                clazz = Integer.class;
                break;
            case 4:
                clazz = Boolean.class;
                break;
        }
        return clazz;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 4;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        if (aValue instanceof Boolean && column == 4) {
            Vector rowData = (Vector) getDataVector().get(row);
            rowData.set(4, (boolean) aValue);
            fireTableCellUpdated(row, column);
        }
    }
}
