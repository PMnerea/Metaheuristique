package jobshop.solvers;

import jobshop.Instance;
import jobshop.encodings.ResourceOrder;
import jobshop.encodings.Schedule;
import jobshop.solvers.neighborhood.Neighborhood;
import jobshop.solvers.neighborhood.Nowicki;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Taboo implements Solver {

    final Solver baseSolver;

    final int maxIteration;

    final Neighborhood neighborhood = new Nowicki();

    public Taboo(Solver solver, int maxIteration){
        this.baseSolver = solver;
        this.maxIteration = maxIteration;
    }

    // TODO - Second version -> Create structure to manage taboo solutions
    // Some solutions are taboo for a certain time
    // TODO - Third version -> Accept Taboo solution when it improves solution
    @Override
    public  Optional<Schedule> solve(Instance instance, long deadline){

        Optional<Schedule> current = baseSolver.solve(instance,deadline);
        assert current.isPresent();

        // Init best solution
        Schedule bestSolution = current.get();
        Schedule localBestSolution = current.get();

        // Add base solution to tabou solution list
        ArrayList<Schedule> tabooSolutions = new ArrayList<>();
        tabooSolutions.add(current.get());

        // Init iterator variable
        int iterator = 0;

        List<ResourceOrder> neighbors;
        while(iterator>=maxIteration){
            iterator++;
            neighbors = neighborhood.generateNeighbors(new ResourceOrder(localBestSolution));
            // Find best neighbor
            for (ResourceOrder neighbor : neighbors) {
                if (neighbor.toSchedule().get().makespan() < localBestSolution.makespan()) {
                    localBestSolution = neighbor.toSchedule().get();
                }
            }
            tabooSolutions.add(localBestSolution.toSchedule().get());

            if (localBestSolution.makespan() < bestSolution.makespan()){
                bestSolution = localBestSolution;
            }
        }
        return Optional.ofNullable(bestSolution);
    }
}
