package com.jin.ds;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
/**
 * This class is used to log the elapsed time of operations e.g. calls to external systems.
 * 
 * @author gburson
 * @date 16 Feb 2012
 * @revision : $Id: TimeLogger.java,v 1.6 2013-10-29 16:11:30 pmagrian Exp $
 */
public class TimeLogger {
    public static void main(String[] args) {
	System.out.println(Math.random()*10);
    }
    private Date startDate = null;
    private static final String CLASSNAME = TimeLogger.class.getName();
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(CLASSNAME);

    
    
    private static final long NANOS_PER_MILLI = 1000000L;
    private static final float ONE_THOUSAND_MS = 1000;
    private long start = 0;
    private String operationName = "";
    private boolean running = false;

    /**
     * Creates a new time logger for the named component.
     * 
     * @param component - unique identifier of the component to time
     */
    public TimeLogger() {
		this.startDate = null;
    }

    /**
     * Starts the timer for the named component operation.
     */
    public void start() {
        running = true;
        start = System.nanoTime();
        startDate = new Date();
    }

    /**
     * Stops the timer and writes an entry to the log.
     * 
     * @param didTimeOut - Set to true if the external system timed out
     */
    public void stop(final boolean didTimeOut) {
        stopAndPrint();
    }

    /**
     * Stops the timer and writes an entry to the log.
     */
    public void stop() {
        stop(false);
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * Does the actual job of stopping the timer, writing an entry to the log and populating the
     * timings map maintained for the individual thread. The timings map will get examined in the
     * runtime servlet filter which will finally output all interface timings into the logs
     * 
     * @param didTimeOut - Set to true if the external system timed out <- NOT USER AT THE MOMENT
     * The didTimeOut param could be used to determineif the time it took constituted a timeout 
     * 
     */
    //private void stopAndPrint(final boolean didTimeOut) {
    public void stopAndPrint() {
        String methodName = "stopAndPrint";
        running = false;
        long end = System.nanoTime();
        long duration = end - start;
        String component = operationName;
        long millis = duration / NANOS_PER_MILLI;
        float seconds = millis / ONE_THOUSAND_MS;
        String msg = String.format("component %s completed in %dms %6.3fs", operationName.replace(' ', '_').toUpperCase(), millis, seconds);

        if (LOGGER.isLoggable(Level.FINE)) {
        	LOGGER.logp(Level.INFO, CLASSNAME, "timelogger", msg);
        }
        
        // the format of this logger needs to be kept for the dashboard stats which are based on it and are written with unix tools 
        stopAndPrint(Level.INFO);
        
        // Modified to make the recording use the generic component name
        try {
            List<Long> times = new ArrayList<Long>();;
            times.add(new Long(millis));

        } catch (Exception e) {
            LOGGER.logp(Level.FINEST, CLASSNAME, methodName, "Could not capture timing for component:" + component);
        }
    }

    /**
     * Logs duration at supplied log-level.
     * @param logLevel
     */
    public void stopAndPrint(Level logLevel) {
        Date endDate = new Date();
        long longDiffInMilliSecs = endDate.getTime() - startDate.getTime();
        if (LOGGER.isLoggable(logLevel)) {
            LOGGER.log(logLevel, this.operationName + " took " + longDiffInMilliSecs + " millisecs");
        }
    }
}
