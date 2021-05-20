package com.xivs.client.gui.components;

import com.xivs.client.data.Client;
import com.xivs.client.data.DataProvider;
import com.xivs.client.data.WorkersDataProvider;
import com.xivs.client.gui.windows.CreateUpdateObjectWindow;
import com.xivs.common.Utils.WorkerContainer;
import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.lab.Worker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkersTable extends JPanel {
    private JTable workersTable;
    private final DefaultTableModel workersTableModel;
    private String[] columnHeaders = new String[]{"Ключ", "id", "Владелец", "Имя рабочего", "Дата создания", "Зарплата", "Дата окончания контракта", "Статус", "Должность", "X", "Y", "Адрес организации", "Почтовый индекс организации", "Тип организации", "Годовой оборот организации"};
    private final DataProvider<ArrayList<WorkerContainer>> provider;
    public synchronized void redraw(){

        ArrayList<WorkerContainer> containers = provider.getData();
        this.workersTableModel.setRowCount(0);
        for (WorkerContainer c: containers){
            workersTableModel.addRow(new Object[] { c.key, c.id, c.owner, c.name, c.creationDate, c.salary, c.endDate, c.status, c.position, c.x, c.y, c.street, c.zipCode, c.type, c.annualTurnover});
            }
        this.workersTableModel.fireTableDataChanged();

    }
    public WorkersTable(DataProvider<ArrayList<WorkerContainer>> provider, Dimension size){
        super();
        this.provider = provider;
        this.provider.addUpdateEvent(this::redraw);
        workersTableModel = new DefaultTableModel();
        workersTableModel.setColumnIdentifiers(columnHeaders);
        this.workersTable = new JTable(workersTableModel){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        JScrollPane scrollPane = new JScrollPane(this.workersTable);
        this.workersTable.setFillsViewportHeight(true);
        this.workersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.workersTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.workersTable.setRowHeight(50);
        this.workersTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    String key = (String)workersTableModel.getValueAt(row, 0);

                    new CreateUpdateObjectWindow(CreateUpdateObjectWindow.UPDATE, key);
                }
            }
        });

        scrollPane.setPreferredSize(new Dimension(size));
        add(scrollPane);
        setSize(size);

        setVisible(true);



    }
}
