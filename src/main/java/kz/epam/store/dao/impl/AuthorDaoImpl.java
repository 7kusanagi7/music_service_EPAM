package kz.epam.store.dao.impl;

import kz.epam.store.dao.AuthorDao;
import kz.epam.store.database.DBConnectionPool;
import kz.epam.store.entity.Author;
import kz.epam.store.exception.DBException;
import kz.epam.store.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoImpl implements AuthorDao {
    private static final String SELECT_ALL = "SELECT * FROM author";
    private static final String SELECT_BY_ID = "SELECT * FROM author WHERE id = ?";
    private static final String INSERT_AUTHOR = "INSERT INTO author(full_name) VALUES(?) RETURNING id";
    private static final String UPDATE_BY_ID = "UPDATE author SET full_name = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM author WHERE id = ?";

    private static final String AUTHOR_ID = "id";
    private static final String AUTHOR_FULLNAME = "full_name";

    @Override
    public Author get(int id) throws DaoException {
        Author author = null;
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String fullName = resultSet.getString(AUTHOR_FULLNAME);
                    author = new Author(id, fullName);
                }
            }
            return author;
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Author> getAll() throws DaoException {
        List<Author> authors = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(AUTHOR_ID);
                    String name = resultSet.getString(AUTHOR_FULLNAME);
                    authors.add(new Author(id, name));
                }
            }
            return authors;
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int save(Author author) throws DaoException {
        int insertedIndex = -1;
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(INSERT_AUTHOR)) {
            statement.setString(1, author.getFullName());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                insertedIndex = resultSet.getInt(AUTHOR_ID);
                author.setId(insertedIndex);
            }
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
        return insertedIndex;
    }

    @Override
    public void update(Author author) throws DaoException {
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(UPDATE_BY_ID)) {
            statement.setString(1, author.getFullName());
            statement.setInt(2, author.getId());
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Author author) throws DaoException {
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, author.getId());
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }
}
