package com.vynaloze.functionoptimization.ga;

import java.util.Random;

public class Chromosome {
    private double geneX;
    private double geneY;

    public Chromosome() {
        this.geneX = GARandom.get();
        this.geneY = GARandom.get();
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
        final Random random = new Random();
        final double mutationValue = random.nextDouble() * (random.nextBoolean() ? 1 : -1);
        if (random.nextBoolean()) {
            geneX += mutationValue * geneX;
        } else {
            geneY += mutationValue * geneY;
        }
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "geneX=" + geneX +
                ", geneY=" + geneY +
                '}';
    }
}
