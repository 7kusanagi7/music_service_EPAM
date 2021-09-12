package kz.epam.store.service.impl;

import kz.epam.store.dao.GenreDao;
import kz.epam.store.dao.impl.GenreDaoImpl;
import kz.epam.store.entity.Genre;
import kz.epam.store.exception.DaoException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenreServiceImpl implements GenreService {

    private final GenreDao dao;

    public GenreServiceImpl(){
        dao = new GenreDaoImpl();
    }

    @Override
    public List<Genre> getAll() throws ServiceException {
        try {
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Integer, List<Genre>> getAllDiskGenres() throws ServiceException {
        try {
            return dao.getAllDiskGenres();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addGenre(Genre genre) throws ServiceException {
        try {
            dao.save(genre);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addDiskGenres(int diskId, List<Genre> genresList) throws ServiceException {
        try {
            List<Genre> genresWithId = new ArrayList<>();
            for(Genre genre: genresList){
                genresWithId.add(getGenreByName(genre.getName()));
            }
            dao.insertDiskGenres(diskId, genresWithId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Genre getGenreByName(String genreName) throws ServiceException {
        for(Genre genre: getAll()){
            if (genre.getName().equals(genreName))
                return genre;
        }
        return null;
    }

    @Override
    public List<Integer> getDisksByGenreName(String genreName) throws ServiceException {
        try {
            return dao.findDisksByGenreName(genreName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteGenreByName(String genreName) throws ServiceException {
        try {
            dao.delete(getGenreByName(genreName));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByDiskId(int diskId) throws ServiceException {
        try {
            dao.deleteDiskGenres(diskId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Genre> getByDiskGenres(int diskId) throws ServiceException {
        return getAllDiskGenres().get(diskId);
    }


}
