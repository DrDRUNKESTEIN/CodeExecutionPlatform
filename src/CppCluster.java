import java.util.ArrayList;

public class CppCluster extends ExecutionCluster {
    //This class represents a cluster for executing C++ code
    //This class implements consistent hashing to distribute code execution requests across multiple C++ code executors
    private int cluster_id;
    public ArrayList<CodeExecutor> executors = new ArrayList<CodeExecutor>();
    public CppCluster(int cluster_id, ArrayList<CodeExecutor> executors) {
        this.cluster_id = cluster_id;
        this.executors = executors;
    }
    public int getCluster_id() {
        return cluster_id;
    }

    public ArrayList<CodeExecutor> getExecutors() {
        return executors;
    }
    //implement hashing to distribute code execution requests across multiple C++ code executors
    public boolean ExecuteCppCode(String SourceCode){
        // Dummy implementation for executing C++ code
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
