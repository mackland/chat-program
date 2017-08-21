package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import view.ChatWindow;
import model.chatFunctionality;
import model.utility;

public class SetupProgram {

    private ChatWindow myWindow;
    private chatFunctionality myProgram;
    private String ip;
    private int port;
    boolean isServer;

    public SetupProgram(boolean tof, String username, String ip, int port) {
        this.port = port;
        this.ip = ip;
        isServer = tof;

        String ServerorClient = getType(tof);

        myWindow = new view.ChatWindow(ServerorClient, username);
        myWindow.setVisible(true);

        if (tof) {
            myProgram = new model.Server(username, port, myWindow);
        } else {
            myProgram = new model.Client(username, ip, port, myWindow);
        }

        myProgram.start();

        myWindow.userText.addActionListener(new sendMessageClick());
        myWindow.usernameText.addActionListener(new updateUsername());
        myWindow.disconnectButton.addActionListener(new disconnectClick());
        myWindow.colorButton.addActionListener(new getColor());
    }

    private String getType(boolean tof) {
        if (tof) {
            return "Server";
        } else {
            return "Client";
        }
    }

    private class sendMessageClick implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            try {
                myProgram.sendMessage(evt.getActionCommand());
            } catch (IOException ex) {

            } catch (XMLStreamException ex) {

            }
        }
    }

    private class updateUsername implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            myProgram.setUsername(evt.getActionCommand());
        }
    }

    private class disconnectClick implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            try {
                myProgram.disconnect();
            } catch (XMLStreamException ex) {

            } catch (IOException ex) {

            }
        }
    }

    private class getColor implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            Color tempColor = myWindow.getColor();
            String HexaColor;
            HexaColor = utility.toHexString(tempColor);
            myProgram.setColor(HexaColor);
        }
    }
}
