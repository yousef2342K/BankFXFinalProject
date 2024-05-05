package bankfx;

/**
 *
 * @author JoEKhalid
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BankStatementFX implements interfaces.BankStatementinterface {

    private Stage primaryStage;
    private int accNo;

    public BankStatementFX(int accNo, Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.accNo = accNo;
    }

    public BankStatementFX(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void bankStatementFun(int accNo) throws IOException {
        File file = new File("db/Bank Statement/acc_" + accNo + ".txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(5);

            Label descriptionLabel = new Label("Description");
            Label typeLabel = new Label("Type");
            Label amountLabel = new Label("Amount");
            Label remarksLabel = new Label("Remarks");
            Label dateLabel = new Label("Date");
            Label timeLabel = new Label("Time");

            gridPane.addRow(0, descriptionLabel, typeLabel, amountLabel, remarksLabel, dateLabel, timeLabel);

            int row = 1;
            while (scanner.hasNextLine()) {
                String trWLine = scanner.nextLine();
                String[] trLine = trWLine.split(" ");
                String description = trLine[0] + " " + trLine[1] + " " + trLine[2];
                String type = trLine[3];
                String amount = "$" + trLine[4];
                String remarks = trLine[5];
                String date = trLine[6];
                String time = trLine[7];

                gridPane.addRow(row++, new Label(description), new Label(type), new Label(amount),
                        new Label(remarks), new Label(date), new Label(time));
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bank Statement");
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(gridPane);
            alert.showAndWait();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No Transaction found!");
            alert.showAndWait();
            exit(accNo);
        }
        exit(accNo);
    }

    void exit(int accNo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Press Enter key to continue...");
        alert.showAndWait();
        MenuFX menu = new MenuFX(accNo,primaryStage);
        menu.showMenu(accNo, primaryStage);
    }
}
