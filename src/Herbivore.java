import java.util.ArrayList;

public class Herbivore extends Entity {
    public static final int ENTITY_ID = 2;
    public static ArrayList<Herbivore> herbivoreList = new ArrayList<>();
    private static final int MULTIPLY_CONST = 10;

    public Herbivore(int x, int y){
        super(x, y, 1, ENTITY_ID, 5);
        herbivoreList.add(this);
    }

    public void eat(){
        if(super.eat(Grass.ENTITY_ID)){
            Grass.removeGrassIJ(x, y);

            if(multiply >= MULTIPLY_CONST){
                multiply(MULTIPLY_CONST);
            }
        }else{
            super.move();
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
    protected void die() {
        super.die();
        herbivoreList.remove(this);
    }
}