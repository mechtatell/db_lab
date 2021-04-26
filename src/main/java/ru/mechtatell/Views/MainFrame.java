package ru.mechtatell.Views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.Views.Util.Frame;

import javax.swing.*;

@Component
public class MainFrame extends Frame {

    private final PositionFrame positionFrame;
    private final EmployeeFrame employeeFrame;
    private final TeamFrame teamFrame;
    private final MaterialFrame materialFrame;
    private final PlanFrame planFrame;
    private final ProjectFrame projectFrame;

    @Autowired
    public MainFrame(PositionFrame positionFrame, EmployeeFrame employeeFrame, TeamFrame teamFrame, MaterialFrame materialFrame, PlanFrame planFrame, ProjectFrame projectFrame) {
        this.positionFrame = positionFrame;
        this.employeeFrame = employeeFrame;
        this.teamFrame = teamFrame;
        this.materialFrame = materialFrame;
        this.planFrame = planFrame;
        this.projectFrame = projectFrame;
    }

    public void init() {
        JFrame frame = super.init("Главная форма", 700, 500);
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu employeeMenu = new JMenu("Работники");
        JMenuItem showItemEmployee = new JMenuItem("Список работников");
        employeeMenu.add(showItemEmployee);
        showItemEmployee.addActionListener(e -> employeeFrame.init());

        JMenu positionMenu = new JMenu("Должность");
        JMenuItem showItemPosition = new JMenuItem("Список должностей");
        positionMenu.add(showItemPosition);
        showItemPosition.addActionListener(e -> positionFrame.init());

        JMenu teamMenu = new JMenu("Бригада");
        JMenuItem showItemTeam = new JMenuItem("Список бригад");
        teamMenu.add(showItemTeam);
        showItemTeam.addActionListener(e -> teamFrame.init());

        JMenu projectMenu = new JMenu("Проект");
        JMenuItem showItemProject = new JMenuItem("Список проектов");
        projectMenu.add(showItemProject);
        showItemProject.addActionListener(e -> projectFrame.init());

        JMenu planMenu = new JMenu("План");
        JMenuItem showItemPlan = new JMenuItem("Список планов");
        planMenu.add(showItemPlan);
        showItemPlan.addActionListener(e -> planFrame.init());

        JMenu materialMenu = new JMenu("Материал");
        JMenuItem showItemMaterial = new JMenuItem("Список материалов");
        materialMenu.add(showItemMaterial);
        showItemMaterial.addActionListener(e -> materialFrame.init());

        menuBar.add(employeeMenu);
        menuBar.add(positionMenu);
        menuBar.add(teamMenu);
        menuBar.add(projectMenu);
        menuBar.add(planMenu);
        menuBar.add(materialMenu);

        frame.setVisible(true);
    }
}
