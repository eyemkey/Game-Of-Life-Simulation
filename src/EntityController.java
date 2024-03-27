import java.util.Random;

public class EntityController {
    private static final int[] entityPopulationPercentage= {
            27, //Empty Space
            40, //Grass
            18, //Herbivore
            11, //Predator
            2, //Monster
            2 //Wizard
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
        Main.VisualControllerInstance.rectangleMatrixSetFill(x, y, entityMatrix[x][y]);
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
}
