package com.vynaloze.fo.net;

import com.vynaloze.fo.Response;
import com.vynaloze.fo.Worker;
import com.vynaloze.fo.dao.Dao;
import com.vynaloze.fo.de.WorkerDE;
import com.vynaloze.fo.functions.TestFunction;
import com.vynaloze.fo.ga.WorkerGA;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Controller {
    private final Dao dao;

    public Controller() {
        dao = new Dao();
    }

    public Response process(final String request, final ObjectOutputStream out) {
        try {
            final String[] splitted = request.split(";");

            if (splitted[0].equalsIgnoreCase(Response.Status.DROP.getStatus())) {
                return new Response(Response.Status.DROP);
            }

            if (splitted.length < 2 || splitted.length > 3) {
                out.writeObject("Invalid request size. Possible options: [ GA/DE ];[ ROS/BEA/BUK ](;VIS)");

                return new Response(Response.Status.INVALID);
            }

            final Worker worker;
            switch (splitted[0].toUpperCase()) {
                case "GA":
                    worker = new WorkerGA(dao);
                    break;
                case "DE":
                    worker = new WorkerDE(dao);
                    break;
                default:
                    out.writeObject("Invalid algorithm. Possible options: GA, DE.");

                    return new Response(Response.Status.INVALID);
            }

            final TestFunction testFunction = getFunctionFrom(splitted[1]);
            if (testFunction == null) {
                out.writeObject("Function not found");
                return new Response(Response.Status.INVALID);
            }

            worker.setTestFunction(testFunction);
            worker.run(out);

            final boolean visualise = splitted.length == 3;
            if (visualise) {
                return new Response(Response.Status.OK, dao.getResults(testFunction.getClass(), splitted[0]), testFunction);
            } else {
                return new Response(Response.Status.OK);
            }

        } catch (final IOException e) {
            e.printStackTrace();
            return new Response(Response.Status.INVALID);
        }
    }

    private TestFunction getFunctionFrom(final String className) {
        final String packagePrefix = "com.vynaloze.fo.functions.";
        try {
            final Class<?> clazz = Class.forName(packagePrefix + className);
            final Constructor<?> ctor = clazz.getConstructor();
            return (TestFunction) ctor.newInstance();
        } catch (final ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return null;
    }
}
