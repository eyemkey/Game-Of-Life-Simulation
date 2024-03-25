import java.util.Random;

public class EntityController {
    private static final int[] entityPopulationPercentage= {
            27, //Empty Space 30
            40, //Grass 40
            18, //Herbivore 15
            11, //Predator
            2, //Monster
            2 //Wizard
    };

    public static final int[][] entityMatrix = new int[Main.CELL_COUNT][Main.CELL_COUNT];

    public static void setEntityMatrixIJ(int i, int j, int value){
        entityMatrix[i][j] = value;
        Main.VisualControllerInstance.rectangleMatrixSetFill(i, j, entityMatrix[i][j]);
    }
    public static int getEntityMatrixIJ(int i, int j){
        return entityMatrix[i][j];
    }

    private static int getRandomEntity(){
        Random random = new Random();
        int entityGuesser = random.nextInt(100) + 1;
        int curr = 0;
        for (int l = 0; l < entityPopulationPercentage.length; l++) {
            curr += entityPopulationPercentage[l];
            if(entityGuesser <= curr){
                return l;
            }
        }
        return -1;
    }

    public static void initializeEntityMatrix() {
        for (int i = 0; i < Main.CELL_COUNT; i++) {
            for (int j = 0; j < Main.CELL_COUNT; j++) {
                int randomEntity = getRandomEntity();
                createNewEntity(new int[] {i, j}, randomEntity);
            }
        }
    }

    public static void createNewEntity(int[] cords, int entityId) {
        switch (entityId){
            case 0:
                break;
            case 1:
                new Grass(cords[0], cords[1]);
                break;
            case 2:
                new Herbivore(cords[0], cords[1]);
                break;
            case 3:
                new Predator(cords[0], cords[1]);
                break;
            case 4:
                new Monster(cords[0], cords[1]);
                break;
            case 5:
                new Wizard(cords[0], cords[1]);
                break;
        }
    }
}
