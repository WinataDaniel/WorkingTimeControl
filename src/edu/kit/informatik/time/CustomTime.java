package edu.kit.informatik.time;

import edu.kit.informatik.exception.ProgramException;

/**
 * This class is the structural representation of the time in the program
 *
 * @author Daniel Winata
 * @version 1.0
 */
public class CustomTime {
    // maximal possible hours
    public static final int MAX_POSSIBLE_HOURS = 24;
    //minimal possible hours
    public static final int MIN_POSSIBLE_HOURS = 0;
    // maximal possible minutes
    public static final int MAX_POSSIBLE_MINUTES = 60;
    //minimal possible minutes
    public static final int MIN_POSSIBLE_MINUTES = 0;
    // representation of hours
    private int hour;
    // representation of minutes
    private int minute;

    /**
     * Initialization of hour and minute, restricting their minimal and maximal values
     *
     * @param hour   hour of the day
     * @param minute minute of the hour
     * @throws ProgramException if the incorrect time maxi- and minimums are given
     */
    public CustomTime(int hour, int minute) throws ProgramException {
        if (hour > MAX_POSSIBLE_HOURS || hour < MIN_POSSIBLE_HOURS) {
            throw new ProgramException("given incorrect hour dimensions.");
        }
        if (minute > MAX_POSSIBLE_MINUTES || minute < MIN_POSSIBLE_MINUTES) {
            throw new ProgramException("given incorrect minute dimensions");
        }
        if (hour == MAX_POSSIBLE_HOURS && minute > MIN_POSSIBLE_MINUTES) {
            throw new ProgramException("incorrect input of hour minutes.");
        }
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * @return the string representation of the time, which is hh:mm
     */
    @Override
    public String toString() {
        String hours = String.format("%02d", hour);
        String minutes = String.format("%02d", minute);
        return hours + ":" + minutes;
    }

    /**
     * @return an hour integer value
     */
    public int getHour() {
        return hour;
    }

    /**
     * @return a minute integer value
     */
    public int getMinute() {
        return minute;
    }
}
