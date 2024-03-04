import java.util.ArrayList;
import java.util.Random;

public class Wizard {
    public static ArrayList<Wizard> wizardList = new ArrayList<>();
    public static final int ENTITY_ID = 5;


    private static final ArrayList<int[]> directions = getDirectionsList();
    private static final int MAX_CAPACITY = 5;
    private static final int MAX_TIMER = 10;
    private int x;
    private int y;
    private int timer;
    private int capacity;

    public Wizard(int x, int y){
        this.x = x;
        this.y = y;
        this.capacity = 5;

        setTimer();

        Main.setEntityMatrixIJ(x, y, ENTITY_ID);
        wizardList.add(this);
    }

    private void setTimer(){
        Random random = new Random();
        timer = random.nextInt(MAX_TIMER);
    }

    private static ArrayList<int[]> getDirectionsList(){
        ArrayList<int[]> directions = new ArrayList<>();

        for(int i = -7; i <= 7; i++){
            for(int j = -7; j <= 7; j++){
                directions.add(new int[] {i, j});
            }
        }
        return directions;
    }

    public void create(){
        timer--;
        if(timer <= 0){
            setTimer();
            ArrayList<int[]> emptyCells = Main.getCells(x, y, directions, Main.EMPTY_CELL_ID);
            if(!emptyCells.isEmpty()){
                Random random = new Random();
                int[] randomEmptyCell = emptyCells.get(random.nextInt(emptyCells.size()));

                new Herbivore(randomEmptyCell[0], randomEmptyCell[1]);
                capacity--;
                if(capacity == 0){
                    die();
                }
            }
        }
    }

    private void die() {
        Main.setEntityMatrixIJ(x, y, Main.EMPTY_CELL_ID);
        wizardList.remove(this);
    }


}
