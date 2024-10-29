import java.util.Scanner;

/**FoodPyramid represents a food pyramid and implements a CLI to interact with the pyramid.
 * @author Nathan Ng
 *  email: nathan.ng@stonybrook.edu
 *  ID: 116188023
 *  Recitation: 4
 */
public class FoodPyramid {

    private OrganismTree tree;
    private static Scanner sc;
    private static final String TYPE = "Is the organism an herbivore / a carnivore / an omnivore? (H / C / O):";
    private static final String PLANT_ERROR = "ERROR: The cursor is at a plant node. Plants cannot be predators.";
    private static final String CAPACITY_ERROR = "ERROR: There is no more room for more prey for this predator.";
    private static final String DIET_ERROR = "ERROR: This prey cannot be added as it does not match the diet of the predator.";

    /**Instantiates FoodPyramid with an apex predator in OrganismTree.
     *
     * @param apexPredator OrganismNode to set as root/apex predator of OrganismTree.
     */
    public FoodPyramid(OrganismNode apexPredator) {
        tree = new OrganismTree(apexPredator);
    }

    /**Returns the OrganismTree of FoodPyramid.
     *
     * @return OrganismTree.
     */
    public OrganismTree getTree() {
        return tree;
    }

    public static void main(String[] args){
        sc = new Scanner(System.in);

        String commands = """
                        (PC) - Create New Plant Child
                        (AC) - Create New Animal Child
                        (RC) - Remove Child
                        (P) - Print Out Cursor’s Prey
                        (C) - Print Out Food Chain
                        (F) - Print Out Food Pyramid at Cursor
                        (LP) - List All Plants Supporting Cursor
                        (R) - Reset Cursor to Root
                        (M) - Move Cursor to Child
                        (Q) - Quit
                        """;

        System.out.println("What is the name of the apex predator?: ");
        String apexName = sc.nextLine();
        String type;

        //Probes user for details about the apex predator.
        while(true) {
            System.out.println(TYPE);
            type = sc.nextLine();
            if(type.equals("H") || type.equals("C") || type.equals("O"))
                break;
            System.out.println("Invalid Type");
        }

        OrganismNode apexPredator = new OrganismNode(apexName,
   false,
          (type.equals("H") || type.equals("O")),
          (type.equals("C") || type.equals("O")));

        FoodPyramid tree = new FoodPyramid(apexPredator);

        System.out.println("Constructing food pyramid. . .");

        CLI:
        while(true){
            System.out.println(commands);
            String command = sc.nextLine().toUpperCase();

            switch(command) {
                case "PC": tree.pC(); break;
                case "AC": tree.aC(); break;
                case "RC": tree.rC(); break;
                case "P": tree.p(); break;
                case "C": System.out.println(tree.getTree().listFoodChain());
                  break;
                case "F": tree.getTree().printOrganismTree(); break;
                case "LP": System.out.println(tree.getTree().listAllPlants());
                  break;
                case "R": tree.getTree().cursorReset();
                System.out.println("Cursor successfully reset to root");break;
                case "M": tree.m(); break;
                case "Q": break CLI;
            }
        }
    }

    /**Helper method to add a plant child to the cursor of the tree.
     *
     */
    private void pC(){
        if(tree.getCursor().getIsPlant()){
            System.out.println(PLANT_ERROR);
            return;
        }
        if (tree.getCursor().isFull()){
            System.out.println(CAPACITY_ERROR);
            return;
        }
        if(!tree.getCursor().getIsHerbivore()){
            System.out.println(DIET_ERROR);
            return;
        }
        try{
            String name = sc.nextLine();
            tree.addPlantChild(name);
            System.out.println(name +
              " has successfully been added as prey for the "
              + getTree().getCursor().getName() + "!");
        }
        catch(Exception e){
            System.out.println("ERROR: This prey already exists for this predator");
        }
    }

    /**Helper method to add animal child to the cursor of the tree.
     *
     */
    private void aC(){
        if(tree.getCursor().getIsPlant()){
            System.out.println(PLANT_ERROR);
            return;
        }
        if (tree.getCursor().isFull()){
            System.out.println(CAPACITY_ERROR);
            return;
        }
        if(!tree.getCursor().getIsCarnivore()){
            System.out.println(DIET_ERROR);
            return;
        }
        try{
            String name = sc.nextLine();
            String type;
            while(true) {
                System.out.println(TYPE);
                type = sc.nextLine();
                if(type.equals("H") || type.equals("C") || type.equals("O"))
                    break;
                System.out.println("Invalid Type");
            }
            tree.addAnimalChild(name, (type.equals("H") || type.equals("O")),
              (type.equals("C") || type.equals("O")));
            System.out.println("A(n) " + name +
              " has successfully been added as prey for the "
              + getTree().getCursor().getName() + "!");
        }
        catch(Exception e){
            System.out.println("ERROR: This prey already exists for this predator");
        }
    }

    /**Helper method that removes the child of the cursor of the tree with inputted name.
     *
     */
    private void rC(){
        System.out.println("What is the name of the organism to be removed?:");
        String name = sc.nextLine();
        try{
            tree.removeChild(name);
            System.out.println("A(n) " + name +
              " has been successfully removed as prey for the "
              + getTree().getCursor().getName() + "!");
        }
        catch(Exception e){
            System.out.println(name + " is not a child of " +
              getTree().getCursor().getName());
        }
    }

    /**Helper method that prints out the prey of the cursor of the tree.
     *
     */
    private void p(){
        try{
            System.out.println(tree.listPrey());
        }
        catch(Exception e){
            System.out.println("Plants do not have prey.");
        }
    }

    /**Helper method that moves cursor to the specified name.
     *
     */
    private void m(){
        String name = sc.nextLine();
        try{
            tree.moveCursor(name);
            System.out.println("Cursor successfully moved to " + name + "!");
        }
        catch(Exception e){
            System.out.println("ERROR: This prey does not exist for this predator");
        }
    }

}
