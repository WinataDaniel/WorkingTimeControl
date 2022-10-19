package edu.kit.informatik.time;

import edu.kit.informatik.exception.ProgramException;

/**
 * This class is the structural representation of the date in the program
 *
 * @author Daniel Winata
 * @version 1.0
 */
public class CustomDate {
    // minimal possible year
    private static final int MIN_POSSIBLE_YEAR = 1000;
    // maximal possible year
    private static final int MAX_POSSIBLE_YEAR = 9999;
    // minimal possible month
    public static final int MIN_POSSIBLE_MONTH = 1;
    // maximal possible month
    public static final int MAX_POSSIBLE_MONTH = 12;
    // minimal possible day
    public static final int MIN_POSSIBLE_DAY = 1;
    // first month
    private static final int JAN = 1;
    // third month
    private static final int MAR = 3;
    // fifth month
    private static final int MAY = 5;
    // seventh month
    private static final int JUL = 7;
    // eighth month
    private static final int AUG = 8;
    // tenth month
    private static final int OKT = 10;
    // twelfth month
    private static final int DEC = 12;
    // fourth month
    private static final int APR = 4;
    // sixth month
    private static final int JUN = 6;
    // ninth month
    private static final int SEP = 9;
    // eleventh month
    private static final int NOV = 11;
    // second month
    private static final int FEB = 2;
    // 31 days
    public static final int THIRTY_ONE = 31;
    // 30 days
    private static final int THIRTY = 30;
    // 29 days
    private static final int TWENTY_NINE = 29;
    // 28 days
    private static final int TWENTY_EIGHT = 28;
    // utility integer to prove the leap year
    private static final int FOUR_HUNDRED = 400;
    // utility integer to prove the leap year
    public static final int ZERO = 0;
    // utility integer to prove the leap year
    private static final int FOUR = 4;
    // utility integer to prove the leap year
    private static final int HUNDRED = 100;
    // utility integer to find a week day
    private static final int TWO = 2;
    // utility integer to find a week day
    private static final int FOURTEEN = 14;
    // utility integer to find a week day
    private static final int MAX_POSSIBLE_WEEKDAY = 7;
    // constant of year to estimate the week day
    private int yO = 0;
    // constant of month to estimate the week day
    private int mO = 0;
    // constant of day to estimate the week day
    private int dO = 0;
    // helpful constant to estimate the week day
    private int x = 0;
    // integer representation of the year
    private int year;
    // integer representation of the month
    private int month;
    // integer representation of the day
    private int day;

    /**
     * Initialization of the date, which consists of year, month and day
     *
     * @param year  year of the date
     * @param month month of the date
     * @param day   day of the date
     * @throws ProgramException if the maxi- und minimal values of the parameters are incorrect
     */
    public CustomDate(int year, int month, int day) throws ProgramException {
        if (year < MIN_POSSIBLE_YEAR || year > MAX_POSSIBLE_YEAR) {
            throw new ProgramException("the given year is out of range.");
        }
        if (month < MIN_POSSIBLE_MONTH || month > MAX_POSSIBLE_MONTH) {
            throw new ProgramException("the given month is out of range.");
        }
        if (day < MIN_POSSIBLE_DAY || day > checkMonthReturnDay(month, year)) {
            throw new ProgramException("the given day is out of month's range.");
        }
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Checks the year and month of the date, checks the leap year
     * to return the maximal number of days in the month
     *
     * @param month
     * @param year
     * @return
     */
    private int checkMonthReturnDay(int month, int year) {
        switch (month) {
            case JAN:
            case MAR:
            case MAY:
            case JUL:
            case AUG:
            case OKT:
            case DEC:
                day = THIRTY_ONE;
                return day;
            case APR:
            case JUN:
            case SEP:
            case NOV:
                day = THIRTY;
                return day;
            case FEB:
                if (((year % FOUR == ZERO) && !(year % HUNDRED == ZERO)) || (year % FOUR_HUNDRED == ZERO)) {
                    day = TWENTY_NINE;
                    return day;
                } else {
                    day = TWENTY_EIGHT;
                    return day;
                }
            default:
                return -1;
        }
    }

    /**
     * Finds the week day by the year, month and day
     *
     * @return the week day as integer (0 - Sunday, 1 - Monday, etc.)
     */
    public int findWeekday() {
        yO = this.year - (FOURTEEN - this.month) / MAX_POSSIBLE_MONTH;
        x = yO + (yO / FOUR) - (yO / HUNDRED) + (yO / FOUR_HUNDRED);
        mO = this.month + MAX_POSSIBLE_MONTH * ((FOURTEEN - this.month) / MAX_POSSIBLE_MONTH) - TWO;
        dO = (this.day + x + (THIRTY_ONE * mO) / MAX_POSSIBLE_MONTH) % MAX_POSSIBLE_WEEKDAY;
        return dO;
    }

    /**
     * @return the string representation of the date in format YYYY-MM-DD
     */
    @Override
    public String toString() {
        String days = String.format("%02d", day);
        String months = String.format("%02d", month);
        return year + "-" + months + "-" + days;
    }

    /**
     * @return integer value of the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @return integer value of the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @return integer value of the day
     */
    public int getDay() {
        return day;
    }
}
