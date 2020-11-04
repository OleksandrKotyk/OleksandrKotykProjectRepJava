package main.java;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;

import static main.java.Main.readFile;

public class Email {
    String toEmail, toName;
    String text;
    String subject;
    String[] files;

    public Email(String toEmail, String toName, String text, String subject, String[] files) {
        this.toEmail = toEmail;
        this.toName = toName;
        this.text = text;
        this.subject = subject;
        this.files = files;
    }

    public String makeMIME(String from) throws IOException {
        StringBuilder ss = new StringBuilder();
        ss.append(MessageFormat.format(readFile("src/main/java/textFiles/message.txt"), subject, from, toName, toEmail, text));
        for (String fileName : files) {
            File file = new File(fileName);
            Tika tika = new Tika();
            String mimeType = tika.detect(file);
            String base64File = Base64.encode(Files.readAllBytes(file.toPath()));
            ss.append("\r\n");
            ss.append(MessageFormat.format(readFile("src/main/java/textFiles/fileText.txt"), mimeType, file.getName(), file.getName()));
            ss.append("\r\n");
            ss.append(base64File);
        }
        ss.append("\r\n--XXXXboundary text--");
        return ss.toString();
    }


}
