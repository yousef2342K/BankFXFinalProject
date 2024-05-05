package bankfx;

/**
 *
 * @author JoEKhalid
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class interfaces {

    public interface AccountValidator {

        boolean rAccCheck(int accNo) throws FileNotFoundException;

        boolean sAccBalCheck(int accNo, int tAmount) throws FileNotFoundException;
    }

    public interface TransactionRecorder {

        void writeTransaction(int accNo, int rAccNo, int tAmount, String tRemarks) throws IOException;
    }

    public interface TransactionProcessor {

        void transaction(int accNo, int rAccNo, int tAmount) throws IOException;
    }

    public interface AccountDetailsInterface {

        void accountDetailsFun(int accNo) throws IOException;
    }

    public interface LoginInterface {

        void loginFun() throws IOException;

        void loginAuth(int accNo, String pass) throws IOException;
    }

    public interface Creationinterface {

        void createAccFun() throws IOException;

        String[] getUserInfoFromUser() throws IOException;

        int accNoCreation() throws IOException;

        void credWrite(int accNo, String[] accLineInfo) throws IOException;

        void balWrite(int accNo) throws IOException;

        void userWrite(int accNo, String[] accLineInfo) throws IOException;
    }

    public interface Deletioninterface {

        void accCloseFun(int accNo, String fileName) throws IOException;

        void delLine(int accNo, String fileName) throws IOException;
    }

    public interface BalanceInquiryinterface {

        void balanceInquiryFun(int accNo) throws IOException;
    }

    public interface BankStatementinterface {

        void bankStatementFun(int accNo) throws IOException;
    }
}
