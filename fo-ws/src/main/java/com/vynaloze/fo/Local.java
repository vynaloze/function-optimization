package com.vynaloze.fo;

import com.vynaloze.fo.functions.RosenbrockFunction;
import com.vynaloze.fo.ga.WorkerGA;
import java.io.PrintWriter;

public class Local {
    public static void main(String[] args) {
        WorkerGA worker = new WorkerGA();
        worker.setTestFunction(new RosenbrockFunction());
        worker.run(new PrintWriter(System.out));
    }
}
