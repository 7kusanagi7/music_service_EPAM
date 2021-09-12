package kz.epam.store.service;

import kz.epam.store.entity.Genre;
import kz.epam.store.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface GenreService {
    List<Genre> getAll() throws ServiceException;

    Map<Integer, List<Genre>> getAllDiskGenres() throws ServiceException;

    void addGenre(Genre genre) throws ServiceException;

    void addDiskGenres(int diskId, List<Genre> genresList) throws ServiceException;

    Genre getGenreByName(String genreName) throws ServiceException;

    List<Integer> getDisksByGenreName(String genreName) throws ServiceException;

    void deleteGenreByName(String genreName) throws ServiceException;

    void deleteByDiskId(int diskId) throws ServiceException;

    List<Genre> getByDiskGenres(int diskId) throws ServiceException;
}
