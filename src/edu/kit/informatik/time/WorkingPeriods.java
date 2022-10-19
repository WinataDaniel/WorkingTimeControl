package edu.kit.informatik.time;

import edu.kit.informatik.employees.program.TimingProgram;
import edu.kit.informatik.exception.ProgramException;

/**
 * This class is the structural representation of the working periods in the program
 * Implements the Comparable interface to override and customize the compareTo method
 *
 * @author Daniel Winata
 * @version 1.0
 */
public class WorkingPeriods implements Comparable<WorkingPeriods> {
    // representation of the start of working time
    private WorkingTime startW;
    // representation of the end of working time
    private WorkingTime endW;
    // representation of the start of pause
    private WorkingTime startP;
    // representation of the end of pause
    private WorkingTime endP;
    // representation of the worked time in the given time range
    private CustomTime recordedTime;


    /**
     * Initialization of the attributes of working periods (with pause)
     * Restricting the conditions for the input of working times
     *
     * @param startW start of the working time
     * @param endW   end of working time
     * @param startP start of pause
     * @param endP   end of pause
     * @throws ProgramException if the date was given incorrectly, pause if not within working hours
     */
    public WorkingPeriods(WorkingTime startW, WorkingTime endW, WorkingTime startP, WorkingTime endP)
            throws ProgramException {
        if (startW.getWorkingDate().getYear() != endW.getWorkingDate().getYear()
                && ((startW.getWorkingDate().getMonth() != CustomDate.MAX_POSSIBLE_MONTH
                && endW.getWorkingDate().getMonth() != CustomDate.MIN_POSSIBLE_MONTH)
                && (startW.getWorkingDate().getDay() != CustomDate.THIRTY_ONE && endW.getWorkingDate().getDay()
                != CustomDate.MIN_POSSIBLE_DAY))) {
            throw new ProgramException("incorrect start and end years given.");
        }
        if (startW.getWorkingDate().getMonth() != endW.getWorkingDate().getMonth()
                && ((startW.getWorkingDate().getDay() != CustomDate.THIRTY_ONE)
                && (endW.getWorkingDate().getDay() != CustomDate.MIN_POSSIBLE_DAY))) {
            throw new ProgramException("incorrect start and end months given.");
        }
        if (startP.getWorkingDate().getYear() == endP.getWorkingDate().getYear()
                && startP.getWorkingDate().getMonth() == endP.getWorkingDate().getMonth()) {
            if (endP.getWorkingDate().getDay() < startP.getWorkingDate().getDay()) {
                throw new ProgramException("end of the pause is earlier than its start");
            } else if (endP.getWorkingDate().getDay() == startP.getWorkingDate().getDay()) {
                if (endP.getWorkingTime().getHour() < startP.getWorkingTime().getHour()) {
                    throw new ProgramException("end of the pause hours is earlier than its start");
                } else if (endP.getWorkingTime().getHour() == startP.getWorkingTime().getHour()) {
                    if (endP.getWorkingTime().getMinute() < startP.getWorkingTime().getMinute()) {
                        throw new ProgramException("end of the pause minutes is earlier than its start");
                    }
                }
            }
        }
        this.startW = startW;
        this.endW = endW;
        if (!(withinWorkingTime(startP) && withinWorkingTime(endP))) {
            throw new ProgramException("the pause is not within working hours.");
        }
        this.startP = startP;
        this.endP = endP;
    }


