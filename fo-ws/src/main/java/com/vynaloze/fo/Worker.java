package com.vynaloze.fo;

import com.vynaloze.fo.dao.Dao;
import com.vynaloze.fo.functions.TestFunction;
import java.io.PrintWriter;

public abstract class Worker {
    protected TestFunction testFunction;
    protected final Dao dao;

    protected Worker(final Dao dao) {
        this.dao = dao;
    }

    public void setTestFunction(TestFunction testFunction) {
        this.testFunction = testFunction;
    }

    public abstract void run(final PrintWriter out);
}
