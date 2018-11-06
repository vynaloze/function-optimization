package com.vynaloze.fo;

import com.vynaloze.fo.dao.Dao;
import com.vynaloze.fo.functions.TestFunction;
import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class Worker {
    protected TestFunction testFunction;
    protected final Dao dao;

    protected Worker(final Dao dao) {
        this.dao = dao;
    }

    public void setTestFunction(final TestFunction testFunction) {
        this.testFunction = testFunction;
    }

    public abstract void run(final ObjectOutputStream out) throws IOException;
}
