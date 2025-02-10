package org.example;

public interface PublishPersonDetails {

    void createHTMLHeader();

    void createHTMLFooter(StringBuilder htmlContent);

    StringBuilder setInValidHtmlContent(String firstNames, String lastName, String dob, String address, int yearsAtAddress, String passportNumber, String nationalInsuranceNumber, String reason);

    StringBuilder setValidHtmlContent(String firstNames, String lastName, String dob, String address, int yearsAtAddress, String passportNumber, String nationalInsuranceNumber);

    String generateDashboardHtml(int noPassportNoNIN, int lessThanFiveYears, int moreThanTwoWordsInFirstName, int validCount, int invalidCount);
}
