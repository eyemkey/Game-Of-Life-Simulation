import java.util.LinkedList;

public class Predator extends Entity {
    public static final int ENTITY_ID = 3;
    public static LinkedList<Predator> predatorList = new LinkedList<>();
    private static final int MULTIPLY_CONST = 10;

    public Predator(int x, int y){
        super(x, y, 1, ENTITY_ID, 5);
        predatorList.add(this);
    }

    public void eat(){
        if(super.eat(Herbivore.ENTITY_ID)){
            Herbivore.die(x, y);
            if(multiply >= MULTIPLY_CONST){
                super.multiply(MULTIPLY_CONST);
            }
        }else{
            super.move();
        }
    }

    protected void die() {
        super.die();
        predatorList.remove(this);
    }

    /**
     * Makes the predator at (x, y) die
     * @param x coordinate of the Predator to die
     * @param y coordinate of the Predator to die
     */
    public static void die(int x, int y){
        for (int i = 0; i < predatorList.size(); i++) {
            if(predatorList.get(i).x == x && predatorList.get(i).y == y){
                predatorList.remove(i);
                return;
            }
        }
    }
}