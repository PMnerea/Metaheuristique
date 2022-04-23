package jobshop.solvers;


import jobshop.Instance;
import jobshop.encodings.ResourceOrder;
import jobshop.encodings.Schedule;
import jobshop.encodings.Task;
import jobshop.solvers.neighborhood.Neighborhood;
import jobshop.solvers.neighborhood.Nowicki;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DescentSolverTests {


    @Test
    public void testDescentSolving() throws  IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa3"));
        ResourceOrder order = new ResourceOrder(instance);

        Nowicki nowicki = new Nowicki();
        //List<ResourceOrder> neighborhood = nowicki.generateNeighbors(order);

        GreedySolver solverSPT = new GreedySolver(GreedySolver.Priority.SPT);
        //Optional<Schedule> resultSPT = solverSPT.solve(instance, 100);

        // One solver for each algorithm
        // FIXME - voisinqge vide
        /** PB : le voisinage est vide **/
        DescentSolver descentSolver = new DescentSolver(nowicki, solverSPT);
        Optional<Schedule> resultDescent = descentSolver.solve(instance, 100);


        // Affichage de chaque solution
        System.out.println("============= Descent ===============");
        System.out.println(resultDescent.toString());
    }
}
