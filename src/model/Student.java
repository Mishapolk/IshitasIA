package model;

public class Student {
    private int id;
    private String name;
    private String studentClass;
    private String photo;
    private String conditions;
    private String allergies;
    private String emergencyContact;

    public Student(int id, String name, String studentClass, String photo, String conditions, String allergies, String emergencyContact) {
        this.id = id;
        this.name = name;
        this.studentClass = studentClass;
        this.photo = photo;
        this.conditions = conditions;
        this.allergies = allergies;
        this.emergencyContact = emergencyContact;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getStudentClass() { return studentClass; }
    public String getPhoto() { return photo; }
    public String getConditions() { return conditions; }
    public String getAllergies() { return allergies; }
    public String getEmergencyContact() { return emergencyContact; }
}
