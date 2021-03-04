package ru.mechtatell.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.dao.EmployeeDAO;
import ru.mechtatell.dao.MaterialDAO;
import ru.mechtatell.dao.PlanDAO;
import ru.mechtatell.dao.TeamDAO;
import ru.mechtatell.models.Employee;
import ru.mechtatell.models.Material;
import ru.mechtatell.models.Plan;
import ru.mechtatell.models.Team;
import ru.mechtatell.views.components.TableEmployee;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlanFrame {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;

    private final PlanDAO planDAO;
    private final MaterialDAO materialDAO;

    @Autowired
    public PlanFrame(PlanDAO planDAO, MaterialDAO materialDAO) {
        this.planDAO = planDAO;
        this.materialDAO = materialDAO;
    }

    public void init() {
        frame = new JFrame("Планы");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(710, 500);
        frame.setLayout(null);

        refreshTable();

        JButton buttonCreate = new JButton("Создать план");
        frame.add(buttonCreate);
        buttonCreate.setBounds(520, 20, 160, 30);
        buttonCreate.addActionListener(e -> create());

        JButton buttomRemove = new JButton("Удалить план");
        frame.add(buttomRemove);
        buttomRemove.setBounds(520, 60, 160, 30);
        buttomRemove.addActionListener(e -> remove());

        JButton buttonUpdate = new JButton("Изменить план");
        frame.add(buttonUpdate);
        buttonUpdate.setBounds(520, 100, 160, 30);
        buttonUpdate.addActionListener(e -> update());

        frame.repaint();
        frame.setVisible(true);
    }

    private void refreshTable() {
        List<Plan> plans = planDAO.index();
        Object[][] data = new String[plans.size()][4];

        for (int i = 0; i < data.length; i++) {
            data[i][0] = String.valueOf(plans.get(i).getId());
            data[i][1] = plans.get(i).getConstructionType();
            data[i][2] = String.valueOf(plans.get(i).getFloorsCount());
            data[i][3] = String.valueOf(plans.get(i).getMaterialList().size());
        }

        String[] header = {"id", "Тип конструкции", "Количество этажей", "Количество материалов"};
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

    private JPanel getNewTeamPanel(Plan plan) {
        JPanel panelCreateTeam = new JPanel();
        JLabel labelName = new JLabel("Введите имя");
        JLabel labelEmployees = new JLabel("Выберете сотрудников");

        TableEmployee model = new TableEmployee();
        for (Employee employee : plan.get) {
            boolean check = false;
            if (team != null) {
                check = team.getEmployeeList().contains(employee);
            }
            model.addRow(new Object[]{employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getPosition(), check});
        }
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JTextField textFieldName = new JTextField(name, 10);
        textFieldName.setMaximumSize(new Dimension(400, 30));
        panelCreateTeam.setLayout(new BoxLayout(panelCreateTeam, BoxLayout.Y_AXIS));
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.add(labelName);
        namePanel.add(textFieldName);
        namePanel.add(Box.createVerticalStrut(10));

        JPanel employeePanel = new JPanel();
        employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));
        employeePanel.add(labelEmployees);
        employeePanel.add(scrollPane);

        panelCreateTeam.add(namePanel);
        panelCreateTeam.add(employeePanel);

        return panelCreateTeam;
    }

    private void create() {
        JPanel employeePanel = getNewTeamPanel("", employeeDAO.index(), null);
        int result = JOptionPane.showConfirmDialog(frame, employeePanel, "Создание бригады",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                JTextField nameField = (JTextField) ((JComponent) employeePanel.getComponent(0)).getComponent(1);
                if (nameField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат имени");
                }

                List<Employee> employees = new ArrayList<>();
                JTable table = (JTable) ((JScrollPane) ((JComponent)employeePanel.getComponent(1)).getComponent(1)).getViewport().getView();

                for (int i = 0; i < table.getRowCount(); i++) {
                    boolean check = Boolean.parseBoolean((String.valueOf(table.getModel().getValueAt(i, 4))));
                    if (check) {
                        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(i, 0)));
                        employees.add(employeeDAO.show(id));
                    }
                }

                String name = nameField.getText();

                Team team = new Team(name, employees);
                teamDAO.save(team);
                refreshTable();
            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                create();
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
                teamDAO.remove(id);
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

        Team team = teamDAO.show(id);

        JPanel employeePanel = getNewTeamPanel(team.getName(), employeeDAO.index(), team);
        int result = JOptionPane.showConfirmDialog(frame, employeePanel, "Изменение бригады",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                JTextField nameField = (JTextField) ((JComponent) employeePanel.getComponent(0)).getComponent(1);
                if (nameField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат названия");
                }

                List<Employee> employeeList = new ArrayList<>();
                JTable table = (JTable) ((JScrollPane) ((JComponent)employeePanel.getComponent(1)).getComponent(1)).getViewport().getView();

                for (int i = 0; i < table.getRowCount(); i++) {
                    boolean check = Boolean.parseBoolean((String.valueOf(table.getModel().getValueAt(i, 4))));
                    if (check) {
                        int employeeId = Integer.parseInt(String.valueOf(table.getModel().getValueAt(i, 0)));
                        employeeList.add(employeeDAO.show(employeeId));
                    }
                }

                String name = nameField.getText();

                Team updatedTeam = new Team(name, employeeList);
                teamDAO.update(id, updatedTeam);
                refreshTable();

            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                update();
            }
        }
    }
}
