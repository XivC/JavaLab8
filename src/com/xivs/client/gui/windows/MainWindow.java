package com.xivs.client.gui.windows;

import com.xivs.client.data.Client;
import com.xivs.client.data.FilteredWorkersDataProvider;
import com.xivs.client.data.SortedWorkersDataProvider;
import com.xivs.client.data.WorkersDataProvider;
import com.xivs.client.gui.components.FilteringForm;
import com.xivs.client.gui.components.MainMenuBar;
import com.xivs.client.gui.components.SortingForm;
import com.xivs.client.gui.components.WorkersTable;
import com.xivs.client.interpreter.Interpreter;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.io.CommandLineInputManager;
import com.xivs.common.io.CommandLineOutManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static com.xivs.client.Application.APP;


public class MainWindow extends JFrame {
    Client client;
    public void start(){
        setVisible(true);
        Interpreter interpreter = new Interpreter(new CommandLineInputManager(), new CommandLineOutManager(), client);
        new Thread(interpreter::run).start();
        client.sendRequest(new Request("show", new HashMap<>()));
        client.receive();
    }
    public void close(){
        setVisible(false);
    }
    public MainWindow(){
        client = APP.client;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1280,720));
        FilteredWorkersDataProvider filter = new FilteredWorkersDataProvider(new WorkersDataProvider(client));
        SortedWorkersDataProvider sorted = new SortedWorkersDataProvider(filter);
        setJMenuBar(new MainMenuBar());
        JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Container contentPane = getContentPane();
        //flow.setLayout(new BoxLayout(flow, BoxLayout.Y_AXIS));
        flow.add(new WorkersTable(sorted, new Dimension(1000, 500)));
        flow.add(new SortingForm(sorted, new Dimension(420, 30)));
        flow.add(new FilteringForm(filter, new Dimension(900, 30)));
        contentPane.add(flow);
        pack();



    }
}
