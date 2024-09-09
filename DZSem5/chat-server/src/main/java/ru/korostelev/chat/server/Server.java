package ru.korostelev.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void runServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientManager clientManager = new ClientManager(socket);
                System.out.println("Подключился новый клиент!");
                Thread thread = new Thread(clientManager);
                thread.start();
            }
        } catch (IOException e) {
            closeSocket();
            e.printStackTrace();
        }
    }

    private void closeSocket(){
        try {
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
