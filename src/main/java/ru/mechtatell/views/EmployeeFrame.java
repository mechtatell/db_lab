package ru.mechtatell.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.dao.EmployeeDAO;
import ru.mechtatell.dao.PositionDAO;
import ru.mechtatell.models.Employee;
import ru.mechtatell.models.Position;
import ru.mechtatell.views.components.ComboBoxPosition;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

@Component
public class EmployeeFrame {
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
        frame = new JFrame("Работники");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(710, 500);
        frame.setLayout(null);

        refreshTable();

        JButton buttonCreate = new JButton("Создать работника");
        frame.add(buttonCreate);
        buttonCreate.setBounds(520, 20, 160, 30);
        buttonCreate.addActionListener(e -> createNewEmployee());

        JButton buttomRemove = new JButton("Уволить работника");
        frame.add(buttomRemove);
        buttomRemove.setBounds(520, 60, 160, 30);
        buttomRemove.addActionListener(e -> remove());

        JButton buttonUpdate = new JButton("Изменить работника");
        frame.add(buttonUpdate);
        buttonUpdate.setBounds(520, 100, 160, 30);
        buttonUpdate.addActionListener(e -> update());

        frame.repaint();
        frame.setVisible(true);
    }

    private void refreshTable() {
        List<Employee> employeeList = employeeDAO.index();
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
        scrollPane.setBounds(5, 5, 500, 450);
        frame.repaint();
    }

    private JPanel getNewEmployeePanel(String firstName, String lastName, int positionId) {
        JPanel panelCreateEmployee = new JPanel();
        JLabel labelFirstName = new JLabel("Введите имя");
        JLabel labelLastName = new JLabel("Введите фамилию");
        JLabel labelPosition = new JLabel("Выберете должность");

        JTextField textFieldFirstName = new JTextField(firstName, 10);
        JTextField textFieldLastName = new JTextField(lastName, 10);

        Position[] positions = positionDAO.index().toArray(new Position[0]);
        ComboBoxPosition comboBoxPositionModel = new ComboBoxPosition(positions);
        JComboBox<Position> comboBoxPosition = new JComboBox<>(comboBoxPositionModel);
        comboBoxPosition.setSelectedItem(positionId);

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

    private void createNewEmployee() {
        JPanel employeePanel = getNewEmployeePanel("", "", 1);
        int result = JOptionPane.showConfirmDialog(frame, employeePanel, "Создание сотрудника",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
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
                int positionId = (int) comboBoxPosition.getSelectedItem();

                Employee employee = new Employee(firstName, lastName, positionDAO.show(positionId));
                employeeDAO.save(employee);
                refreshTable();
            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                createNewEmployee();
            }
        }
    }

    private void remove() {
        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
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

    private void update() {
        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        }

        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0)));

        Employee employee = employeeDAO.show(id);

        JPanel employeePanel = getNewEmployeePanel(employee.getFirstName(), employee.getLastName(), employee.getPosition().getId());
        int result = JOptionPane.showConfirmDialog(frame, employeePanel, "Изменение сотрудника",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
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
                int positionId = (int) comboBoxPosition.getSelectedItem();

                Employee updatedEmployee = new Employee(firstName, lastName, positionDAO.show(positionId));
                employeeDAO.update(id, updatedEmployee);
                refreshTable();

            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                createNewEmployee();
            }
        }
    }
}
