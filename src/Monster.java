import java.util.LinkedList;

public class Monster extends Entity{
    public static final int ENTITY_ID = 4;
    public static LinkedList<Monster> monsterList = new LinkedList<>();
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
            if(energy-- <= 0){
                die();
                return;
            }
            super.move(new int[] {Main.EMPTY_CELL_ID}, false);
        }
    }

    protected void die() {
        super.die(false);
        monsterList.remove(this);
    }
}
