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

import static com.xivs.client.Application.APP;
import static com.xivs.client.gui.windows.CreateUpdateObjectWindow.CREATE;
import static javax.swing.JFileChooser.APPROVE_OPTION;

public class MainMenuBar extends JMenuBar {

    public MainMenuBar(MainWindow window){
        super();
        JMenu instruments = new JMenu("Инструменты");
        JMenuItem removeAllByPosition = new JMenuItem("Удалить всех опр. должности");
        JMenuItem createWorker = new JMenuItem("Создать рабочего");
        JMenuItem executeScript = new JMenuItem("Выполнить скрипт");
        JMenuItem removeGreater = new JMenuItem("Удалть всех с большей ЗП");
        JMenuItem clear = new JMenuItem("Очистить");
        JMenu server = new JMenu("Сервер");
        JMenuItem info = new JMenuItem ("Информация");
        JMenuItem exit = new JMenuItem ("Отключиться");
        JMenu visual = new JMenu("Визуализация");
        JMenuItem table = new JMenuItem("Таблица");
        JMenuItem graphics = new JMenuItem("Графика");
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
                String message = "Скрипт поставлен на исполнение";
                JOptionPane.showMessageDialog(new JFrame(), message, "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                String message = "Скрипт или файл сломался :(";
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

            new LoginWindow(new Dimension(500, 300));
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
