
package com.xivs.client.gui.windows;

import com.xivs.client.data.Client;
import com.xivs.client.gui.components.builders.AddressBuilderForm;
import com.xivs.client.gui.components.builders.CoordinatesBuilderForm;
import com.xivs.client.gui.components.builders.OrganizationBuilderForm;
import com.xivs.client.gui.components.builders.WorkerBuilderForm;
import com.xivs.client.gui.components.builders.wrappers.MinimizableBuilderWrapper;

import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.lab.*;


import java.awt.*;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.swing.*;

import static com.xivs.client.Application.APP;

public class CreateUpdateObjectWindow extends JFrame {
    MinimizableBuilderWrapper<Worker> workersFormWrapper;
    MinimizableBuilderWrapper<Coordinates> coordinatesFormWrapper;
    MinimizableBuilderWrapper<Organization> organizationFormWrapper;
    MinimizableBuilderWrapper<Address> addressFormWrapper;
    JTextField keyField;
    JLabel messageLabel;
    Client client;
    public static int UPDATE = 0;
    public static int CREATE = 1;
    ResourceBundle res = APP.getResources();
    private void setMessage(String message, Color color){
        messageLabel.setText(message);
        messageLabel.setForeground(color);
    }

    private void delete(){
        HashMap<String, DataTransference<?>> arguments = new HashMap<>();
        arguments.put("key", new DataTransference<>(String.class, keyField.getText()));
        Request rq = new Request("remove_key", arguments);
        client.sendRequest(rq);
        Response resp = client.receive();
        setMessage(resp.messages.get(0), resp.status.equals(Response.Status.OK) ? Color.GREEN : Color.RED);
        if(resp.status.equals(Response.Status.OK)) setVisible(false);
    }
    private void updateCreate(boolean create){
        Worker worker = build();

        if(worker == null) {setMessage(res.getString("field_filling_error"), Color.RED); return;}
        HashMap<String, DataTransference<?>> arguments = new HashMap<>();
        if(!create) arguments.put("id", new DataTransference<>(Long.class, client.getWorkers().get(keyField.getText()).id));
        else arguments.put("key", new DataTransference<>(String.class, keyField.getText()));

        arguments.put("worker", new DataTransference<>(Worker.class, worker));
        Request rq = null;
        rq = new Request(create ? "insert":"update", arguments);


        client.sendRequest(rq);
        Response resp = client.receive();
        System.out.println(resp.messages);
        setMessage(resp.messages.get(0), resp.status.equals(Response.Status.OK) ? Color.GREEN : Color.RED);
    }

    private Worker build(){
        Worker worker = workersFormWrapper.build();
        if(worker == null) return null;
        Coordinates coordinates = coordinatesFormWrapper.build();
        if(coordinates == null) return null;
        worker.coordinates = coordinates;
        Organization organization = organizationFormWrapper.build();
        if (organization == null) return null;
        if (organization.equals(Organization.DEFAULT)){ worker.organization = organization; return worker;}
        Address address = addressFormWrapper.build();
        if(address == null) return null;
        organization.officialAddress = address;
        worker.organization = organization;
        return worker;

    }
    public CreateUpdateObjectWindow(int state, String key) {
        super();
        Worker defaultWorker;
        client = APP.client;
        if(key == null) defaultWorker = null;
        else defaultWorker = client.getWorkers().get(key);

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 700));
        Container container = getContentPane();
        container.setLayout(new FlowLayout());


        client.addConnectionLostEvent(()->setMessage(res.getString("server_lost_connection"), Color.red));
        client.addConnectionRestoredEvent(()->setMessage(res.getString("server_connection_restored"), Color.green));



        JPanel forms = new JPanel();
        forms.setLayout(new BoxLayout(forms, BoxLayout.Y_AXIS));
        JScrollPane forms_pane = new JScrollPane(forms);
        forms_pane.setPreferredSize(new Dimension(500, 500));
        WorkerBuilderForm workersForm = new WorkerBuilderForm(new Dimension(500, 300), defaultWorker);
        CoordinatesBuilderForm coordinatesForm = new CoordinatesBuilderForm(new Dimension(500, 110), defaultWorker == null ? null : defaultWorker.coordinates);
        OrganizationBuilderForm organizationForm = new OrganizationBuilderForm(new Dimension(500, 110),defaultWorker == null ? null :  defaultWorker.organization);
        AddressBuilderForm addressForm = new AddressBuilderForm(new Dimension(500, 110), defaultWorker == null ? null : defaultWorker.organization.officialAddress);
        workersFormWrapper = new MinimizableBuilderWrapper<>(workersForm, null, res.getString("worker_input"), new Dimension(500, 100), false);
        coordinatesFormWrapper = new MinimizableBuilderWrapper<>(coordinatesForm, null, res.getString("coordinates_input"), new Dimension(500, 100), false);
        organizationFormWrapper = new MinimizableBuilderWrapper<>(organizationForm, Organization.DEFAULT, res.getString("organization_input"), new Dimension(500, 100), true);
        addressFormWrapper = new MinimizableBuilderWrapper<>(addressForm, Address.DEFAULT, res.getString("address_input"), new Dimension(500, 100), true);
        workersFormWrapper.setHeaderFont(new Font("Tacoma", Font.BOLD, 11));
        coordinatesFormWrapper.setHeaderFont(new Font("Tacoma", Font.BOLD, 11));
        organizationFormWrapper.setHeaderFont(new Font("Tacoma", Font.BOLD, 11));
        addressFormWrapper.setHeaderFont(new Font("Tacoma", Font.BOLD, 11));

        forms.add(workersFormWrapper);
        forms.add(Box.createRigidArea(new Dimension(5, 15)));
        forms.add(coordinatesFormWrapper);
        forms.add(Box.createRigidArea(new Dimension(5, 15)));
        forms.add(organizationFormWrapper);
        forms.add(Box.createRigidArea(new Dimension(5, 15)));
        forms.add(addressFormWrapper);
        forms.add(Box.createRigidArea(new Dimension(5, 15)));
        forms_pane.invalidate();
        forms_pane.validate();

        messageLabel = new JLabel();
        keyField = new JTextField();
        JLabel keyLabel = new JLabel(res.getString("key"));
        keyLabel.setFont(new Font("Tacoma", Font.BOLD, 11));
        if (defaultWorker != null){ keyField.setText(key); keyField.setEnabled(false);};
        JPanel keyPanel = new JPanel(new GridLayout(1,2,5,12));
        keyPanel.setPreferredSize(new Dimension(500, 30));
        keyPanel.add(keyLabel);
        keyPanel.add(keyField);

        container.add(messageLabel);
        container.add(keyPanel);
        container.add(forms_pane);
        JButton updateButton = new JButton(res.getString("update"));
        JButton deleteButton = new JButton(res.getString("delete"));
        JButton createButton = new JButton(res.getString("create"));
        updateButton.addActionListener((e)->updateCreate(false));
        deleteButton.addActionListener((e)-> delete());
        createButton.addActionListener((e)->updateCreate(true));
        JPanel buttonsPanel = new JPanel(new GridLayout(1,2,10,12));

        buttonsPanel.add(state == CREATE ? createButton: updateButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.setPreferredSize(new Dimension(500, 75));
        container.add(buttonsPanel);
        pack();
        setVisible(true);

    }

}
