package jobshop.solvers;


import jobshop.Instance;
import jobshop.encodings.Schedule;
import jobshop.encodings.Task;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

public class GreedySolverTests {

    @Test
    public void testInitLastDoneTasks() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa1"));

        GreedySolver solverSPT = new GreedySolver(GreedySolver.Priority.SPT);

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

    public void testUpdateDoable() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa1"));

        GreedySolver  solverSPT = new GreedySolver(GreedySolver.Priority.SPT);

        ArrayList<Task> resultDoableTasks = solverSPT.InitDoableTasks(instance);
        ArrayList<Task> expectedDoableTasks = solverSPT.InitDoableTasks(instance);
        expectedDoableTasks.set(0,new Task(0,1));

        solverSPT.UpdateDoableTasks(instance,resultDoableTasks,new Task(0,0));

        Assert.assertEquals(expectedDoableTasks,resultDoableTasks);

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
        Task result = solverSPT.SPTTask(instance, resultDoableTasks);
        Assert.assertEquals(result,expectedResultSPT);
    }

    @Test
    public void testGreedyLPTTest() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa1"));

        GreedySolver  solverLPT = new GreedySolver(GreedySolver.Priority.LPT);

        ArrayList<Task> resultDoableTasks = solverLPT.InitDoableTasks(instance);
        ArrayList<Task> expectedResultArray = new ArrayList<Task>();
        expectedResultArray.add(0,new Task(0,0));
        expectedResultArray.add(1,new Task(1,0));

        Assert.assertEquals(expectedResultArray,resultDoableTasks);

        Task expectedResultLPT = new Task(0,0); // first job first task
        Task result = solverLPT.LPTTask(instance, resultDoableTasks);
        Assert.assertEquals(result, expectedResultLPT);
    }

    @Test
    public void testGreedySRPTTest() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa1"));

        GreedySolver  solverSRPT = new GreedySolver(GreedySolver.Priority.SRPT);

        ArrayList<Task> resultDoableTasks = solverSRPT.InitDoableTasks(instance);
        ArrayList<Task> resultLastDoneTasks = solverSRPT.InitLastDoneTasks(instance);
        ArrayList<Task> expectedResultArray = new ArrayList<Task>();
        expectedResultArray.add(0,new Task(0,0));
        expectedResultArray.add(1,new Task(1,0));

        Assert.assertEquals(expectedResultArray,resultDoableTasks);

        Task expectedResultSRPT = new Task(0,0); // First job, first task
                                                          // The 2 jobs have the same remaining time
                                                          // So we return the first one
        Task result = solverSRPT.SRPTTask(instance, resultDoableTasks, resultLastDoneTasks);
        Assert.assertEquals(expectedResultSRPT, result);
    }

    @Test
    public void testGreedyLRPTTest() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa1"));

        GreedySolver  solverLRPT = new GreedySolver(GreedySolver.Priority.LRPT);

        ArrayList<Task> resultDoableTasks = solverLRPT.InitDoableTasks(instance);
        ArrayList<Task> resultLastDoneTasks = solverLRPT.InitLastDoneTasks(instance);
        ArrayList<Task> expectedResultArray = new ArrayList<Task>();
        expectedResultArray.add(0,new Task(0,0));
        expectedResultArray.add(1,new Task(1,0));

        Assert.assertEquals(expectedResultArray,resultDoableTasks);

        Task expectedResultLRPT = new Task(0,0); // First job, first task
                                                          // The 2 jobs have the same remaining time
                                                          // So we return the first one
        Task result = solverLRPT.LRPTTask(instance, resultDoableTasks, resultLastDoneTasks);
        Assert.assertEquals(expectedResultLRPT, result);
    }

    // TODO - Create test for EST-LRPT and EST-SPT
    // FIXME - Out of bonds errors

    @Test
    public void testGreedySolving() throws  IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa3"));

        // One solver for each algorithm
        GreedySolver solverSPT = new GreedySolver(GreedySolver.Priority.SPT);
        Optional<Schedule> resultSPT = solverSPT.solve(instance, 100);

        GreedySolver solverLPT = new GreedySolver(GreedySolver.Priority.LPT);
        Optional<Schedule> resultLPT = solverLPT.solve(instance, 100);

        GreedySolver solverSRPT = new GreedySolver(GreedySolver.Priority.SRPT);
        Optional<Schedule> resultSRPT = solverSRPT.solve(instance, 100);

        GreedySolver solverLRPT = new GreedySolver(GreedySolver.Priority.LRPT);
        Optional<Schedule> resultLRPT = solverLRPT.solve(instance, 100);

        // Affichage de chaque solution
        System.out.println("============= SPT ===============");
        System.out.println(resultSPT.toString());

        System.out.println("============= LPT ===============");
        System.out.println(resultLPT.toString());

        System.out.println("============= SRPT ===============");
        System.out.println(resultSRPT.toString());

        System.out.println("============= LRPT ===============");
        System.out.println(resultLRPT.toString());
    }
}
