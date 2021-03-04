package ru.mechtatell.views.components;

import ru.mechtatell.models.Position;

import javax.swing.*;

public class ComboBoxPosition extends DefaultComboBoxModel<Position> {

    public ComboBoxPosition(Position[] items) {
        super(items);
    }

    @Override
    public Object getSelectedItem() {
        return super.getSelectedItem() != null ? ((Position) super.getSelectedItem()).getId() : null;
    }
}
