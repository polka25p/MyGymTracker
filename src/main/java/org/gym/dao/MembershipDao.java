package org.gym.dao;

import org.gym.model.Membership;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) interface that defines the abstract methods
 * for managing {@link Membership} entities.
 *
 * @author Polina
 * @version 1.0
 * @see Membership
 */
public interface MembershipDao {

    /**
     * Persists a new membership record in the database.
     *
     * @param membership the {@link Membership} object containing the data to be saved;
     * must not be {@code null}.
     * @throws SQLException if a database access error occurs or the SQL query fails.
     */
    void save(Membership membership) throws SQLException;

    /**
     * Retrieves a single membership record from the database.
     *
     * @return the {@link Membership} object found, or {@code null} if no membership exists.
     * @throws SQLException if a database access error occurs.
     */
    Membership getMembership() throws SQLException;

    /**
     * Deletes a specific membership from the database by its unique identifier.
     * * @param id the unique identifier (ID) of the membership to delete.
     * @throws SQLException if a database access error occurs.
     */
    void delete(int id) throws SQLException;
}