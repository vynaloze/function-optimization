package com.vynaloze.fo.ga;

import com.vynaloze.fo.Worker;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class WorkerGA extends Worker {
    private final Random random = new Random();
    private List<Individual> population = new ArrayList<>();

    @Override
    public void run(final PrintWriter out) {
        out.println("Creating new population with size " + Params.POP_SIZE);
        for (int i = 0; i < Params.POP_SIZE; i++) {
            population.add(new Individual(testFunction.getDomain()));
        }

        for (int i = 0; i < Params.ITERATIONS; i++) {
            final List<Individual> newPopulation = new ArrayList<>();

            out.println("Iteration " + i + "/" + Params.ITERATIONS);
            out.println("a) Evaluating fitness.");
            for (final Individual individual : population) {
                individual.evaluateFitness(testFunction);
            }

            population.sort(Comparator.comparingDouble(Individual::getFitnessValue).reversed());
            out.println("Best individuals:");
            for (int j = 0; j < 5; j++) {
                final Individual individual = population.get(j);
                out.println(j + ". " + individual);
            }
            out.println("Worst individuals:");
            for (int j = 0; j < 5; j++) {
                final Individual individual = population.get(population.size() - 1 - j);
                out.println(j + ". " + individual);
            }

            out.println("b) Selection with crossover.");
            calculateProbabilities();
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

            out.println("c) Mutation");
            for (final Individual individual : newPopulation) {
                if (random.nextDouble() < Params.MUTATION_RATE) {
                    individual.mutate();
                }
            }

            population = newPopulation;
        }
        out.println("Finished.");
        out.print("Best Individual: ");
        final Individual best = population.stream().max(Comparator.comparingDouble(Individual::getProbability)).get();
        out.println(best);
    }

    private void calculateProbabilities() {
        final double totalFitness = population.stream().mapToDouble(Individual::getFitnessValue).sum();
        for (final Individual individual : population) {
            individual.setProbability(individual.getFitnessValue() / totalFitness);
        }
    }

    private Individual pick() {
        final double probability = random.nextDouble();
        double cumulativeProbability = 0.0;
        for (final Individual individual : population) {
            cumulativeProbability += individual.getProbability();
            if (cumulativeProbability >= probability) {
                return individual;
            }
        }
        return null;
    }
}