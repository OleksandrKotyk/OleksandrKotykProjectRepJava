package main.java;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EmailSender {
    String login, name, host;
    int port;
    SMTP smtp;

    public EmailSender(String login, String name, String host, int port) throws IOException {
        this.login = login;
        this.smtp = new SMTP(host, port);
        this.host = host;
        this.port = port;
        this.name = name;
    }

    public boolean logIn(String password) throws IOException, InterruptedException {
        smtp.send("HELO smtp.gmail.com", 0);
        smtp.send("AUTH LOGIN", 500);
        smtp.send(Base64.getEncoder().encodeToString(login.getBytes(StandardCharsets.UTF_8)), 0);
        String logIn = smtp.send(Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8)), 0);
        return !logIn.startsWith("535");
    }

    public boolean logOut() {
        try {
            smtp.send("EXIT", 0);
            smtp.destroy();
            smtp = new SMTP(host, port);
        } catch (IOException | InterruptedException e) {
            return false;
        }
        return true;
    }

    public boolean sendEmail(Email email) throws IOException, InterruptedException {
        smtp.send(String.format("MAIL FROM: <%s>", login), 0);
        smtp.send(String.format("RCPT TO: <%s>", email.toEmail), 0);
        smtp.send("Data", 500);
        String isSend = smtp.send(email.makeMIME(String.format("%s <%s>", name, login)).concat("\r\n."), 100);
        return isSend.startsWith("250");
    }
}


//    EmailSender emailSender = new EmailSender("malkit2000ll@gmail.com", "Oleksandr Kotyk", "smtp.gmail.com", 465);
//        System.out.println(emailSender.logIn("S7sh97k105t"));
//                System.out.println(emailSender.logIn("S97sh97k105t"));
//                System.out.println(emailSender.logOut());
//                System.out.println(emailSender.logIn("S97sh97k105t"));
//                System.out.println(emailSender.sendEmail(new Email("malkit2000l@gmail.com", "Oleksandr Kotyk", "New message", "Subject", new String[]{"src/main/java/k.log"})));