package com.xivs.client.gui.components;

import com.xivs.client.data.Client;
import com.xivs.client.data.DataProvider;
import com.xivs.client.data.WorkersDataProvider;
import com.xivs.client.gui.windows.CreateUpdateObjectWindow;
import com.xivs.common.Utils.WorkerContainer;
import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.lab.Worker;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import static com.xivs.client.Application.APP;

public class WorkersTable extends JPanel {
    ResourceBundle res = APP.getResources();
    private JTable workersTable;
    private final DefaultTableModel workersTableModel;
    private String[] columnHeaders = new String[]{res.getString("key"), res.getString("id"), res.getString("owner"), res.getString("name"),res.getString("creation_date"), res.getString("salary"), res.getString("end_date"), res.getString("status"), res.getString("position"), res.getString("x"), res.getString("y"), res.getString("address"), res.getString("zip_code"), res.getString("organization_type"), res.getString("annual_turnover")};
    private final DataProvider<ArrayList<WorkerContainer>> provider;
    private void setWidth(){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        workersTable.getColumnModel().getColumn(0).setMinWidth(50);
        workersTable.getColumnModel().getColumn(1).setMinWidth(75);
        workersTable.getColumnModel().getColumn(2).setMinWidth(75);
        workersTable.getColumnModel().getColumn(3).setMinWidth(100);
        workersTable.getColumnModel().getColumn(4).setMinWidth(100);
        workersTable.getColumnModel().getColumn(5).setMinWidth(50);
        workersTable.getColumnModel().getColumn(6).setMinWidth(175);
        workersTable.getColumnModel().getColumn(7).setMinWidth(175);
        workersTable.getColumnModel().getColumn(8).setMinWidth(100);
        workersTable.getColumnModel().getColumn(9).setMinWidth(75);
        workersTable.getColumnModel().getColumn(10).setMinWidth(75);
        workersTable.getColumnModel().getColumn(11).setMinWidth(200);
        workersTable.getColumnModel().getColumn(12).setMinWidth(150);
        workersTable.getColumnModel().getColumn(13).setMinWidth(100);
        workersTable.getColumnModel().getColumn(14).setMinWidth(175);
        for(int x=0;x<workersTable.getColumnModel().getColumnCount();x++){
           workersTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        }

    }
    public synchronized void redraw(){

        ArrayList<WorkerContainer> containers = provider.getData();
        this.workersTableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", APP.getLocale());
        NumberFormat nf = NumberFormat.getInstance(APP.getLocale());

        for (WorkerContainer c: containers){
            workersTableModel.addRow(new Object[] { c.key, c.id, c.owner, c.name, c.creationDate.format(formatter), nf.format(c.salary), c.endDate.format(formatter), c.status, c.position, nf.format(c.x), nf.format(c.y), c.street, c.zipCode, c.type, c.annualTurnover});
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
        setWidth();
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
