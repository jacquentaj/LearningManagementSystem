package learning.service;

import learning.exception.ResourceNotFoundException;
import learning.exception.ValidationException;
import learning.model.Student;
import learning.repository.StudentRepository;

import java.util.List;

public class StudentService {
    private final StudentRepository repository = new StudentRepository();

    public void create(Student student) {
        if (student == null || student.getStudentId() <= 0) {
            throw new ValidationException("Valid student and positive ID are required");
        }
        if (student.getName() == null || student.getName().isBlank()
                || student.getEmail() == null || student.getEmail().isBlank()
                || student.getDepartment() == null || student.getDepartment().isBlank()) {
            throw new ValidationException("Student fields cannot be empty");
        }
        repository.save(student);
    }

    public Student getById(int id) {
        if (id <= 0) throw new ValidationException("ID must be positive");

        Student student = repository.findById(id);
        if (student == null) {
            throw new ResourceNotFoundException("Student not found: " + id);
        }
        return student;
    }

    public List<Student> getAll() {
        return repository.findAll();
    }

    public void update(Student student) {
        if (student == null || student.getStudentId() <= 0) {
            throw new ValidationException("Valid student is required");
        }
        repository.update(student);
    }

    public void delete(int id) {
        if (id <= 0) throw new ValidationException("ID must be positive");
        repository.deleteById(id);
    }
}