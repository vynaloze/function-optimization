package com.vynaloze.fo.ga;

import com.vynaloze.fo.functions.Domain;
import com.vynaloze.fo.functions.TestFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Individual {
    private final Chromosome chromosome;
    private double functionValue;
    private double fitnessValue;
    private double probability;
    private static final Random random = new Random();

    public Individual(final Domain domain) {
        this.chromosome = new Chromosome(domain);
    }

    public Individual(final Chromosome chromosome) {
        this.chromosome = chromosome;
    }

    public Chromosome getChromosome() {
        return chromosome;
    }

    public double getFunctionValue() {
        return functionValue;
    }

    public double getFitnessValue() {
        return fitnessValue;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(final double probability) {
        this.probability = probability;
    }

    public void evaluateFitness(final TestFunction function) {
        functionValue = function.apply(getChromosome().getGenes());
        fitnessValue = 1.0 / functionValue;
    }

    public void mutate() {
        chromosome.mutate();
    }

    public static Individual crossover(final Individual i1, final Individual i2) {
        final List<Double> genes = new ArrayList<>();
        for (int i = 0; i < i1.chromosome.getGenes().size(); i++) {
            genes.add(random.nextBoolean() ? i1.getChromosome().getGene(i) : i2.getChromosome().getGene(i));
        }
        return new Individual(new Chromosome(genes));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final Double gene : chromosome.getGenes()) {
            sb.append(gene.toString()).append(", ");
        }
        sb.append("f(x,y)=").append(functionValue);
        return sb.toString();
    }
}
