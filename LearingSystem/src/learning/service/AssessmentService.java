package learning.service;

import learning.exception.ResourceNotFoundException;
import learning.exception.ValidationException;
import learning.model.Assessment;
import learning.repository.AssessmentRepository;

import java.util.List;

public class AssessmentService {

    private final AssessmentRepository repository = new AssessmentRepository();

    public void create(Assessment assessment) {
        if (assessment == null || assessment.getAssessmentId() <= 0) {
            throw new ValidationException("Valid assessment and positive ID are required");
        }

        if (assessment.getCourseId() <= 0) {
            throw new ValidationException("Course ID must be positive");
        }

        if (assessment.getTitle() == null || assessment.getTitle().isBlank()
                || assessment.getType() == null || assessment.getType().isBlank()) {
            throw new ValidationException("Assessment title and type are required");
        }

        if (assessment.getMaxMarks() == null
                || assessment.getMaxMarks().signum() <= 0) {
            throw new ValidationException("Maximum marks must be positive");
        }

        if (assessment.getDurationMinutes() <= 0) {
            throw new ValidationException("Duration must be positive");
        }

        repository.save(assessment);
    }

    public Assessment getById(int id) {
        if (id <= 0) throw new ValidationException("ID must be positive");

        Assessment assessment = repository.findById(id);

        if (assessment == null) {
            throw new ResourceNotFoundException("Assessment not found: " + id);
        }

        return assessment;
    }

    public List<Assessment> getAll() {
        return repository.findAll();
    }

    public List<Assessment> findByCourseId(int courseId) {
        if (courseId <= 0) {
            throw new ValidationException("Course ID must be positive");
        }

        return repository.findByCourseId(courseId);
    }

    public List<Assessment> findByType(String type) {
        if (type == null || type.isBlank()) {
            throw new ValidationException("Assessment type is required");
        }

        return repository.findByType(type);
    }

    public void update(Assessment assessment) {
        if (assessment == null || assessment.getAssessmentId() <= 0) {
            throw new ValidationException("Valid assessment is required");
        }

        repository.update(assessment);
    }

    public void delete(int id) {
        if (id <= 0) throw new ValidationException("ID must be positive");
        repository.deleteById(id);
    }
}