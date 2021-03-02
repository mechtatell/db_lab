package ru.mechtatell.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.mechtatell.dao.EmployeeDAO;

import javax.swing.*;

@Component
public class MainFrame {

    @Autowired
    private ApplicationContext appContext;

    public void init() {
        JFrame frame = new JFrame("Главная форма");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu employeeMenu = new JMenu("Работники");
        JMenuItem showItemEmployee = new JMenuItem("Список работников");
        employeeMenu.add(showItemEmployee);
        showItemEmployee.addActionListener(e -> appContext.getBean(EmployeeFrame.class).init());

        JMenu positionMenu = new JMenu("Должность");
        JMenuItem showItemPosition = new JMenuItem("Список должностей");
        positionMenu.add(showItemPosition);
        showItemPosition.addActionListener(e -> appContext.getBean(PositionFrame.class).init());

        JMenu teamMenu = new JMenu("Бригада");
        //teamMenu.addActionListener(e -> saveFile());

        JMenu projectMenu = new JMenu("Проект");
        //projectMenu.addActionListener(e -> saveFile());

        JMenu planMenu = new JMenu("План");
        //planMenu.addActionListener(e -> saveFile());

        JMenu materialMenu = new JMenu("Материал");
        //materialMenu.addActionListener(e -> saveFile());

        menuBar.add(employeeMenu);
        menuBar.add(positionMenu);
        menuBar.add(teamMenu);
        menuBar.add(projectMenu);
        menuBar.add(planMenu);
        menuBar.add(materialMenu);

        frame.repaint();
        frame.setVisible(true);
    }
}
