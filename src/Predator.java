import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Predator extends Entity {
    public static final int ENTITY_ID = 3;
    public static LinkedList<Predator> predatorList = new LinkedList<>();
    private static final int MULTIPLY_CONST = 10;
    private static ArrayList<int[]> regularDirections;
    private static ArrayList<int[]> huntDirections;
    private static final int huntRange = 2;
    private static final int searchRange = 7;
    private boolean isOnGrass;

    private Entity prey;

    public Predator(int x, int y){
        super(x, y, 1, ENTITY_ID, 5);
        regularDirections = directions;
        huntDirections = super.getDirectionList(huntRange);
        isOnGrass = false;
        predatorList.add(this);
    }

    public void eat(int[] preyIDs){
        boolean flag = false;
        for(int id : preyIDs){
            if(super.eat(id)){
                if(id == Herbivore.ENTITY_ID){
                    Herbivore.die(x, y);
                }else if(id == Predator.ENTITY_ID){
                    Predator.die(x, y);
                }
                flag = true;
            }
        }
        if(flag) return;

        if(prey == null){
            findPrey(preyIDs);
        }
        move();
    }

    protected void move(){
        if(energy-- <= 0) {
            die();
            return;
        }
        if(prey == null) isOnGrass = super.move(new int[] {Grass.ENTITY_ID, Main.EMPTY_CELL_ID}, isOnGrass);
        else{
            int distanceX = prey.x - x;
            int distanceY = prey.y - y;

            int dx = 0, dy = 0;

            if(distanceX > huntRange){
                dx = huntRange;
            }else if(distanceX < -huntRange){
                dx = -huntRange;
            }

            if(distanceY > huntRange){
                dy = huntRange;
            }else if(distanceY < -huntRange){
                dy = -huntRange;
            }

            super.moveInDirection(new int[] {dx, dy}, isOnGrass, new int[] {Grass.ENTITY_ID, Main.EMPTY_CELL_ID});
        }
    }

    protected void die() {
        super.die(isOnGrass);
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

    public Entity getPrey(){
        return prey;
    }

    public void clearPrey(){
        prey = null;
    }
    public boolean hasPrey(){
        return prey != null;
    }
    protected boolean findPrey(int[] preyIDs){
        int[][] entityMatrix = Utility.copyMatrix(EntityController.entityMatrix);
        int length = EntityController.entityMatrix.length;
        int[] dx = {1, 1, 1, 0, -1, -1, -1, 0};
        int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};
        Queue<int[]> cordQ = new LinkedList<>();
        cordQ.add(new int[] {x, y});
        int mark = -1;
        entityMatrix[x][y] = mark--;

        while(!cordQ.isEmpty() && mark > -searchRange * searchRange){
            int[] currCord = cordQ.poll();
            for(int i = 0; i < dx.length; i++){
                int newX = currCord[0] + dx[i];
                int newY = currCord[1] + dy[i];

                if(newX >= 0 && newX < length && newY >= 0 && newY < length && entityMatrix[newX][newY] >= 0){
                    if(entityMatrix[newX][newY] == Herbivore.ENTITY_ID && Utility.contains(preyIDs, Herbivore.ENTITY_ID)){
                        prey = Herbivore.getHerbivore(newX, newY);
                        setDirections(huntDirections);
                        return true;
                    }else if(entityMatrix[newX][newY] == Predator.ENTITY_ID && Utility.contains(preyIDs, Predator.ENTITY_ID)){
                        prey = Predator.getPredator(newX, newY);
                        setDirections(huntDirections);
                        return true;
                    }
                    entityMatrix[newX][newY] = mark--;
//                    Utility.printMatrix(entityMatrix);
                    cordQ.add(new int[] {newX, newY});
                }

            }
        }
        setDirections(regularDirections);
        return false;
    }

    private static Predator getPredator(int x, int y){
        for(int i = 0; i < predatorList.size(); i++){
            if(predatorList.get(i).x == x && predatorList.get(i).y == y){
                return predatorList.get(i);
            }
        }
        return null;
    }
}