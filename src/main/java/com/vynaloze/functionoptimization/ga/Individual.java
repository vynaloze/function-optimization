package com.vynaloze.functionoptimization.ga;

import com.vynaloze.functionoptimization.functions.Domain;
import java.util.Random;
import java.util.function.BiFunction;

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

    public void evaluateFitness(final BiFunction<Double, Double, Double> function) {
        functionValue = function.apply(getChromosome().getGeneX(), getChromosome().getGeneY());
        fitnessValue = 1.0 / functionValue;
    }

    public void mutate() {
        chromosome.mutate();
    }

    public static Individual crossover(final Individual i1, final Individual i2) {
        final double x = random.nextBoolean() ? i1.getChromosome().getGeneX() : i2.getChromosome().getGeneX();
        final double y = random.nextBoolean() ? i1.getChromosome().getGeneY() : i2.getChromosome().getGeneY();
        return new Individual(new Chromosome(x, y));
    }

    @Override
    public String toString() {
        return "x=" + chromosome.getGeneX()
                + " y=" + chromosome.getGeneY()
                + "; f(x,y)=" + functionValue;
    }
}
