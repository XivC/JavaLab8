package com.xivs.client.gui.components.builders;

import com.xivs.common.Utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class BuilderForm<T> extends JPanel implements Builder<T> {
    T defaultValue;

    public abstract T build();
    abstract void initFields();
    void initDefaultValues(){};

    HashMap<String, Pair<JComponent, Boolean>> fields;
    private void init(Dimension size, int rows){
        setPreferredSize(size);
        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(rows, 3, 5, 12));
        grid.setPreferredSize(size);//new Dimension((int)(size.width*0.8), (int)(size.height*0.7)));
        fields = new HashMap<>();

        initFields();
        gridFields(grid, fields);
        container.add(grid);
        add(container);
        if(defaultValue != null) initDefaultValues();

    }
    public BuilderForm(Dimension size, int rows, T defaultValue){
        super();
        this.defaultValue = defaultValue;
        init(size, rows);
    }
    public BuilderForm(Dimension size, int rows){

        super();
        this.defaultValue = null;
        init(size, rows);

    }

    void gridFields(JPanel grid, HashMap<String, Pair<JComponent, Boolean>> fields){
        for (String key: fields.keySet()){
            JComponent field = fields.get(key).get1();
            JCheckBox active = new JCheckBox();
            active.setEnabled(fields.get(key).get2());
            active.addActionListener((e)->{
                JCheckBox box = (JCheckBox) e.getSource();
                field.setEnabled(!box.isSelected());
            });
            grid.add(new JLabel(key));
            grid.add(field);
            grid.add(active);

        }
    }
}
