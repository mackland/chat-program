package model;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;
import view.IObserver;

public class Server extends Thread implements chatFunctionality {

    private ArrayList<ClientThread> clientList;
    private String username;
    private int port;
    private ServerSocket server;
    private IObserver observer;
    private static int uniqueId;
    private XMLWriter writer;
    private XMLParser parser;
    private String color = "#000000";

    public Server(String name, int port, IObserver observer) {
        uniqueId = 0;
        this.username = name;
        this.port = port;
        clientList = new ArrayList<ClientThread>();
        this.observer = observer;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            observer.notify("Waiting for someone to connect...", 0);
            while (!server.isClosed()) {
                try {
                    Socket socketen = server.accept();
                    ClientThread thread = new ClientThread(socketen, observer);
                    startThread(thread);
                } catch (EOFException eofException) {
                    System.out.println("fastnade här");
                } catch (SocketException se) {
                    System.out.println("Server stängde connection");
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void sendMessage(String message) throws IOException, XMLStreamException {
        broadcast(writer.textMsg(username, message, color));
        observer.notify(username + ": " + message, color);
    }

    @Override
    public void disconnect() throws XMLStreamException, IOException {
        broadcast(writer.diconnectMsg(username));
        removeCloseThreads();
        server.close();
    }

    private synchronized void broadcast(String message) {
        // loop in reverse order in case we would have to remove a Client because it has disconnected
        try {
            for (int i = clientList.size(); --i >= 0;) {
                clientList.get(i).sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
        }
    }

    private synchronized void unicast(String message, int receiverId) throws IOException, XMLStreamException {
        // we loop in reverse order in case we would have to remove a Client because it has disconnected
        for (int i = clientList.size(); --i >= 0;) {
            ClientThread thread = clientList.get(i);
            if (thread.id == receiverId) {
                thread.sendMessage(message);
            }
        }
    }

    private boolean answerRequest(String username) {
        return observer.notify(username);
    }

    private void startThread(ClientThread thread) {
        clientList.add(thread);
        thread.start();
    }

    private synchronized void removeThread(int threadId) {
        for (int i = 0; i < clientList.size(); ++i) {
            ClientThread thread = clientList.get(i);
            if (thread.id == threadId) {
                clientList.remove(i);
            }
        }
    }

    private synchronized void removeCloseThreads() throws IOException {
        for (int i = 0; i < clientList.size(); ++i) {
            ClientThread thread = clientList.get(i);
            thread.closeStreams();
            removeThread(thread.id);
        }
    }

    private class ClientThread extends ConnectionObject {

        private int id;

        ClientThread(Socket socket, IObserver observer) {
            this.client = socket;
            this.observer = observer;
            id = ++uniqueId;
            writer = new XMLWriter();
            parser = new XMLParser();

            try {
                setupStreams();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            do {
                try {
                    lineIn = input.readLine();
                    chatMessage = parser.parseXML(lineIn);
                    msghandler(chatMessage);
                } catch (SocketException se) {

                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (XMLStreamException e) {

                }
            } while (!client.isClosed());
        }

        private void setupStreams() throws IOException {
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream(), true);
        }

        public void sendMessage(String message) throws IOException, XMLStreamException {
            output.println(message);
            output.flush();
        }

        private void msghandler(ChatMessage message) throws IOException, XMLStreamException {
            // TODO Auto-generated catch block
            switch (message.getType()) {
                case ChatMessage.TEXT:
                    broadcast(lineIn);//text for server etc
                    observer.notify(message.getSender() + ": " + message.getText(), message.getColor());
                    break;

                case ChatMessage.REQUEST:
                    //Get request, add or deny
                    observer.notify(message.getSender() + " wants to connect.", color);
                    if (answerRequest(message.getSender())) {
                        output.println(writer.requestMsg(username, "", "Yes"));
                        broadcast(writer.reportMsg(username, message.getSender() + " has connected.", ""));
                        observer.notify(message.getSender() + " has connected.", "#000000");
                        unicast(writer.textMsg(username, "Välkommen", color), id);
                        observer.notify(message.getSender(), 2);
                    } else {
                        output.println(writer.requestMsg(username, "", "No"));
                        closeStreams();
                        removeThread(id);
                        //todo remove from clientlist
                    }
                    break;

                case ChatMessage.DISCONNECT:
                    //TODO: Send message to all, display, remove
                    unicast(writer.diconnectMsg(username), id);
                    closeStreams();
                    removeThread(id);
                    broadcast(writer.reportMsg("", message.getSender() + " has disconnected.", color));
                    observer.notify(message.getSender() + " has disconnected.", "#000000");
                    observer.notify(message.getSender(), 3);
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
}
