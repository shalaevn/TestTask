package server;

import Obj.OutObj;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class ClientHandler {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    OutObj outObj;

    public ClientHandler(Server server, Socket socket){
        try {
            in = new ObjectInputStream(new DataInputStream(socket.getInputStream()));
            out = new ObjectOutputStream(new DataOutputStream(socket.getOutputStream()));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        outObj = (OutObj) in.readObject();
                        String cmd = outObj.words.get(0);
                        outObj.words.remove(0);
                        switch(cmd){
                            case "add":
                                Dictionary.setWord(outObj.words);
                                outObj.words = new ArrayList<>();
                                outObj.words.add("<значения слова успешно добавлены>");
                                break;
                            case "get":
                                outObj.words = Dictionary.getWord(outObj.words.get(0));
                                if (outObj.words.size() == 0)
                                    outObj.words.add("<слово отсутствует в словаре>");
                                break;
                            case "delete":
                                if(Dictionary.deleteWord(outObj.words)) {
                                    outObj.words = new ArrayList<>();
                                    outObj.words.add("<значения слова успешно удалены>");
                                } else {
                                    outObj.words = new ArrayList<>();
                                    outObj.words.add("<слово/значение отсутствует в словаре>");
                                }
                                break;
                            default:
                                outObj.words = new ArrayList<>();
                                outObj.words.add("<введена не существующая команда>");
                                break;
                        }
                        out.writeObject(outObj);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
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
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
