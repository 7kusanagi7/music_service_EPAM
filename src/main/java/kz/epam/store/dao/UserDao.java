package kz.epam.store.dao;

import kz.epam.store.entity.User;
import kz.epam.store.exception.DaoException;

public interface UserDao extends Dao<User> {
   User getByUsername(String username) throws DaoException;

    User getByEmail(String email) throws DaoException;
}
