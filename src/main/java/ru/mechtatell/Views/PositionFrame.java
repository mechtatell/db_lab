package ru.mechtatell.Views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.Interfaces.PositionDAO;
import ru.mechtatell.Models.Position;
import ru.mechtatell.Views.Util.CRUDLogic;
import ru.mechtatell.Views.Util.Frame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

@Component
public class PositionFrame extends Frame {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;

    private final PositionDAO positionDAO;

    @Autowired
    public PositionFrame(PositionDAO positionDAO) {
        this.positionDAO = positionDAO;
    }

    public void init() {
        String NAME = "Должности";
        frame = super.init(NAME, 535, 500);
        refreshTable();
        super.addNavigationButtons(NAME, new CRUDLogic() {
            @Override
            public void create() {
                createPosition();
            }

            @Override
            public void update() {
                updatePosition();
            }

            @Override
            public void remove() {
                removePosition();
            }
        });

        frame.repaint();
        frame.setVisible(true);
    }

    private void refreshTable() {
        java.util.List<Position> positionList = positionDAO.findAll();
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

    private JPanel getNewPositionPanel(Position position) {
        JPanel panelPosition = new JPanel();
        JLabel labelName = new JLabel("Введите название");
        JLabel labelPayment = new JLabel("Введите оплату");

        JTextField textFieldName = new JTextField(position != null ?
                position.getName() : "", 10);
        JTextField textFieldPayment = new JTextField(position != null ?
                String.valueOf(position.getPayment()) : "", 10);

        panelPosition.setLayout(new GridLayout(6, 1));
        panelPosition.add(labelName);
        panelPosition.add(textFieldName);
        panelPosition.add(Box.createVerticalStrut(10));
        panelPosition.add(labelPayment);
        panelPosition.add(textFieldPayment);

        return panelPosition;
    }

    private void createPosition() {
        JPanel positionPanel = getNewPositionPanel(new Position());
        int result = JOptionPane.showConfirmDialog(frame, positionPanel, "Создание должности",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                Position position = createPositionModel(positionPanel);
                positionDAO.save(position);
                refreshTable();
            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                createPosition();
            }
        }
    }

    private void removePosition() {
        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
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

    private void updatePosition() {
        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0)));
        Position position = positionDAO.findById(id).orElse(null);

        JPanel positionPanel = getNewPositionPanel(position);
        int result = JOptionPane.showConfirmDialog(frame, positionPanel, "Создание должности",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                Position updatedPosition = createPositionModel(positionPanel);
                updatedPosition.setId(id);
                positionDAO.save(updatedPosition);
                refreshTable();
            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                createPosition();
            }
        }
    }

    private Position createPositionModel(JPanel positionPanel) throws Exception {
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

        return new Position(name, payment);
    }
}
