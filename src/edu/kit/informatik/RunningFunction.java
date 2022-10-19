package edu.kit.informatik;

import edu.kit.informatik.employees.Employee;
import edu.kit.informatik.employees.program.TimingProgram;
import edu.kit.informatik.exception.ProgramException;
import edu.kit.informatik.time.CustomDate;
import edu.kit.informatik.time.CustomTime;
import edu.kit.informatik.time.WorkingPeriods;
import edu.kit.informatik.time.WorkingTime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents functional part of the game, which runs the program
 *
 * @author Daniel Winata
 * @version 1.0
 */
public class RunningFunction {
    /**
     * Regular expression to check, if the input arguments of date and time in command line are correct
     */
    private static final String DATE_AND_TIME = "([0-9]{4})-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])T"
            + "(2[0-4]|1[0-9]|0[0-9]):([0-5]?[0-9])";
    /**
     * Regular expression to check, if the input arguments of 'employee' command and its parameters
     * given in command line are correct
     */
    private static final String EMPLOYEE = "^employee\\s(A|N|P|NP)\\s([A-Za-z]*)\\s([A-Za-z]*)\\s([0-9]{4})-"
            + "(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$";
    /**
     * Regular expression to check, if the input arguments of 'workingTime' command (without pauses)
     * and its parameters given in command line are correct
     */
    private static final String WORKING_TIME_NO_PAUSE = "^workingtime\\s([0-9]+)\\s" + DATE_AND_TIME + "\\s"
            + DATE_AND_TIME + "$";
    /**
     * Regular expression to check, if the input arguments of 'workingtime' command (with pauses)
     * and its parameters given in command line are correct
     */
    private static final String WORKING_TIME_PAUSE = "^workingtime\\s([0-9]+)\\s" + DATE_AND_TIME + "\\s"
            + DATE_AND_TIME + "\\s" + DATE_AND_TIME + "\\s" + DATE_AND_TIME + "$";
    /**
     * Regular expression to check, if the input arguments of 'list' command and its parameter (id of employee)
     * given in command line are correct
     */
    private static final String LIST_ID = "^list\\s([0-9]+)$";
    /**
     * Regular expression to check, if the input arguments of 'list' command and its parameter (time point)
     * given in command line are correct
     */
    private static final String LIST_TIMEPOINT = "^list\\s" + DATE_AND_TIME + "$";
    /**
     * Regular expression to check, if the format in given document responds to requirements
     */
    private static final String HOLIDAY_TEST = "^([0-9]{4})-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$";
    /**
     * Regular expression to check, if the input arguments of 'quit' command are given correctly
     */
    private static final String QUIT = "^quit$";
    // utility attribute to match the supposed input with an actual input
    private static final int ONE = 1;
    // utility attribute to match the supposed input with an actual input
    private static final int TWO = 2;
    // utility attribute to match the supposed input with an actual input
    private static final int THREE = 3;
    // utility attribute to match the supposed input with an actual input
    private static final int FOUR = 4;
    // utility attribute to match the supposed input with an actual input
    private static final int FIVE = 5;
    // utility attribute to match the supposed input with an actual input
    private static final int SIX = 6;
    // utility attribute to match the supposed input with an actual input
    private static final int SEVEN = 7;
    // utility attribute to match the supposed input with an actual input
    private static final int EIGHT = 8;
    // utility attribute to match the supposed input with an actual input
    private static final int NINE = 9;
    // utility attribute to match the supposed input with an actual input
    private static final int TEN = 10;
    // utility attribute to match the supposed input with an actual input
    private static final int ELEVEN = 11;
    // utility attribute to match the supposed input with an actual input
    private static final int TWELVE = 12;
    // utility attribute to match the supposed input with an actual input
    private static final int THIRTEEN = 13;
    // utility attribute to match the supposed input with an actual input
    private static final int FOURTEEN = 14;
    // utility attribute to match the supposed input with an actual input
    private static final int FIFTEEN = 15;
    // utility attribute to match the supposed input with an actual input
    private static final int SIXTEEN = 16;
    // utility attribute to match the supposed input with an actual input
    private static final int SEVENTEEN = 17;
    // utility attribute to match the supposed input with an actual input
    private static final int EIGHTEEN = 18;
    // utility attribute to match the supposed input with an actual input
    private static final int NINETEEN = 19;
    // utility attribute to match the supposed input with an actual input
    private static final int TWENTY = 20;
    // utility attribute to match the supposed input with an actual input
    private static final int TWENTY_ONE = 21;


