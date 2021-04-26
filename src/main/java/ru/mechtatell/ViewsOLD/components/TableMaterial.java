package ru.mechtatell.ViewsOLD.components;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class TableMaterial extends DefaultTableModel {

    public TableMaterial() {
        super(new String[]{"Id", "Название", "Стоимось","Количество", "Выбран?"}, 0);
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
        return column == 4 || column == 3;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        if (aValue instanceof Boolean && column == 4) {
            Vector rowData = (Vector) getDataVector().get(row);
            rowData.set(4, aValue);
            fireTableCellUpdated(row, column);
        } else if (column == 3) {
            Vector rowData = (Vector) getDataVector().get(row);
            rowData.set(3, aValue);
            fireTableCellUpdated(row, column);
        }
    }
}