    /**
     * Initialization of the attributes of working periods (without pause)
     * Restricting the conditions for the input of working times
     *
     * @param startW start of the working time
     * @param endW   end of working time
     * @throws ProgramException if the date was given incorrectly, pause if not within working hours
     */
    public WorkingPeriods(WorkingTime startW, WorkingTime endW) throws ProgramException {
        if (startW.getWorkingDate().getYear() != endW.getWorkingDate().getYear()
                && ((startW.getWorkingDate().getMonth() != CustomDate.MAX_POSSIBLE_MONTH && endW.getWorkingDate().getMonth() != CustomDate.MIN_POSSIBLE_MONTH)
                && (startW.getWorkingDate().getDay() != CustomDate.THIRTY_ONE && endW.getWorkingDate().getDay() != CustomDate.MIN_POSSIBLE_DAY))) {
            throw new ProgramException("incorrect start and end years.");
        }
        if (startW.getWorkingDate().getMonth() != endW.getWorkingDate().getMonth()
                && ((startW.getWorkingDate().getDay() != CustomDate.THIRTY_ONE) && (endW.getWorkingDate().getDay() != CustomDate.MIN_POSSIBLE_DAY))) {
            throw new ProgramException("incorrect start and end months given.");
        }
        this.startW = startW;
        this.endW = endW;
    }


    /**
     * Checks if the time point consisting of year, month, day, hour and minute is included in working hours
     *
     * @param timePoint which has to be checked
     * @return true, if it is within, else false
     */
    public boolean withinWorkingTime(WorkingTime timePoint) {
        if (timePoint.getWorkingDate().getYear() == startW.getWorkingDate().getYear()
                && timePoint.getWorkingDate().getYear() == endW.getWorkingDate().getYear()) {
            if (timePoint.getWorkingDate().getMonth() == startW.getWorkingDate().getMonth()
                    && timePoint.getWorkingDate().getMonth() == endW.getWorkingDate().getMonth()) {
                if (endW.getWorkingDate().getDay() - startW.getWorkingDate().getDay() == CustomDate.MIN_POSSIBLE_DAY
                        && (timePoint.getWorkingDate().getDay() == startW.getWorkingDate().getDay()
                        || timePoint.getWorkingDate().getDay() == endW.getWorkingDate().getDay())) {
                    return utilityWithinHours(timePoint);
                } else if (timePoint.getWorkingDate().getDay() == startW.getWorkingDate().getDay()
                        && timePoint.getWorkingDate().getDay() == endW.getWorkingDate().getDay()) {
                    if (startW.getWorkingTime().getHour() <= timePoint.getWorkingTime().getHour()
                            && timePoint.getWorkingTime().getHour() <= endW.getWorkingTime().getHour()) {
                        if (startW.getWorkingTime().getHour() == timePoint.getWorkingTime().getHour()) {
                            return startW.getWorkingTime().getMinute() <= timePoint.getWorkingTime().getMinute()
                                    && timePoint.getWorkingTime().getMinute() <= CustomTime.MAX_POSSIBLE_MINUTES;
                        } else if (endW.getWorkingTime().getHour() == timePoint.getWorkingTime().getHour()) {
                            return 0 <= timePoint.getWorkingTime().getMinute()
                                    && timePoint.getWorkingTime().getMinute() <= endW.getWorkingTime().getMinute();
                        } else if (endW.getWorkingTime().getHour() == startW.getWorkingTime().getHour()
                                && timePoint.getWorkingTime().getHour() == startW.getWorkingTime().getHour()) {
                            return startW.getWorkingTime().getMinute() <= timePoint.getWorkingTime().getMinute()
                                    && timePoint.getWorkingTime().getMinute() <= endW.getWorkingTime().getMinute();
                        } else {
                            return true;
                        }
                    }
                } else if (startW.getWorkingDate().getDay() - timePoint.getWorkingDate().getDay() == CustomDate.MIN_POSSIBLE_DAY
                        && (startW.getWorkingTime().getHour() == CustomTime.MIN_POSSIBLE_HOURS
                        || endW.getWorkingTime().getHour() == CustomTime.MIN_POSSIBLE_HOURS)
                        && timePoint.getWorkingTime().getHour() == CustomTime.MAX_POSSIBLE_HOURS) {
                    return true;
                } else
                    return timePoint.getWorkingDate().getDay() - endW.getWorkingDate().getDay() == CustomDate.MIN_POSSIBLE_DAY
                            && (startW.getWorkingTime().getHour() == CustomTime.MAX_POSSIBLE_HOURS
                            || endW.getWorkingTime().getHour() == CustomTime.MAX_POSSIBLE_HOURS)
                            && timePoint.getWorkingTime().getHour() == CustomTime.MIN_POSSIBLE_HOURS;
            } else if (endW.getWorkingDate().getMonth() - startW.getWorkingDate().getMonth() == CustomDate.MIN_POSSIBLE_MONTH) {
                if (endW.getWorkingDate().getDay() - startW.getWorkingDate().getDay() < -26
                        && endW.getWorkingDate().getDay() - startW.getWorkingDate().getDay() > -31) {
                    return utilityWithinHours(timePoint);
                }
            }
        } else if (endW.getWorkingDate().getYear() - startW.getWorkingDate().getYear() == CustomDate.MIN_POSSIBLE_DAY) {
            if (endW.getWorkingDate().getMonth() - startW.getWorkingDate().getMonth() == -11) {
                if (endW.getWorkingDate().getDay() - startW.getWorkingDate().getDay() == -30) {
                    return utilityWithinHours(timePoint);
                }
            }
        }
        return false;
    }

