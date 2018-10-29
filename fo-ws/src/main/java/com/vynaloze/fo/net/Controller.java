package com.vynaloze.fo.net;

import com.vynaloze.fo.Worker;
import com.vynaloze.fo.de.WorkerDE;
import com.vynaloze.fo.functions.BealeFunction;
import com.vynaloze.fo.functions.BukinN6Function;
import com.vynaloze.fo.functions.RosenbrockFunction;
import com.vynaloze.fo.ga.WorkerGA;
import java.io.PrintWriter;

public class Controller {
    public static final String DROP = "DROP";
    public static final String INVALID = "INVALID";
    public static final String OK = "OK";

    public String process(final String request, final PrintWriter out) {
        final String[] splitted = request.toUpperCase().split(";");

        if (splitted[0].equalsIgnoreCase(DROP)) {
            return DROP;
        }

        if (splitted.length != 2) {
            out.println("Invalid request size.");
            return INVALID;
        }

        final Worker worker;
        switch (splitted[0]) {
            case "GA":
                worker = new WorkerGA();
                break;
            case "DE":
                worker = new WorkerDE();
                break;
            default:
                out.println("Invalid algorithm. Possible options: GA, DE.");
                return INVALID;
        }
        switch (splitted[1]) {
            case "ROS":
                worker.setTestFunction(new RosenbrockFunction());
                break;
            case "BEA":
                worker.setTestFunction(new BealeFunction());
                break;
            case "BUK":
                worker.setTestFunction(new BukinN6Function());
                break;
            default:
                out.println("Invalid function. Possible options: ROS,BEA,BUK.");
                return INVALID;
        }

        worker.run(out);

        return OK;
    }
}
