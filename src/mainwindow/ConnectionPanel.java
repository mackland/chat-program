package mainwindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class ConnectionPanel extends JFrame {

    public ConnectionPanel() {
        initComponents();
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        nameLabel = new JLabel("Name:");
        ipLabel = new JLabel("Host (IP):");
        portLabel = new JLabel("Port:");
        nameField = new JTextField("Mackland");
        ipField = new JTextField("127.0.0.1");
        portField = new JTextField("1234");
        jPanel2 = new JPanel();
        serverRadio = new JRadioButton("Server");
        clientRadio = new JRadioButton("Client");
        radioGroup = new ButtonGroup();
        okButton = new JButton("Ok");
        cancelButton = new JButton("Cancel");

        isServer = true;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jPanel1.setBorder(BorderFactory.createLineBorder(new Color(153, 153, 153)));
        jPanel1.setForeground(new Color(153, 153, 153));

        ipField.setEditable(false);

        serverRadio.setSelected(true);
        serverRadio.addActionListener(new chooseServer());
        radioGroup.add(serverRadio);

        clientRadio.addActionListener(new chooseClient());
        radioGroup.add(clientRadio);

        okButton.addActionListener(new startProgram());

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
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(portLabel)
                                .addComponent(nameLabel)
                                .addComponent(ipLabel))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(portField)
                                .addComponent(ipField)
                                .addComponent(nameField))
                        .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(nameLabel)
                                .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(ipLabel)
                                .addComponent(ipField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(portLabel)
                                .addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(BorderFactory.createLineBorder(new Color(153, 153, 153)));

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(serverRadio)
                        .addGap(18, 18, 18)
                        .addComponent(clientRadio)
                        .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(serverRadio)
                                .addComponent(clientRadio))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addComponent(okButton, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addComponent(cancelButton)))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cancelButton)
                                .addComponent(okButton))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
    private class startProgram implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            username = nameField.getText();
            ip = ipField.getText();
            port = Integer.parseInt(portField.getText());

            new controller.SetupProgram(isServer, username, ip, port);
        }
    }

    private class chooseServer implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            isServer = true;
            ipField.setEditable(false);
        }
    }

    private class chooseClient implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            isServer = false;
            ipField.setEditable(true);
        }
    }

    public static void main(String args[]) {
        new ConnectionPanel().setVisible(true);
    }

    private boolean isServer;
    private String username;
    private String ip;
    private int port;

    private JButton okButton;
    private JButton cancelButton;

    private JPanel jPanel1;
    private JPanel jPanel2;

    private JRadioButton serverRadio;
    private JRadioButton clientRadio;
    private ButtonGroup radioGroup;

    private JLabel nameLabel;
    private JLabel ipLabel;
    private JLabel portLabel;

    private JTextField nameField;
    private JTextField ipField;
    private JTextField portField;
}
