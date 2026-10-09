package learning.model;

import java.math.BigDecimal;

public class Assessment {
    private int assessmentId;
    private int courseId;
    private String title;
    private String type;
    private BigDecimal maxMarks;
    private int durationMinutes;

    public Assessment() {
    }

    public Assessment(int assessmentId, int courseId, String title,
                      String type, BigDecimal maxMarks, int durationMinutes) {
        this.assessmentId = assessmentId;
        this.courseId = courseId;
        this.title = title;
        this.type = type;
        this.maxMarks = maxMarks;
        this.durationMinutes = durationMinutes;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(BigDecimal maxMarks) {
        this.maxMarks = maxMarks;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
