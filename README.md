# InforTrackTest

This project contains API tests for the InforTrack application.

## Description
This project is built using Maven and contains API tests written in Java using the TestNG framework. It uses Rest Assured for API testing and generates Extent reports for test execution results.

## Setup Instructions
### Prerequisites:
JDK Version: JDK 21

Maven Version: 3.9.6

### Build Information:
To set up the project, follow these steps:
1. Clone the Repository: Clone this repository to your local machine using the following command:
```bash
git clone https://github.com/vund92/InforTrackTest.git
```
2. Build the Project: Build the project using Maven by running the following command in the project root directory:
```bash
mvn clean install
```

## Test executions
To run the tests, use the following Maven command:
```bash
mvn clean test -Denv=<execution_environment>
```
Replace <execution_environment> with the appropriate environment keyword: test / stage
