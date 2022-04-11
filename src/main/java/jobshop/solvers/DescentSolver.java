package jobshop.solvers;

import jobshop.Instance;
import jobshop.encodings.Schedule;
import jobshop.encodings.Task;
import jobshop.solvers.neighborhood.Neighborhood;

import java.util.ArrayList;
import java.util.Optional;

/** An empty shell to implement a descent solver. */
public class DescentSolver implements Solver {

    final Neighborhood neighborhood;
    final Solver baseSolver;


    /** Creates a new array list of all blocks of a critical path.
     *
     * @param instance the current studied instance.
     * @param criticalPath the critical path to study.
     * @return the list of all blocks of a critical path
     */
    public ArrayList<ArrayList> blocksOfCriticalPath(Instance instance , ArrayList<Task> criticalPath) {
        // blocks contient la liste des blocks
        ArrayList<ArrayList> blocks = new ArrayList<>();
        // block contient les taches contenues dans un block
        ArrayList<Task> block = new ArrayList<>();

        // on va regarder pour chaque machine si elle a des blocks
        for(int m = 0; m<instance.numMachines; m++) {
            // on initialise un block
            block = new ArrayList<>();

            // pour chaque tache du chemin critique on va regarder si elle appartient à un block
            for (int i = 0; i < criticalPath.size() - 1; i++) {
                // on regarde la tache actuelle et la suivante
                Task currentTask = criticalPath.get(i);
                Task nexTask = criticalPath.get(i + 1);

                // Si la tache actuelle appartient a la machine et la taille du block est de 0 alors on l'ajoute
                if (instance.machine(currentTask) == m && block.size() == 0) {
                    block.add(currentTask);
                }
                // si l'actuelle et la suivante valent m alors on ajoute la suivante
                // (l'actuelle est sensee y etre deja)
                else if (instance.machine(currentTask) == m && instance.machine(nexTask) == m) {
                    block.add(nexTask);
                }
                // si aucun des cas precedent et le block est plus grand que 1 alors on a un block
                else if (block.size() > 1) {
                    blocks.add(block);
                    block = new ArrayList<>();
                }
            }
        }
        return blocks;
    }

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
        throw new UnsupportedOperationException();
    }

}
