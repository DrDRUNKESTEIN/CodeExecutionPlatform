public class CodeExecutor {
    private int executor_id;
    private String coding_language;
    private int available;
    public boolean ExecuteCode(String SourceCode){
        
        this.available = 0;
        System.out.println("Executing code in " + coding_language + " with executor ID " + executor_id);
        System.out.println("Source Code: " + SourceCode);

        // Simulate code execution
        try {
            Thread.sleep(2000); // Simulate time taken to execute code
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.available = 1; // Mark executor as available
        return true; // Assume execution is always successful
    }
    public CodeExecutor(int executor_id, String coding_language) {
        this.executor_id = executor_id;
        this.coding_language = coding_language;
        this.available = 1; // 1 means available, 0 means busy
    }
    public int getAvailable() {
        return available;
    }
    public void setAvailable(int available) {
        this.available = available;
    }
    public String getCoding_language() {
        return coding_language;
    }
    public int getExecutor_id() {
        return executor_id;
    }
}
