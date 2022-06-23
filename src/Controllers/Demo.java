package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** FUTURE ENHANCEMENT: A good idea for a future update would be to make the program able to save a file and also opena
 *  file and read the content of it. This would allow inventories that are kept in a Microsoft Excel document easily
 *  transfer their data to this program and possibly switch from Microsoft Excel to this program.
 *
 * JavaDoc files are in the JavaDoc folder
 *
 * Starts the program & Loads the Main.fxml
 */
    public class Demo extends Application {
        @Override
        public void start(Stage mainStage) throws Exception {
            Parent parent = FXMLLoader.load(getClass().getResource("/Fxml/Main.fxml"));
            Scene scene = new Scene(parent);
            mainStage.setScene(scene);
            mainStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

