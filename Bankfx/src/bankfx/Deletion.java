package bankfx;

import bankfx.interfaces.Deletioninterface;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class Deletion implements Deletioninterface {

    private Stage primaryStage;
    private int accNo;

    public Deletion(int accNo, Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.accNo = accNo;
    }

    public Deletion(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void accCloseFun(int accNo, String fileName) {
        try {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Account Deletion");
            confirmationAlert.setHeaderText("Are you sure you want to delete your account?");
            confirmationAlert.setContentText("Choose your option:");

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");

            confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                delLine(accNo, fileName);
                delLine(accNo, "db/balanceDB.txt");
                delLine(accNo, "db/userDB.txt");
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Account Deletion");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Your account has been deleted successfully!");
                successAlert.showAndWait();
                MainFX main = new MainFX(primaryStage);
                main.start(primaryStage);
            } else {
                MenuFX menu = new MenuFX(accNo, primaryStage);
                menu.showMenu(accNo, primaryStage);
            }
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    @Override
    public void delLine(int accNo, String fileName) throws IOException {
        try {
            File file = new File(fileName);
            StringBuilder newInfo = new StringBuilder();
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] subLine = line.split(" ");
                    int countLine = subLine.length;
                    if (accNo != Integer.parseInt(subLine[0])) {
                        String newLine = "";
                        for (int x = 0; x < countLine; x++) {
                            newLine += subLine[x] + " ";
                        }
                        newInfo.append(newLine.trim()).append("\n");
                    }
                }
            }

            String newInfoStr = newInfo.toString().trim();

            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write(newInfoStr);
            }
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    private void handleIOException(IOException e) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("IOException");
        errorAlert.setHeaderText("An error occurred");
        errorAlert.setContentText("IOException occurred: " + e.getMessage());
        errorAlert.showAndWait();
    }
}
