import java.util.ArrayList;

public class Monster extends Entity{
    public static final int ENTITY_ID = 4;
    public static ArrayList<Monster> monsterList = new ArrayList<>();
    private static final int MULTIPLY_CONST = 15;

    public Monster(int x, int y){
        super(x, y, 2, ENTITY_ID, 5);
        monsterList.add(this);
    }

    public void eat(){
        if(super.eat(Predator.ENTITY_ID)){
            Predator.die(x, y);
        }else if(super.eat(Herbivore.ENTITY_ID)){
            Herbivore.die(x, y);
        }

        if(multiply >= MULTIPLY_CONST){
            super.multiply(MULTIPLY_CONST);
        }else{
            move();
        }
    }

    protected void die() {
        super.die();
        monsterList.remove(this);
    }
}
