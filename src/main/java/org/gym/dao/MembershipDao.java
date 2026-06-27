package org.gym.dao;

import org.gym.model.Membership;
import java.sql.SQLException;

/**
 * Data Access Object interface for managing {@link Membership} entities.
 */
public interface MembershipDao {

    /**
     * Saves a new membership or updates an existing one in the database.
     *
     * @param membership the membership object containing the data to be saved
     * @throws SQLException if an error occurs while working with the PostgreSQL database
     */
    void save(Membership membership) throws SQLException;

    /**
     * Retrieves the current active gym membership from the database.
     * @return the {@link Membership} object, or null if no membership is found
     * @throws SQLException if a database access error occurs
     */
    Membership getMembership() throws SQLException;

    /**
     * Deletes a specific membership from the database by its unique identifier.
     * @param id the unique identifier of the membership to delete
     * @throws SQLException if a database access error occurs
     */
    void delete(int id) throws SQLException;
}