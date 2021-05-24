package com.xivs.client.gui.windows;

import com.xivs.client.data.Client;
import com.xivs.common.Utils.Parser.ByteParser;
import com.xivs.common.Utils.Parser.IntegerParser;
import com.xivs.common.dataTransfer.Auth;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.xivs.client.Application.APP;

public class LoginWindow extends JFrame{
    JLabel messageLabel;
    JTextField serverTextField;
    JTextField portTextField;
    JTextField loginTextField;
    JTextField passwordTextField;
    Client client;
    ResourceBundle res = APP.getResources();

    private void startMainWindow(){
        new MainWindow().start();
        setVisible(false);
    }
    private void setMessage(String message, Color color){
        messageLabel.setText(message);
        messageLabel.setForeground(color);
    }
    private void login(){
        client.setAuth(new Auth(this.loginTextField.getText(), this.passwordTextField.getText()));
        client.sendRequest(new Request("login", new HashMap<>()));
        Response resp = client.receive();
        if (resp == null) return;
        if (resp.status == Response.Status.ERROR){
            setMessage(res.getString("server_login_error"), Color.red);
            client.disconnect();
        }

        else{

            startMainWindow();

        }
    }
    private void register(){
        client.setAuth(new Auth(this.loginTextField.getText(), this.passwordTextField.getText()));
        client.sendRequest(new Request("register", new HashMap<>()));
        Response resp = client.receive();
        if (resp == null) return;
        if (resp.status == Response.Status.ERROR){
            setMessage(res.getString("server_register_error"), Color.red);
            client.disconnect();
        }
        else{

            startMainWindow();

        }
    }
    private boolean connect(){

        Byte[] ipp = Arrays.stream(this.serverTextField.getText().split("\\.")).map(p -> ByteParser.parse(p, (byte) 0)).toArray(Byte[]::new);

        byte[] ip = new byte[]{0,0,0,0};
        for (int i = 0; i < ip.length; i++){
            ip[i] = ipp[i];
        }
        int port = IntegerParser.parse(portTextField.getText(), 1);
        System.out.println(client.isConnected());
        if(!client.isConnected()) {
            client.connect(ip, port);
            if (!client.isConnected()) {
                setMessage(res.getString("server_unreachable"), Color.RED);

                return false;
            }
        }
        client.addConnectionLostEvent(()->setMessage(res.getString("server_lost_connection"), Color.red));
        client.addConnectionRestoredEvent(()->setMessage(res.getString("server_connection_restored"), Color.green));
        return true;






    }
    public LoginWindow(Dimension size, Locale loc){
        client = APP.client;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(res.getString("login_title"));
        setPreferredSize(size);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout( contentPane,BoxLayout.Y_AXIS));
        JPanel messageBox = new JPanel();
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageBox.add(messageLabel);
        JPanel formContainer = new JPanel(new GridLayout(4, 2, 5, 15));
        serverTextField = new JTextField();
        portTextField = new JTextField();
        loginTextField = new JTextField();
        passwordTextField = new JPasswordField();
        formContainer.add(new JLabel(res.getString("server")));
        formContainer.add(serverTextField);
        formContainer.add(new JLabel(res.getString("port")));
        formContainer.add(portTextField);
        formContainer.add(new JLabel(res.getString("login")));
        formContainer.add(loginTextField);
        formContainer.add(new JLabel(res.getString("password")));
        formContainer.add(passwordTextField);
        JPanel formBox = new JPanel(new FlowLayout());
        formContainer.setPreferredSize(new Dimension((int)(size.width*0.8), (int)(size.height*0.5)));
        formBox.add(formContainer);
        JPanel buttonContainer = new JPanel(new GridLayout(1, 2, 5, 15));
        JPanel buttonBox = new JPanel(new FlowLayout());
        JButton login = new JButton(res.getString("login_button"));
        JButton register = new JButton(res.getString("register_button"));
        login.addActionListener(e->{
            if(connect()) login();
        });
        register.addActionListener(e->{if(connect()) register();});
        buttonContainer.setPreferredSize(new Dimension((int)(size.width*0.5), (int)(size.height*0.1)));
        JComboBox<Locale> languages = new JComboBox<>();
        languages.addItem(new Locale("ru", "RU"));
        languages.addItem(new Locale("bg", "BG"));
        languages.addItem(new Locale("is", "IS"));
        languages.addItem(new Locale("es", "CR"));
        languages.setSelectedItem(loc);
        languages.addActionListener((e)->{
            JComboBox<Locale> box = (JComboBox<Locale>) e.getSource();
            Locale l = (Locale)box.getSelectedItem();
            APP.setLocale(l);

            new LoginWindow(new Dimension(500, 300), l);
            setVisible(false);

        });
        JPanel lang_panel = new JPanel(new FlowLayout());
        languages.setPreferredSize(new Dimension(400, 30));
        lang_panel.add(languages);
        buttonContainer.add(login);
        buttonContainer.add(register);
        buttonBox.add(buttonContainer);
        contentPane.add(messageBox);
        contentPane.add(formBox);
        contentPane.add(lang_panel);
        contentPane.add(buttonBox);
        pack();
        setVisible(true);







    }
}
