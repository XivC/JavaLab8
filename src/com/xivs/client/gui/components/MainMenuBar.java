package com.xivs.client.gui.components;


import com.xivs.client.gui.windows.*;
import com.xivs.client.interpreter.Interpreter;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.io.FileInputManager;
import com.xivs.common.io.VoidOutputManager;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

import static com.xivs.client.Application.APP;
import static com.xivs.client.gui.windows.CreateUpdateObjectWindow.CREATE;
import static javax.swing.JFileChooser.APPROVE_OPTION;

public class MainMenuBar extends JMenuBar {
    ResourceBundle res = APP.getResources();
    public MainMenuBar(MainWindow window){
        super();
        JMenu instruments = new JMenu(res.getString("instruments"));
        JMenuItem removeAllByPosition = new JMenuItem(res.getString("remove_all_by_position"));
        JMenuItem createWorker = new JMenuItem(res.getString("create_worker"));
        JMenuItem executeScript = new JMenuItem(res.getString("execute_script"));
        JMenuItem removeGreater = new JMenuItem(res.getString("remove_greater"));
        JMenuItem clear = new JMenuItem(res.getString("clear"));
        JMenu server = new JMenu(res.getString("server"));
        JMenuItem info = new JMenuItem (res.getString("information"));
        JMenuItem exit = new JMenuItem (res.getString("disconnect"));
        JMenu visual = new JMenu(res.getString("visualization"));
        JMenuItem table = new JMenuItem(res.getString("table"));
        JMenuItem graphics = new JMenuItem(res.getString("graphics"));
        createWorker.addActionListener((e)-> new CreateUpdateObjectWindow(CREATE, null));
        removeAllByPosition.addActionListener((e)->new RemoveAllByPositionWindow());
        executeScript.addActionListener((e)->{
            JFileChooser file = new JFileChooser();
            int state = file.showOpenDialog(null);
            file.setFileSelectionMode(JFileChooser.FILES_ONLY);
            File f = file.getSelectedFile();
            try {
                Interpreter exe_inter = new Interpreter(new FileInputManager(f), new VoidOutputManager(), APP.client);
                new Thread(exe_inter::run).start();
                String message = res.getString("script_executing");
                JOptionPane.showMessageDialog(new JFrame(), message, "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                String message = res.getString("script_error");
                JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        clear.addActionListener((e)-> {
            Request rq = new Request("clear", new HashMap<>());
            APP.client.sendRequest(rq);
            Response resp = APP.client.receive();
            JOptionPane.showMessageDialog(new JFrame(), resp.messages.get(0));
        });
        removeGreater.addActionListener((e)->new RemoveGreaterWindow());
        info.addActionListener((e)->new InfoWindow());
        exit.addActionListener((e)-> {
            for (Window w: Window.getWindows()){
                w.dispose();
            }
            APP.client.disconnect();
            new LoginWindow(new Dimension(500, 300), APP.getLocale());
        });
        table.addActionListener((e)->window.setVisualization(1));
        graphics.addActionListener((e)->window.setVisualization(2));
        instruments.add(removeAllByPosition);
        instruments.add(removeGreater);
        instruments.add(createWorker);
        instruments.add(executeScript);
        instruments.add(clear);
        server.add(info);
        server.add(exit);
        visual.add(table);
        visual.add(graphics);
        add(instruments);
        add(server);
        add(visual);




    }
}
