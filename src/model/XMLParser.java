package model;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.stream.events.Attribute;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

//import model.ChatMessage;
public class XMLParser {

    private static final String MESSAGE = "message";
    private static final String TEXT = "text";
    private static final String SENDER = "sender";
    private static final String COLOR = "color";
    private static final String REQUEST = "request";
    private static final String DISCONNECT = "disconnect";
    private static final String FILEREQUEST = "filerequest";
    private static final String FILERESPONSE = "fileresponse";
    private static final String NAME = "name";
    private static final String SIZE = "size";
    private static final String ANSWER = "answer";
    private static final String PORT = "port";
    private static final String REPORT = "report";
    private static final String EVENT = "event";

    private ChatMessage message;
    private byte[] byteArray;
    private ByteArrayInputStream inputStream;

    XMLInputFactory inputFactory;
    XMLEventReader eventReader;
    XMLEvent event;
    StartElement startElement;
    Iterator<Attribute> attributes;

    @SuppressWarnings({"unchecked"})
    public ChatMessage parseXML(String xml) throws XMLStreamException, UnsupportedEncodingException {

        message = new ChatMessage();

        // Turn the string into a readable stream
        byteArray = xml.getBytes("UTF-8");
        inputStream = new ByteArrayInputStream(byteArray);

        /* Create a new XMLInputFactory */
        inputFactory = XMLInputFactory.newInstance();
        /* Make Entity References readable, ><& etc */
        inputFactory.setProperty(XMLInputFactory.IS_COALESCING, true);
        /* Setup a new eventReader */
        eventReader = inputFactory.createXMLEventReader(inputStream);

        /* read the XML-formated string */
        while (eventReader.hasNext()) {
            /* Move the parser forward one step */
            event = eventReader.nextEvent();

            /* If the next element is a new tag */
            if (event.isStartElement()) {
                startElement = event.asStartElement();

                /* If <message> tag */
                if (startElement.getName().getLocalPart().equals(MESSAGE)) {
                    /*
                     *  We read the attributes from this tag and add the 
                     *  sender attribute to our object
                     */
                    attributes = startElement.getAttributes();

                    while (attributes.hasNext()) {
                        Attribute attribute = attributes.next();
                        if (attribute.getName().toString().equals(SENDER)) {
                            message.setSender(attribute.getValue());
                        }
                    }
                } else if (event.asStartElement().getName().getLocalPart().equals(TEXT)) {

                    message.setType(ChatMessage.TEXT);
                    event = eventReader.nextEvent();

                    /* Disregard unknown tags */
                    while (event.isStartElement()) {
                        event = eventReader.nextEvent();
                    }

                    /* Read the text within the tag */
                    // obs IScharcters() i testet, ASchar i konverteringen
                    if (event.isCharacters()) {
                        message.setText(event.asCharacters().getData());
                    }

                    /* Read attributes of the text */
                    Iterator<Attribute> textAttributes = startElement.getAttributes();
                    while (textAttributes.hasNext()) {
                        Attribute attribute = textAttributes.next();
                        if (attribute.getName().toString().equals(COLOR)) {
                            message.setColor(attribute.getValue());
                        }
                    }

                } else if (event.asStartElement().getName().getLocalPart().equals(FILEREQUEST)) {
                    /* We got a filerequest message */
                    message.setType(ChatMessage.FILEREQUEST);

                    /* Move the parser forward one step */
                    event = eventReader.nextEvent();

                    /* Read the text within the filerequest tag*/
                    message.setText(event.asCharacters().getData());

                    /* Read attributes of the filerequest tag */
                    Iterator<Attribute> textAttributes = startElement
                            .getAttributes();
                    while (textAttributes.hasNext()) {
                        Attribute attribute = textAttributes.next();
                        if (attribute.getName().toString().equals(NAME)) {
                            message.setFileName(attribute.getValue());
                        }
                        if (attribute.getName().toString().equals(SIZE)) {
                            message.setFileSize(attribute.getValue());
                        }
                    }
                } else if (event.asStartElement().getName().getLocalPart().equals(FILERESPONSE)) {

                    /* We got a fileresponse message - WOOOOOOOOO */
                    message.setType(ChatMessage.FILERESPONSE);

                    /* Move parser forward one step */
                    event = eventReader.nextEvent();

                    /* Read the text within the fileresponse tag */
                    if (event.isCharacters()) {
                        message.setText(event.asCharacters().getData());
                    }

                    // Read attributes of the fileresponse-tag
                    Iterator<Attribute> textAttributes = startElement.getAttributes();
                    while (textAttributes.hasNext()) {
                        Attribute attribute = textAttributes.next();
                        if (attribute.getName().toString().equals(ANSWER)) {
                            message.setAnswer(attribute.getValue());
                        }
                        if (attribute.getName().toString().equals(PORT)) {
                            message.setPort(attribute.getValue());
                        }
                    }
                } else if (event.asStartElement().getName().getLocalPart().equals(REQUEST)) {
                    message.setType(ChatMessage.REQUEST);

                    /* Move parser forward one step */
                    event = eventReader.nextEvent();

                    /* Read the text within the request tag */
                    if (event.isCharacters()) {
                        message.setText(event.asCharacters().getData());
                    }

                    // Read attributes of the request-tag
                    Iterator<Attribute> textAttributes = startElement.getAttributes();
                    while (textAttributes.hasNext()) {
                        Attribute attribute = textAttributes.next();
                        if (attribute.getName().toString().equals(ANSWER)) {
                            message.setAnswer(attribute.getValue());
                        }
                    }
                } else if (event.asStartElement().getName().getLocalPart().equals(REPORT)) {
                    message.setType(ChatMessage.REPORT);

                    /* Move parser forward one step */
                    event = eventReader.nextEvent();

                    /* Read the text within the report tag */
                    if (event.isCharacters()) {
                        message.setText(event.asCharacters().getData());
                    }

                    // Read attributes of the request-tag
                    Iterator<Attribute> textAttributes = startElement.getAttributes();
                    while (textAttributes.hasNext()) {
                        Attribute attribute = textAttributes.next();
                        if (attribute.getName().toString().equals(EVENT)) {
                            message.setEvent(attribute.getValue());
                        }
                    }
                } else if (event.asStartElement().getName().getLocalPart().equals(DISCONNECT)) {
                    message.setType(ChatMessage.DISCONNECT);
                }
            }
        }

        /* Return the Item containing the different message parts */
        return message;
    }
}
