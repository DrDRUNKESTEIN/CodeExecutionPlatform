public class Request {
    private final String sourceCode;
    private final String language;

    public Request(String sourceCode, String language) {
        this.sourceCode = sourceCode;
        this.language = language;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public String getLanguage() {
        return language;
    }
}
