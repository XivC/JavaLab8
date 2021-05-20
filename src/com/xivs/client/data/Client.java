package com.xivs.client.data;

import com.xivs.client.data.DataProvider;
import com.xivs.common.Utils.ObjectSerializer;
import com.xivs.common.dataTransfer.Auth;
import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.lab.Worker;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

public class Client extends DataProvider<HashMap<String, DataTransference<?>>> {
    private volatile Socket socket;
    private volatile Socket syncSocket;
    private volatile boolean isConnected = false;
    private volatile int port;
    private volatile byte[] ip;
    private volatile Auth auth;
    private volatile Thread syncThread;
    private volatile Thread waitConnectionThread;
    protected volatile ExecutorService responseReaderPool;
    private ArrayList<Runnable> connectionLostEvents;
    private ArrayList<Runnable> connectionRestoredEvents;
    private volatile boolean restoreNeeded;
    HashMap<String, Worker> workers;

    public HashMap<String, Worker> getWorkers(){
        return this.workers;
    }

    void update(){
        Request syncRequest = new Request("sync", new HashMap<>());


        while (true){

            if(!isConnected || this.auth.login.equals("")){
                try {
                    Thread.sleep(10);
                    continue;
                }
                catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }

            syncRequest.setAuth(this.auth);
            this.sendRequest(this.syncSocket, syncRequest);
            Callable<Response> readResponseTask = () -> this.receive(this.syncSocket);

            Response resp = readResponseFromPool(readResponseTask);

            if (resp != null && resp.status.equals(Response.Status.OK)){
                this.data = resp.attachments;
                this.workers  = (HashMap<String, Worker>) ((this.data).get("workers").get());
                this.updateEvent();
                System.out.println("synced!");
            }


        }

    }

    private Response readResponseFromPool(Callable<Response> readResponseTask) {
        Future<Response> responseFuture = responseReaderPool.submit(readResponseTask);
        Response resp = null;
        while (!responseFuture.isDone()) {
            try {


                resp = responseFuture.get();

            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
                break;
            }
        }

        return resp;
    }

    public Client() {
        this.auth = new Auth("", "");
        this.syncThread = new Thread(this::update);
        this.waitConnectionThread = new Thread(this::waitConnection);
        this.responseReaderPool = Executors.newCachedThreadPool();
        this.connectionLostEvents = new ArrayList<>();
        this.connectionRestoredEvents = new ArrayList<>();
        this.workers = new HashMap<>();




    }

    public void connect(byte[] ip, int port) {
        try {
            InetAddress address = InetAddress.getByAddress(ip);
            this.port = port;
            this.ip = ip;
            this.socket = new Socket(address, this.port);
            this.syncSocket = new Socket(address, this.port);
            this.isConnected = true;
            this.restoreNeeded = false;
            if(!syncThread.isAlive()) syncThread.start();
            if(!waitConnectionThread.isAlive()) waitConnectionThread.start();

        } catch (IOException ex) {
            this.isConnected = false;
        }
    }

    public void disconnect() {
        try {
            this.isConnected = false;
            this.restoreNeeded = false;
            //this.syncThread.interrupt();
            //this.waitConnectionThread.interrupt();
            this.socket.close();
            this.syncSocket.close();


        } catch (Exception ex) {
            System.out.println("112231212123123123123");
            JOptionPane.showMessageDialog(new JFrame(), "Пиздец");

            this.isConnected = false;
        }

    }

    public void waitConnection() {
        while(true){
            if(!restoreNeeded) {
                try {
                    Thread.sleep(5000);
                    continue;
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }

        for(Runnable r: connectionLostEvents){
            r.run();
        }
        while (!this.isConnected) {
            System.out.println("Сервер умер. Пробуем восстановить соединение");

            byte[] ip = this.ip;
            if (this.socket != null && this.syncSocket != null){
                try {
                    this.socket.close();
                    this.syncSocket.close();

                }
                catch(IOException ex){ex.printStackTrace();}
            }
            this.connect(ip, port);


        }
        System.out.println("Соединение восстановлено");
        for(Runnable r: connectionRestoredEvents){
            r.run();
        }
        this.restoreNeeded = false;
        }



    }

    public  Response receive() {
        //return receive(socket);
        Callable<Response> readResponseTask = () -> this.receive(this.socket);
        return readResponseFromPool(readResponseTask);


    }
    private Response receive(Socket socket){
        try {
            Response resp;
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectStream = new ObjectInputStream(inputStream);
            resp = (Response) objectStream.readObject();

            return resp;

        } catch (IOException | ClassNotFoundException | NullPointerException ex) {

            this.isConnected = false;
            this.restoreNeeded = true;
            try {
                socket.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }

            return null;
        }
    }
    private  boolean sendRequest(Socket socket, Request rq){
        rq.setAuth(this.auth);
        byte[] bytes = ObjectSerializer.serialize(rq);
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(bytes);
            return true;

        } catch (IOException | NullPointerException ex) {
            this.isConnected = false;
            this.restoreNeeded = true;
            try {
                socket.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }

            return false;
        }
    }
    public boolean sendRequest(Request rq) {
        return this.sendRequest(this.socket, rq);

    }
    public boolean isConnected() {
        return this.isConnected;
    }
    public void setAuth(Auth auth){
        this.auth = auth;
    }
    public void addConnectionLostEvent(Runnable r){
        this.connectionLostEvents.add(r);
    }
    public void addConnectionRestoredEvent(Runnable r){
        this.connectionRestoredEvents.add(r);
    }


}
