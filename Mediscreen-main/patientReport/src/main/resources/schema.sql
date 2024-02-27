/* Setting up PatientReport DB */

-- Clear Database

USE mediscreen;
DROP TABLE IF EXISTS triggers;


-- Table structure for table `triggers`

CREATE TABLE triggers (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  term VARCHAR(30) 
  );

