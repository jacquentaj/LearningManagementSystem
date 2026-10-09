package learning.repository;

import learning.exception.DatabaseOperationException;
import learning.model.Enrollment;
import learning.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepository {

    public void save(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments "
                + "(enrollment_id, student_id, course_id, enrollment_date, status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, enrollment.getEnrollmentId());
            ps.setInt(2, enrollment.getStudentId());
            ps.setInt(3, enrollment.getCourseId());
            ps.setDate(4, Date.valueOf(enrollment.getEnrollmentDate()));
            ps.setString(5, enrollment.getStatus());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to save enrollment", e);
        }
    }

    public Enrollment findById(int id) {
        String sql = "SELECT * FROM enrollments WHERE enrollment_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapEnrollment(rs) : null;
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find enrollment", e);
        }
    }

    public List<Enrollment> findAll() {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollments ORDER BY enrollment_id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                enrollments.add(mapEnrollment(rs));
            }
            return enrollments;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to list enrollments", e);
        }
    }

    public boolean existsById(int id) {
        return findById(id) != null;
    }

    public void update(Enrollment enrollment) {
        String sql = "UPDATE enrollments SET student_id = ?, course_id = ?, "
                + "enrollment_date = ?, status = ? WHERE enrollment_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, enrollment.getStudentId());
            ps.setInt(2, enrollment.getCourseId());
            ps.setDate(3, Date.valueOf(enrollment.getEnrollmentDate()));
            ps.setString(4, enrollment.getStatus());
            ps.setInt(5, enrollment.getEnrollmentId());

            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("Enrollment not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update enrollment", e);
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM enrollments WHERE enrollment_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("Enrollment not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete enrollment", e);
        }
    }

    public List<Enrollment> findByStudentId(int studentId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollments WHERE student_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    enrollments.add(mapEnrollment(rs));
                }
            }

            return enrollments;

        } catch (SQLException e) {
            throw new DatabaseOperationException(
                    "Failed to find student enrollments", e);
        }
    }

    private Enrollment mapEnrollment(ResultSet rs) throws SQLException {
        return new Enrollment(
                rs.getInt("enrollment_id"),
                rs.getInt("student_id"),
                rs.getInt("course_id"),
                rs.getDate("enrollment_date").toLocalDate(),
                rs.getString("status")
        );
    }
}