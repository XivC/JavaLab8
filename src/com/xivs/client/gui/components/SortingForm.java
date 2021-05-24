package com.xivs.client.gui.components;

import com.xivs.client.data.DataProvider;
import com.xivs.client.data.SortedWorkersDataProvider;
import com.xivs.common.Utils.WorkerContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.xivs.client.Application.APP;

public class SortingForm extends JPanel {
    ResourceBundle res = APP.getResources();
    public SortingForm(SortedWorkersDataProvider provider, Dimension size){
        super();
        ActionListener sortActionListener = e -> {
            JComboBox<String> box = (JComboBox<String>)e.getSource();
            String item = (String)box.getSelectedItem();

            if(item.equals(res.getString("key")))  {
                    provider.setSortField("key");
                    provider.sort();
                }
            if(item.equals(res.getString("owner"))) {
                    provider.setSortField("owner");
                    provider.sort();
                }
            if(item.equals(res.getString("name"))) {
                    provider.setSortField("name");
                    provider.sort();
                }
            if(item.equals(res.getString("id"))) {
                    provider.setSortField("id");
                    provider.sort();
                }
            if(item.equals(res.getString("creation_date")))  {
                    provider.setSortField("creationDate");
                    provider.sort();
                }
            if(item.equals(res.getString("salary")))  {
                    provider.setSortField("salary");
                    provider.sort();
                }
            if(item.equals(res.getString("end_date")))  {
                    provider.setSortField("endDate");
                    provider.sort();
                }
            if(item.equals(res.getString("status")))  {
                    provider.setSortField("status");
                    provider.sort();
                }
            if(item.equals(res.getString("position")))  {
                    provider.setSortField("position");
                    provider.sort();
                }
            if(item.equals(res.getString("x")))  {
                    provider.setSortField("x");
                    provider.sort();
                }
            if(item.equals(res.getString("y")))  {
                    provider.setSortField("y");
                    provider.sort();
                }
            if(item.equals(res.getString("address")))  {
                    provider.setSortField("street");
                    provider.sort();
                }
            if(item.equals(res.getString("zip_code")))  {
                    provider.setSortField("zipCode");
                    provider.sort();
                }
            if(item.equals(res.getString("organization_type")))  {
                    provider.setSortField("type");
                    provider.sort();
                }
            if(item.equals(res.getString("annual_turnover")))  {
                    provider.setSortField("annualTurnover");
                    provider.sort();
                }


        };
        ActionListener modeActionListener = e -> {
            JComboBox<String> box = (JComboBox<String>)e.getSource();
            String item = (String)box.getSelectedItem();

                if(item.equals(res.getString("descending"))) {provider.setSortMode(SortedWorkersDataProvider.DESCENDING); provider.sort();}
                if(item.equals(res.getString("ascending")))  {provider.setSortMode(SortedWorkersDataProvider.ASCENDING); provider.sort();}



        };

        setPreferredSize(size);
        GridLayout layout = new GridLayout(1, 4, 10, 12);
        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"",res.getString("key"), res.getString("owner"), res.getString("id"), res.getString("name"), res.getString("creation_date"), res.getString("salary"), res.getString("end_date"), res.getString("status"), res.getString("position"), res.getString("x"), res.getString("y"), res.getString("address"), res.getString("zip_code"), res.getString("organization_type"), res.getString("annual_turnover")});
        JComboBox<String> modeComboBox = new JComboBox<>(new String[]{"",res.getString("descending"), res.getString("ascending")});
        sortComboBox.addActionListener(sortActionListener);
        modeComboBox.addActionListener(modeActionListener);
        setLayout(layout);
        add(new JLabel(res.getString("sort_by"), SwingConstants.RIGHT));
        add(sortComboBox);
        add(new JLabel(res.getString("in_order"), SwingConstants.RIGHT));
        add(modeComboBox);


        //setVisible(true);
    }
}
