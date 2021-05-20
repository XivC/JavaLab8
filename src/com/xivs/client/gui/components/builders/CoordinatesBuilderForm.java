package com.xivs.client.gui.components.builders;

import com.xivs.common.Utils.Pair;
import com.xivs.common.lab.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class CoordinatesBuilderForm extends BuilderForm<Coordinates>{
    JTextField xField;
    JTextField yField;
    @Override
    public Coordinates build() {
        if(!Coordinates.Params.x.parse(xField.getText())) return null;
        if(!Coordinates.Params.y.parse(yField.getText())) return null;
        return new Coordinates(Coordinates.Params.x.get(), Coordinates.Params.y.get());

    }
    @Override
    void initDefaultValues(){
        xField.setText(String.valueOf(defaultValue.x));
        yField.setText(String.valueOf(defaultValue.y));
    }
    @Override
    void initFields() {
        xField = new JTextField();
        yField = new JTextField();
        fields.put("X", new Pair<>(xField, false));
        fields.put("Y", new Pair<>(yField, false));

    }
    public CoordinatesBuilderForm(Dimension size, Coordinates defaultValue){
        super(size, 2, defaultValue);
    }
}
