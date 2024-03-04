import java.util.ArrayList;
import java.util.Random;


public class Predator {
    private static final int MULTIPLY_CONST = 10;
    public static ArrayList<Predator> predatorList = new ArrayList<>();
    public static final int ENTITY_ID = 3;
    private final static ArrayList<int[]> directions = getDirectionsList();
    private int x;
    private int y;
    private int energy;
    private int multiply;

    public Predator(int x, int y){
        this.x = x;
        this.y = y;
        this.multiply = 0;
        this.energy = 5;

        Main.setEntityMatrixIJ(x, y, ENTITY_ID);
        predatorList.add(this);
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
        ArrayList<int[]> herbivoreCords = Main.getCells(x, y, directions, Herbivore.ENTITY_ID);
        if(!herbivoreCords.isEmpty()){
            Random random = new Random();
            int[] randomHerbivoreCord = herbivoreCords.get(random.nextInt(herbivoreCords.size()));

            Main.setEntityMatrixIJ(x, y, Main.EMPTY_CELL_ID);
            x = randomHerbivoreCord[0]; y = randomHerbivoreCord[1];
            Main.setEntityMatrixIJ(x, y, ENTITY_ID);

            Herbivore.die(x, y);

            multiply++;
            energy++;
            if(multiply >= MULTIPLY_CONST){
                multiply();
            }
        }else{
            move();
        }
    }

    private void multiply() {
        multiply -= MULTIPLY_CONST;
        ArrayList<int[]> emptyCells = Main.getCells(x, y, directions, Main.EMPTY_CELL_ID);
        if(!emptyCells.isEmpty()){
            Random random = new Random();
            int[] randomEmptyCell = emptyCells.get(random.nextInt(emptyCells.size()));

            new Predator(randomEmptyCell[0], randomEmptyCell[0]);
        }
    }

    private void move() {
        energy--;
        if(energy <= 0){
            die();
            return;
        }

        ArrayList<int[]> emptyCells = Main.getCells(x, y, directions, Main.EMPTY_CELL_ID);
        if(!emptyCells.isEmpty()){
            Random random = new Random();
            int[] randomEmptyCell = emptyCells.get(random.nextInt(emptyCells.size()));

            Main.setEntityMatrixIJ(x, y, Main.EMPTY_CELL_ID);
            x = randomEmptyCell[0]; y = randomEmptyCell[1];
            Main.setEntityMatrixIJ(x, y, ENTITY_ID);
        }
    }

    private void die() {
        Main.setEntityMatrixIJ(x, y, Main.EMPTY_CELL_ID);
        predatorList.remove(this);
    }
    public static void die(int x, int y){
        for (int i = 0; i < predatorList.size(); i++) {
            if(predatorList.get(i).x == x && predatorList.get(i).y == y){
                predatorList.remove(i);
                return;
            }
        }
    }
}
