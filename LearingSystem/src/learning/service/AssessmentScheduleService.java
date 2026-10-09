package learning.service;

import learning.exception.ResourceNotFoundException;
import learning.exception.ValidationException;
import learning.model.AssessmentSchedule;
import learning.repository.AssessmentScheduleRepository;

import java.util.List;

public class AssessmentScheduleService {

    private final AssessmentScheduleRepository repository =
            new AssessmentScheduleRepository();

    public void create(AssessmentSchedule schedule) {
        if (schedule == null || schedule.getScheduleId() <= 0) {
            throw new ValidationException("Valid schedule and positive ID are required");
        }

        if (schedule.getAssessmentId() <= 0) {
            throw new ValidationException("Assessment ID must be positive");
        }

        if (schedule.getScheduleDate() == null
                || schedule.getStartTime() == null
                || schedule.getEndTime() == null) {
            throw new ValidationException("Date, start time and end time are required");
        }

        if (!schedule.getEndTime().isAfter(schedule.getStartTime())) {
            throw new ValidationException("End time must be after start time");
        }

        if (schedule.getRoom() == null || schedule.getRoom().isBlank()) {
            throw new ValidationException("Room is required");
        }

        repository.save(schedule);
    }

    public AssessmentSchedule getById(int id) {
        if (id <= 0) throw new ValidationException("ID must be positive");

        AssessmentSchedule schedule = repository.findById(id);

        if (schedule == null) {
            throw new ResourceNotFoundException("Schedule not found: " + id);
        }

        return schedule;
    }

    public List<AssessmentSchedule> getAll() {
        return repository.findAll();
    }

    public List<AssessmentSchedule> findByAssessmentId(int assessmentId) {
        if (assessmentId <= 0) {
            throw new ValidationException("Assessment ID must be positive");
        }

        return repository.findByAssessmentId(assessmentId);
    }

    public void update(AssessmentSchedule schedule) {
        if (schedule == null || schedule.getScheduleId() <= 0) {
            throw new ValidationException("Valid schedule is required");
        }

        if (schedule.getStartTime() == null || schedule.getEndTime() == null
                || !schedule.getEndTime().isAfter(schedule.getStartTime())) {
            throw new ValidationException("End time must be after start time");
        }

        repository.update(schedule);
    }

    public void delete(int id) {
        if (id <= 0) throw new ValidationException("ID must be positive");
        repository.deleteById(id);
    }
}