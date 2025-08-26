# IshitasIA

Criterion A: Planning

Scenario:
The client is Ms. Pooja, the school nurse at my school. She is responsible for managing student health concerns, maintaining vaccination records, tracking medication stock, and responding to medical emergencies during school hours.

After consulting with my client, I have been told that currently, all tasks are carried out using handwritten forms, manual registers, and printed slips. Students come to the nurse with a paper slip signed by the teacher, which Ms. Pooja logs by hand—recording the student’s name, class, and the time the student left the nurse’s office. Vaccination schedules, medication permissions, and consent forms are stored in physical files or noted manually.

My client stated that this paper-based system causes significant delays and inefficiencies. Ms. Pooja often has to re-write the same data repeatedly for each student, especially during rush hours. She struggles to recall individual students’ medical histories or allergies without a central reference, and there’s no system in place to visualize patterns such as common illnesses or peak times for specific conditions. Medication stock is also tracked manually, which leads to surprise shortages. The absence of access control means anyone could potentially view or misplace sensitive medical information. Furthermore, the process of sending slips between teachers and the nurse is slow and unreliable, often affecting timely treatment or communication with parents in cases of emergencies.

Rationale for proposed solution:
To overcome these issues, I will develop a Java-based application with a user-friendly GUI using NetBeans IDE and an SQLite database as the backend. Java is ideal due to its cross-platform compatibility and strong community support. Its built-in Swing and JavaFX libraries allow for the creation of responsive, user-friendly interfaces. NetBeans IDE simplifies development with built-in tools for design, debugging, and version control. SQLite is chosen for its lightweight, serverless structure, which makes it easy to set up on school computers without the need for complex installations or maintenance. It also ensures fast data access and minimal resource usage, making it an efficient choice for educational environments.

To address the delays and inefficiencies caused by the current paper-based system, the proposed application will centralize all student health information. Instead of manually flipping through files, Ms. Pooja will have access to individual student health profiles which will load instantly when a name or ID is entered. This will help her quickly recall critical information, especially during emergencies. The issue of repeated handwriting and data duplication will be resolved through auto-fill forms. Rather than relying on paper slips passed between teachers and the nurse, the system will allow teachers to submit digital nurse slips that appear instantly on the nurse’s dashboard, ensuring better communication.

Moreover, vaccination dates and medication permissions will be stored digitally with a calendar and automatic reminders to track schedules. Medication stock alerts will prevent unexpected shortages by notifying Ms. Pooja before supplies run low. To protect sensitive information, the application will implement role-based login, ensuring that only authorized users can access confidential data. Health trends and common illness patterns can be visualized through graphs, helping identify a general concern or outbreaks. Moreover, the ability to generate and print PDF summaries of visits, consent forms, and reports will simplify communication with parents.

Word Count: 522

Success Criteria

Login & Access Rights
a. The application should have a secure login using School ID/PIN for both teachers and the nurse.
b. The application should have role-based access:

Nurse: Full access to student health records, inventory, bookings, and reports.

Teachers: Limited access to fill/view nurse slips for their students and sign digitally.

Appointment Booking System
a. The application should provide a calendar interface for vaccination and health bookings with color-coded slots (Green–Available, Red–Booked).
b. The calendar should allow editing or cancelling by clicking on booked entries.
c. Teachers or nurse can book slots by entering Student Name, Grade, Preferred Time.
d. Once a slot is booked, it is marked unavailable and stored in the system database.

Appointment Reminders & Consent Tracker
a. The application should automatically notify the nurse of upcoming appointments for the day.
b. The application should provide an option to generate a PDF appointment slip with student name, vaccine type, time slot, and consent status.
c. The application should allow the nurse to mark parental consent as “Received” or “Pending”, and this status should display clearly.

Student Health Profile & History
a. Each student should have a profile including Name, Class, ID, Photo, Medical Conditions, Allergies, Emergency Contact.
b. When the name or ID is entered, the profile photo and past visit history should load automatically.
c. Medical history should include past visits, illnesses, treatments, and medications administered.

Auto-Fill Visit Form
a. When the nurse selects a student, the visit form should auto-fill the current date, time, student name, and class.
b. The application should suggest common past complaints (e.g., headache, stomachache) for quick data entry.

Inventory Management & Low Stock Alerts
a. The application should allow the nurse to log the use of medical supplies and medicines.
b. The inventory should update quantities automatically after usage.
c. The system should display alerts when any item falls below the reorder threshold (e.g., “Only 4 gauze packs left — restock soon!”).

Medication Tracker
a. The application should allow the nurse to log the medicine name, dosage, and time of administration.
b. The system should track medicine restrictions for each student (e.g., “Only 1 Strepsil per day”).
c. The application should prevent over-administering with reminder alerts.

Digital Nurse Slip System
a. Teachers should be able to fill a digital form including student name, class, and time the student left its class.
b. The form should be automatically sent to the nurse dashboard.
c. The nurse should digitally sign the slip, add medical notes, and have the option to send it back to the teacher or save it to the system.

Auto-Generated Reports (PDF)
a. The application should generate PDF reports after every student visit including: student info, complaint, treatment, medication, and nurse notes – in cases of severe situations where the child is sent home, and parents have to be reported.
b. Reports should be saved in the system database.
c. Reports marked “Urgent” or “Severe” should be automatically emailed to the parent’s registered address.

Health Insights & Graphical Analysis
a. The application should generate graphs displaying:

Most common illnesses

Peak illness months

Class/grade-wise illness distribution
b. These insights should help the school plan preventive measures.

Nurse Dashboard – Real-Time Panel
a. The dashboard should show live status of:

Currently signed-in students

Medications due in the next hour

Daily summary of student visits

Pending digital slips and unsigned reports
