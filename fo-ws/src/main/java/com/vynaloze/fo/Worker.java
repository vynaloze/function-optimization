package com.vynaloze.fo;

import com.vynaloze.fo.functions.TestFunction;
import java.io.PrintWriter;

public abstract class Worker {
    protected TestFunction testFunction;

    public void setTestFunction(TestFunction testFunction) {
        this.testFunction = testFunction;
    }

    public abstract void run(final PrintWriter out);
}
