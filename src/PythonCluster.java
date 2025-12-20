import java.util.ArrayList;

public class PythonCluster {
    private int cluster_id;
    private ArrayList<CodeExecutor> executors = new ArrayList<CodeExecutor>();
    public PythonCluster(int cluster_id, ArrayList<CodeExecutor> executors) {
        this.cluster_id = cluster_id;
        this.executors = executors;
    }
    public int getCluster_id() {
        return cluster_id;
    }

    public ArrayList<CodeExecutor> getExecutors() {
        return executors;
    }
    //implement hashing to distribute code execution requests across multiple Python code executors
    public boolean ExecutePythonCode(String SourceCode){
        // Dummy implementation for executing Python code
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
