package com.example.client.forms;

import com.example.client.client.EmployeeClient;
import com.example.client.client.MonthClient;
import com.example.client.client.TaskClient;
import com.example.client.mapper.TaskMapper;
import com.example.client.model.PlanTableModel;
import com.example.client.model.TaskTableModel;
import com.example.client.renderer.ColoredCellRenderer;
import com.example.client.renderer.DateRenderer;
import dto.EmployeeDTO;
import dto.MonthDTO;
import dto.TaskByMonthDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Collection;
import java.util.Date;

import static com.example.client.factory.ComboBoxModelFactory.createComboBoxModel;

@Log4j2
@Component
@RequiredArgsConstructor
public class Main extends JFrame {
    private JPanel rootPanel;
    private JTable taskTable;
    private JTable planTable;
    private JComboBox<EmployeeDTO> employeeComboBox;
    private JComboBox<MonthDTO> monthComboBox;
    private JTabbedPane tabbedPane1;
    private final TaskTableModel taskTableModel = new TaskTableModel();
    private final PlanTableModel planTableModel = new PlanTableModel();

    private final TaskClient taskClient;
    private final EmployeeClient employeeClient;
    private final MonthClient monthClient;

    private final TaskMapper taskMapper;

    @PostConstruct
    public void init() {
        setContentPane(rootPanel);

        initTaskTable();
        initPlanTable();
        initEmployeeComboBox();
        initMonthComboBox();
    }

    public void initTaskTable() {
        taskTable.setModel(taskTableModel);
        taskTable.setDefaultRenderer(Date.class, new DateRenderer());
        taskTable.getTableHeader().setReorderingAllowed(false);
        taskTable.getColumnModel().getColumn(0).setMaxWidth(68);

    }

    public void initPlanTable() {
        planTable.setModel(planTableModel);
        planTable.setDefaultRenderer(Object.class, new ColoredCellRenderer());
        planTable.getTableHeader().setReorderingAllowed(false);
        planTable.getTableHeader().setVisible(false);

    }

    public void initEmployeeComboBox() {
        employeeComboBox.addItemListener(this::comboBoxEmployeeItemStateChanged);
        employeeComboBox.setModel(createComboBoxModel(employeeClient.getEmployees()));

    }

    public void initMonthComboBox() {
        monthComboBox.addItemListener(this::comboBoxMonthItemStateChanged);
        monthComboBox.setModel(createComboBoxModel(monthClient.getMonths()));
        monthComboBox.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));

    }

    private void comboBoxEmployeeItemStateChanged(ItemEvent e) {
        if (e.getStateChange() != ItemEvent.SELECTED)
            return;

        var employee = (EmployeeDTO) e.getItem();
        var month = (MonthDTO) monthComboBox.getSelectedItem();

        fillTaskTable(employee.id());

        if (month != null)
            fillPlanTable(employee.id(), month);
    }

    private void comboBoxMonthItemStateChanged(ItemEvent e) {
        if (e.getStateChange() != ItemEvent.SELECTED)
            return;

        var month = (MonthDTO) e.getItem();
        var employee = (EmployeeDTO) employeeComboBox.getSelectedItem();

        if (employee != null)
            fillPlanTable(employee.id(), month);
    }

    private void fillTaskTable(Long employeeId) {

        var tasks = taskClient.getTasksByEmployeeId(employeeId);
        var data = taskMapper.taskToVectorOfVectors(tasks);

        taskTableModel.setData(data);
    }

    private void fillPlanTable(Long employeeId, MonthDTO month) {

        var tasks = taskClient.getTasksByEmployeeIdAndMonthNumber(employeeId, month.number());
        var data = taskMapper.taskByMonthToVectorOfVectors(tasks);

        planTableModel.setDataForMonth(data, month);
        planTable.getColumnModel().getColumn(0).setMinWidth(110);
        planTable.getTableHeader().setVisible(true);
        colorTheCells(tasks);
    }

    private void colorTheCells(Collection<TaskByMonthDTO> tasks) {

        var renderer = (ColoredCellRenderer) planTable.getDefaultRenderer(Object.class);
        var coloredCells = tasks.stream().map(TaskByMonthDTO::range).toList();
        renderer.setColoredCells(coloredCells);

        log.info("Закрашены ячейки в соответствии с планом выполнения задач: " + tasks);
    }
}