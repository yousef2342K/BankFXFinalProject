package bankfx;

/**
 *
 * @author JoEKhalid
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreationFX extends Application {

    private Stage primaryStage;

    public CreationFX(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("JOE Bank Ltd.");
        primaryStage.setScene(createCreationScene());
        primaryStage.show();
    }

    public Scene createCreationScene() {
        Label nameLabel = new Label("Name (First Last):");
        TextField nameField = new TextField();

        Label dobLabel = new Label("Date of Birth (YYYY-MM-DD):");
        TextField dobField = new TextField();

        Label genderLabel = new Label("Gender:");
        TextField genderField = new TextField();

        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();

        Label phoneLabel = new Label("Phone Number:");
        TextField phoneField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label citizenshipLabel = new Label("Citizenship Number:");
        TextField citizenshipField = new TextField();

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();

        Button createButton = new Button("Create Account");
        Button backButton = new Button("back");
        backButton.setOnAction(e -> {
            MainFX mainFX = new MainFX(primaryStage);
            mainFX.start(primaryStage);
        });
        createButton.setOnAction(e -> {
            if (validateFields(nameField, dobField, genderField, addressField, phoneField, emailField, citizenshipField, passwordField)) {
                if (phoneField.getText().length() != 11) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Incorrect Phone number!");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a valid phone number (it should contain 11 digits)!");
                    alert.showAndWait();
                } else {
                    String[] accLineInfo = {
                        nameField.getText(),
                        dobField.getText(),
                        genderField.getText(),
                        addressField.getText(),
                        phoneField.getText(),
                        emailField.getText(),
                        citizenshipField.getText(),
                        passwordField.getText()
                    };
                    try {
                        createAccFun(accLineInfo);
                        LoginFX loginFX = new LoginFX(primaryStage);
                        primaryStage.setScene(loginFX.createLoginScene());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        displayErrorAlert("Account Creation Failed", "An error occurred while creating the account.");
                    }
                }
            } else {
                displayErrorAlert("Field Validation Error", "Please fill in all fields.");
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, nameLabel, nameField);
        gridPane.addRow(1, dobLabel, dobField);
        gridPane.addRow(2, genderLabel, genderField);
        gridPane.addRow(3, addressLabel, addressField);
        gridPane.addRow(4, phoneLabel, phoneField);
        gridPane.addRow(5, emailLabel, emailField);
        gridPane.addRow(6, citizenshipLabel, citizenshipField);
        gridPane.addRow(7, passwordLabel, passwordField);
        gridPane.add(createButton, 0, 8, 2, 1);
        gridPane.add(backButton, 0, 9);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(gridPane);

        layout.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 17px;");

        return new Scene(layout, 400, 400);
    }

    public int accNoCreation() throws IOException {
        String lastLine = "";
        int accNo;
        File file = new File("db/credentials.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            lastLine = scanner.nextLine();
        }
        if (Objects.equals(lastLine, "")) {
            accNo = 1;
        } else {
            String[] subLine = lastLine.split(" ");
            accNo = Integer.parseInt(subLine[0]);
            accNo++;
        }
        return accNo;
    }

    public void credWrite(int accNo, String[] accLineInfo) throws IOException {
        FileWriter writer = new FileWriter("db/credentials.txt", true);
        writer.write("\n" + accNo + " " + accLineInfo[7]);
        writer.close();
    }

    public void balWrite(int accNo) throws IOException {
        int initialBal = 69;
        BufferedWriter writer = new BufferedWriter(new FileWriter("db/balanceDB.txt", true));
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println(accNo + " " + initialBal);
        printWriter.close();
    }

    public void userWrite(int accNo, String[] accLineInfo) throws IOException {
        FileWriter writer = new FileWriter("db/userDB.txt", true);
        writer.write("\n" + accNo + " ");
        for (int i = 0; i < 7; i++) {
            writer.write(accLineInfo[i] + " ");
        }
        writer.close();
    }

    public void createAccFun(String[] accLineInfo) throws IOException {
        int accNo = accNoCreation();
        credWrite(accNo, accLineInfo);
        balWrite(accNo);
        userWrite(accNo, accLineInfo);

        displaySuccessAlert("Account Creation Successful",
                "Your account has been created successfully!\n"
                + "Account Number: " + accNo + "\n"
                + "Password: " + accLineInfo[7]);
    }

    public void displayErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void displaySuccessAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateFields(TextField... fields) {
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
