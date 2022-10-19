package edu.kit.informatik.employees.program;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.employees.Employee;
import edu.kit.informatik.exception.ProgramException;
import edu.kit.informatik.time.CustomDate;
import edu.kit.informatik.time.WorkingPeriods;
import edu.kit.informatik.time.WorkingTime;

import java.util.*;

/**
 * This class is the structural representation of the working periods in the program
 * Implements the Comparable interface to override and customize the compareTo method
 *
 * @author Daniel Winata
 * @version 1.0
 */
public class TimingProgram {
    // Zero is defined in weekdays as Sunday
    private static final int SUN = 0;
    // minimal possible minute, hour or days
    private static final int ZERO_HMD = 0;
    // maximal possible minute
    private static final int MAX_POSSIBLE_MIN = 59;
    // six hours
    public static final int SIX_HOURS = 6;
    // nine hours
    private static final int NINE_HOURS = 9;
    // amount of maximal allowed Sundays to work in one year
    private static final int MAX_SUNDAYS = 37;
    // empty output, if the the list is empty
    private static final String EMPTY = "EMPTY";
    // utility year representation helpful in transferring the holidays to an array
    private int calendarYear;
    // utility month representation helpful in transferring the holidays to an array
    private int calendarMonth;
    // utility day representation helpful in transferring the holidays to an array
    private int calendarDay;
    // array of holidays from the text document
    private CustomDate[] dHoliday;
    // map to represent the employee and its corresponding working times as list elements
    private Map<Employee, List<WorkingPeriods>> timingMap = new HashMap<>();
    // list of employees added to the system
    private List<Employee> employeeList = new ArrayList<>();
    // the list of employees, which were at work during the given time point
    // it refreshes each time the employees in time point are asked
    private List<Employee> employeesInTimePoint = new ArrayList<>();

    /**
     * Initialization of the timing program for the case, that the text document
     * with holidays is successfully taken and transferred to the program
     * Adding all holidays to CustomDate array
     *
     * @param stringHolidays string array of holidays, where each element is a string of format YYYY-MM-DD
     * @throws ProgramException if the date is given incorrectly
     */
    public TimingProgram(String[] stringHolidays) throws ProgramException {
        dHoliday = new CustomDate[stringHolidays.length];
        for (int i = 0; i < stringHolidays.length; i++) {
            String[] calendarDates = stringHolidays[i].split("-");
            calendarYear = Integer.parseInt(calendarDates[0]);
            calendarMonth = Integer.parseInt(calendarDates[1]);
            calendarDay = Integer.parseInt(calendarDates[2]);
            dHoliday[i] = new CustomDate(calendarYear, calendarMonth, calendarDay);
        }
    }

    /**
     * Initialization of the timing program for the case, that holidays do not exist
     */
    public TimingProgram() {
    }

    /**
     * Adds an employee to the system, if it was not added yet
     *
     * @param employee employee to be added
     * @throws ProgramException if employee with this id was already added
     */
    public void addEmployee(Employee employee) throws ProgramException {
        if (!findEmployee(employee.getId())) {
            employeeList.add(employee);
            Terminal.printLine("Added new employee with identifier " + employee.getId() + ".");
        } else {
            throw new ProgramException("employee with this id was already added.");
        }
    }

