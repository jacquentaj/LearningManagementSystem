package learning.service;

import learning.exception.ResourceNotFoundException;
import learning.exception.ValidationException;
import learning.model.Course;
import learning.repository.CourseRepository;

import java.util.List;

public class CourseService {

    private final CourseRepository repository = new CourseRepository();

    public void create(Course course) {
        if (course == null || course.getCourseId() <= 0) {
            throw new ValidationException("Valid course and positive ID are required");
        }

        if (course.getCourseCode() == null || course.getCourseCode().isBlank()
                || course.getCourseName() == null || course.getCourseName().isBlank()) {
            throw new ValidationException("Course code and name cannot be empty");
        }

        if (course.getMaxStudents() <= 0) {
            throw new ValidationException("Maximum students must be positive");
        }

        repository.save(course);
    }

    public Course getById(int id) {
        if (id <= 0) throw new ValidationException("ID must be positive");

        Course course = repository.findById(id);

        if (course == null) {
            throw new ResourceNotFoundException("Course not found: " + id);
        }

        return course;
    }

    public List<Course> getAll() {
        return repository.findAll();
    }

    public void update(Course course) {
        if (course == null || course.getCourseId() <= 0) {
            throw new ValidationException("Valid course is required");
        }

        repository.update(course);
    }

    public void delete(int id) {
        if (id <= 0) throw new ValidationException("ID must be positive");
        repository.deleteById(id);
    }
}