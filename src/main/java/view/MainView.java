package view;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView extends Application{

    private Stage stage;
    private AnchorPane anchorPane;


    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage=primaryStage;
        this.stage.setTitle("QuickResize");

        initRootLayout();

    }

    /**
     * 初始化布局
     */
    private void initRootLayout(){
        try {
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(MainView.class.getResource("/fxml/main.fxml"));
            anchorPane=fxmlLoader.load();

            Scene scene=new Scene(anchorPane);

            this.stage.setScene(scene);

            MainController controller=fxmlLoader.getController();

            this.stage.show();


            controller.setMainView(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getStage() {
        return stage;
    }
}
