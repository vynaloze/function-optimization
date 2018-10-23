package com.vynaloze.functionoptimization.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Worker {
    private final Random random = new Random();
    private List<Individual> population = new ArrayList<>();
    private final BiFunction<Double, Double, Double> rosenbrock = (x, y) -> Math.pow(1 - x, 2) + 100 * Math.pow((y - Math.pow(x, 2)), 2);

    public void run() {
        for (int i = 0; i < Params.POP_SIZE; i++) {
            population.add(new Individual());
        }

        for (int i = 0; i < Params.ITERATIONS; i++) {
            final List<Individual> newPopulation = new ArrayList<>();

            System.out.println("Computing fitness");
            for (final Individual individual : population) {
                individual.evaluateFitness(rosenbrock);
            }
            final double totalFitness = population.stream().map(Individual::getFitnessValue).reduce(Double::sum).get();
            final double bestFitness = population.stream().map(Individual::getFitnessValue).max(Double::compareTo).get();
            System.out.println("Total fitness: " + totalFitness);
            System.out.println("Best fitness: " + bestFitness);

            System.out.println("Reproducing... Hehe.");

            for (int p = 0; p < Params.POP_SIZE; p++) {

            }

        }
    }

}
