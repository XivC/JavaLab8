package com.xivs.client.gui.windows;

import com.xivs.client.data.Client;
import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.lab.Position;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static com.xivs.client.Application.APP;

public class RemoveAllByPositionWindow extends JFrame {
    JLabel messageLabel;
    JComboBox<Position> positionField;

    private void setMessage(String message, Color color){
        messageLabel.setText(message);
        messageLabel.setForeground(color);
    }
    private JComboBox<Position> initPositionField(){
        JComboBox<Position> positionField = new JComboBox<>();
        for(Position p: Position.values()){
            if(p.equals(Position.NONE)) continue;
            positionField.addItem(p);
        }
        return positionField;
    }
    public RemoveAllByPositionWindow(){
        super();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        Client client = APP.client;
        client.addConnectionLostEvent(()->setMessage("Потеряно соединение с сервером", Color.red));
        client.addConnectionRestoredEvent(()->setMessage("Соединение восстановлено", Color.green));
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        setPreferredSize(new Dimension(300, 200));
        setResizable(false);
        messageLabel = new JLabel();
        positionField = initPositionField();
        positionField.setPreferredSize(new Dimension(250, 50));
        JButton delete = new JButton("Удалить");

        delete.setPreferredSize(new Dimension(250, 50));
        delete.addActionListener((e)->
        {
            Position position = (Position)positionField.getSelectedItem();
            HashMap<String, DataTransference<?>> arguments = new HashMap<>();
            arguments.put("position", new DataTransference<Position>(Position.class, position));
            Request rq = new Request("remove_all_by_position", arguments);
            client.sendRequest(rq);
            Response resp = client.receive();
            setMessage(resp.messages.get(0), resp.status.equals(Response.Status.OK) ? Color.GREEN : Color.RED);

        });
        contentPane.add(messageLabel);
        contentPane.add(positionField);
        contentPane.add(delete);
        pack();
        setVisible(true);

    }
}
