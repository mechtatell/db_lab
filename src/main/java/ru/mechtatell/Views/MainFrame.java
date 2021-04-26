package ru.mechtatell.Views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.Views.Util.Frame;

import javax.swing.*;
import java.awt.*;

@Component
public class MainFrame extends Frame {

    private final PositionFrame positionFrame;
    private final EmployeeFrame employeeFrame;
    private final TeamFrame teamFrame;
    private final MaterialFrame materialFrame;
    private final PlanFrame planFrame;
    private final ProjectFrame projectFrame;
    private final QueryFrame queryFrame;

    @Autowired
    public MainFrame(PositionFrame positionFrame, EmployeeFrame employeeFrame, TeamFrame teamFrame, MaterialFrame materialFrame, PlanFrame planFrame, ProjectFrame projectFrame, QueryFrame queryFrame) {
        this.positionFrame = positionFrame;
        this.employeeFrame = employeeFrame;
        this.teamFrame = teamFrame;
        this.materialFrame = materialFrame;
        this.planFrame = planFrame;
        this.projectFrame = projectFrame;
        this.queryFrame = queryFrame;
    }

    public void init() {
        JFrame frame = new JFrame("Главная");
        frame.setSize(700, 500);
        frame.setLayout(null);
        frame.setBackground(Color.WHITE);

        JLabel labelMain = new JLabel("Строительная фирма");
        labelMain.setBounds(60, 30, 680, 50);
        labelMain.setFont(new Font("Roboto", Font.BOLD, 54));

        JButton buttonPosition = new JButton("Должности");
        buttonPosition.setBounds(180, 160, 150, 25);
        buttonPosition.addActionListener(e -> positionFrame.init());

        JButton buttonEmployee = new JButton("Работники");
        buttonEmployee.setBounds(180, 200, 150, 25);
        buttonEmployee.addActionListener(e -> employeeFrame.init());

        JButton buttonTeam = new JButton("Бригады");
        buttonTeam.setBounds(180, 240, 150, 25);
        buttonTeam.addActionListener(e -> teamFrame.init());

        JButton buttonProject = new JButton("Проекты");
        buttonProject.setBounds(370, 160, 150, 25);
        buttonProject.addActionListener(e -> projectFrame.init());

        JButton buttonPlan = new JButton("Планы");
        buttonPlan.setBounds(370, 200, 150, 25);
        buttonPlan.addActionListener(e -> planFrame.init());

        JButton buttonMaterial = new JButton("Материалы");
        buttonMaterial.setBounds(370, 240, 150, 25);
        buttonMaterial.addActionListener(e -> materialFrame.init());

        JButton buttonQuery = new JButton("Запросы");
        buttonQuery.setBounds(180, 280, 340, 25);
        buttonQuery.addActionListener(e -> queryFrame.init());

        frame.add(labelMain);
        frame.add(buttonEmployee);
        frame.add(buttonMaterial);
        frame.add(buttonPlan);
        frame.add(buttonPosition);
        frame.add(buttonProject);
        frame.add(buttonQuery);
        frame.add(buttonTeam);

        frame.setVisible(true);
    }
}
