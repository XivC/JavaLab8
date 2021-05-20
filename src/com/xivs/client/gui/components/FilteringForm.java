package com.xivs.client.gui.components;

import com.xivs.client.data.FilteredWorkersDataProvider;
import com.xivs.client.data.SortedWorkersDataProvider;
import com.xivs.client.gui.custom.HintTextField;
import com.xivs.common.Utils.Parser.*;
import com.xivs.common.Utils.WorkerContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Predicate;

public class FilteringForm extends JPanel {
    private final FilteredWorkersDataProvider provider;
    JPanel cards;
    JTextField lowerBoundField;
    JTextField upperBoundField;
    JTextField containsField;
    Predicate<WorkerContainer> filter = (p)->true;

    public FilteringForm(FilteredWorkersDataProvider provider, Dimension size){
        super();
        ActionListener comboActionListener = e -> {
            try {
                JComboBox<String> box = (JComboBox<String>) e.getSource();
                String item = (String) box.getSelectedItem();
                switch (Objects.requireNonNull(item)) {
                    case "Ключу" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> p.key.contains(containsField.getText());
                    }
                    case "Владельцу" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> p.owner.contains(containsField.getText());
                    }
                    case "Имени рабочего" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> p.name.contains(containsField.getText());
                    }
                    case "id" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> String.valueOf(p.id).contains(containsField.getText());
                    }
                    case "Дате создания" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "lowup");
                        this.filter = (p) -> p.creationDate.isAfter(LocalDateParser.parse(lowerBoundField.getText(), LocalDate.MIN)) && p.creationDate.isBefore(LocalDateParser.parse(upperBoundField.getText(), LocalDate.MAX));
                    }
                    case "Зарплате" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "lowup");
                        this.filter = (p) -> FloatParser.parse(lowerBoundField.getText(), Float.MIN_VALUE) <= p.salary && p.salary <= FloatParser.parse(upperBoundField.getText(), Float.MAX_VALUE);
                    }
                    case "Дате окончания контракта" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "lowup");
                        this.filter = (p) -> p.endDate.isAfter(LocalDateParser.parse(lowerBoundField.getText(), LocalDate.MIN)) && p.endDate.isBefore(LocalDateParser.parse(upperBoundField.getText(), LocalDate.MAX));
                    }
                    case "Статусу" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> String.valueOf(p.status).contains(containsField.getText());
                    }
                    case "Должности" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> String.valueOf(p.position).contains(containsField.getText());
                    }
                    case "X" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "lowup");
                        this.filter = (p) -> LongParser.parse(lowerBoundField.getText(), Long.MIN_VALUE) <= p.x && p.x <= LongParser.parse(upperBoundField.getText(), Long.MAX_VALUE);
                    }
                    case "Y" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "lowup");
                        this.filter = (p) -> DoubleParser.parse(lowerBoundField.getText(), Double.MIN_VALUE) <= p.y && p.y <= DoubleParser.parse(upperBoundField.getText(), Double.MAX_VALUE);
                    }
                    case "Адресу организации" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> p.street.contains(containsField.getText());
                    }
                    case "Почтовому индексу организации" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> p.zipCode.contains(containsField.getText());
                    }
                    case "Типу организации" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> String.valueOf(p.type).contains(containsField.getText());
                    }
                    case "Годовому обороту организации" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "lowup");
                        this.filter = (p) -> IntegerParser.parse(lowerBoundField.getText(), Integer.MIN_VALUE) <= p.annualTurnover && p.annualTurnover <= IntegerParser.parse(upperBoundField.getText(), Integer.MAX_VALUE);
                    }
                    case "Не фильтровать" -> {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> true;
                    }
                }
            }
            catch (NullPointerException ex) {return;}

        };
        ActionListener buttonActionListener = e->{
            provider.setFilter(filter);
            provider.filter();
        };
        setPreferredSize(size);
        this.provider = provider;
        GridLayout layout = new GridLayout(1, 4, 5, 12);
        setLayout(layout);
        JComboBox<String> filterComboBox = new JComboBox<>(new String[]{"Не фильтровать","Ключу", "Владельцу", "id", "Имени рабочего", "Дате создания", "Зарплате", "Дате окончания контракта", "Статусу", "Должности", "X", "Y", "Адресу организации", "Почтовому индексу организации", "Типу организации", "Годовому обороту организации"});
        lowerBoundField = new HintTextField("Нижняя граница");
        upperBoundField = new HintTextField("Верхняя граница");
        containsField = new HintTextField("Содержит");
        filterComboBox.addActionListener(comboActionListener);
        JButton button = new JButton("Фильтровать");
        button.addActionListener(buttonActionListener);
        cards = new JPanel(new CardLayout());
        JPanel lowUpCard = new JPanel(new GridLayout(1, 2, 10, 12));
        lowUpCard.add(lowerBoundField);
        lowUpCard.add(upperBoundField);
        cards.add(lowUpCard, "lowup");
        cards.add(containsField, "contains");
        add(new JLabel("Фильтровать по ", SwingConstants.RIGHT));
        add(filterComboBox);
        add(cards);
        add(button);





        //setVisible(true);
    }
}
