//package ru.mechtatell.ViewsOLD;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import ru.mechtatell.DAO.JdbcTemplateImpl.EmployeeDAO;
//import ru.mechtatell.DAO.JdbcTemplateImpl.TeamDAO;
//import ru.mechtatell.Models.Employee;
//import ru.mechtatell.Models.Team;
//import ru.mechtatell.ViewsOLD.components.TableEmployee;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableCellRenderer;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class TeamFrame {
//    private JFrame frame;
//    private JTable table;
//    private JScrollPane scrollPane;
//
//    private final EmployeeDAO employeeDAO;
//    private final TeamDAO teamDAO;
//
//    @Autowired
//    public TeamFrame(EmployeeDAO employeeDAO, TeamDAO teamDAO) {
//        this.employeeDAO = employeeDAO;
//        this.teamDAO = teamDAO;
//    }
//
//    public void init() {
//        frame = new JFrame("Бригады");
//        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        frame.setSize(535, 500);
//        frame.setLayout(null);
//        frame.setResizable(false);
//
//        refreshTable();
//
//        JLabel labelMain = new JLabel("Бригады");
//        frame.add(labelMain);
//        labelMain.setFont(new Font("Roboto", Font.BOLD, 16));
//        labelMain.setBounds(40, 10, 100, 30);
//
//        JButton buttonCreate = new JButton("Создать");
//        frame.add(buttonCreate);
//        buttonCreate.setBounds(190, 10, 100, 30);
//        buttonCreate.addActionListener(e -> create());
//
//        JButton buttomRemove = new JButton("Удалить");
//        frame.add(buttomRemove);
//        buttomRemove.setBounds(300, 10, 100, 30);
//        buttomRemove.addActionListener(e -> remove());
//
//        JButton buttonUpdate = new JButton("Изменить");
//        frame.add(buttonUpdate);
//        buttonUpdate.setBounds(410, 10, 100, 30);
//        buttonUpdate.addActionListener(e -> update());
//
//        frame.repaint();
//        frame.setVisible(true);
//    }
//
//    private void refreshTable() {
//        List<Team> teamList = teamDAO.index();
//        Object[][] data = new String[teamList.size()][3];
//
//        for (int i = 0; i < data.length; i++) {
//            data[i][0] = String.valueOf(teamList.get(i).getId());
//            data[i][1] = teamList.get(i).getName();
//            data[i][2] = String.valueOf(teamList.get(i).getEmployeeList().size());
//        }
//
//        String[] header = {"id", "Название", "Число сотрудников"};
//        table = new JTable(data, header);
//        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
//        table.getColumnModel().getColumn(0).setPreferredWidth(10);
//        if (scrollPane != null) {
//            frame.remove(scrollPane);
//        }
//        scrollPane = new JScrollPane(table);
//        frame.add(scrollPane);
//        scrollPane.setBounds(10, 50, 500, 402);
//        frame.repaint();
//    }
//
//    private JPanel getNewTeamPanel(String name, List<Employee> employeeList, Team team) {
//        JPanel panelCreateTeam = new JPanel();
//        JLabel labelName = new JLabel("Введите имя");
//        JLabel labelEmployees = new JLabel("Выберете сотрудников");
//
//        TableEmployee model = new TableEmployee();
//        for (Employee employee : employeeList) {
//            boolean check = false;
//            if (team != null) {
//                check = team.getEmployeeList().contains(employee);
//            }
//            model.addRow(new Object[]{employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getPosition(), check});
//        }
//        JTable table = new JTable(model);
//        JScrollPane scrollPane = new JScrollPane(table);
//        JTextField textFieldName = new JTextField(name, 10);
//        textFieldName.setMaximumSize(new Dimension(400, 30));
//        panelCreateTeam.setLayout(new BoxLayout(panelCreateTeam, BoxLayout.Y_AXIS));
//        JPanel namePanel = new JPanel();
//        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
//        namePanel.add(labelName);
//        namePanel.add(textFieldName);
//        namePanel.add(Box.createVerticalStrut(10));
//
//        JPanel employeePanel = new JPanel();
//        employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));
//        employeePanel.add(labelEmployees);
//        employeePanel.add(scrollPane);
//
//        panelCreateTeam.add(namePanel);
//        panelCreateTeam.add(employeePanel);
//
//        return panelCreateTeam;
//    }
//
//    private void create() {
//        JPanel employeePanel = getNewTeamPanel("", employeeDAO.index(), null);
//        int result = JOptionPane.showConfirmDialog(frame, employeePanel, "Создание бригады",
//                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//
//        try {
//            if (result == JOptionPane.OK_OPTION) {
//                JTextField nameField = (JTextField) ((JComponent) employeePanel.getComponent(0)).getComponent(1);
//                if (nameField.getText().isEmpty()) {
//                    throw new Exception("Некорректный формат имени");
//                }
//
//                List<Employee> employees = new ArrayList<>();
//                JTable table = (JTable) ((JScrollPane) ((JComponent)employeePanel.getComponent(1)).getComponent(1)).getViewport().getView();
//
//                for (int i = 0; i < table.getRowCount(); i++) {
//                    boolean check = Boolean.parseBoolean((String.valueOf(table.getModel().getValueAt(i, 4))));
//                    if (check) {
//                        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(i, 0)));
//                        employees.add(employeeDAO.show(id));
//                    }
//                }
//
//                String name = nameField.getText();
//
//                Team team = new Team(name, employees);
//                teamDAO.save(team);
//                refreshTable();
//            }
//        } catch (Exception ex) {
//            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
//                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
//            if (resultError == JOptionPane.OK_OPTION) {
//                create();
//            }
//        }
//    }
//
//    private void remove() {
//        if (table.getSelectedRowCount() != 1) {
//            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
//                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
//        }
//
//        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0)));
//
//        if (id > 0) {
//            int resultError = JOptionPane.showConfirmDialog(frame, "Вы действительно хотите удалить запись?",
//                    "Подтверждение", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
//            if (resultError == JOptionPane.OK_OPTION) {
//                teamDAO.remove(id);
//                refreshTable();
//                table.clearSelection();
//            }
//        }
//    }
//
//    private void update() {
//        if (table.getSelectedRowCount() != 1) {
//            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
//                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
//        }
//
//        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0)));
//
//        Team team = teamDAO.show(id);
//
//        JPanel employeePanel = getNewTeamPanel(team.getName(), employeeDAO.index(), team);
//        int result = JOptionPane.showConfirmDialog(frame, employeePanel, "Изменение бригады",
//                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//
//        try {
//            if (result == JOptionPane.OK_OPTION) {
//                JTextField nameField = (JTextField) ((JComponent) employeePanel.getComponent(0)).getComponent(1);
//                if (nameField.getText().isEmpty()) {
//                    throw new Exception("Некорректный формат названия");
//                }
//
//                List<Employee> employeeList = new ArrayList<>();
//                JTable table = (JTable) ((JScrollPane) ((JComponent)employeePanel.getComponent(1)).getComponent(1)).getViewport().getView();
//
//                for (int i = 0; i < table.getRowCount(); i++) {
//                    boolean check = Boolean.parseBoolean((String.valueOf(table.getModel().getValueAt(i, 4))));
//                    if (check) {
//                        int employeeId = Integer.parseInt(String.valueOf(table.getModel().getValueAt(i, 0)));
//                        employeeList.add(employeeDAO.show(employeeId));
//                    }
//                }
//
//                String name = nameField.getText();
//
//                Team updatedTeam = new Team(name, employeeList);
//                teamDAO.update(id, updatedTeam);
//                refreshTable();
//
//            }
//        } catch (Exception ex) {
//            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
//                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
//            if (resultError == JOptionPane.OK_OPTION) {
//                update();
//            }
//        }
//    }
//}
