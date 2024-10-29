/**OrganismTree represents an ecological tree and provides methods for accessing and interacting with nodes.
 * @author Nathan Ng
 *  email: nathan.ng@stonybrook.edu
 *  ID: 116188023
 *  Recitation: 4
 */
public class OrganismTree {
    private OrganismNode root, cursor;

    /**Instantiates OrganismTree with one node, which is the apex predator and root.
     *
     * @param apexPredator OrganismNode to set as root and apex.
     */
    public OrganismTree(OrganismNode apexPredator){
        root = cursor = apexPredator;
    }

    /**Resets the cursor to the root.
     *
     */
    public void cursorReset(){
        cursor = root;
    }

    /**Returns the OrganismNode at the cursor.
     *
     * @return OrganismNode at cursor.
     */
    public OrganismNode getCursor(){
        return cursor;
    }

    /**Moves cursor to the child with the inputted name.
     *
     * @param name name of child to move to.
     * @throws IllegalArgumentException Indicates that no child has the input name.
     */
    public void moveCursor(String name) throws IllegalArgumentException{
        if(cursor.getLeft().getName().equals(name))
            cursor = cursor.getLeft();
        else if(cursor.getMiddle().getName().equals(name))
            cursor = cursor.getMiddle();
        else if(cursor.getRight().getName().equals(name))
            cursor = cursor.getRight();
        else
            throw new IllegalArgumentException();
    }

    /** Returns the string representation of the prey of the cursor.
     *
     * @return String of prey.
     * @throws IsPlantException Indicates that cursor is a plant and cannot have prey.
     */
    public String listPrey() throws IsPlantException{
        if(cursor.getIsPlant())
            throw new IsPlantException();
        String result = cursor.getName() + " -> ";
        if(cursor.getLeft() != null)
            result += " " + cursor.getLeft().getName();
        if(cursor.getMiddle() != null)
            result += ", " + cursor.getMiddle().getName();
        if(cursor.getRight() != null)
            result += ", " + cursor.getRight().getName();

        return result;
    }

    /**Lists the path from the apex predator to the cursor.
     *
     * @return String representation of the food chain path.
     */
    public String listFoodChain(){
        StringBuilder sb = new StringBuilder();
        listFoodChainHelper(root, sb);
        return sb.toString();
    }

    /**Helper method for listFoodChain that recursively finds the path.
     *
     * @param cursor2 cursor for traversing the OrganismTree.
     * @param sb StringBuilder that holds the name of organisms in the path.
     * @return Indicates if organism is on the path.
     */
    private Boolean listFoodChainHelper(OrganismNode cursor2, StringBuilder sb){
        if(cursor2 == null)
            return false;
        if(cursor2 == cursor) {
            sb.insert(0, cursor2.getName());
            return true;
        }
        if(listFoodChainHelper(cursor2.getLeft(), sb)){
            sb.insert(0, cursor2.getName() + " -> ");
            return true;
        }
        if(listFoodChainHelper(cursor2.getMiddle(), sb)){
            sb.insert(0, cursor2.getName() + " -> ");
            return true;
        }
        if(listFoodChainHelper(cursor2.getRight(), sb)){
            sb.insert(0, cursor2.getName() + " -> ");
            return true;
        }
        return false;

    }

    /**Prints the OrganismTree with |- to represent an animal, - as plant, and uses indents to indicate children.
     *
     */
    public void printOrganismTree(){
        StringBuilder sb = new StringBuilder();
        printOrganismTreeHelper(cursor, sb, 0);
        System.out.print(sb.toString());
    }

    /**Helper method for printOrganismTree that travels in pre-order and prints nodes.
     *
     * @param cursor2 cursor for traversing OrganismTree.
     * @param sb StringBuilder that holds the string representation.
     * @param depth Indicates the depth of the node.
     */
    private void printOrganismTreeHelper(OrganismNode cursor2,
      StringBuilder sb, int depth){

        if(cursor2 == null)
            return;
        String res;
        if(cursor2.getIsPlant())
            res = "- " + cursor2.getName();
        else
            res = "|- " + cursor2.getName();
        sb.append(res.indent(depth*4));

        printOrganismTreeHelper(cursor2.getLeft(), sb, depth +1);
        printOrganismTreeHelper(cursor2.getMiddle(), sb, depth +1);
        printOrganismTreeHelper(cursor2.getRight(), sb, depth +1);
    }

