package jobshop.solvers;

import jobshop.encodings.Task;

import java.util.Objects;

public class ESTReturn {

    final int startingTime;
    final Task task;

    public ESTReturn(Task task,int startingTime){
        this.startingTime = startingTime;
        this.task = task;
    }

    public int getStartingTime(){
        return this.startingTime;
    }

    public Task getTask() {
        return this.task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ESTReturn)) return false;
        ESTReturn estReturn = (ESTReturn) o;
        return getStartingTime() == estReturn.getStartingTime() && Objects.equals(getTask(), estReturn.getTask());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartingTime(), getTask());
    }
}
