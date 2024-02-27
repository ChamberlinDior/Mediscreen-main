# Patienui

Patientui is an API that displays patient information drawn from the PatientInfo API.

### Prerequisites

What things you need to install the software and how to install them

- Spring boot 3.0.4
- Java 17
- Maven 4.0.0
- Lombok 1.18.26
- ThymeLeaf 3.0.4

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install Lombock:

https://projectlombok.org/download


## Endpoints examples

**To display the Home page :**<br>
[http://localhost:8080](http://localhost:8080)

**To get the list of all patients in Patient list page :**<br>
[http://localhost:8080/PatientList](http://localhost:8080/PatientList)

**To find a patient by his Last name and First name in Patient search page :**<br>
http://localhost:8080/PatientSearch<br>
Exemple : [http://localhost:8080/PatientSearch?family=Last1&&given=First1](http://localhost:8080/PatientSearch?family=Last1&&given=First1)

**To get a patient informations in Patient info page :**<br>
http://localhost:8080/PatientInfo<br>
Exemple : [http://localhost:8080/PatientInfo?id=1](http://localhost:8080/PatientInfo?id=1)








