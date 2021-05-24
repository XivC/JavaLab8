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
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static com.xivs.client.Application.APP;

public class FilteringForm extends JPanel {
    private final FilteredWorkersDataProvider provider;
    JPanel cards;
    JTextField lowerBoundField;
    JTextField upperBoundField;
    JTextField containsField;
    Predicate<WorkerContainer> filter = (p)->true;
    ResourceBundle res = APP.getResources();
    public FilteringForm(FilteredWorkersDataProvider provider, Dimension size){
        super();
        ActionListener comboActionListener = e -> {
            try {
                JComboBox<String> box = (JComboBox<String>) e.getSource();
                String item = (String) box.getSelectedItem();

                new JComboBox<>(new String[]{res.getString("no_filter"),res.getString("key"), res.getString("owner"), res.getString("id"), res.getString("name"), res.getString("creation_date"), res.getString("salary"), res.getString("end_date"), res.getString("status"), res.getString("position"), res.getString("x"), res.getString("y"), res.getString("address"), res.getString("zip_code"), res.getString("organization_type"), res.getString("annual_turnover")});

                assert item != null;
                if (item.equals(res.getString("key")))  {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> p.key.contains(containsField.getText());
                    }
                if (item.equals(res.getString("owner"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> p.owner.contains(containsField.getText());
                    }
                if (item.equals(res.getString("name"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> p.name.contains(containsField.getText());
                    }
                if (item.equals(res.getString("id"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> String.valueOf(p.id).contains(containsField.getText());
                    }
                if (item.equals(res.getString("creation_date")))  {
                        ((CardLayout) this.cards.getLayout()).show(cards, "lowup");
                        this.filter = (p) -> p.creationDate.isAfter(LocalDateParser.parse(lowerBoundField.getText(), LocalDate.MIN)) && p.creationDate.isBefore(LocalDateParser.parse(upperBoundField.getText(), LocalDate.MAX));
                    }
                if (item.equals(res.getString("salary"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "lowup");
                        this.filter = (p) -> FloatParser.parse(lowerBoundField.getText(), Float.MIN_VALUE) <= p.salary && p.salary <= FloatParser.parse(upperBoundField.getText(), Float.MAX_VALUE);
                    }
                if (item.equals(res.getString("end_date"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "lowup");
                        this.filter = (p) -> p.endDate.isAfter(LocalDateParser.parse(lowerBoundField.getText(), LocalDate.MIN)) && p.endDate.isBefore(LocalDateParser.parse(upperBoundField.getText(), LocalDate.MAX));
                    }
                if (item.equals(res.getString("status"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> String.valueOf(p.status).contains(containsField.getText());
                    }
                if (item.equals(res.getString("position"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> String.valueOf(p.position).contains(containsField.getText());
                    }
                if (item.equals(res.getString("x"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "lowup");
                        this.filter = (p) -> LongParser.parse(lowerBoundField.getText(), Long.MIN_VALUE) <= p.x && p.x <= LongParser.parse(upperBoundField.getText(), Long.MAX_VALUE);
                    }
                if (item.equals(res.getString("y"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "lowup");
                        this.filter = (p) -> DoubleParser.parse(lowerBoundField.getText(), Double.MIN_VALUE) <= p.y && p.y <= DoubleParser.parse(upperBoundField.getText(), Double.MAX_VALUE);
                    }
                if (item.equals(res.getString("address"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> p.street.contains(containsField.getText());
                    }
                if (item.equals(res.getString("zip_code"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> p.zipCode.contains(containsField.getText());
                    }
                if (item.equals(res.getString("organization_type"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> String.valueOf(p.type).contains(containsField.getText());
                    }
                if (item.equals(res.getString("annual_turnover"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "lowup");
                        this.filter = (p) -> IntegerParser.parse(lowerBoundField.getText(), Integer.MIN_VALUE) <= p.annualTurnover && p.annualTurnover <= IntegerParser.parse(upperBoundField.getText(), Integer.MAX_VALUE);
                    }
                if (item.equals(res.getString("no_filter"))) {
                        ((CardLayout) this.cards.getLayout()).show(cards, "contains");
                        this.filter = (p) -> true;
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
        JComboBox<String> filterComboBox = new JComboBox<>(new String[]{res.getString("no_filter"),res.getString("key"), res.getString("owner"), res.getString("id"), res.getString("name"), res.getString("creation_date"), res.getString("salary"), res.getString("end_date"), res.getString("status"), res.getString("position"), res.getString("x"), res.getString("y"), res.getString("address"), res.getString("zip_code"), res.getString("organization_type"), res.getString("annual_turnover")});
        lowerBoundField = new HintTextField(res.getString("lower_bound"));
        upperBoundField = new HintTextField(res.getString("upper_bound"));
        containsField = new HintTextField(res.getString("contains"));
        filterComboBox.addActionListener(comboActionListener);
        JButton button = new JButton(res.getString("filter"));
        button.addActionListener(buttonActionListener);
        cards = new JPanel(new CardLayout());
        JPanel lowUpCard = new JPanel(new GridLayout(1, 2, 10, 12));
        lowUpCard.add(lowerBoundField);
        lowUpCard.add(upperBoundField);
        cards.add(lowUpCard, "lowup");
        cards.add(containsField, "contains");
        add(new JLabel(res.getString("filter_by"), SwingConstants.RIGHT));
        add(filterComboBox);
        add(cards);
        add(button);





        //setVisible(true);
    }
}
