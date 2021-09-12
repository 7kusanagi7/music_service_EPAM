package kz.epam.store.service.impl;

import kz.epam.store.dao.UserDao;
import kz.epam.store.dao.impl.UserDaoImpl;
import kz.epam.store.entity.User;
import kz.epam.store.exception.DaoException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao dao;

    public UserServiceImpl(){
        dao = new UserDaoImpl();
    }

    @Override
    public boolean register(User user) throws ServiceException {
        try {
            if(dao.getByUsername(user.getUsername()) != null)
                return false;

            user.setLoan(BigDecimal.valueOf(0));
            user.setPassword(encodePassword(user.getPassword()));
            return dao.save(user) > 0; //successful when returned id is positive
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User logIn(String username, String password) throws ServiceException {
        try {
            User user = dao.getByUsername(username);
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
            return null;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserByUsername(String username) throws ServiceException {
        try {
            return dao.getByUsername(username);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) throws ServiceException {
        try {
            return dao.getByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        try {
            dao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserById(int userId) throws ServiceException {
        try {
            return dao.get(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUserPassword(User user, String newPassword) throws ServiceException {
        user.setPassword(encodePassword(newPassword));
        updateUser(user);
    }

    private String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
