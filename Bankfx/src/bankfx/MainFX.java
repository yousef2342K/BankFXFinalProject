package bankfx;

/**
 *
 * @author JoEKhalid
 */
import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MainFX extends Application {

    private Stage primaryStage;

    public MainFX(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public MainFX() {
    }

    @Override
    public void start(Stage primaryStage) {
        Label l1 = new Label("JOE Bank Ltd.");
        l1.getStyleClass().add("lab1");
        Button b1 = new Button("About");
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About JOE Bank Ltd.");
        aboutAlert.setHeaderText(null);
        aboutAlert.setContentText("Welcome to JOE Bank Ltd., where your financial journey begins with trust and innovation. At JOE Bank, we are committed to providing exceptional banking services tailored to meet your needs. With a focus on integrity, reliability, and cutting-edge technology, we strive to empower our customers to achieve their financial goals with confidence.");
        aboutAlert.showAndWait();
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                aboutAlert.show();
            }
        });
        GridPane g1 = new GridPane();

        g1.add(l1, 0, 0);
        g1.setVgap(10);
        g1.setHgap(10);
        g1.setAlignment(Pos.CENTER);
        g1.setPadding(new Insets(20, 50, 20, 50));

        primaryStage.setTitle("JOE Bank Ltd.");

        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Create Account");

        loginButton.setStyle("-fx-font-size: 14pt;");
        createAccountButton.setStyle("-fx-font-size: 14pt;");

        loginButton.setOnAction(e -> {
            LoginFX loginFX = new LoginFX(primaryStage);
            primaryStage.setScene(loginFX.createLoginScene());
        });

        createAccountButton.setOnAction(e -> {
            CreationFX creationFX = new CreationFX(primaryStage);
            primaryStage.setScene(creationFX.createCreationScene());
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        layout.getChildren().addAll(g1, loginButton, createAccountButton, b1);

        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add((new File("C:\\Users\\LeaderTech\\Documents\\NetBeansProjects\\Bankfx\\src\\bankfx\\styles.css")).toURI().toString());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
