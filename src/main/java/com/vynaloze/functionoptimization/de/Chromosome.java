package com.vynaloze.functionoptimization.de;

import com.vynaloze.functionoptimization.functions.Domain;
import java.util.concurrent.ThreadLocalRandom;

public class Chromosome {
    private final double geneX;
    private final double geneY;

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
}
