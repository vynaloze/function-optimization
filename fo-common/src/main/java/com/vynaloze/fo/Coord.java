package com.vynaloze.fo;

import java.io.Serializable;

public class Coord implements Serializable {
    public final double x;
    public final double y;
    public final double z;

    public Coord(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
