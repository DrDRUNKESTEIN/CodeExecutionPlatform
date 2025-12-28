import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class JavaCluster {
    private int cluster_id;
    private ArrayList<CodeExecutor> executors = new ArrayList<CodeExecutor>();
    private final BlockingQueue<Request> queue = new LinkedBlockingQueue<>();
    private final Thread dispatcher;
    private volatile boolean running = true;
    public JavaCluster(int cluster_id, ArrayList<CodeExecutor> executors) {
        this.cluster_id = cluster_id;
        this.executors = executors;
        // start dispatcher thread
        dispatcher = new Thread(() -> {
            while (running || !queue.isEmpty()) {
                try {
                    Request req = queue.poll(500, TimeUnit.MILLISECONDS);
                    if (req == null) continue;
                    boolean assigned = false;
                    // Try to find an available executor using atomic acquire to avoid races
                    while (!assigned) {
                        int size = this.executors.size();
                        int start = java.util.concurrent.ThreadLocalRandom.current().nextInt(size);
                        for (int i = 0; i < size; i++) {
                            CodeExecutor executor = this.executors.get((start + i) % size);
                            if (executor.tryAcquire()) {
                                assigned = true;
                                ExecutorService svc = Main.executor;
                                svc.submit(() -> {
                                    try {
                                        executor.ExecuteCode(req.getSourceCode());
                                    } finally {
                                        executor.release();
                                    }
                                });
                                break;
                            }
                        }
                        if (!assigned) {
                            Thread.sleep(100); // wait a bit and retry
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "JavaCluster-Dispatcher-" + cluster_id);
        dispatcher.setDaemon(true);
        dispatcher.start();
    }
    public int getCluster_id() {
        return cluster_id;
    }

    public ArrayList<CodeExecutor> getExecutors() {
        return executors;
    }
    //implement hashing to distribute code execution requests across multiple Java code executors
    public void ExecuteJavaCode(String SourceCode){
        // enqueue request and return immediately
        queue.offer(new Request(SourceCode, "Java"));
    }

    public void shutdown() {
        running = false;
        dispatcher.interrupt();
    }
}
