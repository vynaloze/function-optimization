package com.vynaloze.fo.functions;

public class BukinN6Function extends TestFunction {
    public BukinN6Function() {
        super(new Domain(-15, -5, -3, 3));
    }

    @Override
    public Double apply(final Double x, final Double y) {
        return 100.0 * Math.sqrt(Math.abs(y - 0.01 * Math.pow(x, 2))) + 0.01 * Math.abs(x + 10);
    }

    // expected min: f(-10,1) = 0
}
