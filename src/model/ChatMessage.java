package model;

public class ChatMessage {

    public static final int TEXT = 0;
    public static final int REQUEST = 1;
    public static final int REPORT = 2;
    public static final int DISCONNECT = 3;
    public static final int FILEREQUEST = 4;
    public static final int FILERESPONSE = 5;
    public static final int ENCRYPTED = 6;

    private int type;
    private String text;
    private String sender;
    private String receiver;
    private String color;
    private String answer;
    private String fileName;
    private String fileSize;
    private String port;
    private String event;

    public ChatMessage() {

    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String sender) {
        this.receiver = sender;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String size) {
        this.fileSize = size;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
