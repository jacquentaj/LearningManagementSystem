package learning.service;

import learning.exception.ResourceNotFoundException;
import learning.exception.ValidationException;
import learning.model.Enrollment;
import learning.repository.EnrollmentRepository;

import java.util.List;

public class EnrollmentService {

    private final EnrollmentRepository repository = new EnrollmentRepository();

    public void create(Enrollment enrollment) {
        if (enrollment == null || enrollment.getEnrollmentId() <= 0) {
            throw new ValidationException("Valid enrollment and positive ID are required");
        }

        if (enrollment.getStudentId() <= 0 || enrollment.getCourseId() <= 0) {
            throw new ValidationException("Student ID and course ID must be positive");
        }

        if (enrollment.getEnrollmentDate() == null
                || enrollment.getStatus() == null
                || enrollment.getStatus().isBlank()) {
            throw new ValidationException("Enrollment date and status are required");
        }

        repository.save(enrollment);
    }

    public Enrollment getById(int id) {
        if (id <= 0) throw new ValidationException("ID must be positive");

        Enrollment enrollment = repository.findById(id);

        if (enrollment == null) {
            throw new ResourceNotFoundException("Enrollment not found: " + id);
        }

        return enrollment;
    }

    public List<Enrollment> getAll() {
        return repository.findAll();
    }

    public List<Enrollment> findByStudentId(int studentId) {
        if (studentId <= 0) {
            throw new ValidationException("Student ID must be positive");
        }

        return repository.findByStudentId(studentId);
    }

    public void update(Enrollment enrollment) {
        if (enrollment == null || enrollment.getEnrollmentId() <= 0) {
            throw new ValidationException("Valid enrollment is required");
        }

        repository.update(enrollment);
    }

    public void delete(int id) {
        if (id <= 0) throw new ValidationException("ID must be positive");
        repository.deleteById(id);
    }
}