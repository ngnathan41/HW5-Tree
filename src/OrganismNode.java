/**OrganismNode represents a node in an ecological tree, it tracks the name, types of food eaten, and up to three prey.
 * @author Nathan Ng
 *  email: nathan.ng@stonybrook.edu
 *  ID: 116188023
 *  Recitation: 4
 */
public class OrganismNode {
    private String name;
    private boolean isPlant, isHerbivore, isCarnivore;
    private OrganismNode left, middle, right;

    /**Creates an instance of OrganismNode with null attributes.
     *
     */
    public OrganismNode() {

    }

    /**Creates an instance of OrganismNode with attributes and no children.
     *
     * @param name Name of organism
     * @param isPlant Indicates if organism is a plant
     * @param isHerbivore Indicates if the organism eats plants
     * @param isCarnivore Indicates if the organism eats animals
     */
    public OrganismNode(String name, boolean isPlant, boolean isHerbivore, boolean isCarnivore){
        this.name = name;
        this.isPlant = isPlant;
        this.isHerbivore = isHerbivore;
        this.isCarnivore = isCarnivore;
    }

    /**Returns name of organism.
     *
     * @return Name of organism.
     */
    public String getName() {
        return name;
    }

    /**Indicates if organism is plant.
     *
     * @return whether organism is plant.
     */
    public boolean getIsPlant() {
        return isPlant;
    }

    /**Indicates if organism is a herbivore.
     *
     * @return whether organism is a herbivore.
     */
    public boolean getIsHerbivore() {
        return isHerbivore;
    }

    /**Indicates if organism is a carnivore.
     *
     * @return whether organism is a carnivore
     */
    public boolean getIsCarnivore() {
        return isCarnivore;
    }

    /**Returns left child/prey of organism.
     *
     * @return left child/prey
     */
    public OrganismNode getLeft() {
        return left;
    }

    /**Returns middle child/prey of organism.
     *
     * @return middle child/prey
     */
    public OrganismNode getMiddle() {
        return middle;
    }

    /**Returns right child/prey of organism.
     *
     * @return right child/prey
     */
    public OrganismNode getRight() {
        return right;
    }

    /**Indicates if the organism has max children.
     *
     * @return indicates if a child/prey can be added to organism
     */
    public boolean isFull(){
        return left!=null && middle!=null && right != null;
    }

    /**Sets the left child/prey of organism.
     *
     * @param left child/prey to set to left.
     */
    public void setLeft(OrganismNode left) {
        this.left = left;
    }

    /**Sets the middle child/prey of organism.
     *
     * @param middle child/prey to set to middle.
     */
    public void setMiddle(OrganismNode middle) {
        this.middle = middle;
    }

    /**Sets the right child/prey of organism.
     *
     * @param right child/prey to set to right.
     */
    public void setRight(OrganismNode right) {
        this.right = right;
    }

    /**Adds a prey/child to the organism, throws exceptions if invalid.
     *
     * @param preyNode organism to add to prey.
     * @throws PositionNotAvailableException Indicates that no more prey/children can be added.
     * @throws IsPlantException Indicates that prey cannot be added to plants.
     * @throws DietMismatchException Indicates that the prey does not match the diet of the organism.
     * @custom_Precondition The organism has space to add children, isn't a plant, and it's diet matches the prey.
     */
    public void addPrey(OrganismNode preyNode) throws PositionNotAvailableException, IsPlantException, DietMismatchException{
        if(getIsPlant())
            throw new IsPlantException();
        if(getLeft() != null && getRight() != null && getMiddle() != null)
            throw new PositionNotAvailableException();
        if(!(getIsHerbivore() && preyNode.getIsPlant()) || !(getIsCarnivore() && !preyNode.getIsPlant())             )
            throw new DietMismatchException();

        if (getLeft() == null)
            left = preyNode;
        else if (getMiddle() == null)
            middle = preyNode;
        else
            right = preyNode;
    }

}
