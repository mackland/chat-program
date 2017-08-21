package model;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;

public interface chatFunctionality{
   
    public void sendMessage(String text) throws XMLStreamException, IOException ;
    
    public void setUsername(String name);
    
    public void disconnect() throws XMLStreamException, IOException ;
    
    public void setColor(String color);
    
    public void start();
}
