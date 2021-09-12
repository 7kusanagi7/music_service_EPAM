package kz.epam.store.dao.impl;

import kz.epam.store.dao.GenreDao;
import kz.epam.store.database.DBConnectionPool;
import kz.epam.store.entity.Genre;
import kz.epam.store.exception.DBException;
import kz.epam.store.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreDaoImpl implements GenreDao {

    //GENRE SQL
    private static final String SELECT_ALL = "SELECT * FROM genre";
    private static final String SELECT_BY_ID = "SELECT * FROM genre WHERE id = ?";
    private static final String INSERT_GENRE = "INSERT INTO genre(name) VALUES(?) RETURNING id";
    private static final String UPDATE_BY_ID = "UPDATE genre SET name = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM genre WHERE id = ?";

    //DISK GENRE SQL
    private static final String SELECT_ALL_DISK_GENRES = "SELECT * FROM disk_genre";
    private static final String INSERT_DISK_GENRES = "INSERT INTO disk_genre" +
            "(disk_id, genre_id) VALUES (?, ?)";
    private static final String SELECT_BY_GENRE_NAME = "SELECT disk_id FROM disk_genre, genre " +
            "WHERE genre.name = ? AND genre.id = disk_genre.genre_id";
    private static final String DELETE_DISK_GENRES = "DELETE FROM disk_genre WHERE disk_id = ?";

    private static final String GENRE_ID = "id";
    private static final String GENRE_NAME = "name";
    private static final String DISK_ID = "disk_id";
    private static final String GENRE_DISK_ID = "genre_id";

    @Override
    public Genre get(int id) throws DaoException {
        Genre genre = null;
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString(GENRE_NAME);
                    genre = new Genre(id, name);
                }
            }
            return genre;
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Genre> getAll() throws DaoException {
        List<Genre> genres = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(GENRE_ID);
                    String name = resultSet.getString(GENRE_NAME);
                    genres.add(new Genre(id, name));
                }
            }
            return genres;
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int save(Genre genre) throws DaoException {
        int insertedIndex = -1;
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
        PreparedStatement statement = poolConnection.getConnection().prepareStatement(INSERT_GENRE)) {
            statement.setString(1, genre.getName());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                insertedIndex = resultSet.getInt(GENRE_ID);
                genre.setId(insertedIndex);
            }
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
        return insertedIndex;
    }

    @Override
    public void update(Genre genre) throws DaoException {
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(UPDATE_BY_ID)) {
            statement.setString(1, genre.getName());
            statement.setInt(2, genre.getId());
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Genre genre) throws DaoException {
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, genre.getId());
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Map<Integer, List<Genre>> getAllDiskGenres() throws DaoException {
        Map<Integer, List<Genre>> genresMap = new HashMap<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_ALL_DISK_GENRES)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int diskId = resultSet.getInt(DISK_ID);
                    if (genresMap.get(diskId) != null) {
                        Genre genre = get(resultSet.getInt(GENRE_DISK_ID));
                        genresMap.get(diskId).add(genre);
                    } else {
                        List<Genre> genres = new ArrayList<>();
                        Genre genre = get(resultSet.getInt(GENRE_DISK_ID));
                        genres.add(genre);
                        genresMap.put(diskId, genres);
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
        return genresMap;
    }

    @Override
    public void insertDiskGenres(int diskId, List<Genre> diskGenres) throws DaoException {
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(INSERT_DISK_GENRES)) {
            for (Genre genre : diskGenres) {
                statement.setInt(1, diskId);
                statement.setInt(2, genre.getId());
                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Integer> findDisksByGenreName(String genreName) throws DaoException {
        List<Integer> periodicalIds = new ArrayList<>();
        try (DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
             PreparedStatement statement = poolConnection.getConnection().prepareStatement(SELECT_BY_GENRE_NAME)) {
            statement.setString(1, genreName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    periodicalIds.add(resultSet.getInt(DISK_ID));
                }
            }
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
        return periodicalIds;
    }

    @Override
    public void deleteDiskGenres(int diskId) throws DaoException {
        try(DBConnectionPool.PoolConnection poolConnection = DBConnectionPool.getInstance().getConnection();
            PreparedStatement statement = poolConnection.getConnection().prepareStatement(DELETE_DISK_GENRES)) {
            statement.setInt(1, diskId);
            statement.execute();
        } catch (DBException | SQLException e) {
            throw new DaoException(e);
        }
    }
}
