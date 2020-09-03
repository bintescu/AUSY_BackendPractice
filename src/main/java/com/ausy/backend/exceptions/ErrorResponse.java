package com.ausy.backend.Exceptions;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorResponse extends RuntimeException {
    public static final Logger lgr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
            getInputArguments().toString().contains("jdwp");

    private String errorMessage;
    private int errorId;
    public ErrorResponse(){

    }

    public ErrorResponse(String errorMessage, int errorId) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorId = errorId;
    }

    public ErrorResponse(Throwable cause, String errorMessage, int errorId) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
        this.errorId = errorId;
    }
    public static void setupLogger() {
        lgr.setLevel(Level.FINE);

        try {

            FileHandler fh = new FileHandler("fileLogger");
            lgr.addHandler(fh);
            fh.setLevel(Level.FINE);
        }
        catch (IOException e){
            lgr.log(Level.SEVERE,"The logger file was not create");
        }
    }


    public static void LogError(ErrorResponse e){
        if(isDebug) {
            e.printStackTrace();
        }
        else {
            lgr.log(Level.SEVERE,e.getErrorMessage());
        }

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorId() {
        return errorId;
    }
}
