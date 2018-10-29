package com.vynaloze.fo.functions;

public class BealeFunction extends TestFunction {
    public BealeFunction() {
        super(new Domain(-4.5, 4.5, -4.5, 4.5));
    }

    @Override
    public Double apply(final Double x, final Double y) {
        return Math.pow(1.5 - x + x * y, 2) + Math.pow(2.25 - x + x * Math.pow(y, 2), 2) + Math.pow(2.625 - x + x * Math.pow(y, 3), 2);
    }

    // expected min: f(3,0.5) = 0
}
