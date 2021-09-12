package kz.epam.store.action.impl.user;

import kz.epam.store.action.Action;
import kz.epam.store.action.ActionResult;
import kz.epam.store.action.RequestContent;
import kz.epam.store.action.RoutingType;
import kz.epam.store.config.ParameterConstant;
import kz.epam.store.entity.User;
import kz.epam.store.exception.ActionException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.UserService;
import kz.epam.store.service.impl.UserServiceImpl;

import java.math.BigDecimal;

public class PayLoanAction implements Action {

    private static final UserService userService = new UserServiceImpl();

    @Override
    public ActionResult execute(RequestContent requestContent) throws ActionException {
        try {
            ActionResult actionResult = new ActionResult(RoutingType.REDIRECT, requestContent.getReferer());
            User user = (User) requestContent.getSessionAttribute(ParameterConstant.USER);
            user.setLoan(BigDecimal.valueOf(0));
            userService.updateUser(user);
            return actionResult;
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
