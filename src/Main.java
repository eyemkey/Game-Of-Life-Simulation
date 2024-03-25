import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
    public static final VisualController VisualControllerInstance = new VisualController();
    public static final int EMPTY_CELL_ID = 0;
    public static int CELL_COUNT = VisualControllerInstance.getCellCount();

    @Override
    public void start(Stage stage) throws Exception {
        //Initialize GridPane and Scene
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, VisualControllerInstance.getSceneSide(), VisualControllerInstance.getSceneSide());

        //---------------------------------------

        EntityController.initializeEntityMatrix();
        VisualControllerInstance.addRectanglesToGridPane(gridPane);

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