package learning.model;

public class Instructor {
    private int instructorId;
    private String name;
    private String email;
    private String specialization;

    public Instructor() {
    }

    public Instructor(int instructorId, String name, String email,
                      String specialization) {
        this.instructorId = instructorId;
        this.name = name;
        this.email = email;
        this.specialization = specialization;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
