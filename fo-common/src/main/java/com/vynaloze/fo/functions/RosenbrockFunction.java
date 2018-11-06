package com.vynaloze.fo.functions;

public class RosenbrockFunction extends TestFunction {
    public RosenbrockFunction() {
        super(new Domain(-100, 100, -100, 100));
    }

    @Override
    public Double apply(final Double x, final Double y) {
        return Math.pow(1.0 - x, 2) + 100.0 * Math.pow((y - Math.pow(x, 2)), 2);
    }

    // expected min: f(1,1) = 0
}
