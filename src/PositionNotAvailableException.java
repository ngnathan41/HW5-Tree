/**Custom exception that indicates that an OrganismNode has max prey.
 * @author Nathan Ng
 *  email: nathan.ng@stonybrook.edu
 *  ID: 116188023
 *  Recitation: 4
 */
public class PositionNotAvailableException extends Exception {
    /**Throws an exception with a message.
     *
     * @param message
     */
    public PositionNotAvailableException(String message) {
        super(message);
    }

    /**
     * Throws an exception.
     */
    public PositionNotAvailableException() {

    }
}
