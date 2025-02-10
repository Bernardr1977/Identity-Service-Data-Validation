package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class PersonDetails {

    ValidatePersonDetails validatePersonDetails;

    public PersonDetails(ValidatePersonDetails validatePersonDetails) {
        this.validatePersonDetails = validatePersonDetails;
    }

    public static void main(String[] args) {
        try {
            new PersonDetails(new ValidatePersonDetails()).processXML();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Error processing XML file: " + e.getMessage());
            e.printStackTrace(); // Log full stack trace
        }
    }

    public void processXML() throws ParserConfigurationException, IOException, SAXException {
        String filePath = "src/main/resources/Input/records.xml";
        File inputFile = new File(filePath);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document doc = dbFactory.newDocumentBuilder().parse(inputFile);

        NodeList personList = doc.getElementsByTagName("person");
        if (personList == null || personList.getLength() == 0) {
            System.err.println("No person records found in XML.");
            return;
        }

        validatePersonDetails.validateRecords(personList);

        System.out.println("Processing complete. Data has been published to the output folder.");
    }
}