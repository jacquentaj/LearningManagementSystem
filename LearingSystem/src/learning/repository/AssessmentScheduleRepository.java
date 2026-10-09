package learning.repository;

import learning.exception.DatabaseOperationException;
import learning.model.AssessmentSchedule;
import learning.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssessmentScheduleRepository {

    public void save(AssessmentSchedule schedule) {
        String sql = "INSERT INTO assessment_schedules "
                + "(schedule_id, assessment_id, schedule_date, start_time, end_time, room) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, schedule.getScheduleId());
            ps.setInt(2, schedule.getAssessmentId());
            ps.setDate(3, Date.valueOf(schedule.getScheduleDate()));
            ps.setTime(4, Time.valueOf(schedule.getStartTime()));
            ps.setTime(5, Time.valueOf(schedule.getEndTime()));
            ps.setString(6, schedule.getRoom());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to save schedule", e);
        }
    }

    public AssessmentSchedule findById(int id) {
        String sql = "SELECT * FROM assessment_schedules WHERE schedule_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapSchedule(rs) : null;
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find schedule", e);
        }
    }

    public List<AssessmentSchedule> findAll() {
        List<AssessmentSchedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM assessment_schedules ORDER BY schedule_id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                schedules.add(mapSchedule(rs));
            }
            return schedules;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to list schedules", e);
        }
    }

    public boolean existsById(int id) {
        return findById(id) != null;
    }

    public void update(AssessmentSchedule schedule) {
        String sql = "UPDATE assessment_schedules SET assessment_id = ?, "
                + "schedule_date = ?, start_time = ?, end_time = ?, room = ? "
                + "WHERE schedule_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, schedule.getAssessmentId());
            ps.setDate(2, Date.valueOf(schedule.getScheduleDate()));
            ps.setTime(3, Time.valueOf(schedule.getStartTime()));
            ps.setTime(4, Time.valueOf(schedule.getEndTime()));
            ps.setString(5, schedule.getRoom());
            ps.setInt(6, schedule.getScheduleId());

            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("Schedule not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update schedule", e);
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM assessment_schedules WHERE schedule_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("Schedule not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete schedule", e);
        }
    }

    public List<AssessmentSchedule> findByAssessmentId(int assessmentId) {
        List<AssessmentSchedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM assessment_schedules WHERE assessment_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, assessmentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    schedules.add(mapSchedule(rs));
                }
            }

            return schedules;

        } catch (SQLException e) {
            throw new DatabaseOperationException(
                    "Failed to find assessment schedules", e);
        }
    }

    private AssessmentSchedule mapSchedule(ResultSet rs) throws SQLException {
        return new AssessmentSchedule(
                rs.getInt("schedule_id"),
                rs.getInt("assessment_id"),
                rs.getDate("schedule_date").toLocalDate(),
                rs.getTime("start_time").toLocalTime(),
                rs.getTime("end_time").toLocalTime(),
                rs.getString("room")
        );
    }
}