package ru.mechtatell.Views.Util;

import javax.swing.*;
import java.awt.*;

public class Frame {

    private JFrame frame;

    public JFrame init(String name, int width, int height) {
        frame = new JFrame(name);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLayout(null);
        frame.setResizable(false);

        return frame;
    }

    protected void addNavigationButtons(String name, CRUDLogic logic) {
        JLabel labelMain = new JLabel(name);
        labelMain.setFont(new Font("Roboto", Font.BOLD, 16));
        labelMain.setBounds(40, 10, 100, 30);

        JButton buttonCreate = new JButton("Создать");
        buttonCreate.setBounds(190, 10, 100, 30);
        buttonCreate.addActionListener(e -> logic.create());

        JButton buttomRemove = new JButton("Удалить");
        buttomRemove.setBounds(300, 10, 100, 30);
        buttomRemove.addActionListener(e -> logic.remove());

        JButton buttonUpdate = new JButton("Изменить");
        buttonUpdate.setBounds(410, 10, 100, 30);
        buttonUpdate.addActionListener(e -> logic.update());

        frame.add(labelMain);
        frame.add(buttonCreate);
        frame.add(buttomRemove);
        frame.add(buttonUpdate);
    }
}
