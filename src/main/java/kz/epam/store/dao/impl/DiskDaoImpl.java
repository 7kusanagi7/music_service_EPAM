package kz.epam.store.dao.impl;

import kz.epam.store.dao.DiskDao;
import kz.epam.store.database.DBConnectionPool;
import kz.epam.store.entity.Disk;
import kz.epam.store.exception.DBException;
import kz.epam.store.exception.DaoException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiskDaoImpl implements DiskDao {

    private static final String SELECT_ALL = "SELECT * FROM disk";
    private static final String SELECT_BY_ID = "SELECT * FROM disk WHERE id = ?";
    private static final String INSERT_DISK = "INSERT INTO disk(title, price, author_id, cover_image, description) " +
            "VALUES(?, ?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_BY_ID = "UPDATE disk SET title = ?, price = ?, author_id = ?, " +
            "cover_image = ?, description = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM disk WHERE id = ?";

    private static final String DISK_ID = "id";
    private static final String DISK_TITLE = "title";
    private static final String DISK_PRICE = "price";
    private static final String DISK_AUTHOR_ID = "author_id";
    private static final String DISK_COVER_IMAGE = "cover_image";
    private static final String DISK_DESCRIPTION = "description";

    @Override
    public Disk get(int id) throws DaoException {
        Disk disk = null;
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    disk = createDisk(resultSet);
                }
            }
            return disk;
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Disk> getAll() throws DaoException {
        List<Disk> disks = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    disks.add(createDisk(resultSet));
                }
            }
            return disks;
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int save(Disk disk) throws DaoException {
        int insertedIndex = -1;
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(INSERT_DISK)) {
            setStatementParams(statement, disk);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                insertedIndex = resultSet.getInt(DISK_ID);
                disk.setId(insertedIndex);
            }
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
        return insertedIndex;
    }

    @Override
    public void update(Disk disk) throws DaoException {
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(UPDATE_BY_ID)) {
            setStatementParams(statement, disk);
            statement.setInt(6, disk.getId());
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Disk disk) throws DaoException {
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, disk.getId());
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    private Disk createDisk(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(DISK_ID);
        String title = resultSet.getString(DISK_TITLE);
        BigDecimal price = resultSet.getBigDecimal(DISK_PRICE);
        int authorId = resultSet.getInt(DISK_AUTHOR_ID);
        String coverImage = resultSet.getString(DISK_COVER_IMAGE);
        String description = resultSet.getString(DISK_DESCRIPTION);

        Disk disk = new Disk();
        disk.setId(id).setTitle(title).setPrice(price).setAuthorId(authorId)
                .setCoverImage(coverImage).setDescription(description);
        return disk;
    }

    private void setStatementParams(PreparedStatement statement, Disk disk) throws SQLException {
        statement.setString(1, disk.getTitle());
        statement.setBigDecimal(2, disk.getPrice());
        statement.setInt(3, disk.getAuthorId());
        statement.setString(4, disk.getCoverImage());
        statement.setString(5, disk.getDescription());
    }
}
