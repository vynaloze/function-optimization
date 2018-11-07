package com.vynaloze.fo.functions;

import java.io.Serializable;
import java.util.List;

public abstract class TestFunction implements Serializable {
    private final Domain domain;

    public TestFunction(final Domain domain) {
        this.domain = domain;
    }

    public Domain getDomain() {
        return domain;
    }

    public abstract double apply(final List<Double> args);
}
