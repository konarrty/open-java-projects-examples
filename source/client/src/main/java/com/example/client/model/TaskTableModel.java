package com.example.client.model;

import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.Vector;

public class TaskTableModel extends DefaultTableModel {
    public TaskTableModel() {
        super();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }


    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Выполнена";
            case 1 -> "Задача";
            case 2 -> "Дата начала";
            case 3 -> "Дата окончания";
            case 4 -> "Дата выполнения";
            default -> throw new IllegalStateException("Не сущетсвует столбца с таким номером");
        };
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Boolean.class;
            case 1 -> String.class;
            case 2, 3, 4 -> Date.class;
            default -> throw new IllegalStateException("Не сущетсвует столбца с таким номером");
        };
    }

    @Override
    public boolean isCellEditable(int row, int column) {

        return false;
    }

    public void setData(Vector<Vector<Object>> data) {

        setRowCount(0);
        data.forEach(this::addRow);
    }

}
