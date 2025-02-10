# Identity Service Data Validation and Migration
This project is designed to parse, validate, and transform identity records from an XML file (records.xml) before migrating them to a new identity service. The system ensures that all data is correctly formatted and meets predefined validation rules before being processed.

Solution Breakdown
1. Input Data (records.xml)
The system reads identity records from an XML file located at:
📍 src/main/resources/Input/records.xml

    Each identity record contains the following fields:
    firstNames, lastName, dateOfBirth, address, yearsAtAddress,  passportNumber,  nationalInsuranceNumber

2. Java Classes Overview
   
| Class Name | Description |
| ---------- | ----------- |
| PersonDetails.java |	The main entry point of the application. It loads the XML file, parses identity records, and initiates validation. |
| PersonService.java | 	A service layer responsible for helper functions like extracting XML values and parsing addresses. |
| PublishPersonDetails.java (Interface)	| Defines methods for publishing validated records (e.g., exporting to HTML or database). |
| ValidatePersonDetails.java	| Implements validation logic to ensure identity records meet the required criteria. Generates reports in HTML format. |

3. Validation Process
Each record goes through a series of validation checks in ValidatePersonDetails.java.

The validation includes:

✅ Name Validation – Ensures the name format is correct.

✅ Date of Birth Validation – Verifies age (must be 18+).

✅ Address Validation – Confirms validity and residency duration.

✅ Passport/National Insurance Validation – Ensures at least one identifier exists.

4. Test Automation
Feature files have been created in src/test/resources/features/ to validate different aspects of the identity records:

| Feature File Name |	Purpose |
| ----------------- | ------- |
| AddressValidation.feature |	Tests if the address meets validation rules. |
| DOBValidation.feature |	Ensures the date of birth and age validation works. |
| NamesValidation.feature	| Verifies the correctness of first and last names. |
| PPNoOrNINoValidation.feature	| Checks that at least one of Passport or NI Number exists. |
| YearAtAddressValidation.feature |	Validates residency duration (minimum 5 years required). |


How to Run the Application

Clone the Repository

git clone <repo-url>

cd \src\main\java\org\example\

java src/main/java/org/example/PersonDetails.java

Output Reports
After execution, the system generates HTML reports under the output folder:

📂 src/main/resources/Output/valid-records.html → Contains valid records.

📂 src/main/resources/Output/invalid-records.html → Lists invalid records with reasons.

📂 src/main/resources/Output/dashboard-summary.html → Overview of validation results.

Conclusion

This project ensures that identity records meet the required quality before being migrated to the new identity service. The validation process is NOT automated (only the feature files have be created to showcase how the automation can be done) and generates reports.
