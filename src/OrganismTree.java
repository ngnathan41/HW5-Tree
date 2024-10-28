public class OrganismTree {
    private OrganismNode root, cursor;

    public OrganismTree(OrganismNode apexPredator){
        root = cursor = apexPredator;
    }

    public void cursorReset(){
        cursor = root;
    }

    public OrganismNode getCursor(){
        return cursor;
    }
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
    public String listPrey() throws IsPlantException{
        if(cursor.getIsPlant())
            throw new IsPlantException();
        String result = cursor.getName() + " -> ";
        if(cursor.getLeft() != null){
            result += " " + cursor.getLeft().getName();
            if(cursor.getMiddle() != null){
                result += ", " + cursor.getMiddle().getName();
                if(cursor.getRight() != null)
                    result += ", " + cursor.getRight().getName();
            }
        }

        return result;
    }

    public String listFoodChain(){
        StringBuilder sb = new StringBuilder();
        listFoodChainHelper(root, sb);
        return sb.toString();
    }

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

    public void printOrganismTree(){
        StringBuilder sb = new StringBuilder();
        printOrganismTreeHelper(cursor, sb, 0);
        System.out.print(sb.toString());
    }

    private void printOrganismTreeHelper(OrganismNode cursor2, StringBuilder sb, int depth){
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

    public String listAllPlants(){
        String res = listAllPlantsHelper(cursor);
        if(res.endsWith(", "))
            res = res.substring(0, res.length()-2);
        return res;
    }

    private String listAllPlantsHelper(OrganismNode cursor2){
        if (cursor2 == null)
            return "";
        if(cursor2.getIsPlant())
            return cursor2.getName() + ", ";
        return listAllPlantsHelper(cursor2.getLeft()) + listAllPlantsHelper(cursor2.getMiddle()) + listAllPlantsHelper(cursor2.getRight());
    }

    private void addChild(String name, boolean isHerbivore, boolean isCarnivore, boolean isPlant) throws IllegalArgumentException, PositionNotAvailableException{
        if(cursor.isFull())
            throw new PositionNotAvailableException();
        if((cursor.getLeft() != null && cursor.getLeft().getName().equals(name)) || (cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name)) || (cursor.getRight()!=null && cursor.getRight().getName().equals(name)))
            throw new IllegalArgumentException();

        if(cursor.getLeft() == null)
            cursor.setLeft(new OrganismNode(name, isPlant, isHerbivore, isCarnivore));
        else if(cursor.getMiddle() == null)
            cursor.setMiddle(new OrganismNode(name, isPlant, isHerbivore, isCarnivore));
        else
            cursor.setRight(new OrganismNode(name, isPlant, isHerbivore, isCarnivore));
    }

    public void addPlantChild(String name) throws IllegalArgumentException, PositionNotAvailableException{
        addChild(name, false, false,true);
    }

    public void addAnimalChild(String name, boolean isHerbivore, boolean isCarnivore) throws IllegalArgumentException, PositionNotAvailableException{
        addChild(name, isHerbivore, isCarnivore,false);
    }

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
