import java.util.ArrayList;

public class Wizard extends Entity{
    public static ArrayList<Wizard> wizardList = new ArrayList<>();
    public static final int ENTITY_ID = 5;
    private static final int MAX_CAPACITY = 5;
    private static final int MAX_TIMER = 10;
    private int timer;
    private int capacity;

    public Wizard(int x, int y){
        super(x, y, 7, ENTITY_ID, 0);
        this.capacity = getRandomInt(MAX_CAPACITY);
        setTimer();
        wizardList.add(this);
    }

    private void setTimer(){
        timer = getRandomInt(MAX_TIMER) + 1;
    }

    public void create(){
        timer--;
        if(timer <= 0){
            setTimer();
            ArrayList<int[]> emptyCells = getCells(x, y, directions, Main.EMPTY_CELL_ID);
            if(!emptyCells.isEmpty()){
                int[] randomEmptyCell = emptyCells.get(getRandomInt(emptyCells.size()));

                new Herbivore(randomEmptyCell[0], randomEmptyCell[1]);
                capacity--;
                if(capacity <= 0){
                    die();
                }
            }
        }
    }

    protected void die() {
        super.die();
        wizardList.remove(this);
    }

}
