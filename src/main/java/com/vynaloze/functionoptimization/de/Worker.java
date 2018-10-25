package com.vynaloze.functionoptimization.de;

import com.vynaloze.functionoptimization.functions.RosenbrockFunction;
import com.vynaloze.functionoptimization.functions.TestFunction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Worker {
    private List<Individual> population = new ArrayList<>();
    private final TestFunction testFunction = new RosenbrockFunction();

    public void run() {
        System.out.println("Creating new population with size " + Params.POP_SIZE);
        for (int i = 0; i < Params.POP_SIZE; i++) {
            population.add(new Individual(testFunction.getDomain()));
        }

        for (int iteration = 0; iteration < Params.ITERATIONS; iteration++) {
            final List<Individual> newPopulation = new ArrayList<>();

            System.out.println("Iteration " + iteration + "/" + Params.ITERATIONS);


            for (int target = 0; target < population.size(); target++) {
//                System.out.println("a) Take 3 distinct parents.");
                final List<Individual> candidates = getThreeDistinctParents(target);
//                System.out.println("b) Mutate.");
                final Individual mutated = Individual.mutate(candidates);
//                System.out.println("c) Crossover.");
                final Individual targetCandidate = population.get(target);
                final Individual offspring = Individual.crossover(targetCandidate, mutated);
//                System.out.println("d) Selection");
                targetCandidate.evaluateFitness(testFunction);
                offspring.evaluateFitness(testFunction);
//                System.out.println("Target: " + targetCandidate);
//                System.out.println("Offspring: " + offspring);

                if (targetCandidate.getFitnessValue() < offspring.getFitnessValue()) {
//                    System.out.println("e) Replacing.");
                    newPopulation.add(offspring);
                } else {
                    newPopulation.add(targetCandidate);
                }
            }
            population = newPopulation;

        }
        System.out.println("Finished.");
        System.out.print("Best Individual: ");
        final Individual best = population.stream().max(Comparator.comparingDouble(Individual::getFitnessValue)).get();
        System.out.println(best);
    }

    private List<Individual> getThreeDistinctParents(final int current) {
        return ThreadLocalRandom.current()
                .ints(0, population.size())
                .boxed()
                .distinct()
                .filter(i -> i != current)
                .limit(3)
                .map(i -> population.get(i))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