    /**Lists all plants below the cursor.
     *
     * @return String of all plants.
     */
    public String listAllPlants(){
        String res = listAllPlantsHelper(cursor);
        if(res.endsWith(", "))
            res = res.substring(0, res.length()-2);
        return res;
    }

    /**Helper method for listAllPlants, travels recursively to find all plants.
     *
     * @param cursor2 cursor to traverse with.
     * @return String of all plants.
     */
    private String listAllPlantsHelper(OrganismNode cursor2){
        if (cursor2 == null)
            return "";
        if(cursor2.getIsPlant())
            return cursor2.getName() + ", ";
        return listAllPlantsHelper(cursor2.getLeft())
          + listAllPlantsHelper(cursor2.getMiddle())
          + listAllPlantsHelper(cursor2.getRight());
    }

    /**Helper method that adds child/prey to the cursor.
     *
     * @param name Name of prey
     * @param isHerbivore Indicates if prey eats plants.
     * @param isCarnivore Indicates if prey eats animals.
     * @param isPlant Indicates if prey is a plant
     * @throws IllegalArgumentException Indicates that a child with name exists.
     * @throws PositionNotAvailableException Indicates that cursor has max number of children.
     * @throws IsPlantException Indicates that cursor is a plant and cannot have prey.
     * @throws DietMismatchException Indicates that the input prey does not match the diet of cursor.
     */
    private void addChild(String name, boolean isHerbivore,
      boolean isCarnivore, boolean isPlant)
      throws IllegalArgumentException, PositionNotAvailableException,
      IsPlantException, DietMismatchException{

        if((cursor.getLeft() != null && cursor.getLeft().getName().equals(name))
          || (cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name))
          || (cursor.getRight()!=null && cursor.getRight().getName().equals(name)))
            throw new IllegalArgumentException();

        OrganismNode child = new OrganismNode(name, isPlant, isHerbivore, isCarnivore);
        cursor.addPrey(child);
    }

    /**Adds a plant child to the cursor.
     *
     * @param name Name of prey
     * @throws IllegalArgumentException Indicates that a child with name exists.
     * @throws PositionNotAvailableException Indicates that cursor has max number of children.
     * @throws IsPlantException Indicates that cursor is a plant and cannot have prey.
     * @throws DietMismatchException Indicates that the input prey does not match the diet of cursor.
     */
    public void addPlantChild(String name) throws IllegalArgumentException,
      PositionNotAvailableException, IsPlantException, DietMismatchException{
        addChild(name, false, false,true);
    }

    /**Adds an animal child to the cursor.
     *
     * @param name Name of prey
     * @param isHerbivore Indicates if prey eats plants.
     * @param isCarnivore Indicates if prey eats animals.
     * @throws IllegalArgumentException Indicates that a child with name exists.
     * @throws PositionNotAvailableException Indicates that cursor has max number of children.
     * @throws IsPlantException Indicates that cursor is a plant and cannot have prey.
     * @throws DietMismatchException Indicates that the input prey does not match the diet of cursor.
     */
    public void addAnimalChild(String name, boolean isHerbivore, boolean isCarnivore) throws IllegalArgumentException, PositionNotAvailableException, IsPlantException, DietMismatchException{
        addChild(name, isHerbivore, isCarnivore,false);
    }

    /**Removes child of cursor with input name.
     *
     * @param name Name of child to remove.
     * @throws IllegalArgumentException Indicates that no child has the specified name.
     */
    public void removeChild(String name) throws IllegalArgumentException{
        if(cursor.getLeft().getName().equals(name)){
            cursor.setLeft(cursor.getMiddle());
            cursor.setMiddle(cursor.getRight());
            cursor.setRight(null);
        }
        else if(cursor.getMiddle().getName().equals(name)){
            cursor.setMiddle(cursor.getRight());
            cursor.setRight(null);
        }
        else if(cursor.getRight().getName().equals(name))
            cursor.setRight(null);
        else
            throw new IllegalArgumentException();
    }
}
