package com.vynaloze.functionoptimization.ga;

import java.util.Random;
import java.util.function.BiFunction;

public class Individual {
    private final Chromosome chromosome;
    private double fitnessValue;
    private double probability;

    public Individual() {
        this.chromosome = new Chromosome();
    }

    public Individual(final Chromosome chromosome) {
        this.chromosome = chromosome;
    }

    public Chromosome getChromosome() {
        return chromosome;
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
        fitnessValue = 1.0 / function.apply(getChromosome().getGeneX(), getChromosome().getGeneY());
    }

    public void mutate() {
        chromosome.mutate();
    }

    public static Individual crossover(final Individual i1, final Individual i2){
        final Random random = new Random();
        final double x = random.nextBoolean() ? i1.getChromosome().getGeneX() : i2.getChromosome().getGeneX();
        final double y = random.nextBoolean() ? i1.getChromosome().getGeneY() : i2.getChromosome().getGeneY();
        final Chromosome chromosome = new Chromosome(x,y);
        return new Individual(chromosome);
    }

    @Override
    public String toString() {
        return "Individual{" +
                "chromosome=" + chromosome +
                ", fitnessValue=" + fitnessValue +
                ", probability=" + probability +
                '}';
    }
}
