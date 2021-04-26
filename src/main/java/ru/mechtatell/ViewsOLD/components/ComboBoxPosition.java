package ru.mechtatell.ViewsOLD.components;

import ru.mechtatell.Models.Position;

import javax.swing.*;

public class ComboBoxPosition extends DefaultComboBoxModel<Position> {

    public ComboBoxPosition(Position[] items) {
        super(items);
    }

    @Override
    public Object getSelectedItem() {
        return super.getSelectedItem() != null ? ((Position) super.getSelectedItem()) : null;
    }
}
