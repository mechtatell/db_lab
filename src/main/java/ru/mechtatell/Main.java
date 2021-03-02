package ru.mechtatell;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.mechtatell.dao.EmployeeDAO;
import ru.mechtatell.view.EmployeeFrame;
import ru.mechtatell.view.MainFrame;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext/user-bean-config.xml");
        context.getBean(MainFrame.class).init();
    }
}
