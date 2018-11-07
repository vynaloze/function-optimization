package com.vynaloze.fo.de;

import com.vynaloze.fo.functions.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Chromosome {
    private final List<Double> genes;

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
}
