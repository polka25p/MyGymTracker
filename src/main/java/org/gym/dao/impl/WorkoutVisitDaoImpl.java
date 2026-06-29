package org.gym.dao.impl;

import org.gym.dao.WorkoutVisitDao;
import org.gym.model.WorkoutVisit;
import org.gym.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link WorkoutVisitDao} interface providing CRUD operations
 * for the {@link WorkoutVisit} entity using JDBC.
 * <p>
 * This class handles database interactions with the {@code workouts} table,
 * utilizing {@link ConnectionManager} to manage database connections. It ensures proper
 * resource management via try-with-resources blocks and prevents SQL injection
 * using {@link PreparedStatement}.
 * </p>
 *
 * @author Polina
 * @version 1.0
 * @see WorkoutVisitDao
 * @see WorkoutVisit
 */

public class WorkoutVisitDaoImpl implements WorkoutVisitDao {
    String sql_save = "INSERT INTO workouts (visit_date, notes, with_trainer, trainer_paid) VALUES (?, ?, ?, ?)";

    /**
     * Saves a new workout visit record to the database.
     *
     * @param workout the {@link WorkoutVisit} object containing the visit details to be saved;
     * must not be {@code null}.
     * @throws SQLException if a database access error occurs or the SQL query fails.
     */

    @Override
    public void save(WorkoutVisit workout) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_save)) {
            statement.setObject(1, workout.getVisitDate());
            statement.setString(2, workout.getNotes());
            statement.setBoolean(3, workout.isWithTrainer());
            statement.setBoolean(4, workout.isTrainerPaid());
            statement.executeUpdate();
        }
    }

    String sql_get = "SELECT id, visit_date, notes, with_trainer, trainer_paid FROM workouts ORDER BY visit_date DESC";

    /**
     * Retrieves all workout visit records from the database.
     * <p>
     * The returned list is sorted by {@code visit_date} in descending order
     * (most recent visits first), as defined by the underlying SQL query.
     * </p>
     *
     * @return a {@link List} of {@link WorkoutVisit} objects;
     * an empty list if no records are found in the database.
     * @throws SQLException if a database access error occurs or the SQL query fails.
     */

    @Override
    public List<WorkoutVisit> getAllWorkouts() throws SQLException {
        List<WorkoutVisit> workouts = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_get)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                WorkoutVisit workoutVisit = new WorkoutVisit();
                workoutVisit.setId(resultSet.getInt("id"));
                workoutVisit.setNotes(resultSet.getString("notes"));
                workoutVisit.setWithTrainer(resultSet.getBoolean("with_trainer"));
                workoutVisit.setTrainerPaid(resultSet.getBoolean("trainer_paid"));
                workoutVisit.setVisitDate(resultSet.getObject("visit_date", LocalDate.class));
                workouts.add(workoutVisit);
            }
        }
        return workouts;
    }

    String sql_delete = "DELETE FROM workouts WHERE id = ?";

    /**
     * Deletes a specific workout visit record from the database by its unique identifier.
     *
     * @param id the unique identifier (ID) of the workout visit to be deleted.
     * @throws SQLException if a database access error occurs or the SQL query fails.
     */

    @Override
    public void delete(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_delete)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    String sql_update = "UPDATE workouts SET trainer_paid = true WHERE id = ?;";

    /**
     * Updates the trainer's payment status for a specific workout visit to {@code true}.
     * <p>
     * This method executes an SQL {@code UPDATE} statement that modifies the
     * {@code trainer_paid} flag for the record that matches the provided unique identifier.
     * </p>
     *
     * @param id the unique identifier (ID) of the workout visit to be updated.
     * @throws SQLException if a database access error occurs or the SQL query fails.
     */

    @Override
    public void updateTrainerPaidStatus(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql_update)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
