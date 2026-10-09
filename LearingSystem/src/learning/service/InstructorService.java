package learning.service;

import learning.exception.ResourceNotFoundException;
import learning.exception.ValidationException;
import learning.model.Instructor;
import learning.repository.InstructorRepository;

import java.util.List;

public class InstructorService {

    private final InstructorRepository repository = new InstructorRepository();

    public void create(Instructor instructor) {
        if (instructor == null || instructor.getInstructorId() <= 0) {
            throw new ValidationException("Valid instructor and positive ID are required");
        }

        if (instructor.getName() == null || instructor.getName().isBlank()
                || instructor.getEmail() == null || instructor.getEmail().isBlank()
                || instructor.getSpecialization() == null
                || instructor.getSpecialization().isBlank()) {
            throw new ValidationException("Instructor fields cannot be empty");
        }

        repository.save(instructor);
    }

    public Instructor getById(int id) {
        if (id <= 0) throw new ValidationException("ID must be positive");

        Instructor instructor = repository.findById(id);

        if (instructor == null) {
            throw new ResourceNotFoundException("Instructor not found: " + id);
        }

        return instructor;
    }

    public List<Instructor> getAll() {
        return repository.findAll();
    }

    public void update(Instructor instructor) {
        if (instructor == null || instructor.getInstructorId() <= 0) {
            throw new ValidationException("Valid instructor is required");
        }

        repository.update(instructor);
    }

    public void delete(int id) {
        if (id <= 0) throw new ValidationException("ID must be positive");
        repository.deleteById(id);
    }
}