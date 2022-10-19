package edu.kit.informatik.employees;

import edu.kit.informatik.exception.ProgramException;
import edu.kit.informatik.time.CustomDate;

/**
 * This class is the structural representation of the employee in the program
 * Implements the Comparable interface to override and customize the compareTo method
 *
 * @author Daniel Winata
 * @version 1.0
 */
public class Employee implements Comparable<Employee> {
    // usual worker
    public static String A = "A";
    // night worker
    public static String N = "N";
    // night production worker
    public static String NP = "NP";
    // production worker
    public static String P = "P";
    // utility attribute to count and increase the id by 1 each time the object is created
    private static int idCounter = 0;
    // string representation of the employee role
    private final String role;
    // string representation of the employee name
    private final String name;
    // string representation of the employee surname
    private final String surname;
    // date representation of the employee birth date
    private final CustomDate dateOfBirth;
    // integer representation of the employee id
    private int id;
    // Sunday counter
    private int sundayCounter;

    /**
     * Initialization of the essential employee attributes such as name, surname, date of birth, role
     * id increases by 1 each time, the object id created
     *
     * @param role        employee role
     * @param name        employee name
     * @param surname     employee surname
     * @param dateOfBirth employee date of birth
     * @throws ProgramException if the incorrect role formats are given
     */
    public Employee(final String role, final String name, final String surname, final CustomDate dateOfBirth)
            throws ProgramException {
        if (!(role.equals(N) || role.equals(A) || role.equals(NP) || role.equals(P))) {
            throw new ProgramException("incorrect role is given.");
        }
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        idCounter++;
        this.id = idCounter;
    }

    /**
     * @return string representation of the employee (name surname birthday role)
     */
    @Override
    public String toString() {
        return name + " " + surname + " " + dateOfBirth + " " + role;
    }

    /**
     * Sorts employees by their id increasingly from bottom to top
     *
     * @param employee to compare another employee with
     * @return the integer resulting their distance to each other
     */
    @Override
    public int compareTo(Employee employee) {
        return (Integer.compare(this.getId(), employee.getId()));
    }

    /**
     * @return the string value of the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @return the integer value of id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the integer value of sunday amounts
     */
    public int getSundayCounter() {
        return sundayCounter;
    }

    /**
     * Sets new value to the sunday amount
     *
     * @param sundayCounter new value of sunday amount
     */
    public void setSundayCounter(int sundayCounter) {
        this.sundayCounter = sundayCounter;
    }
}
