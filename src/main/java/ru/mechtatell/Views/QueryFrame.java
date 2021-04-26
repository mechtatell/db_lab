package ru.mechtatell.Views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.DTO.TeamDTO;
import ru.mechtatell.DAO.Interfaces.EmployeeDAO;
import ru.mechtatell.DAO.Interfaces.ProjectDAO;
import ru.mechtatell.DAO.DTO.EmployeeDTO;
import ru.mechtatell.DAO.DTO.ProjectDTO;
import ru.mechtatell.DAO.Interfaces.TeamDAO;
import ru.mechtatell.Views.Util.Frame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.List;

@Component
public class QueryFrame extends Frame {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;

    private final ProjectDAO projectDAO;
    private final EmployeeDAO employeeDAO;
    private final TeamDAO teamDAO;

    @Autowired
    public QueryFrame(ProjectDAO projectDAO, EmployeeDAO employeeDAO, TeamDAO teamDAO) {
        this.projectDAO = projectDAO;
        this.employeeDAO = employeeDAO;
        this.teamDAO = teamDAO;
    }

    public void init() {
        String NAME = "Запросы";
        frame = super.init(NAME, 535, 500);

        JButton buttonProjectQuery = new JButton("Планы");
        buttonProjectQuery.setBounds(190, 10, 100, 30);
        buttonProjectQuery.addActionListener(e -> refreshTableProject());

        JButton buttonEmployeeQuery = new JButton("Работники");
        buttonEmployeeQuery.setBounds(300, 10, 100, 30);
        buttonEmployeeQuery.addActionListener(e -> refreshTableEmployee());

        JButton buttonTeamQuery = new JButton("Бригады");
        buttonTeamQuery.setBounds(410, 10, 100, 30);
        buttonTeamQuery.addActionListener(e -> refreshTableTeam());

        frame.add(buttonProjectQuery);
        frame.add(buttonEmployeeQuery);
        frame.add(buttonTeamQuery);

        frame.repaint();
        frame.setVisible(true);
    }

    private void refreshTableProject() {
        List<ProjectDTO> projectStats = projectDAO.findProjectStat();
        Object[][] data = new String[projectStats.size()][6];

        for (int i = 0; i < data.length; i++) {
            data[i][0] = projectStats.get(i).getName();
            data[i][1] = projectStats.get(i).getStart().toString().split(" ")[0];
            data[i][2] = projectStats.get(i).getEnd().toString().split(" ")[0];
            data[i][3] = projectStats.get(i).getType();
            data[i][4] = String.valueOf(projectStats.get(i).getFloors());
            data[i][5] = String.valueOf(projectStats.get(i).getPrice());
        }

        String[] header = {"Проект", "Начало", "Завершение", "Тип", "Этажи", "Стоимость"};
        table = new JTable(data, header);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(40);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setPreferredWidth(30);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setPreferredWidth(20);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        if (scrollPane != null) {
            frame.remove(scrollPane);
        }
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        scrollPane.setBounds(10, 50, 500, 402);
        frame.repaint();
    }

    private void refreshTableEmployee() {
        List<EmployeeDTO> employeeStats = employeeDAO.findEmployeeStat();
        Object[][] data = new String[employeeStats.size()][5];

        for (int i = 0; i < data.length; i++) {
            data[i][0] = employeeStats.get(i).getName();
            data[i][1] = employeeStats.get(i).getSurname();
            data[i][2] = employeeStats.get(i).getPosition();
            data[i][3] = String.valueOf(employeeStats.get(i).getPayment());
            data[i][4] = String.valueOf(employeeStats.get(i).getTeams());
        }

        String[] header = {"Имя", "Фамилия", "Должность", "Оплата", "Бригады"};
        table = new JTable(data, header);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setPreferredWidth(25);
        if (scrollPane != null) {
            frame.remove(scrollPane);
        }
        scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        scrollPane.setBounds(10, 50, 500, 402);
        frame.repaint();
    }

    private void refreshTableTeam() {
        List<TeamDTO> teamStats = teamDAO.findTeamStat();
        Object[][] data = new String[teamStats.size()][3];

        for (int i = 0; i < data.length; i++) {
            data[i][0] = teamStats.get(i).getName();
            data[i][1] = String.valueOf(teamStats.get(i).getCount());
            data[i][2] = String.valueOf(teamStats.get(i).getSum());
        }

        String[] header = {"Название", "Сотрудники", "Общая оплата"};
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
}
