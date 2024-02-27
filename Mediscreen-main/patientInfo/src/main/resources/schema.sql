/* Setting up PatientInfo DB */

-- Clear Database

USE mediscreen;
DROP TABLE IF EXISTS patient;


-- Table structure for table `patient`

CREATE TABLE patient (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  family VARCHAR(100) NOT NULL,
  given VARCHAR(100) NOT NULL,
  dob VARCHAR(10) NOT NULL,
  sex VARCHAR(1) NOT NULL,
  address VARCHAR(100),
  phone VARCHAR(20)
  );

