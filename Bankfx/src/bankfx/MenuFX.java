package bankfx;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

/**
 *
 * @author JoEKhalid
 */
public class MenuFX extends Application {

    private int accNo;
    private Stage primaryStage;

    public MenuFX(int accNo) {
        this.accNo = accNo;
    }

    public MenuFX(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public MenuFX(int accNo, Stage primaryStage) {
        this.accNo = accNo;
        this.primaryStage = primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene showMenu(int accNo, Stage primaryStage) {
        Label titleLabel = new Label("Menu:");
        Button balanceButton = new Button("Balance Inquiry");
        Button accountDetailsButton = new Button("Account Details");
        Button fundTransferButton = new Button("Fund Transfer");
        Button bankStatementButton = new Button("Bank Statement");
        Button accountClosureButton = new Button("Account Closure");
        Button logoutButton = new Button("Log out");
        Button exitButton = new Button("Exit");

        balanceButton.setOnAction(e -> {
            try {
                balInquiry();
            } catch (IOException ex) {
                Logger.getLogger(MenuFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        accountDetailsButton.setOnAction(e -> {
            try {
                accDetails();
            } catch (IOException ex) {
                Logger.getLogger(MenuFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        fundTransferButton.setOnAction(e -> {
            try {
                fundTransfer();
            } catch (IOException ex) {
                Logger.getLogger(MenuFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        bankStatementButton.setOnAction(e -> {
            try {
                tranHistory();
            } catch (IOException ex) {
                Logger.getLogger(MenuFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        accountClosureButton.setOnAction(e -> {
            try {
                accClose();
            } catch (IOException ex) {
                Logger.getLogger(MenuFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        logoutButton.setOnAction(e -> logout(primaryStage));
        exitButton.setOnAction(e -> exit());

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));

        StackPane menuContainer = new StackPane();
        menuContainer.getStyleClass().add("menu-container");

        VBox menuBox = new VBox(20);
        menuBox.getStyleClass().add("menu-box");
        menuBox.getChildren().addAll(
                titleLabel, balanceButton, accountDetailsButton, fundTransferButton,
                bankStatementButton, accountClosureButton, logoutButton, exitButton
        );

        menuContainer.getChildren().add(menuBox);

        layout.getChildren().add(menuContainer);

        Scene menuscene = new Scene(layout, 400, 500);
        menuscene.getStylesheets().add((new File("C:\\Users\\LeaderTech\\Documents\\NetBeansProjects\\Bankfx\\src\\bankfx\\styles.css")).toURI().toString());

        primaryStage.setScene(menuscene);
        primaryStage.show();
        return menuscene;

    }

    private void balInquiry() throws IOException {
        BalanceInquiryFX balanceinq = new BalanceInquiryFX(accNo, primaryStage);
        balanceinq.balanceInquiryFun(accNo);
    }

    private void accClose() throws IOException {
        Deletion accCloseFunc = new Deletion(accNo, primaryStage);
        accCloseFunc.accCloseFun(accNo, "db/credentials.txt");
    }

    private void accDetails() throws IOException {
        AccountDetailsFX accdetails = new AccountDetailsFX(accNo, primaryStage);
        accdetails.accountDetailsFun(accNo);
    }

    private void fundTransfer() throws IOException {
        TransactionFX transcation = new TransactionFX(accNo, primaryStage);
        transcation.transactionFun(accNo);
    }

    private void tranHistory() throws IOException {
        BankStatementFX bankstatement = new BankStatementFX(accNo, primaryStage);
        bankstatement.bankStatementFun(accNo);
    }

    private void logout(Stage primaryStage) {
        MainFX mainfx = new MainFX(primaryStage);
        mainfx.start(primaryStage);
    }

    private void exit() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
