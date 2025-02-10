package org.example;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.FileWriter;
import java.io.IOException;

public class PersonService implements PublishPersonDetails {

    static StringBuilder validHtmlContent = new StringBuilder();
    static StringBuilder invalidHtmlContent = new StringBuilder();

    String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    boolean isInvalidName(String name) {
        return name == null || !name.matches("[A-Za-z\\-' ]{1,45}");
    }

    boolean isValidAddress(Element addressElement) {
        if (addressElement == null) return false;
        boolean hasLine1AndPostcode = getTagValue("line1", addressElement) != null && getTagValue("postcode", addressElement) != null;
        boolean hasFullAddress = getTagValue("buildingNumber", addressElement) != null &&
                getTagValue("streetName", addressElement) != null &&
                getTagValue("town", addressElement) != null &&
                getTagValue("postcode", addressElement) != null;
        return hasLine1AndPostcode || hasFullAddress;
    }

    String parseAddress(Element addressElement) {
        if (addressElement == null) return "-";
        String line1 = getTagValue("line1", addressElement);
        String postcode = getTagValue("postcode", addressElement);
        String buildingNumber = getTagValue("buildingNumber", addressElement);
        String streetName = getTagValue("streetName", addressElement);
        String town = getTagValue("town", addressElement);

        if (line1 != null && postcode != null) {
            return "line1: " + line1 + ", " + postcode;
        } else if (buildingNumber != null && streetName != null && town != null && postcode != null) {
            return "line1: " + buildingNumber + " " + streetName + " " + town + ", " + postcode;
        }
        return "-";
    }

    @Override
    public void createHTMLHeader() {
        validHtmlContent.append("<html><head><title>Valid Records</title></head><body>");
        validHtmlContent.append("<table border='1'><tr style=\"background-color:#6699CC\"><th>First Names</th><th>Last Name</th><th>DOB</th><th>Address</th><th>Years at Address</th><th>Passport Number</th><th>NI Number</th></tr>");

        invalidHtmlContent.append("<html><head><title>Invalid Records</title></head><body>");
        invalidHtmlContent.append("<table border='1'><tr style=\"background-color:#6699CC\"><th>First Names</th><th>Last Name</th><th>DOB</th><th>Address</th><th>Years at Address</th><th>Passport Number</th><th>NI Number</th><th>Reason</th></tr>");
    }

    @Override
    public void createHTMLFooter(StringBuilder htmlContent) {
        htmlContent.append("</table></body></html>");
    }

    @Override
    public StringBuilder setInValidHtmlContent(String firstNames, String lastName, String dob, String address, int yearsAtAddress, String passportNumber, String nationalInsuranceNumber, String reason) {
        return invalidHtmlContent.append("<tr>")
                .append("<td>").append(firstNames).append("</td>")
                .append("<td>").append(lastName).append("</td>")
                .append("<td>").append(dob).append("</td>")
                .append("<td>").append(address).append("</td>")
                .append("<td>").append(yearsAtAddress).append("</td>")
                .append("<td>").append(passportNumber != null ? passportNumber : "-").append("</td>")
                .append("<td>").append(nationalInsuranceNumber != null ? nationalInsuranceNumber : "-").append("</td>")
                .append("<td>").append(reason).append("</td>")
                .append("</tr>");
    }

    @Override
    public StringBuilder setValidHtmlContent(String firstNames, String lastName, String dob, String address, int yearsAtAddress, String passportNumber, String nationalInsuranceNumber) {
        return validHtmlContent.append("<tr>")
                .append("<td>").append(firstNames).append("</td>")
                .append("<td>").append(lastName).append("</td>")
                .append("<td>").append(dob).append("</td>")
                .append("<td>").append(address).append("</td>")
                .append("<td>").append(yearsAtAddress).append("</td>")
                .append("<td>").append(passportNumber != null ? passportNumber : "-").append("</td>")
                .append("<td>").append(nationalInsuranceNumber != null ? nationalInsuranceNumber : "-").append("</td>")
                .append("</tr>");
    }

    @Override
    public String generateDashboardHtml(int noPassportNoNIN, int lessThanFiveYears, int moreThanTwoWordsInFirstName, int validCount, int invalidCount) {
        return new StringBuilder()
                .append("<html lang='en'><head><meta charset='UTF-8'><title>Dash Board</title><style>")
                .append("  body{font-family:Arial,sans-serif;display:flex;justify-content:center;align-items:center;height:100vh;margin:0;}")
                .append("  table{border-collapse:collapse;width:50%;text-align:center;}td{padding:10px;border:1px solid #ddd;}th,td")
                .append("  {font-size:1.2em;}</style></head><body><table><tr bgcolor='#ecf0f1'><td>Valid Records</td><td>Invalid Records</td>")
                .append("</tr><tr><td>")
                .append(validCount)
                .append("</td><td>")
                .append(invalidCount)
                .append("</td></tr><tr bgcolor='#ecf0f1'><td>No Passport and National Insurance number</td>")
                .append("  <td>People Lived at Address < 5 Years</td></tr><tr><td>")
                .append(noPassportNoNIN).append("</td><td>")
                .append(moreThanTwoWordsInFirstName)
                .append("</td></tr><tr bgcolor='#ecf0f1'>")
                .append("  <td colspan='2'>People with > 2 Words in First Name</td></tr><tr><td colspan='2'>")
                .append(lessThanFiveYears).append("</td></tr></table></body></html>")
                .toString();

    }

    public void publishHTMLFiles(StringBuilder htmlContent, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter("src/main/resources/Output/" + fileName + ".html")) {
            writer.write(htmlContent.toString());
        }
    }
}
