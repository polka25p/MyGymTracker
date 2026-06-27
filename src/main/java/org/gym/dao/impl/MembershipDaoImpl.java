package org.gym.dao.impl;

import org.gym.dao.MembershipDao;
import org.gym.model.Membership;
import org.gym.util.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MembershipDaoImpl implements MembershipDao {
    String sql_save = "INSERT INTO gym_membership (next_payment_date, price) VALUES (?, ?)";

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

    @Override
    public void delete(int id) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_delete)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
