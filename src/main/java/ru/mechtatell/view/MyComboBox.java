package ru.mechtatell.view;

import ru.mechtatell.models.Position;

import javax.swing.*;

public class MyComboBox extends DefaultComboBoxModel<Position> {

    public MyComboBox(Position[] items) {
        super(items);
    }

    @Override
    public Object getSelectedItem() {
        return ((Position) super.getSelectedItem()).getId();
    }
}
