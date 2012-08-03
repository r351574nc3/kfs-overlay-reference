/*
 * Copyright 2008 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl2.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kualigan.kfs.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class with static methods wrapping {@link Logger} methods. Automatically sets up logger for you. It's called the <code>BufferedLogger</code> because
 * it handles everything in a {@link StringBuilder} using {@link StringBuilder#append(CharSequence)} method<br/>
 * <br/>
 *  
 * To use these just do
 * <code>
 * import BufferedLogger.*
 * </code>
 * 
 * @see org.slf4j.Logger
 */
public class SimpleLogger {
    
    /**
     * Applies a pattern with parameters to create a {@link String} used as a logging message
     *  
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     * @return Logging Message
     */
    private static final String getMessage(Object ... objs) {
        StringBuilder retval = new StringBuilder();
        
        for (Object obj : objs) {
            retval.append(obj);
        }
        
        return retval.toString();
    }
    
    /**
     * Uses {@link StackTraceElement[]} from {@link Throwable} to determine the calling class. Then, the {@link Logger} is retrieved for it by
     * convention
     * 
     * 
     * @return Logger for the calling class
     */
    private static final Logger getLogger() {
        try {
            return LoggerFactory.getLogger(Class.forName(new Throwable().getStackTrace()[2].getClassName()));
        }
        catch (Exception e) {
            // This will never happen unless Java is broken
            return LoggerFactory.getLogger(SimpleLogger.class);
        }
    }

    /**
     * Uses {@link StackTraceElement[]} from {@link Throwable} to determine the calling class. Then, the {@link Logger} is retrieved for it by
     * convention. Just like {@link #getLogger()} except this is intended to be called directly from classes.
     * 
     * 
     * @return Logger for the calling class
     */
    public static final Logger logger() {
        try {
            return LoggerFactory.getLogger(Class.forName(new Throwable().getStackTrace()[1].getClassName()));
        }
        catch (Exception e) {
            // This will never happen unless Java is broken
            return LoggerFactory.getLogger(SimpleLogger.class);
        }
    }

    /**
     * Wraps {@link Logger#trace(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void trace(Object ... objs) {
        Logger log = getLogger();
        if (log.isTraceEnabled()) {
            log.trace(getMessage(objs));
        }
    }

    /**
     * Wraps {@link Logger#trace(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void tracef(final String format, final Object ... objs) {
        Logger log = getLogger();
        if (log.isTraceEnabled()) {
            log.trace(String.format(format, objs));
        }
    }

    /**
     * Wraps {@link Logger#debug(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void debug(Object ... objs) {
        Logger log = getLogger();
        if (log.isDebugEnabled()) {
            log.debug(getMessage(objs));
        }
    }

    /**
     * Wraps {@link Logger#debug(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void debugf(final String format, Object ... objs) {
        Logger log = getLogger();
        if (log.isDebugEnabled()) {
            log.debug(String.format(format, objs));
        }
    }

    /**
     * Wraps {@link Logger#info(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void info(Object ... objs) {
        Logger log = getLogger();
        if (log.isInfoEnabled()) {
            log.info(getMessage(objs));
        }
    }

    /**
     * Wraps {@link Logger#info(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void infof(final String format, final Object ... objs) {
        Logger log = getLogger();
        if (log.isInfoEnabled()) {
            log.info(String.format(format, objs));
        }
    }

    /**
     * Wraps {@link Logger#warn(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void warn(Object ... objs) {
        Logger log = getLogger();
        if (log.isWarnEnabled()) {
            log.warn(getMessage(objs));
        }
    }

    /**
     * Wraps {@link Logger#warn(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void warnf(final String format, final Object ... objs) {
        Logger log = getLogger();
        if (log.isWarnEnabled()) {
            log.warn(String.format(format, objs));
        }
    }

    /**
     * Wraps {@link Logger#error(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void error(Object ... objs) {
        Logger log = getLogger();
        if (log.isErrorEnabled()) {
            getLogger().error(getMessage(objs));
        }
    }

    /**
     * Wraps {@link Logger#error(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void errorf(final String format, Object ... objs) {
        Logger log = getLogger();
        if (log.isErrorEnabled()) {
            getLogger().error(String.format(format, objs));
        }
    }
}
