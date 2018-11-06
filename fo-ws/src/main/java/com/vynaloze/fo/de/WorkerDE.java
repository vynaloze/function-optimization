package com.vynaloze.fo.de;

import com.vynaloze.fo.Coord;
import com.vynaloze.fo.Results;
import com.vynaloze.fo.Worker;
import com.vynaloze.fo.dao.Dao;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class WorkerDE extends Worker {
    private List<Individual> population = new ArrayList<>();

    public WorkerDE(final Dao dao) {
        super(dao);
    }

    @Override
    public void run(final PrintWriter out) {
        final Results results = new Results(testFunction.getClass(), "DE");

        out.println("Creating new population with size " + Params.POP_SIZE);
        for (int i = 0; i < Params.POP_SIZE; i++) {
            population.add(new Individual(testFunction.getDomain()));
        }

        for (int iteration = 0; iteration < Params.ITERATIONS; iteration++) {
            final List<Individual> newPopulation = new ArrayList<>();

            out.println("Iteration " + iteration + "/" + Params.ITERATIONS);


            for (int target = 0; target < population.size(); target++) {
//                out.println("a) Take 3 distinct parents.");
                final List<Individual> candidates = getThreeDistinctParents(target);
//                out.println("b) Mutate.");
                final Individual mutated = Individual.mutate(candidates);
//                out.println("c) Crossover.");
                final Individual targetCandidate = population.get(target);
                final Individual offspring = Individual.crossover(targetCandidate, mutated);
//                out.println("d) Selection");
                targetCandidate.evaluateFitness(testFunction);
                offspring.evaluateFitness(testFunction);
//                out.println("Target: " + targetCandidate);
//                out.println("Offspring: " + offspring);

                if (targetCandidate.getFitnessValue() < offspring.getFitnessValue()) {
//                    out.println("e) Replacing.");
                    newPopulation.add(offspring);
                } else {
                    newPopulation.add(targetCandidate);
                }
            }
            population = newPopulation;

            out.print("Best Individual: ");
            final Individual best = population.stream().max(Comparator.comparingDouble(Individual::getFitnessValue)).get();
            out.println(best);
            final Coord coord = new Coord(best.getChromosome().getGeneX(), best.getChromosome().getGeneY(), best.getFunctionValue());
            results.add(coord);
        }
        out.println("Finished.");
        dao.putResults(results);
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
