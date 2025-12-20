import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
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
                    break;
                }
                System.out.print("Enter programming language (Java/Python/C++): ");
                language = reader.readLine();
                boolean result = requestHandler.HandleRequest(code, language);
                if (result) {
                    System.out.println("Code executed successfully.");
                } else {
                    System.out.println("Code execution failed.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
