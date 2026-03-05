package main.engine.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Facade over java.util.logging for simplified logging.
 * 
 * usage: 
 *   Log.info(message)
 *   Log.warning(message)
 *   Log.error(message, exception)
 */
public class Log {
    
    private static final Logger logger = Logger.getLogger("Game Engine");

    static {
        try{
            // Create logs directory 
            File logsDir = new File("logs");
            if(!logsDir.exists()) logsDir.mkdir();
            
            // Create File handler
            FileHandler fileHandler = new FileHandler("logs/game.log", 1024 * 1024, 3, false);
            
            // Set Formatter
            fileHandler.setFormatter(new SimpleFormatter());

            // Add handler to logger
            logger.addHandler(fileHandler);

            // Set Level
            logger.setLevel(Level.ALL); // Log everything to file
            fileHandler.setLevel(Level.ALL); 
            
            // Disable parent handlers 
            logger.setUseParentHandlers(false); // Prevents duplicate console output

            // Add Console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO); // Only INFO+ to console
            logger.addHandler(consoleHandler);

        }catch(IOException e){
            System.err.println("Failed to setup file logging: "+e.getMessage());
        }
    }

    /** Prevents instantiation of utility class. */
    private Log(){
        throw new IllegalStateException("Utility class");
    }


    /** Logs informational message. */
    public static void info(String message){
        logger.log(Level.INFO, message);
    }


    /** Logs warning message. */
    public static void warning(String message){
        logger.log(Level.WARNING, message);
    }


    /** Logs error message. */
    public static void error(String message){
        logger.log(Level.SEVERE, message);
    }


    /** Logs error message with exception stack trace. */
    public static void error(String message, Throwable exception){
        logger.log(Level.SEVERE, message, exception);
    }

    /** Logs debug message (disabled by default). */
    public static void debug(String message){
        logger.log(Level.FINE, message);
    }
}
