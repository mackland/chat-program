package model;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import view.IObserver;

public class Client extends ConnectionObject implements chatFunctionality {

    private String ip;
    private int port;
    private XMLWriter writer;
    private XMLParser parser;
    private String lineIn;
    public String color = "#000000";
    private String username;

    public Client(String username, String host, int port, IObserver observer) {
        this.username = username;
        this.ip = host;
        this.port = port;
        this.observer = observer;
        writer = new XMLWriter();
        parser = new XMLParser();
    }

    public void run() {
        try {
            connectToServer();
            setupStreams();
            requestConnection();
            whileChatting();
        } catch (EOFException eofException) {
            //showMessage("\n Client terminated connection");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (XMLStreamException xmlStreamException) {
            
        }finally {
            //closeCrap();
        }
    }

    private void connectToServer() throws IOException {
        observer.notify("Attempting connection...", 0);
        client = new Socket(InetAddress.getByName(ip), port);
        //observer.notify("Connected to: " + InetAddress.getLocalHost(), 0);
    }

    private void setupStreams() throws IOException {
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(client.getOutputStream(), true);
        //observer.notify("Streams are setup", 0);
    }

    private void requestConnection() {
        try {
            //System.out.println("Frågar om lov");
            output.println(writer.requestMsg(username, "", ""));
            output.flush();
        } catch (XMLStreamException ex) {

        }
    }

    private void whileChatting() throws IOException, XMLStreamException, SocketException {
        do {

                lineIn = input.readLine();
                chatMessage = parser.parseXML(lineIn);
                msghandler(chatMessage);

        } while (!client.isClosed());
    }

    @Override
    public void sendMessage(String message) throws IOException, XMLStreamException {
        output.println(writer.textMsg(username, message, color));
        output.flush();
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void disconnect() {
        try {
            output.println(writer.diconnectMsg(username));
            output.flush();
            observer.notify("--- Disconnected from server ---", 0);
        } catch (XMLStreamException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setColor(String color) {
        this.color = color;
    }

    private void msghandler(ChatMessage message) throws IOException, XMLStreamException {
        // TODO Auto-generated catch block

        switch (message.getType()) {
            case ChatMessage.TEXT:
                //text for server etc
                observer.notify(chatMessage.getSender() + ": " + chatMessage.getText(), chatMessage.getColor());
                break;
            case ChatMessage.REQUEST:
                //Get request, add or deny
                if (message.getAnswer().equals("Yes")) {
                    observer.notify("Nu ansluten till: " + message.getSender(), color);
                } else if (message.getAnswer().equals("No")) {
                    try {
                        observer.notify("Request was not accepted", color);
                        closeStreams();
                        //client.close();
                    } catch (IOException ex) {

                    }
                } else {
                    System.out.println("Dont know what the response was");
                }
                break;
            case ChatMessage.REPORT:
                observer.notify(message.getText(), "#000000");

                break;
            case ChatMessage.DISCONNECT:
                //TODO: Send message to all, display, remove
                //observer.notify("Request was not accepted", 0);
                //pusha ngt genom output
                output.println(writer.diconnectMsg(username));
                closeStreams();
                //client.close();
                observer.notify("dispose", 1);
                break;
            case ChatMessage.FILEREQUEST:
                //TODO:
                break;
            case ChatMessage.FILERESPONSE:
                //TODO:
                break;
        }
    }
}
