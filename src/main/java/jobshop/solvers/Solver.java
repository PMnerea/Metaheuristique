package jobshop.solvers;

import jobshop.Instance;
import jobshop.encodings.Schedule;
import jobshop.solvers.neighborhood.Nowicki;

import java.util.Optional;

/** Common interface that must implemented by all solvers. */
public interface Solver {

    /** Look for a solution until blocked or a deadline has been met.
     *
     * @param instance Jobshop instance that should be solved.
     * @param deadline Absolute time at which the solver should have returned a solution.
     *                 This time is in milliseconds and can be compared with System.currentTimeMilliseconds()
     * @return An optional schedule that will be non empty if a solution was found.
     */
    Optional<Schedule> solve(Instance instance, long deadline);

    /** Static factory method to create a new solver based on its name. */
    static Solver getSolver(String name) {
        switch (name) {
            case "basic": return new BasicSolver();
            case "spt": return new GreedySolver(GreedySolver.Priority.SPT);
            case "lrpt": return new GreedySolver(GreedySolver.Priority.LRPT);
            case "lpt": return new GreedySolver(GreedySolver.Priority.LPT);
            case "srpt": return new GreedySolver(GreedySolver.Priority.SRPT);
            case "est_spt": return new GreedySolver(GreedySolver.Priority.EST_SPT);
            case "est_lrpt": return new GreedySolver(GreedySolver.Priority.EST_LRPT);
            case "random_spt": return new GreedyRandomSolver(GreedySolver.Priority.SPT,20);
            case "random_lrpt": return new GreedyRandomSolver(GreedySolver.Priority.LRPT,20);
            case "random_lpt": return new GreedyRandomSolver(GreedySolver.Priority.LPT,20);
            case "random_srpt": return new GreedyRandomSolver(GreedySolver.Priority.SRPT,20);
            case "random_est_spt": return new GreedyRandomSolver(GreedySolver.Priority.EST_SPT,20);
            case "random_est_lrpt": return new GreedyRandomSolver(GreedySolver.Priority.EST_LRPT,20);
            case "taboo_basic": return new TabooSolver(new BasicSolver(),5000,5);
            case "taboo_spt": return new TabooSolver(new GreedySolver(GreedySolver.Priority.SPT),5000,20);
            case "taboo_lrpt": return new TabooSolver(new GreedySolver(GreedySolver.Priority.LRPT) ,5000,20);
            case "taboo_lpt": return new TabooSolver(new GreedySolver(GreedySolver.Priority.LPT) ,5000,20);
            case "taboo_srpt": return new TabooSolver(new GreedySolver(GreedySolver.Priority.SRPT),5000,20);
            case "taboo_est_spt": return new TabooSolver(new GreedySolver(GreedySolver.Priority.EST_SPT),5000,20);
            case "taboo_est_lrpt": return new TabooSolver(new GreedySolver(GreedySolver.Priority.EST_LRPT),5000,20);
            case "taboo_random_spt": return new TabooSolver(new GreedyRandomSolver(GreedySolver.Priority.SPT,20),5000,20);
            case "taboo_random_lrpt": return new TabooSolver(new GreedyRandomSolver(GreedySolver.Priority.LRPT,20),5000,20);
            case "taboo_random_lpt": return new TabooSolver(new GreedyRandomSolver(GreedySolver.Priority.LPT,20),5000,20);
            case "taboo_random_srpt": return new TabooSolver(new GreedyRandomSolver(GreedySolver.Priority.SRPT,20),5000,20);
            case "taboo_random_est_spt": return new TabooSolver(new GreedyRandomSolver(GreedySolver.Priority.EST_SPT,20),5000,20);
            case "taboo_random_est_lrpt": return new TabooSolver(new GreedyRandomSolver(GreedySolver.Priority.EST_LRPT,20),5000,20);
            case "descent_basic": return new DescentSolver(new Nowicki(),new BasicSolver());
            case "descent_spt": return new DescentSolver(new Nowicki(),new GreedySolver(GreedySolver.Priority.SPT));
            case "descent_lrpt": return new DescentSolver(new Nowicki(),new GreedySolver(GreedySolver.Priority.LRPT));
            case "descent_lpt": return new DescentSolver(new Nowicki(),new GreedySolver(GreedySolver.Priority.LPT));
            case "descent_srpt": return new DescentSolver(new Nowicki(), new GreedySolver(GreedySolver.Priority.SRPT));
            case "descent_est_spt": return new DescentSolver(new Nowicki(),new GreedySolver(GreedySolver.Priority.EST_SPT));
            case "descent_est_lrpt": return new DescentSolver(new Nowicki(),new GreedySolver(GreedySolver.Priority.EST_LRPT));
            case "descent_random_spt": return new DescentSolver(new Nowicki(),new GreedyRandomSolver(GreedySolver.Priority.SPT,20));
            case "descent_random_lrpt": return new DescentSolver(new Nowicki(),new GreedyRandomSolver(GreedySolver.Priority.LRPT,20));
            case "descent_random_lpt": return new DescentSolver(new Nowicki(),new GreedyRandomSolver(GreedySolver.Priority.LPT,20));
            case "descent_random_srpt": return new DescentSolver(new Nowicki(),new GreedyRandomSolver(GreedySolver.Priority.SRPT,20));
            case "descent_random_est_spt": return new DescentSolver(new Nowicki(),new GreedyRandomSolver(GreedySolver.Priority.EST_SPT,20));
            case "descent_random_est_lrpt": return new DescentSolver(new Nowicki(),new GreedyRandomSolver(GreedySolver.Priority.EST_LRPT,20));
            default: throw new RuntimeException("Unknown solver: "+ name);
        }
    }

    enum SolverType{
        GREEDY, RANDOMISED, BASIC, DESCENT, TABOU
    }

}
