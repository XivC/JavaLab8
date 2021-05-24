package com.xivs.client.data;

import com.xivs.common.Utils.ObjectSerializer;
import com.xivs.common.dataTransfer.Auth;
import com.xivs.common.dataTransfer.DataTransference;
import com.xivs.common.dataTransfer.Request;
import com.xivs.common.dataTransfer.Response;
import com.xivs.common.lab.Worker;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
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
    private ArrayList<Runnable> connectionLostEvents;
    private ArrayList<Runnable> connectionRestoredEvents;
    private volatile boolean syncFlag;
    private volatile boolean restoreFlag;
    HashMap<String, Worker> workers;

    public HashMap<String, Worker> getWorkers(){
        return this.workers;
    }

    void update(){
        Request syncRequest = new Request("sync", new HashMap<>());


        while (true) {
            try {
            if (!syncFlag) {

                    Thread.sleep(100);
                    continue;


            }

                syncRequest.setAuth(this.auth);
                this.sendRequestSync(syncRequest);

                Response resp = receiveSync();

                if (resp != null && resp.status.equals(Response.Status.OK)) {

                    this.data = resp.attachments;
                    this.workers = (HashMap<String, Worker>) ((this.data).get("workers").get());
                    System.out.println(resp.status);
                    this.updateEvent();
                    System.out.println(resp.status);
                    System.out.println("synced!");
                }
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                continue;
            }
        }


    }



    public Client() {
        this.auth = new Auth("", "");
        this.connectionLostEvents = new ArrayList<>();
        this.connectionRestoredEvents = new ArrayList<>();
        this.workers = new HashMap<>();
        this.syncThread = new Thread(this::update);
        this.waitConnectionThread = new Thread(this::waitConnection);
        this.syncFlag = false;
        this.restoreFlag = false;
        this.syncSocket = new Socket();
        this.socket = new Socket();
        this.syncThread.start();
        this.waitConnectionThread.start();


    }

    public void connect(byte[] ip, int port) {
        try {
            this.port = port;
            this.ip = ip;
            InetAddress address = InetAddress.getByAddress(this.ip);
            InetSocketAddress addr = new InetSocketAddress(address, this.port);
            try {
                this.socket.connect(addr, 100);
                this.syncSocket.connect(addr, 100);
            }
            catch (SocketException ex){
                this.socket = new Socket(address, port);
                this.syncSocket = new Socket(address, port);
            }
            this.isConnected = true;
            this.syncFlag = true;


        }
        catch (IOException ex){
            ex.printStackTrace();
            this.isConnected = false;
        }


    }

    public  void disconnect() {
        try {
            this.socket.close();
            this.syncSocket.close();
            this.syncFlag = false;
            this.restoreFlag = false;
            this.isConnected = false;
        }
        catch (Exception ex) {return;}



    }

    public void waitConnection() {
        while (true){
            if (!restoreFlag){
                try {
                    Thread.sleep(100);
                    continue;
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            else {
                for (Runnable r : connectionLostEvents) {
                    r.run();
                }
                isConnected = false;

                while (!isConnected) {
                    this.syncFlag = false;
                    System.out.println("Серверу хана");
                    try {
                        this.syncSocket.close();
                        this.socket.close();
                        InetAddress address = InetAddress.getByAddress(this.ip);
                        InetSocketAddress addr = new InetSocketAddress(address, this.port);
                        this.socket = new Socket();
                        this.syncSocket = new Socket();
                        this.socket.connect(addr, 300 * 1000);
                        this.isConnected = true;
                        this.syncSocket.connect(addr);
                        this.syncFlag = true;
                        this.restoreFlag = false;
                        for (Runnable r : connectionRestoredEvents) {
                            r.run();
                        }
                        System.out.println("сервер снова с вами");
                    }
                    catch (IOException ex ){
                        isConnected = false;
                        restoreFlag = false;
                        continue;
                    }

                }



            }

        }
    }



    private Response receiveSync(){
        try {
            Response resp;
            InputStream inputStream = syncSocket.getInputStream();
            ObjectInputStream objectStream = new ObjectInputStream(inputStream);
            resp = (Response) objectStream.readObject();

            return resp;

        } catch (IOException | ClassNotFoundException | NullPointerException ex) {


            return null;
        }
    }

    public Response receive() {

        try {
            Response resp;
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectStream = new ObjectInputStream(inputStream);
            resp = (Response) objectStream.readObject();

            return resp;

        } catch (IOException | ClassNotFoundException | NullPointerException ex) {

            //this.restoreFlag = true;
            return null;
        }

    }


    public boolean sendRequest(Request rq) {
        rq.setAuth(this.auth);
        byte[] bytes = ObjectSerializer.serialize(rq);
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(bytes);
            return true;

        } catch (IOException | NullPointerException ex) {
            this.restoreFlag = true;
            return false;
        }

    }
    public boolean sendRequestSync(Request rq){
        rq.setAuth(this.auth);
        byte[] bytes = ObjectSerializer.serialize(rq);
        try {
            OutputStream outputStream = syncSocket.getOutputStream();
            outputStream.write(bytes);
            return true;

        } catch (IOException | NullPointerException ex) {
            return false;
        }
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
