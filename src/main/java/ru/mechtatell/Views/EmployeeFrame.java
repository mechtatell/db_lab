package ru.mechtatell.Views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.Interfaces.EmployeeDAO;
import ru.mechtatell.DAO.Interfaces.PositionDAO;
import ru.mechtatell.Models.Employee;
import ru.mechtatell.Models.Position;
import ru.mechtatell.Views.Util.CRUDLogic;
import ru.mechtatell.Views.Util.Frame;
import ru.mechtatell.Views.Util.components.ComboBoxPosition;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

@Component
public class EmployeeFrame extends Frame {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;

    private final EmployeeDAO employeeDAO;
    private final PositionDAO positionDAO;

    @Autowired
    public EmployeeFrame(EmployeeDAO employeeDAO, PositionDAO positionDAO) {
        this.employeeDAO = employeeDAO;
        this.positionDAO = positionDAO;
    }

    public void init() {
        String NAME = "Работники";
        frame = super.init(NAME, 535, 500);
        refreshTable();
        super.addNavigationButtons(NAME, new CRUDLogic() {
            @Override
            public void create() {
                createEmployee();
            }

            @Override
            public void update() {
                updateEmployee();
            }

            @Override
            public void remove() {
                removeEmployee();
            }
        });

        frame.repaint();
        frame.setVisible(true);
    }

    private void refreshTable() {
        List<Employee> employeeList = employeeDAO.findAll();
        Object[][] data = new String[employeeList.size()][4];

        for (int i = 0; i < data.length; i++) {
            data[i][0] = String.valueOf(employeeList.get(i).getId());
            data[i][1] = employeeList.get(i).getFirstName();
            data[i][2] = employeeList.get(i).getLastName();
            data[i][3] = String.valueOf(employeeList.get(i).getPosition());
        }

        String[] header = {"id", "Имя", "Фамилия", "Должность"};
        table = new JTable(data, header);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        if (scrollPane != null) {
            frame.remove(scrollPane);
        }
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        scrollPane.setBounds(10, 50, 500, 402);
        frame.repaint();
    }

    private JPanel getNewEmployeePanel(Employee employee) {
        JPanel panelCreateEmployee = new JPanel();
        JLabel labelFirstName = new JLabel("Введите имя");
        JLabel labelLastName = new JLabel("Введите фамилию");
        JLabel labelPosition = new JLabel("Выберете должность");

        JTextField textFieldFirstName = new JTextField(employee != null ? employee.getFirstName() : "", 10);
        JTextField textFieldLastName = new JTextField(employee != null ? employee.getLastName() : "", 10);

        Position[] positions = positionDAO.findAll().toArray(new Position[0]);
        ComboBoxPosition comboBoxPositionModel = new ComboBoxPosition(positions);
        JComboBox<Position> comboBoxPosition = new JComboBox<>(comboBoxPositionModel);

        if (employee != null && employee.getPosition() != null) {
            Position position = Arrays.stream(positions)
                    .filter(e -> e.getId() == employee.getPosition().getId())
                    .findAny().orElse(null);

            int index = comboBoxPositionModel.getIndexOf(position);
            comboBoxPosition.setSelectedIndex(index);
        }

        panelCreateEmployee.setLayout(new GridLayout(9, 1));
        panelCreateEmployee.add(labelFirstName);
        panelCreateEmployee.add(textFieldFirstName);
        panelCreateEmployee.add(Box.createVerticalStrut(10));
        panelCreateEmployee.add(labelLastName);
        panelCreateEmployee.add(textFieldLastName);
        panelCreateEmployee.add(Box.createVerticalStrut(10));
        panelCreateEmployee.add(labelPosition);
        panelCreateEmployee.add(comboBoxPosition);
        panelCreateEmployee.add(Box.createVerticalStrut(10));

        return panelCreateEmployee;
    }

    private void createEmployee() {
        JPanel employeePanel = getNewEmployeePanel(new Employee());
        int result = JOptionPane.showConfirmDialog(frame, employeePanel, "Создание работника",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                Employee employee = createEmployeeModel(employeePanel);
                employeeDAO.save(employee);
                refreshTable();
            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                createEmployee();
            }
        }
    }

    private void removeEmployee() {
        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0)));

        if (id > 0) {
            int resultError = JOptionPane.showConfirmDialog(frame, "Вы действительно хотите удалить запись?",
                    "Подтверждение", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                employeeDAO.remove(id);
                refreshTable();
                table.clearSelection();
            }
        }
    }

    private void updateEmployee() {
        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0)));

        Employee employee;
        if (employeeDAO.findById(id).isPresent()) {
            employee = employeeDAO.findById(id).get();
        } else {
            return;
        }

        JPanel employeePanel = getNewEmployeePanel(employee);
        int result = JOptionPane.showConfirmDialog(frame, employeePanel, "Создание работника",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                Employee updatedEmployee = createEmployeeModel(employeePanel);
                updatedEmployee.setId(id);
                employeeDAO.save(updatedEmployee);
                refreshTable();
            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                createEmployee();
            }
        }
    }

    private Employee createEmployeeModel(JPanel employeePanel) throws Exception {
        JTextField firstNameField = (JTextField) employeePanel.getComponent(1);
        if (firstNameField.getText().isEmpty()) {
            throw new Exception("Некорректный формат ФИО");
        }

        JTextField lastNameField = (JTextField) employeePanel.getComponent(4);
        if (lastNameField.getText().isEmpty()) {
            throw new Exception("Некорректный формат ФИО");
        }

        JComboBox<String> comboBoxPosition = (JComboBox<String>) employeePanel.getComponent(7);
        if (comboBoxPosition.getSelectedItem() == null) {
            throw new Exception("Не выбрана должность");
        }

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        int positionId = ((Position) comboBoxPosition.getSelectedItem()).getId();

        return new Employee(firstName, lastName, positionDAO.findById(positionId).orElse(null));
    }
}
