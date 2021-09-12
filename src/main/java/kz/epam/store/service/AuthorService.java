package kz.epam.store.service;

import kz.epam.store.entity.Author;
import kz.epam.store.exception.ServiceException;

import java.util.List;

public interface AuthorService {
    List<Author> getAll() throws ServiceException;

    void addAuthor(Author author) throws ServiceException;

    Author getById(int authorId) throws ServiceException;

    void deleteById(int authorId) throws ServiceException;
}
