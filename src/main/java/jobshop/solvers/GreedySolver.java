package jobshop.solvers;

import jobshop.Instance;
import jobshop.encodings.Schedule;
import jobshop.encodings.Task;

import java.util.ArrayList;
import java.util.Optional;

/**
 * An empty shell to implement a greedy solver.
 */
public class GreedySolver implements Solver {

    /**
     * Priority that the solver should use.
     */
    final Priority priority;

    /**
     * Creates a new greedy solver that will use the given priority.
     */
    public GreedySolver(Priority p) {
        this.priority = p;
    }

    /**
     * @param job
     * @param instance
     * @param jobsLastDoneTasks Task ArrayList for every last finished tasks for each job
     * @return
     */
    public int computeRemainingTime(int job, Instance instance, ArrayList<Task> jobsLastDoneTasks) {

        int lastDoneTask = jobsLastDoneTasks.get(job).task;
        int remainingTime = 0;
        // Calculate time from remaining tasks
        for (int i = lastDoneTask + 1; i < instance.numTasks; i++) {
            remainingTime += instance.duration(job, i);
        }
        return remainingTime;
    }

    /**
     * @param instance Instance to create list of doable tasks from.
     * @return Task ArrayList of doable Tasks
     */
    public ArrayList<Task> InitDoableTasks(Instance instance) {
        // Set of tasks
        ArrayList<Task> doableTasks = new ArrayList<>();
        // Initialize possible tasks with the initial tasks from all the jobs
        // Same number of tasks per job
        for (int i = 0; i < instance.numJobs; i++) {
            doableTasks.add(i, new Task(i, 0));
        }
        return doableTasks;
    }

    /**
     * @param instance    The current instance
     * @param doableTasks Task ArrayList of doable tasks
     * @return Shortest processing time task in the set
     */
    public Task SPTTask(Instance instance, ArrayList<Task> doableTasks) {
        int min = Integer.MAX_VALUE;
        int index = 0;
        int duration;
        for (int i = 0; i < doableTasks.size(); i++) {
            duration = instance.duration(doableTasks.get(i));
            if (duration < min) {
                min = duration;
                index = i;
            }
        }
        return doableTasks.get(index);
    }

    /**
     * @param instance          The current instance
     * @param doableTasks       Task ArrayList of doable tasks
     * @param jobsLastDoneTasks Task ArrayList for every last finished tasks for each job
     * @return Longest remaining processing time job's task is returned
     */
    public Task LRPTTask(Instance instance, ArrayList<Task> doableTasks, ArrayList<Task> jobsLastDoneTasks) {
        // Compute job remaining time
        int max = Integer.MIN_VALUE;
        int index = 0;
        int currentTime;
        for (int i = 0; i < instance.numJobs; i++) {
            // compute the remaining time for each job
            currentTime = computeRemainingTime(i, instance, jobsLastDoneTasks);
            if (currentTime > max) {
                max = currentTime;
                index = i;
            }
        }

        return doableTasks.get(index);
    }

    // TODO - Implement a greedy solver
    @Override
    public Optional<Schedule> solve(Instance instance, long deadline) {
        // Solution is represented by a resource order object

        // Set of tasks -> 1 task for each job
        ArrayList<Task> doableTasks = InitDoableTasks(instance);
        while (doableTasks.size() != 0) {
            // TODO - Find a way to use specific choice function -> switch?
            // Add everything with index for doableTasks and last done tasks
        }

        throw new UnsupportedOperationException();
    }


    /**
     * All possible priorities for the greedy solver.
     */
    public enum Priority {
        SPT, LPT, SRPT, LRPT, EST_SPT, EST_LPT, EST_SRPT, EST_LRPT
    }
}
