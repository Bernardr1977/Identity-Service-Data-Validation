package org.example;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import java.io.IOException;

public class ValidatePersonDetails extends PersonService{


    public void validateRecords(NodeList personList) throws IOException {

        int noPassportNoNIN = 0;
        int lessThanFiveYears = 0;
        int moreThanTwoWordsInFirstName = 0;
        int validCount = 0;
        int invalidCount = 0;

        StringBuilder validHtmlContent = new StringBuilder();
        StringBuilder invalidHtmlContent = new StringBuilder();

        createHTMLHeader();

        for (int i = 0; i < personList.getLength(); i++) {
            Node personNode = personList.item(i);
            if (personNode.getNodeType() == Node.ELEMENT_NODE) {
                Element person = (Element) personNode;

                // Get element values from the XML
                String firstNames = getTagValue("firstNames", person);
                String lastName = getTagValue("lastName", person);
                String dob = getTagValue("dateOfBirth", person);
                String yearsAtAddressStr = getTagValue("yearsAtAddress", person);
                String passportNumber = getTagValue("passportNumber", person);
                passportNumber = (passportNumber != null) ? passportNumber.toUpperCase() : null;
                String nationalInsuranceNumber = getTagValue("nationalInsuranceNumber", person);
                int yearsAtAddress = (yearsAtAddressStr != null) ? Integer.parseInt(yearsAtAddressStr) : 0;
                int age = 0;
                try {
                    if (dob != null) {
                        LocalDate birthDate = LocalDate.parse(dob, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        age = Period.between(birthDate, LocalDate.now()).getYears();
                    }
                } catch (Exception e) {
                    System.err.println("Invalid date format for DOB: " + dob);
                }


                Element addressElement = (Element) person.getElementsByTagName("address").item(0);
                String address = parseAddress(addressElement);

               String reason = "";

                //Validate all the rules for invalid records and add reason
                if (yearsAtAddress < 5) lessThanFiveYears++;
                if (firstNames.split(" ").length >= 2) moreThanTwoWordsInFirstName++;
                if (isInvalidName(firstNames)) reason += "Invalid first name. <br> ";
                if (isInvalidName(lastName)) reason += "Invalid last name. <br>";
                if (age < 18) reason += "Person is under 18. <br>";
                if (yearsAtAddress < 5) reason += "Has not lived at address for 5 years. <br> ";
                if (!isValidAddress(addressElement)) reason += "Invalid address. <br>";
                if (passportNumber == null && nationalInsuranceNumber == null) {
                    noPassportNoNIN++;
                    reason += "Person has no passport number or national insurance number. <br>";
                }
                boolean isValid = reason.isEmpty();


                //Form HTML with content
                if (isValid) {
                    validHtmlContent = setValidHtmlContent(firstNames, lastName, dob, address, yearsAtAddress,
                            passportNumber != null ? passportNumber : "-",
                            nationalInsuranceNumber != null ? nationalInsuranceNumber : "-");
                    validCount++;
                } else {
                    invalidHtmlContent = setInValidHtmlContent(firstNames, lastName, dob, address, yearsAtAddress,
                            passportNumber != null ? passportNumber : "-",
                            nationalInsuranceNumber != null ? nationalInsuranceNumber : "-", String.valueOf(reason));
                    invalidCount++;
                }

            }
        }

        String content = generateDashboardHtml(noPassportNoNIN, lessThanFiveYears,  moreThanTwoWordsInFirstName,  validCount,  invalidCount);

        // Publish the content to HTML file
        publishIfNotEmpty(invalidHtmlContent, "invalid-records");
        publishIfNotEmpty(validHtmlContent, "valid-records");
        publishIfNotEmpty(new StringBuilder(content),"dashboard-summary");
    }

    private void publishIfNotEmpty(StringBuilder content, String fileName) throws IOException {
        if (!content.isEmpty()) {
            if(!fileName.equals("dashboard-summary")) {
                createHTMLFooter(content);
                publishHTMLFiles(content, fileName);
            } else {
                publishHTMLFiles(content, fileName);
            }
        }
    }




}