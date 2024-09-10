package ru.korostelev.chat.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.UUID;

public class ClientManager implements Runnable {

    private final Socket socket;
    private final UUID clientId;
    private String name;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    public final static ArrayList<ClientManager> clients = new ArrayList<>();

    public ClientManager(Socket socket) {
        this.socket = socket;
        this.clientId = UUID.randomUUID();
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

    private void privateMessage(String destinationName,String message){
        for (ClientManager client : clients) {
            try {
                if (client.name.equals(destinationName)) {
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

    private void broadcastMessage(String message) {
        for (ClientManager client : clients) {
            try {
                if (!client.getClientId().equals(this.clientId)) {
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
                //System.out.println(messageFromClient);
                if(messageFromClient.startsWith(name + ": @")){
                    messageFromClient = messageFromClient.replaceFirst(name + ": ", "");
                    StringTokenizer tokens = new StringTokenizer(messageFromClient, " ");
                    String destinationName = tokens.nextElement().toString().substring(1);
                    messageFromClient = messageFromClient.replaceFirst("@" + destinationName + " ", "");
                    messageFromClient = "от " + this.name + ": " + messageFromClient;
                            privateMessage(destinationName, messageFromClient);
                } else {
                    broadcastMessage(messageFromClient);
                }
            } catch (IOException e) {
                closeAllResource(socket, bufferedWriter, bufferedReader);
                e.printStackTrace();
                break;
            }
        }
    }

    public UUID getClientId() {
        return clientId;
    }
}
