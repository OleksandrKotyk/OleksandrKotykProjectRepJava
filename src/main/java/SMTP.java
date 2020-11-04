package main.java;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

public class SMTP {
    private final SSLSocket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public SMTP(String host, int port) throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        socket = (SSLSocket) factory.createSocket(host, port);
        socket.startHandshake();
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        in.readLine();
    }


    public String send(String message, int time) throws IOException, InterruptedException {
        out.print(message + "\r\n");
        out.flush();

        if (out.checkError()) {
            throw new IOException("SMTP send exception !!!");
        }

        Thread.sleep(time);
        return in.readLine();
    }

    public void destroy() throws IOException {
        if (socket != null) {
            socket.close();
            in.close();
            out.close();
        }
    }

}
