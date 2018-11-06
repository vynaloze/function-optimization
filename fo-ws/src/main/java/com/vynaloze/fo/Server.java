package com.vynaloze.fo;

import com.vynaloze.fo.net.Controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int portNumber = 8888;
    private static final Controller controller = new Controller();

    public static void main(String[] args) {
        try (final ServerSocket serverSocket = new ServerSocket(portNumber);
             final Socket socket = serverSocket.accept();
             final ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;

            out.writeObject("Connected.\n");

            System.out.println("Client connected.");

            //fixme
            while ((inputLine = in.readLine()) != null) {
                final Response response = controller.process(inputLine, out);
                if (response.getStatus().equals(Response.Status.OK)) {
                    out.writeObject(response);

                }
                if (response.getStatus().equals(Response.Status.DROP)) {
                    out.writeObject(Response.Status.DROP.getStatus() + "\n");

                    break;
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
