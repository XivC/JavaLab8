package com.xivs.client.gui.components;

import com.xivs.client.data.DataProvider;
import com.xivs.client.data.SortedWorkersDataProvider;
import com.xivs.common.Utils.WorkerContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SortingForm extends JPanel {

    public SortingForm(SortedWorkersDataProvider provider, Dimension size){
        super();
        ActionListener sortActionListener = e -> {
            JComboBox<String> box = (JComboBox<String>)e.getSource();
            String item = (String)box.getSelectedItem();
            switch (item) {
                case "Ключу" -> {
                    provider.setSortField("key");
                    provider.sort();
                }
                case "Владельцу" -> {
                    provider.setSortField("owner");
                    provider.sort();
                }
                case "Имени рабочего" -> {
                    provider.setSortField("name");
                    provider.sort();
                }
                case "id" -> {
                    provider.setSortField("id");
                    provider.sort();
                }
                case "Дате создания" -> {
                    provider.setSortField("creationDate");
                    provider.sort();
                }
                case "Зарплате" -> {
                    provider.setSortField("salary");
                    provider.sort();
                }
                case "Дате окончания контракта" -> {
                    provider.setSortField("endDate");
                    provider.sort();
                }
                case "Статусу" -> {
                    provider.setSortField("status");
                    provider.sort();
                }
                case "Должности" -> {
                    provider.setSortField("position");
                    provider.sort();
                }
                case "X" -> {
                    provider.setSortField("x");
                    provider.sort();
                }
                case "Y" -> {
                    provider.setSortField("y");
                    provider.sort();
                }
                case "Адресу организации" -> {
                    provider.setSortField("street");
                    provider.sort();
                }
                case "Почтовому индексу организации" -> {
                    provider.setSortField("zipCode");
                    provider.sort();
                }
                case "Типу организации" -> {
                    provider.setSortField("type");
                    provider.sort();
                }
                case "Годовому обороту организации" -> {
                    provider.setSortField("annualTurnover");
                    provider.sort();
                }
            }

        };
        ActionListener modeActionListener = e -> {
            JComboBox<String> box = (JComboBox<String>)e.getSource();
            String item = (String)box.getSelectedItem();
            switch(item){
                case "Убывания": provider.setSortMode(SortedWorkersDataProvider.DESCENDING); provider.sort();break;
                case "Возрастания": provider.setSortMode(SortedWorkersDataProvider.ASCENDING); provider.sort();break;

            }

        };

        setPreferredSize(size);
        GridLayout layout = new GridLayout(1, 4, 10, 12);
        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"","Ключу", "Владельцу", "id", "Имени рабочего", "Дате создания", "Зарплате", "Дате окончания контракта", "Статусу", "Должности", "X", "Y", "Адресу организации", "Почтовому индексу организации", "Типу организации", "Годовому обороту организации"});
        JComboBox<String> modeComboBox = new JComboBox<>(new String[]{"","Убывания", "Возрастания"});
        sortComboBox.addActionListener(sortActionListener);
        modeComboBox.addActionListener(modeActionListener);
        setLayout(layout);
        add(new JLabel("Сортировать по ", SwingConstants.RIGHT));
        add(sortComboBox);
        add(new JLabel("в порядке ", SwingConstants.RIGHT));
        add(modeComboBox);


        //setVisible(true);
    }
}
