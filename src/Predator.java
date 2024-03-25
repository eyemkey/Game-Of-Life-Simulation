import java.util.ArrayList;

public class Predator extends Entity {
    public static final int ENTITY_ID = 3;
    public static ArrayList<Predator> predatorList = new ArrayList<>();
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
    public static void die(int x, int y){
        for (int i = 0; i < predatorList.size(); i++) {
            if(predatorList.get(i).x == x && predatorList.get(i).y == y){
                predatorList.remove(i);
                return;
            }
        }
    }
}