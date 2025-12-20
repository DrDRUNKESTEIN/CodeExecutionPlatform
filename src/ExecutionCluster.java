import java.util.ArrayList;

public class ExecutionCluster {
    private int cluster_id;
    private ArrayList<CodeExecutor> executors = new ArrayList<CodeExecutor>();
    public ExecutionCluster(){
        return;
    }
    public ExecutionCluster(int cluster_id, ArrayList<CodeExecutor> executors) {
        this.cluster_id = cluster_id;
        this.executors = executors;
    }
    public int getCluster_id() {
        return cluster_id;
    }
    public ArrayList<CodeExecutor> getExecutors() {
        return executors;
    }

}
