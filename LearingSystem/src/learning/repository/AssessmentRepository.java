package learning.repository;

import learning.exception.DatabaseOperationException;
import learning.model.Assessment;
import learning.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssessmentRepository {

    public void save(Assessment assessment) {
        String sql = "INSERT INTO assessments "
                + "(assessment_id, course_id, title, type, max_marks, duration_minutes) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, assessment.getAssessmentId());
            ps.setInt(2, assessment.getCourseId());
            ps.setString(3, assessment.getTitle());
            ps.setString(4, assessment.getType());
            ps.setBigDecimal(5, assessment.getMaxMarks());
            ps.setInt(6, assessment.getDurationMinutes());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to save assessment", e);
        }
    }

    public Assessment findById(int id) {
        String sql = "SELECT * FROM assessments WHERE assessment_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapAssessment(rs) : null;
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find assessment", e);
        }
    }

    public List<Assessment> findAll() {
        List<Assessment> assessments = new ArrayList<>();
        String sql = "SELECT * FROM assessments ORDER BY assessment_id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                assessments.add(mapAssessment(rs));
            }
            return assessments;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to list assessments", e);
        }
    }

    public boolean existsById(int id) {
        return findById(id) != null;
    }

    public void update(Assessment assessment) {
        String sql = "UPDATE assessments SET course_id = ?, title = ?, type = ?, "
                + "max_marks = ?, duration_minutes = ? WHERE assessment_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, assessment.getCourseId());
            ps.setString(2, assessment.getTitle());
            ps.setString(3, assessment.getType());
            ps.setBigDecimal(4, assessment.getMaxMarks());
            ps.setInt(5, assessment.getDurationMinutes());
            ps.setInt(6, assessment.getAssessmentId());

            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("Assessment not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update assessment", e);
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM assessments WHERE assessment_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("Assessment not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete assessment", e);
        }
    }

    public List<Assessment> findByCourseId(int courseId) {
        List<Assessment> assessments = new ArrayList<>();
        String sql = "SELECT * FROM assessments WHERE course_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    assessments.add(mapAssessment(rs));
                }
            }

            return assessments;

        } catch (SQLException e) {
            throw new DatabaseOperationException(
                    "Failed to find course assessments", e);
        }
    }

    public List<Assessment> findByType(String type) {
        List<Assessment> assessments = new ArrayList<>();
        String sql = "SELECT * FROM assessments WHERE LOWER(type) = LOWER(?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, type);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    assessments.add(mapAssessment(rs));
                }
            }

            return assessments;

        } catch (SQLException e) {
            throw new DatabaseOperationException(
                    "Failed to filter assessments by type", e);
        }
    }

    private Assessment mapAssessment(ResultSet rs) throws SQLException {
        return new Assessment(
                rs.getInt("assessment_id"),
                rs.getInt("course_id"),
                rs.getString("title"),
                rs.getString("type"),
                rs.getBigDecimal("max_marks"),
                rs.getInt("duration_minutes")
        );
    }
}