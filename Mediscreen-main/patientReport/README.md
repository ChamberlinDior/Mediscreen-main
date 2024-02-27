# PatienReport

PatientReport is an API that calculates a patient's risk of having diabetes based on age, gender, and terms used in their doctor's notes.

It communicate with Patientui API.

PatientNote use MySQL data base that contains trigger terms to look for in the doctor's notes.


### Prerequisites

What things you need to install the software and how to install them

- Spring boot 3.0.4
- Java 17
- Maven 4.0.0
- Lombok 1.18.26
- MySQL 8.0.32

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install Lombock:

https://projectlombok.org/download


## Endpoints examples

**To get a patient's age and risk of having diabetes :**<br>
http://localhost:8083/PatientReport


## Swagger documentation

[http://localhost:8083/swagger-ui/index.html#/](http://localhost:8083/swagger-ui/index.html#/)






