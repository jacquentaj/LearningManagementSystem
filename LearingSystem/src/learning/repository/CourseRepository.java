package learning.repository;

import learning.exception.DatabaseOperationException;
import learning.model.Course;
import learning.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {

    public void save(Course course) {
        String sql = "INSERT INTO courses "
                + "(course_id, course_code, course_name, instructor_id, max_students) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, course.getCourseId());
            ps.setString(2, course.getCourseCode());
            ps.setString(3, course.getCourseName());

            if (course.getInstructorId() == 0) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, course.getInstructorId());
            }

            ps.setInt(5, course.getMaxStudents());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to save course", e);
        }
    }

    public Course findById(int id) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCourse(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find course", e);
        }
    }

    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY course_id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                courses.add(mapCourse(rs));
            }
            return courses;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to list courses", e);
        }
    }

    public boolean existsById(int id) {
        return findById(id) != null;
    }

    public void update(Course course) {
        String sql = "UPDATE courses SET course_code = ?, course_name = ?, "
                + "instructor_id = ?, max_students = ? WHERE course_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, course.getCourseCode());
            ps.setString(2, course.getCourseName());

            if (course.getInstructorId() == 0) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, course.getInstructorId());
            }

            ps.setInt(4, course.getMaxStudents());
            ps.setInt(5, course.getCourseId());

            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("Course not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update course", e);
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM courses WHERE course_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("Course not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete course", e);
        }
    }

    private Course mapCourse(ResultSet rs) throws SQLException {
        int instructorId = rs.getInt("instructor_id");
        if (rs.wasNull()) {
            instructorId = 0;
        }

        return new Course(rs.getInt("course_id"), rs.getString("course_code"),rs.getString("course_name"), instructorId,rs.getInt("max_students")
        );
    }
}