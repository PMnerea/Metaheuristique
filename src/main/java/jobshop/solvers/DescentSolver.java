package jobshop.solvers;

import jobshop.Instance;
import jobshop.encodings.ResourceOrder;
import jobshop.encodings.Schedule;
import jobshop.encodings.Task;
import jobshop.solvers.neighborhood.Neighborhood;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** An empty shell to implement a descent solver. */
public class DescentSolver implements Solver {

    final Neighborhood neighborhood;
    final Solver baseSolver;


    /** Creates a new descent solver with a given neighborhood and a solver for the initial solution.
     *
     * @param neighborhood Neighborhood object that should be used to generates neighbor solutions to the current candidate.
     * @param baseSolver A solver to provide the initial solution.
     */
    public DescentSolver(Neighborhood neighborhood, Solver baseSolver) {
        this.neighborhood = neighborhood;
        this.baseSolver = baseSolver;
    }

    // TODO - Implement a descent solver using Nowicki and Smutnicki neighborhood
    @Override
    public Optional<Schedule> solve(Instance instance, long deadline) {
        Optional<Schedule> schedule = this.baseSolver.solve(instance, deadline);

        boolean foundSolution = false;
        while (!foundSolution) {
            List<ResourceOrder> neighborhood = this.neighborhood.generateNeighbors(new ResourceOrder(schedule.get()));
            int bestNeighbor = -1;
            int bestNeighborSpan = -1;
            int bestMakespan = schedule.get().makespan();

            for (int i=0; i<neighborhood.size(); i++) {
                int currentSpan = neighborhood.get(i).toSchedule().get().makespan();
                if (currentSpan < bestNeighborSpan || bestNeighborSpan == -1) {
                    bestNeighborSpan = currentSpan;
                    bestNeighbor = i;
                }
            }

            if (bestNeighborSpan >= bestMakespan) {
                foundSolution = true;
            }
            else {
                schedule = neighborhood.get(bestNeighbor).toSchedule();
            }
        }

        return schedule;
    }

}
