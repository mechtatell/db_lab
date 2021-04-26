package ru.mechtatell.Views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.Interfaces.MaterialDAO;
import ru.mechtatell.DAO.Interfaces.PlanDAO;
import ru.mechtatell.Models.Material;
import ru.mechtatell.Models.MaterialPlan;
import ru.mechtatell.Models.Plan;
import ru.mechtatell.Views.Util.CRUDLogic;
import ru.mechtatell.Views.Util.Frame;
import ru.mechtatell.Views.Util.components.TableMaterial;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlanFrame extends Frame {
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
        String NAME = "Планы";
        frame = super.init(NAME, 535, 500);
        refreshTable();
        super.addNavigationButtons(NAME, new CRUDLogic() {
            @Override
            public void create() {
                createPlan();
            }

            @Override
            public void update() {
                updatePlan();
            }

            @Override
            public void remove() {
                removePlan();
            }
        });

        frame.repaint();
        frame.setVisible(true);
    }

    private void refreshTable() {
        List<Plan> plans = planDAO.findAll();
        Object[][] data = new String[plans.size()][4];

        for (int i = 0; i < data.length; i++) {
            data[i][0] = String.valueOf(plans.get(i).getId());
            data[i][1] = plans.get(i).getConstructionType();
            data[i][2] = String.valueOf(plans.get(i).getFloorsCount());
            data[i][3] = String.valueOf(plans.get(i).getMaterialPlanList().size());
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
        for (Material material : materialDAO.findAll()) {
            boolean check = false;
            int count = 0;
            if (plan != null && plan.getMaterialPlanList() != null) {
                check = plan.getMaterialPlanList().stream().anyMatch(e -> e.getMaterial().getId() == material.getId());
                if (check) {
                    count = plan.getMaterialPlanList()
                            .stream().filter(e -> e.getMaterial().getId() == material.getId())
                            .findAny().get().getCount();
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

    private void createPlan() {
        JPanel planPanel = getNewPlanPanel(new Plan());
        int result = JOptionPane.showConfirmDialog(frame, planPanel, "Создание плана",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                Plan plan = createPlanModel(planPanel);
                planDAO.save(plan);
                addMaterialPlanList(plan, planPanel);
                refreshTable();
            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                createPlan();
            }
        }
    }

    private void removePlan() {
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
                planDAO.remove(id);
                refreshTable();
                table.clearSelection();
            }
        }
    }

    private void updatePlan() {
        if (table.getSelectedRowCount() != 1) {
            JOptionPane.showConfirmDialog(frame, "Строка выбрана некорректно", "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0)));

        Plan plan;
        if (planDAO.findById(id).isPresent()) {
            plan = planDAO.findById(id).get();
        } else {
            return;
        }

        JPanel planPanel = getNewPlanPanel(plan);
        int result = JOptionPane.showConfirmDialog(frame, planPanel, "Создание бригады",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        try {
            if (result == JOptionPane.OK_OPTION) {
                Plan updatedPlan = createPlanModel(planPanel);
                updatedPlan.setId(id);
                planDAO.save(updatedPlan);
                addMaterialPlanList(updatedPlan, planPanel);
                refreshTable();
            }
        } catch (Exception ex) {
            int resultError = JOptionPane.showConfirmDialog(frame, ex.getMessage(), "Ошибка",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resultError == JOptionPane.OK_OPTION) {
                updatePlan();
            }
        }
    }

    private Plan createPlanModel(JPanel planPanel) throws Exception {
        JTextField typeField = (JTextField) ((JComponent) planPanel.getComponent(0)).getComponent(1);
        if (typeField.getText().isEmpty()) {
            throw new Exception("Некорректный формат типа конструкции");
        }

        JTextField floorsField = (JTextField) ((JComponent) planPanel.getComponent(0)).getComponent(4);
        if (floorsField.getText().isEmpty()) {
            throw new Exception("Некорректный формат количества этажей");
        }

        String type = typeField.getText();
        int floorsCount = Integer.parseInt(floorsField.getText());

        return new Plan(type, floorsCount);
    }

    private void addMaterialPlanList(Plan plan, JPanel planPanel) {
        List<MaterialPlan> materialPlanList = new ArrayList<>();
        JTable table = (JTable) ((JScrollPane) ((JComponent)planPanel.getComponent(1)).getComponent(1)).getViewport().getView();

        for (int i = 0; i < table.getRowCount(); i++) {
            boolean check = Boolean.parseBoolean((String.valueOf(table.getModel().getValueAt(i, 4))));
            if (check) {
                int id = Integer.parseInt(String.valueOf(table.getModel().getValueAt(i, 0)));
                int count = Integer.parseInt(String.valueOf(table.getModel().getValueAt(i, 3)));
                materialPlanList.add(new MaterialPlan(materialDAO.findById(id).get(), plan, count));
            }
        }

        plan.setMaterialPlanList(materialPlanList);
        planDAO.save(plan);
    }
}