    /**
     * Utility method to prevent using the same code twice
     * Returns true, if time point was within working start and end time,
     * in case that end and start date differ
     *
     * @param timePoint to be checked if it was during working hours
     * @return true, if time point within working time, else false
     */
    private boolean utilityWithinHours(WorkingTime timePoint) {
        if ((startW.getWorkingTime().getHour() <= timePoint.getWorkingTime().getHour()
                && timePoint.getWorkingTime().getHour() <= CustomTime.MAX_POSSIBLE_HOURS)
                || (CustomTime.MIN_POSSIBLE_HOURS <= timePoint.getWorkingTime()
                .getHour() && timePoint.getWorkingTime().getHour() <= endW.getWorkingTime().getHour())) {
            if (startW.getWorkingTime().getHour() == timePoint.getWorkingTime().getHour()) {
                return startW.getWorkingTime().getMinute() <= timePoint.getWorkingTime().getMinute()
                        && timePoint.getWorkingTime().getMinute() <= CustomTime.MAX_POSSIBLE_MINUTES;
            } else if (endW.getWorkingTime().getHour() == timePoint.getWorkingTime().getHour()) {
                return CustomTime.MIN_POSSIBLE_MINUTES <= timePoint.getWorkingTime().getMinute()
                        && timePoint.getWorkingTime().getMinute() <= endW.getWorkingTime().getMinute();
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks, if the working hours are counted as night work
     *
     * @return true, if it is night work, else false
     * @throws ProgramException if the working time cannot exceed 10 hours or employee cannot work so many months
     */
    public boolean nightWork() throws ProgramException {
        if ((endW.getWorkingTime().getHour() >= CustomDate.MIN_POSSIBLE_DAY && endW.getWorkingTime().getHour()
                <= TimingProgram.SIX_HOURS)
                || ((startW.getWorkingTime().getHour() >= 23 && startW.getWorkingTime().getHour()
                <= CustomTime.MAX_POSSIBLE_HOURS)
                || (startW.getWorkingTime().getHour() >= CustomTime.MIN_POSSIBLE_HOURS && startW.getWorkingTime().getHour() <= 4))) {
            return recordedTime().getHour() == 2 && recordedTime.getMinute() > CustomTime.MIN_POSSIBLE_MINUTES
                    || (recordedTime().getHour() > 2 && recordedTime.getMinute() >= CustomTime.MIN_POSSIBLE_MINUTES);
        }
        return false;
    }


    /**
     * Estimates the 'clean' worked time of an employee
     *
     * @return the time object, with estimated hour and minutes
     * @throws ProgramException if the working time cannot exceed 10 hours or employee cannot work so many months
     */
    public CustomTime recordedTime() throws ProgramException {
        if (endW.getWorkingDate().getDay() - startW.getWorkingDate().getDay() > CustomDate.MIN_POSSIBLE_DAY
                || endW.getWorkingDate().getDay() - startW.getWorkingDate().getDay() < CustomDate.ZERO) {
            throw new ProgramException("the working time cannot exceed 10 hours.");
        }
        if ((endW.getWorkingDate().getMonth() != startW.getWorkingDate().getMonth())
                || (endW.getWorkingDate().getYear() != startW.getWorkingDate().getYear())) {
            throw new ProgramException("employee cannot work so many months.");
        }
        if (endP == null || startP == null) {
            int hourResult = endW.getWorkingTime().getHour() - startW.getWorkingTime().getHour();
            int minuteResult = endW.getWorkingTime().getMinute() - startW.getWorkingTime().getMinute();
            if (hourResult < CustomDate.ZERO && endW.getWorkingDate().getDay() - startW.getWorkingDate().getDay()
                    == CustomDate.MIN_POSSIBLE_DAY) {
                hourResult = hourResult + 24;
            }
            if (minuteResult < CustomDate.ZERO) {
                minuteResult = minuteResult + 60;
                hourResult = hourResult - 1;
            }
            recordedTime = new CustomTime(hourResult, minuteResult);
            return recordedTime;
        } else {
            int hourResult = (endW.getWorkingTime().getHour() - startW.getWorkingTime().getHour())
                    - (endP.getWorkingTime().getHour() - startP.getWorkingTime().getHour());
            int minuteResult = (endW.getWorkingTime().getMinute() - startW.getWorkingTime().getMinute())
                    - (endP.getWorkingTime().getMinute() - startP.getWorkingTime().getMinute());
            if (hourResult < 0 && endW.getWorkingDate().getDay() - startW.getWorkingDate().getDay() == CustomDate.MIN_POSSIBLE_DAY) {
                hourResult = hourResult + 24;
            }
            if (minuteResult < CustomDate.ZERO) {
                minuteResult = minuteResult + 60;
                hourResult = hourResult - 1;
            }
            recordedTime = new CustomTime(hourResult, minuteResult);
            return recordedTime;
        }
    }


    /**
     * @return the string representation of the working periods
     */
    @Override
    public String toString() {
        if (startP == null || endP == null) {
            return startW + " " + endW;
        }
        return startW + " " + endW + " " + startP + " " + endP;
    }

    /**
     * Sorts the working time increasingly beginning from the earliest start
     *
     * @param workTime to compare another class with
     * @return the integer resulting their distance to each other
     */
    @Override
    public int compareTo(WorkingPeriods workTime) {
        if (this.getStartW().getWorkingDate().getYear() != workTime.getStartW().getWorkingDate().getYear()) {
            return this.getStartW().getWorkingDate().getYear() - workTime.getStartW().getWorkingDate().getYear();
        } else {
            if (this.getStartW().getWorkingDate().getMonth() != workTime.getStartW().getWorkingDate().getMonth()) {
                return this.getStartW().getWorkingDate().getMonth()
                        - workTime.getStartW().getWorkingDate().getMonth();
            } else {
                if (this.getStartW().getWorkingDate().getDay() != workTime.getStartW().getWorkingDate().getDay()) {
                    return this.getStartW().getWorkingDate().getDay()
                            - workTime.getStartW().getWorkingDate().getDay();
                }
            }
        }
        return 0;
    }

    /**
     * @return the custom time object of estimated worked time
     */
    public CustomTime getRecordedTime() {
        return recordedTime;
    }

    /**
     * @return the working time object of the starting work
     */
    public WorkingTime getStartW() {
        return startW;
    }

    /**
     * @return the working time object of the ending work
     */
    public WorkingTime getEndW() {
        return endW;
    }

    /**
     * @return the working time object of the starting pause
     */
    public WorkingTime getStartP() {
        return startP;
    }

    /**
     * @return the working time object of the ending pause
     */
    public WorkingTime getEndP() {
        return endP;
    }
}
