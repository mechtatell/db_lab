package ru.mechtatell.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.dao.PlanDAO;
import ru.mechtatell.dao.ProjectDAO;
import ru.mechtatell.dao.TeamDAO;
import ru.mechtatell.models.Employee;
import ru.mechtatell.models.Plan;
import ru.mechtatell.models.Project;
import ru.mechtatell.models.Team;
import ru.mechtatell.views.components.ComboBoxPlan;
import ru.mechtatell.views.components.TableTeam;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProjectFrame {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;

    private final TeamDAO teamDAO;
    private final ProjectDAO projectDAO;
    private final PlanDAO planDAO;

    @Autowired
    public ProjectFrame(TeamDAO teamDAO, ProjectDAO projectDAO, PlanDAO planDAO) {
        this.teamDAO = teamDAO;
        this.projectDAO = projectDAO;
        this.planDAO = planDAO;
    }

    public void init() {
        frame = new JFrame("Проект");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(910, 500);
        frame.setLayout(null);

        refreshTable();

        JButton buttonCreate = new JButton("Создать проект");
        frame.add(buttonCreate);
        buttonCreate.setBounds(720, 20, 160, 30);
        buttonCreate.addActionListener(e -> create());

        JButton buttomRemove = new JButton("Удалить проект");
        frame.add(buttomRemove);
        buttomRemove.setBounds(720, 60, 160, 30);
        buttomRemove.addActionListener(e -> remove());

        JButton buttonUpdate = new JButton("Изменить проект");
        frame.add(buttonUpdate);
        buttonUpdate.setBounds(720, 100, 160, 30);
        buttonUpdate.addActionListener(e -> update());

        frame.repaint();
        frame.setVisible(true);
    }

    private void refreshTable() {
        List<Project> projectList = projectDAO.index();
        Object[][] data = new String[projectList.size()][6];

        for (int i = 0; i < data.length; i++) {
            data[i][0] = String.valueOf(projectList.get(i).getId());
            data[i][1] = projectList.get(i).getName();
            data[i][2] = String.valueOf(projectList.get(i).getPlan().getId());
            System.out.println(projectList.get(i).getStartDate().toString());
            data[i][3] = projectList.get(i).getStartDate().toString();
            data[i][4] = projectList.get(i).getEndDate() != null ? projectList.get(i).getEndDate().toString() : "";
            data[i][5] = String.valueOf(projectList.get(i).getTeamList().size());
        }

        String[] header = {"id", "Название", "План", "Начало постройки", "Конец постройки", "Количество бригад"};

        table = new JTable(data, header);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(10);
        if (scrollPane != null) {
            frame.remove(scrollPane);
        }
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        scrollPane.setBounds(5, 5, 700, 450);
        frame.repaint();
    }

    private JPanel getNewProjectPanel(Project project) {
        JPanel panelCreateProject = new JPanel();
        JLabel labelName = new JLabel("Введите имя");
        JLabel labelStartDate = new JLabel("Выберите дату начала");
        JLabel labelEndDate = new JLabel("Выберете дату окончания");
        JLabel labelTeams = new JLabel("Выберете бригады");
        JLabel labelPlan = new JLabel("Выберете план");

        TableTeam model = new TableTeam();
        for (Team team : teamDAO.index()) {
            boolean check = false;
            if (project != null) {
                check = project.getTeamList().contains(team);
            }
            model.addRow(new Object[]{team.getId(), team.getName(), team.getEmployeeList().size(), check});
        }
        JTable table = new JTable(model);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(10);
        table.getColumnModel().getColumn(3).setPreferredWidth(10);
        JScrollPane scrollPane = new JScrollPane(table);

        JTextField textFieldName = new JTextField(project != null ? project.getName() : "", 10);
        textFieldName.setMaximumSize(new Dimension(400, 30));

        JFormattedTextField textFieldStartDate = new JFormattedTextField(new SimpleDateFormat("dd.MM.yyyy"));
        textFieldStartDate.setMaximumSize(new Dimension(400, 30));
        JFormattedTextField textFieldEndDate = new JFormattedTextField(new SimpleDateFormat("dd.MM.yyyy"));
        textFieldEndDate.setMaximumSize(new Dimension(400, 30));

        Plan[] plans = planDAO.index().toArray(new Plan[0]);
        ComboBoxPlan comboBoxPlanModel = new ComboBoxPlan(plans);
        JComboBox<Plan> comboBoxPlan = new JComboBox<>(comboBoxPlanModel);

        panelCreateProject.setLayout(new BoxLayout(panelCreateProject, BoxLayout.Y_AXIS));
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(labelName);
        infoPanel.add(textFieldName);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(labelStartDate);
        infoPanel.add(textFieldStartDate);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(labelEndDate);
        infoPanel.add(textFieldEndDate);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(labelPlan);
        infoPanel.add(comboBoxPlan);
        infoPanel.add(Box.createVerticalStrut(10));

        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BoxLayout(teamPanel, BoxLayout.Y_AXIS));
        teamPanel.add(labelTeams);
        teamPanel.add(scrollPane);

        panelCreateProject.add(infoPanel);
        panelCreateProject.add(teamPanel);

        return panelCreateProject;
    }

    private void create() {
        JPanel employeePanel = getNewProjectPanel(null);
        int result = JOptionPane.showConfirmDialog(frame, employeePanel, "Создание сотрудника",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                JTextField nameField = (JTextField) ((JComponent) employeePanel.getComponent(0)).getComponent(1);
                if (nameField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат имени");
                }

                JTextField startDateField = (JTextField) ((JComponent) employeePanel.getComponent(0)).getComponent(4);
                if (nameField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат даты");
                }

                JTextField endDateField = (JTextField) ((JComponent) employeePanel.getComponent(0)).getComponent(7);

                JComboBox<String> comboBoxPlan = (JComboBox<String>) ((JComponent) employeePanel.getComponent(0)).getComponent(10);
                if (comboBoxPlan.getSelectedItem() == null) {
                    throw new Exception("Не выбран план");
                }

                List<Team> teams = new ArrayList<>();
                JTable table = (JTable) ((JScrollPane) ((JComponent) employeePanel.getComponent(1)).getComponent(1)).getViewport().getView();

                for (int i = 0; i < table.getRowCount(); i++) {
                    boolean check = Boolean.parseBoolean((String.valueOf(table.getModel().getValueAt(i, 3))));
                    if (check) {
                        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(i, 0)));
                        teams.add(teamDAO.show(id));
                    }
                }

                String name = nameField.getText();
                Plan plan = planDAO.show((Integer) comboBoxPlan.getSelectedItem());
                System.out.println(startDateField.getText());
                Date startDate = new Date(new SimpleDateFormat("dd.MM.yyyy").parse(startDateField.getText()).getTime());
                Date endDate = null;
                if (!endDateField.getText().equals("")) {
                    endDate = new Date(new SimpleDateFormat("dd.MM.yyyy").parse(endDateField.getText()).getTime());
                }

                Project project = new Project(plan, name, startDate, endDate, teams);
                projectDAO.save(project);
                refreshTable();
            }
        } catch (Exception ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
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
                projectDAO.remove(id);
                refreshTable();
                table.clearSelection();
            }
        }
    }

    private void update() {
        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0)));

        Project project = projectDAO.show(id);
        JPanel projectPanel = getNewProjectPanel(project);
        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        }

        int result = JOptionPane.showConfirmDialog(frame, projectPanel, "Создание сотрудника",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                JTextField nameField = (JTextField) ((JComponent) projectPanel.getComponent(0)).getComponent(1);
                if (nameField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат имени");
                }

                JTextField startDateField = (JTextField) ((JComponent) projectPanel.getComponent(0)).getComponent(4);
                if (nameField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат даты");
                }

                JTextField endDateField = (JTextField) ((JComponent) projectPanel.getComponent(0)).getComponent(7);

                JComboBox<String> comboBoxPlan = (JComboBox<String>) ((JComponent) projectPanel.getComponent(0)).getComponent(10);
                if (comboBoxPlan.getSelectedItem() == null) {
                    throw new Exception("Не выбран план");
                }

                List<Team> teams = new ArrayList<>();
                JTable table = (JTable) ((JScrollPane) ((JComponent) projectPanel.getComponent(1)).getComponent(1)).getViewport().getView();

                for (int i = 0; i < table.getRowCount(); i++) {
                    boolean check = Boolean.parseBoolean((String.valueOf(table.getModel().getValueAt(i, 3))));
                    if (check) {
                        int teamId = Integer.parseInt(String.valueOf(table.getModel().getValueAt(i, 0)));
                        teams.add(teamDAO.show(teamId));
                    }
                }

                String name = nameField.getText();
                Plan plan = planDAO.show((Integer) comboBoxPlan.getSelectedItem());
                System.out.println(startDateField.getText());
                Date startDate = new Date(new SimpleDateFormat("dd.MM.yyyy").parse(startDateField.getText()).getTime());
                Date endDate = null;
                if (!endDateField.getText().equals("")) {
                    endDate = new Date(new SimpleDateFormat("dd.MM.yyyy").parse(endDateField.getText()).getTime());
                }

                Project updatedProject = new Project(plan, name, startDate, endDate, teams);
                projectDAO.update(id, updatedProject);
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
}
