package ru.mechtatell.Views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.Interfaces.EmployeeDAO;
import ru.mechtatell.DAO.Interfaces.TeamDAO;
import ru.mechtatell.Models.Employee;
import ru.mechtatell.Models.Team;
import ru.mechtatell.Views.Util.CRUDLogic;
import ru.mechtatell.Views.Util.Frame;
import ru.mechtatell.ViewsOLD.components.TableEmployee;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TeamFrame extends Frame {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;

    private final TeamDAO teamDAO;
    private final EmployeeDAO employeeDAO;

    @Autowired
    public TeamFrame(TeamDAO teamDAO, EmployeeDAO employeeDAO) {
        this.teamDAO = teamDAO;
        this.employeeDAO = employeeDAO;
    }

    public void init() {
        String NAME = "Бригады";
        frame = super.init(NAME, 535, 500);
        refreshTable();
        super.addNavigationButtons(NAME, new CRUDLogic() {
            @Override
            public void create() {
                createTeam();
            }

            @Override
            public void update() {
                updateTeam();
            }

            @Override
            public void remove() {
                removeTeam();
            }
        });

        frame.repaint();
        frame.setVisible(true);
    }

    private void refreshTable() {
        List<Team> teamList = teamDAO.findAll();
        Object[][] data = new String[teamList.size()][3];

        for (int i = 0; i < data.length; i++) {
            data[i][0] = String.valueOf(teamList.get(i).getId());
            data[i][1] = teamList.get(i).getName();
            data[i][2] = String.valueOf(teamList.get(i).getEmployeeList().size());
        }

        String[] header = {"id", "Название", "Число сотрудников"};
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

    private JPanel getNewTeamPanel(Team team) {
        JPanel panelCreateTeam = new JPanel();
        JLabel labelName = new JLabel("Введите имя");
        JLabel labelEmployees = new JLabel("Выберете сотрудников");

        TableEmployee model = new TableEmployee();
        for (Employee employee : employeeDAO.findAll()) {
            boolean check = false;
            if (team != null && team.getEmployeeList() != null) {
                check = team.getEmployeeList().contains(employee);
            }
            model.addRow(new Object[]{employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getPosition(), check});
        }
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JTextField textFieldName = new JTextField(team.getName(), 10);
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

    private void createTeam() {
        JPanel teamPanel = getNewTeamPanel(new Team());
        int result = JOptionPane.showConfirmDialog(frame, teamPanel, "Создание бригады",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                Team team = createTeamModel(teamPanel);
                teamDAO.save(team);
                refreshTable();
            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                createTeam();
            }
        }
    }

    private void removeTeam() {
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
                teamDAO.remove(id);
                refreshTable();
                table.clearSelection();
            }
        }
    }

    private void updateTeam() {
        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0)));

        Team team;
        if (teamDAO.findById(id).isPresent()) {
            team = teamDAO.findById(id).get();
        } else {
            return;
        }

        JPanel teamPanel = getNewTeamPanel(team);
        int result = JOptionPane.showConfirmDialog(frame, teamPanel, "Создание бригады",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                Team updatedTeam = createTeamModel(teamPanel);
                updatedTeam.setId(id);
                teamDAO.save(updatedTeam);
                refreshTable();
            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                createTeam();
            }
        }
    }

    private Team createTeamModel(JPanel teamPanel) throws Exception {
        JTextField nameField = (JTextField) ((JComponent) teamPanel.getComponent(0)).getComponent(1);
        if (nameField.getText().isEmpty()) {
            throw new Exception("Некорректный формат имени");
        }

        List<Employee> employees = new ArrayList<>();
        JTable table = (JTable) ((JScrollPane) ((JComponent) teamPanel.getComponent(1)).getComponent(1)).getViewport().getView();

        for (int i = 0; i < table.getRowCount(); i++) {
            boolean check = Boolean.parseBoolean((String.valueOf(table.getModel().getValueAt(i, 4))));
            if (check) {
                int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(i, 0)));
                employees.add(employeeDAO.findById(id).orElse(null));
            }
        }

        String name = nameField.getText();
        return new Team(name, employees);
    }
}
