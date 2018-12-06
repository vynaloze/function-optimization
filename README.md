# Function optimization exercise for PPS - report

### Structure

The application consists of three modules:
- __fo-ws__ - server, which handles clients' requests to optimize function given as a simple class name (which implements TestFunction interface). All the computing is done on the server and the results are returned to the client.
- __fo-client__ - client asks server to optimize a function and return the result. Optionally, visualization of the test function along with the results of every iteration can be requested. The visualization is performed by the client.
- __fo-common__ - contains modules required by both modules. It also contains definitions of the test functions.

### Genetic Algorithm

#### Steps:
1. Generate new population with POP_SIZE individuals.
2. Do the next steps for ITERATIONS iterations:
3. Evaluate fitness for each individual.
4. Based on the fitness, calculate probability of being chosen to the next generation for each individual.
5. Pick random (with regard to calculated probability) individuals to the new generation until it reaches POP_SIZE. About CROSSOVER_RATE % of individuals will crossover and pass their offsprings to the new population instead.
6. Mutate MUTATION_RATE % of individuals.

#### Specifics:
1. Fitness - (1/functionValue) to get larger value of fitness with the smaller function value
2. Crossover - create a new individual, building chromosome in following way: for each gene, pick it from a random parent.
3. Mutation - randomize each gene using random factor.


### Differential Evolution Algorithm

#### Steps:
1. Generate new population with POP_SIZE individuals.
2. Do the next steps for ITERATIONS iterations:
3. Do the next steps for each individual:
4. Pick 3 distinct, random parents.
5. Mutate them, resulting in one mutated individual.
6. Crossover mutated individual with the currently processed (_target_) individual.
7. Evaluate fitness values of the offspring and target individual and add the better to the new generation. 

#### Specifics:
1. Fitness - (1/functionValue) to get larger value of fitness with the smaller function value
2. Crossover - create a new individual, building chromosome in following way: for each gene, pick it either from the offspring or the target individual. Note: at least one gene must come from the offspring.
3. Mutation - create new individual using following formula: I2 + (I1 - I0) * EVOLUTION_RATE
