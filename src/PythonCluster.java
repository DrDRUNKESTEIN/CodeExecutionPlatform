import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class PythonCluster {
    private int cluster_id;
    private ArrayList<CodeExecutor> executors = new ArrayList<CodeExecutor>();
    private final BlockingQueue<Request> queue = new LinkedBlockingQueue<>();
    private final Thread dispatcher;
    private volatile boolean running = true;
    public PythonCluster(int cluster_id, ArrayList<CodeExecutor> executors) {
        this.cluster_id = cluster_id;
        this.executors = executors;
        dispatcher = new Thread(() -> {
            while (running || !queue.isEmpty()) {
                try {
                    Request req = queue.poll(500, TimeUnit.MILLISECONDS);
                    if (req == null) continue;
                    boolean assigned = false;
                    while (!assigned) {
                        for (CodeExecutor executor : this.executors) {
                            if (executor.isAvailable()) {
                                assigned = true;
                                ExecutorService svc = Main.executor;
                                svc.submit(() -> executor.ExecuteCode(req.getSourceCode()));
                                break;
                            }
                        }
                        if (!assigned) {
                            Thread.sleep(100);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "PythonCluster-Dispatcher-" + cluster_id);
        dispatcher.setDaemon(true);
        dispatcher.start();
    }
    public int getCluster_id() {
        return cluster_id;
    }

    public ArrayList<CodeExecutor> getExecutors() {
        return executors;
    }
    //implement hashing to distribute code execution requests across multiple Python code executors
    public void ExecutePythonCode(String SourceCode){
        // enqueue request and return immediately
        queue.offer(new Request(SourceCode, "Python"));
    }

    public void shutdown() {
        running = false;
        dispatcher.interrupt();
    }
}
