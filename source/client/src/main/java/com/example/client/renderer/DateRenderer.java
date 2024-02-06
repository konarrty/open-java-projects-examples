package com.example.client.renderer;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateRenderer extends DefaultTableCellRenderer {

    public DateRenderer() {
        super();

    }

    public void setValue(Object value) {
        if (value instanceof Date dateValue) {
            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
           
            value = formatter.format(dateValue);
        }
        super.setValue(value);
    }
}