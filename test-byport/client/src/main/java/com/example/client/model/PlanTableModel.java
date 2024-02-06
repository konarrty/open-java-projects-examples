package com.example.client.model;

import dto.MonthDTO;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.util.stream.IntStream;

public class PlanTableModel extends DefaultTableModel {

    private static final String FIRST_COLUMN_NAME = "Задача";

    public PlanTableModel() {
        super();
        addColumn(FIRST_COLUMN_NAME);

    }

    @Override
    public boolean isCellEditable(int row, int column) {

        return false;
    }

    public void setDataForMonth(Vector<Vector<Object>> data, MonthDTO month) {
        setColumnCount(1);
        IntStream.range(1, month.days() + 1).forEach(this::addColumn);
        setDataVector(data, columnIdentifiers);
    }
}
