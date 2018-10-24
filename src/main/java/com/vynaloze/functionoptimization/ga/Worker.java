package com.vynaloze.functionoptimization.ga;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public class Worker {
    private final Random random = new Random();
    private List<Individual> population = new ArrayList<>();
    private final BiFunction<Double, Double, Double> rosenbrock = (x, y) -> Math.pow(1.0 - x, 2) + 100.0 * Math.pow((y - Math.pow(x, 2)), 2);

    public void run() {
        for (int i = 0; i < Params.POP_SIZE; i++) {
            population.add(new Individual());
        }

        for (int i = 0; i < Params.ITERATIONS; i++) {
            final List<Individual> newPopulation = new ArrayList<>();

//            System.out.println("Computing fitness");
            for (final Individual individual : population) {
                individual.evaluateFitness(rosenbrock);
            }
            final double totalFitness = population.stream().mapToDouble(Individual::getFitnessValue).sum();
            final double bestFitness = population.stream().mapToDouble(Individual::getFitnessValue).max().getAsDouble();
//            System.out.println("Total fitness: " + totalFitness);
//            System.out.println("Best fitness: " + bestFitness);

//            System.out.println("Calculating probabilities");
            for (final Individual individual : population) {
                individual.setProbability(individual.getFitnessValue() / totalFitness);
            }

//            System.out.println("Reproducing... Hehe.");
            while (newPopulation.size() < Params.POP_SIZE) {
                final Individual parent1 = pick();
                final Individual parent2 = pick();
                if (random.nextDouble() < Params.CROSSOVER_RATE) {
                    newPopulation.add(Individual.crossover(parent1, parent2));
                    newPopulation.add(Individual.crossover(parent1, parent2));
                } else {
                    newPopulation.add(parent1);
                    newPopulation.add(parent2);
                }
            }

//            System.out.println("Mutating");
            for (final Individual individual : newPopulation) {
                if (random.nextDouble() < Params.MUTATION_RATE) {
                    individual.mutate();
                }
            }

            population = newPopulation;

            Individual best = population.stream().max(Comparator.comparingDouble(Individual::getProbability)).get();
            System.out.println(best);
        }
        System.out.println("Finished");
    }

    private Individual pick(){
        final double probability = random.nextDouble();
        double cumulativeProbability = 0.0;
        for(final Individual individual : population){
            cumulativeProbability += individual.getProbability();
            if(cumulativeProbability >= probability){
                return individual;
            }
        }
        return null;
    }

}
