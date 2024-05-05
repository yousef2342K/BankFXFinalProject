package bankfx;

/**
 *
 * @author JoEKhalid
 */
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFX {

    private Stage primaryStage;

    public LoginFX(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene createLoginScene() {
        Label accountLabel = new Label("Account Number:");
        TextField accountField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Label errorLabel = new Label();

        Button loginButton = new Button("Login");
        Button backButton = new Button("back");
        backButton.setOnAction(e -> {
            MainFX mainFX = new MainFX(primaryStage);
            mainFX.start(primaryStage);
        });
        loginButton.setOnAction(e -> {
            try {
                int accNo = Integer.parseInt(accountField.getText());
                String pass = passwordField.getText();
                loginAuth(accNo, pass, errorLabel);
            } catch (NumberFormatException ex) {
                errorLabel.setText("Please enter a valid account number.");
            } catch (IOException ex) {
                Logger.getLogger(LoginFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(accountLabel, accountField, passwordLabel, passwordField, loginButton, errorLabel, backButton);
        Scene loginscene = new Scene(layout, 400, 300);
        loginscene.getStylesheets().add((new File("C:\\Users\\LeaderTech\\Documents\\NetBeansProjects\\Bankfx\\src\\bankfx\\styles.css")).toURI().toString());
        return loginscene;
    }

    private void loginAuth(int accNo, String pass, Label errorLabel) throws IOException {
        File file = new File("db/credentials.txt");
        Scanner scanner = new Scanner(file);
        boolean loginBoo = false;
        boolean incPass = false;

        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] subLine = line.split(" ");
                if (accNo == Integer.parseInt(subLine[0]) && pass.equals(subLine[1])) {
                    loginBoo = true;
                    break;
                } else if (accNo == Integer.parseInt(subLine[0])) {
                    incPass = true;
                    break;
                }
            }
        } finally {
            scanner.close();
        }

        if (loginBoo) {
            errorLabel.setText("Login Successful!!");
            MenuFX menu = new MenuFX(accNo, primaryStage);
            menu.showMenu(accNo, primaryStage);
        } else if (incPass) {
            errorLabel.setText("Incorrect Password! Please enter again.");
        } else {
            errorLabel.setText("Account doesn't exist! Please enter again.");
        }
    }
}
