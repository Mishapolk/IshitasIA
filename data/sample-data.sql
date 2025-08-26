DROP TABLE IF EXISTS users;
CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT, role TEXT);
INSERT INTO users(username,password,role) VALUES
('nurse','nurse123','nurse'),
('teacher','teacher123','teacher');

DROP TABLE IF EXISTS students;
CREATE TABLE students (id INTEGER PRIMARY KEY, name TEXT, class TEXT, photo TEXT, conditions TEXT, allergies TEXT, emergency_contact TEXT);
INSERT INTO students VALUES
(1,'Alice Johnson','5A','', 'Asthma','Peanuts','Mrs. Johnson 1234567890'),
(2,'Bob Smith','6B','', 'None','N/A','Mr. Smith 0987654321'),
(3,'Charlie Brown','5A','', 'Diabetes','Nuts','Mrs. Brown 1122334455');

DROP TABLE IF EXISTS slips;
CREATE TABLE slips (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER, teacher_name TEXT, complaint TEXT, time_sent TEXT, nurse_notes TEXT, status TEXT);
INSERT INTO slips(student_id, teacher_name, complaint, time_sent, nurse_notes, status) VALUES
(1,'Mr. Adams','Headache','2024-05-01 09:15','Given rest','resolved'),
(2,'Ms. Blake','Stomachache','2024-05-02 10:30','', 'pending');

DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory (id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT UNIQUE, quantity INTEGER, threshold INTEGER);
INSERT INTO inventory(item,quantity,threshold) VALUES
('Bandage',20,5),
('Paracetamol',30,10),
('Antiseptic',15,3);

DROP TABLE IF EXISTS medications;
CREATE TABLE medications (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER, medicine TEXT, dosage TEXT, time_given TEXT);
INSERT INTO medications(student_id,medicine,dosage,time_given) VALUES
(1,'Paracetamol','10ml','2024-04-20 09:00'),
(3,'Insulin','5 units','2024-04-21 12:00');
