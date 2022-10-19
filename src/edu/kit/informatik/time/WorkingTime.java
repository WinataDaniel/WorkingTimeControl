package edu.kit.informatik.time;

/**
 * This class is the structural representation of the working time in the program
 *
 * @author Daniel Winata
 * @version 1.0
 */
public class WorkingTime {
    // working date representation
    private CustomDate workingDate;
    // working time representation
    private CustomTime workingTime;

    /**
     * Initialization of the working date and time of the employee
     *
     * @param workingDate working date of the employee
     * @param workingTime working time of the employee
     */
    public WorkingTime(CustomDate workingDate, CustomTime workingTime) {
        this.workingDate = workingDate;
        this.workingTime = workingTime;
    }

    /**
     * @return string representation of the working hours, which is YYYY-MM-DDThh:mm
     */
    @Override
    public String toString() {
        return workingDate + "T" + workingTime;
    }

    /**
     * @return the custom date object value of the date
     */
    public CustomDate getWorkingDate() {
        return workingDate;
    }

    /**
     * @return the custom time object value of the date
     */
    public CustomTime getWorkingTime() {
        return workingTime;
    }
}
