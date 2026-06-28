package org.gym.dao.impl;

import org.gym.dao.ExerciseDao;
import org.gym.model.Exercise;
import org.gym.model.WorkoutVisit;
import org.gym.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link ExerciseDao} interface providing CRUD operations
 * for the {@link Exercise} entity using JDBC.
 * <p>
 * This class handles database interactions with the {@code exercises} table,
 * utilizing {@link ConnectionManager} to manage database connections. It ensures proper
 * resource management via try-with-resources blocks and prevents SQL injection
 * using {@link PreparedStatement}.
 * </p>
 *
 * @author Polina
 * @version 1.0
 * @see ExerciseDao
 * @see Exercise
 */

public class ExerciseDaoImpl implements ExerciseDao {
    String sql_save = "INSERT INTO exercises (workout_id, name, weight, sets_reps) VALUES (?, ?, ?, ?)";

    /**
     * Saves a new exercise record to the database.
     *
     * @param exercise the {@link Exercise} object containing the data to be saved;
     * must not be {@code null}.
     * @throws SQLException if a database access error occurs or the SQL query fails.
     */

    @Override
    public void save(Exercise exercise) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_save)) {
            statement.setInt(1, exercise.getWorkoutId());
            statement.setString(2, exercise.getName());
            statement.setDouble(3, exercise.getWeight());
            statement.setString(4, exercise.getSetsReps());
            statement.executeUpdate();
        }
    }

    String sql_get = "SELECT id, workout_id, name, weight, sets_reps FROM exercises WHERE workout_id = ? ORDER BY id";

    /**
     * Retrieves all exercise records associated with a specific workout ID from the database.
     * <p>
     * The returned list is sorted by the exercise {@code id} in ascending order,
     * ensuring the exercises are listed in the order they were created or logged.
     * </p>
     *
     * @param workoutId the unique identifier (ID) of the workout session.
     * @return a {@link List} of {@link Exercise} objects belonging to the workout;
     * an empty list if no exercises are found.
     * @throws SQLException if a database access error occurs or the SQL query fails.
     */

    @Override
    public List<Exercise> getExercisesByWorkoutId(int workoutId) throws SQLException {
        List<Exercise> exercises = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_get)) {
            statement.setInt(1, workoutId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setWorkoutId(resultSet.getInt("workout_id"));
                exercise.setName(resultSet.getString("name"));
                exercise.setWeight(resultSet.getDouble("weight"));
                exercise.setSetsReps(resultSet.getString("sets_reps"));
                exercises.add(exercise);
            }
        }
        return exercises;
    }

    String sql_delete = "DELETE FROM exercises WHERE id = ?";

    /**
     * Deletes a specific exercise record from the database by its unique identifier.
     *
     * @param id the unique identifier (ID) of the exercise to be deleted.
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
}
