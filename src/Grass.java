import java.util.LinkedList;

public class Grass extends Entity {
    public static final int ENTITY_ID = 1;
    private static final int MULTIPLY_CONST = 8;
    public static LinkedList<Grass> grassList = new LinkedList<>();

    public Grass(int x, int y){
        super(x, y, 1, ENTITY_ID, 0);
        grassList.add(this);
    }

    /**
     * Removes the grass with specified coordinate
     * @param x coordinate of the grass to be removed
     * @param y coordinate of the grass to be removed
     */
    public static void removeGrassIJ(int x, int y){
        for (int i = 0; i < grassList.size(); i++) {
            if(grassList.get(i).x == x && grassList.get(i).y == y){
                grassList.remove(i);
                return;
            }
        }
    }

    public void multiply(){
        multiply++;
        if(multiply >= MULTIPLY_CONST){
            super.multiply(MULTIPLY_CONST);
        }
    }
}
