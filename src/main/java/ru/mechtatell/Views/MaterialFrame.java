package ru.mechtatell.Views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.MaterialDAO;
import ru.mechtatell.Models.Material;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

@Component
public class MaterialFrame {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;

    private final MaterialDAO materialDAO;

    @Autowired
    public MaterialFrame(MaterialDAO MaterialDAO) {
        this.materialDAO = MaterialDAO;
    }

    public void init() {
        frame = new JFrame("Материалы");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(535, 500);
        frame.setLayout(null);
        frame.setResizable(false);

        refreshTable();

        JLabel labelMain = new JLabel("Материалы");
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
        java.util.List<Material> materialList = materialDAO.index();
        Object[][] data = new String[materialList.size()][3];

        for (int i = 0; i < data.length; i++) {
            data[i][0] = String.valueOf(materialList.get(i).getId());
            data[i][1] = materialList.get(i).getName();
            data[i][2] = String.valueOf(materialList.get(i).getPrice());
        }

        String[] header = {"id", "Название", "Стоимость"};
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

    private JPanel getMaterialPanel(String name, String price) {
        JPanel panelMaterial = new JPanel();
        JLabel labelName = new JLabel("Введите название");
        JLabel labelPrice = new JLabel("Введите стоимость");

        JTextField textFieldName = new JTextField(name, 10);
        JTextField textFieldPrice = new JTextField(price, 10);

        panelMaterial.setLayout(new GridLayout(6, 1));
        panelMaterial.add(labelName);
        panelMaterial.add(textFieldName);
        panelMaterial.add(Box.createVerticalStrut(10));
        panelMaterial.add(labelPrice);
        panelMaterial.add(textFieldPrice);

        return panelMaterial;
    }

    private void create() {
        JPanel materialPanel = getMaterialPanel("", "");
        int result = JOptionPane.showConfirmDialog(frame, materialPanel, "Создание материала",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                JTextField nameField = (JTextField) materialPanel.getComponent(1);
                if (nameField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат названия");
                }

                JTextField priceField = (JTextField) materialPanel.getComponent(4);
                if (priceField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат стоимости");
                }

                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());

                Material material = new Material(name, price);
                materialDAO.save(material);
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
                materialDAO.remove(id);
                refreshTable();
                table.clearSelection();
            }
        }
    }

    private void update() {
        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        }

        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0)));

        Material material = materialDAO.show(id);

        JPanel MaterialPanel = getMaterialPanel(material.getName(), String.valueOf(material.getPrice()));
        int result = JOptionPane.showConfirmDialog(frame, MaterialPanel, "Редактирование материала",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                JTextField nameField = (JTextField) MaterialPanel.getComponent(1);
                if (nameField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат названия");
                }

                JTextField priceField = (JTextField) MaterialPanel.getComponent(4);
                if (priceField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат стоимости");
                }

                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());

                Material updatedMaterial = new Material(name, price);
                materialDAO.update(id, updatedMaterial);
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
