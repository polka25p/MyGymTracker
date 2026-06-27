package org.gym.dao.impl;

import org.gym.dao.MembershipDao;
import org.gym.model.Membership;
import org.gym.util.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Implementation of the {@link MembershipDao} interface providing CRUD operations
 * for the {@link Membership} entity using JDBC.
 * <p>
 * This class handles database interactions with the {@code gym_membership} table,
 * utilizing {@link ConnectionManager} to obtain database connections. All SQL
 * operations are executed using {@link PreparedStatement} to prevent SQL injection
 * and ensure resource management via try-with-resources blocks.
 * </p>
 *
 * @author Polina
 * @version 1.0
 * @see MembershipDao
 * @see Membership
 */

public class MembershipDaoImpl implements MembershipDao {
    String sql_save = "INSERT INTO gym_membership (next_payment_date, price) VALUES (?, ?)";

    /**
     * Saves a new membership record to the database.
     *
     * @param membership the {@link Membership} object containing the data to be saved;
     * must not be {@code null}.
     * @throws SQLException if a database access error occurs or the SQL query fails.
     */

    @Override
    public void save(Membership membership) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql_save)) {
            statement.setObject(1, membership.getNextPaymentDate());
            statement.setDouble(2, membership.getPrice());
            statement.executeUpdate();
        }
    }

    String sql_get = "SELECT id, next_payment_date, price FROM gym_membership LIMIT 1";

    /**
     * Retrieves a single membership record from the database.
     * <p>
     * Due to the {@code LIMIT 1} clause, this method fetches the first available
     * membership record found in the {@code gym_membership} table.
     * </p>
     *
     * @return the {@link Membership} object populated with database data,
     * or {@code null} if no records are found.
     * @throws SQLException if a database access error occurs or the SQL query fails.
     */

    @Override
    public Membership getMembership() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_get)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Membership membership = new Membership();
                membership.setNextPaymentDate(resultSet.getObject("next_payment_date", LocalDate.class));
                membership.setPrice(resultSet.getDouble("price"));
                membership.setId(resultSet.getInt("id"));
                return membership;
            }  else {return  null;}
        }
    }

    String sql_delete = "DELETE FROM gym_membership WHERE id = ?";

    /**
     * Deletes a membership record from the database by its unique identifier.
     *
     * @param id the unique identifier (ID) of the membership to be deleted.
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
