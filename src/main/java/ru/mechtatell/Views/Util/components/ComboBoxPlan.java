package ru.mechtatell.Views.Util.components;

import ru.mechtatell.Models.Plan;

import javax.swing.*;

public class ComboBoxPlan extends DefaultComboBoxModel<Plan> {

    public ComboBoxPlan(Plan[] items) {
        super(items);
    }

    @Override
    public Object getSelectedItem() {
        return super.getSelectedItem() != null ? ((Plan) super.getSelectedItem()) : null;
    }
}
