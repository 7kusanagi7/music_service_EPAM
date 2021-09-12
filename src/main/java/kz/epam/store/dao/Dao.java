package kz.epam.store.dao;

import kz.epam.store.exception.DaoException;

import java.util.List;

public interface Dao<T> {

    T get(int id) throws DaoException;

    List<T> getAll() throws DaoException;

    int save(T t) throws DaoException;

    void update(T t) throws DaoException;

    void delete(T t) throws DaoException;
}
