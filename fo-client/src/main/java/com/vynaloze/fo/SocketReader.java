package com.vynaloze.fo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SocketReader implements Runnable {
    private final BufferedReader in;
    private final PrintWriter out;

    public SocketReader(final BufferedReader in, final PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        String input;
        try {
            while ((input = in.readLine()) != null) {
                out.println(">>>" + input);
                if (input.equalsIgnoreCase("DROP")) {
                    System.exit(0);
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
