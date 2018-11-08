package com.vynaloze.fo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String host = "localhost";
    private static final int portNumber = 8888;

    public static void main(String[] args) {
        try (final Socket socket = new Socket(host, portNumber);
             final ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
             final PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
             final BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
             final PrintWriter stdOut = new PrintWriter(System.out, true)) {

            final Thread reader = new Thread(new SocketReader(socketIn, stdOut));
            reader.setDaemon(true);
            reader.start();

            String input;
            while ((input = stdIn.readLine()) != null) {
                socketOut.println(input);
                if (input.equalsIgnoreCase(Response.Status.DROP.getStatus())) {
                    System.exit(0);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
