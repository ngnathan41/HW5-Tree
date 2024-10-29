/**Custom exception that indicates that a prey is a plant.
 * @author Nathan Ng
 *  email: nathan.ng@stonybrook.edu
 *  ID: 116188023
 *  Recitation: 4
 */
public class IsPlantException extends Exception {

    /**Throws an exception with a message.
     *
     * @param message
     */
    public IsPlantException(String message) {
        super(message);
    }

    /**
     * Throws an exception.
     */
    public IsPlantException() {

    }
}
