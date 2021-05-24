package com.xivs.client.gui.windows;

import com.xivs.client.data.Client;
import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.lab.Worker;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.ResourceBundle;

import static com.xivs.client.Application.APP;

public class InfoWindow extends JFrame {
    JLabel messageLabel;

    ResourceBundle res = APP.getResources();
    private void setMessage(String message, Color color){
        messageLabel.setText(message);
        messageLabel.setForeground(color);
    }

    public InfoWindow(){
        super();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        Client client = APP.client;
        client.addConnectionLostEvent(()->setMessage(res.getString("server_lost_connection"), Color.red));
        client.addConnectionRestoredEvent(()->setMessage(res.getString("server_connection_restored"), Color.green));
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        setPreferredSize(new Dimension(800, 200));
        setResizable(false);

        messageLabel = new JLabel();
        contentPane.add(messageLabel);
        JPanel form = new JPanel(new GridLayout(3,2,5,12));
        JLabel creationTimeLabel = new JLabel(res.getString("creation_date_time"));
        JLabel itemsCountLabel = new JLabel(res.getString("items_count"));
        JLabel typeLabel = new JLabel(res.getString("collection_type"));
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
