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

    /**
     * @return A list of change coordinates based on the range
     */
    protected ArrayList<int[]> getDirectionsList(){
        ArrayList<int[]> list = new ArrayList<>();
        for(int i = -range; i <= range; i++){
            for(int j = -range; j <= range; j++){
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
    protected static ArrayList<int[]> getCells(int x, int y, ArrayList<int[]> directions, int targetCellID){
        ArrayList<int[]> cellCoords = new ArrayList<>();

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            int length = EntityController.entityMatrix.length;
            if (newX >= 0 && newX < length && newY >= 0 && newY < length && EntityController.entityMatrix[newX][newY] == targetCellID) {
                cellCoords.add(new int[]{newX, newY});
            }
        }

        return cellCoords;
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
     */
    protected void move(){
        if(energy-- <= 0){
            die();
            return;
        }

        ArrayList<int[]> emptyCells = getCells(x, y, directions, Main.EMPTY_CELL_ID);
        if(!emptyCells.isEmpty()){
            int[] randomEmptyCell = emptyCells.get(getRandomInt(emptyCells.size()));

            EntityController.setEntityMatrixIJ(x, y, Main.EMPTY_CELL_ID);
            x = randomEmptyCell[0]; y = randomEmptyCell[1];
            EntityController.setEntityMatrixIJ(x, y, entityId);
        }
    }

    /**
     * Makes the entity eat the food around
     * @param preyID ID of what the entity tries to feed on
     * @return true if fed false otherwise
     */
    protected boolean eat(int preyID){
        ArrayList<int[]> preyCords = getCells(x, y, directions, preyID);
        if(!preyCords.isEmpty()){
            int[] randomPreyCord = preyCords.get(getRandomInt(preyCords.size()));

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

    protected void die(){
        EntityController.setEntityMatrixIJ(x, y, Main.EMPTY_CELL_ID);
    }

}
