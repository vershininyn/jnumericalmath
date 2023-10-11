package dev.projects.utils.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggableException extends Exception {

    private static Logger _logger = LogManager.getLogger(LoggableException.class);

    public LoggableException(Exception ex) {
        this(ex.getLocalizedMessage());
        initCause(ex);
    }

    public LoggableException(String message) {
        super(message);

        StackTraceElement ste = getStackTrace()[0];

        _logger.fatal("The class: "+ste.getClassName()
                +",the method:"+ste.getMethodName()
                +",with the message: "+message+" is raised", getCause());
    }
}
