package ru.mechtatell.Views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.MaterialDAO;
import ru.mechtatell.DAO.PlanDAO;
import ru.mechtatell.Models.Material;
import ru.mechtatell.Models.Plan;
import ru.mechtatell.Views.components.TableMaterial;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PlanFrame {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;

    private final PlanDAO planDAO;
    private final MaterialDAO materialDAO;

    @Autowired
    public PlanFrame(PlanDAO planDAO, MaterialDAO materialDAO) {
        this.planDAO = planDAO;
        this.materialDAO = materialDAO;
    }

    public void init() {
        frame = new JFrame("Планы");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(535, 500);
        frame.setLayout(null);
        frame.setResizable(false);

        refreshTable();

        JLabel labelMain = new JLabel("Планы");
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
        List<Plan> plans = planDAO.index();
        Object[][] data = new String[plans.size()][4];

        for (int i = 0; i < data.length; i++) {
            data[i][0] = String.valueOf(plans.get(i).getId());
            data[i][1] = plans.get(i).getConstructionType();
            data[i][2] = String.valueOf(plans.get(i).getFloorsCount());
            data[i][3] = String.valueOf(plans.get(i).getMaterialList().size());
        }

        String[] header = {"id", "Тип конструкции", "Количество этажей", "Количество материалов"};
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

    private JPanel getNewPlanPanel(Plan plan) {
        JPanel panelCreateTeam = new JPanel();
        JLabel labelType = new JLabel("Введите тип конструкции");
        JLabel labelFloors = new JLabel("Введите количество этажей");
        JLabel labelMaterials = new JLabel("Выберете материалы");

        TableMaterial model = new TableMaterial();
        for (Material material : materialDAO.index()) {
            boolean check = false;
            int count = 0;
            if (plan != null) {
                check = plan.getMaterialList().containsKey(material);
                if (check) {
                    count = plan.getMaterialList().get(material);
                }
            }
            model.addRow(new Object[]{material.getId(), material.getName(), material.getPrice(), count, check});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JTextField textFieldType = new JTextField(plan != null ? plan.getConstructionType() : "", 10);
        textFieldType.setMaximumSize(new Dimension(400, 30));

        JTextField textFieldFloors = new JTextField(plan != null ? String.valueOf(plan.getFloorsCount()) : "", 10);
        textFieldFloors.setMaximumSize(new Dimension(400, 30));

        panelCreateTeam.setLayout(new BoxLayout(panelCreateTeam, BoxLayout.Y_AXIS));
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.add(labelType);
        namePanel.add(textFieldType);
        namePanel.add(Box.createVerticalStrut(10));
        namePanel.add(labelFloors);
        namePanel.add(textFieldFloors);
        namePanel.add(Box.createVerticalStrut(10));

        JPanel materialPanel = new JPanel();
        materialPanel.setLayout(new BoxLayout(materialPanel, BoxLayout.Y_AXIS));
        materialPanel.add(labelMaterials);
        materialPanel.add(scrollPane);

        panelCreateTeam.add(namePanel);
        panelCreateTeam.add(materialPanel);

        return panelCreateTeam;
    }

    private void create() {
        JPanel planPanel = getNewPlanPanel(null);
        int result = JOptionPane.showConfirmDialog(frame, planPanel, "Создание плана",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                JTextField typeField = (JTextField) ((JComponent) planPanel.getComponent(0)).getComponent(1);
                if (typeField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат типа конструкции");
                }

                JTextField floorsField = (JTextField) ((JComponent) planPanel.getComponent(0)).getComponent(4);
                if (floorsField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат количества этажей");
                }

                Map<Material, Integer> materials = new HashMap<>();
                JTable table = (JTable) ((JScrollPane) ((JComponent)planPanel.getComponent(1)).getComponent(1)).getViewport().getView();

                for (int i = 0; i < table.getRowCount(); i++) {
                    boolean check = Boolean.parseBoolean((String.valueOf(table.getModel().getValueAt(i, 4))));
                    if (check) {
                        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(i, 0)));
                        int count = Integer.parseInt(String.valueOf(table.getModel().getValueAt(i, 3)));
                        materials.put(materialDAO.show(id), count);
                    }
                }

                String type = typeField.getText();
                int floorsCount = Integer.parseInt(floorsField.getText());

                Plan plan = new Plan(type, floorsCount, materials);
                planDAO.save(plan);
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
                planDAO.remove(id);
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

        Plan plan = planDAO.show(id);

        JPanel planPanel = getNewPlanPanel(plan);
        int result = JOptionPane.showConfirmDialog(frame, planPanel, "Изменение плана",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                JTextField typeField = (JTextField) ((JComponent) planPanel.getComponent(0)).getComponent(1);
                if (typeField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат типа конструкции");
                }

                JTextField floorsField = (JTextField) ((JComponent) planPanel.getComponent(0)).getComponent(4);
                if (floorsField.getText().isEmpty()) {
                    throw new Exception("Некорректный формат количества этажей");
                }

                Map<Material, Integer> materials = new HashMap<>();
                JTable table = (JTable) ((JScrollPane) ((JComponent)planPanel.getComponent(1)).getComponent(1)).getViewport().getView();

                for (int i = 0; i < table.getRowCount(); i++) {
                    boolean check = Boolean.parseBoolean((String.valueOf(table.getModel().getValueAt(i, 4))));
                    if (check) {
                        int materialId = Integer.parseInt(String.valueOf(table.getModel().getValueAt(i, 0)));
                        int count = Integer.parseInt(String.valueOf(table.getModel().getValueAt(i, 3)));
                        materials.put(materialDAO.show(materialId), count);
                    }
                }

                String type = typeField.getText();
                int floorsCount = Integer.parseInt(floorsField.getText());

                Plan updatedPlan = new Plan(type, floorsCount, materials);
                planDAO.update(id, updatedPlan);
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
