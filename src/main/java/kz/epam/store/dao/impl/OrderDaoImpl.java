package kz.epam.store.dao.impl;

import kz.epam.store.dao.OrderDao;
import kz.epam.store.database.DBConnectionPool;
import kz.epam.store.entity.Order;
import kz.epam.store.exception.DBException;
import kz.epam.store.exception.DaoException;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private static final String SELECT_ALL = "SELECT * FROM orders";
    private static final String SELECT_BY_ID = "SELECT * FROM orders WHERE id = ?";
    private static final String INSERT_ORDER = "INSERT INTO orders(user_id, price, disk_id, start_date, end_date) " +
            "VALUES(?, ?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_BY_ID = "UPDATE orders SET user_id = ?, price = ?, disk_id = ?, " +
            "start_date = ?, end_date = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM orders WHERE id = ?";

    private static final String ORDER_ID = "id";
    private static final String ORDER_USER_ID = "user_id";
    private static final String ORDER_PRICE = "price";
    private static final String ORDER_DISK_ID = "disk_id";
    private static final String ORDER_START_DATE = "start_date";
    private static final String ORDER_END_DATE = "end_date";

    @Override
    public Order get(int id) throws DaoException {
        Order order = null;
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    order = createOrder(resultSet);
                }
            }
            return order;
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> getAll() throws DaoException {
        List<Order> order = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    order.add(createOrder(resultSet));
                }
            }
            return order;
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int save(Order order) throws DaoException {
        int insertedIndex = -1;
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(INSERT_ORDER)) {
            setStatementParams(statement, order);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                insertedIndex = resultSet.getInt(ORDER_ID);
                order.setId(insertedIndex);
            }
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
        return insertedIndex;
    }

    @Override
    public void update(Order order) throws DaoException {
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(UPDATE_BY_ID)) {
            setStatementParams(statement, order);
            statement.setInt(6, order.getId());
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Order order) throws DaoException {
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, order.getId());
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    private Order createOrder(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ORDER_ID);
        int userId = resultSet.getInt(ORDER_USER_ID);
        BigDecimal price = resultSet.getBigDecimal(ORDER_PRICE);
        int diskId = resultSet.getInt(ORDER_DISK_ID);
        Date startDate = resultSet.getDate(ORDER_START_DATE);
        Date endDate = resultSet.getDate(ORDER_END_DATE);

        Order order = new Order();
        order.setId(id).setUserId(userId).setPrice(price).setDiskId(diskId).setStartDate(startDate).setEndDate(endDate);
        return order;
    }

    private void setStatementParams(PreparedStatement statement, Order order) throws SQLException {
        statement.setInt(1, order.getUserId());
        statement.setBigDecimal(2, order.getPrice());
        statement.setInt(3, order.getDiskId());
        statement.setDate(4, order.getStartDate());
        statement.setDate(5, order.getEndDate());
    }
}
