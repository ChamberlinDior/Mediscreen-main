/* Data to test the PatientReport DB */

-- USE mediscreen;

-- Default values for table 'triggers'
delete from triggers;
INSERT INTO triggers (term)
VALUES
('hémoglobine a1c'),('hemoglobin a1c'),
('microalbumine'),('microalbumin'),
('taille'),('height'),
('poids'),('weight'),
('fumeur'),('smoker'),
('anormal'),('abnormal'),
('cholestérol'),('cholesterol'),
('vertige'),('dizziness'),
('rechute'),('relapse'),
('réaction'),('reaction'),
('anticorps'),('antibodies');
