package ru.mechtatell.Views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.Interfaces.MaterialDAO;
import ru.mechtatell.Models.Material;
import ru.mechtatell.Views.Util.CRUDLogic;
import ru.mechtatell.Views.Util.Frame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

@Component
public class MaterialFrame extends Frame {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;

    private final MaterialDAO materialDAO;

    @Autowired
    public MaterialFrame(MaterialDAO materialDAO) {
        this.materialDAO = materialDAO;
    }

    public void init() {
        String NAME = "Материалы";
        frame = super.init(NAME, 535, 500);
        refreshTable();
        super.addNavigationButtons(NAME, new CRUDLogic() {
            @Override
            public void create() {
                createMaterial();
            }

            @Override
            public void update() {
                updateMaterial();
            }

            @Override
            public void remove() {
                removeMaterial();
            }
        });

        frame.repaint();
        frame.setVisible(true);
    }

    private void refreshTable() {
        java.util.List<Material> materialList = materialDAO.findAll();
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

    private JPanel getNewMaterialPanel(Material material) {
        JPanel panelMaterial = new JPanel();
        JLabel labelName = new JLabel("Введите название");
        JLabel labelPrice = new JLabel("Введите стоимость");

        JTextField textFieldName = new JTextField(material != null ? material.getName() : "", 10);
        JTextField textFieldPrice = new JTextField(material != null ? String.valueOf(material.getPrice()) : "", 10);

        panelMaterial.setLayout(new GridLayout(6, 1));
        panelMaterial.add(labelName);
        panelMaterial.add(textFieldName);
        panelMaterial.add(Box.createVerticalStrut(10));
        panelMaterial.add(labelPrice);
        panelMaterial.add(textFieldPrice);

        return panelMaterial;
    }

    private void createMaterial() {
        JPanel materialPanel = getNewMaterialPanel(new Material());
        int result = JOptionPane.showConfirmDialog(frame, materialPanel, "Создание материала",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                Material Material = createMaterialModel(materialPanel);
                materialDAO.save(Material);
                refreshTable();
            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                createMaterial();
            }
        }
    }

    private void removeMaterial() {
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
                materialDAO.remove(id);
                refreshTable();
                table.clearSelection();
            }
        }
    }

    private void updateMaterial() {
        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0)));
        Material material = materialDAO.findById(id).orElse(null);

        JPanel materialPanel = getNewMaterialPanel(material);
        int result = JOptionPane.showConfirmDialog(frame, materialPanel, "Создание материала",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                Material updatedMaterial = createMaterialModel(materialPanel);
                updatedMaterial.setId(id);
                materialDAO.save(updatedMaterial);
                refreshTable();
            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                createMaterial();
            }
        }
    }

    private Material createMaterialModel(JPanel materialPanel) throws Exception {
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

        return new Material(name, price);
    }
}
