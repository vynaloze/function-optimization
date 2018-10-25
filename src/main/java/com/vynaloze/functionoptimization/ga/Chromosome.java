package com.vynaloze.functionoptimization.ga;

import com.vynaloze.functionoptimization.functions.Domain;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chromosome {
    private double geneX;
    private double geneY;
    private static final Random random = new Random();

    public Chromosome(final Domain domain) {
        this.geneX = ThreadLocalRandom.current().nextDouble(domain.getMinX(), domain.getMaxX());
        this.geneY = ThreadLocalRandom.current().nextDouble(domain.getMinY(), domain.getMaxY());
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
        final double mutationValue = random.nextDouble() * (random.nextBoolean() ? 1 : -1);
        if (random.nextBoolean()) {
            geneX += mutationValue * geneX;
        } else {
            geneY += mutationValue * geneY;
        }
    }
}