    /**
     * Adds working time for corresponding employees
     * Restricts carrying out the function, if multiple conditions are not fulfilled
     *
     * @param id
     * @param periods
     * @throws ProgramException if not production workers wanted to work on holidays,
     *                          day workers wanted to work at night, wrong time periods given
     */
    public void addWorkingTime(int id, WorkingPeriods periods) throws ProgramException {
        if (returnEmployeeById(id) == null) {
            throw new ProgramException("employee was not added yet.");
        }
        if ((Objects.requireNonNull(returnEmployeeById(id)).getRole().equals(Employee.P)
                || Objects.requireNonNull(returnEmployeeById(id)).getRole().equals(Employee.A))
                && (periods.nightWork())) {
            throw new ProgramException("night work can only be performed by night workers.");
        }
        if ((Objects.requireNonNull(returnEmployeeById(id)).getRole().equals(Employee.N)
                || Objects.requireNonNull(returnEmployeeById(id)).getRole().equals(Employee.A))) {
            if ((periods.getStartW().getWorkingDate().findWeekday() == SUN
                    || periods.getEndW().getWorkingDate().findWeekday() == SUN)) {
                throw new ProgramException("only production workers can work on Sundays.");
            }
            if (holidaysExist()) {
                if (matchHolidays(periods.getStartW().getWorkingDate())
                        || matchHolidays(periods.getEndW().getWorkingDate())) {
                    throw new ProgramException("only production workers can work on holidays.");
                }
            }
        }
        if (periods.getStartP() == null || periods.getEndP() == null) {
            if (((periods.recordedTime().getHour() >= SIX_HOURS && periods.recordedTime().getMinute()
                    > ZERO_HMD) || (periods.recordedTime().getHour() > SIX_HOURS
                    && periods.recordedTime().getMinute() >= ZERO_HMD)) && ((periods.recordedTime().getHour()
                    < NINE_HOURS && periods.recordedTime().getMinute() < MAX_POSSIBLE_MIN)
                    || (periods.recordedTime().getHour() == NINE_HOURS && periods.recordedTime().getMinute()
                    == ZERO_HMD))) {
                throw new ProgramException("the working time must be interrupted by a 30 minutes break.");
            }
            if (periods.recordedTime().getHour() > NINE_HOURS
                    && (periods.recordedTime().getMinute() >= ZERO_HMD)) {
                throw new ProgramException("the working time must be interrupted by a 45 minutes break.");
            }
        }
        if (periods.recordedTime().getHour() <= ZERO_HMD
                && periods.recordedTime().getMinute() <= ZERO_HMD) {
            throw new ProgramException("wrong time periods given.");
        }
        if (timingMap.containsKey(returnEmployeeById(id))) {
            for (Employee employee : timingMap.keySet()) {
                if (employee.equals(returnEmployeeById(id))) {
                    if (workingTimeIntersection(timingMap.get(returnEmployeeById(id)), periods)) {
                        throw new ProgramException("the working period to be added intersects with existing one.");
                    }
                    timingMap.get(returnEmployeeById(id)).add(periods);
                    countSundays(employee, periods);
                    timingMap.replace(employee, timingMap.get(returnEmployeeById(id)));
                    if (periods.getEndP() == null || periods.getStartP() == null) {
                        Terminal.printLine("Recorded working time " + periods.recordedTime()
                                + " for employee " + id + ".");
                    } else if (periods.getEndP() != null || periods.getStartP() != null) {
                        Terminal.printLine("Recorded working time " + periods.recordedTime()
                                + " for employee " + id + ".");
                    }
                }
            }
        } else {
            ArrayList<WorkingPeriods> periodsArrayList = new ArrayList<>();
            periodsArrayList.add(periods);
            countSundays(Objects.requireNonNull(returnEmployeeById(id)), periods);
            timingMap.put(returnEmployeeById(id), periodsArrayList);
            if (periods.getEndP() == null || periods.getStartP() == null) {
                Terminal.printLine("Recorded working time " + periods.recordedTime()
                        + " for employee " + id + ".");
            } else if (periods.getEndP() != null || periods.getStartP() != null) {
                Terminal.printLine("Recorded working time " + periods.recordedTime()
                        + " for employee " + id + ".");
            }
        }
    }


