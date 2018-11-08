package com.vynaloze.fo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

public class SocketReader implements Runnable {
    private final ObjectInputStream in;
    private final PrintWriter out;

    public SocketReader(final ObjectInputStream in, final PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        Object input;
        try {
            while ((input = in.readObject()) != null) {
                if (input instanceof Response) {
                    final Response response = (Response) input;
                    if (response.visualize()) {
                        Plotter.plot(response.getResults(), response.getTestFunction());
                    }
                } else if (input instanceof String) {
                    final String line = (String) input;
                    out.println(">>>" + line);
                }
            }
        } catch (final IOException | ClassNotFoundException e) {
            //e.printStackTrace();
        }
    }
}
