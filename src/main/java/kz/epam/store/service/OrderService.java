package kz.epam.store.service;

import kz.epam.store.entity.Order;
import kz.epam.store.exception.ServiceException;

import java.util.List;

public interface OrderService {
    void addOrder(List<Order> orders) throws ServiceException;

    List<Order> getOrdersByUserId(int userId) throws ServiceException;
}
