import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;


public class Main extends Application {
    public static final int EMPTY_CELL_ID = 0;
    private static final int SCENE_WIDTH = 500; //800
    private static final int SCENE_HEIGHT = 500; //800
    private static final int CELL_SIDE = 25;

    private static final int CELL_COUNT = SCENE_WIDTH / CELL_SIDE;
    private static final Color[] COLOR_MAPPING = {
            Color.GRAY,    //0
            Color.GREEN,   //1
            Color.YELLOW,  //2
            Color.RED,     //3
            Color.BLUE,    //4
            Color.PURPLE   //5
    };
    private static final int[] entityPopulationPercentage= {
            30, //Empty Space 30
            40, //Grass 40
            15, //Herbivore 15
            9, //Predator
            4, //Monster
            2 //Wizard
    };
//    private static Rectangle[][] rectangleMatrix = new Rectangle[CELL_COUNT][CELL_COUNT];
    private static final Rectangle[][] rectangleMatrix = getRectangleMatrix();
    private static final int[][] entityMatrix = new int[CELL_COUNT][CELL_COUNT];

    public static void setEntityMatrixIJ(int i, int j, int value){
        entityMatrix[i][j] = value;
        rectangleMatrix[i][j].setFill(COLOR_MAPPING[entityMatrix[i][j]]);
    }
    public static int getEntityMatrixIJ(int i, int j){
        return entityMatrix[i][j];
    }

    private static void addRectanglesToGridPane(GridPane gridPane){
        for(int i = 0; i < rectangleMatrix.length; i++){
            for(int j = 0; j < rectangleMatrix[i].length; j++){
                gridPane.add(rectangleMatrix[i][j], i, j);
            }
        }
    }
    private static Rectangle[][] getRectangleMatrix(){
        Rectangle[][] rectangles = new Rectangle[CELL_COUNT][CELL_COUNT];
        for(int i = 0; i < CELL_COUNT; i++){
            for(int j = 0; j < CELL_COUNT; j++){
                rectangles[i][j] = new Rectangle(CELL_SIDE, CELL_SIDE, COLOR_MAPPING[0]);
            }
        }
        return rectangles;
    }

    private void initializeEntityMatrix() {
        for (int i = 0; i < CELL_COUNT; i++) {
            for (int j = 0; j < CELL_COUNT; j++) {
                int randomEntity = getRandomEntity();
                switch (randomEntity){
                    case 0:
                        break;
                    case 1:
                        new Grass(i, j);
                        break;
                    case 2:
                        new Herbivore(i, j);
                        break;
                    case 3:
                        new Predator(i, j);
                        break;
                    case 4:
                        new Monster(i, j);
                        break;
                    case 5:
                        new Wizard(i, j);
                        break;
                }
            }
        }
    }

    private int getRandomEntity(){
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

    public static ArrayList<int[]> getCells(int x, int y, ArrayList<int[]> directions, int targetCell){
        ArrayList<int[]> cellCoords = new ArrayList<>();

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (newX >= 0 && newX < entityMatrix.length && newY >= 0 && newY < entityMatrix[0].length && entityMatrix[newX][newY] == targetCell) {
                cellCoords.add(new int[]{newX, newY});
            }
        }

        return cellCoords;
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Initialize GridPane and Scene
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, SCENE_WIDTH, SCENE_HEIGHT);

        //---------------------------------------

        initializeEntityMatrix();
        addRectanglesToGridPane(gridPane);

        //Start the Stage
        stage.setScene(scene);
        stage.setTitle("Rectangle");
        stage.show();

        new AnimationTimer() {
            int frameCounter = 0;

            @Override
            public void handle(long l) {

                if (++frameCounter % 20 != 0) return;

                System.out.println("Handle");
                frameCounter %= 20;

                for (int i = 0; i < Grass.grassList.size(); i++) {
                    Grass.grassList.get(i).multiply();
                }

                for(int i = 0; i < Herbivore.herbivoreList.size(); i++){
                    Herbivore.herbivoreList.get(i).eat();
                }

                for (int i = 0; i < Predator.predatorList.size(); i++) {
                    Predator.predatorList.get(i).eat();
                }

                for (int i = 0; i < Monster.monsterList.size(); i++) {
                    Monster.monsterList.get(i).eat();
                }

                for (int i = 0; i < Wizard.wizardList.size(); i++) {
                    Wizard.wizardList.get(i).create();
                }
            }
        }.start();
    }

    public static void main(String[] args) {

        launch(args);
    }
}