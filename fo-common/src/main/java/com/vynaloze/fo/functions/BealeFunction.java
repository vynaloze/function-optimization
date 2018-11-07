package com.vynaloze.fo.functions;

import java.util.List;

public class BealeFunction extends TestFunction {
    public BealeFunction() {
        super(new Domain(new Domain.Range(-4.5, 4.5), new Domain.Range(-4.5, 4.5)));
    }

    @Override
    public double apply(final List<Double> args) {
        return Math.pow(1.5 - args.get(0) + args.get(0) * args.get(1), 2)
                + Math.pow(2.25 - args.get(0) + args.get(0) * Math.pow(args.get(1), 2), 2)
                + Math.pow(2.625 - args.get(0) + args.get(0) * Math.pow(args.get(1), 3), 2);
    }

    // expected min: f(3,0.5) = 0
}
