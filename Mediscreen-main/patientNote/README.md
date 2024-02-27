# PatienNote

PatientNote is an API for managing the personal notes of a group of patients.

It communicate with Patientui API.

PatientNote use MongoDB data base.


### Prerequisites

What things you need to install the software and how to install them

- Spring boot 3.0.4
- Java 17
- Maven 4.0.0
- Lombok 1.18.26
- MogoDB 4.8.2

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install Lombock:

https://projectlombok.org/download


## Endpoints examples

**To get the notes of a patient identified by his id :**<br>
http://localhost:8082/PatientNote/byPatient<br>
Exemple : [http://localhost:8082/PatientNote/byPatient?patId=1](http://localhost:8082/PatientNote/byPatient?patId=1)

## Swagger documentation

[http://localhost:8082/swagger-ui/index.html#/](http://localhost:8082/swagger-ui/index.html#/)






