package ru.korostelev.chat.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final Socket socket;
    private final String name;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public Client(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                            socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));
        } catch (IOException e) {
            closeAllResource(socket, bufferedWriter, bufferedReader);
            e.printStackTrace();
        }
    }

    public void listenForMessage(){
        new Thread(() -> {
            String message;
            while (socket.isConnected()){
                try {
                    message = bufferedReader.readLine();
                    System.out.println(message);
                } catch (IOException e) {
                    closeAllResource(socket, bufferedWriter, bufferedReader);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMessage(){
        try {
            bufferedWriter.write(name);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()){
                String message = scanner.nextLine();
                bufferedWriter.write(name + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeAllResource(socket, bufferedWriter, bufferedReader);
            e.printStackTrace();
        }
    }

    private void closeAllResource(Socket socket,
                                  BufferedWriter bufferedWriter,
                                  BufferedReader bufferedReader){
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
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
