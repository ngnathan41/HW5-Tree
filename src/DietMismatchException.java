/**Custom exception that indicates that the diet of the prey doesn't match the predator.
 * @author Nathan Ng
 *  email: nathan.ng@stonybrook.edu
 *  ID: 116188023
 *  Recitation: 4
 */
public class DietMismatchException extends Exception {

    /**Throws an exception with a message.
     *
     * @param message
     */
    public DietMismatchException(String message) {
        super(message);
    }

    /**
     * Throws an exception.
     */
    public DietMismatchException() {

    }
}
