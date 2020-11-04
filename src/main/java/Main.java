package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

/*
 * This example demostrates how to use a SSLSocket as client to
 * send a HTTP request and get response from an HTTPS server.
 * It assumes that the client is not behind a firewall
 */

public class Main {
    public static String readFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }


    public static void main(String[] args) throws Exception {
        EmailSender emailSender = new EmailSender("malkit2000ll@gmail.com", "Oleksandr Kotyk", "smtp.gmail.com", 465);
//        System.out.println(emailSender.logIn("S7sh97k105t"));
        System.out.println(emailSender.logIn("S97sh97k105t"));
//        System.out.println(emailSender.logOut());
//        System.out.println(emailSender.logIn("S97sh97k105t"));
        System.out.println(emailSender.sendEmail(new Email("malkit2000l@icloud.com", "Oleksandr Kotyk", "New messagefasdf\ndsf\ndfsdaf", "Subject", new String[]{"src/main/java/textFiles/message.txt"})));
    }
}