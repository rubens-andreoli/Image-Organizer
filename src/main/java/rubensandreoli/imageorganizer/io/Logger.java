/*
 * Copyright (C) 2023 Rubens A. Andreoli Jr.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package rubensandreoli.imageorganizer.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Used to log exceptions on a text file. This file is generated, if it doesn't exist, on the same folder as the application.<br>
 * The logger is disabled by default and must be enabled in order to work {@code Logger::setEnabled}.
 * 
 * @author Rubens A. Andreoli Jr.
 */
public class Logger {

    public static enum Level {
        DEBUG, INFO, WARNING, ERROR, CRITICAL, SEVERE
    }
    
    public static final String FILENAME = "crash.log";
    private static final String SEPARATOR = "-------------//----------------";
    private static final String LOG_MASK = "[%s]\r\ndate: %s\r\ncomment: %s\r\nmessage: %s\r\nstack trace: %s\r\n"+SEPARATOR+"\r\n";
    
    public static final Logger log = new Logger(); //eager initialization
    
    private boolean enabled = true;
    private boolean verbose = false;
    
    private Logger(){};

    /**
     * Print an exception following the template bellow.<br>
     * <br>
     * LOG TEMPLATE:<br>
     * date: [current date and time]<br>
     * comment: [additional comment]<br>
     * message: [exception message]<br>
     * stack trace: [exception stack trace]<br>
     * {@code Logger::SEPARATOR}
     * 
     * @param lvl level of the exception
     * @param comment additional comment
     * @param ex exception to be logged, must not be {@code null}
     */
    public void print(Level lvl, String comment, Exception ex){
        if(!enabled) return;
        if(ex == null || lvl == null) return;
        if(!verbose && lvl.compareTo(Level.ERROR) < 0) return;
        
        final StringBuilder trace = new StringBuilder();
        
        for (StackTraceElement e : ex.getStackTrace()) {
            trace.append(e.toString()).append("\r\n");
        }
        try(var bw = new BufferedWriter(new FileWriter(new File(FILENAME), true))){
            bw.write(String.format(LOG_MASK, lvl.toString(), LocalDateTime.now(), comment, ex.getMessage(), trace));
        } catch (IOException e) {
            System.err.println("ERROR: failed writing log file");
        }
    }
    
    /**
     * Print an exception with an empty comment section.
     * 
     * @see Level
     * @see Logger#print(Level, String, Exception)
     * @param lvl level of the exception
     * @param ex exception to be logged, must not be {@code null}
     */
    public void print(Level lvl, Exception ex){
        print(lvl, "", ex);
    }
    
    /**
     * Print an exception with an empty comment section and a predetermined {@code Level}.<br>
     * Instances of {@code RuntimeException} are considered {@code Level.SEVERE} and all others are {@code Level.INFO}.
     * 
     * @see Level
     * @see Logger#print(Level, Exception)
     * @param ex exception to be logged, must not be {@code null}
     */
    public void print(Exception ex){
        print((ex instanceof RuntimeException? Level.SEVERE : Level.INFO), ex);
    }

    public void setEnabled(boolean b){
        enabled = b;
    }
    
    /**
     * Verbose mode will log exceptions bellow {@code Level.ERROR}.
     * 
     * @see Level
     * @param b if true, the verbose mode is enabled; otherwise is disabled 
     */
    public void setVerbose(boolean b){
        verbose = b;
    }
    
}
