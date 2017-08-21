package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import view.NewJFrame;

public class SendFile extends Thread{
    private JFileChooser a;
    private File myFile;    
    
    public SendFile(){
        a = new JFileChooser();
        a.showDialog(null, null);
        myFile = a.getSelectedFile();
        start();
    }
    
    public void run(){
        try {
            sendFile();
        } catch (IOException ex) {
            Logger.getLogger(SendFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendFile() throws IOException{
        ServerSocket fileServer = new ServerSocket(1233);
        
        System.out.println(myFile.toString());
        System.out.println(myFile.getName());
        System.out.println(myFile.length()/1024 + " kb");
        while(true){
            Socket fileSocket = fileServer.accept();
            byte[] mybytesarray = new byte[(int) myFile.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
            bis.read(mybytesarray,0,mybytesarray.length);
            OutputStream os = fileSocket.getOutputStream();
            os.write(mybytesarray,0,mybytesarray.length);
            os.flush();
            bis.close();
            os.close();
            fileSocket.close();
            a.getSelectedFile();
        }
    }
    
    public void receiveFile() throws IOException{
        Socket fileSocket = new Socket("127.0.0.1", 1233);
        byte[] mybytesarray = new byte[1024];
        InputStream is = fileSocket.getInputStream();
        FileOutputStream fos = new FileOutputStream(myFile);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int bytesRead = is.read(mybytesarray,0,mybytesarray.length);
        bos.write(mybytesarray,0,bytesRead);
        bos.close();
        is.close();
        fileSocket.close();
    }
    
    public static void main(String[] args) throws IOException{
        final SendFile b = new SendFile();

        System.out.println("kommer vi ens hit?");
        b.receiveFile();
    }
}
 