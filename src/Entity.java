import java.util.ArrayList;
import java.util.Random;
public abstract class Entity {
    protected int x;
    protected int y;
    protected int range;
    protected int multiply;
    private final int entityId;
    protected int energy;
    protected ArrayList<int[]> directions;

    /**
     * @param x coordinate of the entity
     * @param y coordinate of the entity
     * @param range The value of how far can move
     * @param entityId The unique ID for each class
     * @param energy The energy the object has
     */
    public Entity(int x, int y, int range, int entityId, int energy){
        this.x = x;
        this.y = y;
        this.range = range;
        this.entityId = entityId;
        this.energy = energy;
        EntityController.setEntityMatrixIJ(x, y, entityId);
        multiply = 0;
        directions = getDirectionsList();
    }

    protected void setDirections(ArrayList<int[]> newDirections){
        directions = newDirections;
    }
    public int getEntityId(){
        return entityId;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    /**
     * @return A list of change coordinates based on the range
     */
    protected ArrayList<int[]> getDirectionsList(){
        return getDirectionList(1);
    }

    protected ArrayList<int[]> getDirectionList(int directionRange){
        ArrayList<int[]> list = new ArrayList<>();
        for(int i = -directionRange; i <= directionRange; i++){
            for(int j = -directionRange; j <= directionRange; j++){
                list.add(new int[] {i, j});
            }
        }
        return list;
    }

    protected int getRandomInt(int range) {
        Random random = new Random();
        return random.nextInt(range);
    }

    /**
     * Finds the specified entities nearby
     * @param x coordinate to look around
     * @param y coordinate to look around
     * @param directions to look at
     * @param targetCellID ID of the entity to look for
     * @return ArrayList of coordinates nearby with targetCellID
     */
    protected static ArrayList<int[]> getCells(int x, int y, ArrayList<int[]> directions, int[] targetCellID){
        ArrayList<int[]> cellCoords = new ArrayList<>();

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            int length = EntityController.entityMatrix.length;
            if (newX >= 0 && newX < length && newY >= 0 && newY < length && Utility.contains(targetCellID, EntityController.entityMatrix[newX][newY])) {
                cellCoords.add(new int[]{newX, newY});
            }
        }

        return cellCoords;
    }
    protected  static ArrayList<int[]> getCells(int x, int y, ArrayList<int[]> directions, int targetCell){
        return getCells(x, y, directions, new int[] {targetCell});
    }

    /**
     * Multiplies the entity
     * @param MULTIPLY_CONST the set value after which the entity multiplies
     */
    protected void multiply(int MULTIPLY_CONST){
        multiply -= MULTIPLY_CONST;
        ArrayList<int[]> emptyCells = getCells(x, y, directions, Main.EMPTY_CELL_ID);
        if(!emptyCells.isEmpty()){
            int[] randomEmptyCellCords = emptyCells.get(getRandomInt(emptyCells.size()));

            EntityController.createNewEntity(randomEmptyCellCords, entityId);
        }
    }

    /**
     * Randomly moves the entity
     * @return true if new Coordinate is on grass otherwise false
     */
    protected boolean move(int[] targetCells, boolean isOnGrass){
//        if(energy-- <= 0){
//            die(isOnGrass);
//            return false;
//        }

        ArrayList<int[]> emptyCells = getCells(x, y, directions, targetCells);
        if(!emptyCells.isEmpty()){
            int[] randomEmptyCell = emptyCells.get(getRandomInt(emptyCells.size()));

            EntityController.setEntityMatrixIJ(x, y, isOnGrass ? Grass.ENTITY_ID : Main.EMPTY_CELL_ID);
            x = randomEmptyCell[0]; y = randomEmptyCell[1];
            boolean isGrass = EntityController.getEntityMatrixIJ(x, y) == Grass.ENTITY_ID;
            EntityController.setEntityMatrixIJ(x, y, entityId);
            return isGrass;
        }
        return isOnGrass;
    }

    protected boolean moveInDirection(int[] direction, boolean isOnGrass, int[] targetCells){
        int newX = x + direction[0];
        int newY = y + direction[1];
        int length = EntityController.entityMatrix.length;
        if(newX >= 0 && newX < length && newY >= 0 && newY < length && Utility.contains(targetCells, EntityController.entityMatrix[newX][newY])){
            EntityController.setEntityMatrixIJ(x, y, isOnGrass ? Grass.ENTITY_ID : Main.EMPTY_CELL_ID);
            x = newX; y = newY;
            boolean isGrass = EntityController.getEntityMatrixIJ(x, y) == Grass.ENTITY_ID;
            EntityController.setEntityMatrixIJ(x, y, entityId);
            return isGrass;
        }
        return isOnGrass;
    }

    /**
     * Makes the entity eat the food around
     * @param preyID ID of what the entity tries to feed on
     * @return true if fed false otherwise
     */
    protected boolean eat(int preyID){
        ArrayList<int[]> preyCords = getCells(x, y, directions, new int[] {preyID});
        if(!preyCords.isEmpty()){
            int[] randomPreyCord = preyCords.get(getRandomInt(preyCords.size()));

            if(entityId == Predator.ENTITY_ID){
                EntityController.preyAnnulation(randomPreyCord[0], randomPreyCord[1]);
            }

            EntityController.setEntityMatrixIJ(x, y, Main.EMPTY_CELL_ID);
            x = randomPreyCord[0]; y = randomPreyCord[1];
            EntityController.setEntityMatrixIJ(x, y, entityId);

            multiply+=preyID;
            energy+=preyID;
            return true;
        }else{
            return false;
        }
    }



    protected void die(boolean isOnGrass){
        EntityController.setEntityMatrixIJ(x, y, isOnGrass ? 1 : 0);
    }



}
