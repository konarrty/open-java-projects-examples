package com.example.client.renderer;

import dto.RangeDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

@Setter
@Getter
public class ColoredCellRenderer extends DefaultTableCellRenderer {

    private List<RangeDTO> coloredCells;

    public ColoredCellRenderer() {
        super();

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (coloredCells == null)
            return cellComponent;

        RangeDTO range = coloredCells.get(row);
        if (range == null
            || range.start() > column
            || range.end() < column) {
            cellComponent.setBackground(Color.WHITE);
        } else {
            cellComponent.setBackground(new Color(217, 217, 217));
        }
        return cellComponent;
    }
}