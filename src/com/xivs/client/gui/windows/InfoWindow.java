package com.xivs.client.gui.windows;

import com.xivs.client.data.Client;
import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.lab.Worker;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static com.xivs.client.Application.APP;

public class InfoWindow extends JFrame {
    JLabel messageLabel;


    private void setMessage(String message, Color color){
        messageLabel.setText(message);
        messageLabel.setForeground(color);
    }

    public InfoWindow(){
        super();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        Client client = APP.client;
        client.addConnectionLostEvent(()->setMessage("Потеряно соединение с сервером", Color.red));
        client.addConnectionRestoredEvent(()->setMessage("Соединение восстановлено", Color.green));
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        setPreferredSize(new Dimension(800, 200));
        setResizable(false);

        messageLabel = new JLabel();
        contentPane.add(messageLabel);
        JPanel form = new JPanel(new GridLayout(3,2,5,12));
        JLabel creationTimeLabel = new JLabel("Дата и время создания");
        JLabel itemsCountLabel = new JLabel("Кол-во элементов");
        JLabel typeLabel = new JLabel("Тип коллекции");
        JLabel creationTime = new JLabel();
        JLabel itemsCount = new JLabel();
        JLabel type = new JLabel();

        Request rq = new Request("info", new HashMap<>());
        if(client.sendRequest(rq)){
            Response resp = client.receive();
            creationTime.setText(resp.attachments.get("creationDate").get().toString());
            itemsCount.setText(resp.messages.get(0));
            type.setText(resp.messages.get(1));
        }
        form.add(creationTimeLabel);
        form.add(creationTime);
        form.add(itemsCountLabel);
        form.add(itemsCount);
        form.add(typeLabel);
        form.add(type);
        contentPane.add(form);
        pack();
        setVisible(true);

    }
}
