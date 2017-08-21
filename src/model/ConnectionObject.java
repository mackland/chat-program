package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import view.IObserver;

public class ConnectionObject extends Thread {

    protected BufferedReader input;
    protected PrintWriter output;
    protected IObserver observer;
    protected ChatMessage chatMessage;
    protected Socket client;
    protected String lineIn;

    public ConnectionObject() {

    }

    protected void closeStreams() throws IOException {
        output.close();
        input.close();
        client.close();
    }
}
