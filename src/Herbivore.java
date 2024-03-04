import java.util.ArrayList;
import java.util.Random;

public class Herbivore {

    public static ArrayList<Herbivore> herbivoreList = new ArrayList<>();
    public static final int ENTITY_ID = 2;
    private static final int MULTIPLY_CONST = 10;

    private static final ArrayList<int[]> directions = getDirectionsList();
    private int x;
    private int y;
    private int multiply;
    private int energy;


    public Herbivore(int x, int y){
        this.x = x;
        this.y = y;
        this.multiply = 0;
        this.energy = 5;

        Main.setEntityMatrixIJ(x, y, ENTITY_ID);
        herbivoreList.add(this);
    }
    private static ArrayList<int[]> getDirectionsList(){
        ArrayList<int[]> directions = new ArrayList<>();
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                directions.add(new int[] {i, j});
            }
        }
        return directions;
    }

    public void eat(){

        ArrayList<int[]> grassCords = Main.getCells(x, y, directions, Grass.ENTITY_ID);
        if(!grassCords.isEmpty()){
            Random random = new Random();
            int[] randomGrassCord = grassCords.get(random.nextInt(grassCords.size()));

            Main.setEntityMatrixIJ(x, y, Main.EMPTY_CELL_ID);

            x = randomGrassCord[0]; y = randomGrassCord[1];
            Main.setEntityMatrixIJ(x, y, ENTITY_ID);

            Grass.removeGrassIJ(x, y);

            multiply++;
            energy++;
            if(multiply >= MULTIPLY_CONST){
                multiply();
            }

        }else{
            move();
        }
    }

    private void move() {
        energy--;
        if(energy <= 0){
            die();
            return;
        }

        ArrayList<int[]> emptyCells = Main.getCells(x, y, directions, 0);
        if(!emptyCells.isEmpty()){
            Random random = new Random();
            int[] randomEmptyCell = emptyCells.get(random.nextInt(emptyCells.size()));

            Main.setEntityMatrixIJ(x, y, 0);
            x = randomEmptyCell[0]; y = randomEmptyCell[1];
            Main.setEntityMatrixIJ(x, y, 2);
        }
    }

    public static void die(int x, int y){
        for (int i = 0; i < herbivoreList.size(); i++) {
            if(herbivoreList.get(i).x == x && herbivoreList.get(i).y == y){
                herbivoreList.remove(i);
                return;
            }
        }
    }
    private void die() {
        Main.setEntityMatrixIJ(x, y, Main.EMPTY_CELL_ID);
        herbivoreList.remove(this);
    }

    private void multiply() {
        multiply -= MULTIPLY_CONST;
        ArrayList<int[]> emptyCells = Main.getCells(x, y, directions, Main.EMPTY_CELL_ID);
        if(!emptyCells.isEmpty()){
            Random random = new Random();
            int[] randomEmptyCell = emptyCells.get(random.nextInt(emptyCells.size()));

            new Herbivore(randomEmptyCell[0], randomEmptyCell[1]);
        }
    }

}
