import java.util.ArrayList;

public class JavaCluster {
    private int cluster_id;
    private ArrayList<CodeExecutor> executors = new ArrayList<CodeExecutor>();
    public JavaCluster(int cluster_id, ArrayList<CodeExecutor> executors) {
        this.cluster_id = cluster_id;
        this.executors = executors;
    }
    public int getCluster_id() {
        return cluster_id;
    }

    public ArrayList<CodeExecutor> getExecutors() {
        return executors;
    }
    //implement hashing to distribute code execution requests across multiple Java code executors
    public boolean ExecuteJavaCode(String SourceCode){
        // Dummy implementation for executing Java code
        //Find a executor that is empty
        while(true){
            for(CodeExecutor executor : executors){
                if(executor.getAvailable() == 1){
                    return executor.ExecuteCode(SourceCode);
                }
            }
        }
        
    }
}
