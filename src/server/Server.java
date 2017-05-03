package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;




public class Server {
    private ServerSocket serverSocket;
    private Socket socket;

    private final int PORT = 8189;

    public Server(){
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер подключен. Ожидается подключение клиентов...");
            while(true){
                socket = serverSocket.accept();
                System.out.println("Запрос от клиента");
               new ClientHandler(this, socket);
            }
        } catch (IOException e){
            System.err.println("Ошибка с подключением сервера");
        } finally {
            try {
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
