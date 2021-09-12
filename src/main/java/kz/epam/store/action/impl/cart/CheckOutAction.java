package kz.epam.store.action.impl.cart;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.action.RoutingType;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.Disk;
import kz.epam.store.entity.Order;
import kz.epam.store.entity.User;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.OrderService;
import kz.epam.store.service.UserService;
import kz.epam.store.service.impl.OrderServiceImpl;
import kz.epam.store.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class CheckOutAction implements Action {

    private static final OrderService orderService = new OrderServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(RoutingType.REDIRECT, requestContent.getReferer());

            List<Order> orders = createOrderList(requestContent);
            orderService.addOrder(orders);
            if (requestContent.getRequestParameter("credit") != null) {
                User user = (User) requestContent.getSessionAttribute(ParameterConstant.USER);
                user.setLoan(user.getLoan().subtract((BigDecimal) requestContent.getSessionAttribute(ParameterConstant.TOTAL_PRICE)));
                userService.updateUser(user);
            }

            actionResult.putSessionAttribute(ParameterConstant.CART_PRODUCTS, new LinkedHashSet<Disk>());
            actionResult.putSessionAttribute(ParameterConstant.TOTAL_PRICE, new BigDecimal(0));
            actionResult.putSessionAttribute(ParameterConstant.QUANTITIES, new HashMap<Integer, Integer>());
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }

    private List<Order> createOrderList(RequestContent requestContent) {
        List<Order> orders = new ArrayList<>();
        User user = (User) requestContent.getSessionAttribute(ParameterConstant.USER);
        Order order;
        for (Disk disk : (Set<Disk>) requestContent.getSessionAttribute(ParameterConstant.CART_PRODUCTS)) {
            order = createOrder(disk, requestContent, user);
            orders.add(order);
        }
        return orders;
    }

    private Order createOrder(Disk disk, RequestContent requestContent, User user) {
        Order order = new Order();
        order.setUserId(user.getId());
        order.setDiskId(disk.getId());

        Date startDate = Date.valueOf(LocalDate.now().withDayOfMonth(1).plusMonths(1));
        order.setStartDate(startDate);

        Map<Integer, Integer> quantities = (Map<Integer, Integer>) requestContent.getSessionAttribute(ParameterConstant.QUANTITIES);
        int quantity = quantities.get(disk.getId());
        order.setEndDate(Date.valueOf(startDate.toLocalDate().plusMonths(quantity)));
        order.setPrice(disk.getPrice().multiply(BigDecimal.valueOf(quantity)));

        return order;
    }
}
