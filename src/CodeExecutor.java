import java.util.concurrent.atomic.AtomicBoolean;

public class CodeExecutor {
    private int executor_id;
    private String coding_language;
    // Use AtomicBoolean for thread-safe availability checks/updates
    private AtomicBoolean available = new AtomicBoolean(true);

    public void ExecuteCode(String SourceCode){
        // mark as busy
        available.set(false);
        System.out.println("Executing code in " + coding_language + " with executor ID " + executor_id);
        System.out.println("Source Code: " + SourceCode);

        // Simulate code execution
        try {
            Thread.sleep(2000); // Simulate time taken to execute code
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Execution interrupted for executor " + executor_id);
        }

        // mark as available again
        available.set(true);
        System.out.println("Execution finished on executor " + executor_id);
    }

    public CodeExecutor(int executor_id, String coding_language) {
        this.executor_id = executor_id;
        this.coding_language = coding_language;
        this.available.set(true);
    }

    public boolean isAvailable() {
        return available.get();
    }

    public String getCoding_language() {
        return coding_language;
    }

    public int getExecutor_id() {
        return executor_id;
    }
}
