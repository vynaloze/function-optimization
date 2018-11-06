package com.vynaloze.fo.net;

import com.vynaloze.fo.Response;
import com.vynaloze.fo.Results;
import com.vynaloze.fo.Worker;
import com.vynaloze.fo.dao.Dao;
import com.vynaloze.fo.de.WorkerDE;
import com.vynaloze.fo.functions.BealeFunction;
import com.vynaloze.fo.functions.BukinN6Function;
import com.vynaloze.fo.functions.RosenbrockFunction;
import com.vynaloze.fo.functions.TestFunction;
import com.vynaloze.fo.ga.WorkerGA;
import java.io.IOException;
import java.io.ObjectOutputStream;

//fixme all of this to look up
public class Controller {
    private final Dao dao;

    public Controller() {
        dao = new Dao();
    }

    public Response process(final String request, final ObjectOutputStream out) {
        try {
            final String[] splitted = request.toUpperCase().split(";");

            if (splitted[0].equalsIgnoreCase(Response.Status.DROP.getStatus())) {
                return new Response(Response.Status.DROP, null, null);
            }

            if (splitted.length != 2) {
                out.writeObject("Invalid request size. Possible options: [ GA/DE ];[ ROS/BEA/BUK ]");

                return new Response(Response.Status.INVALID, null, null);
            }

            final Worker worker;
            switch (splitted[0]) {
                case "GA":
                    worker = new WorkerGA(dao);
                    break;
                case "DE":
                    worker = new WorkerDE(dao);
                    break;
                default:
                    out.writeObject("Invalid algorithm. Possible options: GA, DE.");

                    return new Response(Response.Status.INVALID, null, null);
            }
            final TestFunction testFunction;
            switch (splitted[1]) {
                case "ROS":
                    testFunction = new RosenbrockFunction();
                    break;
                case "BEA":
                    testFunction = new BealeFunction();
                    break;
                case "BUK":
                    testFunction = new BukinN6Function();
                    break;
                default:
                    out.writeObject("Invalid function. Possible options: ROS,BEA,BUK.");

                    return new Response(Response.Status.INVALID, null, null);
            }

            worker.setTestFunction(testFunction);
            worker.run(out);

            final Results results = dao.getResults(testFunction.getClass(), splitted[0]).get();
            return new Response(Response.Status.OK, results, testFunction);

        } catch (final IOException e) {
            e.printStackTrace();
            return new Response(Response.Status.INVALID, null, null);
        }
    }
}