    /**
     * Runs commands, which are responsible for correct functionality of the program
     * Reads (if exists) the text document and takes its containing as holidays (in case of correct format)
     * Checks if the input arguments are correct
     *
     * @param args array of command line arguments
     */
    public static void main(String[] args) {
        TimingProgram program;
        try {
            if (args.length == 1) {
                String theFile = args[0];
                String[] holidaysList = Terminal.readFile(theFile);
                if (!checkHolidayFormat(holidaysList)) {
                    throw new ProgramException("holidays date format is incorrect");
                }
                program = new TimingProgram(holidaysList);
            } else {
                throw new ProgramException("given incorrect command line arguments.");
            }
        } catch (ProgramException e) {
            Terminal.printError(e.getMessage());
            program = new TimingProgram();
        }
        while (true) {
            String readLine = Terminal.readLine();
            try {
                if (Pattern.matches(EMPLOYEE, readLine)) {
                    addEmployee(program, readLine);
                } else if (Pattern.matches(WORKING_TIME_NO_PAUSE, readLine)) {
                    workingTimeNoPause(program, readLine);
                } else if (Pattern.matches(WORKING_TIME_PAUSE, readLine)) {
                    workingTimePause(program, readLine);
                } else if (Pattern.matches(LIST_ID, readLine)) {
                    listID(program, readLine);
                } else if (Pattern.matches(LIST_TIMEPOINT, readLine)) {
                    listTimePoint(program, readLine);
                } else if (Pattern.matches(QUIT, readLine)) {
                    return;
                } else {
                    throw new ProgramException("incorrect command arguments. Please try again.");
                }
            } catch (ArrayIndexOutOfBoundsException | ProgramException e) {
                Terminal.printError(e.getMessage());
            }
        }
    }

    /**
     * Adds employees to the system, after checking the input format
     *
     * @param program timing program object, to reach its method
     * @param input   the string input of the read command line
     * @throws ProgramException if date format is given incorrect
     */
    private static void addEmployee(TimingProgram program, String input) throws ProgramException {
        Matcher matchCommand = Pattern.compile(EMPLOYEE).matcher(input);
        if (matchCommand.find()) {
            program.addEmployee(new Employee(matchCommand.group(ONE), matchCommand.group(TWO), matchCommand.group(THREE),
                    new CustomDate(Integer.parseInt(matchCommand.group(FOUR)), Integer.parseInt(matchCommand.group(FIVE)),
                            Integer.parseInt(matchCommand.group(SIX)))));
        }
    }

    /**
     * Add working time (without pause) to the corresponding employee
     *
     * @param program timing program object, to reach its method
     * @param input   the string input of the read command line
     * @throws ProgramException if date and time formats are given incorrect
     */
    private static void workingTimeNoPause(TimingProgram program, String input) throws ProgramException {
        Matcher matchCommand = Pattern.compile(WORKING_TIME_NO_PAUSE).matcher(input);
        if (matchCommand.find()) {
            program.addWorkingTime((Integer.parseInt(matchCommand.group(ONE))), new WorkingPeriods(
                    new WorkingTime(new CustomDate(Integer.parseInt(matchCommand.group(TWO)),
                            Integer.parseInt(matchCommand.group(THREE)), Integer.parseInt(matchCommand.group(FOUR))),
                            new CustomTime(Integer.parseInt(matchCommand.group(FIVE)),
                                    Integer.parseInt(matchCommand.group(SIX)))),
                    new WorkingTime(new CustomDate(Integer.parseInt(matchCommand.group(SEVEN)),
                            Integer.parseInt(matchCommand.group(EIGHT)), Integer.parseInt(matchCommand.group(NINE))),
                            new CustomTime(Integer.parseInt(matchCommand.group(TEN)),
                                    Integer.parseInt(matchCommand.group(ELEVEN))))));
        }
    }

