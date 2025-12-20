public class RequestHandler {
    private int handler_id;
    private CppCluster cppCluster;
    private JavaCluster javaCluster;
    private PythonCluster pythonCluster;
    public static  RequestHandler request_handler;
    private RequestHandler(int handler_id,PythonCluster pythonCluster,JavaCluster javaCluster,CppCluster cppCluster) {
        this.handler_id = handler_id;
        this.cppCluster = cppCluster;
        this.javaCluster = javaCluster;
        this.pythonCluster = pythonCluster;
    }
    public static RequestHandler getRequest_handler(int handler_id,PythonCluster pythonCluster,JavaCluster javaCluster,CppCluster cppCluster) {
        if(request_handler == null){
            request_handler=new RequestHandler(handler_id, pythonCluster, javaCluster, cppCluster);
            
        }
        return request_handler;
    }
    public int getHandler_id() {
        return handler_id;
    }
    public CppCluster getCppCluster() {
        return cppCluster;
    }
    public JavaCluster getJavaCluster() {
        return javaCluster;
    }
    public PythonCluster getPythonCluster() {
        return pythonCluster;
    }
    public boolean HandleRequest(String SourceCode, String Language){
        if(Language.equals("C++")){
            return cppCluster.ExecuteCppCode(SourceCode);
        }
        else if(Language.equals("Java")){
            return javaCluster.ExecuteJavaCode(SourceCode);
        }
        else if(Language.equals("Python")){
            return pythonCluster.ExecutePythonCode(SourceCode);
        }
        else{
            return false;
        }
    }
}
