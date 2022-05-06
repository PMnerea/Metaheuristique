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
        DescentSolver descentSolver = new DescentSolver(nowicki, solverSPT);
        Optional<Schedule> resultDescent = descentSolver.solve(instance, 100);


        // Affichage de chaque solution
        System.out.println("============= Descent ===============");
        System.out.println(resultDescent.toString());
    }

    @Test
    public void testGenerateNeighbors() throws IOException{
        Instance instance = Instance.fromFile(Paths.get("instances/aaa3"));
        ResourceOrder current = new ResourceOrder(instance);
        Nowicki nowicki = new Nowicki();

        List<Nowicki.Swap> swaps = nowicki.allSwaps(current);
        List<ResourceOrder> orders = nowicki.generateNeighbors(current);

        boolean testValue;
        Assert.assertTrue((orders.size() == swaps.size()));

        for (int i = 0; i < swaps.size(); i++) {
            testValue = swaps.get(i).generateFrom(current).equals(orders.get(i));
            Assert.assertTrue(testValue);
        }

    }
}
