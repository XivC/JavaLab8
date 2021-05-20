package com.xivs.client.gui.windows;

import com.xivs.client.data.Client;
import com.xivs.common.Utils.Parser.ByteParser;
import com.xivs.common.Utils.Parser.IntegerParser;
import com.xivs.common.dataTransfer.Auth;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import static com.xivs.client.Application.APP;

public class LoginWindow extends JFrame{
    JLabel messageLabel;
    JTextField serverTextField;
    JTextField portTextField;
    JTextField loginTextField;
    JTextField passwordTextField;
    Client client;

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
        if (resp.status == Response.Status.ERROR){
            setMessage("Неверный логин или пароль", Color.red);
            //client.disconnect();
        }
        else{

            startMainWindow();

        }
    }
    private void register(){
        client.setAuth(new Auth(this.loginTextField.getText(), this.passwordTextField.getText()));
        client.sendRequest(new Request("register", new HashMap<>()));
        Response resp = client.receive();
        if (resp.status == Response.Status.ERROR){
            setMessage("Не удалось создать акаунт", Color.red);
            //client.disconnect();
        }
        else{

            startMainWindow();

        }
    }
    private void connect(){

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
                setMessage("Сервер недоступен", Color.RED);

                return;
            }
        }
        client.addConnectionLostEvent(()->setMessage("Потеряно соединение с сервером", Color.red));
        client.addConnectionRestoredEvent(()->setMessage("Соединение восстановлено", Color.green));






    }
    public LoginWindow(Dimension size){
        client = APP.client;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Авторизация");
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
        formContainer.add(new JLabel("Сервер"));
        formContainer.add(serverTextField);
        formContainer.add(new JLabel("Порт"));
        formContainer.add(portTextField);
        formContainer.add(new JLabel("Логин"));
        formContainer.add(loginTextField);
        formContainer.add(new JLabel("Пароль"));
        formContainer.add(passwordTextField);
        JPanel formBox = new JPanel(new FlowLayout());
        formContainer.setPreferredSize(new Dimension((int)(size.width*0.8), (int)(size.height*0.5)));
        formBox.add(formContainer);
        JPanel buttonContainer = new JPanel(new GridLayout(1, 2, 5, 15));
        JPanel buttonBox = new JPanel(new FlowLayout());
        JButton login = new JButton("Войти");
        JButton register = new JButton("Регистрация");
        login.addActionListener(e->{connect(); login();});
        register.addActionListener(e->{connect(); register();});
        buttonContainer.setPreferredSize(new Dimension((int)(size.width*0.5), (int)(size.height*0.1)));
        buttonContainer.add(login);
        buttonContainer.add(register);
        buttonBox.add(buttonContainer);
        contentPane.add(messageBox);
        contentPane.add(formBox);
        contentPane.add(buttonBox);
        pack();
        setVisible(true);







    }
}
