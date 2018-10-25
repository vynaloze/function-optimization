package com.vynaloze.functionoptimization.functions;

import java.util.function.BiFunction;

public abstract class TestFunction implements BiFunction<Double, Double, Double> {
    private final Domain domain;

    public TestFunction(final Domain domain) {
        this.domain = domain;
    }

    public Domain getDomain() {
        return domain;
    }
}
