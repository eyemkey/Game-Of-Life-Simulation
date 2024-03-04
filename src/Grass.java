import java.util.ArrayList;
import java.util.Random;

public class Grass {
    public static ArrayList<Grass> grassList = new ArrayList<>();
    public static final int ENTITY_ID = 1;
    private static final ArrayList<int[]> directions = getDirectionsList();


    private final int x;
    private final int y;
    private int multiply;

    public Grass(int x, int y){
        this.x = x;
        this.y = y;
        Main.setEntityMatrixIJ(x, y, ENTITY_ID);
        multiply = 0;
        grassList.add(this);
    }


    public static void removeGrassIJ(int x, int y){
        for (int i = 0; i < grassList.size(); i++) {
            if(grassList.get(i).x == x && grassList.get(i).y == y){
                grassList.remove(i);
                return;
            }
        }
    }

    private static ArrayList<int[]> getDirectionsList(){
        ArrayList<int[]> list = new ArrayList<>();
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                list.add(new int[] {i, j});
            }
        }
        return list;
    }

    public void multiply(){
        final int MULTIPLY_CONST = 8;

        multiply++;

        if(multiply >= MULTIPLY_CONST){
            System.out.println("MULTIPLY");
            Random random = new Random();
            ArrayList<int[]> emptyCells = Main.getCells(x, y, directions, Main.EMPTY_CELL_ID);
            if(!emptyCells.isEmpty()){
                int[] emptyCord = emptyCells.get(random.nextInt(emptyCells.size()));

                new Grass(emptyCord[0], emptyCord[1]);

                multiply = 0;
            }
        }
    }
}
