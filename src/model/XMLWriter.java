package model;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

public class XMLWriter {

    private ByteArrayOutputStream message;		// The XML-coded message
    private XMLOutputFactory outputFactory;		// Creates the event writer
    private XMLEventWriter eventWriter;			// Adds the tags and attributes of the message to the stream
    private XMLEventFactory eventFactory;		// Creates the tags and attributes of the message

    protected String textMsg(String sender, String text, String color) throws XMLStreamException {
        message = new ByteArrayOutputStream();
        outputFactory = XMLOutputFactory.newInstance();
        eventWriter = outputFactory.createXMLEventWriter(message);
        eventFactory = XMLEventFactory.newInstance();

        // Create the tags and add attributes		
        createOpenTag("message", "", "sender", sender);
        createOpenTag("text", text, "color", color);

        // Close the tags	
        eventWriter.add(eventFactory.createEndElement("", "", "text"));
        eventWriter.add(eventFactory.createEndElement("", "", "message"));
        eventWriter.close();

        return message.toString();
    }

    protected String requestMsg(String sender, String text, String answer) throws XMLStreamException {

        // Initialize variables
        message = new ByteArrayOutputStream();
        outputFactory = XMLOutputFactory.newInstance();
        eventWriter = outputFactory.createXMLEventWriter(message);
        eventFactory = XMLEventFactory.newInstance();

        // Create the tags and add attributes
        createOpenTag("message", "", "sender", sender);
        createOpenTag("request", text, "answer", answer);

        // Close the tags
        eventWriter.add(eventFactory.createEndElement("", "", "request"));
        eventWriter.add(eventFactory.createEndElement("", "", "message"));
        eventWriter.close();

        return message.toString();
    }

    public String reportMsg(String sender, String text, String event) throws XMLStreamException {

        // Initialize variables
        message = new ByteArrayOutputStream();
        outputFactory = XMLOutputFactory.newInstance();
        eventWriter = outputFactory.createXMLEventWriter(message);
        eventFactory = XMLEventFactory.newInstance();

        // Create the tags and add attributes
        createOpenTag("message", "", "sender", sender);
        createOpenTag("report", text, "event", event);

        // Close the tags
        eventWriter.add(eventFactory.createEndElement("", "", "report"));
        eventWriter.add(eventFactory.createEndElement("", "", "message"));
        eventWriter.close();

        return message.toString();
    }

    public String diconnectMsg(String sender) throws XMLStreamException {

        // Initialize variables
        message = new ByteArrayOutputStream();
        outputFactory = XMLOutputFactory.newInstance();
        eventWriter = outputFactory.createXMLEventWriter(message);
        eventFactory = XMLEventFactory.newInstance();

        // Create the tags and add attributes		
        createOpenTag("message", "", "sender", sender);
        createOpenTag("disconnect", "", "", "");

        // Close the tags
        eventWriter.add(eventFactory.createEndElement("", "", "disconnect"));
        eventWriter.add(eventFactory.createEndElement("", "", "message"));
        eventWriter.close();

        return message.toString();
    }

    private void createOpenTag(String tagName, String tagValue, String attributeName, String attributeValue) throws XMLStreamException {
        eventWriter.add(eventFactory.createStartElement("", "", tagName));
        if (attributeName != "") {
            eventWriter.add(eventFactory.createAttribute(attributeName, attributeValue));
        }
        eventWriter.add(eventFactory.createCharacters(tagValue));
    }
}
