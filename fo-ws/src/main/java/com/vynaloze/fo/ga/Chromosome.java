package com.vynaloze.fo.ga;

import com.vynaloze.fo.functions.Domain;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chromosome {
    private final List<Double> genes;
    private static final Random random = new Random();

    public Chromosome(final Domain domain) {
        this.genes = new ArrayList<>();
        for (final Domain.Range range : domain.getRanges()) {
            genes.add(ThreadLocalRandom.current().nextDouble(range.getMin(), range.getMax()));
        }
    }

    public Chromosome(final List<Double> genes) {
        this.genes = genes;
    }

    public double getGene(final int position) {
        return genes.get(position);
    }

    public List<Double> getGenes() {
        return genes;
    }

    public void mutate() {
        final int index = random.nextInt(genes.size());
        final double old = genes.remove(index);
        genes.add(index, randomize(old));
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
