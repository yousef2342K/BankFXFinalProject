/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package bankfx;

import bankfx.interfaces.AccountDetailsInterface;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.stage.Stage;

/**
 *
 * @author JoEKhalid
 */
public class AccountDetailsFX implements AccountDetailsInterface {

    private Stage primaryStage;
    private int accNo;

    public AccountDetailsFX(int accNo, Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.accNo = accNo;
    }

    public AccountDetailsFX(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void accountDetailsFun(int accNo) throws IOException {
        File file = new File("db/userDB.txt");
        Scanner scanner = new Scanner(file);
        String wholeDetail = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] subLine = line.split(" ");
            if (accNo == Integer.parseInt(subLine[0])) {
                wholeDetail = line;
                break;
            }
        }
        String[] detail = wholeDetail.split(" ");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Account Details");
        alert.setHeaderText(null);
        if (detail.length >= 9) {

            alert.setContentText("""
                             Account Details:
                             
                             Full Name: """ + detail[1] + " " + detail[2] + "\n"
                    + "Account Number: " + detail[0] + "\n"
                    + "Gender: " + detail[4] + "\n"
                    + "Address: " + detail[5] + "\n"
                    + "Date of Birth: " + detail[3] + "\n"
                    + "Phone number: " + detail[6] + "\n"
                    + "Email: " + detail[7] + "\n"
                    + "Identification: " + detail[8]);

            alert.showAndWait();

        } else {

            alert.setContentText("Incomplete account details.");
        }

        scanner.close();
    }
}
