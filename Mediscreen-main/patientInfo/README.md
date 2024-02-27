# PatienInfo

PatientInfo is an API for managing the personal information of a group of patients.

It communicate with Patientui API.

PatientInfo use MySQL data base.


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

**To get the list of all patients in JSON format :**<br>
[http://localhost:8081/PatientInfo/list](http://localhost:8081/PatientInfo/list)

**To get a patient by his id :**<br>
http://localhost:8081/PatientInfo/byId<br>
Exemple : [http://localhost:8081/PatientInfo/byId?id=1](http://localhost:8081/PatientInfo/byId?id=1)

**To get a patient by his Last name and First name :**<br>
http://localhost:8081/PatientInfo/byName<br>
Exemple : [http://localhost:8081/PatientInfo/byName?family=TestNone&&given=Test](http://localhost:8081/PatientInfo/byName?family=TestNone&&given=Test)


## Swagger documentation

[http://localhost:8081/swagger-ui/index.html#/](http://localhost:8081/swagger-ui/index.html#/)






