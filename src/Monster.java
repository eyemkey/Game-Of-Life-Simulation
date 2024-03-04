import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static com.sun.javafx.fxml.expression.Expression.multiply;
import static java.nio.file.Files.move;

public class Monster {
    public static final int ENTITY_ID = 4;

    public static ArrayList<Monster> monsterList = new ArrayList<>();
    private static final int MULTIPLY_CONST = 15;
    private final static ArrayList<int[]> directions = getDirectionsList();

    private int x;
    private int y;
    private int energy;
    private int multiply;

    public Monster(int x, int y){
        this.x = x;
        this.y = y;
        this.multiply = 0;
        this.energy = 3;

        Main.setEntityMatrixIJ(x, y, ENTITY_ID);
        monsterList.add(this);
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

    private ArrayList<int[]> mergeCords(ArrayList<int[]> list1, ArrayList<int[]> list2){
        ArrayList<int[]> merged = new ArrayList<>(list1);
        merged.addAll(list2);

        return merged;
    }
    public void eat(){
        ArrayList<int[]> preyCords = mergeCords(Main.getCells(x, y, directions, Predator.ENTITY_ID), Main.getCells(x, y, directions, Herbivore.ENTITY_ID));

        if(!preyCords.isEmpty()){
            Random random = new Random();
            int[] randomPreyCord = preyCords.get(random.nextInt(preyCords.size()));

            Main.setEntityMatrixIJ(x, y, Main.EMPTY_CELL_ID);
            x = randomPreyCord[0]; y = randomPreyCord[1];
            if(Main.getEntityMatrixIJ(x, y) == Herbivore.ENTITY_ID){
                Herbivore.die(x, y);
            }else if(Main.getEntityMatrixIJ(x, y) == Predator.ENTITY_ID){
                Predator.die(x, y);
            }

            Main.setEntityMatrixIJ(x, y, ENTITY_ID);

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
            int[] randomEmptyCord = emptyCells.get(random.nextInt(emptyCells.size()));
            new Monster(randomEmptyCord[0], randomEmptyCord[1]);
        }
    }

    private void move() {
        energy--;
        if(energy <= 0){
            die();
            return;
        }

        //Movement TODO
        ArrayList<int[]> emptyCells = Main.getCells(x, y, directions, Main.EMPTY_CELL_ID);
        if(!emptyCells.isEmpty()){
            Random random = new Random();
            int[] randomEmptyCord = emptyCells.get(random.nextInt(emptyCells.size()));

            Main.setEntityMatrixIJ(x, y, Main.EMPTY_CELL_ID);
            x = randomEmptyCord[0]; y = randomEmptyCord[1];
            Main.setEntityMatrixIJ(x, y, ENTITY_ID);

        }
    }

    private void die() {
        Main.setEntityMatrixIJ(x, y, Main.EMPTY_CELL_ID);
        monsterList.remove(this);
    }
}
