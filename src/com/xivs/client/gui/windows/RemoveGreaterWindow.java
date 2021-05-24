package com.xivs.client.gui.windows;

import com.xivs.client.data.Client;
import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.lab.Position;
import com.xivs.common.lab.Worker;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.ResourceBundle;

import static com.xivs.client.Application.APP;

public class RemoveGreaterWindow extends JFrame {
    JLabel messageLabel;
    JTextField salaryField;
    ResourceBundle res = APP.getResources();
    private void setMessage(String message, Color color){
        messageLabel.setText(message);
        messageLabel.setForeground(color);
    }

    public RemoveGreaterWindow(){
        super();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        Client client = APP.client;
        client.addConnectionLostEvent(()->setMessage(res.getString("server_lost_connection"), Color.red));
        client.addConnectionRestoredEvent(()->setMessage(res.getString("server_connection_restored"), Color.green));
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        setPreferredSize(new Dimension(300, 200));
        setResizable(false);
        messageLabel = new JLabel();
        salaryField = new JTextField();
        salaryField.setPreferredSize(new Dimension(250, 50));
        JButton delete = new JButton(res.getString("delete"));

        delete.setPreferredSize(new Dimension(250, 50));
        delete.addActionListener((e)->
        {
            if (!Worker.Params.salary.parse(salaryField.getText())){
                setMessage(res.getString("field_filling_error"), Color.RED);
                return;
            }
            HashMap<String, DataTransference<?>> arguments = new HashMap<>();
            arguments.put("salary", new DataTransference<Float>(Float.class, Worker.Params.salary.get()));
            Request rq = new Request("remove_greater", arguments);
            client.sendRequest(rq);
            Response resp = client.receive();
            setMessage(resp.messages.get(0), resp.status.equals(Response.Status.OK) ? Color.GREEN : Color.RED);

        });
        contentPane.add(messageLabel);
        contentPane.add(salaryField);
        contentPane.add(delete);
        pack();
        setVisible(true);

    }
}
