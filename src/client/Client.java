package client;


import Obj.OutObj;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Client {
    private final int PORT = 8189;
    private String ipAddress;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private OutObj outObj = new OutObj();


    public Client(String[] strings){
        ipAddress = strings[0];
        try {
            socket = new Socket(ipAddress,PORT);
            out = new ObjectOutputStream(new DataOutputStream(socket.getOutputStream()));
            outObj.words = new ArrayList<>();
            for (int i = 1; i < strings.length; i++) {
                outObj.words.add(strings[i]);
            }
            out.writeObject(outObj);
            in = new ObjectInputStream(new DataInputStream(socket.getInputStream()));
            outObj = (OutObj)in.readObject();
            for (String s : outObj.words){
                System.out.println(s);
            }
        } catch (IOException e) {
            System.err.println("Ошибка подключения к серверу");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
