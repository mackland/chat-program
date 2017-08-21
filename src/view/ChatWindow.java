package view;

import javax.swing.*;
import java.awt.Color;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ChatWindow extends JFrame implements IObserver {

    public ChatWindow(String type, String username) {
        super(type);
        initComponents(username);
    }

    private void initComponents(String username) {

        textAreaScrollPane = new JScrollPane();
        userText = new JTextField();

        textPane = new JTextPane();

        jPanel1 = new JPanel();
        boldToggle = new JToggleButton();
        fileButton = new JButton("File");
        colorButton = new JButton("Color");
        sendButton = new JButton("Send");

        jListScrollPane = new JScrollPane();
        userJList = new JList();

        jPanel2 = new JPanel();
        radioGroup = new ButtonGroup();
        jToggleButton2 = new JToggleButton("Encrypt");
        ceasarRadio = new JRadioButton("Ceasar");
        aesRadio = new JRadioButton("AES");

        jLabel1 = new JLabel("Username:");
        usernameText = new JTextField(username);
        disconnectButton = new JButton("Disconnect");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        radioGroup.add(aesRadio);
        radioGroup.add(ceasarRadio);

        textAreaScrollPane.setViewportView(textPane);
        jPanel1.setBorder(BorderFactory.createLineBorder(new Color(153, 153, 153)));

        boldToggle.setFont(new java.awt.Font("Tahoma", 1, 13));
        boldToggle.setText("B");
        listModel = new DefaultListModel();
        userJList = new JList(listModel);
        jListScrollPane.setViewportView(userJList);

        setLayout();

        pack();
    }

    private void setLayout() {
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(boldToggle)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fileButton)
                        .addGap(28, 28, 28)
                        .addComponent(colorButton)
                        .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(boldToggle)
                                .addComponent(fileButton)
                                .addComponent(colorButton))
                        .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel2.setBorder(BorderFactory.createLineBorder(new Color(153, 153, 153)));

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jToggleButton2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addContainerGap())
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(ceasarRadio)
                                                .addComponent(aesRadio))
                                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToggleButton2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(aesRadio)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ceasarRadio)
                        .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textAreaScrollPane, GroupLayout.PREFERRED_SIZE, 250, Short.MAX_VALUE)
                                .addComponent(userText)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(usernameText)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(disconnectButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                        .addComponent(jListScrollPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)) //, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE
                                .addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(usernameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(disconnectButton))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(textAreaScrollPane, GroupLayout.PREFERRED_SIZE, 116, Short.MAX_VALUE)
                                .addComponent(jListScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(userText, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(sendButton)
                                        .addGap(13, 13, 13)
                                        .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    public void showMessage(String text, String col) {
        Color color = Color.decode(col);
        appendToPane(textPane, "\n" + text, color);
        userText.setText("");
    }

    public void notify(String message, String col) {
        showMessage(message, col);
    }

    public void notify(String text, int type) {
        if (type == 0) {

        } else if (type == 1) {
            dispose();
        } else if (type == 2) {
            listModel.addElement(text);
        } else {
            listModel.removeElement(text);
        }
    }

    public boolean notify(String text) {
        return messageBox(text);
    }

    public Color getColor() {
        Color color = JColorChooser.showDialog(null, "Pick color", Color.WHITE);
        if (color == null) {
            color = (Color.BLACK);
        }
        return color;
    }

    private boolean messageBox(String username) {
        int reply = JOptionPane.showConfirmDialog(null, "New user wants to connect, " + username, "Attention!", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }

    private void appendToPane(JTextPane textpane, String message, Color col) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet AttrSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, col);

        AttrSet = sc.addAttribute(AttrSet, StyleConstants.FontFamily, "Lucida Console");
        AttrSet = sc.addAttribute(AttrSet, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        textpane.setCharacterAttributes(AttrSet, false);
        textpane.replaceSelection(message);
        int len = textpane.getDocument().getLength();
        textpane.setCaretPosition(len);

    }

    private JTextPane textPane;
    private JButton sendButton;
    private JButton fileButton;
    public JButton colorButton;
    public JButton disconnectButton;
    private JLabel jLabel1;
    private JList userJList;
    private DefaultListModel listModel;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JRadioButton ceasarRadio;
    private JRadioButton aesRadio;
    private ButtonGroup radioGroup;
    private JScrollPane textAreaScrollPane;
    private JScrollPane jListScrollPane;
    public JTextField userText;
    public JTextField usernameText;
    private JToggleButton boldToggle;
    private JToggleButton jToggleButton2;
}
