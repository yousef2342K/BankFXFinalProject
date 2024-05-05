package bankfx;

import bankfx.interfaces.BalanceInquiryinterface;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class BalanceInquiryFX implements BalanceInquiryinterface {

    private Stage primaryStage;
    private int accNo;

    public BalanceInquiryFX(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public BalanceInquiryFX(int accNo, Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.accNo = accNo;
    }

    @Override
    public void balanceInquiryFun(int accNo) {
        try {
            File file = new File("db/balanceDB.txt");
            Scanner scanner = new Scanner(file);
            int accBalance = -1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] subLine = line.split(" ");
                if (accNo == Integer.parseInt(subLine[0])) {
                    accBalance = Integer.parseInt(subLine[1]);
                    break;
                }
            }
            if (accBalance == -1) {
                showErrorAlert("Error", "Balance Inquiry Failed", "Account not found.");
            } else {
                showBalanceScene(accBalance);
            }
        } catch (IOException | NumberFormatException e) {
            showErrorAlert("Error", "File Reading Error", "An error occurred while reading the balance database.");
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showBalanceScene(int balance) {
        Label balanceLabel = new Label("Your current balance is: $" + balance);
        Button backbtn = new Button("back");
        VBox root = new VBox(balanceLabel, backbtn);
        root.setAlignment(Pos.CENTER);
        backbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MenuFX menu = new MenuFX(accNo, primaryStage);
                menu.showMenu(accNo, primaryStage);
            }
        });

        Scene scene = new Scene(root, 300, 200);
        scene.getStylesheets().add((new File("C:\\Users\\LeaderTech\\Documents\\NetBeansProjects\\Bankfx\\src\\bankfx\\styles.css")).toURI().toString());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Balance Inquiry");
        primaryStage.show();
    }
}
