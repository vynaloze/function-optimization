package com.vynaloze.fo.functions;

import java.util.List;

@SuppressWarnings("unused")
public class RosenbrockFunction extends TestFunction {
    public RosenbrockFunction() {
        super(new Domain(new Domain.Range(-100, 100), new Domain.Range(-100, 100)));
    }

    @Override
    public double apply(final List<Double> args) {
        return Math.pow(1.0 - args.get(0), 2) + 100.0 * Math.pow((args.get(1) - Math.pow(args.get(0), 2)), 2);
    }

    // expected min: f(1,1) = 0
}
