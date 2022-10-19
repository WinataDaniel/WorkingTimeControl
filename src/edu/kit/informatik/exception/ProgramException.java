package edu.kit.informatik.exception;

/**
 * Encapsulation of an exception for the illegal arguments in the system.
 *
 * @author Daniel Winata
 * @version 1.0
 */
public class ProgramException extends Exception {

    /**
     * Serial version UID for this exception type
     */
    private static final long serialVersionUID = 1888536871327098678L;

    /**
     * Instantiates a new {@link ProgramException} with the give message.
     *
     * @param message the message of the exception
     */
    public ProgramException(final String message) {
        super(message);
    }
}
