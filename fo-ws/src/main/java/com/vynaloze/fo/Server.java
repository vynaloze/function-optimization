package com.vynaloze.fo;

import com.vynaloze.fo.net.Controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int portNumber = 8888;
    private static final Controller controller = new Controller();

    public static void main(String[] args) {
        try (final ServerSocket serverSocket = new ServerSocket(portNumber);
             final Socket socket = serverSocket.accept();
             final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;

            out.println("Connected.");
            System.out.println("Client connected.");

            while ((inputLine = in.readLine()) != null) {
                final String response = controller.process(inputLine, out);
                if (response.equalsIgnoreCase(Controller.DROP)) {
                    out.println(response);
                    break;
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
