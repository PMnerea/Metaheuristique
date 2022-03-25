package jobshop.solvers;

import jobshop.Instance;
import jobshop.encodings.Schedule;

import java.util.ArrayList;
import java.util.Optional;

/** An empty shell to implement a greedy solver. */
public class GreedySolver implements Solver {

    /** All possible priorities for the greedy solver. */
    public enum Priority {
        SPT, LPT, SRPT, LRPT, EST_SPT, EST_LPT, EST_SRPT, EST_LRPT
    }

    /** Priority that the solver should use. */
    final Priority priority;

    /** Creates a new greedy solver that will use the given priority. */
    public GreedySolver(Priority p) {
        this.priority = p;
    }

    // TODO - Implement a greedy solver
    @Override
    public Optional<Schedule> solve(Instance instance, long deadline) {
        // Solution is represented by a resource order object

        // Set of possible tasks, a task is a tuple (job_number,task_number)
        ArrayList<Tuple> possibleTasks = new ArrayList<Tuple>();
        // Initialize possible tasks with the initial tasks from all the jobs

        throw new UnsupportedOperationException();
    }
}
