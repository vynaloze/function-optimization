package com.vynaloze.fo.functions;

import java.util.List;

@SuppressWarnings("unused")
public class BukinN6Function extends TestFunction {
    public BukinN6Function() {
        super(new Domain(new Domain.Range(-15, -5), new Domain.Range(-3, 3)));
    }

    @Override
    public double apply(final List<Double> args) {
        return 100.0 * Math.sqrt(Math.abs(args.get(1) - 0.01 * Math.pow(args.get(0), 2))) + 0.01 * Math.abs(args.get(0) + 10);
    }

    // expected min: f(-10,1) = 0
}
