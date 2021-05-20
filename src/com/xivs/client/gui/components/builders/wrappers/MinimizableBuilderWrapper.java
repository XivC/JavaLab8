package com.xivs.client.gui.components.builders.wrappers;


import com.xivs.client.gui.components.builders.Builder;
import com.xivs.client.gui.components.builders.BuilderForm;

import javax.swing.*;
import java.awt.*;

public class MinimizableBuilderWrapper<T> extends JPanel implements Builder<T>  {
    T defaultValue;
    BuilderForm<T> wrap;
    boolean isMinimized;
    Dimension maxSize;
    Dimension selfSize;
    JLabel headerText;

    @Override
    public T build(){
        return isMinimized ? defaultValue:wrap.build();
    }

    public void changeState(boolean state){

        this.wrap.setVisible(state);
        this.isMinimized = !state;
        if(isMinimized) setSize(selfSize);
        else setSize(maxSize);    }

    public void setHeaderFont(Font font){
        headerText.setFont(font);
    }
    public MinimizableBuilderWrapper(BuilderForm<T> wrap, T defaultValue, String text, Dimension selfSize, boolean isMinimizable){
        super();
        this.defaultValue = defaultValue;
        this.wrap = wrap;
        this.maxSize = new Dimension(Math.max(wrap.getPreferredSize().width, selfSize.width), wrap.getPreferredSize().height + selfSize.height);
        this.selfSize = selfSize;
        this.headerText = new JLabel(text);
        JCheckBox minimizeCheckBox = new JCheckBox();
        minimizeCheckBox.setEnabled(isMinimizable);
        minimizeCheckBox.addActionListener((e)-> {
            JCheckBox box = (JCheckBox)e.getSource();
            changeState(box.isSelected());
        });
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setLayout(new FlowLayout());

        changeState(true);
        minimizeCheckBox.setSelected(true);
        JPanel headerPanel = new JPanel(new GridLayout(1, 2, 5, 12));
        headerPanel.add(headerText);
        headerPanel.add(minimizeCheckBox);
        wrap.setAlignmentX(CENTER_ALIGNMENT);
        add(headerPanel);
        add(wrap);



    }
}
