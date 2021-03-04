package ru.mechtatell.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.dao.PositionDAO;
import ru.mechtatell.models.Position;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

@Component
public class PositionFrame {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;

    private final PositionDAO positionDAO;

    @Autowired
    public PositionFrame(PositionDAO positionDAO) {
        this.positionDAO = positionDAO;
    }

    public void init() {
        frame = new JFrame("Должности");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(535, 500);
        frame.setLayout(null);
        frame.setResizable(false);

        refreshTable();

        JLabel labelMain = new JLabel("Должности");
        frame.add(labelMain);
        labelMain.setFont(new Font("Roboto", Font.BOLD, 16));
        labelMain.setBounds(40, 10, 100, 30);

        JButton buttonCreate = new JButton("Создать");
        frame.add(buttonCreate);
        buttonCreate.setBounds(190, 10, 100, 30);
        buttonCreate.addActionListener(e -> create());

        JButton buttomRemove = new JButton("Удалить");
        frame.add(buttomRemove);
        buttomRemove.setBounds(300, 10, 100, 30);
        buttomRemove.addActionListener(e -> remove());

        JButton buttonUpdate = new JButton("Изменить");
        frame.add(buttonUpdate);
        buttonUpdate.setBounds(410, 10, 100, 30);
        buttonUpdate.addActionListener(e -> update());

        frame.repaint();
        frame.setVisible(true);
    }

    private void refreshTable() {
        java.util.List<Position> positionList = positionDAO.index();
        Object[][] data = new String[positionList.size()][3];

        for (int i = 0; i < data.length; i++) {
            data[i][0] = String.valueOf(positionList.get(i).getId());
            data[i][1] = positionList.get(i).getName();
            data[i][2] = String.valueOf(positionList.get(i).getPayment());
        }

        String[] header = {"id", "Название должности", "Оплата"};
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

    private JPanel getPositionPanel(String name, String payment) {
        JPanel panelPosition = new JPanel();
        JLabel labelName = new JLabel("Введите название");
        JLabel labelPayment = new JLabel("Введите оплату");

        JTextField textFieldName = new JTextField(name, 10);
        JTextField textFieldPayment = new JTextField(payment, 10);

        panelPosition.setLayout(new GridLayout(6, 1));
        panelPosition.add(labelName);
        panelPosition.add(textFieldName);
        panelPosition.add(Box.createVerticalStrut(10));
        panelPosition.add(labelPayment);
        panelPosition.add(textFieldPayment);

        return panelPosition;
    }

    private void create() {
        JPanel positionPanel = getPositionPanel("", "");
        int result = JOptionPane.showConfirmDialog(frame, positionPanel, "Создание должности",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                JTextField nameField = (JTextField) positionPanel.getComponent(1);
                if (nameField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат названия");
                }

                JTextField paymentField = (JTextField) positionPanel.getComponent(4);
                if (paymentField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат оплаты");
                }

                String name = nameField.getText();
                double payment = Double.parseDouble(paymentField.getText());

                Position position = new Position(name, payment);
                positionDAO.save(position);
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
                positionDAO.remove(id);
                refreshTable();
                table.clearSelection();
            }
        }
    }

    private void update() {
        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0)));

        Position position = positionDAO.show(id);

        JPanel positionPanel = getPositionPanel(position.getName(), String.valueOf(position.getPayment()));
        int result = JOptionPane.showConfirmDialog(frame, positionPanel, "Создание должности",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                JTextField nameField = (JTextField) positionPanel.getComponent(1);
                if (nameField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат названия");
                }

                JTextField paymentField = (JTextField) positionPanel.getComponent(4);
                if (paymentField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат оплаты");
                }

                String name = nameField.getText();
                double payment = Double.parseDouble(paymentField.getText());

                Position updatedPosition = new Position(name, payment);
                positionDAO.update(id, updatedPosition);
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
