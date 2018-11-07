package com.vynaloze.fo.ga;

import com.vynaloze.fo.Results;
import com.vynaloze.fo.Worker;
import com.vynaloze.fo.dao.Dao;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class WorkerGA extends Worker {
    private final Random random = new Random();
    private List<Individual> population = new ArrayList<>();

    public WorkerGA(final Dao dao) {
        super(dao);
    }

    @Override
    public void run(final ObjectOutputStream out) throws IOException {
        final Results results = new Results(testFunction.getClass(), "GA");

        out.writeObject("Creating new population with size " + Params.POP_SIZE);
        for (int i = 0; i < Params.POP_SIZE; i++) {
            population.add(new Individual(testFunction.getDomain()));
        }

        for (int i = 0; i < Params.ITERATIONS; i++) {
            final List<Individual> newPopulation = new ArrayList<>();

            out.writeObject("Iteration " + i + "/" + Params.ITERATIONS);
//            out.writeObject("a) Evaluating fitness.");
            for (final Individual individual : population) {
                individual.evaluateFitness(testFunction);
            }

//            population.sort(Comparator.comparingDouble(Individual::getFitnessValue).reversed());
//            out.writeObject("Best individuals:");
//            for (int j = 0; j < 5; j++) {
//                final Individual individual = population.get(j);
//                out.writeObject(j + ". " + individual);
//            }
//            out.writeObject("Worst individuals:");
//            for (int j = 0; j < 5; j++) {
//                final Individual individual = population.get(population.size() - 1 - j);
//                out.writeObject(j + ". " + individual);
//            }

//            out.writeObject("b) Selection with crossover.");
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

//            out.writeObject("c) Mutation");
            for (final Individual individual : newPopulation) {
                if (random.nextDouble() < Params.MUTATION_RATE) {
                    individual.mutate();
                }
            }

            population = newPopulation;

            out.writeObject("Best Individual: ");
            final Individual best = population.stream().max(Comparator.comparingDouble(Individual::getProbability)).get();
            out.writeObject(best.toString());
            //todo as param - do not store results if not visualise
            //final Coord coord = new Coord(best.getChromosome().getGeneX(), best.getChromosome().getGeneY(), best.getFunctionValue());
            //results.add(coord);
        }
        out.writeObject("Finished.");
        dao.putResults(results);
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
