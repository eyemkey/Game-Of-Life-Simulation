import java.sql.SQLOutput;
import java.util.Random;

public class EntityController {
    private static final int[] entityPopulationPercentage= {
            29, //Empty Space
            45, //Grass
            20, //Herbivore
            4, //Predator
            1, //Monster
            1 //Wizard
    };

    public static final int[][] entityMatrix = new int[Main.CELL_COUNT][Main.CELL_COUNT];

    /**
     * Sets the x,y entry of the entityMatrix
     *
     * @param x a coordinate X to be changed
     * @param y a coordinate Y to be changed
     * @param value the value to be inserted
     */
    public static void setEntityMatrixIJ(int x, int y, int value){
        entityMatrix[x][y] = value;
        Main.VisualControllerInstance.rectangleMatrixSetFill(x, y, value);
    }
    public static int getEntityMatrixIJ(int i, int j){
        return entityMatrix[i][j];
    }

    /**
     * Gets a random entity using entityID
     *
     * @return the entityID of the random entity
     */
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

    /**
     * Initializes the Entity Matrix
     */
    public static void initializeEntityMatrix() {
//        test();
        for (int i = 0; i < Main.CELL_COUNT; i++) {
            for (int j = 0; j < Main.CELL_COUNT; j++) {
                int randomEntity = getRandomEntity();
                createNewEntity(new int[] {i, j}, randomEntity);
            }
        }
    }

    /**
     * Creates a specified entity at a specified point.
     *
     * @param cords the coordinate of the object to be created
     * @param entityId the type of entity to be created
     */
    public static void createNewEntity(int[] cords, int entityId) {
        switch (entityId){
            case 0:
                break;
            case Grass.ENTITY_ID:
                new Grass(cords[0], cords[1]);
                break;
            case Herbivore.ENTITY_ID:
                new Herbivore(cords[0], cords[1]);
                break;
            case Predator.ENTITY_ID:
                new Predator(cords[0], cords[1]);
                break;
            case Monster.ENTITY_ID:
                new Monster(cords[0], cords[1]);
                break;
            case Wizard.ENTITY_ID:
                new Wizard(cords[0], cords[1]);
                break;
        }
    }

    public static void preyAnnulation(int x, int y){
        for(Predator predator : Predator.predatorList){
            if(predator.hasPrey() && predator.getPrey().getX() == x && predator.getPrey().getY() == y){
                predator.clearPrey();
            }
        }
    }

    private static void test(){
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                if(i == 10 && j == 10){
                    createNewEntity(new int[] {i, j}, 3);
                }else if(i == 13 && j == 13) createNewEntity(new int[] {i, j}, 2);
                else createNewEntity(new int[] {i, j}, 1);
            }
        }
    }
}
