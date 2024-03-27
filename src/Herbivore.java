import java.util.LinkedList;

public class Herbivore extends Entity {
    public static final int ENTITY_ID = 2;
    public static LinkedList<Herbivore> herbivoreList = new LinkedList<>();
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
            if(energy-- <= 0){
                die();
                return;
            }
            super.move(new int[] {Main.EMPTY_CELL_ID}, false);
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
        EntityController.preyAnnulation(x, y);
        super.die(false);
        herbivoreList.remove(this);
    }

    public static Herbivore getHerbivore(int x, int y){
        for(int i = 0; i < herbivoreList.size(); i++){
            if(herbivoreList.get(i).x == x && herbivoreList.get(i).y == y){
                EntityController.preyAnnulation(x, y);
                return herbivoreList.get(i);
            }
        }
        return null;
    }
}