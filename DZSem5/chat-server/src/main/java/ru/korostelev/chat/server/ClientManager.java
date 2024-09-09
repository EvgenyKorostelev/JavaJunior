package ru.korostelev.chat.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager implements Runnable {

    private final Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String name;
    public final static ArrayList<ClientManager> clients = new ArrayList<>();

    public ClientManager(Socket socket) {
        this.socket = socket;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            name = bufferedReader.readLine();
            clients.add(this);
            System.out.println(name + " подключился к чату.");
            broadcastMessage("Server: " + name + " подключился к чату.");
        } catch (IOException e) {
            closeAllResource(socket, bufferedWriter, bufferedReader);
            e.printStackTrace();
        }

    }

    private void closeAllResource(Socket socket,
                                  BufferedWriter bufferedWriter,
                                  BufferedReader bufferedReader) {
        removeClient();
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeClient() {
        clients.remove(this);
        System.out.println(name + " покинул чат.");
        broadcastMessage("Server: " + name + " покинул чат.");
    }

    private void broadcastMessage(String message) {
        for (ClientManager client : clients) {
            try {
                if (!client.name.equals(this.name)) {// лучше по clientId
                    client.bufferedWriter.write(message);
                    client.bufferedWriter.newLine();
                    client.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeAllResource(socket, bufferedWriter, bufferedReader);
                e.printStackTrace();
            }
        }
    }


    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                if(messageFromClient == null){
                    closeAllResource(socket, bufferedWriter, bufferedReader);
                    break;
                }
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeAllResource(socket, bufferedWriter, bufferedReader);
                e.printStackTrace();
                break;
            }
        }
    }
}
