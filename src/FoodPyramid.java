import java.util.Scanner;
public class FoodPyramid {

    private OrganismTree tree;
    private static Scanner sc;
    private static final String TYPE = "Is the organism an herbivore / a carnivore / an omnivore? (H / C / O):";

    public FoodPyramid(OrganismNode apexPredator) {
        tree = new OrganismTree(apexPredator);
    }

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

        while(true) {
            System.out.println(TYPE);
            type = sc.nextLine();
            if(type.equals("H") || type.equals("C") || type.equals("O"))
                break;
            System.out.println("Invalid Type");
        }

        OrganismNode apexPredator = new OrganismNode(apexName, false, (type.equals("H") || type.equals("O")), (type.equals("C") || type.equals("O")));
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
                case "C": System.out.println(tree.getTree().listFoodChain());break;
                case "F": tree.getTree().printOrganismTree(); break;
                case "LP": System.out.println(tree.getTree().listAllPlants()); break;
                case "R": tree.getTree().cursorReset();
                System.out.println("Cursor successfully reset to root");break;
                case "M": tree.m(); break;
                case "Q": break CLI;
            }
        }
    }

    public void pC(){
        if(tree.getCursor().getIsPlant()){
            System.out.println("ERROR: The cursor is at a plant node. Plants cannot be predators.");
            return;
        }
        if (tree.getCursor().isFull()){
            System.out.println("ERROR: There is no more room for more prey for this predator.");
            return;
        }
        if(!tree.getCursor().getIsHerbivore()){
            System.out.println("ERROR: This prey cannot be added as it does not match the diet of the predator.");
            return;
        }
        try{
            String name = sc.nextLine();
            tree.addPlantChild(name);
            System.out.println(name + " has successfully been added as prey for the " + getTree().getCursor().getName() + "!");
        }
        catch(Exception e){
            System.out.println("ERROR: This prey already exists for this predator");
        }
    }

    public void aC(){
        if(tree.getCursor().getIsPlant()){
            System.out.println("ERROR: The cursor is at a plant node. Plants cannot be predators.");
            return;
        }
        if (tree.getCursor().isFull()){
            System.out.println("ERROR: There is no more room for more prey for this predator.");
            return;
        }
        if(!tree.getCursor().getIsCarnivore()){
            System.out.println("ERROR: This prey cannot be added as it does not match the diet of the predator.");
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
            tree.addAnimalChild(name, (type.equals("H") || type.equals("O")), (type.equals("C") || type.equals("O")));
            System.out.println("A(n) " + name + " has successfully been added as prey for the " + getTree().getCursor().getName() + "!");
        }
        catch(Exception e){
            System.out.println("ERROR: This prey already exists for this predator");
        }
    }

    public void rC(){
        System.out.println("What is the name of the organism to be removed?:");
        String name = sc.nextLine();
        try{
            tree.removeChild(name);
            System.out.println("A(n) " + name + " has been successfully removed as prey for the " + getTree().getCursor().getName() + "!");
        }
        catch(Exception e){
            System.out.println(name + " is not a child of " + getTree().getCursor().getName());
        }
    }

    public void p(){
        try{
            System.out.println(tree.listPrey());
        }
        catch(Exception e){
            System.out.println("Plants do not have prey.");
        }
    }

    public void m(){
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
