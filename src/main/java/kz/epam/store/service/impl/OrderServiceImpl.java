package kz.epam.store.service.impl;

import kz.epam.store.dao.OrderDao;
import kz.epam.store.dao.impl.OrderDaoImpl;
import kz.epam.store.entity.Order;
import kz.epam.store.exception.DaoException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.OrderService;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderDao dao = new OrderDaoImpl();

    @Override
    public void addOrder(List<Order> orders) throws ServiceException {
        for(Order order: orders) {
            try {
                dao.save(order);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) throws ServiceException {
        try {
            List<Order> orders = new ArrayList<>();
            for(Order order: dao.getAll()){
                if(order.getUserId() == userId)
                    orders.add(order);
            }
            return orders;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
