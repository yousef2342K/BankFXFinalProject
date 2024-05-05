package bankfx;

/**
 *
 * @author JoEKhalid
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ABSTRACTION LEC 10 *
 */
public abstract class BaseTransaction {

    protected void writeToFile(String fileName, String description, String type, int tAmount, String tRemarks) throws IOException {
        String date = java.time.LocalDate.now().toString();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = formatter.format(now);

        Writer writer = new FileWriter("db/Bank Statement/" + fileName, true);
        writer.write(description + " " + type + " " + tAmount + " " + tRemarks + " " + date + " " + time + "\n");
        writer.close();
    }

    public abstract boolean rAccCheck(int accNo) throws IOException;

    public abstract boolean sAccBalCheck(int accNo, int tAmount) throws IOException;

    public abstract void transaction(int accNo, int rAccNo, int tAmount) throws IOException;

    public abstract void writeTransaction(int accNo, int rAccNo, int tAmount, String tRemarks) throws IOException;
}
