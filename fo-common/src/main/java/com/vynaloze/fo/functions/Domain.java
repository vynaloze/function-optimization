package com.vynaloze.fo.functions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Domain implements Serializable {
    private final List<Range> ranges;

    public Domain(final Range... ranges) {
        this.ranges = Arrays.asList(ranges);
    }

    public List<Range> getRanges() {
        return ranges;
    }

    public static class Range implements Serializable {
        private final double min;
        private final double max;

        public Range(final double min, final double max) {
            this.min = min;
            this.max = max;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }
    }
}
