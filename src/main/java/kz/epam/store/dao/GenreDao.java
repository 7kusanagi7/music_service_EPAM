package kz.epam.store.dao;

import kz.epam.store.entity.Genre;
import kz.epam.store.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface GenreDao extends Dao<Genre> {

    Map<Integer, List<Genre>> getAllDiskGenres() throws DaoException;
    void insertDiskGenres(int diskId, List<Genre> diskGenres) throws DaoException;

    List<Integer> findDisksByGenreName(String genreName) throws DaoException;

    void deleteDiskGenres(int diskId) throws DaoException;
}