    /**
     * Checks if the existing working hours intersect with new working hours to be added
     *
     * @param addedPeriodsList list of existing working hours
     * @param periodsToBeAdded working periods, we want to add
     * @return true, if they intersect, else false
     */
    private boolean workingTimeIntersection(List<WorkingPeriods> addedPeriodsList, WorkingPeriods periodsToBeAdded) {
        for (WorkingPeriods period : addedPeriodsList) {
            if (period.withinWorkingTime(periodsToBeAdded.getStartW())
                    || period.withinWorkingTime(periodsToBeAdded.getEndW())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Counts Sundays and increases by 1, after the NP or N have worked on Sunday
     * Restricts the amount of possible working Sundays until 37
     *
     * @param employee's worked Sundays are counted
     * @param periods    to be checked as Sunday work
     * @throws ProgramException if the worker has worked more than 37 Sundays
     */
    private void countSundays(Employee employee, WorkingPeriods periods) throws ProgramException {
        if (employee.getRole().equals(Employee.P) || employee.getRole().equals(Employee.NP)) {
            if (employee.getSundayCounter() >= MAX_SUNDAYS) {
                throw new ProgramException("at least 15 Sundays in year should be free from work.");
            }
            if (periods.getStartW().getWorkingDate().findWeekday() == SUN
                    || periods.getEndW().getWorkingDate().findWeekday() == SUN) {
                employee.setSundayCounter(employee.getSundayCounter() + 1);
            }
        }
    }

    /**
     * Checks if the given date matches with ine of the holidays
     *
     * @param date to be checked
     * @return true, if date matches, else false
     */
    private boolean matchHolidays(CustomDate date) {
        for (CustomDate customDate : dHoliday) {
            if (customDate.getYear() == date.getYear() && customDate.getMonth() == date.getMonth()
                    && customDate.getDay() == date.getDay()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints out the list of employees, which were working in the given time point
     * Sorting them by id
     *
     * @param timePoint given time point
     */
    public void printTimePoint(WorkingTime timePoint) {
        for (Employee worker : timingMap.keySet()) {
            for (WorkingPeriods onePeriod : timingMap.get(worker)) {
                if (onePeriod.withinWorkingTime(timePoint) && !employeesInTimePoint.contains(worker)) {
                    employeesInTimePoint.add(worker);
                }
            }
        }
        Collections.sort(employeesInTimePoint);
        if (employeesInTimePoint.isEmpty() || timingMap.isEmpty()) {
            Terminal.printLine(EMPTY);
        } else {
            String result = "";
            for (Employee employee : employeesInTimePoint) {
                result += employee + "\n";
            }
            if (result.length() > 0 && result.charAt(result.length() - 1) == '\n') {
                result = result.substring(0, result.length() - 1);
                Terminal.printLine(result);
            }
        }
        employeesInTimePoint.clear();
    }


    /**
     * Prints out the worked time and working hours of the employee by id
     *
     * @param id employee's id
     * @throws ProgramException if incorrect id input
     */
    public void printEmployeeById(int id) throws ProgramException {
        if (id <= 0) {
            throw new ProgramException("incorrect id input.");
        }
        try {
            if (timingMap.get(returnEmployeeById(id)).isEmpty()) {
                Terminal.printLine(EMPTY);
            }
            Collections.sort(timingMap.get(returnEmployeeById(id)));
            String result = "";
            for (int i = 0; i < timingMap.get(returnEmployeeById(id)).size(); i++) {
                result += timingMap.get(returnEmployeeById(id)).get(i).getRecordedTime().toString()
                        + " " + timingMap.get(returnEmployeeById(id)).get(i) + "\n";
            }
            if (result.length() > 0 && result.charAt(result.length() - 1) == '\n') {
                result = result.substring(0, result.length() - 1);
                Terminal.printLine(result);
            }
        } catch (NullPointerException e) {
            Terminal.printLine(EMPTY);
        }
    }


    /**
     * Returns the employee object by its id, if the employee was added
     *
     * @param id of employee
     * @return the employee object
     * @throws ProgramException if the employee has not been added yet
     */
    private Employee returnEmployeeById(int id) throws ProgramException {
        if (employeeList.isEmpty()) {
            throw new ProgramException("the employee has not been added yet.");
        }
        for (Employee e : employeeList) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }


    /**
     * @return if holidays exist true, else false
     */
    private boolean holidaysExist() {
        return dHoliday != null;
    }

    /**
     * Searches an employee by id
     *
     * @param id of employee
     * @return true if the employee was added, else false
     */
    private boolean findEmployee(int id) {
        for (Employee e : employeeList) {
            if (e.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
