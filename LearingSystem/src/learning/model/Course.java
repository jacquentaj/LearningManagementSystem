package learning.model;

public class Course {
    private int courseId;
    private String courseCode;
    private String courseName;
    private int instructorId;
    private int maxStudents;

    public Course(int courseId, String courseCode, String courseName,
                  int instructorId, int maxStudents) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.instructorId = instructorId;
        this.maxStudents = maxStudents;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }
}
