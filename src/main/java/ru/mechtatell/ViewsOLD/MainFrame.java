//package ru.mechtatell.ViewsOLD;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PreDestroy;
//import javax.swing.*;
//
//@Component
//public class MainFrame {
//
//    @Autowired
//    private ApplicationContext appContext;
//
//    public void init() {
//        JFrame frame = new JFrame("Главная форма");
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setSize(700, 500);
//
//        JMenuBar menuBar = new JMenuBar();
//        frame.setJMenuBar(menuBar);
//
//        JMenu employeeMenu = new JMenu("Работники");
//        JMenuItem showItemEmployee = new JMenuItem("Список работников");
//        employeeMenu.add(showItemEmployee);
//        showItemEmployee.addActionListener(e -> appContext.getBean(EmployeeFrame.class).init());
//
//        JMenu positionMenu = new JMenu("Должность");
//        JMenuItem showItemPosition = new JMenuItem("Список должностей");
//        positionMenu.add(showItemPosition);
//        showItemPosition.addActionListener(e -> appContext.getBean(PositionFrame.class).init());
//
//        JMenu teamMenu = new JMenu("Бригада");
//        JMenuItem showItemTeam = new JMenuItem("Список бригад");
//        teamMenu.add(showItemTeam);
//        showItemTeam.addActionListener(e -> appContext.getBean(TeamFrame.class).init());
//
//        JMenu projectMenu = new JMenu("Проект");
//        JMenuItem showItemProject = new JMenuItem("Список проектов");
//        projectMenu.add(showItemProject);
//        showItemProject.addActionListener(e -> appContext.getBean(ProjectFrame.class).init());
//
//        JMenu planMenu = new JMenu("План");
//        JMenuItem showItemPlan = new JMenuItem("Список планов");
//        planMenu.add(showItemPlan);
//        showItemPlan.addActionListener(e -> appContext.getBean(PlanFrame.class).init());
//
//        JMenu materialMenu = new JMenu("Материал");
//        JMenuItem showItemMaterial = new JMenuItem("Список материалов");
//        materialMenu.add(showItemMaterial);
//        showItemMaterial.addActionListener(e -> appContext.getBean(MaterialFrame.class).init());
//
//        menuBar.add(employeeMenu);
//        menuBar.add(positionMenu);
//        menuBar.add(teamMenu);
//        menuBar.add(projectMenu);
//        menuBar.add(planMenu);
//        menuBar.add(materialMenu);
//
//        frame.repaint();
//        frame.setVisible(true);
//    }
//
//    @PreDestroy
//    private void close() {
//        ((AnnotationConfigApplicationContext) appContext).close();
//    }
//}
