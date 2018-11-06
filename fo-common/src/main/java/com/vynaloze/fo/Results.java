package com.vynaloze.fo;

import com.vynaloze.fo.functions.TestFunction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Results implements Serializable {
    private final Class<? extends TestFunction> function;
    private final String algorithm;
    private final List<Coord> points;

    public Results(final Class<? extends TestFunction> function, final String algorithm) {
        this.function = function;
        this.algorithm = algorithm;
        this.points = new ArrayList<>();
    }

    public void add(final Coord coord) {
        points.add(coord);
    }

    public Class<? extends TestFunction> getFunction() {
        return function;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public List<Coord> getPoints() {
        return points;
    }
}
