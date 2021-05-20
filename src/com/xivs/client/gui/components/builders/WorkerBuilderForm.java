package com.xivs.client.gui.components.builders;

import com.xivs.client.gui.custom.HintTextField;
import com.xivs.common.Utils.Pair;
import com.xivs.common.lab.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class WorkerBuilderForm extends BuilderForm<Worker> {
    JTextField nameField;
    JTextField salaryField;
    JTextField endDateField;
    JComboBox<Position> positionField;
    JComboBox<Status> statusField;


    public Worker build(){
        if (!Worker.Params.name.parse(nameField.getText())) return null;
        if (!Worker.Params.salary.parse(salaryField.getText())) return null;
        String name = Worker.Params.name.get();
        Float salary = Worker.Params.salary.get();
        LocalDate endDate = LocalDate.MAX;
        if (endDateField.isEnabled()) {
           if( !Worker.Params.endDate.parse(endDateField.getText())) return null;
           endDate = Worker.Params.endDate.get();
        }
        Position position = (Position) positionField.getSelectedItem();
        Status status = Status.NONE;
        if (statusField.isEnabled()) status = (Status) statusField.getSelectedItem();
        return new Worker(name, salary, endDate, status, position, Organization.DEFAULT, Coordinates.DEFAULT);

    }
    @Override
    void initDefaultValues(){
        nameField.setText(defaultValue.name);
        salaryField.setText(String.valueOf(defaultValue.salary));
        endDateField.setText(defaultValue.endDate.toString());
        positionField.setSelectedItem(defaultValue.position);
        statusField.setSelectedItem(defaultValue.status);


    }
    void initFields(){

        nameField = new JTextField();
        salaryField = new JTextField();
        endDateField = new HintTextField("YYYY-MM-DD");
        positionField = initPositionField();
        statusField = initStatusField();
        fields.put("Имя", new Pair<>(nameField, false));
        fields.put("Зарплата", new Pair<>(salaryField, false));
        fields.put("Дата окончания контракта", new Pair<>(endDateField, true));
        fields.put("Должность", new Pair<>(positionField, false));
        fields.put("Статус", new Pair<>(statusField, true));

    }
    private JComboBox<Position> initPositionField(){
        JComboBox<Position> positionField = new JComboBox<>();
        for(Position p: Position.values()){
            if(p.equals(Position.NONE)) continue;
            positionField.addItem(p);
        }
        return positionField;
    }
    private JComboBox<Status> initStatusField(){
        JComboBox<Status> statusField = new JComboBox<>();
        for(Status s: Status.values()){
            statusField.addItem(s);
        }
        return statusField;

    }
    public WorkerBuilderForm(Dimension size, Worker defaultValue){
        super(size, 5, defaultValue);
    }
}
