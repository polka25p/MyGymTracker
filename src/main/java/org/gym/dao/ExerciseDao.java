package org.gym.dao;

import org.gym.model.Exercise;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object (DAO) interface that defines the abstract methods
 * for CRUD operations regarding {@link Exercise} entities.
 * <p>
 * This interface establishes the contract for interacting with the underlying
 * data source to manage exercise records, specifically allowing exercises to be
 * associated with specific workout sessions.
 * </p>
 *
 * @author Polina
 * @version 1.0
 * @see Exercise
 */

public interface ExerciseDao {

    /**
     * Persists a new exercise record in the database.
     *
     * @param exercise the {@link Exercise} object containing the data to be saved;
     * must not be {@code null}.
     * @throws SQLException if a database access error occurs or the operation fails.
     */
    void save(Exercise exercise) throws SQLException;

    /**
     * Retrieves all exercises associated with a specific workout visit.
     *
     * @param workoutId the unique identifier (ID) of the workout session
     * whose exercises are to be retrieved.
     * @return a {@link List} of {@link Exercise} objects belonging to the specified workout;
     * an empty list if no exercises are found for the given ID.
     * @throws SQLException if a database access error occurs or the operation fails.
     */
    List<Exercise> getExercisesByWorkoutId(int workoutId) throws SQLException;

    /**
     * Deletes a specific exercise record from the database by its unique identifier.
     *
     * @param id the unique identifier (ID) of the exercise to be removed.
     * @throws SQLException if a database access error occurs or the operation fails.
     */
    void delete(int id) throws SQLException;
}
