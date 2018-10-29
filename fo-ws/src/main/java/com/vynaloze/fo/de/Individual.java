package com.vynaloze.fo.de;

import com.vynaloze.fo.functions.Domain;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public class Individual {
    private final Chromosome chromosome;
    private double functionValue;
    private double fitnessValue;
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

    public void evaluateFitness(final BiFunction<Double, Double, Double> function) {
        functionValue = function.apply(getChromosome().getGeneX(), getChromosome().getGeneY());
        fitnessValue = 1.0 / functionValue;
    }

    public static Individual mutate(final List<Individual> candidates) {
        return Individual.add(
                candidates.get(2),
                Individual.multiplyByConstant(
                        Individual.substract(
                                candidates.get(1),
                                candidates.get(0)),
                        Params.EVOLUTION_RATE));
    }

    private static Individual multiplyByConstant(final Individual i, final double F) {
        final Chromosome c = i.getChromosome();
        final Chromosome chromosome = new Chromosome(F * c.getGeneX(), F * c.getGeneY());
        return new Individual(chromosome);
    }

    private static Individual add(final Individual i1, final Individual i2) {
        final Chromosome c1 = i1.getChromosome();
        final Chromosome c2 = i2.getChromosome();
        final Chromosome chromosome = new Chromosome(c1.getGeneX() + c2.getGeneX(), c1.getGeneY() + c2.getGeneY());
        return new Individual(chromosome);
    }

    private static Individual substract(final Individual i1, final Individual i2) {
        final Chromosome c1 = i1.getChromosome();
        final Chromosome c2 = i2.getChromosome();
        final Chromosome chromosome = new Chromosome(c1.getGeneX() - c2.getGeneX(), c1.getGeneY() - c2.getGeneY());
        return new Individual(chromosome);
    }

    public static Individual crossover(final Individual target, final Individual offspring) {
        final boolean forcePickX = random.nextBoolean();
        final boolean forcePickY = !forcePickX;
        double probability = random.nextDouble();
        final double x = (probability < Params.CROSSOVER_RATE || forcePickX) ? offspring.getChromosome().getGeneX() : target.getChromosome().getGeneX();
        probability = random.nextDouble();
        final double y = (probability < Params.CROSSOVER_RATE || forcePickY) ? offspring.getChromosome().getGeneY() : target.getChromosome().getGeneY();
        return new Individual(new Chromosome(x, y));
    }

    @Override
    public String toString() {
        return "x=" + chromosome.getGeneX()
                + " y=" + chromosome.getGeneY()
                + "; f(x,y)=" + functionValue;
    }
}
