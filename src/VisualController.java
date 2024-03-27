import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VisualController {
    private static final int SCENE_SIDE = 500;
    private static final int CELL_SIDE = 25;

    private static final int CELL_COUNT = SCENE_SIDE / CELL_SIDE;

    public int getCellCount(){
        return CELL_COUNT;
    }
    public int getSceneSide(){
        return SCENE_SIDE;
    }

    private static final Color[] COLOR_MAPPING = {
            Color.GRAY,    //0
            Color.GREEN,   //1
            Color.YELLOW,  //2
            Color.RED,     //3
            Color.BLUE,    //4
            Color.PURPLE   //5
    };

    private final Rectangle[][] rectangleMatrix = getRectangleMatrix();


    public void rectangleMatrixSetFill(int x, int y, int cellID){
        rectangleMatrix[x][y].setFill(COLOR_MAPPING[cellID]);
    }

    public void addRectanglesToGridPane(GridPane gridPane){
        for(int i = 0; i < rectangleMatrix.length; i++){
            for(int j = 0; j < rectangleMatrix[i].length; j++){
                gridPane.add(rectangleMatrix[i][j], i, j);
            }
        }
    }
    private Rectangle[][] getRectangleMatrix(){
        Rectangle[][] rectangles = new Rectangle[CELL_COUNT][CELL_COUNT];
        for(int i = 0; i < CELL_COUNT; i++){
            for(int j = 0; j < CELL_COUNT; j++){
                rectangles[i][j] = new Rectangle(CELL_SIDE, CELL_SIDE, COLOR_MAPPING[0]);
            }
        }
        return rectangles;
    }
}
