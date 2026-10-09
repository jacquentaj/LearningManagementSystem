package learning.repository;

import learning.exception.DatabaseOperationException;
import learning.model.Student;
import learning.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    public void save(Student s) {
        String sql = "INSERT INTO students VALUES (?, ?, ?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, s.getStudentId());
            p.setString(2, s.getName());
            p.setString(3, s.getEmail());
            p.setString(4, s.getDepartment());
            p.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Could not save student", e);
        }
    }

    public Student findById(int id) {
        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);

            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    return new Student(
                        r.getInt("student_id"),
                        r.getString("name"),
                        r.getString("email"),
                        r.getString("department")
                    );
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Could not find student", e);
        }
    }

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY student_id";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) {
                students.add(new Student(
                    r.getInt("student_id"),
                    r.getString("name"),
                    r.getString("email"),
                    r.getString("department")
                ));
            }
            return students;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Could not list students", e);
        }
    }

    public boolean existsById(int id) {
        return findById(id) != null;
    }

    public void update(Student s) {
        String sql = "UPDATE students SET name=?, email=?, department=? WHERE student_id=?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, s.getName());
            p.setString(2, s.getEmail());
            p.setString(3, s.getDepartment());
            p.setInt(4, s.getStudentId());

            if (p.executeUpdate() == 0) {
                throw new IllegalArgumentException("Student not found");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Could not update student", e);
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM students WHERE student_id=?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);

            if (p.executeUpdate() == 0) {
                throw new IllegalArgumentException("Student not found");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Could not delete student", e);
        }
    }

	public void save1(Student student) {
		// TODO Auto-generated method stub
		
	}
}