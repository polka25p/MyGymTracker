package org.gym.dao;

import org.gym.model.WorkoutVisit;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object (DAO) interface that defines the abstract methods
 * for CRUD operations regarding {@link WorkoutVisit} entities.
 * <p>
 * This interface decouples the business logic layer from the specific
 * database implementation, providing a standard contract for managing
 * workout visit records.
 * </p>
 *
 * @author Polina
 * @version 1.0
 * @see WorkoutVisit
 */

public interface WorkoutVisitDao {

    /**
     * Persists a new workout visit record in the data source.
     *
     * @param workout the {@link WorkoutVisit} object containing the visit details to be saved;
     * must not be {@code null}.
     * @throws SQLException if a database access error occurs or the operation fails.
     */
    void save(WorkoutVisit workout) throws SQLException;

    /**
     * Retrieves all recorded workout visits from the data source.
     *
     * @return a {@link List} of {@link WorkoutVisit} objects;
     * an empty list if no records are found.
     * @throws SQLException if a database access error occurs or the operation fails.
     */
    List<WorkoutVisit> getAllWorkouts() throws SQLException;

    /**
     * Deletes a specific workout visit record from the data source by its unique identifier.
     *
     * @param id the unique identifier (ID) of the workout visit to be removed.
     * @throws SQLException if a database access error occurs or the operation fails.
     */
    void delete(int id) throws SQLException;

    /**
     * Updates the trainer payment status to paid for a specific workout visit.
     *
     * @param id the unique identifier of the workout visit
     * @throws SQLException if a database access error occurs
     */
    void updateTrainerPaidStatus(int id) throws SQLException;
}
