public class OrganismNode {
    private String name;
    private boolean isPlant, isHerbivore, isCarnivore;
    private OrganismNode left, middle, right;

    public OrganismNode() {

    }

    public OrganismNode(String name, boolean isPlant, boolean isHerbivore, boolean isCarnivore){
        this.name = name;
        this.isPlant = isPlant;
        this.isHerbivore = isHerbivore;
        this.isCarnivore = isCarnivore;
    }

    public String getName() {
        return name;
    }

    public boolean getIsPlant() {
        return isPlant;
    }

    public boolean getIsHerbivore() {
        return isHerbivore;
    }

    public boolean getIsCarnivore() {
        return isCarnivore;
    }

    public OrganismNode getLeft() {
        return left;
    }

    public OrganismNode getMiddle() {
        return middle;
    }

    public OrganismNode getRight() {
        return right;
    }

    public boolean isFull(){
        return left!=null && middle!=null && right != null;
    }

    public void setLeft(OrganismNode left) {
        this.left = left;
    }

    public void setMiddle(OrganismNode middle) {
        this.middle = middle;
    }

    public void setRight(OrganismNode right) {
        this.right = right;
    }

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
