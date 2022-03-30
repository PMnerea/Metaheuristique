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
    public void testInitLastDoneTasks() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa1"));

        GreedySolver  solverSPT = new GreedySolver(GreedySolver.Priority.SPT);

        ArrayList<Task> resultLastTasks = solverSPT.InitLastDoneTasks(instance);
        ArrayList<Task> expectedResultArray = new ArrayList<Task>();
        expectedResultArray.add(0,new Task(0,-1));
        expectedResultArray.add(1,new Task(1,-1));

        Assert.assertEquals(expectedResultArray,resultLastTasks);
    }

    @Test
    public void testRemainingTime() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa1"));

        GreedySolver  solverSPT = new GreedySolver(GreedySolver.Priority.SPT);

        ArrayList<Task> resultLastTasks = solverSPT.InitLastDoneTasks(instance);
        Assert.assertEquals(8, solverSPT.computeRemainingTime(0, instance, resultLastTasks));
        Assert.assertEquals(8, solverSPT.computeRemainingTime(1, instance, resultLastTasks));
    }

    @Test
    /// TODO - Finish this function
    public void testUpdateLastDone() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa1"));

        GreedySolver  solverSPT = new GreedySolver(GreedySolver.Priority.SPT);

        ArrayList<Task> resultDoableTasks = solverSPT.InitDoableTasks(instance);
        ArrayList<Task> resultLastDone = solverSPT.InitLastDoneTasks(instance);

        // Job 1 Task 1
        resultLastDone = solverSPT.UpdateDoableTasks(instance,resultDoableTasks,new Task(0,0));

    }

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

    @Test
    public void testGreedyLRPTTest() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa1"));

        GreedySolver  solverLRPT = new GreedySolver(GreedySolver.Priority.LRPT);

        ArrayList<Task> resultDoableTasks = solverLRPT.InitDoableTasks(instance);
        ArrayList<Task> expectedResultArray = new ArrayList<Task>();
        expectedResultArray.add(0,new Task(0,0));
        expectedResultArray.add(1,new Task(1,0));

        Assert.assertEquals(expectedResultArray,resultDoableTasks);

        //solverLRPT.computeRemainingTime(0, instance, );
        Task expectedResultSPT = new Task(1,0); // second job first task
       // Assert.assertEquals(result,expectedResultSPT);
    }


}
