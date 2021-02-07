package javagameengine.exceptions;

public class GLException extends Exception {
    private String glLog = "";

    public GLException(String message, String glLog) {
        super(message);
        this.glLog = glLog;
    }

    public String getGlLog() {
        return glLog;
    }
}
