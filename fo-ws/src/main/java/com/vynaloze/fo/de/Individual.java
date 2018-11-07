package com.vynaloze.fo.de;

import com.vynaloze.fo.functions.Domain;
import com.vynaloze.fo.functions.TestFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void evaluateFitness(final TestFunction function) {
        functionValue = function.apply(getChromosome().getGenes());
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
        final List<Double> genes = new ArrayList<>();
        for (final Double gene : c.getGenes()) {
            genes.add(F * gene);
        }
        return new Individual(new Chromosome(genes));
    }

    private static Individual add(final Individual i1, final Individual i2) {
        final Chromosome c1 = i1.getChromosome();
        final Chromosome c2 = i2.getChromosome();
        final List<Double> genes = new ArrayList<>();
        for (int i = 0; i < c1.getGenes().size(); i++) {
            final double c1Gene = c1.getGene(i);
            final double c2Gene = c2.getGene(i);
            genes.add(c1Gene + c2Gene);
        }
        return new Individual(new Chromosome(genes));
    }

    private static Individual substract(final Individual i1, final Individual i2) {
        final Chromosome c1 = i1.getChromosome();
        final Chromosome c2 = i2.getChromosome();
        final List<Double> genes = new ArrayList<>();
        for (int i = 0; i < c1.getGenes().size(); i++) {
            final double c1Gene = c1.getGene(i);
            final double c2Gene = c2.getGene(i);
            genes.add(c1Gene - c2Gene);
        }
        return new Individual(new Chromosome(genes));
    }

    public static Individual crossover(final Individual target, final Individual offspring) {
        final int size = target.getChromosome().getGenes().size();
        final int forcePick = random.nextInt(size);
        final List<Double> genes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final double probability = random.nextDouble();
            genes.add((probability < Params.CROSSOVER_RATE || i == forcePick) ? offspring.getChromosome().getGene(i) : target.getChromosome().getGene(i));
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
