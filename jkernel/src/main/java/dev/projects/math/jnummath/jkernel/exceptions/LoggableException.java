package dev.projects.math.jnummath.jkernel.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggableException extends RuntimeException {
    private static Logger logger = LogManager.getLogger(LoggableException.class);

    public LoggableException(Exception ex) {
        this(ex.getLocalizedMessage());
        initCause(ex);
    }

    public LoggableException(String message) {
        super(message);

        StackTraceElement ste = getStackTrace()[0];

        logger.fatal("The class: "+ste.getClassName()
                +",the method:"+ste.getMethodName()
                +",with the message: "+message+" is raised", getCause());
    }
}
