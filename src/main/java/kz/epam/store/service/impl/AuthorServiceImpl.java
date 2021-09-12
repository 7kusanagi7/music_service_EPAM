package kz.epam.store.service.impl;

import kz.epam.store.dao.AuthorDao;
import kz.epam.store.dao.impl.AuthorDaoImpl;
import kz.epam.store.entity.Author;
import kz.epam.store.exception.DaoException;
import kz.epam.store.exception.ServiceException;

import java.util.List;

public class AuthorServiceImpl implements kz.epam.store.service.AuthorService {

    private final AuthorDao dao;

    public AuthorServiceImpl(){
        dao = new AuthorDaoImpl();
    }

    @Override
    public List<Author> getAll() throws ServiceException {
        try {
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addAuthor(Author author) throws ServiceException {
        try {
            dao.save(author);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Author getById(int authorId) throws ServiceException {
        try {
            return dao.get(authorId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(int authorId) throws ServiceException {
        try {
            dao.delete(getById(authorId));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
