package com.vynaloze.fo;

import com.vynaloze.fo.dao.Dao;
import com.vynaloze.fo.functions.BealeFunction;
import com.vynaloze.fo.functions.TestFunction;
import com.vynaloze.fo.ga.WorkerGA;

public class Local {
    public static void main(String[] args) throws Exception {
        Dao dao = new Dao();
        Worker worker = new WorkerGA(dao);
        TestFunction testFunction = new BealeFunction();
        worker.setTestFunction(testFunction);
//        worker.run(new PrintWriter(System.out));

//        Results results = dao.getResults(testFunction.getClass(), "GA").get();

    }
}