    /**
     * Add working time (with pause) to the corresponding employee
     *
     * @param program timing program object, to reach its method
     * @param input   the string input of the read command line
     * @throws ProgramException if date and time formats are given incorrect
     */
    private static void workingTimePause(TimingProgram program, String input) throws ProgramException {
        Matcher matchCommand = Pattern.compile(WORKING_TIME_PAUSE).matcher(input);
        if (matchCommand.find()) {
            program.addWorkingTime((Integer.parseInt(matchCommand.group(ONE))), new WorkingPeriods(
                    new WorkingTime(new CustomDate(Integer.parseInt(matchCommand.group(TWO)),
                            Integer.parseInt(matchCommand.group(THREE)), Integer.parseInt(matchCommand.group(FOUR))),
                            new CustomTime(Integer.parseInt(matchCommand.group(FIVE)),
                                    Integer.parseInt(matchCommand.group(SIX)))),
                    new WorkingTime(new CustomDate(Integer.parseInt(matchCommand.group(SEVEN)),
                            Integer.parseInt(matchCommand.group(EIGHT)), Integer.parseInt(matchCommand.group(NINE))),
                            new CustomTime(Integer.parseInt(matchCommand.group(TEN)),
                                    Integer.parseInt(matchCommand.group(ELEVEN)))),
                    new WorkingTime(new CustomDate(Integer.parseInt(matchCommand.group(TWELVE)),
                            Integer.parseInt(matchCommand.group(THIRTEEN)), Integer.parseInt(matchCommand.group(FOURTEEN))),
                            new CustomTime(Integer.parseInt(matchCommand.group(FIFTEEN)),
                                    Integer.parseInt(matchCommand.group(SIXTEEN)))),
                    new WorkingTime(new CustomDate(Integer.parseInt(matchCommand.group(SEVENTEEN)),
                            Integer.parseInt(matchCommand.group(EIGHTEEN)), Integer.parseInt(matchCommand.group(NINETEEN))),
                            new CustomTime(Integer.parseInt(matchCommand.group(TWENTY)),
                                    Integer.parseInt(matchCommand.group(TWENTY_ONE))))));
        }
    }

    /**
     * List employees working times by giving their id
     *
     * @param program timing program object, to reach its method
     * @param input   the string input of the read command line
     * @throws ProgramException if id is given incorrect
     */
    private static void listID(TimingProgram program, String input) throws ProgramException {
        Matcher matchCommand = Pattern.compile(LIST_ID).matcher(input);
        if (matchCommand.find()) {
            program.printEmployeeById(Integer.parseInt(matchCommand.group(ONE)));
        }
    }

    /**
     * List the Employees, if the given time point is in range of their working time
     *
     * @param program timing program object, to reach its method
     * @param input   the string input of the read command line
     * @throws ProgramException if date and time formats are given incorrect
     */
    private static void listTimePoint(TimingProgram program, String input) throws ProgramException {
        Matcher matchCommand = Pattern.compile(LIST_TIMEPOINT).matcher(input);
        if (matchCommand.find()) {
            program.printTimePoint(new WorkingTime(new CustomDate(Integer.parseInt(matchCommand.group(ONE)),
                    Integer.parseInt(matchCommand.group(TWO)), Integer.parseInt(matchCommand.group(THREE))),
                    new CustomTime(Integer.parseInt(matchCommand.group(FOUR)),
                            Integer.parseInt(matchCommand.group(FIVE)))));
        }
    }

    /**
     * Checks the format of holidays, which is YYYY-MM-DD
     *
     * @param holidaysList list of holidays from the text document
     * @return true if everything is correct, else return false
     */
    private static boolean checkHolidayFormat(String[] holidaysList) {
        for (String day : holidaysList) {
            if (!Pattern.matches(HOLIDAY_TEST, day)) {
                return false;
            }
        }
        return true;
    }
}
