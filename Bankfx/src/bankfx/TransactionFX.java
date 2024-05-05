package bankfx;

import bankfx.interfaces.AccountValidator;
import bankfx.interfaces.TransactionProcessor;
import bankfx.interfaces.TransactionRecorder;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.*;

import java.util.Optional;

import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.util.Scanner;

public class TransactionFX extends BaseTransaction implements AccountValidator, TransactionProcessor, TransactionRecorder {

    private Stage primaryStage;
    private int accNo;

    public TransactionFX(int accNo, Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.accNo = accNo;
    }

    public TransactionFX(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    void transactionFun(int accNo) throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Transaction");
        dialog.setHeaderText(null);
        dialog.setContentText("Receiver's Account Number: (or '0' to finish)");

        Optional<String> result = dialog.showAndWait();
        if (!result.isPresent() || result.get().equals("0")) {
            MenuFX menu = new MenuFX(accNo, primaryStage);
            menu.showMenu(accNo, primaryStage);
            return;
        }

        int rAccNo;
        try {
            rAccNo = Integer.parseInt(result.get());
        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid account number!");
            alert.showAndWait();
            transactionFun(accNo);
            return;
        }

        dialog.setContentText("Amount: ");
        result = dialog.showAndWait();
        if (!result.isPresent()) {
            return;
        }

        int tAmount;
        try {
            tAmount = Integer.parseInt(result.get());
        } catch (NumberFormatException e) {
            // Handle invalid input (non-integer)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid amount!");
            alert.showAndWait();
            transactionFun(accNo);
            return;
        }

        dialog.setContentText("Remarks: ");
        result = dialog.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        String tRemarks = result.get();

        allTransaction(accNo, rAccNo, tAmount, tRemarks);
    }

    void allTransaction(int accNo, int rAccNo, int tAmount, String tRemarks) throws IOException {
        if (rAccCheck(rAccNo)) {

            if (sAccBalCheck(accNo, tAmount)) {

                transaction(accNo, rAccNo, tAmount);
                writeTransaction(accNo, rAccNo, tAmount, tRemarks);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Transaction Successful");
                alert.setHeaderText(null);
                alert.setContentText("Transaction Successful!");
                alert.showAndWait();
                MenuFX menu = new MenuFX(accNo, primaryStage);
                menu.showMenu(accNo, primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Insufficient Balance");
                alert.setHeaderText(null);
                alert.setContentText("Insufficient Balance!");
                alert.showAndWait();
                transactionFun(accNo);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect Account Number");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Account Number!");
            alert.showAndWait();
            transactionFun(accNo);
        }
    }

    @Override
    public boolean rAccCheck(int rAccNo) throws FileNotFoundException {
        File file = new File("db/balanceDB.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] subLine = line.split(" ");
            int a = Integer.parseInt(subLine[0]);
            if (rAccNo == a) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean sAccBalCheck(int accNo, int tAmount) throws FileNotFoundException {
        File file = new File("db/balanceDB.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] subLine = line.split(" ");
            int a = Integer.parseInt(subLine[0]);
            int b = Integer.parseInt(subLine[1]);
            if (accNo == a) {
                if (tAmount <= b) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void transaction(int accNo, int rAccNo, int tAmount) throws IOException {
        File file = new File("db/balanceDB.txt");
        Scanner scanner = new Scanner(file);
        String newInfo = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] subLine = line.split(" ");
            int a = Integer.parseInt(subLine[0]);
            int b = Integer.parseInt(subLine[1]);
            if (accNo == a) {
                b = b - tAmount;
            } else if (rAccNo == a) {
                b = b + tAmount;
            }
            String newLine = a + " " + b;
            newInfo += newLine + "\n";
        }
        Writer writer = new FileWriter("db/balanceDB.txt");
        writer.write(newInfo);
        writer.close();
    }

    @Override
    public void writeTransaction(int accNo, int rAccNo, int tAmount, String tRemarks) throws IOException {
        debitWrite(accNo, rAccNo, tAmount, tRemarks);
        creditWrite(accNo, rAccNo, tAmount, tRemarks);
    }

    void debitWrite(int accNo, int rAccNo, int tAmount, String tRemarks) throws IOException {
        String description = ("Transfer to " + rAccNo);
        String type = "Debit";
        String date = java.time.LocalDate.now().toString();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = formatter.format(now);
        Writer writer = new FileWriter("db/Bank Statement/acc_" + accNo + ".txt", true);
        writer.write(description + " " + type + " " + tAmount + " " + tRemarks + " " + date + " " + time + "\n");
        writer.close();
    }

    void creditWrite(int accNo, int rAccNo, int tAmount, String tRemarks) throws IOException {
        String description = ("Transfer from " + accNo);
        String type = "Credit";
        String date = java.time.LocalDate.now().toString();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = formatter.format(now);
        Writer writer = new FileWriter("db/Bank Statement/acc_" + rAccNo + ".txt", true);
        writer.write(description + " " + type + " " + tAmount + " " + tRemarks + " " + date + " " + time + "\n");
        writer.close();
    }

}
