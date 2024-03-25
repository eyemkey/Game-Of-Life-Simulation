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

    protected int getMultiply(){
        return multiply;
    }
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

    protected static ArrayList<int[]> getCells(int x, int y, ArrayList<int[]> directions, int targetCellID){
        ArrayList<int[]> cellCoords = new ArrayList<>();

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (newX >= 0 && newX < EntityController.entityMatrix.length && newY >= 0 && newY < EntityController.entityMatrix[0].length && EntityController.entityMatrix[newX][newY] == targetCellID) {
                cellCoords.add(new int[]{newX, newY});
            }
        }

        return cellCoords;
    }

    protected void multiply(int MULTIPLY_CONST){
        multiply -= MULTIPLY_CONST;
        ArrayList<int[]> emptyCells = getCells(x, y, directions, Main.EMPTY_CELL_ID);
        if(!emptyCells.isEmpty()){
            int[] randomEmptyCellCords = emptyCells.get(getRandomInt(emptyCells.size()));

            EntityController.createNewEntity(randomEmptyCellCords, entityId);
        }
    }

    protected void move(){
        energy--;
        if(energy <= 0){
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
