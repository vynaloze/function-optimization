package com.vynaloze.fo.ga;

import com.vynaloze.fo.functions.Domain;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chromosome {
    private double geneX;
    private double geneY;
    private static final Random random = new Random();

    public Chromosome(final Domain domain) {
        this.geneX = ThreadLocalRandom.current().nextDouble(domain.getMinX(), domain.getMaxX());  //bukinN6:
        this.geneY = ThreadLocalRandom.current().nextDouble(domain.getMinY(), domain.getMaxY()); //fixme Best Individual: x=-0.4089798587405582 y=0.9195756692939667; f(x,y)=1.3208502897482972
    }

    public Chromosome(final double geneX, final double geneY) {
        this.geneX = geneX;
        this.geneY = geneY;
    }

    public double getGeneX() {
        return geneX;
    }

    public double getGeneY() {
        return geneY;
    }

    public void mutate() {
        if (random.nextBoolean()) {
            geneX = randomize(geneX);
        } else {
            geneY = randomize(geneY);
        }
    }

    private double randomize(final double value) {
        final double mutationValue = random.nextDouble() * (random.nextBoolean() ? 1 : -1);
        if (value <= 0) {
            return value + mutationValue;
        } else {
            return value * mutationValue;
        }
    }
}
