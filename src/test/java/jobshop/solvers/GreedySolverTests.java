package jobshop.solvers;


import jobshop.Instance;
import jobshop.encodings.Schedule;
import jobshop.encodings.Task;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.lang.reflect.Array;
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

    @Test
    public void testComputeAvailableTime() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa3"));
        GreedySolver solverEST = new GreedySolver(GreedySolver.Priority.EST_SPT);

        // Create necessary arrays for the method
        ArrayList<Task> doableTasks = solverEST.InitDoableTasks(instance);
        ArrayList<Integer> finishingTimeMachines = new ArrayList<>();
        ArrayList<Integer> jobCurrentTime = new ArrayList<>();
        solverEST.InitESTArrays(instance,finishingTimeMachines,jobCurrentTime);

        ArrayList<ESTReturn> expected = new ArrayList<>();

        // First test
        for (int i = 0; i < 4; i++) {
            expected.add(new ESTReturn(new Task(i,0),0));
        }
        ArrayList<ESTReturn> res = solverEST.computeAvailableTime(instance,doableTasks,finishingTimeMachines,jobCurrentTime);
        Assert.assertEquals(expected,res);

        // Second test
        expected.set(0,new ESTReturn(new Task(0,1),1));
        expected.set(1,new ESTReturn(new Task(1,0),0));
        expected.set(2,new ESTReturn(new Task(2,0),1));
        expected.set(3,new ESTReturn(new Task(3,0),0));

        solverEST.UpdateDoableTasks(instance,doableTasks,doableTasks.get(0));
        jobCurrentTime.set(0,1);
        finishingTimeMachines.set(0,1);

        res = solverEST.computeAvailableTime(instance,doableTasks,finishingTimeMachines,jobCurrentTime);
        Assert.assertEquals(expected,res);
    }

    @Test
    public void testGreedyESTSPT() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa3"));

        ESTReturn res;

        GreedySolver  solverESTSPT = new GreedySolver(GreedySolver.Priority.EST_SPT);
        ArrayList<Task> doableTasks = solverESTSPT.InitDoableTasks(instance);
        ArrayList<Integer> finishingTimeMachines = new ArrayList<>();
        ArrayList<Integer> jobCurrentTime = new ArrayList<>();
        solverESTSPT.InitESTArrays(instance,finishingTimeMachines,jobCurrentTime);

        // First test
        Task expected = new Task(0,0);
        res = solverESTSPT.EST_SPTTask(instance,doableTasks,finishingTimeMachines,jobCurrentTime);
        Assert.assertEquals(res.getTask(),expected);

        // Second test
        Task currentTask = res.getTask();
        // Update finishing time for the job
        int calc = res.getStartingTime() + instance.duration(currentTask);
        jobCurrentTime.set(currentTask.job, calc);
        // Update finishing time for the machine
        finishingTimeMachines.set(instance.machine(currentTask),calc);
        solverESTSPT.UpdateDoableTasks(instance,doableTasks,currentTask);
        expected = new Task(3,0);
        res = solverESTSPT.EST_SPTTask(instance,doableTasks,finishingTimeMachines,jobCurrentTime);
        Assert.assertEquals(res.getTask(),expected);

    }

    @Test
    public void testGreedyESTLRPT() throws IOException {
        Instance instance = Instance.fromFile(Paths.get("instances/aaa3"));

        ESTReturn res;

        GreedySolver  solverESTLRPT = new GreedySolver(GreedySolver.Priority.EST_LRPT);
        ArrayList<Task> doableTasks = solverESTLRPT.InitDoableTasks(instance);
        ArrayList<Integer> finishingTimeMachines = new ArrayList<>();
        ArrayList<Integer> jobCurrentTime = new ArrayList<>();
        ArrayList<Task> lastDoneTasks = solverESTLRPT.InitLastDoneTasks(instance);

        solverESTLRPT.InitESTArrays(instance,finishingTimeMachines,jobCurrentTime);

        // First test
        Task expected = new Task(2,0);
        res = solverESTLRPT.EST_LRPTTask(instance,doableTasks,lastDoneTasks,finishingTimeMachines,jobCurrentTime);
        Assert.assertEquals(res.getTask(),expected);

        // Second test
        Task currentTask = res.getTask();
        // Update finishing time for the job
        int calc = res.getStartingTime() + instance.duration(currentTask);
        jobCurrentTime.set(currentTask.job, calc);
        // Update finishing time for the machine
        finishingTimeMachines.set(instance.machine(currentTask),calc);
        solverESTLRPT.UpdateDoableTasks(instance,doableTasks,currentTask);
        lastDoneTasks.set(currentTask.job,currentTask);

        expected = new Task(3,0);
        res = solverESTLRPT.EST_LRPTTask(instance,doableTasks,lastDoneTasks,finishingTimeMachines,jobCurrentTime);
        Assert.assertEquals(res.getTask(),expected);
    }
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

        GreedySolver solverESTSPT = new GreedySolver(GreedySolver.Priority.EST_SPT);
        Optional<Schedule> resultESTSPT = solverESTSPT.solve(instance, 100);

        GreedySolver solverESTLRPT = new GreedySolver(GreedySolver.Priority.EST_LRPT);
        Optional<Schedule> resultESTLRPT = solverESTLRPT.solve(instance, 100);

        // Affichage de chaque solution
        System.out.println("============= SPT ===============");
        System.out.println(resultSPT.toString());

        System.out.println("============= LPT ===============");
        System.out.println(resultLPT.toString());

        System.out.println("============= SRPT ===============");
        System.out.println(resultSRPT.toString());

        System.out.println("============= LRPT ===============");
        System.out.println(resultLRPT.toString());

        System.out.println("============= EST-SPT ===============");
        System.out.println(resultESTSPT.toString());

        System.out.println("============= EST-LRPT ===============");
        System.out.println(resultESTLRPT.toString());


    }
}
