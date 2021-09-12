package kz.epam.store.action.impl.user;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.config.JspPath;
import kz.epam.store.config.MessageConstant;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.Disk;
import kz.epam.store.entity.Order;
import kz.epam.store.entity.User;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.DiskService;
import kz.epam.store.service.OrderService;
import kz.epam.store.service.UserService;
import kz.epam.store.service.impl.DiskServiceImpl;
import kz.epam.store.service.impl.OrderServiceImpl;
import kz.epam.store.service.impl.UserServiceImpl;
import kz.epam.store.util.MessageManager;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowProfileAction implements Action {

    private static final OrderService orderService = new OrderServiceImpl();
    private static final DiskService diskService = new DiskServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(JspPath.PROFILE_PAGE);
            int userId;
            if (requestContent.getRequestParameter(ParameterConstant.USER_ID) != null) {
                userId = Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.USER_ID));
            } else {
                userId = ((User) requestContent.getSessionAttribute(ParameterConstant.USER)).getId();
            }
            User user = userService.getUserById(userId);
            List<Order> userOrders = orderService.getOrdersByUserId(userId);
            Map<Integer, Disk> userDisks = new HashMap<>();
            for (Order order : userOrders) {
                userDisks.put(order.getDiskId(), diskService.getDiskById(order.getDiskId()));
            }
            actionResult.putRequestAttribute(ParameterConstant.USER, user);
            actionResult.putRequestAttribute(ParameterConstant.ORDERS, userOrders);
            actionResult.putRequestAttribute(ParameterConstant.DISKS, userDisks);
            actionResult.putRequestAttribute(ParameterConstant.STATUSES, defineOrderStatus(userOrders, requestContent));
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }

    private Map<Integer, String> defineOrderStatus(List<Order> orders, RequestContent requestContent) {
        Map<Integer, String> statuses = new HashMap<>();
        for (Order order : orders) {
            LocalDate today = LocalDate.now();
            LocalDate startDate = order.getStartDate().toLocalDate();
            LocalDate endDate = order.getEndDate().toLocalDate();
            String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
            if (startDate.isAfter(today)) {
                String notYetStatus = MessageManager.getMessage(MessageConstant.STATUS_NOTYET, locale);
                statuses.put(order.getDiskId(), notYetStatus);
            } else if ((startDate.isEqual(today) || startDate.isBefore(today)) && endDate.isAfter(today)) {
                String activeStatus = MessageManager.getMessage(MessageConstant.STATUS_ACTIVE, locale);
                statuses.put(order.getDiskId(), activeStatus);
            } else {
                String expiredStatus = MessageManager.getMessage(MessageConstant.STATUS_EXPIRED, locale);
                statuses.put(order.getDiskId(), expiredStatus);
            }
        }
        return statuses;
    }
}
