package jobshop.solvers;


import jobshop.Instance;
import jobshop.encodings.Task;
import jobshop.solvers.Solver;
import jobshop.solvers.GreedySolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

public class GreedySolverTests {

    @Test
    public void testGreedySPTTest() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa1"));

        GreedySolver  solverSPT = new GreedySolver(GreedySolver.Priority.SPT);

        ArrayList<Task> resultDoableTasks = solverSPT.InitDoableTasks(instance);
        ArrayList<Task> expectedResultArray = new ArrayList<Task>();
        expectedResultArray.add(0,new Task(0,0));
        expectedResultArray.add(1,new Task(1,0));

        Assert.assertEquals(expectedResultArray,resultDoableTasks);


        Task expectedResultSPT = new Task(1,0); // second job first task
        Task result = solverSPT.SPTTask(instance,expectedResultArray);
        Assert.assertEquals(result,expectedResultSPT);
    }

    public void testGreedyLRPTTest() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa1"));

        GreedySolver  solverLRPT = new GreedySolver(GreedySolver.Priority.LRPT);

        ArrayList<Task> resultDoableTasks = solverLRPT.InitDoableTasks(instance);
        ArrayList<Task> expectedResultArray = new ArrayList<Task>();
        expectedResultArray.add(0,new Task(0,0));
        expectedResultArray.add(1,new Task(1,0));

        Assert.assertEquals(expectedResultArray,resultDoableTasks);


        Task expectedResultSPT = new Task(1,0); // second job first task
       // Assert.assertEquals(result,expectedResultSPT);
    }


}
