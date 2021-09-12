package kz.epam.store.dao.impl;

import kz.epam.store.dao.UserDao;
import kz.epam.store.database.DBConnectionPool;
import kz.epam.store.entity.User;
import kz.epam.store.exception.DBException;
import kz.epam.store.exception.DaoException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final String SELECT_ALL = "SELECT * FROM users";
    private static final String SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String INSERT_USER = "INSERT INTO users(username, password, is_admin, " +
            "email, first_name, last_name, city, address, postal_index, loan, is_banned) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BY_ID = "UPDATE users SET username = ?, password = ?, is_admin = ?," +
            "email = ?, first_name = ?, last_name = ?, city = ?, address = ?, postal_index = ?, loan = ?, is_banned = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?";

    private static final String USER_ID = "id";
    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "password";
    private static final String USER_ADMIN = "is_admin";
    private static final String USER_EMAIL = "email";
    private static final String USER_FIRSTNAME = "first_name";
    private static final String USER_LASTNAME = "last_name";
    private static final String USER_CITY = "city";
    private static final String USER_ADDRESS = "address";
    private static final String USER_POSTAL_INDEX = "postal_index";
    private static final String USER_LOAN = "loan";
    private static final String USER_BANNED = "is_banned";


    @Override
    public User get(int id) throws DaoException {
        User user = null;
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
            return user;
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> getAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(createUser(resultSet));
                }
            }
            return users;
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int save(User user) throws DaoException {
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(INSERT_USER)) {
            setStatementParams(statement, user);

            return statement.executeUpdate();
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(User user) throws DaoException {
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(UPDATE_BY_ID)) {
            setStatementParams(statement, user);
            statement.setInt(12, user.getId());
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(User user) throws DaoException {
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, user.getId());
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User getByUsername(String username) throws DaoException {
        User user = null;
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_USERNAME)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
            return user;
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User getByEmail(String email) throws DaoException {
        User user = null;
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
            return user;
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(USER_ID);
        String username = resultSet.getString(USER_USERNAME);
        String password = resultSet.getString(USER_PASSWORD);
        boolean isAdmin = resultSet.getBoolean(USER_ADMIN);
        String email = resultSet.getString(USER_EMAIL);
        String firstName = resultSet.getString(USER_FIRSTNAME);
        String lastName = resultSet.getString(USER_LASTNAME);
        String city = resultSet.getString(USER_CITY);
        String address = resultSet.getString(USER_ADDRESS);
        String postalIndex = resultSet.getString(USER_POSTAL_INDEX);
        BigDecimal loan = resultSet.getBigDecimal(USER_LOAN);
        boolean isBanned = resultSet.getBoolean(USER_BANNED);

        User user = new User();
        user.setId(id).setUsername(username).setPassword(password).setAdmin(isAdmin).setEmail(email)
                .setFirstName(firstName).setLastName(lastName).setCity(city).setAddress(address)
                .setPostalIndex(postalIndex).setLoan(loan).setBanned(isBanned);
        return user;
    }

    private void setStatementParams(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setBoolean(3, user.isAdmin());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getFirstName());
        statement.setString(6, user.getLastName());
        statement.setString(7, user.getCity());
        statement.setString(8, user.getAddress());
        statement.setString(9, user.getPostalIndex());
        statement.setBigDecimal(10, user.getLoan());
        statement.setBoolean(11, user.isBanned());
    }
}
