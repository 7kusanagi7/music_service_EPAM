package kz.epam.store.service;

import kz.epam.store.entity.User;
import kz.epam.store.exception.ServiceException;

import java.util.List;

public interface UserService {

    boolean register(User user) throws ServiceException;

    User logIn(String username, String password) throws ServiceException;

    User getUserByUsername(String username) throws ServiceException;

    User getUserByEmail(String email) throws ServiceException;

    void updateUser(User user) throws ServiceException;

    User getUserById(int userId) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;

    void updateUserPassword(User user, String newPassword) throws ServiceException;
}
