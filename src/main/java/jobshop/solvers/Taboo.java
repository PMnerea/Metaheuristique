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

    final int tabooTime;

    final Nowicki neighborhood = new Nowicki();

    public Taboo(Solver solver, int maxIteration,int time){
        this.baseSolver = solver;
        this.maxIteration = maxIteration;
        this.tabooTime = time;
    }

    // TODO - Third version -> Accept Taboo solution when it improves solution
    @Override
    public  Optional<Schedule> solve(Instance instance, long deadline){

        long start = System.currentTimeMillis();
        long end = 0;
        Optional<Schedule> current = baseSolver.solve(instance,deadline);
        assert current.isPresent();

        // Init best solution
        Schedule bestSolution = current.get();
        Schedule localBestSolution = current.get();

        // Init iterator variable
        int iterator = 0;

        // Init iterator for swaps
        int indexSwap;
        int indexSelectedSwap;

        // Boolean found a local best
        boolean found;

        // Create Taboo List
        // No need to add current solution because we only keep in memory the taboo swaps
        TabooList tabooList = new TabooList();

        // Swap list
        List<Nowicki.Swap> swaps;
        // Each neighbor is at that same index than its corresponding swap??
        List<ResourceOrder> neighbors;

        while(iterator>=maxIteration){
            found = false;
            // Update tabooList
            tabooList.update();
            iterator++;
            neighbors = neighborhood.generateNeighbors(new ResourceOrder(localBestSolution));
            swaps = neighborhood.allSwaps(new ResourceOrder(localBestSolution));

            // Find best neighbor
            indexSwap = 0;
            indexSelectedSwap = 0;
            for (ResourceOrder neighbor : neighbors) {
                // Check if swap is not part of the taboo list
                if (!tabooList.isPresent(swaps.get(indexSwap))){
                    // Find best neighbor
                    if (neighbor.toSchedule().get().makespan() < localBestSolution.makespan()) {
                        localBestSolution = neighbor.toSchedule().get();
                        indexSelectedSwap = indexSwap;
                        found = true;
                    }
                }
                indexSwap++;
            }

            // Modifications only if a local best is found
            if (found) {
                // Add selected swap to the tabooList
                tabooList.addTaboo(tabooTime, swaps.get(indexSelectedSwap));
                // Check for global best
                if (localBestSolution.makespan() < bestSolution.makespan()) {
                    bestSolution = localBestSolution;
                }
            }

            end = System.currentTimeMillis();
            if ((end - start)> deadline) {break;}
        }
        return Optional.ofNullable(bestSolution);
    }
}
