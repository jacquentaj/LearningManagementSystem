package learning.repository;

import learning.exception.DatabaseOperationException;
import learning.model.Instructor;
import learning.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorRepository {

    public void save(Instructor instructor) {
        String sql = "INSERT INTO instructors "
                + "(instructor_id, name, email, specialization) VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, instructor.getInstructorId());
            ps.setString(2, instructor.getName());
            ps.setString(3, instructor.getEmail());
            ps.setString(4, instructor.getSpecialization());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to save instructor", e);
        }
    }

    public Instructor findById(int id) {
        String sql = "SELECT * FROM instructors WHERE instructor_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapInstructor(rs);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find instructor", e);
        }
    }

    public List<Instructor> findAll() {
        List<Instructor> instructors = new ArrayList<>();
        String sql = "SELECT * FROM instructors ORDER BY instructor_id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                instructors.add(mapInstructor(rs));
            }
            return instructors;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to list instructors", e);
        }
    }

    public boolean existsById(int id) {
        return findById(id) != null;
    }

    public void update(Instructor instructor) {
        String sql = "UPDATE instructors SET name = ?, email = ?, "
                + "specialization = ? WHERE instructor_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, instructor.getName());
            ps.setString(2, instructor.getEmail());
            ps.setString(3, instructor.getSpecialization());
            ps.setInt(4, instructor.getInstructorId());

            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("Instructor not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update instructor", e);
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM instructors WHERE instructor_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("Instructor not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete instructor", e);
        }
    }

    private Instructor mapInstructor(ResultSet rs) throws SQLException {
        return new Instructor(
                rs.getInt("instructor_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("specialization")
        );
    }
}