import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static ExecutorService executor=Executors.newFixedThreadPool(10);
    public static void main(String[] args) {
        System.out.println("Code Execution Cluster System Initialized.");
        int handler_id=1;
        CodeExecutor executor1 = new CodeExecutor(1, "Java");
        CodeExecutor executor2 = new CodeExecutor(2, "Java");
        CodeExecutor executor3 = new CodeExecutor(3, "Python");
        CodeExecutor executor4 = new CodeExecutor(4, "Python");
        CodeExecutor executor5 = new CodeExecutor(5, "C++");
        CodeExecutor executor6 = new CodeExecutor(6, "C++");
        ArrayList<CodeExecutor> javaExecutors = new ArrayList<CodeExecutor>();
        javaExecutors.add(executor1);
        javaExecutors.add(executor2);
        ArrayList<CodeExecutor> pythonExecutors = new ArrayList<CodeExecutor>();
        pythonExecutors.add(executor3);
        pythonExecutors.add(executor4);
        ArrayList<CodeExecutor> cppExecutors = new ArrayList<CodeExecutor>();
        cppExecutors.add(executor5);
        cppExecutors.add(executor6);
        JavaCluster javaCluster = new JavaCluster(1, javaExecutors);
        PythonCluster pythonCluster = new PythonCluster(1, pythonExecutors);
        CppCluster cppCluster = new CppCluster(1, cppExecutors);
        RequestHandler requestHandler = RequestHandler.getRequest_handler(handler_id, pythonCluster, javaCluster, cppCluster);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            String code;
            String language;
            try {
                System.out.print("Enter code (or 'exit' to quit): ");
                code = reader.readLine();
                if (code.equalsIgnoreCase("exit")) {
                    System.out.println("Shutting down... waiting for queued tasks to finish.");
                    // shutdown clusters (stop dispatchers)
                    javaCluster.shutdown();
                    pythonCluster.shutdown();
                    cppCluster.shutdown();
                    // stop accepting new tasks and wait
                    executor.shutdown();
                    try {
                        if (!executor.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                            executor.shutdownNow();
                        }
                    } catch (InterruptedException ie) {
                        executor.shutdownNow();
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("Shutdown complete.");
                    break;
                }
                System.out.print("Enter programming language (Java/Python/C++): ");
                language = reader.readLine();
                // Enqueue the request quickly and return; actual execution happens in cluster dispatchers
                requestHandler.HandleRequest(code, language);
                System.out.println("Request accepted and queued for execution.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
